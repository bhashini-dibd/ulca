from src.models.db import SummarizeDatasetModel
from src.models.db import AggregateDatasetModel

class SummarizeDatasetRepo(object):
    def __init__(self):
        self.summarizeDatasetModel       = SummarizeDatasetModel()
        self.aggregateDatasetModel       = AggregateDatasetModel()

    def search(self):
        corpus_stats = self.summarizeDatasetModel.search()
        return  corpus_stats

    def aggregate(self, search_data):
        corpus_stats,count = self.aggregateDatasetModel.data_aggregator(search_data)
        if not corpus_stats:
            return corpus_stats,0
        return corpus_stats, count