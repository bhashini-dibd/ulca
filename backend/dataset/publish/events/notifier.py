import logging
from configs.configs import notifier_input_topic, notifier_search_complete_status
from kafkawrapper.producer import Producer
from utils.datasetutils import DatasetUtils


log = logging.getLogger('file')
mongo_instance = None
prod = Producer()
utils = DatasetUtils()


class NotifierEvent:
    def __init__(self):
        pass

    # Method to post notification events to the notifier consumer
    def create_notifier_event(self, srn, user_id, res_count):
        try:
            event = {"event": notifier_search_complete_status,
                     "entityID": srn,
                     "userID": user_id,
                     "details": {
                         "resultCount": res_count
                     }
                     }
            prod.produce(event, notifier_input_topic, None)
        except Exception as e:
            log.exception(e)