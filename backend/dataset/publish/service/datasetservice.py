import logging
from logging.config import dictConfig
from repository.parallel import ParallelRepo
from repository.datasetrepo import DatasetRepo
from repository.asr import ASRRepo
from repository.ocr import OCRRepo
from repository.monolingual import MonolingualRepo
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual



log = logging.getLogger('file')

mongo_instance = None
parallelrepo = ParallelRepo()
datasetrepo = DatasetRepo()
asrrepo = ASRRepo()
ocrrepo = OCRRepo()
monorepo = MonolingualRepo()


class DatasetService:
    def __init__(self):
        pass

    def set_dataset_db(self, request):
        log.info("Setting Dataset DB.....")
        if request["all"]:
            datasetrepo.set_dataset_db()
            parallelrepo.set_parallel_collection()
            asrrepo.set_asr_collection()
            ocrrepo.set_ocr_collection()
            monorepo.set_monolingual_collection()
        elif request["col"] == dataset_type_parallel:
            parallelrepo.set_parallel_collection()
        elif request["col"] == dataset_type_asr:
            asrrepo.set_asr_collection()
        elif request["col"] == dataset_type_ocr:
            ocrrepo.set_ocr_collection()
        elif request["col"] == dataset_type_monolingual:
            monorepo.set_monolingual_collection()

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