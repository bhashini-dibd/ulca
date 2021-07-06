import sqlalchemy as db
from config import MONGO_CONNECTION_URL,MONGO_DB_SCHEMA,DRUID_CONNECTION_URL
from pymongo import MongoClient
import logging

log = logging.getLogger('fie')
client = MongoClient(MONGO_CONNECTION_URL)

def get_data_store():
    log.info("Establishing connection with druid")
    engine      = db.create_engine(DRUID_CONNECTION_URL)  
    connection  = engine.connect()
    return connection

