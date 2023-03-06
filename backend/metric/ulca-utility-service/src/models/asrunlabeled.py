from config import data_db_schema,data_asr_unlabeled
from repositories import DataRepo
import utilities
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
repo = DataRepo()

class AsrUnlabeledModel:
    def __init__(self):
        self.db     =   data_db_schema
        self.col    =   data_asr_unlabeled

    def compute_asr_unlabeled_data_filters(self,asr_unlabeled_data):
        log.info("Updating asr-unlabeled filter params!")
        try:
            for filter in asr_unlabeled_data["filters"]:
                if filter["filter"]         ==  "sourceLanguage":
                    langres                 =   repo.distinct("sourceLanguage",self.db,self.col)
                    filter["values"]        =   self.get_language_filter(langres)
                    log.info("collected available languages")
                elif filter["filter"]       ==  "collectionMethod.collectionDescription":
                    collection_method       =   repo.distinct("collectionMethod.collectionDescription",self.db,self.col)
                    filter["values"]        =   self.get_collection_details(collection_method)
                    log.info("collected availabel collection methods")
                else:
                    response                =   repo.distinct(filter["filter"],self.db,self.col)
                    filter["values"]        =   self.get_formated_data(response)
                    log.info(f"collected available {filter['label']}")
                
            return asr_unlabeled_data
        except Exception as e:
            log.info(f"Exception on AsrUnlabeledModel :{e}")


    
    def get_language_filter(self,lang_list):
        log.info("formatting language filter")
        values = []
        for data in lang_list:
            attribute = {}
            attribute["value"] = data
            attribute["label"] = utilities.static.LANG_CODES.get(data)
            values.append(attribute)
        return values
    
    def get_collection_details(self,collection_methods):
        log.info("formatting collection method,details filter")
        values = []
        for data in collection_methods:
            collection = {}
            collection["value"] = data
            collection["label"] = ' '.join(data.split('-')).title()
            tools =[]
            if data in ['auto-aligned"']:
                asrtools = repo.distinct("collectionMethod.collectionDetails.alignmentTool'",self.db,self.col)
                tools.append({"Alignment Tool" : asrtools })
                tools.append({"Alignment Score Range" : ['<0.5','0.5-0.6','0.6-0.7','0.8-0.9','0.9-1.0'] })

            if data in ['machine-generated-transcript']:
                models = repo.distinct("collectionMethod.collectionDetails.asrModel",self.db,self.col)
                tools.append({"ASR Model" : models })

                evaluation_tools = repo.distinct("collectionMethod.collectionDetails.evaluationMethod",self.db,self.col)
                tools.append({"Evaluation Method" : evaluation_tools })
            
            collection["tool/method"] = tools
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