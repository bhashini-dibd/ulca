from flask import Flask, jsonify, request
from events.error import ErrorEvent

ulca_error_consumer = Flask(__name__)

# REST endpoint to fetch error report
@ulca_error_consumer.route('/ulca/error-consumer/v0/error/report', methods=["POST"])
def get_error_report():
    service = ErrorEvent()
    req_criteria = request.get_json()
    result = service.get_error_report(req_criteria["serviceRequestNumber"], False)
    return jsonify(result), 200


@ulca_error_consumer.route('/ulca/error-consumer/v0/error/fetch-report', methods=["POST"])
def get_error_report():
    service = ErrorEvent()
    req_criteria = request.get_json()
    result = service.get_error_report(req_criteria["serviceRequestNumber"], False)
    return jsonify(result), 200
    

@ulca_error_consumer.route('/ulca/error-consumer/health', methods=["GET"])
def get():
    response = {"code": "200", "status": "ACTIVE"}
    return jsonify(response)


