from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_asr, asr_minimum_words_per_min
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
import audio_metadata
from word2number import w2n
from datetime import timedelta

class AudioMetadataCheck(BaseValidator):
    """
    Verifies the metadata for the audio file and
    adds the durationInSeconds field. Also verifies
    the correlation between the length of text and duration of audio clip
    """
    def execute(self, request):
        log.info('----Executing the audio file metadata check----')
        try:
            if request["datasetType"] == dataset_type_asr:
                audio_file = request['record']['fileLocation']
                metadata = audio_metadata.load(audio_file)

                if 'samplingRate' in request['record'].keys() and request['record']['samplingRate'] != None:
                    if metadata.streaminfo.sample_rate != request['record']['samplingRate']*1000:
                        return {"message": "Sampling rate does not match the specified value", "code": "INCORRECT_SAMPLING_RATE", "status": "FAILED"}
                if 'bitsPerSample' in request['record'].keys() and request['record']['bitsPerSample'] != None:
                    if metadata.streaminfo.bit_depth != w2n.word_to_num(request['record']['bitsPerSample']):
                        return {"message": "Bits per sample does not match the specified value", "code": "INCORRECT_BITS_PER_SAMPLE", "status": "FAILED"}

                if 'duration' in request['record'].keys():
                    request['record']['durationInSeconds'] = request['record']['duration']
                elif 'startTime' in request['record'].keys() and 'endTime' in request['record'].keys():
                    h, m, s = request['record']['startTime'].split(':')
                    start_t = timedelta(hours=int(h), minutes=int(m), seconds=float(s))
                    h, m, s = request['record']['endTime'].split(':')
                    end_t = timedelta(hours=int(h), minutes=int(m), seconds=float(s))
                    request['record']['durationInSeconds'] = (end_t-start_t).total_seconds()
                else:
                    request['record']['durationInSeconds'] = metadata.streaminfo.duration

                num_words = len(list(request['record']['text'].split()))
                words_per_minute = (num_words/request['record']['durationInSeconds'])*60
                if words_per_minute < asr_minimum_words_per_min:
                    return {"message": "Number of words too less for the audio duration", "code": "AUDIO_TEXT_INVALID_CORRELATION", "status": "FAILED"}

                log.info('----Audio metadata check -> Passed----')
                return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing Audio metadata check', e)
            return {"message": "Exception while executing Audio metadata check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

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