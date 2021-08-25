from src.db import get_data_store
from sqlalchemy import text
from config import TIME_CONVERSION_VAL
from src.models.api_enums import LANG_CODES
import logging
from logging.config import dictConfig
log = logging.getLogger('file')



class QueryUtils:
    def __init__(self):
        pass

    def query_runner(self,query):
        try:
            collection      =   get_data_store()
            log.info("Query executed : {}".format(query))
            result          =   collection.execute(text(query)).fetchall()
            result_parsed   =   ([{**row} for row in result])
            # log.info("Query Result : {}".format(result_parsed))
            collection.close()
            return result_parsed
        except Exception as e:
            log.exception("Exception on query execution : {}".format(str(e)))
            return []

    def result_formater(self,result_parsed,group_by_field,dtype):
        try:
            aggs={}
            for item in result_parsed:
                if aggs.get(item[group_by_field]) == None:
                    fields={}
                    if item["isDelete"] == "false":
                        fields[False] = item["total"]
                        fields[True]=0
                    else:
                        fields[True]=item["total"]
                        fields[False]=0

                    aggs[item[group_by_field]] = fields
                else:
                    if item["isDelete"] == "false":
                        aggs[item[group_by_field]][False] += item["total"]
                    else:
                        aggs[item[group_by_field]][True] += item["total"]
    
            aggs_parsed ={}
            for val in aggs:
                agg = aggs[val]
                if dtype in ["asr-corpus","asr-unlabeled-corpus"]:
                    aggs_parsed[val] = (agg[False]-agg[True])/TIME_CONVERSION_VAL
                else:
                    aggs_parsed[val] = (agg[False]-agg[True])
            log.info("Query Result : {}".format(aggs_parsed))
            chart_data =[]
            for val in aggs_parsed:
                value = aggs_parsed.get(val) 
                if value == 0:
                    continue
                elem={}
                elem["_id"]=val
                if not val:
                    elem["label"]="Unspecified"
                else:
                    if group_by_field == "primarySubmitterName": 
                        elem["label"] = val
                    else:
                        title=val.split('-')
                        elem["label"]=" ".join(title).title()
                elem["value"]=value
                chart_data.append(elem)                 
            return chart_data
        except Exception as e:
            log.exception("Exception on result parsing : {}".format(str(e)))
            return []
    
    def result_formater_for_lang_pairs(self,result_parsed,dtype,lang):
        try:
            aggs ={}
            for item in result_parsed:  
                if item["targetLanguage"] == lang:
                    check = "sourceLanguage" 
                if item["sourceLanguage"] == lang :
                    check = "targetLanguage"
                if dtype in ["asr-corpus","ocr-corpus","monolingual-corpus","asr-unlabeled-corpus"]:
                    check = "sourceLanguage"

                if aggs.get(item[check]) == None:
                    fields={}
                    if item["isDelete"] == "false":
                        fields[False] = item["total"]
                        fields[True]=0
                    else:
                        fields[True]=item["total"]
                        fields[False]=0

                    aggs[item[check]] = fields
                else:
                    if item["isDelete"] == "false":
                        aggs[item[check]][False] += item["total"]
                    else:
                        aggs[item[check]][True] += item["total"]
                
            aggs_parsed ={}
            for val in aggs:
                agg = aggs[val]
                if dtype in ["asr-corpus","asr-unlabeled-corpus"]:
                    aggs_parsed[val] = (agg[False]-agg[True])/TIME_CONVERSION_VAL
                else:
                    aggs_parsed[val] = (agg[False]-agg[True])
            log.info("Query Result : {}".format(aggs_parsed))
            chart_data =[]
            for val in aggs_parsed:
                value = aggs_parsed.get(val) 
                if value == 0:
                    continue
                elem={}
                label = LANG_CODES.get(val)
                if label == None:
                    label = val
                elem["_id"]=val
                elem["label"]=label
                elem["value"]=value
                chart_data.append(elem)     
            return chart_data
        except Exception as e:
            log.exception("Exception on result parsing for lang pairs : {}".format(str(e)))
            return []

    def del_count_calculation(self,attribute,attribute_list):
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

        return aggs_parsed

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