import logging
from datetime import datetime
from logging.config import dictConfig
from repository.parallel import ParallelRepo
from repository.datasetrepo import DatasetRepo


log = logging.getLogger('file')

mongo_instance = None
parallelrepo = ParallelRepo()
datasetrepo = DatasetRepo()

class DatasetService:
    def __init__(self):
        pass

    def set_dataset_db(self, request):
        log.info("Setting Dataset DB..... | {}".format(datetime.now()))
        if request["all"]:
            datasetrepo.set_dataset_db()
            parallelrepo.set_parallel_collection()
        elif request["col"] == "PARALLEL":
            parallelrepo.set_parallel_collection()



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