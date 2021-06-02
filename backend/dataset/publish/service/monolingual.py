import hashlib
import logging
import multiprocessing
import threading
import time
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, offset, limit
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils

log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()


class MonolingualService:
    def __init__(self):
        pass

    def load_monolingual_dataset(self, request):
        log.info("Loading Dataset..... | {}".format(datetime.now()))
        try:
            ip_data = request["data"]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            log.info(f'Enriching and dumping dataset.....')
            clean_data = self.get_clean_data(ip_data, error_list)
            log.info(f'Actual Data: {len(ip_data)}, Clean Data: {len(clean_data)}')
            if clean_data:
                func = partial(self.get_enriched_data, request=request, sequential=False)
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
    def get_clean_data(self, ip_data, error_list):
        duplicate_records, clean_data = set([]), []
        for data in ip_data:
            if 'text' not in data.keys():
                error_list.append({"record": data, "cause": "INVALID_RECORD", "description": "Text is missing"})
                continue
            data['text'] = str(data['text']).strip()
            if data['text'] in duplicate_records:
                error_list.append({"record": data, "cause": "DUPLICATE_RECORD",
                                   "description": "This record is repeated multiple times in the input"})
                continue
            else:
                duplicate_records.add(data['text'])
                clean_data.append(data)
        duplicate_records.clear()
        return clean_data

    def get_enriched_data(self, data, metadata):
        txt_hash = str(hashlib.sha256(data["text"].encode('utf-16')).hexdigest())
        records = self.get_monolingual_dataset_internal({"textHash": txt_hash})
        if records:
            return "DUPLICATE", data
        insert_data = data
        insert_data["textHash"] = txt_hash
        insert_data["timestamp"] = eval(str(time.time()).replace('.', '')[0:13])
        tag_details = {
            "datasetType": data["datasetType"],
            "collectionMode": data["collectionMode"], "language": data["language"],
            "collectionSource": data["collectionSource"], "dataset": metadata["datasetId"],
            "domain": data["domain"], "license": data["license"], "textHash": data["textHash"]
        }
        insert_data["tags"] = list(utils.get_tags(tag_details))
        return "INSERT", insert_data


    def get_monolingual_dataset_internal(self, query):
        try:
            db_query = {"$in": query["hash"]}
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, None, None)
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