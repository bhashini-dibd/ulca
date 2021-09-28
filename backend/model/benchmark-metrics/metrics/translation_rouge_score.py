import logging
import rouge
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval

log = logging.getLogger('file')

class TranslationRougeScoreEval(ModelMetricEval):

    def __init__(self):
        pass


    def machine_translation_metric_eval(self, ground_truth, machine_translation):
        try:
            score = rouge.get_scores(ground_truth,machine_translation)
            return score[0]['rouge-l']['f']
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