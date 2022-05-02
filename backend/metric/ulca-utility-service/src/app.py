from flask import Flask
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
import config
import logging
from flask_mail import Mail
from services.status_updater_cron import StatusCronProcessor
import config
log = logging.getLogger('file')
from logging.config import dictConfig
import threading
notify  = Flask(__name__)

notify.config.update(config.MAIL_SETTINGS)
#creating an instance of Mail class
mail=Mail(notify)

if config.ENABLE_CORS:
    cors    = CORS(notify, resources={r"/api/*": {"origins": "*"}})

def start_cron():
    with notify.test_request_context():
        statcron    =    StatusCronProcessor(threading.Event())
        statcron.start()

#def start_mism():
#    with notify.test_request_context():
#        statmims    =    NotifierResource(threading.Event())
#        statmims.start()

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        notify.register_blueprint(blueprint, url_prefix=config.API_URL_PREFIX)


if __name__ == "__main__":
    start_cron()
    #start_mism()
    log.info('here')
    notify.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
    
# Log config
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