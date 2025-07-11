import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from datasets import load_metric
import numpy as np
import os

log = logging.getLogger('file')

class TranslationChrfScoreEval(ModelMetricEval):
    """
    Implementation of metric evaluation of Translation type models
    using Chrf
    ChrF is MT evaluation metrics that use the F-score statistic for character n-gram matches.
    """

    def __init__(self):
        #self.chrf_score = load_metric('chrf', revision='master')
        chrf_path = os.path.join(os.path.dirname(__file__), 'chrf')
        self.chrf_score = load_metric(chrf_path)


    def machine_translation_metric_eval(self, ground_truth, machine_translation,language):

        try:
            
           if ground_truth and machine_translation:
                conv_grnd_trth = [[i] for i in ground_truth]
                eval_score = self.chrf_score.compute(predictions=machine_translation, references=conv_grnd_trth, lowercase=True)# MT --> list of string(sentences), GT-->list of list of string(sentences)
                if np.isnan(eval_score['score']):
                    log.error("Unable to calculate chrf score for translation")
                    return None
                else:
                    return eval_score['score']
           else:
                return None
        except Exception as e:
            log.exception(f"Exception in calculating chrf: {str(e)}")
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