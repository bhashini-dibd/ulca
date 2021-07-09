
import enum

class APIStatus(enum.Enum):

    SUCCESS                         = {"message" : "Request successful"}
    ERR_GLOBAL_MISSING_PARAMETERS   = {"message" : "Data Missing"}

