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
        try:
            for filter in mono_data["filters"]:
                if filter["filter"]         ==  "sourceLanguage":
                    langres                 =   repo.distinct("sourceLanguage",self.db,self.col)
                    filter["values"]        =   self.get_language_filter(langres)
                if filter["filter"]         ==  "domain":
                    domainres               =   repo.distinct("domain",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(domainres)
                if filter["filter"]         ==  "collectionSource":
                    sourceres               =   repo.distinct("collectionSource",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(sourceres)
                if filter["filter"]         ==  "license":
                    licenseres              =   repo.distinct("license",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(licenseres)
                if filter["filter"]         ==  "submitterName":
                    domainres               =   repo.distinct("submitter.name",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(domainres)
        except Exception as e:
            log.info(f"Exception on MonolingualModel :{e}")
    

    def get_language_filter(self,lang_list):
        values = []
        for data in lang_list:
            attribute = {}
            attribute["value"] = data
            attribute["label"] = LANG_CODES.get(data)
            values.append(attribute)
        return values

    def get_collection_details(self,collection_data):
        values = []
        for data in collection_data:
            collection = {}
            collection["value"] = data["_id"]
            collection["label"] = str(data["_id"]).title()
            collection["tool/method"] = []

            for obj in data["details"]:
                if "ocrTool" in obj:
                    collection["tool/method"].append({"ocrTool":obj["ocrTool"]})
            
            collection["tool/method"] = list(set(collection["tool/method"]))
            values.append(collection)
        return values

    def get_formated_data(self, attribute_data):
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