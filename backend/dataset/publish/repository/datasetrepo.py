import logging
import pymongo
from datetime import datetime
from logging.config import dictConfig
from configs.configs import db_cluster, db

log = logging.getLogger('file')

mongo_instance = None

class DatasetRepo:
    def __init__(self):
        pass

    def set_dataset_db(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo Sharded DB up.....')
            client = pymongo.MongoClient(db_cluster)
            client.drop_database(db)
            ulca_db = client[db]
            db_cli = client.admin
            db_cli.command('enableSharding', db)
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB Local...')