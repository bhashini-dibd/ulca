from src.models.db import AggregateDatasetModel, AggregateModelData, AggregateBenchmarkData

class SummarizeDatasetRepo(object):
    def __init__(self):
        self.aggregateDatasetModel      =   AggregateDatasetModel()
        self.aggregateModel             =   AggregateModelData() 
        self.aggregateBenchmark         =   AggregateBenchmarkData()

   
    #processing requests based on the data type
    def aggregate(self, search_data):
        corpus_stats = []
        count = 0
        if search_data["type"] == "model":
            corpus_stats,count = self.aggregateModel.data_aggregator(search_data)
            return corpus_stats, count
        if search_data["type"] == "benchmark":
            corpus_stats,count = self.aggregateBenchmark.data_aggregator(search_data)
            return corpus_stats, count
        else:
            corpus_stats,count = self.aggregateDatasetModel.data_aggregator(search_data)
            return corpus_stats, count
