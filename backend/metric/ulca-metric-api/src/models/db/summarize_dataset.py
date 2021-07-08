from src.db import get_data_store
from sqlalchemy import text
from config import DRUID_DB_SCHEMA ,LANG_CODES
import logging

log = logging.getLogger('file')


class SummarizeDatasetModel(object):
    #data aggregation
    def search(self, dataset):
        try:
            collection      = get_data_store()
            criterions      = dataset["criterions"]
            grouping        = dataset["groupby"]
            count           = "count"
            if len(criterions) == 0:
                dtype = dataset["type"]
                if dtype == "asr-corpus":
                    count = "durationInSeconds"
                if dtype == "parallel-corpus":
                    query = "SELECT SUM(\"{}\") as total, sourceLanguage, targetLanguage,isDelete FROM \"{}\" \
                        WHERE ((datasetType = \'{}\') AND (sourceLanguage != targetLanguage) AND (sourceLanguage = \'en\' \
                        OR targetLanguage = \'en\')) GROUP BY sourceLanguage, targetLanguage,isDelete".format(count,DRUID_DB_SCHEMA,dtype)
                    
                if dtype in ["asr-corpus","ocr-corpus","monolingual-corpus"] :
                    query= "SELECT SUM(\"{}\") as total, sourceLanguage, targetLanguage,isDelete FROM \"{}\" \
                        WHERE ((datasetType = \'{}\') AND (sourceLanguage != targetLanguage) AND (targetLanguage = '') AND (sourceLanguage != ''))\
                        GROUP BY sourceLanguage, targetLanguage,isDelete".format(count,DRUID_DB_SCHEMA,dtype)
                
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
                    if dtype in ["asr-corpus","ocr-corpus","monolingual-corpus"]:
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
                query = "SELECT SUM(\"{count}\") as total, sourceLanguage , targetLanguage ,{group}, isDelete  FROM \"{schema}\" \
                        WHERE ((datasetType = \'{type}\') AND ((sourceLanguage =  \'{srcl}\' AND targetLanguage =  \'{tgtl}\') OR (sourceLanguage =  \'{tgtl}\' AND targetLanguage =  \'{srcl}\')))\
                             GROUP BY sourceLanguage, targetLanguage, {group},isDelete".format(schema=DRUID_DB_SCHEMA,group=grp_val,type=dtype,srcl=src,tgtl=tgt, count=count)

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
                    elem["_id"]=val
                    if not val:
                        elem["label"]="Unlabeled"
                    else:
                        title=val.split('-')
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
                query = "SELECT SUM(\"{count}\") as total, sourceLanguage, targetLanguage,{group},isDelete FROM \"{schema}\" \
                        WHERE ((datasetType = \'{type}\') AND ({match} = \'{val}\') AND ((sourceLanguage =  \'{srcl}\' AND targetLanguage =  \'{tgtl}\') OR (sourceLanguage =  \'{tgtl}\' AND targetLanguage =  \'{srcl}\')))\
                             GROUP BY sourceLanguage, targetLanguage, {group},isDelete".format(schema=DRUID_DB_SCHEMA,group=grp_val,val=sub_q,srcl=src,tgtl=tgt,match=add_field,type=dtype,count=count)

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
                    elem["_id"]=val
                    if not val:
                        elem["label"]="Unlabeled"
                    else:
                        title=val.split('-')
                        elem["label"]=" ".join(title).title()
                    elem["value"]=aggs_parsed.get(val)
                    chart_data.append(elem)                 
                return chart_data        
            collection.close()
        except Exception as e:
            log.exception("db connection exception : {}".format(str(e)))
            return []
