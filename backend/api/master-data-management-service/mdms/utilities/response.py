import uuid
import time
import enum

def post_error(status_code, message,error_type=None):
        message = {
                    'code':status_code,
                    'message':message,
                    'errorID' :str(uuid.uuid4()),
                    'timeStamp':eval(str(time.time()).replace('.', '')[0:13])
                    }
        return message


class CustomResponse:

    def __init__(self, statuscode, data, count=0):
        self.statuscode             =   statuscode
        self.statuscode['data']     =   data
        self.statuscode['count']    =   count

    def getresjson(self):
        return self.statuscode

class Status(enum.Enum):

    SUCCESS                 =   {"message" : "Request successful"}
    FAILURE                 =   {"message" : "Request failed"}