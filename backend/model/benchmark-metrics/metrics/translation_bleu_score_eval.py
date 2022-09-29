import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from sacrebleu.metrics import BLEU

log = logging.getLogger('file')

from indicnlp.tokenize import indic_tokenize
from indicnlp.normalize import indic_normalize
from sacremoses import MosesPunctNormalizer
from sacremoses import MosesTokenizer

class TranslationBLEUScoreEval(ModelMetricEval):
    """
    Implementation of metric evaluation of translation type models
    using BLEU score
    """
    def __init__(self):
        self.bleu = BLEU(tokenize='none')
        self.en_tokenizer = MosesTokenizer(lang="en")
        self.en_normalizer = MosesPunctNormalizer()
        self.indic_normalizer = None

    def preprocess_en(self, line):
        return " ".join(self.en_tokenizer.tokenize(self.en_normalizer.normalize(line.strip()), escape=False))

    def preprocess_indic(self, line, lang):
        return " ".join(indic_tokenize.trivial_tokenize(self.indic_normalizer.normalize(line.strip()), lang))

    def preprocess_data(self, in_data, lang):

        if lang == "en":
            return [self.preprocess_en(sentence) for sentence in in_data]
        else:
            normfactory = indic_normalize.IndicNormalizerFactory()
            self.indic_normalizer = normfactory.get_normalizer(lang)
            return [self.preprocess_indic(sentence, lang) for sentence in in_data]

    def machine_translation_metric_eval(self, ground_truth, machine_translation, language):

        try:
            ground_truth = self.preprocess_data(ground_truth, language)
            machine_translation = self.preprocess_data(machine_translation, language)

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