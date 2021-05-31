from flask import Flask, jsonify, request

from service.parallel import ParallelService
from service.parallel import DatasetService
ulcdatastoreapp = Flask(__name__)


# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca/parallel-dataset/v0/load', methods=["POST"])
def insert_dataset():
    req_criteria = request.get_json()
    service = ParallelService()
    response = service.load_dataset(req_criteria)
    return jsonify(response), 200

# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca/parallel-dataset/v0/search', methods=["POST"])
def search_dataset():
    req_criteria = request.get_json()
    service = ParallelService()
    data = service.get_dataset(req_criteria)
    response = {"dataset": data}
    return jsonify(response), 200

# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca/dataset/v0/cluster/set', methods=["POST"])
def set_cluster():
    service = DatasetService()
    req_criteria = request.get_json()
    service.set_dataset_db(req_criteria)
    response = {"message": "DONE"}
    return jsonify(response), 200
