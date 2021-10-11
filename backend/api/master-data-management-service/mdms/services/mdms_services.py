import config
from utilities import post_error, MdUtils
import logging

log         =   logging.getLogger('file')
utils       =   MdUtils()

class MasterDataServices():

    #choosing upload mechanism as per config
    def get_attributes_data(self,attribute,lang):
        git_file_location   =   f"{config.git_folder_prefix}{attribute}.json"
        data                =   utils.read_from_git(git_file_location)
        for attrib in data:
            if isinstance(attrib["values"],list):
                locale_match        =   [val for val in attrib["values"] if val["locale"]==lang]
                attrib["values"]    =   locale_match

            else:
                sub_master_data = utils.read_from_git(attrib["values"])
        return data
            

            
        
        



                

