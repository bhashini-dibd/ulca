import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from sacrebleu.metrics import BLEU

log = logging.getLogger('file')

class TranslationBLEUScoreEval(ModelMetricEval):
    """
    Implementation of metric evaluation of translation type models
    using BLEU score
    """
    def __init__(self):
        self.bleu = BLEU()

    def machine_translation_metric_eval(self, ground_truth, machine_translation):

        try:
            return self.bleu.corpus_score(machine_translation, [ground_truth]).score
        except Exception as e:
            log.exception(f"Exception in calculating BLEU Score: {str(e)}")
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