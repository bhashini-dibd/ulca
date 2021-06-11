import config
import datetime
import os
import uuid

from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from anuvaad_auditor.loghandler import log_info, log_exception
from src.models.db import SummarizeDatasetModel
from src.models.enums.app_enums import CORPUS_TYPE

class SummarizeDatasetRepo(object):
    def __init__(self):
        self.summarizeDatasetModel       = SummarizeDatasetModel()

    def store_summarize_dataset(self, dataset):
        if self.summarizeDatasetModel.store(dataset) == True:
            return True
        return False

    def search(self, search_data):
        corpus_stats = self.summarizeDatasetModel.search(search_data)
        if corpus_stats == []:
            return False, corpus_stats
        return True, corpus_stats