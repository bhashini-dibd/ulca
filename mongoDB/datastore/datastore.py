import hashlib
import json
import threading
import time
import uuid
import random
from collections import OrderedDict
from datetime import datetime
from configs import file_path, file_name, default_offset, default_limit, mongo_server_host, mongo_ulca_db
from configs import mongo_ulca_dataset_col

import pymongo

mongo_instance = None

class Datastore:
    def __init__(self):
        pass

    def load_dataset(self, request):
        print("\nLoading Dataset..... | {}".format(datetime.now()))
        try:
            if 'path' not in request.keys():
                path = f'{file_path}' + f'{file_name}'
            else:
                path = request["path"]
            print("File -- {} | {}".format(path, datetime.now()))
            dataset = open(path, "r")
            data_json = json.load(dataset)
            enriched_data = []
            i, j, c = len(data_json), 0, 0
            thread_batch_size = 1000000
            while len(data_json) < thread_batch_size:
                thread_batch_size = thread_batch_size / 10
            print(f'Thread Batch Size: {thread_batch_size}, Data Size: {len(data_json)} | {datetime.now()}')
            for data in data_json:
                data["score"] = random.uniform(0, 1)
                tag_details, details = {}, request["details"]
                if 'sourceText' in data.keys():
                    tag_details["srcHash"] = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
                tag_details["languageCode"] = f'{details["sourceLanguage"]}|{details["targetLanguage"]}'
                tag_details["name"], tag_details["category"] = details["name"], details["category"]
                tag_details["collectionMode"] = details["collectionMode"]
                tag_details["domain"], tag_details["licence"] = details["domain"], details["licence"]
                tags = list(self.get_tags(tag_details))
                data_dict = {"id": str(uuid.uuid4()), "contributors": request["contributors"],
                             "timestamp": eval(str(time.time()).replace('.', '')[0:13]), "details": details,
                             "data": data, "tags": tags}
                enriched_data.append(data_dict)
                if j == thread_batch_size:
                    c += j
                    j = 0
                    t = threading.Thread(target=self.insert, args=(enriched_data,))
                    t.start()
                    print(f'Dumping {c}..... | {datetime.now()}')
                    enriched_data = []
                j += 1
            if enriched_data:
                t = threading.Thread(target=self.insert, args=(enriched_data,))
                t.start()
                c += len(enriched_data)
                print(f'FINAL DUMP -- Dumping {c} ..... | {datetime.now()}')
            else:
                print(f'FINAL DUMP -- Dumping {c} ..... | {datetime.now()}')
        except Exception as e:
            print(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"message": f'loaded {i} no. of records to DB', "status": "SUCCESS"}

    def get_tags(self, d):
        for v in d.values():
            if isinstance(v, dict):
                yield from self.get_tags(v)
            elif isinstance(v, list):
                for entries in v:
                    yield entries
            else:
                yield v

    def get_dataset(self, query):
        print(f'\nFetching datasets..... | {datetime.now()}')
        offset = query["offset"] if 'offset' in query.keys() else default_offset
        limit = query["limit"] if 'limit' in query.keys() else default_limit
        db_query = {}
        score_query = {}
        if 'minScore' in query.keys():
            score_query["$gte"] = query["minScore"]
        if 'maxScore' in query.keys():
            score_query["$lte"] = query["maxScore"]
        if score_query:
            db_query["data.score"] = score_query
        if 'score' in query.keys():
            db_query["data.score"] = query["score"]
        tags = []
        if 'languageCode' in query.keys():
            tags.append(query["languageCode"])
        if 'collectionMode' in query.keys():
            tags.append(query["collectionMode"])
        if 'licence' in query.keys():
            tags.append(query["licence"])
        if 'domain' in query.keys():
            tags.append(query["domain"])
        if 'srcText' in query.keys():
            tags.append(str(hashlib.sha256(query["srcText"].encode('utf-16')).hexdigest()))
        if tags:
            db_query["tags"] = {"$all": tags}
            #db_query["$where"] = f'this.tags.length > {len(tags)}'
        exclude = {"_id": False, "data": True}
        data = self.search(db_query, exclude, offset, limit)
        count = len(data)
        print(f'Result count: {count} | {datetime.now()}')
        file = "NA"
        '''if data:
            print(f'Dumping dataset to file.... | {datetime.now()}')
            file = f'{result_path}/{uuid.uuid4()}.json'
            with open(file, 'w') as f:
                json.dump(data, f)'''
        print(f'Done! | {datetime.now()}')
        return {"count": count, "query": db_query, "dataset": file}

    ####################### DB ##############################

    def set_mongo_cluster(self, create):
        if create:
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_db)
            ulca_db = client[mongo_ulca_db]
            ulca_col = ulca_db[mongo_ulca_dataset_col]
            ulca_col.create_index([("data.score", -1)])
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("id", "hashed")])
            db = client.admin
            db.command('enableSharding', mongo_ulca_db)
            key = OrderedDict([("id", "hashed")])
            db.command({'shardCollection': f'{mongo_ulca_db}.{mongo_ulca_dataset_col}', 'key': key})
        else:
            return None

    def instantiate(self):
        client = pymongo.MongoClient(mongo_server_host)
        db = client[mongo_ulca_db]
        mongo_instance = db[mongo_ulca_dataset_col]
        return mongo_instance

    def get_mongo_instance(self):
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    def insert(self, data_list):
        col = self.get_mongo_instance()
        col.insert_many(data_list)

    # Searches the object into mongo collection
    def search(self, query, exclude, offset, res_limit):
        col = self.get_mongo_instance()
        if offset is None and res_limit is None:
            res = col.find(query, exclude).sort([('_id', 1)])
        else:
            res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
        result = []
        for record in res:
            result.append(record)
        return result
