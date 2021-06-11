import config
import datetime
import os
import uuid

from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from anuvaad_auditor.loghandler import log_info, log_exception
from src.models.db import DatasetModel
from src.models.enums.app_enums import CORPUS_TYPE

class DatasetRepo(object):
    def __init__(self):
        self.datasetModel       = DatasetModel()

    def store_parallel_corpus_dataset(self, dataset):
        if self.datasetModel.store(dataset) == True:
            return self.datasetModel.search(dataset['datasetId'])
        return False