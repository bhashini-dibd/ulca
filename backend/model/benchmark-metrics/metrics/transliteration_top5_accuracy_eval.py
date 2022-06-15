import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from collections import Counter 


log = logging.getLogger('file')

class TransliterationTopFiveAccuracyEval(ModelMetricEval):
    """
    Implementation of metric evaluation of Transliteration type models
    using Accuracy
    """

    def transliteration_metric_eval(self, ground_truth, machine_translation):
        '''
        Evaluates metrics by comparing the words from ground_truth and machine_translation
        '''

        try:

            if ground_truth and machine_translation:
                top5_match  =  []
                matches = set(ground_truth) & set(machine_translation)
                if len(matches) == 1 or len(matches) >= 1: 
                    top5_match.append(1)
                else:
                    top5_match.append(0)
                top5_res = Counter(top5_match)
                return top5_res[1]/len(top5_match) *100
        except Exception as e:
            log.exception(f"Exception in calculating Accuracy: {str(e)}")


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