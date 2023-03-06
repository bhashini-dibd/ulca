from src.db import get_data_store
from sqlalchemy import text
from config import TIME_CONVERSION_VAL, mdms_bulk_fetch_url
import logging
import requests
import json
from logging.config import dictConfig
log = logging.getLogger('file')

masterDataVals = None


class QueryUtils:
    
    def __init__(self):
        #labels for charts are fetched back from "MDMS Service"
        self.mdmsconfigs=   self.get_master_data() 

    def query_runner(self,query):
        """
        Executing Druid query
        """
        try:
            collection      =   get_data_store()
            log.info("Query executed : {}".format(query))
            result          =   collection.execute(text(query)).fetchall()
            result_parsed   =   ([{**row} for row in result])
            collection.close()
            return result_parsed
        except Exception as e:
            log.exception("Exception on query execution : {}".format(str(e)))
            return []

    def result_formater(self,result_parsed,group_by_field,dtype):
        """
        Formatting the results, labelling
        """
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
                if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
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
                        label = self.mdmsconfigs.get(val)
                        if label:
                            elem["label"] = label["label"]
                        if not label:
                            title=val.split('-')
                            elem["label"]=" ".join(title).title()
                elem["value"]=value
                chart_data.append(elem)                 
            return chart_data
        except Exception as e:
            log.exception("Exception on result parsing : {}".format(str(e)))
            return []
    
    def result_formater_for_lang_pairs(self,result_parsed,dtype,lang):
        """
        Formatting the results for language pairs, labelling
        """
        try:
            aggs ={}
            for item in result_parsed:  
                if item["targetLanguage"] == lang:
                    check = "sourceLanguage" 
                if item["sourceLanguage"] == lang :
                    check = "targetLanguage"
                if dtype in ["asr-corpus","ocr-corpus","monolingual-corpus","document-layout-corpus","asr-unlabeled-corpus","tts-corpus"]:
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
                if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
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
                # label = LANG_CODES.get(val)
                label =  self.mdmsconfigs.get(val)["label"]
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
        """
        Druid updates and "isDelete"
        isDelete field on Druid indicates whether a filed is deleted or not.
        record A: isDelete = False ==> record is inserted;present
        record A: isDelete = True ==> record is deleted
        record A: isDelete = False & isDelete = True ==> record is updated
        actual count of record A : count(isDelete(False)) - count(isDelete(True))

        """
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

    def get_master_data(self,masterNames=None):
        """
        MDMS API call
        """

        global masterDataVals
        if not masterDataVals:
            try:
                if not masterNames:
                    log.info("Getting mdms configs from MDMS service")
                    masterNames= ["datasetTypes","languages","collectionMethods","domains"]
                headers =   {"Content-Type": "application/json"}
                body    =   {"masterNames": masterNames}
                log.info("Intiating request to fetch masters; on url : %s"%mdms_bulk_fetch_url)
                response = requests.post(url=mdms_bulk_fetch_url, headers = headers, json = body)
                content = response.content
                response_data = json.loads(content)
                if "data" not in response_data:
                    return
                log.info("Successfully retrieved masters from MDMS")
                response_data   =   response_data["data"]
                domains         =   [x for x in response_data["domains"] if x["active"]==True ] 
                domain_codes    =   [x["code"] for x in domains] 
                mdmsdomain      =   dict(zip(domain_codes,domains))
                langs           =   [x for x in response_data["languages"] if x["active"]==True]
                lang_codes      =   [x["code"] for x in langs] 
                mdmslang        =   dict(zip(lang_codes,langs)) 
                collmethods     =   [x for x in response_data["collectionMethods"] if x["active"]==True]
                collmethod_codes=   [x["code"] for x in collmethods] 
                mdmscolmethods  =   dict(zip(collmethod_codes,collmethods)) 
                mdmsdomain.update(mdmslang)   # extending the dict
                mdmsdomain.update(mdmscolmethods) # extending the dict
                masterDataVals = mdmsdomain
                return masterDataVals
            except Exception as e:
                log.exception("Exception while fetching masters from MDMS: " +str(e))
                return None
        else:
            log.info("Returning mdms configs from local")
            return masterDataVals

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