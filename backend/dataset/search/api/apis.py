from flask import Flask, jsonify, request
from service.asr import ASRService
from service.ocr import OCRService
from service.monolingual import MonolingualService
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual

from service.parallel import ParallelService
ulca_dataset_search = Flask(__name__)


# REST endpoint to fetch configs
@ulca_dataset_search.route('/ulca/dataset/v0/search', methods=["POST"])
def search_dataset():
    req_criteria, data = request.get_json(), {}
    p_service, m_service, a_service, o_service = ParallelService(), MonolingualService(), ASRService(), OCRService()
    if req_criteria["datasetType"] == dataset_type_parallel:
        data = p_service.get_parallel_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_ocr:
        data = o_service.get_ocr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_asr:
        data = a_service.get_asr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_monolingual:
        data = m_service.get_monolingual_dataset(req_criteria)
    response = {"dataset": data}
    return jsonify(response), 200
