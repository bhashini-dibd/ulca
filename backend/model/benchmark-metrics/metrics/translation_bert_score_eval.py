import logging
from datasets import load_metric
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval

log = logging.getLogger('file')

class TranslationBertScoreEval(ModelMetricEval):
    def __init__(self):
        self.bertscore = load_metric('bertscore')

    def machine_translation_metric_eval(self, ground_truth, machine_translation, language):
        bert_sup_lang = ["en","bn", "kn", "hi", "ta", "te", "pa", "mr", "ur", "ne", "ml"]
        if language in bert_sup_lang:
            try:
                bert_score = self.bertscore.compute(predictions=machine_translation, references=ground_truth, lang=language)
                prediction = sum(bert_score['f1'])/len(bert_score['f1'])
                return  prediction
            except Exception as e:
                log.exception(f"Exception in calculating BERT Score: {str(e)}")
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
