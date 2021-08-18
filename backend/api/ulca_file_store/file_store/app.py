from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
import config
import logging
from logging.config import dictConfig

dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})
log1 = logging.getLogger("azure.core.pipeline.policies.http_logging_policy")
log1.setLevel(logging.WARNING)
log  =   logging.getLogger('file')



file_store_app      =   Flask(__name__)


if config.ENABLE_CORS:
    cors    =   CORS(file_store_app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        file_store_app.register_blueprint(blueprint, url_prefix=config.CONTEXT_PATH)


if __name__ == "__main__":
    log.info("File Store service has started")
    file_store_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    
