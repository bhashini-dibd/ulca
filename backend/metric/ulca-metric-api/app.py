from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import src.services.metriccronjob 
from src import routes
import logging
from logging.config import dictConfig
import config
import threading
from src.services.mismatchcron import AlertCronProcessor

log = logging.getLogger('file')

app = Flask(__name__)

# app.config.update(config.MAIL_SETTINGS)

if config.ENABLE_CORS:
    cors    = CORS(app, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        app.register_blueprint(blueprint, url_prefix=config.API_URL_PREFIX)



def start_cron():
    with app.test_request_context():
        metriccron  =   src.services.metriccronjob.CronProcessor(threading.Event())
        metriccron.start()
        alertcron   =   AlertCronProcessor(threading.Event())
        alertcron.start()
if __name__ == "__main__":
    log.info("starting module")
    start_cron()
    app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    

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