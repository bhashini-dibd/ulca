"""Defines the interface for model evaluation metrics.
    Look at the examples to define and contribute your evaluation metrics 

    The recommended naming convention in case you are submitting new metric should adhere to following guidelines
        <model_task_name><metric name>Evaluation.py
    e.g. ASRWEREvaluation.py or MachineTranslationMETEOREvaluation.py

    Submitter should override the specific method to create own implementatio

"""
class ModelMetricEvaluationInterface:
    def machine_translation_metric_score_evaluation(self, ground_truth: str, hypothesis: str) -> float:
        """
            defines machine translation model scoring function interface

            ground_truth: human vetted sentence
            hypothesis:  model predicted/generated sentence

            returns evaluation score
        """
        pass

    def asr_metric_score_evaluation(self, ground_truth: str, hypothesis: str) -> float:
        """
            defines ASR model scoring function interface

            ground_truth: human vetted sentence
            hypothesis:  model predicted/generated sentence

            returns evaluation score
        """
        pass

    def ocr_metric_score_evaluation(self, ground_truth: str, hypothesis: str, char_level: bool) -> float:
        """
            defines ASR model scoring function interface

            ground_truth: human vetted sentence
            hypothesis:  model extracted sentence
            char_level: provide scoring at word or character level

            returns evaluation score
        """
        pass
