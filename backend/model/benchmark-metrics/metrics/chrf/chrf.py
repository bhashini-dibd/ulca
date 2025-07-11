import datasets
import numpy as np
from nltk.translate import chrf_score

_DESCRIPTION = """\
CHRF is an automatic evaluation metric for machine translation based on character n-gram precision and recall.
"""

_CITATION = """\
@inproceedings{popovic-2015-chrf,
    title = "{CHRF}: character n-gram {F}-score for automatic {MT} evaluation",
    author = "Popovic, Maja",
    booktitle = "Proceedings of the Tenth Workshop on Statistical Machine Translation",
    year = "2015",
    pages = "392--395",
}
"""

_KWARGS_DESCRIPTION = """
Args:
    predictions: list of predicted sentences
    references: list of reference sentences

Returns:
    chrf: average CHRF score
"""

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
