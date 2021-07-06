from os import tcgetpgrp
from src.db import get_data_store
from sqlalchemy import text
from config import DRUID_DB_SCHEMA ,LANG_CODES
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class AggregateDatasetModel(object):
    def __init__(self):
        pass


    def query_runner(self,query):
        try:
            collection      =   get_data_store()
            log.info("Query executed : {}".format(query))
            result          =   collection.execute(text(query)).fetchall()
            result_parsed   =   ([{**row} for row in result])
            log.info("Query Result : {}".format(result_parsed))
            return result_parsed
        except Exception as e:
            log.exception("Exception on query execution : {}".format(str(e)))
            return []


    def data_aggregator(self, request_object):
        try:
            #query fields
            count   =   "count"
            total   =   "total"
            src     =   "sourceLanguage"
            tgt     =   "targetLanguage"
            delete  =   "isDelete"
            datatype=   "datasetType"  


            dtype = request_object["type"]
            # if dtype == "asr-corpus":
            #     count = "durationInSeconds"
            match_params = None
            if "criterions" in request_object:
                match_params = request_object["criterions"]
            grpby_params = None
            if "groupby" in request_object:
                grpby_params = request_object["groupby"]

            #aggregate query for language pairs
            if grpby_params == None and len(match_params) ==1:
                query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete} FROM \"{DRUID_DB_SCHEMA}\"'
                params = match_params[0]
                value = params["value"]

                if dtype == "parallel-corpus":
                    sub_query = f'WHERE (({datatype} = \'{dtype}\') AND ({src} != {tgt}) AND ({src} = \'{value}\' OR {tgt} = \'{value}\')) \
                                    GROUP BY {src}, {tgt},{delete}'

                elif dtype in ["asr-corpus","ocr-corpus","monolingual-corpus"]:
                    sub_query = f'AND ({src} != {tgt}) GROUP BY {src}, {tgt},{delete}'
                qry_for_lang_pair  = query+sub_query

                result_parsed = self.query_runner(qry_for_lang_pair)
                chart_data =  self.result_formater_for_lang_pairs(result_parsed,dtype)
                return chart_data
            #aggregate query for groupby field
            if grpby_params != None and len(match_params) ==2:
                params = grpby_params[0]
                grp_field  = params["field"]
                src_val = next((item["value"] for item in match_params if item["field"] == "sourceLanguage"), False)
                tgt_val = next((item["value"] for item in match_params if item["field"] == "targetLanguage"), False)
                query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
                            WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
                            GROUP BY {src}, {tgt}, {delete}, {grp_field}'

                result_parsed = self.query_runner(query)
                chart_data = self.result_formater(result_parsed,grp_field)
                return chart_data
            #aggregate query for groupby,matching
            if grpby_params != None and len(match_params) ==3:
                params = grpby_params[0]
                grp_field  = params["field"]
                if grp_field == "collectionMethod_collectionDescriptions":
                    sub_field = "domains"
                elif grp_field == "domains":
                    sub_field = "collectionMethod_collectionDescriptions"
                src_val = next((item["value"] for item in match_params if item["field"] == "sourceLanguage"), None)
                tgt_val = next((item["value"] for item in match_params if item["field"] == "targetLanguage"), None)
                sub_val = next((item["value"] for item in match_params  if item["field"] == None), None)
                query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
                            WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
                            AND ({sub_field} = \'{sub_val}\') GROUP BY {src}, {tgt}, {delete}, {grp_field}'
                
                result_parsed = self.query_runner(query)
                chart_data = self.result_formater(result_parsed,grp_field)
                return chart_data
        except Exception as e:
            log.exception("Exception on query aggregation : {}".format(str(e)))
            return []

    
    def result_formater(self,result_parsed,group_by_field):
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
                aggs_parsed[val] = (agg[False]-agg[True])
            log.info("Query Result : {}".format(aggs_parsed))
            chart_data =[]
            for val in aggs_parsed:
                elem={}
                title=val.split('-')
                if not val:
                    title="Unlabeled"
                elem["_id"]=val
                elem["label"]=" ".join(title).title()
                elem["value"]=aggs_parsed.get(val)
                chart_data.append(elem)                 
            return chart_data
        except Exception as e:
            log.exception("Exception on result parsing : {}".format(str(e)))
            return []
    
    def result_formater_for_lang_pairs(self,result_parsed,dtype):
        try:
            aggs ={}
            for item in result_parsed:  
                if item["targetLanguage"] == "en":
                    check = "sourceLanguage" 
                if item["sourceLanguage"] == "en" :
                    check = "targetLanguage"
                if dtype in ["asr-corpus","ocr-corpus","monolingual-corpus"]:
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
                aggs_parsed[val] = (agg[False]-agg[True])
            log.info("Query Result : {}".format(aggs_parsed))
            chart_data =[]
            for val in aggs_parsed:
                elem={}
                label = LANG_CODES.get(val)
                if label == None:
                    label = val
                elem["_id"]=val
                elem["label"]=label
                elem["value"]=aggs_parsed.get(val)
                chart_data.append(elem)     
            return chart_data
        except Exception as e:
            log.exception("Exception on result parsing for lang pairs : {}".format(str(e)))
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