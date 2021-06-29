from flask_restful import Resource
from flask import request,jsonify
import logging
from events.error import ErrorEvent

log = logging.getLogger('file')


# rest request for block merging individual service
class FetchErrorReport(Resource):

    def post(self):
        service = ErrorEvent()
        req_criteria = request.get_json()
        log.info(f'Request received for error report SRN-{req_criteria["serviceRequestNumber"]}')
        result = service.search_error_report(req_criteria["serviceRequestNumber"], False)
        return jsonify(result)

class Health(Resource):
    def get(self):
        response = {"code": "200", "status": "ACTIVE"}
        return jsonify(response)