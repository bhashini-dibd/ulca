import hashlib
import logging
import multiprocessing
import threading
import time
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, no_of_parallel_processes, aws_asr_prefix, search_output_topic, sample_size
from repository.asr import ASRRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer

log = logging.getLogger('file')

mongo_instance = None
repo = ASRRepo()
utils = DatasetUtils()
prod = Producer()

class ASRService:
    def __init__(self):
        pass

    # Method to load ASR Dataset
    def load_asr_dataset(self, request):
        log.info("Loading ASR Dataset.....")
        try:
            ip_data = request["data"]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            metadata = ip_data
            metadata.pop("record")
            ip_data = [ip_data["record"]]
            clean_data = self.get_clean_asr_data(ip_data, error_list)
            log.info(f'Actual Data: {len(ip_data)}, Clean Data: {len(clean_data)}')
            if clean_data:
                func = partial(self.get_enriched_asr_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
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
                    log.info(f'Processing final batch.....')
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
    def get_clean_asr_data(self, ip_data, error_list):
        duplicate_records, clean_data = set([]), []
        for data in ip_data:
            if 'audioFilename' not in data.keys() or 'text' not in data.keys() or 'audioFilePath' not in data.keys():
                error_list.append({"record": data, "cause": "INVALID_RECORD",
                                   "description": "either audioFilename or text or audioFilPath is missing"})
                continue
            audio_hash = utils.hash_file(data["audioFilePath"])
            if not audio_hash:
                error_list.append({"record": data, "cause": "AUDIO_UNAVAILABLE",
                                   "description": f'Audio file is unavailable at -- {data["audioFilePath"]}'})
                continue
            unique_key = f'{audio_hash}|{data["text"]}'
            unique_key = str(hashlib.sha256(unique_key.encode('utf-16')).hexdigest())
            if unique_key in duplicate_records:
                error_list.append({"record": data, "cause": "DUPLICATE_RECORD",
                                   "description": "This audio file is repeated multiple times in the input"})
                continue
            else:
                duplicate_records.add(unique_key)
                data["audioHash"] = unique_key
                clean_data.append(data)
        duplicate_records.clear()
        return clean_data

    # Method to enrich asr dataset
    def get_enriched_asr_data(self, data, metadata):
        records = self.get_asr_dataset_internal({"audioHash": data["audioHash"]})
        if records:
            return "DUPLICATE", data
        insert_data = data
        insert_data["timestamp"] = eval(str(time.time()).replace('.', '')[0:13])
        tag_details = {
            "audioFilename": data["audioFilename"], "channel": data["channel"], "gender": data["gender"],
            "collectionMode": data["collectionMode"], "language": data["language"],
            "collectionSource": data["collectionSource"], "dataset": data["datasetId"], "datasetType": data["datasetType"],
            "domain": data["domain"], "license": data["license"], "audioHash": data["audioHash"]
        }
        insert_data["tags"] = list(utils.get_tags(tag_details))
        s3_file_name = data["audioFilename"] + metadata["serviceRequestNumber"] + insert_data["timestamp"]
        object_store_path = utils.upload_file(data["audioFilePath"], f'{aws_asr_prefix}{s3_file_name}')
        if not object_store_path:
            return "FAILED", insert_data
        insert_data["objStorePath"] = object_store_path
        return "INSERT", insert_data

    # Method for deduplication
    def get_asr_dataset_internal(self, query):
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


    # Method for searching asr datasets
    def get_asr_dataset(self, query):
        log.info(f'Fetching datasets....')
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'age' in query.keys():
                db_query["age"] = query["age"]
            if 'language' in query.keys():
                tags.append(query["language"])
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'collectionSource' in query.keys():
                tags.append(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'channel' in query.keys():
                tags.append(query["channel"])
            if 'gender' in query.keys():
                tags.append(query["gender"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if tags:
                db_query["tags"] = tags
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, off, lim)
            result, query, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}')
            path = utils.push_result_to_s3(result, query["serviceRequestNumber"])
            if path:
                size = sample_size
                if count <= 10:
                    size = count
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": count, "sample": result[:size], "dataset": path}
            else:
                log.error(f'There was an error while pushing result to S3')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None}
            prod.produce(op, search_output_topic, None)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}


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