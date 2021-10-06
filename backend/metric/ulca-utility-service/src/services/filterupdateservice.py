from models.asrunlabeled import AsrUnlabeledModel
from models.asr import AsrModel
from threading import Thread
from config import filter_cron_interval_sec
# from models import ParallelModel, OcrModel, MonolingualModel
import logging
from logging.config import dictConfig
log         =   logging.getLogger('file')
# from utilities import DataUtils

# utils           =   DataUtils()
# parallel_model  =   ParallelModel()
# ocr_model       =   OcrModel()
# mono_model      =   MonolingualModel()
asr_model       =   AsrModel()
asr_unlabeled_model =   AsrUnlabeledModel()

class FilterCronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0

        while not self.stopped.wait(filter_cron_interval_sec):
            log.info(f'FilterCronProcessor run :{run}')
            try:
                data,filepath = utils.read_from_config_file()
                response = self.update_filter_params(data) 
                if response != False:
                    utils.write_to_config_file(filepath,response)
                    log.info("Updated filter params succesfully")
                    utils.upload_to_object_store(filepath)

                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on FilterCronProcessor run : {run} , exception : {e}')

    def update_filter_params(self,data):
        data_new = {}
        para_response = parallel_model.compute_parallel_data_filters(data["parallel-corpus"])
        if not para_response:
            log.info("Failed to update parallel filter params!")
            return False
        data["parallel-corpus"] = para_response
        ocr_response = ocr_model.compute_ocr_data_filters(data["ocr-corpus"])
        if not ocr_response:
            log.info("Failed to update ocr filter params!")
            return False
        data["ocr-corpus"] = ocr_response
        mono_response = mono_model.compute_monolingual_data_filters(data["monolingual-corpus"])
        if not mono_response:
            log.info("Failed to update mono lingual filter params!")
            return False
        data["monolingual-corpus"] = mono_response
        asr_response = asr_model.compute_asr_data_filters(data["asr-corpus"])
        if not asr_response:
            log.info("Failed to update asr filter params!")
            return False
        data["asr-corpus"] = asr_response
        asr_unlabel_response = asr_unlabeled_model.compute_asr_unlabeled_data_filters(data["asr-unlabeled-corpus"])
        if not asr_unlabel_response:
            log.info("Failed to update mono lingual filter params!")
            return False
        data["asr-unlabeled-corpus"] = asr_unlabel_response
        data_new["dataset"] = data
        return data_new


        


    

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
