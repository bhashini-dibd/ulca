from flask import Flask, jsonify, request

from service.parallel import ParallelService
from service.parallel import DatasetService
ulca_dataset_publish = Flask(__name__)


# REST endpoint to fetch configs
@ulca_dataset_publish.route('/ulca/parallel-dataset/v0/load', methods=["POST"])
def insert_dataset():
    req_criteria = request.get_json()
    service = ParallelService()
    response = service.load_parallel_dataset(req_criteria)
    return jsonify(response), 200

# REST endpoint to fetch configs
@ulca_dataset_publish.route('/ulca/dataset/v0/cluster/set', methods=["POST"])
def set_cluster():
    service = DatasetService()
    req_criteria = request.get_json()
    service.set_dataset_db(req_criteria)
    response = {"message": "DONE"}
    return jsonify(response), 200
