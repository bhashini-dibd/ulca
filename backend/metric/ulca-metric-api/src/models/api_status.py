
import enum

class APIStatus(enum.Enum):

    SUCCESS                         = {'ok': True,'http':{'status': 200},'why': "request successful"}
    ERR_GLOBAL_SYSTEM               = {'ok': False,'http':{'status': 500}, 'why': "Internal Server Error"}
    ERR_GLOBAL_MISSING_PARAMETERS   = {'ok': False,'http':{'status': 400}, 'why': "Data Missing"}
    FAILURE                         = {'ok': False,'http':{'status':500},'why':'request failed'}
    CORRUPT_FILE                    = {'ok': False,'http':{'status':500},'why':'uploaded file is corrupt'}
    DATA_NOT_FOUND                  = {'ok': False,'http':{'status':404},'why':'data not found'}

