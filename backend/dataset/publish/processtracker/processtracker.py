import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import pt_publish_tool, pt_search_tool, pt_delete_tool, \
    pt_inprogress_status, pt_success_status, pt_failed_status
from .ptrepo import PTRepo

log = logging.getLogger('file')

mongo_instance = None
repo = PTRepo()

class ProcessTracker:
    def __init__(self):
        pass

    def create_task_event(self, data):
        log.info(f'Publishing pt event for SUBMIT -- {data["serviceRequestNumber"]}')
        try:
            task_event = self.search_task_event(data, pt_publish_tool)
            if task_event:
                if task_event["status"] == pt_inprogress_status:
                    return self.update_task_event(data, task_event)
                else:
                    log.error(f'Record received for a {task_event["status"]} SRN -- {data["serviceRequestNumber"]}')
                    return
            task_event = {"id": str(uuid.uuid4()), "tool": pt_publish_tool, "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                          "startTime": str(datetime.now()), "lastModified": str(datetime.now())}
            if data["status"] == "SUCCESS":
                processed_count = [{"type": "success", "count": 1}, {"type": "failed", "typeDetails": {}, "count": 0}]
            else:
                processed_count = [{"type": "failed", "typeDetails": {data["code"]: 1}, "count": 1}, {"type": "success", "count": 0}]
            details = {"currentRecordIndex": data["currentRecordIndex"], "processedCount": processed_count, "timeStamp": str(datetime.now())}
            task_event["details"] = details
            repo.insert(task_event)
            return
        except Exception as e:
            log.exception(e)
            return

    def end_processing(self, data):
        log.error(f'EOF received for SRN -- {data["serviceRequestNumber"]}')
        task_event = self.search_task_event(data, pt_publish_tool)
        if task_event:
            if task_event["status"] == pt_inprogress_status:
                task_event["status"] = pt_success_status
                task_event["lastModified"] = str(datetime.now())
                task_event["endTime"] = task_event["lastModified"]
                repo.update(task_event)
            else:
                log.error(f'EOF received for a {task_event["status"]} SRN -- {data["serviceRequestNumber"]}')
        else:
            log.error(f'EOF received for a non existent SRN -- {data["serviceRequestNumber"]}')
        log.error(f'Done!')
        return

    def update_task_event(self, data, task_event):
        task_event = task_event[0]
        processed = task_event["details"]["processedCount"]
        if data["status"] == "SUCCESS":
            for value in processed:
                if value["type"] == "success":
                    value["count"] += 1
        else:
            found = False
            for value in processed:
                if value["type"] == "failed":
                    type_details = value["typeDetails"]
                    for key in type_details:
                        if key == data["code"]:
                            type_details[key] += 1
                            found = True
                    if not found:
                        type_details[data["code"]] = 1
                    value["count"] += 1
        details = {"currentRecordIndex": data["currentRecordIndex"], "processedCount": processed, "timeStamp": str(datetime.now())}
        task_event["details"] = details
        task_event["lastModified"] = str(datetime.now())
        repo.update(task_event)
        return

    def task_event_search(self, data, error):
        log.info(f'Publishing pt event for SEARCH -- {data["serviceRequestNumber"]}')
        try:
            task_event = self.search_task_event(data, pt_search_tool)
            if task_event:
                task_event["details"] = data
                if error:
                    task_event["status"] = pt_failed_status
                    task_event["error"] = error
                else:
                    task_event["status"] = pt_success_status
                task_event["lastModified"] = str(datetime.now())
                task_event["endTime"] = task_event["lastModified"]
                repo.update(task_event)
            else:
                task_event = {"id": str(uuid.uuid4()), "tool": pt_search_tool, "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                              "startTime": str(datetime.now()), "lastModified": str(datetime.now())}
                repo.insert(task_event)
            return
        except Exception as e:
            log.exception(e)
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
                task_event["endTime"] = str(datetime.now())
                repo.update(task_event)
            else:
                task_event = {"id": str(uuid.uuid4()), "tool": pt_delete_tool, "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                              "startTime": str(datetime.now()), "lastModified": str(datetime.now())}
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