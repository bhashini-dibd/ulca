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
            log.info(f'Setting the Mongo M1 Sharded DB up..... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            client.drop_database(db)
            ulca_db = client[db]
            db_cli = client.admin
            db_cli.command('enableSharding', db)
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB Local.... | {datetime.now()}')


# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})