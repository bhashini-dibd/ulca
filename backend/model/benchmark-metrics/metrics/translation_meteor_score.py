import logging
from logging.config import dictConfig
import nltk
nltk.download('metric')
import statistics
from models.model_metric_eval import ModelMetricEval

log = logging.getLogger('file')

class TranslationMeteorScoreEval(ModelMetricEval):

    def __init__(self):
        pass


    def machine_translation_metric_eval(self, ground_truth, machine_translation):
        meteor = []
        try:
            for gt, mt in zip(ground_truth,machine_translation):
                meteor.append(nltk.translate.meteor_score.meteor_score([gt],mt))
            return statistics.mean(meteor)
        except Exception as e:
            log.exception(f"Exception in calculating METEOR Score: {str(e)}")
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