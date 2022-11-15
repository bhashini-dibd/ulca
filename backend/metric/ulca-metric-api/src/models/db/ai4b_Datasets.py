from .queryutils import QueryUtils
from config import DRUID_DB_SCHEMA , TIME_CONVERSION_VAL
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

utils = QueryUtils()



class AggregateAI4BDatasetModel(object):
    """
    Processing dataset aggregation
    #from druid
    """
    def __init__(self):
        pass

    def ai4b_data_aggregator(self, request_object):
        try:
            #query fields ; druid filed names
            count   =   "count"
            total   =   "total"
            src     =   "sourceLanguage"
            tgt     =   "targetLanguage"
            delete  =   "isDelete"
            datatype=   "datasetType"  
            duration=   "durationInSeconds"
            t_dtype = "transliteration-corpus"
            sub_name = "primarySubmitterName"
            aib = ["AI4Bharat","Samanantar"]
            

            dtype = request_object["type"]
            emp_list = []
            aibdict = {}
            for some in aib:
                aibquery = f'SELECT SUM(\"{count}\") as {total}, {src},{tgt},{delete} FROM \"{DRUID_DB_SCHEMA}\" WHERE (({datatype} = \'{dtype}\') AND ({sub_name} = \'{some}\') AND ({src != tgt})) GROUP BY {src}, {tgt}, {delete}'
                que_res = utils.query_runner(aibquery)
                
                log.info(f'que_res at line number 48 {que_res}')
                for que_in,que in enumerate(que_res):
                    if que_in +1 == len(que_res):
                        break
                    elif que_res[que_in]["sourceLanguage"] == que_res[que_in + 1]["sourceLanguage"] and que_res[que_in]["targetLanguage"] == que_res[que_in + 1]["targetLanguage"]:
                        aibdict["total_count"] = que_res[que_in]["total"] - que_res[que_in + 1]["total"]
                        if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
                            aibdict["total_count"] = aibdict["total_count"] / TIME_CONVERSION_VAL
                        aibdict["sourceLanguage"] = que_res[que_in]["sourceLanguage"]
                        aibdict["targetLanguage"] = que_res[que_in]["targetLanguage"]
                        aibdict["datasetType"] = dtype
                        emp_list.append(aibdict.copy())
            log.info(f'result of ai4bharat datasets at 61 {emp_list}')
            return emp_list
        except Exception as e:
            log.info("Exception on query aggregation : {}".format(str(e)))

# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})