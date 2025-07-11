import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from datasets import load_metric
import numpy as np
import os

log = logging.getLogger('file')

class ASRCEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of ASR type models
    using CER(Character Error Rate)
    """

    def __init__(self):
        #self.cer_score = load_metric('cer', revision="master")
        cer_path = os.path.join(os.path.dirname(__file__), 'cer')
        self.cer_score = load_metric(cer_path)

    def asr_metric_eval(self, ground_truth, machine_translation):

        try:
            if ground_truth and machine_translation:
                eval_score = self.cer_score.compute(predictions=machine_translation, references=ground_truth)
                if np.isnan(eval_score):
                    log.error("Unable to calculate CER score")
                    return None
                else:
                    return eval_score*100
            else:
                return None
        except Exception as e:
            log.exception(f"Exception in calculating CER: {str(e)}")
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