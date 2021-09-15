import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
import numpy

log = logging.getLogger('file')

class ASRWEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of ASR type models
    using WER
    """

    def editDistance(self, r, h):
        '''
        This function is to calculate the edit distance of reference sentence and the hypothesis sentence.
        Attributes: 
            r -> the list of words produced by splitting reference sentence.
            h -> the list of words produced by splitting hypothesis sentence.
        '''
        try:
            d = numpy.zeros((len(r)+1)*(len(h)+1), dtype=numpy.uint8).reshape((len(r)+1, len(h)+1))
            for i in range(len(r)+1):
                d[i][0] = i
            for j in range(len(h)+1):
                d[0][j] = j
            for i in range(1, len(r)+1):
                for j in range(1, len(h)+1):
                    if r[i-1] == h[j-1]:
                        d[i][j] = d[i-1][j-1]
                    else:
                        substitute = d[i-1][j-1] + 1
                        insert = d[i][j-1] + 1
                        delete = d[i-1][j] + 1
                        d[i][j] = min(substitute, insert, delete)

            result = float(d[len(r)][len(h)]) / len(r) * 100
            return result
        except Exception as e:
            log.exception(f"Exception in calculating WER: {str(e)}")
            return None

    def asr_metric_eval(self, ground_truth, machine_translation):

        log.info("Forming word list from sentence list")
        word_list_ground = []
        for sent in ground_truth:
            word_list_ground.extend(sent.split())

        word_list_hypo = []
        for sent in machine_translation:
            word_list_hypo.extend(sent.split())

        log.info("Initiating distance calculation")
        return self.editDistance(word_list_ground, word_list_hypo)

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