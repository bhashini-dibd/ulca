import logging
from rouge import Rouge
from logging.config import dictConfig
import statistics
from models.model_metric_eval import ModelMetricEval

log = logging.getLogger('file')

class TranslationRougeScoreEval(ModelMetricEval):

    def __init__(self):
        self.rouge = Rouge()


    def machine_translation_metric_eval(self, ground_truth, machine_translation):
        rougescore = []
        try:
            for gt,mt in zip(ground_truth,machine_translation):
                score = self.rouge.get_scores(gt,mt,avg=True)
                score = score['rouge-l']['f']
                rougescore.append(score)
            #score = rouge.get_scores(ground_truth,machine_translation,avg=True)
            return statistics.mean(rougescore)
        except Exception as e:
            log.exception(f"Exception in calculating ROUGE Score: {str(e)}")
            return None



#LogConfig
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