import config
from utilities import post_error, MdUtils
import logging
import validators

log         =   logging.getLogger('file')
utils       =   MdUtils()

class MasterDataServices():

    #deciding master and properties to return
    def get_attributes_data(self,attribute,jsonpath):
        git_file_location   =   f"{config.git_folder_prefix}/{attribute}.json"
        if not jsonpath:
            master_data     =   utils.read_from_git(git_file_location)
            if not master_data:
                return post_error("Not found", "masterName is not valid")
            if "relatedMaster" not in master_data.keys():
                return master_data[attribute]
            else:
                for i,attrib in enumerate(master_data[attribute]):
                    if isinstance(attrib["values"],dict) and attrib["values"]:
                        log.info(f"denormalizing {attrib}")
                        attrib["values"]    =   self.get_sub_master(attrib["values"])                    
            return master_data
        else:
            log.info("jsonPath found on request")
            master_data = self.get_sub_master({"master":f"/{attribute}.json","jsonPath":jsonpath})
            return master_data
            

    
    def get_sub_master(self,sub_master_obj):
        log.info("Starting to fetch sub masters")
        branch              =   sub_master_obj["master"]
        expression          =   sub_master_obj["jsonPath"]
        git_file_location   =   f'{config.git_folder_prefix}{branch}'
        data                =   utils.read_from_git(git_file_location)
        sub_master_data     =   utils.jsonpath_parsing(data,expression)
        for sub in sub_master_data:
            if "values" in sub.keys() and isinstance(sub["values"],dict) and sub["values"]:
                log.info(f'denormalizing {sub["values"]}')
                sub["values"] = self.get_sub_master(sub["values"]) 
        return sub_master_data


        
        



                

