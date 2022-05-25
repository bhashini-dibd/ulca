import uuid

from flask import Flask, jsonify, request

from service.parallel import ParallelService
from service.datasetservice import DatasetService
from service.monolingual import MonolingualService
from service.asr import ASRService
from service.tts import TTSService
from service.ocr import OCRService
from service.asrunlabeled import ASRUnlabeledService
from service.transliteration import TransliterationService
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_tts, dataset_type_transliteration
ulca_dataset_publish = Flask(__name__)


# REST endpoint to fetch configs
@ulca_dataset_publish.route('/ulca/publish/dataset/v0/load', methods=["POST"])
def insert_dataset():
    req_criteria, data = request.get_json(), {}
    p_service, m_service, a_service, o_service, au_service, tts_service, trans_service = ParallelService(), MonolingualService(), \
                                                                                         ASRService(), OCRService(), \
                                                                                         ASRUnlabeledService(), TTSService(), TransliterationService()
    req_criteria["record"]["id"] = str(uuid.uuid4())
    if req_criteria["datasetType"] == dataset_type_parallel:
        data = p_service.load_parallel_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_ocr:
        data = o_service.load_ocr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_asr:
        data = a_service.load_asr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_monolingual:
        data = m_service.load_monolingual_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_tts:
        data = tts_service.load_tts_dataset(req_criteria)
    if data["datasetType"] == dataset_type_transliteration:
        data = trans_service.load_transliteration_dataset(data)
    return jsonify(data), 200


# REST endpoint to fetch configs
@ulca_dataset_publish.route('/ulca/publish/dataset/v0/search', methods=["POST"])
def search_dataset():
    req_criteria, data = request.get_json(), {}
    p_service, m_service, a_service, o_service, au_service, tts_service, trans_service = ParallelService(), MonolingualService(), \
                                                                                         ASRService(), OCRService(), \
                                                                                         ASRUnlabeledService(), TTSService(), TransliterationService()
    if req_criteria["datasetType"] == dataset_type_parallel:
        data = p_service.get_parallel_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_ocr:
        data = o_service.get_ocr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_asr:
        data = a_service.get_asr_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_monolingual:
        data = m_service.get_monolingual_dataset(req_criteria)
    if req_criteria["datasetType"] == dataset_type_tts:
        data = tts_service.get_tts_dataset(req_criteria)
    if data["datasetType"] == dataset_type_transliteration:
        data = trans_service.get_transliteration_dataset(data)
    response = {"dataset": data}
    return jsonify(response), 200


# REST endpoint to reset the Dataset DB
@ulca_dataset_publish.route('/ulca/publish/v0/cluster/set', methods=["POST"])
def set_cluster():
    service = DatasetService()
    req_criteria = request.get_json()
    service.set_dataset_db(req_criteria)
    response = {"message": "DONE"}
    return jsonify(response), 200