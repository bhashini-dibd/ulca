import hashlib
import logging
import multiprocessing
import threading
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, no_of_parallel_processes, offset, limit, search_output_topic, \
    sample_size, mono_non_tag_keys, mono_immutable_keys, delete_output_topic
from repository.monolingual import MonolingualRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer

log = logging.getLogger('file')

repo = MonolingualRepo()
utils = DatasetUtils()
prod = Producer()


class MonolingualService:
    def __init__(self):
        pass

    def load_monolingual_dataset(self, request):
        log.info("Loading Dataset..... | {}".format(datetime.now()))
        try:
            ip_data = request["data"]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            metadata = ip_data
            metadata.pop("record")
            ip_data = [ip_data["record"]]
            clean_data = self.get_clean_data(ip_data, error_list)
            if clean_data:
                func = partial(self.get_enriched_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
                enrichment_processors = pool_enrichers.map_async(func, clean_data).get()
                for result in enrichment_processors:
                    if result:
                        if result[0] == "INSERT":
                            if len(batch_data) == batch:
                                if metadata["datasetMode"] != 'pseudo':
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
                    if metadata["datasetMode"] != 'pseudo':
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
        records = self.get_monolingual_dataset_internal({"textHash": data["textHash"]})
        if records:
            dup_data = self.enrich_duplicate_data(data, records[0], metadata)
            repo.update(dup_data)
            return "DUPLICATE", data
        insert_data = data
        insert_data["datasetType"] = metadata["datasetType"]
        insert_data["datasetId"] = [metadata["datasetId"]]
        for key in insert_data.keys():
            if key not in mono_immutable_keys:
                insert_data[key] = [insert_data[key]]
        insert_data["tags"] = self.get_tags(insert_data)
        return "INSERT", insert_data

    def enrich_duplicate_data(self, data, record, metadata):
        record["datasetId"].append(metadata["datasetId"])
        for key in data.keys():
            if key not in mono_immutable_keys:
                if key not in record.keys():
                    record[key] = [data[key]]
                elif isinstance(data[key], list):
                    record[key] = list(set(data[key]) | set(record[key]))
                else:
                    if isinstance(record[key], list):
                        if data[key] not in record[key]:
                            record[key].append(data[key])
        record["tags"] = self.get_tags(record)
        return record

    def get_tags(self, insert_data):
        tag_details = insert_data
        for key in mono_non_tag_keys:
            if key in tag_details.keys():
                tag_details.pop(key)
        return list(utils.get_tags(tag_details))


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

    # Method for searching asr datasets
    def get_monolingual_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
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
            if 'datasetType' in query.keys():
                tags.append(query["datasetType"])
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

    def delete_mono_dataset(self, delete_req):
        log.info(f'Deleting datasets....')
        records = self.get_monolingual_dataset({"datasetId": delete_req["datasetId"]})
        d, u = 0, 0
        for record in records:
            if len(record["datasetId"]) == 1:
                repo.delete(record["id"])
                d += 1
            else:
                record["datasetId"].remove(delete_req["datasetId"])
                record["tags"].remove(delete_req["datasetId"])
                repo.update(record)
                u += 1
        op = {"serviceRequestNumber": delete_req["serviceRequestNumber"], "deleted": d, "updated": u}
        prod.produce(op, delete_output_topic, None)
        log.info(f'Done!')
        return op

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