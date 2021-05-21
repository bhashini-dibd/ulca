import hashlib
import itertools
import json
import logging
import multiprocessing
import uuid
import random
from collections import OrderedDict
from datetime import datetime
from functools import partial
from logging.config import dictConfig

from configs import file_path, file_name, default_offset, default_limit, mongo_server_host, mongo_ulca_m3_db
from configs import mongo_ulca_dataset_m3_col

import pymongo
log = logging.getLogger('file')


mongo_instance = None

class ModelThree:
    def __init__(self):
        pass

    def load_dataset(self, request):
        log.info("Loading M3 Dataset..... | {}".format(datetime.now()))
        try:
            if 'path' not in request.keys():
                path = f'{file_path}' + f'{file_name}'
            else:
                path = request["path"]
            log.info("File -- {} | {}".format(path, datetime.now()))
            dataset = open(path, "r")
            data_json = json.load(dataset)
            if 'slice' in request.keys():
                data_json = data_json[request["slice"]["start"]:request["slice"]["end"]]
            total, duplicates, batch = len(data_json), 0, request["batch"]
            log.info(f'Enriching and dumping dataset..... | {datetime.now()}')
            duplicate_records, clean_data, record_duplicates = set([]), [], 0
            for data in data_json:
                if 'sourceText' not in data.keys() or 'targetText' not in data.keys():
                    continue
                data['sourceText'] = str(data['sourceText']).strip()
                data['targetText'] = str(data['targetText']).strip()
                tup = (data['sourceText'], data['targetText'])
                if tup in duplicate_records:
                    record_duplicates += 1
                    continue
                else:
                    duplicate_records.add(tup)
                    clean_data.append(data)
            duplicate_records.clear()
            log.info(f'Actual Data: {len(data_json)}, Clean Data: {len(clean_data)} | {datetime.now()}')
            u_count, i_count, = 0, 0
            if clean_data:
                func = partial(self.enriched_and_persist, request=request)
                no_of_m3_process = request["processors"]
                pool_enrichers = multiprocessing.Pool(no_of_m3_process)
                enrichment_processors = pool_enrichers.map_async(func, clean_data).get()
                for result in enrichment_processors:
                    if result:
                        if result[0]:
                            if "INSERT" == result[0]:
                                i_count += 1
                            if "UPDATE" == result[0]:
                                u_count += 1
                    else:
                        duplicates += 1
                pool_enrichers.close()
            log.info(f'Done! -- UPDATES: {u_count}, INSERTS: {i_count}, "DUPLICATES": {duplicates} | {datetime.now()}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"message": 'loaded dataset to DB-M3', "status": "SUCCESS", "total": total, "clean": len(clean_data), "updates":u_count,
                "inserts": i_count, "duplicates": duplicates, "recordDuplicates": record_duplicates}

    def get_tags(self, d):
        for v in d.values():
            if isinstance(v, dict):
                yield from self.get_tags(v)
            elif isinstance(v, list):
                for entries in v:
                    yield entries
            else:
                yield v

    def enriched_and_persist(self, data, request):
        append_record = None
        src_hash = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
        tgt_hash = str(hashlib.sha256(data["targetText"].encode('utf-16')).hexdigest())
        record = self.get_dataset_internal({"tags": [src_hash]})
        if record:
            record = record[0]
            if src_hash in record["tags"] and tgt_hash in record["tags"]:
                log.info(f'DUPLICATE ---- DATA: {data}, RECORD: {record} | {datetime.now()}')
                return None
            else:
                append_record = record
        target = {
            "id": str(uuid.uuid4()),
            "targetText": data["targetText"],
            "alignmentScore": random.uniform(0, 1),
            "targetLanguage": request["details"]["targetLanguage"],
            "targetTextHash": tgt_hash,
            "sourceRef": src_hash,
            "submitter": request["submitter"],
            "contributors": request["contributors"],
        }
        if 'translator' in data.keys():
            target["translator"] = data["translator"]
        if append_record:
            append_record["targets"].append(target)
            tags_dict = {
                "tgtHash": tgt_hash, "lang": request["details"]["targetLanguage"],
                "collectionMode": request["details"]["collectionMode"],
                "domain": request["details"]["domain"], "licence": request["details"]["licence"]
            }
            append_record["tags"].extend(list(self.get_tags(tags_dict)))
            self.update(append_record)
            return "UPDATE", 0
        else:
            tags_dict = {
                "srcHash": src_hash, "tgtHash": tgt_hash, "tgtLang": request["details"]["targetLanguage"],
                "collectionMode": request["details"]["collectionMode"], "srcLang": request["details"]["sourceLanguage"],
                "domain": request["details"]["domain"], "licence": request["details"]["licence"]
            }
            tags = list(self.get_tags(tags_dict))
            record = {
                "id": str(uuid.uuid4()), "sourceTextHash": src_hash, "sourceText": data["sourceText"],
                "sourceLanguage": request["details"]["sourceLanguage"],
                "submitter": request["submitter"], "contributors": request["contributors"],
                "targets": [target], "tags": tags
            }
            self.insert(record)
            return "INSERT", 0

    def get_dataset_internal(self, query):
        try:
            db_query = {}
            if "tags" in query.keys():
                db_query["tags"] = query["tags"]
            data = self.search(db_query, True)
            if data:
                return data[0]
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    def get_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
        try:
            db_query = {}
            score_query = {}
            if 'minScore' in query.keys():
                score_query["$gte"] = query["minScore"]
            if 'maxScore' in query.keys():
                score_query["$lte"] = query["maxScore"]
            if score_query:
                db_query["scoreQuery"] = {"targets.alignmentScore": score_query}
            if 'score' in query.keys():
                db_query["scoreQuery"] = {"targets.alignmentScore": query["score"]}
            tags = []
            if 'srcLang' in query.keys():
                db_query["srcLang"] = query["srcLang"]
            if 'tgtLang' in query.keys():
                db_query["tgtLang"] = query["tgtLang"]
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'licence' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'srcText' in query.keys():
                src_hash = str(hashlib.sha256(query["srcText"].encode('utf-16')).hexdigest())
                tags.append(src_hash)
            if tags:
                db_query["tags"] = tags
            if 'groupBy' in query.keys():
                db_query["groupBy"] = True
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            data = self.search(db_query, False)
            result, query, count = data[0], data[1], data[2]
            if count > 30:
                result = result[:30]
            log.info(f'Result count: {count} | {datetime.now()}')
            log.info(f'Done! | {datetime.now()}')
            return {"count": count, "query": query, "dataset": result}
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    ####################### DB ##############################

    def set_mongo_cluster(self):
        if "localhost" not in mongo_server_host:
            log.info(f'Setting the Mongo M3 Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_m3_db)
            ulca_db = client[mongo_ulca_m3_db]
            ulca_col = ulca_db[mongo_ulca_dataset_m3_col]
            ulca_col.create_index([("tags", -1)])
            db = client.admin
            db.command('enableSharding', mongo_ulca_m3_db)
            key = OrderedDict([("_id", "hashed")])
            db.command({'shardCollection': f'{mongo_ulca_m3_db}.{mongo_ulca_dataset_m3_col}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB M3.... | {datetime.now()}')
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_m3_db)
            ulca_db = client[mongo_ulca_m3_db]
            ulca_col = ulca_db[mongo_ulca_dataset_m3_col]
            ulca_col.create_index([("tags", -1)])
            log.info(f'Done! | {datetime.now()}')

    def instantiate(self):
        client = pymongo.MongoClient(mongo_server_host)
        db = client[mongo_ulca_m3_db]
        mongo_instance = db[mongo_ulca_dataset_m3_col]
        return mongo_instance

    def get_mongo_instance(self):
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert(data)
        return 1

    def update(self, data):
        col = self.get_mongo_instance()
        bulk = col.initialize_unordered_bulk_op()
        bulk.find({'id': data["id"]}).update({'$set': {'targets': data["targets"], "tags": data["tags"]}})
        bulk.execute()
        return 1

    # Searches the object into mongo collection
    def search(self, query, internal):
        result, res, res_count, pipeline, langs = [], None, 0, [], []
        try:
            col = self.get_mongo_instance()
            pipeline = []
            if 'srcLang' in query.keys() and 'tgtLang' in query.keys():
                langs.append(query["srcLang"])
                langs.extend(query["tgtLang"])
                pipeline.append({"$unwind": {"path": "$targets"}})
                pipeline.append({"$match": {"$and": [{"sourceLanguage": {"$in": langs}}, {"targets.targetLanguage": {"$in": langs}}]}})
            elif 'srcLang' in query.keys():
                pipeline.append({"$unwind": {"path": "$targets"}})
                pipeline.append({"$match": {"$or": [{"sourceLanguage": query["srcLang"]}, {"targets.targetLanguage": query["srcLang"]}]}})
            if "tags" in query.keys():
                if internal:
                    pipeline.append({"$match": {"tags": {"$in": query["tags"]}}})
                else:
                    pipeline.append({"$match": {"tags": {"$all": query["tags"]}}})
            if "scoreQuery" in query.keys():
                pipeline.append({"$match": query["scoreQuery"]})
            if 'groupBy' in query.keys():
                pipeline.append({"$group": {"_id": {"sourceHash": "$sourceTextHash"}, "count": {"$sum": 1}}})
                if 'countOfTranslations' in query.keys():
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", query["countOfTranslations"]]}, "$_id.sourceHash", "$$REMOVE"]}}})
                else:
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", 1]}, "$_id.sourceHash", "$$REMOVE"]}}})
                pipeline.append({"$project": {"_id": 1}})
                res = col.aggregate(pipeline, allowDiskUse=True)
                temp = []
                for rec in res:
                    if rec:
                        temp.append(rec)
                if not temp:
                    pipeline[1] = {"$match": {"targets.targetLanguage": {"$in": langs}}}
                    res = col.aggregate(pipeline, allowDiskUse=True)
                else:
                    res = temp
            else:
                pipeline.append({"$project": {"_id": 0}})
                res = col.aggregate(pipeline, allowDiskUse=True)
                temp = []
                for rec in res:
                    if rec:
                        temp.append(rec)
                if not temp:
                    pipeline[1] = {"$match": {"targets.targetLanguage": {"$in": langs}}}
                    res = col.aggregate(pipeline, allowDiskUse=True)
                else:
                    res = temp
            if 'groupBy' in query.keys():
                if res:
                    hashes = []
                    for record in res:
                        if record:
                            if record["_id"]:
                                hashes.append(record["_id"])
                    if hashes:
                        res_count = len(hashes)
                        in_query = {"sourceTextHash": {"$in": hashes}}
                        res = col.find(in_query, {"_id": False})
                    map = {}
                    if not res:
                        return result, pipeline, res_count
                    for record in res:
                        if record:
                            if record["sourceTextHash"] in map.keys():
                                data_list = map[record["sourceTextHash"]]
                                data_list.append(record)
                                map[record["sourceTextHash"]] = data_list
                            else:
                                map[record["sourceTextHash"]] = [record]
                    result = list(map.values())
                    result, res_count = self.post_process_groupby(query, result)
            elif 'srcLang' in query.keys() or 'tgtLang' in query.keys():
                if res:
                    map = {}
                    for record in res:
                        if record:
                            if record["sourceTextHash"] in map.keys():
                                data = map[record["sourceTextHash"]]
                                data["targets"].append(record["targets"])
                                map[record["sourceTextHash"]] = data
                            else:
                                targets = [record["targets"]]
                                record["targets"] = targets
                                map[record["sourceTextHash"]] = record
                    result = list(map.values())
                    result = self.post_process(query, result)
                    res_count = len(result)
            else:
                if res:
                    for record in res:
                        if record:
                            result.append(record)
                res_count = len(result)
        except Exception as e:
            log.exception(e)
        return result, pipeline, res_count

    def post_process(self, query, res):
        src_lang, tgt_lang = None, None
        if 'srcLang' in query.keys():
            src_lang = query["srcLang"]
        if 'tgtLang' in query.keys():
            tgt_lang = query["tgtLang"]
        result_set, res_count = [], 0
        for record in res:
            result_array = []
            result = {}
            try:
                if src_lang == record["sourceLanguage"]:
                    result["sourceText"] = record["sourceText"]
                elif record["sourceLanguage"] in tgt_lang:
                    result["targetText"] = record["targetText"]
                    result["alignmentScore"] = record["alignmentScore"]
                if result:
                    targets = record["targets"]
                    for target in targets:
                        if 'sourceText' in result.keys():
                            if target["targetLanguage"] in tgt_lang:
                                result["targetText"] = target["targetText"]
                                result["alignmentScore"] = target["alignmentScore"]
                                if len(result.keys()) >= 2:
                                    result_array.append(result)
                        elif 'targetText' in result.keys():
                            if target["sourceLanguage"] in tgt_lang:
                                result["sourceText"] = target["targetText"]
                                result["alignmentScore"] = target["alignmentScore"]
                                if len(result.keys()) >= 2:
                                    result_array.append(result)
                else:
                    target_combinations = list(itertools.combinations(record["targets"], 2))
                    for combination in target_combinations:
                        if src_lang == combination[0]["targetLanguage"]:
                            result["sourceText"] = combination[0]["targetText"]
                        elif combination[0]["targetLanguage"] in tgt_lang:
                            result["targetText"] = combination[0]["targetText"]
                            result["alignmentScore"] = combination[0]["alignmentScore"]
                        if result:
                            if src_lang == combination[1]["targetLanguage"]:
                                result["sourceText"] = combination[1]["targetText"]
                            elif combination[1]["targetLanguage"] in tgt_lang:
                                result["targetText"] = combination[1]["targetText"]
                                result["alignmentScore"] = combination[1]["alignmentScore"]
                            if len(result.keys()) >= 2:
                                result_array.append(result)
                            else:
                                result = {}
                                continue
                        else:
                            continue
            except Exception as e:
                continue
            if result_array:
                res_count += len(result_array)
                result_set.append(result_array)
        return result_set


    def post_process_groupby(self, query, res):
        langs, res_count = [], 0
        if 'srcLang' in query.keys():
            langs.append(query["srcLang"])
        if 'tgtLang' in query.keys():
            langs.extend(query["tgtLang"])
        result_set = []
        for record in res:
            src_result_array = []
            try:
                for each_record in record:
                    result_array = []
                    sentences = []
                    try:
                        for target in each_record["targets"]:
                            if target["targetLanguage"] in langs:
                                sentences.append(target)
                        if each_record["sourceLanguage"] in langs:
                            source = {"targetText": each_record["sourceText"], "targetLanguage": each_record["sourceLanguage"],
                                      "alignmentScore": 0}
                            sentences.append(source)
                        if sentences:
                            target_combinations = list(itertools.combinations(sentences, 2))
                            for combination in target_combinations:
                                result = {"sourceText": combination[0]["targetText"],
                                          "targetText": combination[1]["targetText"],
                                          "alignmentScore": combination[1]["alignmentScore"]}
                                result_array.append(result)
                            if result_array:
                                res_count += 1
                                src_result_array.append(result_array)
                    except Exception as e:
                        log.exception(e)
                        continue
            except Exception as e:
                log.exception(e)
            if src_result_array:
                result_set.append(src_result_array)
        return result_set, res_count


    def post_process_xx(self, query, res):
        langs = []
        if 'srcLang' in query.keys():
            langs.append(query["srcLang"])
        if 'tgtLang' in query.keys():
            langs.extend(query["tgtLang"])
        result_set, res_count, result_array = [], 0, []
        for record in res:
            sentences = []
            try:
                for target in record["targets"]:
                    if target["targetLanguage"] in langs:
                        sentences.append(target)
                if record["sourceLanguage"] in langs:
                    source = {"targetText": record["sourceText"], "targetLanguage": record["sourceLanguage"], "alignmentScore": 0}
                    sentences.append(source)
                if sentences:
                    target_combinations = list(itertools.combinations(sentences, 2))
                    for combination in target_combinations:
                        result = {"sourceText": combination[0]["targetText"], "targetText": combination[1]["targetText"],
                                  "alignmentScore": combination[1]["alignmentScore"]}
                        result_array.append(result)
            except Exception as e:
                continue
            if result_array:
                res_count += len(result_array)
                result_set.append(result_array)
        return result_set


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