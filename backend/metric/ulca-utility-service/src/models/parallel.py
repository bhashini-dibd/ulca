from config import data_db_schema,data_parallel
from repositories import DataRepo
from utilities import LANG_CODES
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
repo = DataRepo()

class ParallelModel:
    def __init__(self):
        self.db     =   data_db_schema
        self.col    =   data_parallel

    def compute_parallel_data_filters(self,parallel_data):
        log.info("Updating parallel filter params!")
        try:

            for filter in parallel_data["filters"]:
                if filter["filter"]         ==  "languagepair":
                    language_pairs_query    =  [{"$addFields": { "languagepairs": { "$concat":[ '$sourceLanguage', ' - ', '$targetLanguage'] }}},
                                                 { "$group": {"_id": "$languagepairs"}}]
                    langres                 =   repo.aggregate(language_pairs_query,self.db,self.col)
                    lang_pairs              =   [{x["_id"].split("-")[0]:x["_id"].split("-")[1]} for x in langres]
                    filter["values"]        =   self.get_language_pairs(lang_pairs)
                    log.info("collected available languages")            
                elif filter["filter"]       ==  "collectionMethod.collectionDescription":
                    collection_method       =   repo.distinct("collectionMethod.collectionDescription",self.db,self.col)
                    filter["values"]        =   self.get_collection_details(collection_method)
                    log.info("collected available collection methods")
                else:
                    response                =   repo.distinct(filter["filter"],self.db,self.col)
                    filter["values"]        =   self.get_formated_data(response)
                    log.info(f"collected available {filter['label']}")

            return parallel_data
        except Exception as e:
            log.info(f"Exception on ParallelModel :{e}")


    def get_language_pairs(self,lang_pairs):
        log.info("formatting language filter")
        values = []
        for key in LANG_CODES:
            lang_obj    =   {}
            lang_obj["value"]   =   key
            lang_obj["label"]   =   LANG_CODES.get(key)
            lang_obj["targets"] =   []
            for pair in lang_pairs:
                for k in pair:
                    reskey  = str(k).strip()
                    resval  = pair[k].strip()
                if reskey == key:
                    target = {}
                    target["value"] = resval
                    target["label"] = LANG_CODES.get(resval)
                    lang_obj["targets"].append(target)
            values.append(lang_obj)
        return values

    
    def get_collection_details(self,collection_methods):
        log.info("formatting collection method,details filter")
        values = []
        for data in collection_methods:
            collection = {}
            collection["value"] = data
            collection["label"] = ' '.join(data.split('-')).title()
            tools =[]
            if data in ['auto-aligned' ,'auto-aligned-from-parallel-docs']:
                aligntools = repo.distinct("collectionMethod.collectionDetails.alignmentTool",self.db,self.col)
                tools.append({"Alignment Tool" : aligntools })
                tools.append({"Alignment Score Range" : ['<0.5','0.5-0.6','0.6-0.7','0.8-0.9','0.9-1.0'] })
            
            if data in ['machine-translated','machine-translated-post-edited']:
                models = repo.distinct("collectionMethod.collectionDetails.translationModel",self.db,self.col)
                tools.append({"Translation Model" : models })

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