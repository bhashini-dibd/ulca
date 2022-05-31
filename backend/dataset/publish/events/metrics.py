import datetime
import logging
from configs.configs import metric_event_input_topic, user_mode_pseudo
from kafkawrapper.producer import Producer

log = logging.getLogger('file')


mongo_instance = None
prod = Producer()

class MetricEvent:
    def __init__(self):
        pass

    # Method to construct metric event from the input
    def build_metric_event(self, records, metadata, is_del, is_upd):
        if metadata["userMode"] == user_mode_pseudo:
            return
        if is_upd:
            previous_record, new_record = records[1], records[0]
            previous_record["serviceRequestNumber"], previous_record["userId"] = metadata["serviceRequestNumber"], metadata["userId"]
            previous_record["datasetType"], previous_record["isUpdate"] = metadata["datasetType"], True
            self.create_metric_event(previous_record)
            new_record["serviceRequestNumber"], new_record["userId"] = metadata["serviceRequestNumber"], metadata["userId"]
            new_record["datasetType"], new_record["isUpdate"] = metadata["datasetType"], False
            self.create_metric_event(new_record)
            return
        if not isinstance(records, list):
            records["serviceRequestNumber"], records["userId"] = metadata["serviceRequestNumber"], metadata["userId"]
            records["datasetType"] = metadata["datasetType"]
            if is_del:
                records["isDelete"] = True
            self.create_metric_event(records)
        else:
            for record in records:
                record["serviceRequestNumber"], record["userId"] = metadata["serviceRequestNumber"], metadata[
                    "userId"]
                record["datasetType"] = metadata["datasetType"]
                if is_del:
                    record["isDelete"] = True
                self.create_metric_event(record)

    # Method to construct and post metric events to the bi event consumer
    def create_metric_event(self, data):
        try:
            event = {"eventType": "dataset-training", "eventId": f'{data["serviceRequestNumber"]}_{data["id"]}',
                     "timestamp": str(datetime.datetime.utcnow().isoformat() + 'Z'), "submitterId": data["userId"], "datasetType": data["datasetType"],
                     "sourceLanguage": None, "targetLanguage": None, "domains": None, "license": None, "collectionSource": None,
                     "primarySubmitterId": None, "secondarySubmitterIds": None, "collectionMethod_collectionDescriptions": None,
                     "collectionMethod_collectionDetails_alignmentTool": None, "format": None, "channel": None, "samplingRate": None,
                     "bitsPerSample": None, "gender": None, "durationInSeconds": None, "primarySubmitterName": None}
            if 'sourceLanguage' in data.keys():
                event["sourceLanguage"] = data["sourceLanguage"]
            if 'targetLanguage' in data.keys():
                event["targetLanguage"] = data["targetLanguage"]
            if 'domain' in data.keys():
                event["domains"] = data["domain"]
            if 'license' in data.keys():
                event["license"] = data["license"]
            if 'collectionSource' in data.keys():
                event["collectionSource"] = data["collectionSource"]
            if 'submitter' in data.keys():
                primary_submitter_ids, primary_submitter_names, secondary_submitters = [], [], []
                for submitter in data["submitter"]:
                    if submitter:
                        if 'id' in submitter.keys():
                            primary_submitter_ids.append(submitter["id"])
                        if 'name' in submitter.keys():
                            primary_submitter_names.append(submitter["name"])
                        if 'team' in submitter.keys():
                            for team in submitter["team"]:
                                secondary_submitters.append(team["name"])
                if primary_submitter_ids:
                    event["primarySubmitterId"] = primary_submitter_ids
                if primary_submitter_names:
                    event["primarySubmitterName"] = primary_submitter_names
                if secondary_submitters:
                    event["secondarySubmitterIds"] = secondary_submitters
            if 'collectionMethod' in data.keys():
                coll_desc, coll_details = [], []
                if data["collectionMethod"]:
                    for cm in data["collectionMethod"]:
                        if cm:
                            if 'collectionDescription' in cm.keys():
                                if cm["collectionDescription"]:
                                    if isinstance(cm["collectionDescription"], list):
                                        coll_desc.extend(cm["collectionDescription"])
                                    else:
                                        coll_desc.append(cm["collectionDescription"])
                            if 'collectionDetails' in cm.keys():
                                if cm["collectionDetails"]:
                                    if 'alignmentTool' in cm["collectionDetails"].keys():
                                        coll_details.append(cm["collectionDetails"]["alignmentTool"])
                if coll_desc:
                    event["collectionMethod_collectionDescriptions"] = coll_desc
                if coll_details:
                    event["collectionMethod_collectionDetails_alignmentTool"] = coll_details
            if 'format' in data.keys():
                event["format"] = data["format"]
            if 'channel' in data.keys():
                event["channel"] = data["channel"]
            if 'samplingRate' in data.keys():
                event["samplingRate"] = data["samplingRate"]
            if 'bitsPerSample' in data.keys():
                event["bitsPerSample"] = data["bitsPerSample"]
            if 'gender' in data.keys():
                event["gender"] = data["gender"]
            if 'durationInSeconds' in data.keys():
                event["durationInSeconds"] = data["durationInSeconds"]
            if 'isDelete' in data.keys():
                event["isDelete"] = True
                prod.produce(event, metric_event_input_topic, None)
            elif 'isUpdate' in data.keys():
                event["isDelete"] = data["isUpdate"]
                prod.produce(event, metric_event_input_topic, None)
            else:
                event["isDelete"] = False
                prod.produce(event, metric_event_input_topic, None)
        except Exception as e:
            log.exception(data, e)
            return None