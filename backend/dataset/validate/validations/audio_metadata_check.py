from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_asr, dataset_type_asr_unlabeled, dataset_type_tts, asr_minimum_words_per_min
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
import audio_metadata
from word2number import w2n
from datetime import timedelta
import os
from pydub import AudioSegment

class AudioMetadataCheck(BaseValidator):
    """
    Verifies the metadata for the audio file and
    adds the durationInSeconds field. Also verifies
    the correlation between the length of text and duration of audio clip
    """
    def execute(self, request):
        log.info('----Executing the audio file metadata check----')
        try:
            if request["datasetType"] in [dataset_type_asr, dataset_type_asr_unlabeled, dataset_type_tts]:
                audio_file = request['record']['fileLocation']
                try:
                    if os.path.exists(audio_file) and os.path.isfile(audio_file):
                        file_size = os.path.getsize(audio_file)
                    else:
                        log.info('The audio file does not exist in file store')
                        return {"message": "Exception while executing Audio metadata check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}
                except Exception as e:
                    log.exception(f"Exception while accessing file from file store: {str(e)}")
                    return {"message": "Exception while executing Audio metadata check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

                if file_size == 0:
                    return {"message": "The audio file is unplayable, the filesize is 0 bytes", "code": "ZERO_BYTES_FILE", "status": "FAILED"}

                try:
                    #temp logic for m4a
                    if os.path.exists(audio_file) and os.path.isfile(audio_file) and audio_file.split('.')[-1] != 'm4a':
                        metadata = audio_metadata.load(audio_file)
                    else:
                        log.info('The audio file does not exist in file store')
                        return {"message": "Exception while executing Audio metadata check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}
                except Exception as e:
                    log.exception(f"Exception while loading the audio file: {str(e)}")
                    return {"message": "Unable to load the audio file, file format is unsupported or the file is corrupt", "code": "INVALID_AUDIO_FILE", "status": "FAILED"}


                if 'duration' in request['record'].keys():
                    request['record']['durationInSeconds'] = request['record']['duration']
                elif 'startTime' in request['record'].keys() and 'endTime' in request['record'].keys():
                    h, m, s = request['record']['startTime'].split(':')
                    start_t = timedelta(hours=int(h), minutes=int(m), seconds=float(s))
                    h, m, s = request['record']['endTime'].split(':')
                    end_t = timedelta(hours=int(h), minutes=int(m), seconds=float(s))
                    request['record']['durationInSeconds'] = (end_t-start_t).total_seconds()
                else:
                    #temp logic for m4a
                    if audio_file.split('.')[-1] == 'm4a':
                        myaudio = AudioSegment.from_file(audio_file)
                        request['record']['durationInSeconds'] = myaudio.duration_seconds
                    else:
                        request['record']['durationInSeconds'] = metadata.streaminfo.duration


                if 'samplingRate' in request['record'].keys() and request['record']['samplingRate'] != None:
                    if metadata.streaminfo.sample_rate != request['record']['samplingRate']*1000:
                        error_message = 'Sampling rate does not match the specified value: Expected Value - ' + str(metadata.streaminfo.sample_rate/1000) + ', Specified Value - ' + str(request['record']['samplingRate'])
                        return {"message": error_message, "code": "INCORRECT_SAMPLING_RATE", "status": "FAILED"}
                if 'bitsPerSample' in request['record'].keys() and request['record']['bitsPerSample'] != None:
                    if metadata.filepath.split('.')[-1] != 'mp3' and metadata.streaminfo.bit_depth != w2n.word_to_num(request['record']['bitsPerSample']):
                        error_message = 'Bits per sample does not match the specified value: Expected Value - ' + str(metadata.streaminfo.bit_depth) + ', Specified Value - ' + str(request['record']['bitsPerSample'])
                        return {"message": error_message, "code": "INCORRECT_BITS_PER_SAMPLE", "status": "FAILED"}


                if request["datasetType"] in [dataset_type_asr, dataset_type_tts]:
                    num_words = len(list(request['record']['text'].split()))
                    words_per_minute = (num_words/request['record']['durationInSeconds'])*60
                    if words_per_minute < asr_minimum_words_per_min:
                        return {"message": "Number of words too less for the audio duration", "code": "AUDIO_TEXT_INVALID_CORRELATION", "status": "FAILED"}

                log.info('----Audio metadata check -> Passed----')
                return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing Audio metadata check: {str(e)}")
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