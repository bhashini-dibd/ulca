from logging import exception
import os
from pydub import AudioSegment
from models.response import CustomResponse, post_error
import base64
import json
from config import shared_storage_path
import base64
import requests
import logging
from logging.config import dictConfig
log = logging.getLogger('file')


class ASRComputeRepo:

    def process_asr(self,lang,audio,userId,inference,uri):
        """
        Processing audio urls / encoded audio content
        If url, directly initiating the model call
        If audio is base64 encoded :
        - Decoding back from base64
        - setting channel to 1
        - Setting frame rate to 16k
        - Encoding back to base64
        - decoding to utf-8
        """
        
        callbackurl =   inference["callbackUrl"]
        transformat =   inference["schema"]["request"]["config"]["transcriptionFormat"]["value"]
        audioformat =   inference["schema"]["request"]["config"]["audioFormat"]
        if uri == True:
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
                result = self.make_base64_audio_processor_call(encoded_data.decode("utf-8"),lang,callbackurl,transformat,audioformat,punctiation=True)
                return result

            except Exception as e:
                log.info(f'Exception while processing request: {e}')
                return []

    def process_asr_from_audio_file(self,lang,audio_file_path,callback_url,transformat,audioformat,punctiation=False):
        """
        Processing audio files.
        - Reading file from shared storage
        - setting channel to 1
        - Setting frame rate to 16k
        - Encoding back to base64
        - decoding to utf-8
        """
        try:
            audio = AudioSegment.from_wav(audio_file_path)
            audio = audio.set_channels(1)
            audio = audio.set_frame_rate(16000)
            processed_file = f'{shared_storage_path}audio-processed.wav'
            audio.export(processed_file, format="wav")
            encoded_data=base64.b64encode(open(processed_file, "rb").read()) 
            os.remove(processed_file)
            result = self.make_base64_audio_processor_call(encoded_data.decode("utf-8"),lang,callback_url,transformat,audioformat,punctiation)
            return result

        except Exception as e:
            log.info(f'Exception while processing request: {e}')
            return {}


    
    def make_audiouri_call(self, url,lang,callbackurl,transformat,audioformat):
        """
        API call to model endpoint for audio urls
        """
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"config": {"language": {"sourceLanguage": lang},"transcriptionFormat": {"value":transformat},"audioFormat": audioformat},
                        "audio": [{"audioUri": url}]}
            log.info(f"Request body : {body}")
            request_url = callbackurl
            log.info("Intiating request to process asr data on %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body,verify=False)
            content = response.content
            log.info(content)
            response_data = json.loads(content)
            log.info(f"Response : {response_data}")
            return response_data
        except Exception as e:
            log.exception(f'Exception while making api call: {e}')
            return {"status_text":"Incorrect inference endpoint or invalid response"}


    def make_base64_audio_processor_call(self,data,lang,callbackurl,transformat,audioformat,punctiation):
        """
        API call to model endpoint for audio content
        """
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"config": {"language": {"sourceLanguage": lang},"transcriptionFormat": {"value":transformat},"audioFormat": audioformat,
                        "punctuation": punctiation,"enableInverseTextNormalization": False},"audio": [{"audioContent": str(data)}]}
            request_url = callbackurl
            log.info("Intiating request to process asr data on %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body,verify=False)
            content = response.content
            log.info(content)
            response_data = json.loads(content)
            log.info("Received response from inference end point to transcribe asr data")
            log.info(f"Response : {response_data}")
            return response_data
        except Exception as e:
            log.exception(f'Exception while making api call: {e}')
            return {"status_text":"Incorrect inference endpoint or invalid response"}



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