import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from collections import Counter 


log = logging.getLogger('file')

class TransliterationTopOneAccuracyEval(ModelMetricEval):
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
                top1_match  =  []
                for grnd, mchn in zip(ground_truth,machine_translation):
                    if grnd == mchn:
                        top1_match.append(1)
                    else:
                        top1_match.append(0)
                top1_res = Counter(top1_match)
                return top1_res[1]/len(ground_truth) *100
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