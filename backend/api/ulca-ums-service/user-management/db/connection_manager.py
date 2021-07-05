from config import MONGO_SERVER_HOST, MONGO_DB_SCHEMA
from utilities import MODULE_CONTEXT
from pymongo import MongoClient
from flask import g
import logging

log = logging.getLogger('file')


# establishing connection with mongo instance
def get_db():
    if 'db' not in g:
        log.info("Establishing database connectivity for the current request")
        client = MongoClient(MONGO_SERVER_HOST)
        g.db = client[MONGO_DB_SCHEMA]
    return g.db