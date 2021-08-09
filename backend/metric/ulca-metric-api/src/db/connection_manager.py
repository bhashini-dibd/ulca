import sqlalchemy as db
from config import MONGO_CONNECTION_URL,MONGO_DB_SCHEMA,MONGO_MODEL_COLLECTION,DRUID_CONNECTION_URL
import pymongo
import logging


log = logging.getLogger('fie')
def get_data_store():
    log.info("Establishing connection with druid")
    engine      = db.create_engine(DRUID_CONNECTION_URL)  
    connection  = engine.connect()
    return connection


mongo_instance = None

class ModelRepo:
    
    def __init__(self):
       pass
    #method to instantiate mongo client object
    def instantiate(self,schema,collection):
        global mongo_instance
        client = pymongo.MongoClient(MONGO_CONNECTION_URL)
        if schema == None and collection == None:
            mongo_instance = client[MONGO_DB_SCHEMA][MONGO_MODEL_COLLECTION]
        else:
            mongo_instance = client[schema][collection]
        return mongo_instance

    #geting the mongo clent object
    def get_mongo_instance(self,schema=None,collection=None):
        global mongo_instance
        if not mongo_instance:
            return self.instantiate(schema,collection)
        else:
            return mongo_instance

    def aggregate(self, query,schema=None,collection=None):
        log.info(query,schema,collection)
        try:
            col = self.get_mongo_instance(schema,collection)
            res =   col.aggregate(query) 
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo aggregate: {e}', e)
            return []
    
    def count(self, query,schema=None,collection=None):
        try:
            col = self.get_mongo_instance(schema,collection)
            res =   col.count(query) 
            return res
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []