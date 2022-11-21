from src.models.db import AggregateAi4bModelData


class SummarizeAi4bModelRepo(object):
    def __init__(self):
        self.aggregateAi4bModelData      =   AggregateAi4bModelData()

   
    #processing requests based on the data type
    def ai4b_aggregate(self, search_data):
        corpus_stats = []
        count = 0
        corpus_stats, count = self.aggregateAi4bModelData.ai4b_data_aggregator(search_data)
        return corpus_stats, count
