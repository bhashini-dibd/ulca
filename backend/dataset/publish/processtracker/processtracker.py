import logging
import uuid
from datetime import datetime
from logging.config import dictConfig

from configs.configs import pt_search_tool, pt_delete_tool, pt_inprogress_status, pt_success_status, pt_failed_status
from .ptrepo import PTRepo

log = logging.getLogger('file')

repo = PTRepo()
srn_map = {}

class ProcessTracker:
    def __init__(self):
        pass

    def update_task_details(self, data):
        global srn_map
        if data["status"] == "SUCCESS":
            repo.redis_key_inc(data["serviceRequestNumber"], False)
        else:
            repo.redis_key_inc(data["serviceRequestNumber"], True)

    def task_event_search(self, data, error):
        log.info(f'Publishing pt event for SEARCH -- {data["serviceRequestNumber"]}')
        task_event = self.search_task_event(data, pt_search_tool)
        try:
            if task_event:
                task_event["details"] = data
                if error:
                    task_event["status"] = pt_failed_status
                    task_event["error"] = error
                else:
                    task_event["status"] = pt_success_status
                task_event["lastModifiedTime"] = str(datetime.now())
                task_event["endTime"] = task_event["lastModifiedTime"]
                repo.update(task_event)
            else:
                task_event = {"id": str(uuid.uuid4()), "tool": pt_search_tool,
                              "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                              "startTime": str(datetime.now()), "lastModifiedTime": str(datetime.now()),
                              "details": {"count": 0}}
                repo.insert(task_event)
            return
        except Exception as e:
            log.exception(f'There was an exception while fetching records: {e}', e)
            error = {"code": "EXCEPTION", "serviceRequestNumber": data["serviceRequestNumber"], "message": f'There was an exception while fetching records: {e}'}
            task_event["status"], task_event["error"] = pt_failed_status, error
            task_event["endTime"] = task_event["lastModifiedTime"] = str(datetime.now())
            repo.update(task_event)
            return None

    def task_event_delete(self, data, error):
        log.info(f'Publishing pt event for DELETE -- {data["serviceRequestNumber"]}')
        try:
            task_event = self.search_task_event(data, pt_delete_tool)
            if task_event:
                task_event["details"] = data
                if error:
                    task_event["status"] = pt_failed_status
                    task_event["error"] = error
                else:
                    task_event["status"] = pt_success_status
                task_event["lastModifiedTime"] = str(datetime.now())
                task_event["endTime"] = task_event["lastModifiedTime"]
                repo.update(task_event)
            else:
                task_event = {"id": str(uuid.uuid4()), "tool": pt_delete_tool, "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                              "startTime": str(datetime.now()), "lastModifiedTime": str(datetime.now())}
                repo.insert(task_event)
            return
        except Exception as e:
            log.exception(e)
            return None

    def search_task_event(self, data, tool):
        query = {"serviceRequestNumber": data["serviceRequestNumber"], "tool": tool}
        exclude = {"_id": False}
        result = repo.search(query, exclude, None, None)
        if result:
            return result[0]
        return result


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