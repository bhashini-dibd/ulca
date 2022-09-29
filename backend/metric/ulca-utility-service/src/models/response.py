
from flask import jsonify
import enum

class CustomResponse :
    def __init__(self, statuscode, data, count=0):
        self.statuscode = statuscode
        self.statuscode['data'] = data
        self.statuscode['count'] = count
    
    def getres(self):
        return jsonify(self.statuscode)


class Status(enum.Enum):
    SUCCESS = {'ok': True, 'http': {'status': 200},
               'why': "request successful"}
