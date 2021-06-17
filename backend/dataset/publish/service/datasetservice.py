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