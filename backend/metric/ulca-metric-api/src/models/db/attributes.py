from sqlalchemy.sql.expression import column
from src.db import get_data_store
from sqlalchemy import text
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
        try:
            query = f"SELECT SUM(\"{count}\") as {total},{domain},{datatype},{src},{tgt},{collectionmethod},{delete}  FROM \"{DRUID_DB_SCHEMA}\" \
                        GROUP BY {domain},{datatype},{src},{tgt},{collectionmethod},{delete}"
            result_parsed = utils.query_runner(query)
            result={}
            columns =["sourceLanguage","targetLanguage","datasetType","domains","collectionMethod_collectionDescriptions"]

            for attribute in columns:
                attribute_list=[{attribute:w[attribute],total:w[total],delete:w[delete]} for w in result_parsed]
                # log.info(attribute_list,"*******")
                aggs = {}
                for item in attribute_list:
                    if aggs.get(item[attribute]) == None:
                        fields={}
                        if item["isDelete"] == "false":
                            fields[False] = item["total"]
                            fields[True]=0
                        else:
                            fields[True]=item["total"]
                            fields[False]=0

                        aggs[item[attribute]] = fields
                    else:
                        if item["isDelete"] == "false":
                            aggs[item[attribute]][False] += item["total"]
                        else:
                            aggs[item[attribute]][True] += item["total"]

                aggs_parsed ={}
                for val in aggs:
                    agg = aggs[val]
                    aggs_parsed[val] = (agg[False]-agg[True])
                # log.info("Query Result : {}".format(aggs_parsed))
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
            
            return result  
            
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