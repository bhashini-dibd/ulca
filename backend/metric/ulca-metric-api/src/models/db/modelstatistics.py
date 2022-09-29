import logging
from logging.config import dictConfig
log = logging.getLogger('file')
from src.db import ModelRepo
from .queryutils import QueryUtils

repo    =   ModelRepo()
utils   =   QueryUtils()
class AggregateModelData(object):
    """
    Processing model aggregation
    #from mongo
    """
    def __init__(self):
        self.mdmsconfigs  = utils.get_master_data()

    def data_aggregator(self, request_object):
        try:
            count   =   repo.count({"task.type":{"$ne":None}})  # counting models where the task type is defined
            match_params = None
            if "criterions" in request_object:
                match_params = request_object["criterions"] # where conditions
            grpby_params = None
            if "groupby" in request_object:
                grpby_params = request_object["groupby"]  #grouping fields

            #aggregating the model types; initial chart
            if (match_params ==  None and grpby_params == None):
                query   =   [{ "$group": {"_id": {"model":"$task.type"},"count": { "$sum": 1 }}}]
                log.info(f"Query : {query}")
                result = repo.aggregate(query)
                chart_data = []
                for record in result:
                    rec = {}
                    rec["_id"]      =   record["_id"]["model"]
                    if record["_id"]["model"] == None:
                        continue; #ingnoring the models whose task types are not defined
                    elif record["_id"]["model"] and len(record["_id"]["model"])>9:
                        rec["label"]    =   str(record["_id"]["model"]).title()
                    else:
                        rec["label"]    =   record["_id"]["model"]
                    rec["value"]    =   record["count"]
                    chart_data.append(rec)
                return chart_data,count

            #1st levl drill down on model selected and languages  
            if grpby_params[0]["field"] == "language":
                query   =   [ { '$match':{ "task.type" : match_params[0]["value"] }}, { '$unwind': "$languages" },
                            { "$group": {"_id": {"lang1":"$languages.sourceLanguage","lang2":"$languages.targetLanguage"},
                            "count": { "$sum": 1 }}}]
                log.info(f"Query : {query}")
                result = repo.aggregate(query)
                chart_data = []
                for record in result:
                    rec = {}
                    if match_params[0]["value"] == "TRANSLATION":
                        rec["_id"]      =   record["_id"]["lang1"]+"-"+record["_id"]["lang2"]  # label :language pairs seperated by '-'
                        try:
                            rec["label"]    =   self.mdmsconfigs.get(str(record["_id"]["lang1"]).lower())["label"]+"-"+self.mdmsconfigs.get(str(record["_id"]["lang2"]).lower())["label"]
                        except:
                            log.info(f'Language code not found on MDMS : {record["_id"]["lang1"], record["_id"]["lang2"]}')
                            rec["label"]    =   str(record["_id"]["lang1"]).title()+"-"+str(record["_id"]["lang2"]).title()
                    
                    elif match_params[0]["value"] == "TRANSLITERATION":
                        rec["_id"]    =    record["_id"]["lang2"]
                        try:
                            rec["label"]  =   self.mdmsconfigs.get(str(record["_id"]["lang2"]).lower())["label"]
                        except:
                            log.info(f'Language code not found on MDMS : {record["_id"]["lang1"]}')
                            rec["label"]    =   str(record["_id"]["lang1"]).title()

                    else:
                        rec["_id"]      =   record["_id"]["lang1"] # label :language 
                        try:
                            rec["label"]    =   self.mdmsconfigs.get(str(record["_id"]["lang1"]).lower())["label"]
                        except:
                            log.info(f'Language code not found on MDMS : {record["_id"]["lang1"]}')
                            rec["label"]    =   str(record["_id"]["lang1"]).title()
                    rec["value"]    =   record["count"]
                    chart_data.append(rec)
                return chart_data,count

            #1st levl drill down on model selected and submitters
            if grpby_params[0]["field"] == "submitter":
                query   =   [ { '$match':{ "task.type" : match_params[0]["value"] }},
                            { "$group": {"_id": {"submitter":"$submitter.name"},
                            "count": { "$sum": 1 }}}]
                log.info(f"Query : {query}")
                result = repo.aggregate(query)
                chart_data = []
                for record in result:
                    rec = {}
                    rec["_id"]      =   record["_id"]["submitter"]
                    rec["label"]    =   record["_id"]["submitter"]
                    rec["value"]    =   record["count"]
                    chart_data.append(rec)
                return chart_data,count
                
        except Exception as e:
            log.info(f"Exception on AggregateModelData :{e}")
            return []