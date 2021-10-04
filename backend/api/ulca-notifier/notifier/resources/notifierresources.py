from flask_restful import Resource
from events import NotifierEvent
from flask import request,jsonify
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

#resource class for error report request 
class NotifierResource(Resource):

    def post(self):
        req_criteria = request.get_json()
        log.info("Inside ulca-notifier service")
        if req_criteria.get("event")==None or req_criteria.get("entityID")==None or req_criteria.get("userID")==None:
            log.info('Some of the mandatory keys(event, entityID, userID) are missing ')
            return jsonify({"message":"Some of the mandatory keys(event, entityID, userID) are missing", "data": None,"count":None}),400
        log.info(f'Request received for notifiying user on entityID-{req_criteria["entityID"]}')
        notifier    =   NotifierEvent(req_criteria["userID"])
        if req_criteria["event"] == "dataset-published":
            notifier.data_submission_notifier(req_criteria)
        if req_criteria["event"] == "benchmark-published":
            notifier.benchmark_submission_notifier(req_criteria)
        if req_criteria["event"] == "search-published":
            notifier.data_search_notifier(req_criteria)


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