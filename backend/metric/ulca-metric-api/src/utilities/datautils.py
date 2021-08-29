import requests
from config import DATA_FILTER_SET_FILE_PATH,FILTER_DIR_NAME,FILTER_FILE_NAME, config_file_link
import json
import urllib.request
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class DataUtils:

    def read_filter_params(self):
        """Reading filter configs."""
        
        try:
            filename = FILTER_DIR_NAME + FILTER_FILE_NAME
            urllib.request.urlretrieve(config_file_link, filename)
            with open(filename, 'r') as stream:
                parsed = json.load(stream)
                filterconfigs = parsed['dataset']
            return filterconfigs
        except Exception as exc:
            log.exception("Exception while reading filters: " +str(exc))
            return []



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