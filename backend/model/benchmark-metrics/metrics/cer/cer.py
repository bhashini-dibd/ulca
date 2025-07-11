import datasets

class CER(datasets.Metric):
    def _info(self):
        return datasets.MetricInfo(
            description="Character Error Rate (CER)",
            citation="",
            inputs_description="Predictions and references for CER.",
            features=datasets.Features({
                'prediction': datasets.Value('string'),
                'reference': datasets.Value('string'),
            }),
        )

    def _compute(self, predictions, references):
        import editdistance
        total_edits = 0
        total_chars = 0
        for pred, ref in zip(predictions, references):
            total_edits += editdistance.eval(pred, ref)
            total_chars += len(ref)
        return {
            'cer': total_edits / total_chars if total_chars > 0 else float('inf')
        }