from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
import config
import logging
log  =   logging.getLogger('file')



mdms_app      =   Flask(__name__)


if config.ENABLE_CORS:
    cors    =   CORS(mdms_app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        mdms_app.register_blueprint(blueprint, url_prefix=config.CONTEXT_PATH)


if __name__ == "__main__":
    log.info("File Store service has started")
    mdms_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    
