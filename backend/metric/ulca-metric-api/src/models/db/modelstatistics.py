import logging
from logging.config import dictConfig
log = logging.getLogger('file')
from src.db import ModelRepo

repo    =   ModelRepo()
class AggregateModelData(object):
    def __init__(self):
        pass
# "data": [{"_id": "as", "label": "Assamese", "value": 2460}, {"_id": "bn", "label": "Bengali", "value": 62495}, {"_id": "gu", "label": "Gujarati", "value": 1216904}, {"_id": "hi", "label": "H

    def data_aggregator(self, request_object):
        try:
            count   =   repo.count({})
            # dtype = request_object["type"]
            match_params = None
            if "criterions" in request_object:
                match_params = request_object["criterions"]
            grpby_params = None
            if "groupby" in request_object:
                grpby_params = request_object["groupby"]

            if (match_params ==  None and grpby_params == None):
                query   =   [{ "$group": {"_id": {"model":"$task.type"},"count": { "$sum": 1 }}}]
                log.info(f"Query : {query}")
                result = repo.aggregate(query)
                chart_data = []
                for record in result:
                    rec = {}
                    rec["_id"]      =   record["_id"]["model"]
                    if len(record["_id"]["model"])>9:
                        rec["label"]    =   str(record["_id"]["model"]).title()
                    else:
                        rec["label"]    =   record["_id"]["model"]
                    rec["value"]    =   record["count"]
                    chart_data.append(rec)
                return chart_data,count


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
                        rec["_id"]      =   record["_id"]["lang1"]+"-"+record["_id"]["lang2"]
                        rec["label"]    =   str(record["_id"]["lang1"]).title()+"-"+str(record["_id"]["lang2"]).title()
                    else:
                        rec["_id"]      =   record["_id"]["lang1"]
                        rec["label"]    =   str(record["_id"]["lang1"]).title()
                    rec["value"]    =   record["count"]
                    chart_data.append(rec)
                return chart_data,count

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
            log.info(f"Exception on AggregateodelData :{e}")
            return []