from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
import config
import logging

log = logging.getLogger('file')
compute_app  = Flask(__name__)


if config.ENABLE_CORS:
    cors    = CORS(compute_app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        compute_app.register_blueprint(blueprint, url_prefix=config.API_URL_PREFIX)


if __name__ == "__main__":
    compute_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    
