from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
from src import routes
import logging
import config
import threading
import time
from src.utilities.app_context import LOG_WITHOUT_CONTEXT

logging.basicConfig(filename='info.log',
level=logging.DEBUG,
format='%(asctime)s %(levelname)s %(name)s %(threadName)s : %(message)s')

log = logging.getLogger('file')

flask_app = Flask(__name__)

if config.ENABLE_CORS:
    cors    = CORS(flask_app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        flask_app.register_blueprint(blueprint, url_prefix=config.API_URL_PREFIX)

@flask_app.route(config.API_URL_PREFIX)
def info():
    return "Welcome to Dataset APIs"

if __name__ == "__main__":
    log.info("starting module")
    flask_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
