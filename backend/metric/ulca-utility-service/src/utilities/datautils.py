import requests
from config import data_filter_set_file_path,shared_storage_path,filter_file_name, file_store_host, file_store_upload_endpoint,smtp_server,sender_email, password,dscountsubject,receiver_email
from config import data_metric_host,data_metric_endpoint,shared_storage_path,sts_subject,sts_html
import json
import logging
from logging.config import dictConfig
log = logging.getLogger('file')
from datetime import datetime
import csv
import pytz
IST = pytz.timezone('Asia/Kolkata')
import os
from email.message import EmailMessage
import os
from config import filename
import email, smtplib, ssl



class DataUtils:
    def __init__(self):
        pass

    def write_to_csv(self, data_list):
        try:
            tdy_date    =  datetime.now(IST).strftime('%Y:%m:%d-%H:%M:%S')
            file = f'{shared_storage_path}{tdy_date}.csv'
            csv_headers = ["serviceRequestNumber","download","ingest","validate","publish"]
            log.info('Started csv writing !...')
            with open(file, 'w') as output_file:
                dict_writer = csv.DictWriter(output_file,fieldnames=csv_headers,extrasaction='ignore')
                dict_writer.writeheader()
                for data in data_list:
                    dict_writer.writerow(data)
            log.info(f'{len(data_list)} Jobs written to csv -{file}')
            return file
        except Exception as e:
            log.exception(f'Exception in csv writer: {e}')
            return

    def read_from_config_file(self):
        """Reading filters from git config."""
        
        try:
            file = requests.get(data_filter_set_file_path, allow_redirects=True)
            file_path = shared_storage_path + filter_file_name
            open(file_path, 'wb').write(file.content)
            log.info(f"Filters read from git and pushed to local {file_path}")
            with open(file_path, 'r') as stream:
                parsed = json.load(stream)
                filterconfigs = parsed['dataset']
            return filterconfigs, file_path
        except Exception as exc:
            log.exception("Exception while reading filters: " +str(exc))
            return None, None

    def write_to_config_file(self,filepath,data):
        try:
            log.info(f"updating {filepath}")
            with open (filepath,'w') as confile:
                json.dump(data,confile)
        except Exception as e:
            log.info(f"Exception while writing filter configs : {e}")



    def upload_to_object_store(self,file_path):
        """Uploading file to object store"""
        
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"fileName":"datasetFilterParams.json","storageFolder":"error","fileLocation":file_path}
            request_url = file_store_host+file_store_upload_endpoint
            log.info("Intiating request to store data on object store %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body)
            response_data = response.content
            log.info("Received data from upload end point of file store service")
            response = json.loads(response_data)
            if "data" not in response:
                return False
            log.info(response["data"])
        except Exception as e:
            log.exception(f'Exception while pushing config file to object store: {e}')
            return False

    def get_statistics_from_metrics_service(self,request):
        """Fetching statistics from metrics service"""
        
        try:
            headers =   {"Content-Type": "application/json"}
            request_url = data_metric_host+data_metric_endpoint
            log.info("Fetching data from %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = request)
            response_data = response.content
            log.info("Received data from upload end point of file store service")
            response = json.loads(response_data)
            if "count" not in response:
                return False
            return response["count"]
        except Exception as e:
            log.exception(f'Exception while pushing config file to object store: {e}')
            return False

    def generate_email_notification(self,data):

        try:
            if "parallel_count" in data.keys():
                message = EmailMessage()
                message["From"] = sender_email
                message["To"] = receiver_email
                message["Subject"] = dscountsubject
                html_ = open(filename).read()
                html_ = html_.replace('{{parallel}}',data['parallel_count']).replace('{{mono}}',data['mono_count']).replace('{{asr}}',data['asr_count']).replace('{{asrun}}',data['asr_unlabeled_count']).replace('{{tts}}',data['tts_count']).replace('{{ocr}}',data['ocr_count']).replace('{{transliteration}}',data['transliteration_count']).replace('{{glossary}}',data['glossary_count']).replace('{{inprogress}}',data['inprogress']).replace('{{pending}}',data['pending'])

                message.add_alternative(html_,subtype = 'html')
                
                with smtplib.SMTP_SSL(smtp_server, 465) as server:
                    server.login(sender_email, password)
                        # Prefer the modern send_message method
                    server.send_message(message)
                    server.quit()
                    del message["From"]
                    del message["To"]
            
            else:
                message = EmailMessage()
                message["From"] = sender_email
                message["To"] = receiver_email
                message["Subject"] = sts_subject
                html_ = open(sts_html).read()
                html_ = html_.replace('{{asr_model_name}}',data['asr-model']).replace('{{asr_model_id}}',data['asr-modelid']).replace('{{asr_status}}',data['speech_1']).replace('{{nmt_model_name}}',data['nmt-modelname']).replace('{{nmt_model_id}}',data['nmt-modelid']).replace('{{nmt_model_status}}',data['Translation']).replace('{{tts_model_name}}',data['tts-modelname']).replace('{{tts_model_id}}',data['tts-modelid']).replace('{{tts_model_status}}',data['speech_2'])

                message.add_alternative(html_,subtype = 'html')
                
                with smtplib.SMTP_SSL(smtp_server, 465) as server:
                    server.login(sender_email, password)
                        # Prefer the modern send_message method
                    server.send_message(message)
                    server.quit()
                    del message["From"]
                    del message["To"]

        except Exception as e:
            log.exception("Exception while generating email notification for ULCA statistics: " +
                          str(e))
    



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