import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
import fastwer

log = logging.getLogger('file')

class ASRWEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of ASR type models
    using WER(Word Error Rate)
    """

    def asr_metric_eval(self, ground_truth, machine_translation):

        try:
            return fastwer.score(machine_translation, ground_truth)
        except Exception as e:
            log.exception(f"Exception in calculating WER: {str(e)}")
            return None

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