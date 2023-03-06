import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from datasets import load_metric
import numpy as np

log = logging.getLogger('file')

class ASRWEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of ASR type models
    using WER(Word Error Rate)
    """

    def __init__(self):
        self.wer_score = load_metric('wer')

    def asr_metric_eval(self, ground_truth, machine_translation):

        try:
            if ground_truth and machine_translation:
                eval_score = self.wer_score.compute(predictions=machine_translation, references=ground_truth)
                if np.isnan(eval_score):
                    log.error("Unable to calculate WER score")
                    return None
                else:
                    return eval_score*100
            else:
                return None
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