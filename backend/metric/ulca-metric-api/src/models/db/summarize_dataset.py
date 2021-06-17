from typing import Collection, Counter
from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from src.utilities.pymongo_data_handling import normalize_bson_to_json
import pandas as pd
from src.db import get_data_store
import pymongo
from sqlalchemy import text
from config import DRUID_DB_SCHEMA ,LANG_CODES
import logging

log = logging.getLogger('file')
DB_SCHEMA_NAME  = 'summary_dataset_v2'
# tag_mapping = {'languagePairs':1, 'collectionSource':3, 'domain':4, 'collectionMethod':5}

language_extension = {'pu':'Punjabi', 
                      'be':'Bengali',
                      'en':'English',
                      'ta':'Tamil', 
                      'ml':'Malayalam', 
                      'te':'Telugu', 
                      'ka':'Kannada', 
                      'hi':'Hindi', 
                      'ma':'Marathi',
                      'gu':'Gujarati',
                      'od':'Odia',
                      'bh':'Bhojpuri',
                      'as':'Assamese'}

def get_key(val, lang_dict):
    for key, value in lang_dict.items():
         if val == value:
             return key

class SummarizeDatasetModel(object):
    def __init__(self):
        pass

    def store(self, dataset):
        try:
            collections = get_db()[DB_SCHEMA_NAME]
            result     = collections.insert_one(dataset)
            if result != None and result.acknowledged == True:
                return True
        except Exception as e:
            log.exception("db connection exception :{}".format(e))
            return False

    # def generate_grouping_query(self, group_param):
    #     group_query = {}
    #     tag_index = tag_mapping[group_param['value']]
    #     group_query['$group'] = {"_id": {"$arrayElemAt": ["$tags", tag_index]}, "num_parallel_sentences": {"$sum": "$count"}}
    #     return group_query
    
    def generate_match_query(self, criterions):
        match_query = {}
        match_params = []
        for criteria in criterions:
            if 'value' not in criteria.keys():
                src_lang = get_key(criteria['sourceLanguage']['value'], language_extension)
                tar_lang = get_key(criteria['targetLanguage']['value'], language_extension)
                match_param = src_lang + '-' + tar_lang
            else:
                match_param = criteria['value']
            match_params.append({"$in": [match_param, "$tags"]})
        
        match_query['$match'] = {"$expr": {"$and": match_params}}
        return match_query

    def search(self, dataset):
        
        try:
            collection      = get_data_store()
            criterions      = dataset["criterions"]
            grouping        = dataset["groupby"]

            if len(criterions) == 0:
                dtype = dataset["type"]
                if dtype == "parallel-corpus":
                    query = "SELECT SUM(\"count\") as total, sourceLanguage, targetLanguage,isDelete FROM \"{}\" \
                        WHERE ((datasetType = \'{}\') AND (sourceLanguage != targetLanguage) AND (sourceLanguage = \'en\' \
                        OR targetLanguage = \'en\')) GROUP BY sourceLanguage, targetLanguage,isDelete".format(DRUID_DB_SCHEMA,dtype)
                    
                if dtype == "asr-corpus":
                    query= "SELECT SUM(\"count\") as total, sourceLanguage, targetLanguage,isDelete FROM \"{}\" \
                        WHERE ((datasetType = \'{}\') AND (sourceLanguage != targetLanguage) AND (targetLanguage = '') AND (sourceLanguage != ''))\
                        GROUP BY sourceLanguage, targetLanguage,isDelete".format(DRUID_DB_SCHEMA,dtype)
                
                log.info("Query executed : {}".format(query))

                result          = collection.execute(text(query)).fetchall()
                result_parsed   =([{**row} for row in result])
                log.info("Query Result : {}".format(result_parsed))
                aggs ={}
                for item in result_parsed:  
                    if item["targetLanguage"] == "en":
                        check = "sourceLanguage" 
                    if item["sourceLanguage"] == "en" :
                        check = "targetLanguage"
                    if dtype == "asr-corpus":
                        check = "sourceLanguage"

                    if aggs.get(item[check]) == None:
                        fields={}
                        fields["count"] = item["total"]
                        if item["isDelete"] == "false":
                            fields[False] = item["total"]
                            fields[True]=0
                        else:
                            fields[True]=item["total"]
                            fields[False]=0

                        aggs[item[check]] = fields
                    else:
                        aggs[item[check]]["count"] += item["total"]
                        if item["isDelete"] == "false":
                            aggs[item[check]][False] += item["total"]
                        else:
                            aggs[item[check]][True] += item["total"]
                # print(aggs)
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
           
            if len(criterions) == 1 :
                dtype = dataset["type"]
                src = criterions[0]["sourceLanguage"]["value"]
                tgt = criterions[0]["targetLanguage"]["value"]
                grp_val = grouping["value"]
                query = "SELECT SUM(\"count\") as total, sourceLanguage , targetLanguage ,{group}, isDelete  FROM \"{schema}\" \
                        WHERE ((datasetType = \'{type}\') AND ((sourceLanguage =  \'{srcl}\' AND targetLanguage =  \'{tgtl}\') OR (sourceLanguage =  \'{tgtl}\' AND targetLanguage =  \'{srcl}\')))\
                             GROUP BY sourceLanguage, targetLanguage, {group},isDelete".format(schema=DRUID_DB_SCHEMA,group=grp_val,type=dtype,srcl=src,tgtl=tgt)

                log.info("Query executed : {}".format(query))
                result          = collection.execute(text(query)).fetchall()
                result_parsed   =([{**row} for row in result])
                # log.info("Query Result : {}".format(result_parsed))
                aggs={}
                for item in result_parsed:
                    if aggs.get(item[grp_val]) == None:
                        fields={}
                        fields["count"] = item["total"]
                        if item["isDelete"] == "false":
                            fields[False] = item["total"]
                            fields[True]=0
                        else:
                            fields[True]=item["total"]
                            fields[False]=0

                        aggs[item[grp_val]] = fields
                    else:
                        aggs[item[grp_val]]["count"] += item["total"]
                        if item["isDelete"] == "false":
                            aggs[item[grp_val]][False] += item["total"]
                        else:
                            aggs[item[grp_val]][True] += item["total"]
  
                aggs_parsed ={}
                for val in aggs:
                    agg = aggs[val]
                    aggs_parsed[val] = (agg[False]-agg[True])
                log.info("Query Result : {}".format(aggs_parsed))

                chart_data =[]
                for val in aggs_parsed:
                    elem={}
                    title=val.split('-')
                    elem["_id"]=val
                    elem["label"]=" ".join(title).title()
                    elem["value"]=aggs_parsed.get(val)
                    chart_data.append(elem)
                    
                return chart_data       
            
            if len(criterions) == 2 :
                if grouping["value"]=="source":
                    add_field ="domains"
                if grouping["value"]=="domains":
                    add_field ="collectionMethod_collectionDescriptions"
                if grouping["value"]=="collectionMethod_collectionDescriptions":
                    add_field="domains"

                dtype = dataset["type"]
                src = criterions[0]["sourceLanguage"]["value"]
                tgt = criterions[0]["targetLanguage"]["value"]
                sub_q= criterions[1]["value"]
                grp_val = grouping["value"]
                query = "SELECT SUM(\"count\") as total, sourceLanguage, targetLanguage,{group},isDelete FROM \"{schema}\" \
                        WHERE ((datasetType = \'{type}\') AND ({match} = \'{val}\') AND ((sourceLanguage =  \'{srcl}\' AND targetLanguage =  \'{tgtl}\') OR (sourceLanguage =  \'{tgtl}\' AND targetLanguage =  \'{srcl}\')))\
                             GROUP BY sourceLanguage, targetLanguage, {group},isDelete".format(schema=DRUID_DB_SCHEMA,group=grp_val,val=sub_q,srcl=src,tgtl=tgt,match=add_field,type=dtype)

                log.info("Query executed : {}".format(query))
                result          = collection.execute(text(query)).fetchall()
                result_parsed   =([{**row} for row in result])
                # log.info("Query Result : {}".format(result_parsed))

                aggs={}
                for item in result_parsed:
                    if aggs.get(item[grp_val]) == None:
                        fields={}
                        fields["count"] = item["total"]
                        if item["isDelete"] == "false":
                            fields[False] = item["total"]
                            fields[True]=0
                        else:
                            fields[True]=item["total"]
                            fields[False]=0

                        aggs[item[grp_val]] = fields
                    else:
                        aggs[item[grp_val]]["count"] += item["total"]
                        if item["isDelete"] == "false":
                            aggs[item[grp_val]][False] += item["total"]
                        else:
                            aggs[item[grp_val]][True] += item["total"]
 
                aggs_parsed ={}
                for val in aggs:
                    agg = aggs[val]
                    aggs_parsed[val] = (agg[False]-agg[True])
                log.info("Query Result : {}".format(aggs_parsed))
                chart_data =[]
                for val in aggs_parsed:
                    elem={}
                    title=val.split('-')
                    elem["_id"]=val
                    elem["label"]=" ".join(title).title()
                    elem["value"]=aggs_parsed.get(val)
                    chart_data.append(elem)                 
                return chart_data        
            collection.close()
        except Exception as e:
            log.exception("db connection exception : {}".format(str(e)))
            return []
