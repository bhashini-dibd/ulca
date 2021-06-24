from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
import config
import logging


logging.basicConfig(filename='info.log',level=logging.DEBUG,
                    format='%(asctime)s %(levelname)s %(name)s %(threadName)s : %(message)s')

log                 =   logging.getLogger('file')
file_store_app      =   Flask(__name__)


if config.ENABLE_CORS:
    cors    =   CORS(file_store_app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        file_store_app.register_blueprint(blueprint, url_prefix=config.CONTEXT_PATH)


if __name__ == "__main__":
    file_store_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    
