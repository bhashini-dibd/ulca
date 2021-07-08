import sqlalchemy as db
from config import MONGO_CONNECTION_URL,MONGO_DB_SCHEMA,DRUID_CONNECTION_URL
from pymongo import MongoClient
import logging

log = logging.getLogger('fie')
client = MongoClient(MONGO_CONNECTION_URL)

def get_data_store():
<<<<<<< Updated upstream
    log.info("Establishing connection with druid")
=======
    log_info("Establishing connection with druid", LOG_WITHOUT_CONTEXT)
>>>>>>> Stashed changes
    engine      = db.create_engine(DRUID_CONNECTION_URL)  
    connection  = engine.connect()
    return connection

