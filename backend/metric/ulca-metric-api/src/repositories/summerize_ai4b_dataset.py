from src.models.db import AggregateAI4BDatasetModel


class SummarizeAi4bDatasetRepo(object):
    def __init__(self):
        self.aggregateAi4bDatasetModel      =   AggregateAI4BDatasetModel()

   
    #processing requests based on the data type
    def ai4b_aggregate(self, search_data):
        corpus_stats = []
        count = 0
        corpus_stats, count = self.aggregateAi4bDatasetModel.ai4b_data_aggregator(search_data)
        return corpus_stats, count
