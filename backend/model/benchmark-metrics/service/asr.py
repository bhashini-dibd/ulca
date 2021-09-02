import logging
from datetime import datetime
from logging.config import dictConfig

from models.metric_manager import MetricManager
from utils.mongo_utils import BenchMarkingProcessRepo

log = logging.getLogger('file')
repo = BenchMarkingProcessRepo()


class ASRMetricEvalHandler:
    def __init__(self):
        pass

    def execute_asr_metric_eval(self, request):
        try:
            log.info("Executing ASR Metric Evaluation....  {}".format(datetime.now()))
            metric_mgr = MetricManager.getInstance()
            if 'benchmarkDatasets' in request.keys():
                for benchmark in request["benchmarkDatasets"]:
                    metric_inst = metric_mgr.get_metric_execute(benchmark["metric"])
                    if not metric_inst:
                        log.info("Metric definition not found")
                        return

                    ground_truth = [corpus_sentence["tgt"] for corpus_sentence in benchmark["corpus"]]
                    machine_translation = [corpus_sentence["mtgt"] for corpus_sentence in benchmark["corpus"]]
                    eval_score = metric_inst.asr_metric_eval(ground_truth, machine_translation)
                    if eval_score:
                        #benchmark["corpus_eval_score"] = eval_score
                        doc = {'benchmarkingProcessId':request['benchmarkingProcessId'],'datasetId': benchmark['datasetId'],'score':eval_score}        
                        repo.insert(doc)
                    else:
                        log.exception("Exception while metric evaluation of model")
            else:
                log.info("Missing parameter: benchmark details")
                return
        except Exception as e:
            log.exception(f"Exception while metric evaluation of model: {str(e)}")

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