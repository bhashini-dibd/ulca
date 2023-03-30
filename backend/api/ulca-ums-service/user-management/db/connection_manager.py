from config import MONGO_SERVER_HOST, MONGO_DB_SCHEMA, MONGO_PROCESS_DB_SCHEMA, DB_NAME
from utilities import MODULE_CONTEXT
from pymongo import MongoClient
from flask import g
import logging

log = logging.getLogger('file')



client = MongoClient(
  host = MONGO_SERVER_HOST)



# establishing connection with mongo instance
def get_db():
    log.info("Establishing database connectivity for the current request")
    #db = client[""]
    db = client[MONGO_DB_SCHEMA]
    return db

def get_process_db():
    log.info("Establishing database connectivity for the current request")
    db = client[MONGO_PROCESS_DB_SCHEMA]
    return db