import hashlib
import logging
import multiprocessing
import threading
import time
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, offset, limit, aws_ocr_prefix
from repository.asr import OCRRepo
from utils.datasetutils import DatasetUtils

log = logging.getLogger('file')

mongo_instance = None
repo = OCRRepo()
utils = DatasetUtils()

class OCRService:
    def __init__(self):
        pass

    # Method to load ASR Dataset
    def load_ocr_dataset(self, request):
        log.info("Loading OCR Dataset.....")
        try:
            ip_data = request["data"]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            metadata = ip_data
            metadata.pop("records")
            ip_data = ip_data["records"]
            clean_data = self.get_clean_ocr_data(ip_data, error_list)
            if clean_data:
                func = partial(self.get_enriched_ocr_data, metadata=metadata)
                no_of_m1_process = request["processors"]
                pool_enrichers = multiprocessing.Pool(no_of_m1_process)
                enrichment_processors = pool_enrichers.map_async(func, clean_data).get()
                for result in enrichment_processors:
                    if result:
                        if result[0] == "INSERT":
                            if len(batch_data) == batch:
                                persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                                persist_thread.start()
                                persist_thread.join()
                                count += len(batch_data)
                                batch_data = []
                            batch_data.append(result[1])
                        elif result[0] == "FAILED":
                            error_list.append({"record": result[1], "cause": "UPLOAD_FAILED",
                                               "description": "Upload to s3 bucket failed"})
                        else:
                            error_list.append({"record": result[1], "cause": "DUPLICATE_RECORD",
                                               "description": "This record is already available in the system"})
                pool_enrichers.close()
                if batch_data:
                    persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                    persist_thread.start()
                    persist_thread.join()
                    count += len(batch_data)
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, "INVALID": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        lang_code = f'{request["details"]["sourceLanguage"]}|{request["details"]["targetLanguage"]}'
        return {"message": f'loaded {lang_code} dataset to DB', "status": "SUCCESS", "total": total, "inserts": count,
                "invalid": len(error_list)}

    # Method to perform basic checks on the input
    def get_clean_ocr_data(self, ip_data, error_list):
        duplicate_records, clean_data = set([]), []
        for data in ip_data:
            if 'groundTruth' not in data.keys() or 'imageFilename' not in data.keys() or 'imageFilePath' not in data.keys():
                error_list.append({"record": data, "cause": "INVALID_RECORD",
                                   "description": "either groundTruth or imageFilename or imageFilePath is missing"})
                continue
            image_hash = utils.hash_file(data["imageFilePath"])
            if not image_hash:
                error_list.append({"record": data, "cause": "IMAGE_UNAVAILABLE",
                                   "description": f'Image file is unavailable at -- {data["imageFilePath"]}'})
                continue
            unique_key = f'{image_hash}|{data["groundTruth"]}'
            unique_key = str(hashlib.sha256(unique_key.encode('utf-16')).hexdigest())
            if unique_key in duplicate_records:
                error_list.append({"record": data, "cause": "DUPLICATE_RECORD",
                                   "description": "This image file is repeated multiple times in the input"})
                continue
            else:
                duplicate_records.add(unique_key)
                data["imageHash"] = unique_key
                clean_data.append(data)
        duplicate_records.clear()
        return clean_data

    # Method to enrich asr dataset
    def get_enriched_ocr_data(self, data, metadata):
        records = self.get_ocr_dataset_internal({"imageHash": data["imageHash"]})
        if records:
            return "DUPLICATE", data
        insert_data = data
        insert_data["timestamp"] = eval(str(time.time()).replace('.', '')[0:13])
        tag_details = {
            "imageFilename": data["imageFilename"], "datasetType": data["datasetType"],
            "collectionMode": data["collectionMode"], "language": data["language"],
            "collectionSource": data["collectionSource"], "dataset": data["datasetId"],
            "domain": data["domain"], "license": data["license"], "imageHash": data["imageHash"]
        }
        insert_data["tags"] = list(utils.get_tags(tag_details))
        s3_file_name = data["audioFilename"] + metadata["serviceRequestNumber"] + insert_data["timestamp"]
        object_store_path = utils.upload_file(data["audioFilePath"], f'{aws_ocr_prefix}{s3_file_name}')
        if not object_store_path:
            return "FAILED", insert_data
        insert_data["objStorePath"] = object_store_path
        return "INSERT", insert_data

    # Method for deduplication
    def get_ocr_dataset_internal(self, query):
        try:
            exclude = {"_id": False}
            data = repo.search(query, exclude, None, None)
            if data:
                return data[0]
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None


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