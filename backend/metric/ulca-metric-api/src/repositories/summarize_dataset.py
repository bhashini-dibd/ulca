from src.models.db import SummarizeDatasetModel
<<<<<<< Updated upstream
from src.models.db import AggregateDatasetModel
=======
>>>>>>> Stashed changes

class SummarizeDatasetRepo(object):
    def __init__(self):
        self.summarizeDatasetModel       = SummarizeDatasetModel()
<<<<<<< Updated upstream
        self.aggregateDatasetModel       = AggregateDatasetModel()
=======

>>>>>>> Stashed changes
    def search(self, search_data):
        corpus_stats = self.summarizeDatasetModel.search(search_data)
        if corpus_stats == []:
            return False, corpus_stats
        return True, corpus_stats

    def aggregate(self, search_data):
        corpus_stats = self.aggregateDatasetModel.data_aggregator(search_data)
        if corpus_stats == []:
            return False, corpus_stats
        return True, corpus_stats