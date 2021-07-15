from ModelMetricEvaluationInterface import ModelMetricEvaluationInterface

class MachineTranslationBLEUScoreEvaluation(ModelMetricEvaluationInterface):
    def machine_translation_metric_score_evaluation(self, ground_truth: str, hypothesis: str) -> float:
        """ Concrete class implementation for Machine Tranlsation task using BLEU 
        """
        pass
