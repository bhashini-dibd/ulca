from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from src.models.enums.app_enums import STAGE, CORPUS_TYPE

import uuid
import datetime

class SummarizeDataset(object):
    def create(self, dataset, tags):
        self.datasetId      = dataset['datasetId']
        self.tags           = tags
        self.count          = dataset['count']

        return {
            'datasetId': self.datasetId,
            'tags': self.tags,
            'count': self.count
        }

    def mandatory_params(self, data):
        keys    = ['type', 'criterions', 'groupby']
        for key in keys:
            if key not in data.keys():
                return False, key
        return True, None
    
    def get_value_from_key(self, data, key):
        return data[key]
    
    def get_validated_data(self, data):
        status, key = self.mandatory_params(data)
        if not status:
            return status, key
        return True, data
