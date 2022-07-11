import requests
from config import shared_storage_folder
import json
import urllib.request
import os
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
from jsonpath_ng import jsonpath, parse
from .response import post_error

class MdUtils:
    #reading file from git 
    def read_from_git(self,git_path):
        log.info(f"reading from git: {git_path}")
        try:
            file            =   requests.get(git_path, allow_redirects=True)
            log.info(f'file content {file.content}')
            parsed          =   json.loads(file.content)
            log.info(f'{parsed}')
            return parsed
        except Exception as exe:
            log.exception(f"Exception while reading from git:{exe} " )
            return None

    #parsing json using jsonpath expression
    def jsonpath_parsing(self,json_data,expression):
        log.info("parsing json using jsonpath")
        path_expression =   parse(expression)
        log.info(f'path expression {path_expression}')
        log.info(f'json_data{json_data}')
        values          =   path_expression.find(json_data)[0].value
        log.info(f'values {values}')
        return values



        

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