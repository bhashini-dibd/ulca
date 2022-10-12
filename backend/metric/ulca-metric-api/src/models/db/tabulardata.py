from .queryutils import QueryUtils
import pandas as pd
import numpy as np
from config import DRUID_DB_SCHEMA , TIME_CONVERSION_VAL
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

utils = QueryUtils()

class AggregateTabularDataModel(object):
    """
    Processing dataset aggregation
    #from druid
    """
    def __init__(self):
        pass

    def data_aggregator(self):
        try:
            #query fields ; druid filed names
            count            =   "count"
            total            =   "total"
            src              =   "sourceLanguage"
            tgt              =   "targetLanguage"
            delete           =   "isDelete"
            datatype         =   "datasetType"  
            duration         =   "durationInSeconds"
            domain           =   "domains"
            collection_method=   "collectionMethod_collectionDescriptions"
            submitter        =   "primarySubmitterName"

            # Aggregate data for different dataset types and merge into single report
            dtype_ocr = "ocr-corpus"
            dtype_parallel = "parallel-corpus"
            dtype_asr = "asr-corpus"
            dtype_tts = "tts-corpus"
            query = f'SELECT SUM(\"{count}\") as {total},{datatype}, {src}, {tgt},{delete}, array_to_string({domain}, \',\') as {domain}, array_to_string({collection_method}, \',\') as {collection_method}, array_to_string({submitter}, \',\') as {submitter} FROM \"{DRUID_DB_SCHEMA}\"'
            sub_query = f'WHERE ({datatype} = \'{dtype_parallel}\') GROUP BY {src}, {tgt},{delete}, array_to_string({domain}, \',\'), array_to_string({collection_method}, \',\'), array_to_string({submitter}, \',\'), {datatype} HAVING {total} > 1000'
            qry  = query+sub_query
            result_parsed = utils.query_runner(qry)

            query = f'SELECT SUM(\"{count}\") as {total},{datatype}, {src}, {tgt},{delete}, array_to_string({domain}, \',\') as {domain}, array_to_string({collection_method}, \',\') as {collection_method}, array_to_string({submitter}, \',\') as {submitter} FROM \"{DRUID_DB_SCHEMA}\"'
            sub_query = f'WHERE ({datatype} = \'{dtype_ocr}\') GROUP BY {src}, {tgt},{delete}, array_to_string({domain}, \',\'), array_to_string({collection_method}, \',\'), array_to_string({submitter}, \',\'), {datatype}'
            qry  = query+sub_query
            result_parsed_ocr = utils.query_runner(qry)

            result_parsed = result_parsed + result_parsed_ocr

            query = f'SELECT SUM(\"{count}\" * \"{duration}\") as {total},{datatype}, {src}, {tgt},{delete}, array_to_string({domain}, \',\') as {domain}, array_to_string({collection_method}, \',\') as {collection_method}, array_to_string({submitter}, \',\') as {submitter} FROM \"{DRUID_DB_SCHEMA}\"'
            sub_query = f'WHERE (({datatype} = \'{dtype_asr}\') OR ({datatype} = \'{dtype_tts}\')) GROUP BY {src}, {tgt},{delete}, array_to_string({domain}, \',\'), array_to_string({collection_method}, \',\'), array_to_string({submitter}, \',\'), {datatype}'
            qry  = query+sub_query
            result_parsed_duration = utils.query_runner(qry)
            for elem in result_parsed_duration:
                elem[total] = elem[total] / 3600
                elem[total] = float(np.round(elem[total], 3))

            result_parsed = result_parsed + result_parsed_duration
            result_parsed = [rmz for rmz in result_parsed if rmz['total'] != 0]
            log.info("Data queried from Druid: {} rows".format(len(result_parsed)))
            df = pd.DataFrame(result_parsed)
            df.loc[df[delete]=='true', total] = 0-df[total]
            grouped_df = df.groupby([src, tgt, collection_method, domain, submitter, datatype])[total].sum()
            df1 = grouped_df.to_frame().reset_index()
            df1.sort_values(by=[datatype], inplace=True)
            data_tabular = df1.to_dict('records')
            for elem in data_tabular:
                val = elem[collection_method]
                if not val:
                    elem[collection_method] = 'unspecified'
                val = elem[tgt]
                if not val:
                    elem[tgt] = None
            log.info("Data counts formatted: {} rows".format(len(data_tabular)))
            return data_tabular

        except Exception as e:
            log.exception("Exception on query aggregation : {}".format(str(e)))
            return []

    

   


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