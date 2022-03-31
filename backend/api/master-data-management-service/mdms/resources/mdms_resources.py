from flask_restful import Resource
from flask import request
import logging
from utilities import post_error,CustomResponse,Status
from services import MasterDataServices
import json

log = logging.getLogger('file')

mdserve = MasterDataServices()

#jsonn = '/home/siddanth/Desktop/feed.json'
jsonn ="""
{
  "sts": {
    "initialQuestion": {
      "question": "Are you satisfied with this translation?",
      "answerType": "5-star-rating",
      "editbox": "false"
    },
    "detailedQuestions": [
      {
        "taskType": "asr",
        "question": "Rate Speech to Text Quality",
        "answerType": "5-star-rating",
        "editbox": "true"
      },
      {
        "taskType": "translation",
        "question": "Rate Translated Text Quality",
        "answerType": "5-star-rating",
        "editbox": "true"
      },
      {
        "taskType": "tts",
        "question": "Rate Translated Speech Quality",
        "answerType": "5-star-rating",
        "editbox": "false"
      },
      {
        "question": "Add your comments",
        "answerType": "freetext"
      }
    ]
  }
 }
"""

class MasterDataResource(Resource):

    # reading json request and returning final response for master data search
    def post(self):
        body = request.get_json()
        log.info(f"Request for master data received")
        if body.get("masterName") == None :
            log.error("Data Missing-masterName and locale are mandatory")
            return post_error("Request Failed","Data Missing-masterName is mandatory"), 400
        master       =  body["masterName"]
        jsonpath     =  None
        if "jsonPath" in body:
            jsonpath =   body["jsonPath"]  
        try:
            result = mdserve.get_from_remote_source([master],jsonpath)
            if not result:
                return post_error("Not found", "masterName is not valid") , 400
            log.info(f"Request to mdms fetch succesfull ")
            return CustomResponse(Status.SUCCESS.value,result).getresjson(), 200
            
        except Exception as e:
            log.error(f"Request to mdms failed due to  {e}")
            return post_error("Service Exception on MasterDataResource",f"Exception occurred:{e}"), 400

class FeedbackDataResource(Resource):

    # reading json request and returning final response for master data search
    def post(self):
        body = request.get_json()
        log.info(f"Request for feedback data received")
        if body.get("feedbackType") == None :
            log.error("Data Missing-masterName and locale are mandatory")
            return post_error("Request Failed","Data Missing-masterName is mandatory"), 400
        master       =  body["feedback"]
        feedbackType = body["feedbackType"]
        try:
            #result = mdserve.get_feedbackQns_from_git(master)
            result = json.loads(jsonn)
            if feedbackType in result.keys():
                final = result.get(feedbackType)
            if not result:
                return post_error("Not found", "masterName is not valid") , 400
            log.info(f"Request to mdms fetch succesfull ")
            return CustomResponse(Status.SUCCESS.value,final).getresjson(), 200
            
        except Exception as e:
            log.error(f"Request to mdms failed due to  {e}")
            return post_error("Service Exception on MasterDataResource",f"Exception occurred:{e}"), 400


class BulkMasterDataResource(Resource):

    # reading json request and returning final response for bulk master data search
    def post(self):
        body = request.get_json()
        log.info(f"Request for master data ,bulk search received")
        if body.get("masterNames") == None :
            log.error("Data Missing-masterName and locale are mandatory")
            return post_error("Request Failed","Data Missing-masterName is mandatory"), 400
        master_list    =  body["masterNames"] 
        try:
            result = mdserve.get_attributes_data(master_list)
            if not result:
                return post_error("Not found", "masterName is not valid") , 400
            log.info(f"Request to mdms fetch succesfull ")
            return CustomResponse(Status.SUCCESS.value,result).getresjson(), 200
        except Exception as e:
            log.error(f"Request to mdms for bulk search failed due to  {e}")
            return post_error("Service Exception on BulkMasterDataResource",f"Exception occurred:{e}"), 400

class CacheBustResource(Resource):

    # reading json request and returning final response for cache bust
    def post(self):
        body = request.get_json()
        log.info(f"Request on cache bust for master data received")
        master_list    =  body["masterNames"] 
        try:
            result = mdserve.bust_cache(master_list)
            if not result:
                log.info(f"Request to mdms cache bust succesfull ")
                return CustomResponse(Status.SUCCESS.value,None).getresjson(), 200
            return result, 400
        except Exception as e:
            log.error(f"Request to mdms for cache bust failed due to  {e}")
            return post_error("Service Exception on CacheBustResource",f"Exception occurred:{e}"), 400

