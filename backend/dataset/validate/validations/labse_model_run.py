from models.abstract_handler import BaseValidator
from configs.configs import validate_parallel_labse_threshold
from sentence_transformers import SentenceTransformer
import numpy as np
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class LabseModelRun(BaseValidator):
    """
    Runs the parallel corpus through labse model to verify the translation accuracy
    """
    def __init__(self):
        model_name = 'LaBSE'
        self.model = SentenceTransformer(model_name)

    def execute(self, request):
        log.info('----Executing the Labse model test----')
        try:
            record = request["record"]
            src_txt = record['sourceText']
            tgt_txt = record['targetText']

            source_sentences = [src_txt]
            target_sentences = [tgt_txt]

            source_sentences = list(source_sentences)
            #print("Encode source sentences", len(source_sentences))
            vector_one = self.model.encode(source_sentences, show_progress_bar=True, convert_to_numpy=True)

            target_sentences = list(target_sentences)
            #print("Encode target sentences", len(target_sentences))
            vector_two = self.model.encode(target_sentences, show_progress_bar=True, convert_to_numpy=True)

            vector_one = np.squeeze(vector_one)
            vector_two = np.squeeze(vector_two)
            dot = np.dot(vector_one, vector_two)
            norma = np.linalg.norm(vector_one)
            normb = np.linalg.norm(vector_two)
            cos = dot / (norma * normb)

            if cos < validate_parallel_labse_threshold:
                return {"message": "Low translation accuracy", "code": "LOW_TRANSLATION_SCORE", "status": "FAILED"}

            log.info('----Labse model test  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing Labse model test', e)


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