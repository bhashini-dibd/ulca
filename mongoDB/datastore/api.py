from flask import Flask, jsonify, request

from datastore import Datastore

ulcdatastoreapp = Flask(__name__)


# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca-evaluation/datastore/v0/dataset/load', methods=["POST"])
def insert_dataset():
    req_criteria = request.get_json()
    service = Datastore()
    response = service.load_dataset(req_criteria)
    return jsonify(response), 200


# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca-evaluation/datastore/v0/dataset/search', methods=["POST"])
def search_dataset():
    req_criteria = request.get_json()
    service = Datastore()
    data = service.get_dataset(req_criteria)
    response = {"dataset": data}
    return jsonify(response), 200

# REST endpoint to fetch configs
@ulcdatastoreapp.route('/ulca-evaluation/datastore/v0/cluster/set', methods=["GET"])
def set_cluster():
    service = Datastore()
    service.set_mongo_cluster(True)
    response = {"message": "DONE"}
    return jsonify(response), 200