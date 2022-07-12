from flask_restful import Resource
from models.response import CustomResponse
from models.response import Status
import logging
from services import NotifierService

log = logging.getLogger('file')
service = NotifierService()

class NotifierResource(Resource):

    def post(self):
        log.info("Request received for notifiying the users")
        try:
            service.notify_user()
            res = CustomResponse(Status.SUCCESS.value,None,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on NotifierResource {e}')

class MismatchNotifierResource(Resource):

    def post(self):
        log.info("Request received for notifiying the users")
        try:
            service.notify_mismatch()
            res = CustomResponse(Status.SUCCESS.value,None,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on NotifierResource {e}')