from logging import log
from config import data_db_schema,data_mono
from repositories import DataRepo
from utilities import LANG_CODES
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
repo = DataRepo()

class MonolingualModel:
    def __init__(self):
        self.db     =   data_db_schema
        self.col    =   data_mono

    def compute_monolingual_data_filters(self,mono_data):
        log.info("Updating monolingual filter params!")
        try:
            for filter in mono_data["filters"]:

                if filter["filter"]         ==  "collectionMethod.collectionDescription":
                    collection_method       =   repo.distinct("collectionMethod.collectionDescription",self.db,self.col)
                    filter["values"]        =   self.get_collection_details(collection_method)
                    log.info("collected availabel collection methods")
                elif filter["filter"]       ==  "sorceLanguage":
                    langres                 =   repo.distinct("sourceLanguage",self.db,self.col)
                    filter["values"]        =   self.get_language_filter(langres)
                    log.info("collected available languages") 
                else:
                    response                =   repo.distinct(filter["filter"],self.db,self.col)
                    filter["values"]        =   self.get_formated_data(response)
                    log.info(f"collected available {filter['label']}")

            return mono_data
        except Exception as e:
            log.info(f"Exception on MonolingualModel :{e}")
    

    def get_language_filter(self,lang_list):
        log.info("formatting language filter")
        values = []
        for data in lang_list:
            attribute = {}
            attribute["value"] = data
            attribute["label"] = LANG_CODES.get(data)
            values.append(attribute)
        return values

    def get_collection_details(self,collection_data):
        log.info("formatting collection method,details filter")
        values = []
        if not collection_data:
            collection = {}
            collection["value"] = ""
            collection["label"] = "Unspecified"
            values.append(collection)
        else:
            for data in collection_data:
                collection = {}
                collection["value"] = data
                collection["label"] = ' '.join(data.split('-')).title()
                values.append(collection)
        return values

    def get_formated_data(self, attribute_data):
        log.info("formatting filter attribute")
        values = []
        for data in attribute_data:
            attribute = {}
            attribute["value"] = data
            attribute["label"] = str(data).title()
            values.append(attribute)
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