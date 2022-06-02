import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from datasets import load_metric
import numpy as np

log = logging.getLogger('file')

class TransliterationCEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of Transliteration type models
    using CER(Character Error Rate)
    """

    #def __init__(self):
    #    self.cer_score = load_metric('cer', revision="master")

    def levenshtein(self,u, v):
        prev = None
        curr = [0] + list(range(1, len(v) + 1))
        # Operations: (SUB, DEL, INS)
        prev_ops = None
        curr_ops = [(0, 0, i) for i in range(len(v) + 1)]
        for x in range(1, len(u) + 1):
            prev, curr = curr, [x] + ([None] * len(v))
            prev_ops, curr_ops = curr_ops, [(0, x, 0)] + ([None] * len(v))
            for y in range(1, len(v) + 1):
                delcost = prev[y] + 1
                addcost = curr[y - 1] + 1
                subcost = prev[y - 1] + int(u[x - 1] != v[y - 1])
                curr[y] = min(subcost, delcost, addcost)
                if curr[y] == subcost:
                    (n_s, n_d, n_i) = prev_ops[y - 1]
                    curr_ops[y] = (n_s + int(u[x - 1] != v[y - 1]), n_d, n_i)
                elif curr[y] == delcost:
                    (n_s, n_d, n_i) = prev_ops[y]
                    curr_ops[y] = (n_s, n_d + 1, n_i)
                else:
                    (n_s, n_d, n_i) = curr_ops[y - 1]
                    curr_ops[y] = (n_s, n_d, n_i + 1)
        return curr[len(v)], curr_ops[len(v)]


    def transliteration_metric_eval(self, ground_truth, machine_translation):

        try:
            
            _, (s, i, d) = self.levenshtein(self, ground_truth, machine_translation)
            cer_s = s
            cer_i = i
            cer_d = d
            cer_n = len(ground_truth)

            if cer_n > 0:
                return (cer_s + cer_i + cer_d) / cer_n
            else:
                return 0

            #if ground_truth and machine_translation:
            #    eval_score = self.cer_score.compute(predictions=machine_translation, references=ground_truth)
            #    if np.isnan(eval_score):
            #        log.error("Unable to calculate CER score")
            #        return None
            #    else:
            #        return eval_score*100
            #else:
            #    return None
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