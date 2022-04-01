from flask import Flask
from flask.blueprints import Blueprint
import routes
import config
import logging
from logging.config import dictConfig

log  =   logging.getLogger('file')

asr_create_benchmark_app  = Flask(__name__)

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        asr_create_benchmark_app.register_blueprint(blueprint, url_prefix=config.API_URL_PREFIX)

if __name__ == "__main__":
    log.info("Starting ASR Benchmark Dataset Creation service")
    asr_create_benchmark_app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)


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



