from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from src.utilities.pymongo_data_handling import normalize_bson_to_json

from src.db import get_db
from anuvaad_auditor.loghandler import log_info, log_exception
import pymongo

DB_SCHEMA_NAME  = 'dataset_v2'

class DatasetModel(object):
    def __init__(self):
        collections = get_db()[DB_SCHEMA_NAME]
        try:
            collections.create_index('datasetId')
        except pymongo.errors.DuplicateKeyError as e:
            log_info("duplicate key, ignoring", LOG_WITHOUT_CONTEXT)
        except Exception as e:
            log_exception("db connection exception ",  LOG_WITHOUT_CONTEXT, e)

    def store(self, dataset):
        try:
            collections = get_db()[DB_SCHEMA_NAME]
            result     = collections.insert_one(dataset)
            if result != None and result.acknowledged == True:
                return True
        except Exception as e:
            log_exception("db connection exception ",  LOG_WITHOUT_CONTEXT, e)
            return False

    def search(self, datasetId):
        updated_docs    = []
        try:
            collections     = get_db()[DB_SCHEMA_NAME]
            docs            = collections.find({'datasetId': datasetId})
            for doc in docs:
                updated_docs.append(normalize_bson_to_json(doc))
            return updated_docs[0]
        except Exception as e:
            log_exception("db connection exception ",  LOG_WITHOUT_CONTEXT, e)
            return []
    