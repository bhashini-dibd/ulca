from config import DRUID_DB_SCHEMA
from src.models.api_enums import DataEnums ,LANG_CODES, DATA_TYPES
import logging
from logging.config import dictConfig
from .queryutils import QueryUtils
log = logging.getLogger('file')

utils = QueryUtils()


class SummarizeDatasetModel(object):
    #data aggregation
    def search(self):
        count           =   "count"
        total           =   "total"
        src             =   "sourceLanguage"
        tgt             =   "targetLanguage"
        delete          =   "isDelete"
        datatype        =   "datasetType" 
        domain          =   "domains"
        collectionmethod    =   "collectionMethod_collectionDescriptions"
        parallel        =   "parallel-corpus" 
        try:
            query = f"SELECT SUM(\"{count}\") as {total},{domain},{datatype},{src},{tgt},{collectionmethod},{delete}  FROM \"{DRUID_DB_SCHEMA}\" \
                        GROUP BY {domain},{datatype},{src},{tgt},{collectionmethod},{delete}"

            result_parsed = utils.query_runner(query)
            
            columns =["sourceLanguage","targetLanguage","domains","collectionMethod_collectionDescriptions"]
            # 'datasetType': 'monolingual-corpus'
            dtypes = [w["datasetType"] for w in result_parsed]
            dtypes = list(set(dtypes))
            log.info(dtypes)
            data = {}
            for dom in dtypes:
                result={}
                for attribute in columns:
                    attributes_sorted = []
                    for object in result_parsed:
                        if object["datasetType"]  == dom:
                            attributes_sorted.append(object)
                    attribute_list=[{attribute:w[attribute],total:w[total],delete:w[delete]} for w in attributes_sorted]
                    aggs_parsed = utils.del_count_calculation(attribute,attribute_list)
                    chart_data =[]
                    for val in aggs_parsed:
                        if aggs_parsed.get(val) == 0:
                            continue
                        elem={}
                        if attribute in ["sourceLanguage","targetLanguage"]:
                            if not val:
                                continue
                            label = LANG_CODES.get(val)
                            if label == None:
                                label = val
                        elif attribute in ["datasetType"]:
                            label = DATA_TYPES.get(val)
                            if label == None:
                                label = val
                        elif not val:
                            label = "Unspecified"
                        else:
                            title=val.split('-')
                            label=" ".join(title).title()

                        elem["value"]=val
                        elem["label"]=label

                        chart_data.append(elem)
                
                    result[attribute] = chart_data
                data[dom] = result



            attribute = "lang"
            para_result  = [{attribute : w[src]+'-'+w[tgt], total : w[total], delete : w[delete]} for w in result_parsed if w["datasetType"] == parallel]
            # log.info(para_result)
            count_formatted = utils.del_count_calculation(attribute,para_result)
            countDict = {key:val for key, val in count_formatted.items() if val > 0}
            para_languages = {}
            for key,val in LANG_CODES.items():
                para_list =[]
                for lkey in countDict:
                    codes = lkey.split('-')
                    if key in codes:
                        codes.remove(key)
                        para_list.append(codes[0])
                para_languages[key] = para_list

            print(para_languages,"*******")
            # languages =[]
            # for w in count_formatted:
            #     if count_formatted.get(w) == 0:
            #         continue
                

            # log.info(countDict)

            return data  
            
        except Exception as e:
            log.exception("db connection exception : {}".format(str(e)))
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