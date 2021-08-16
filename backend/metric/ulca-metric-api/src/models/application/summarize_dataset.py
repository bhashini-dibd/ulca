
class SummarizeDataset(object):
    
    def mandatory_params(self, data):
        keys    = ['type', 'criterions', 'groupby']
        for key in keys:
            if key not in data.keys():
                return False, key
        return True, None

    
    #validating request params
    def get_validated_data(self, data):
        status, key = self.mandatory_params(data)
        if not status:
            return status, key
        return True, data
