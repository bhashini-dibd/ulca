import logging
from datetime import datetime
import numpy as np
from logging.config import dictConfig
from kafkawrapper.producer import Producer
from models.metric_manager import MetricManager
from utils.mongo_utils import BenchMarkingProcessRepo
from configs.configs import ulca_notifier_input_topic, ulca_notifier_benchmark_completed_event, ulca_notifier_benchmark_failed_event

log = logging.getLogger('file')
prod = Producer()
repo = BenchMarkingProcessRepo()


class TransliterationMetricEvalHandler:
    def __init__(self):
        pass

    def execute_transliteration_metric_eval(self, request):
        try:
            log.info("Executing transliteration Metric Evaluation....  {}".format(datetime.now()))
            metric_mgr = MetricManager.getInstance()
            if 'benchmarkDatasets' in request.keys():
                for benchmark in request["benchmarkDatasets"]:
                    metric_inst = metric_mgr.get_metric_execute(benchmark["metric"], request["modelTaskType"])
                    if not metric_inst:
                        log.info("Metric definition not found")
                        doc = {'benchmarkingProcessId':request['benchmarkingProcessId'],'benchmarkDatasetId': benchmark['datasetId'],'eval_score': None}
                        log.info(f'doc {doc}')
                        repo.insert(doc)
                        repo.insert_pt({'benchmarkingProcessId': request['benchmarkingProcessId'], 'status': 'Failed'})
                        mail_notif_event = {"event": ulca_notifier_benchmark_failed_event, "entityID": request['modelId'], "userID": request['userId'], "details":{"modelName":request['modelName']}}
                        prod.produce(mail_notif_event, ulca_notifier_input_topic, None)
                        return

                    ground_truth = [corpus_sentence["tgt"] for corpus_sentence in benchmark["corpus"]]
                    machine_translation = [corpus_sentence["mtgt"] for corpus_sentence in benchmark["corpus"]]
                    eval_score = metric_inst.transliteration_metric_eval(ground_truth, machine_translation)
                    log.info(f'eval_score {eval_score}')
                    if eval_score is not None:
                        doc = {'benchmarkingProcessId':request['benchmarkingProcessId'],'benchmarkDatasetId': benchmark['datasetId'],'eval_score': float(np.round(eval_score, 3))}
                        repo.insert(doc)
                        repo.insert_pt({'benchmarkingProcessId': request['benchmarkingProcessId'], 'status': 'Completed'})
                        mail_notif_event = {"event": ulca_notifier_benchmark_completed_event, "entityID": request['modelId'], "userID": request['userId'], "details":{"modelName":request['modelName']}}
                        prod.produce(mail_notif_event, ulca_notifier_input_topic, None)
                    else:
                        log.exception("Exception while calculating metric score of model")
                        doc = {'benchmarkingProcessId':request['benchmarkingProcessId'],'benchmarkDatasetId': benchmark['datasetId'],'eval_score': None}
                        repo.insert(doc)
                        repo.insert_pt({'benchmarkingProcessId': request['benchmarkingProcessId'], 'status': 'Failed'})
                        mail_notif_event = {"event": ulca_notifier_benchmark_failed_event, "entityID": request['modelId'], "userID": request['userId'], "details":{"modelName":request['modelName']}}
                        prod.produce(mail_notif_event, ulca_notifier_input_topic, None)
            else:
                log.exception("Missing parameter: benchmark details")
                repo.insert_pt({'benchmarkingProcessId': request['benchmarkingProcessId'], 'status': 'Failed'})
                mail_notif_event = {"event": ulca_notifier_benchmark_failed_event, "entityID": request['modelId'], "userID": request['userId'], "details":{"modelName":request['modelName']}}
                prod.produce(mail_notif_event, ulca_notifier_input_topic, None)
                return
        except Exception as e:
            log.exception(f"Exception while metric evaluation of model: {str(e)}")
            repo.insert_pt({'benchmarkingProcessId': request['benchmarkingProcessId'], 'status': 'Failed'})
            mail_notif_event = {"event": ulca_notifier_benchmark_failed_event, "entityID": request['modelId'], "userID": request['userId'], "details":{"modelName":request['modelName']}}
            prod.produce(mail_notif_event, ulca_notifier_input_topic, None)

# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})