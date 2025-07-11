import datasets

class CER(datasets.Metric):
    def _info(self):
        return datasets.MetricInfo(
            description="Character Error Rate (CER)",
            citation="",
            inputs_description="Compute the character error rate (CER) between predictions and references.",
            features=datasets.Features({
                'predictions': datasets.Value('string'),
                'references': datasets.Value('string'),
            }),
            # reference_urls is optional, you can add if needed:
            # reference_urls=["https://your-docs-or-paper"]
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
