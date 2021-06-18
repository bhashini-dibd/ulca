from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_asr
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
import audio_metadata
from word2number import w2n

class AudioMetadataCheck(BaseValidator):
    """
    Verifies the metadata for the audio file
    """
    def execute(self, request):
        log.info('----Executing the audio file metadata check----')
        try:
            if request["datasetType"] == dataset_type_asr and 'samplingRate' in request['record'].keys() and 'bitsPerSample' in request['record'].keys():
                audio_file = request['record']['fileLocation']
                metadata = audio_metadata.load(audio_file)
                if metadata.streaminfo.sample_rate != request['record']['samplingRate']*1000:
                    return {"message": "Sampling rate does not match the specified value", "code": "INCORRECT_SAMPLING_RATE", "status": "FAILED"}
                if metadata.streaminfo.bit_depth != w2n.word_to_num(request['record']['bitsPerSample']):
                    return {"message": "Bits per sample does not match the specified value", "code": "INCORRECT_BITS_PER_SAMPLE", "status": "FAILED"}
            
                log.info('----Audio metadata check -> Passed----')
                return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing Audio metadata check', e)


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