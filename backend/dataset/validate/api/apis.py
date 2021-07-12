from flask import Flask, jsonify, request

from service.parallel import ParallelValidate
from service.monolingual import MonolingualValidate
from service.asr import ASRValidate
from service.ocr import OCRValidate
from service.asr_unlabeled import ASRUnlabeledValidate
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_asr_unlabeled

ulca_dataset_validate = Flask(__name__)


# REST endpoint to test validation services
@ulca_dataset_validate.route('/ulca/dataset/v0/validate', methods=["POST"])
def execute_validation():
    req_data = request.get_json()
    dataset_type = req_data["datasetType"]


    if dataset_type == dataset_type_parallel:
        p_service = ParallelValidate()
        res = p_service.execute_validation_pipeline(req_data)

    if dataset_type == dataset_type_asr:
        asr_service = ASRValidate()
        res = asr_service.execute_validation_pipeline(req_data)

    if dataset_type == dataset_type_ocr:
        ocr_service = OCRValidate()
        res = ocr_service.execute_validation_pipeline(req_data)

    if dataset_type == dataset_type_monolingual:
        m_service = MonolingualValidate()
        res = m_service.execute_validation_pipeline(req_data)

    if dataset_type == dataset_type_asr_unlabeled:
        asr_ul_service = ASRUnlabeledValidate()
        res = asr_ul_service.execute_validation_pipeline(req_data)

    return jsonify(res)