import csv
import json
import logging
import os
import threading
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import shared_storage_path, error_prefix, pt_publish_tool, error_batch_size
from .errorrepo import ErrorRepo
from utils.datasetutils import DatasetUtils



log = logging.getLogger('file')
mongo_instance = None
error_repo = ErrorRepo()
utils = DatasetUtils()


class ErrorEvent:
    def __init__(self):
        pass

    def write_error(self, data):
        log.info(f'Writing error for SRN -- {data["serviceRequestNumber"]}')
        if "eof" in data and data["eof"] == True:
            self.handle_eof(data)
        else:
            try:
                file = f'{shared_storage_path}error-{data["datasetName"].replace(" ","-")}-{data["serviceRequestNumber"]}.csv'
                error_rec = {"id": str(uuid.uuid4()), "serviceRequestNumber": data["serviceRequestNumber"],
                            "internal_file": file, "file": file, "time_stamp": str(datetime.now()), "error": data}
                error_repo.insert(error_rec)
                rec_count = error_repo.count({"serviceRequestNumber": data["serviceRequestNumber"]})
                if rec_count % error_batch_size == 0:
                    log.info(f'Upload thread forked for {data["serviceRequestNumber"]}')
                    persister = threading.Thread(target=self.get_error_report, args=(data["serviceRequestNumber"],False))
                    persister.start()
                    # self.get_error_report(data["serviceRequestNumber"],False)
            except Exception as e:
                log.exception(f'Exception while writing errors: {e}', e)
                return False

    def write_to_csv(self, data_list, file, srn):
        try:
            data_modified, data_pub = [], None
            for data in data_list:
                if isinstance(data, str):
                    data = json.loads(data)
                if 'stage' in data.keys():
                    if data["stage"] == pt_publish_tool:
                        data_pub = data
                data_modified.append(data)
            if not data_pub:
                data_pub = data_modified[0]
            with open(file, 'w', newline='') as output_file:
                dict_writer = csv.DictWriter(output_file, list(data_pub.keys()))
                dict_writer.writeheader()
                dict_writer.writerows(data_modified)
                output_file.close()
            log.info(f'{len(data_modified)} Errors written to csv for SRN -- {srn}')
            return
        except Exception as e:
            log.exception(f'Exception in csv writer: {e}', e)
            return

    def get_error_report(self, srn, internal):
        query = {"serviceRequestNumber": srn}
        exclude = {"_id": False}
        error_records = error_repo.search(query, exclude, None, None)

        if internal:
            return error_records
        try:
            if error_records:
                errors, error_rec = [], None
                for error in error_records:
                    errors.append(error["error"])
                    if 'uploaded' in error.keys():
                        error_rec = error  
                if not error_rec:
                    error_rec = error_records[0]
                error_rec["errors"] = errors
                log.info(f"Uploading errors to Object Store for {srn}")
                error_rec = self.upload_error_to_object_store(error_rec, srn)
                error_rec.pop("error")
                error_rec.pop("errors")
                return [error_rec]
            else:
                return []
        except Exception as e:
            log.exception(f'Exception while fetching error report: {e}', e)
            return []

    def upload_error_to_object_store(self, error_record, srn):
        try:
            file = error_record["internal_file"]
            log.info(f'Error List: {len(error_record["errors"])} for SRN -- {srn}')
            self.write_to_csv(error_record["errors"], file, srn)
            file_name = error_record["internal_file"].replace("/opt/","")
            error_record["file"] = utils.file_store_upload_call(file,file_name,error_prefix)
            error_record["uploaded"], error_record["errors"] = True, []
            error_repo.update(error_record)
            log.info(f'Error report uploaded to object store for SRN -- {srn}')
            os.remove(file)
            return error_record
        except Exception as e:
            log.exception(f'Exception while ingesting errors to object store: {e}', e)
            return []

    def handle_eof(self, eof_event):
        log.info('______________EOF__________________')
        log.info(f'Received EOF event to error for srn -- {eof_event["serviceRequestNumber"]}')
        try:
            response = self.get_error_report(eof_event["serviceRequestNumber"], False)
            if response:
                response= response[0]
                response["eof"]= True
                response["id"] = str(uuid.uuid4())
                query = {"serviceRequestNumber": eof_event["serviceRequestNumber"]}
                log.info(f'Removing records on srn -- {eof_event["serviceRequestNumber"]}')
                error_repo.remove(query)
                log.info(f'Inserting eof record on daatabase')
                log.info(f'Response -{response}')
                error_repo.insert(response)
            else:
                log.info(f'EOF Received for srn with 0 errors srn -- {eof_event["serviceRequestNumber"]}')
        except Exception as e:
            log.info(f'Exception on eof handler : {e}')
            return False


    def search_error_report(self, srn, internal):
        try:
            query = {"serviceRequestNumber": srn,"uploaded":True}
            exclude = {"_id": False}
            log.info(f'Search for error reports of SRN -- {srn} from db started')
            error_records = error_repo.search(query, exclude, None, None)
            log.info(f'Error report returned for {srn}')
            return error_records
        except Exception as e:
            log.exception(f'Exception while fetching error report: {e}', e)
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