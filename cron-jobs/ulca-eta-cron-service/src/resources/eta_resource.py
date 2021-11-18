from flask_restful import Resource
from flask import request
from models.response import CustomResponse
from models.status import Status
import logging
from services import ETACalculatorService
log = logging.getLogger('file')

service = ETACalculatorService()
# rest request for ETA calculation
class ETACalculatorResource(Resource):

    # reading json request and reurnung final response
    def post(self):
        log.info("Request received for ETA calculation")
        body    =   request.get_json()
        query   =   body["query"] if body.get("query") else None
        
        try:
            service.calculate_average_eta(query)
            res = CustomResponse(Status.SUCCESS.value,None,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on NotifierResource {e}')