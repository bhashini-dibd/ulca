from flask_restful import Resource
from events import NotifierEvent
from flask import request,jsonify
from configs.configs import ds_completed,ds_failed,bm_completed,bm_failed,search_completed, inference_check
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

#resource class for error report request 
class NotifierResource(Resource):

    def post(self):
        req_criteria = request.get_json()
        log.info("Inside ulca-notifier service")
        if req_criteria.get("event")==None :#or req_criteria.get("entityID")==None or req_criteria.get("userID")==None:
            log.info('Mandatory keys(event) is missing ')
            return {"message":"Mandatory key(event) is missing", "data": None,"count":None},400
        #log.info(f'Request received for notifiying user on entityID-{req_criteria["entityID"]}')
        if "userID" not in req_criteria.keys():
            req_criteria['userID'] = None
        notifier    =   NotifierEvent(req_criteria["userID"])
        if req_criteria["event"] in [ds_completed,ds_failed]:
            notifier.data_submission_notifier(req_criteria)
        if req_criteria["event"] in [bm_completed,bm_failed]:
            notifier.benchmark_submission_notifier(req_criteria)
        if req_criteria["event"] == search_completed:
            notifier.data_search_notifier(req_criteria)
        if req_criteria["event"] == inference_check:
            notifier.model_check_notifier(req_criteria)
        
        return {"message":"Request successful", "data": None,"count":None},200



#resource class for API health check
class Health(Resource):
    def get(self):
        response = {"code": "200", "status": "ACTIVE"}
        return jsonify(response)




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