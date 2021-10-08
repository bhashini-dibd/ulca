import logging
from bert_score import score
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval

log = logging.getLogger('file')

class TranslationBertScoreEval(ModelMetricEval):
    def __init__(self):
        pass

    def machine_translation_metric_eval(self, ground_truth, machine_translation):
        try:
            P, R, F1 = score(ground_truth,machine_translation,lang="en",verbose=True)
            return  f"{F1.mean():.3f}"
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
