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

            log.info("Data queried from Druid: {} rows".format(len(result_parsed)))
            #log.info("Queried data : {}".format(str(result_parsed)))
            df = pd.DataFrame(result_parsed)
            #df[collection_method] = df[collection_method].fillna('unspecified', inplace=True)
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


            # #query fields ; druid filed names
            # count   =   "count"
            # total   =   "total"
            # src     =   "sourceLanguage"
            # tgt     =   "targetLanguage"
            # delete  =   "isDelete"
            # datatype=   "datasetType"  
            # duration=   "durationInSeconds"

            # dtype = request_object["type"]
            # match_params = None 
            # if "criterions" in request_object:
            #     match_params = request_object["criterions"]  # where conditions
            # grpby_params = None
            # if "groupby" in request_object:
            #     grpby_params = request_object["groupby"]   #grouping fields

            # #ASR charts are displayed in hours; initial chart
            # if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
            #     sumtotal_query = f'SELECT SUM(\"{count}\" * \"{duration}\") as {total},{delete}  FROM \"{DRUID_DB_SCHEMA}\"  WHERE ({datatype} = \'{dtype}\') GROUP BY {delete}'
            # else:
            #     #Charts except ASR are displayed in record counts; initial chart
            #     sumtotal_query = f'SELECT SUM(\"{count}\") as {total},{delete}  FROM \"{DRUID_DB_SCHEMA}\"  WHERE ({datatype} = \'{dtype}\') GROUP BY {delete}'
            # sumtotal_result = utils.query_runner(sumtotal_query)
            # true_count = 0
            # false_count = 0
            # for val in sumtotal_result:
            #     if val[delete] == "false":
            #         true_count = val[total]
            #     else:
            #         false_count = val[total]
            # sumtotal = true_count - false_count
            # if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
            #     sumtotal = sumtotal/TIME_CONVERSION_VAL

            # #aggregate query for language pairs; 1st level drill down for the chart
            # if grpby_params == None and len(match_params) ==1:
            #     if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
            #         query = f'SELECT SUM(\"{count}\" * \"{duration}\") as {total}, {src}, {tgt},{delete} FROM \"{DRUID_DB_SCHEMA}\"'
            #     else:
            #         query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete} FROM \"{DRUID_DB_SCHEMA}\"'
            #     params = match_params[0]
            #     value = params["value"]

            #     #parallel corpus src and tgt are interchangable ; eg : one 'en-hi' record is considered as 'hi-en' as well and is also counted while checking for 'hi' pairs
            #     if dtype == "parallel-corpus":
            #         sub_query = f'WHERE (({datatype} = \'{dtype}\') AND ({src} != {tgt}) AND ({src} = \'{value}\' OR {tgt} = \'{value}\')) \
            #                         GROUP BY {src}, {tgt},{delete}'

            #     elif dtype in ["asr-corpus","ocr-corpus","monolingual-corpus","asr-unlabeled-corpus","document-layout-corpus","tts-corpus"]:
            #         sub_query = f'WHERE (({datatype} = \'{dtype}\')AND ({src} != {tgt})) GROUP BY {src}, {tgt},{delete}'
            #     qry_for_lang_pair  = query+sub_query

            #     result_parsed = utils.query_runner(qry_for_lang_pair)
            #     chart_data =  utils.result_formater_for_lang_pairs(result_parsed,dtype,value)
            #     return chart_data,sumtotal

            # #aggregate query for language groupby ; 1st level drill down for the chart
            # if grpby_params != None and len(match_params) ==2:
            #     params = grpby_params[0]
            #     grp_field  = params["field"]
            #     src_val = next((item["value"] for item in match_params if item["field"] == "sourceLanguage"), False)
            #     tgt_val = next((item["value"] for item in match_params if item["field"] == "targetLanguage"), False)
            #     if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
            #          query = f'SELECT SUM(\"{count}\" * \"{duration}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
            #                 WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
            #                 GROUP BY {src}, {tgt}, {delete}, {grp_field}'
            #     else:
            #         query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
            #                 WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
            #                 GROUP BY {src}, {tgt}, {delete}, {grp_field}'

            #     result_parsed = utils.query_runner(query)
            #     chart_data = utils.result_formater(result_parsed,grp_field,dtype)
            #     return chart_data,sumtotal

            # #aggregate query for groupby & matching together; 2nd level drill down
            # if grpby_params != None and len(match_params) ==3:
            #     params = grpby_params[0]
            #     grp_field  = params["field"]
            #     sub_field  = match_params[2]["field"]
            #     src_val = next((item["value"] for item in match_params if item["field"] == "sourceLanguage"), None)
            #     tgt_val = next((item["value"] for item in match_params if item["field"] == "targetLanguage"), None)
            #     sub_val = next((item["value"] for item in match_params  if item["field"] not in ["sourceLanguage","targetLanguage"]))
                
            #     if dtype in ["asr-corpus","asr-unlabeled-corpus","tts-corpus"]:
            #         query = f'SELECT SUM(\"{count}\" * \"{duration}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
            #                 WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
            #                 AND ({sub_field} = \'{sub_val}\') GROUP BY {src}, {tgt}, {delete}, {grp_field}'

            #     else:
            #         query = f'SELECT SUM(\"{count}\") as {total}, {src}, {tgt},{delete},{grp_field} FROM \"{DRUID_DB_SCHEMA}\"\
            #                 WHERE (({datatype} = \'{dtype}\') AND (({src} = \'{src_val}\' AND {tgt} = \'{tgt_val}\') OR ({src} = \'{tgt_val}\' AND {tgt} = \'{src_val}\')))\
            #                 AND ({sub_field} = \'{sub_val}\') GROUP BY {src}, {tgt}, {delete}, {grp_field}'
                
            #     result_parsed = utils.query_runner(query)
            #     chart_data = utils.result_formater(result_parsed,grp_field,dtype)
            #     return chart_data,sumtotal

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