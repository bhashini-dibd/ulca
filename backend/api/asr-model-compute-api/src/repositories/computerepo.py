from logging import exception
import os
from pydub import AudioSegment
import base64
import validators
import json
from config import shared_storage_path,vakyansh_audiouri_link,vakyansh_audicontent_link
import base64
import requests
import logging
from logging.config import dictConfig
log = logging.getLogger('file')


class ASRComputeRepo:

    def process_asr(self,lang,audio,userId,inference):
        
        callbackurl =   inference["callbackUrl"]
        transformat =   inference["schema"]["request"]["config"]["transcriptionFormat"]["value"].upper()
        audioformat =   inference["schema"]["request"]["config"]["audioFormat"].upper()
        url=validators.url(audio)
        if url == True:
            result = self.make_audiouri_call(audio,lang,callbackurl,transformat,audioformat)
            return result
        else:
            try:
                encode_string = audio
                file = f'{shared_storage_path}audio-{userId}.wav'
                with open (file, "wb") as wav_file:
                    decode_string = base64.b64decode(encode_string)
                    wav_file.write(decode_string)

                audio = AudioSegment.from_wav(file)
                audio = audio.set_channels(1)
                audio = audio.set_frame_rate(16000)
                processed_file = f'{shared_storage_path}audio-{userId}-processed.wav'
                audio.export(processed_file, format="wav")

                encoded_data=base64.b64encode(open(processed_file, "rb").read()) 
                os.remove(file)
                os.remove(processed_file)
                result = self.make_base64_audio_processor_call(encoded_data.decode("utf-8"),lang,callbackurl,transformat,audioformat)
                return result

            except Exception as e:
                log.info(f'Exception while processing request: {e}')
                return []

    
    def make_audiouri_call(self, url,lang,callbackurl,transformat,audioformat):
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"config": {"language": {"value": lang},"transcriptionFormat": transformat,"audioFormat": audioformat},
                        "audio": {"audioUri": url}}
            request_url = callbackurl
            log.info("Intiating request to process asr data on %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body)
            content = response.content
            response_data = json.loads(content)
            if response.status_code != 200:
                log.info(f'Requestfailed due to {response_data}')
                return None
            return response_data
        except Exception as e:
            log.exception(f'Exception while making api call: {e}')
            return []


    def make_base64_audio_processor_call(self,data,lang,callbackurl,transformat,audioformat):
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"config": {"language": {"value": lang},"transcriptionFormat": transformat,"audioFormat": audioformat},
                        "audio": {"audioContent": str(data)}}
            request_url = callbackurl
            log.info("Intiating request to process asr data on %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body)
            content = response.content
            response_data = json.loads(content)
            if response.status_code != 200:
                log.info(f'Requestfailed due to {response_data["message"]}')
                return None
            
            log.info("Received response from vakyanch end point to transcribe asr data")
            return response_data
        except Exception as e:
            log.exception(f'Exception while making api call: {e}')
            return []



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