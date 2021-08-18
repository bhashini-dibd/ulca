import logging
from logging.config import dictConfig
from repository.parallel import ParallelRepo
from repository.datasetrepo import DatasetRepo
from repository.asr import ASRRepo
from repository.ocr import OCRRepo
from repository.monolingual import MonolingualRepo
from repository.asrunlabeled import ASRUnlabeledRepo
from utils.datasetutils import DatasetUtils
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, \
    dataset_type_asr_unlabeled

log = logging.getLogger('file')

mongo_instance = None
parallelrepo = ParallelRepo()
datasetrepo = DatasetRepo()
asrrepo = ASRRepo()
ocrrepo = OCRRepo()
monorepo = MonolingualRepo()
asrunlabeledrepo = ASRUnlabeledRepo()
utils = DatasetUtils()


class DatasetService:
    def __init__(self):
        pass

    '''
    Method to set the Dataset DB up based on the request
    params: request (DB setup request)
    '''
    def set_dataset_db(self, request):
        log.info("Setting Dataset DB.....")
        if request["all"]:
            datasetrepo.set_dataset_db()
            parallelrepo.set_parallel_collection()
            asrrepo.set_asr_collection()
            ocrrepo.set_ocr_collection()
            monorepo.set_monolingual_collection()
            asrunlabeledrepo.set_asr_unlabeled_collection()
        elif request["col"] == dataset_type_parallel:
            parallelrepo.set_parallel_collection()
        elif request["col"] == dataset_type_asr:
            asrrepo.set_asr_collection()
        elif request["col"] == dataset_type_ocr:
            ocrrepo.set_ocr_collection()
        elif request["col"] == dataset_type_monolingual:
            monorepo.set_monolingual_collection()
        elif request["col"] == dataset_type_asr_unlabeled:
            asrunlabeledrepo.set_asr_unlabeled_collection()

    '''
    Method to check and process duplicate records.
    params: data (record to be inserted)
    params: record (duplicate record found in the DB)
    params: metadata (metadata of the record to be inserted)
    '''
    def enrich_duplicate_data(self, data, record, metadata, immutable, updatable, non_tag):
        db_record = {}
        for key in record.keys():
            db_record[key] = record[key]
        found = False
        for key in data.keys():
            if key in updatable:
                if key not in db_record.keys():
                    found = True
                    db_record[key] = data[key]
                else:
                    if db_record[key] != data[key]:
                        found = True
                        db_record[key] = data[key]
                continue
            if key not in immutable:
                if key not in db_record.keys():
                    found = True
                    db_record[key] = [data[key]]
                elif isinstance(data[key], list):
                    val = data[key][0]
                    if isinstance(val, dict):
                        pairs = zip(data[key], db_record[key])
                        if any(x != y for x, y in pairs):
                            found = True
                            db_record[key].extend(data[key])
                    else:
                        for entry in data[key]:
                            if entry not in db_record[key]:
                                found = True
                                db_record[key].append(entry)
                else:
                    if isinstance(db_record[key], list):
                        if data[key] not in db_record[key]:
                            found = True
                            db_record[key].append(data[key])
                    else:
                        if db_record[key] != data[key]:
                            found = True
                            db_record[key] = [db_record[key]]
                            db_record[key].append(data[key])
                            db_record[key] = list(set(db_record[key]))
                        else:
                            db_record[key] = [db_record[key]]
        if found:
            db_record["datasetId"].append(metadata["datasetId"])
            dataset_ids = []
            for entry in db_record["datasetId"]:
                if entry not in dataset_ids:
                    dataset_ids.append(entry)
            db_record["datasetId"] = dataset_ids
            db_record["tags"] = self.get_tags(db_record, non_tag)
            return db_record
        else:
            return False

    '''
    Method to fetch tags for a record
    params: insert_data (record to be used to fetch tags)
    '''
    def get_tags(self, insert_data, non_tag):
        tag_details = {}
        for key in insert_data:
            if key not in non_tag:
                tag_details[key] = insert_data[key]
        tags = list(utils.get_tags(tag_details))
        return list(set(tags))


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