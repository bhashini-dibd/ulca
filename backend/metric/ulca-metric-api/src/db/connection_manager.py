import sqlalchemy as db
from sqlalchemy.orm import sessionmaker
from config import MONGO_CONNECTION_URL,MONGO_DB_SCHEMA,DRUID_CONNECTION_URL
from pymongo import MongoClient
from anuvaad_auditor.loghandler import log_info, log_exception
from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from flask import g

client = MongoClient(MONGO_CONNECTION_URL)

def get_db():
    log_info("Establishing connection with mongo", LOG_WITHOUT_CONTEXT)
    return client[MONGO_DB_SCHEMA]


def get_data_store():
    log_info("Establishing connection with druid", LOG_WITHOUT_CONTEXT)
    # db.MetaData()
    # Session     = sessionmaker(bind=engine)
    engine      = db.create_engine(DRUID_CONNECTION_URL)  
    connection  = engine.connect()
    return connection

