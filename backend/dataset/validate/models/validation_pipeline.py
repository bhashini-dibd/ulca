from configs.configs import parallel_corpus_config_path, asr_config_path, ocr_config_path, monolingual_config_path, asr_unlabeled_config_path
import json
import validations as validator_package
from validations.basic_schema_check import BasicSchemaCheck
import os

class ValidationPipeline:

    __p_inst = None

    def __init__(self):
        if ValidationPipeline.__p_inst != None:
            raise Exception("Singleton class")
        else:
            ValidationPipeline.__p_inst = self

    @staticmethod
    def getInstance():
        if ValidationPipeline.__p_inst == None:
            ValidationPipeline()

        return ValidationPipeline.__p_inst

    @property
    def parallel_pipeline(self):
        return self._parallel_pipeline

    @parallel_pipeline.setter
    def parallel_pipeline(self, value):
        self._parallel_pipeline = value

    @property
    def asr_pipeline(self):
        return self._asr_pipeline

    @asr_pipeline.setter
    def asr_pipeline(self, value):
        self._asr_pipeline = value

    @property
    def ocr_pipeline(self):
        return self._ocr_pipeline

    @ocr_pipeline.setter
    def ocr_pipeline(self, value):
        self._ocr_pipeline = value

    @property
    def monolingual_pipeline(self):
        return self._monolingual_pipeline

    @monolingual_pipeline.setter
    def monolingual_pipeline(self, value):
        self._monolingual_pipeline = value

    @property
    def asr_unlabeled_pipeline(self):
        return self._asr_unlabeled_pipeline

    @asr_unlabeled_pipeline.setter
    def asr_unlabeled_pipeline(self, value):
        self._asr_unlabeled_pipeline = value

    def getValidators(self, filepath):
        with open(filepath) as v_file:
            v_list = json.loads(v_file.read())
            return v_list

    def initiate_validators(self, filepath, validation_ptr):
        p_list = self.getValidators(filepath)
        for i in p_list:
            if i["active"] != "True":
                continue
            test = i["test_name"]
            v_class = vars(validator_package)[test]
            # v_class = getattr(importlib.import_module('validations'), i)
            v_inst = v_class()
            validation_ptr = validation_ptr.execute_next(v_inst)
    
    def loadValidators(self):

        # load validation pipeline for parallel corpus
        self.parallel_pipeline = BasicSchemaCheck()
        validation_p = self.parallel_pipeline
        p_filepath = os.path.abspath(os.path.join(os.curdir, parallel_corpus_config_path))
        self.initiate_validators(p_filepath, validation_p)

        # load validation pipeline for asr
        self.asr_pipeline = BasicSchemaCheck()
        validation_p = self.asr_pipeline
        p_filepath = os.path.abspath(os.path.join(os.curdir, asr_config_path))
        self.initiate_validators(p_filepath, validation_p)

        # load validation pipeline for ocr
        self.ocr_pipeline = BasicSchemaCheck()
        validation_p = self.ocr_pipeline
        p_filepath = os.path.abspath(os.path.join(os.curdir, ocr_config_path))
        self.initiate_validators(p_filepath, validation_p)

        # load validation pipeline for monolingual
        self.monolingual_pipeline = BasicSchemaCheck()
        validation_p = self.monolingual_pipeline
        p_filepath = os.path.abspath(os.path.join(os.curdir, monolingual_config_path))
        self.initiate_validators(p_filepath, validation_p)

        # load validation pipeline for asr unlabeled
        self.asr_unlabeled_pipeline = BasicSchemaCheck()
        validation_p = self.asr_unlabeled_pipeline
        p_filepath = os.path.abspath(os.path.join(os.curdir, asr_unlabeled_config_path))
        self.initiate_validators(p_filepath, validation_p)


    def runParallelValidators(self, record):
        return self.parallel_pipeline.execute(record)

    def runAsrValidators(self, record):
        return self.asr_pipeline.execute(record)

    def runOcrValidators(self, record):
        return self.ocr_pipeline.execute(record)

    def runMonolingualValidators(self, record):
        return self.monolingual_pipeline.execute(record)

    def runAsrUnlabeledValidators(self, record):
        return self.asr_unlabeled_pipeline.execute(record)