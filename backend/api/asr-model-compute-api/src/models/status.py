import enum


class Status(enum.Enum):
    SUCCESS      =   {"message": "Request successful"}
    FAILED       =   {"message": "Request failed"}
