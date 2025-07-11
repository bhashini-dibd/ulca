import datasets
import numpy as np
from nltk.translate import chrf_score



class CHRF(datasets.Metric):
    def _info(self):
        return datasets.MetricInfo(
            description=_DESCRIPTION,
            citation=_CITATION,
            inputs_description=_KWARGS_DESCRIPTION,
            features=datasets.Features({
                "predictions": datasets.Value("string"),
                "references": datasets.Value("string"),
            }),
        )

    def _compute(self, predictions, references):
        scores = []
        for pred, ref in zip(predictions, references):
            scores.append(chrf_score.sentence_chrf(ref, pred))
        return {
            "chrf": np.mean(scores)
        }
