import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import pt_publish_tool, pt_inprogress_status, pt_success_status, pt_failed_status, pt_update_batch
from .ptrepo import PTRepo
import time 

log = logging.getLogger('file')

mongo_instance = None
repo = PTRepo()

class ProcessTracker:
    def __init__(self):
        pass

    def update_task_details(self, data):
        duration = None
        if 'duration' in data.keys():
            duration = int(data['duration'])

        if data["status"] == "SUCCESS":
            repo.redis_key_inc('ServiceRequestNumber_'+data["serviceRequestNumber"], duration, False)
        else:
            repo.redis_key_inc('ServiceRequestNumber_'+data["serviceRequestNumber"], duration, True)

    def create_task_event(self, data):
        log.info(f'Publishing pt event for SUBMIT -- {data["serviceRequestNumber"]}')
        try:
            task_event_entry = repo.redis_search([f'{data["serviceRequestNumber"]}|{pt_publish_tool}'])
            if task_event_entry:
                task_event_entry = task_event_entry[0]
                if task_event_entry["taskEvent"]["status"] == pt_inprogress_status:
                    return self.update_task_event(data, task_event_entry)
                else:
                    log.error(f'Record received for a {task_event_entry["taskEvent"]["status"]} SRN -- {data["serviceRequestNumber"]}')
                    return
            strtime = eval(str(time.time()).replace('.', '')[0:13])
            lastmodtime = eval(str(time.time()).replace('.', '')[0:13])
            task_event = {"id": str(uuid.uuid4()), "tool": pt_publish_tool, "serviceRequestNumber": data["serviceRequestNumber"], "status": pt_inprogress_status,
                          "startTime": strtime, "lastModifiedTime": lastmodtime}
            if data["status"] == "SUCCESS":
                processed_count = [{"type": "success", "count": 1}, {"type": "failed", "typeDetails": {}, "count": 0}]
            else:
                processed_count = [{"type": "failed", "typeDetails": {data["code"]: 1}, "count": 1}, {"type": "success", "count": 0}]
            timeStmp = eval(str(time.time()).replace('.', '')[0:13])
            details = {"currentRecordIndex": data["currentRecordIndex"], "processedCount": processed_count, "timeStamp": timeStmp}
            task_event["details"] = details
            log.info(f'Creating PT event for SRN -- {data["serviceRequestNumber"]}')
            repo.insert(task_event)
            del task_event["_id"]
            repo.redis_upsert(f'{data["serviceRequestNumber"]}|{pt_publish_tool}', {"taskEvent": task_event, "count": 1})
            return
        except Exception as e:
            log.exception(e)
            return

    def end_processing(self, data):
        try:
            log.error(f'EOF received for SRN -- {data["serviceRequestNumber"]}')
            task_event = self.search_task_event(data, pt_publish_tool)
            if task_event:
                task_event_entry = repo.redis_search([f'{data["serviceRequestNumber"]}|{pt_publish_tool}'])
                if task_event_entry:
                    task_event_entry = task_event_entry[0]
                    task_event = task_event_entry["taskEvent"]
                if task_event["status"] == pt_inprogress_status:
                    task_event["status"] = pt_success_status
                    task_event["endTime"] = task_event["lastModifiedTime"] = eval(str(time.time()).replace('.', '')[0:13])
                    repo.update(task_event)
                    return task_event
                else:
                    log.info(f'EOF received for a {task_event["status"]} SRN -- {data["serviceRequestNumber"]}')
            else:
                log.error(f'EOF received for a non existent SRN -- {data["serviceRequestNumber"]}')
            repo.redis_delete(f'{data["serviceRequestNumber"]}|{pt_publish_tool}')
            return None
        except Exception as e:
            log.exception(f'Exception while updating eof at publish stage to process tracker, srn -- {data["serviceRequestNumber"]} | exc -- {e}', e)
            return None

    def update_task_event(self, data, task_event_entry):
        try:
            count = 1
            if task_event_entry["count"] == pt_update_batch:
                log.info(f'Updating PT event for SRN -- {data["serviceRequestNumber"]}')
                repo.update(task_event_entry["taskEvent"])
            else:
                count = task_event_entry["count"] + 1
            task_event = task_event_entry["taskEvent"]
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
            details = {"currentRecordIndex": data["currentRecordIndex"], "processedCount": processed, "timeStamp": eval(str(time.time()).replace('.', '')[0:13])}
            task_event["details"] = details
            task_event["lastModifiedTime"] = eval(str(time.time()).replace('.', '')[0:13])
            repo.redis_upsert(f'{data["serviceRequestNumber"]}|{pt_publish_tool}', {"taskEvent": task_event, "count": count})
            return
        except Exception as e:
            log.exception(f'Exception while updating publish stage status to process tracker, srn -- {data["serviceRequestNumber"]} | exc -- {e}', e)
            return


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