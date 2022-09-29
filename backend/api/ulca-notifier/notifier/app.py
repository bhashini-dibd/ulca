from flask import Flask
import logging
from multiprocessing import Process
import kafkawrapper
from configs.configs import app_host, app_port,ENABLE_CORS,context_path, MAIL_SETTINGS
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
log = logging.getLogger('file')
from flask_mail import Mail

ulca_notifier_service = Flask(__name__)

ulca_notifier_service.config.update(MAIL_SETTINGS)
#creating an instance of Mail class
mail=Mail(ulca_notifier_service)

if ENABLE_CORS:
    cors    =   CORS(ulca_notifier_service, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        ulca_notifier_service.register_blueprint(blueprint, url_prefix=context_path)

def start_consumer():
    with ulca_notifier_service.test_request_context():
        try:
            #starting kafka consumers
            notifier_consumer = Process(target=kafkawrapper.consumer_to_notify)
            notifier_consumer.start()
        except Exception as e:
            log.exception(f'Exception while starting kafka consumer (ulca-notifier): {str(e)}')


if __name__ == '__main__':
    log.info("Notifier service started <<<<<<<<<<<<>>>>>>>>>>>")
    start_consumer()
    ulca_notifier_service.run(host=app_host, port=app_port, threaded=True)