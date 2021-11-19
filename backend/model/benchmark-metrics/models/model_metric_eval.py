class ModelMetricEval():
    """
    The Base interface for evaluation metrics for models, declares
    evaluation methods for all model task types(translation, asr, ocr).

    All concrete metric evaluation implementations should derive from this
    class and override the corresponding method.
    New metric implementations also have to be added in the configuration file
    for metrics(configs/metric_config.json) to be enabled in the application.
    """

    def machine_translation_metric_eval(self, ground_truth, machine_translation, language):
        """
        Functional interface for translation type model

        ground_truth        : golden data/human vetted sentences
        machine_translation : model generated sentences
        language            : model output language

        Returns the evaluation score
        """
        pass

    def asr_metric_eval(self, ground_truth, machine_translation):
        """
        Functional interface for ASR type model

        ground_truth        : golden data/human vetted sentences
        machine_translation : model generated sentences
        
        Returns the evaluation score
        """
        pass

    def ocr_metric_eval(self, ground_truth, machine_translation):
        """
        Functional interface for OCR type model

        ground_truth        : golden data/human vetted sentences
        machine_translation : model generated sentences
        
        Returns the evaluation score
        """
        pass