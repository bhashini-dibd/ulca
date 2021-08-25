import enum


class Status(enum.Enum):
    SUCCESS = {'ok': True, 'http': {'status': 200},
               'why': "request successful"}
