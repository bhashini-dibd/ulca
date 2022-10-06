import config
from utilities import post_error, MdUtils
import logging
from models import StoreModel
import logging
log = logging.getLogger('file')

log         =   logging.getLogger('file')
utils       =   MdUtils()
model       =   StoreModel()

class MasterDataServices():

    def get_attributes_data(self,master_list):
        log.info(f"Searching for master in redis store{master_list}")
        master_data         =   model.search(master_list)
        not_on_store_list   =   master_list
        log.info(f'master data @mdms_service - 18 {master_data}')
        if master_data:
            not_on_store_list = [x for x in master_list if x not in master_data.keys()]
            log.info(f'not_on_store_list @21 {not_on_store_list}')
        if not_on_store_list:
            from_git = self.get_from_remote_source(not_on_store_list,None)
            log.info(f'if not_on_store_list {from_git}')
            if from_git:
                master_data.update(from_git)
                for master in from_git:
                    model.upsert(master,from_git[master],None)
        master_data = self.format_result(master_data)
        return master_data

    #deciding master and properties to return
    def get_from_remote_source(self,master_list,jsonpath):
        master_data_dict = {}
        log.info(f'master lists @ 32 {master_list}')
        for master in master_list:
            git_file_location   =   f"{config.git_folder_prefix}/{master}.json"
            if not jsonpath:
                master_data     =   utils.read_from_git(git_file_location)
                if not master_data:
                    return post_error("Exception occurred",None) 
                if "relatedMaster" not in master_data.keys():
                    master_data_dict[master]    =   master_data[master] 
                else:
                    for i,attrib in enumerate(master_data[master]):
                        if isinstance(attrib["values"],dict) and attrib["values"]:
                            log.info(f"denormalizing {attrib}")
                            attrib["values"]    =   self.get_sub_master(attrib["values"])  
                master_data_dict[master]        =   master_data[master]    
            else:
                log.info("jsonPath found on request")
                master_data = self.get_sub_master({"master":f"/{master}.json","jsonPath":jsonpath})
                master_data_dict[master]     =   master_data[master]
        return master_data_dict
            

    #recursive function for sub master data retrieval
    def get_sub_master(self,sub_master_obj):
        log.info("Starting to fetch sub masters")
        branch              =   sub_master_obj["master"]
        expression          =   sub_master_obj["jsonPath"]
        git_file_location   =   f'{config.git_folder_prefix}{branch}'
        data                =   utils.read_from_git(git_file_location)
        log.info(f'data {data}, type {type(data)}')
        log.info(f'expression {expression}, type {type(expression)}')
        sub_master_data     =   utils.jsonpath_parsing(data,expression)
        for sub in sub_master_data:
            if "values" in sub.keys() and isinstance(sub["values"],dict) and sub["values"]:
                log.info(f'denormalizing {sub["values"]}')
                sub["values"] = self.get_sub_master(sub["values"]) 
        return sub_master_data

    #replacing master data files on redis store with new ones
    def bust_cache(self,masters):
        if not masters:
            log.info("Getting master filenames from git")
            master_data_files       =   utils.read_from_git(config.git_master_data_api)
            if master_data_files:
                masters             =   [master["name"].replace(".json","") for master in master_data_files]
                log.info(f"master list: {masters}")
        
        master_data     =       self.get_from_remote_source(masters,None)
        for master in master_data:
            log.info(f"Upserting {master} to redis store")
            model.upsert(master,master_data[master],None)
    
    def format_result(self,result):
        try:
            log.info(f'result of format_result() {result}')
            for master, values in result.items():
                if master == "datasetFilterParams":
                    #log.info(f'format result func {result}')
                    for submaster in values: #parallel-corpus, mono etcc.. level
                        for attrib in submaster["values"]: # filetrs specific to dtype
                            if attrib["code"] == "collectionMethod": 
                              attrib["values"] = [x for x in attrib["values"] if submaster["datasetType"] in x.get("datasetType",[]) ]
            return result
            
        except Exception as e:
            log.error(f"Exception while formatting the end result :{e}")
            return None


            


        
        



                

