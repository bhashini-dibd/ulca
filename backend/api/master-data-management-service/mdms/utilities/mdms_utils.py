import requests
from config import shared_storage_folder
import json
import urllib.request
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class MdUtils:

    def read_from_git(self,git_path):
        try:
            file = requests.get(git_path, allow_redirects=True)
            data_file  = (git_path.split('/'))[-1]
            file_path = shared_storage_folder + data_file
            open(file_path, 'wb').write(file.content)
            log.info(f"Attributes read from git and pushed to local {file_path}")
            with open(file_path, 'r') as stream:
                parsed = json.load(stream)
                filter_name     =   data_file.replace(".json","")
                filterconfigs = parsed[filter_name]
            return filterconfigs
        except Exception as exc:
            log.exception("Exception while reading filters: " +str(exc))
            return None, None

    



        

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