from flask_restful import Resource
from flask import request,jsonify
import logging
from events.error import ErrorEvent
from logging.config import dictConfig
log = logging.getLogger('file')


#resource class for error report request 
class FetchErrorReport(Resource):

    def post(self):
        service = ErrorEvent()
        req_criteria = request.get_json()
        log.info(f'Request received for error report SRN-{req_criteria["serviceRequestNumber"]}')
        result = service.get_error_report(req_criteria["serviceRequestNumber"], False)
        return jsonify(result)

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