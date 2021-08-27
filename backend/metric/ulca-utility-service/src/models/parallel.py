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
            language_pairs_query = [{"$addFields": { "languagepairs": { "$concat":[ '$sourceLanguage', ' - ', '$targetLanguage'] }}},
                                    { "$group": {"_id": "$languagepairs"}}]
            langres             =   repo.aggregate(language_pairs_query,self.db,self.col)
            lang_pairs          =   [{x["_id"].split("-")[0]:x["_id"].split("-")[1]} for x in langres]

            collection_query    =   [{ '$unwind':'$collectionMethod' },{ '$unwind':'$collectionMethod.collectionDescription' },{ '$group': { '_id': '$collectionMethod.collectionDescription', 'details': {'$addToSet': '$collectionMethod.collectionDetails'}}}]
            collectionres       =   repo.aggregate(collection_query,self.db,self.col)

            for filter in parallel_data["filters"]:
                if filter["filter"]         ==  "languagepair":
                    filter["values"]        =   self.get_language_pairs(lang_pairs)
                    log.info("collected available languages")
                if filter["filter"]         ==  "collectionMethod":
                    filter["values"]        =   self.get_collection_details(collectionres)
                    log.info("collected available collection methods")
                if filter["filter"]         ==  "domain":
                    domainres               =   repo.distinct("domain",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(domainres)
                    log.info("collected available domains")
                if filter["filter"]         ==  "collectionSource":
                    sourceres               =   repo.distinct("collectionSource",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(sourceres)
                    log.info("collected available sources")
                if filter["filter"]         ==  "license":
                    licenseres               =   repo.distinct("license",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(licenseres)
                    log.info("collected available licenses")
                if filter["filter"]         ==  "submitterName":
                    domainres               =   repo.distinct("submitter.name",self.db,self.col)
                    filter["values"]        =   self.get_formated_data(domainres)
                    log.info("collected available submitter names")
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

    def get_collection_details(self,collection_data):
        log.info("formatting collection method,details filter")
        values = []
        for data in collection_data:
            collection = {}
            collection["value"] = data["_id"]
            collection["label"] = str(data["_id"]).title()
            tools =[]
            for obj in data["details"]:
                if "alignmentTool" in obj:
                    tools.append({"alignmentTool":obj["alignmentTool"]})
                 
                if "translationModel" in obj:
                    tools.append({"translationModel":obj["translationModel"]})

                if "editingTool" in obj:
                    tools.append({"editingTool":obj["editingTool"]})

                if "evaluationMethod" in obj:
                    tools.append({"evaluationMethod":obj["evaluationMethod"]})
            
            collection["tool/method"] = [i for n, i in enumerate(tools) if i not in tools[n + 1:]]
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