from models.model_metric_eval import ModelMetricEval
from sacrebleu.metrics import BLEU

class TranslationBLEUScoreEval(ModelMetricEval):
    """
    Implementation of metric evaluation of translation type models
    using BLEU score
    """
    def __init__(self):
        self.bleu = BLEU()

    def machine_translation_metric_eval(self, ground_truth, machine_translation):

        return self.bleu.corpus_score(machine_translation, [ground_truth]).score