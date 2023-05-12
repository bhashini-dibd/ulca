from flask_restful import Resource
from flask import request
import logging
from utilities import post_error,CustomResponse,Status
from services import MasterDataServices
import config
from utilities import post_error, MdUtils


log = logging.getLogger('file')
utils       =   MdUtils()
mdserve = MasterDataServices()

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


class PipeLineFeedBack(Resource):
    def post(self):
        body = request.get_json() 
        log.info(f"request for feedback received.")
        add_pipeFeed = {}
        add_tasktype = []
        if not body['feedbackLanguage'] or not body['supportedTasks']:
            return post_error("something wrong with the feedbackLanguage and supportedTasks ",f"Exception occurred:{e}"), 400
        try:
            git_file_location   =   f"{config.git_folder_prefix}/{config.masPipe}.json"
            get_pipe = utils.read_from_git(git_file_location) #dict
            if get_pipe and isinstance(get_pipe,dict) and 'taskFeedback' in get_pipe.keys():
                for task in get_pipe['taskFeedback']:
                    if task['taskType'] in body['supportedTasks']:
                        add_tasktype.append(task)
                        add_pipeFeed['feedbackLanguage'], add_pipeFeed['pipelineFeedback'], add_pipeFeed['taskFeedback'] = body['feedbackLanguage'],get_pipe['pipelineFeedback'], add_tasktype         
                return add_pipeFeed
            else:
                return post_error("Could not get Pipeline Questions from Git",f"Exception occurred:{e}"), 400
        except Exception as e:
            log.error(f"Request to mdms for pipeline Question failed due to {e}")
            return post_error("Service Exception on Pipeline Questions",f"Exception occurred:{e}"), 400
            


        #         log.info(f"somethign {q_res['taskFeedback']}")
        #         for task in q_res['taskFeedback']:
        #             log.info(f"tsasssks feedddd {task}")
        #             if task['taskType'] in body["supportedTasks"]:
        #                 add_pipeFeed.append(task)
        #                 return add_pipeFeed


        