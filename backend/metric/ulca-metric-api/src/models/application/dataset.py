from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from src.models.enums.app_enums import STAGE, CORPUS_TYPE

import uuid
import datetime

class Dataset(object):
    def __init__(self):
        self.datasetId      = str(uuid.uuid4())
        self.stage          = STAGE.SUBMITTED.value
        self.count          = None
        self.description    = None

        self.submitter      = None
        self.contributors   = None

        self.collection_sources = None
        self.domain             = None
        self.collection_method  = None
        self.license            = None

        self.publishedOn        = datetime.datetime.utcnow()
        self.submittedOn        = datetime.datetime.utcnow()
        self.validatedOn        = datetime.datetime.utcnow()

        self.validationSchema   = None
        self.hosting            = None


class ParallelDataset(Dataset):
    def __init__(self):
        super().__init__()
        self.type               = CORPUS_TYPE.PARALLEL_CORPUS.value
        self.languagePairs      = None
        self.targetValidated    = None
        self.alignmentMethod    = None

    def mandatory_params(self, data):
        keys    = ['count', 'submitter', 'contributors', 'languagePairs', 'collectionSource', 'domain', \
            'collectionMethod', 'license', 'validationSchema', 'hosting']
        for key in keys:
            if key not in data.keys():
                return False, key
        return True, None
    
    def get_value_from_key(self, data, key):
        return data[key]

    def get_validated_dataset(self, data):
        status, key = self.mandatory_params(data)
        if status == False:
            return status, key

        data['datasetId']   = self.datasetId
        data['stage']       = self.stage
        data['submittedOn'] = self.submittedOn
        
        if data['count']    < 100:
            return False, 'count'
        return True, data

    def get_tags(self, data):
        tags    = []

        tags.append(self.type)
        
        langPair            = self.get_value_from_key(data, 'languagePairs')
        tags.append(langPair['sourceLanguage']['value'] + '-' + langPair['targetLanguage']['value'])
        tags.append(langPair['targetLanguage']['value'] + '-' + langPair['sourceLanguage']['value'])

        collectionSource    = self.get_value_from_key(data, 'collectionSource')
        tags.extend(collectionSource['value'])

        domain              = self.get_value_from_key(data, 'domain')
        tags.extend(domain['value'])

        collectionMethod    = self.get_value_from_key(data, 'collectionMethod')
        tags.extend(collectionMethod['value'])

        license             = self.get_value_from_key(data, 'license')
        tags.append(license['value'])

        return tags
