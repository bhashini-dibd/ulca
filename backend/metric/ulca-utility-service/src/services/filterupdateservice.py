from models.asrunlabeled import AsrUnlabeledModel
from models.asr import AsrModel
from threading import Thread
from config import error_cron_interval_sec
from models import ParallelModel, OcrModel, MonolingualModel
import logging
from logging.config import dictConfig
log         =   logging.getLogger('file')
from utilities import DataUtils

utils           =   DataUtils()
parallel_model  =   ParallelModel()
ocr_model       =   OcrModel()
mono_model      =   MonolingualModel()
asr_model       =   AsrModel()
asr_unlabeled_model =   AsrUnlabeledModel

class FilterCronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0

        while not self.stopped.wait(error_cron_interval_sec):
            log.info(f'FilterCronProcessor run :{run}')
            try:
                data,filepath = utils.read_from_config_file()
                response = self.update_filter_params(data) 
                if response != False:
                    utils.write_to_config_file(filepath)
                    log.info("Updated filter params succesfully")
                    utils.upload_to_object_store(filepath)

                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on FilterCronProcessor run : {run} , exception : {e}')

    def update_filter_params(self,data):
        para_response = parallel_model.compute_parallel_data_filters(data["parallel-dataset"])
        if not para_response:
            log.info("Failed to update parallel filter params!")
            return False
        data["parallel-dataset"] = para_response
        ocr_response = ocr_model.compute_ocr_data_filters(data["ocr-dataset"])
        if not ocr_response:
            log.info("Failed to update ocr filter params!")
            return False
        data["ocr-dataset"] = ocr_response
        mono_response = mono_model.compute_monolingual_data_filters(data["monolingual-dataset"])
        if not mono_response:
            log.info("Failed to update mono lingual filter params!")
            return False
        data["monolingual-dataset"] = mono_response
        asr_response = asr_model.compute_asr_data_filters(data["asr-dataset"])
        if not asr_response:
            log.info("Failed to update asr filter params!")
            return False
        data["asr-dataset"] = asr_response
        asr_unlabel_response = asr_unlabeled_model.compute_asr_unlabeled_data_filters(data["asr-unlabeled-dataset"])
        if not asr_unlabel_response:
            log.info("Failed to update mono lingual filter params!")
            return False
        data["asr-unlabeled-dataset"] = asr_unlabel_response

        return data


        


    

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
