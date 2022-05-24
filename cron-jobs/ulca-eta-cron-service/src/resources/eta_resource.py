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
            result  = service.calculate_average_eta([query])
            res     = CustomResponse(Status.SUCCESS.value,result,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on ETACalculatorResource {e}')
            return None

class ETAResource(Resource):

    # reading json request and reurnung final response
    def get(self):
        log.info("Request received for fetching ETA values")
        eta_type    =   None
        eta_type    =   request.args.get("type")
        try:
            result  = service.fetch_estimates(eta_type)
            res     = CustomResponse(Status.SUCCESS.value,result,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on ETAResource {e}')
            return None