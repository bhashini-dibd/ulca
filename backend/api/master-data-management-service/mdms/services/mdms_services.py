import config
from utilities import post_error, MdUtils
import logging
import validators

log         =   logging.getLogger('file')
utils       =   MdUtils()

class MasterDataServices():

    #choosing upload mechanism as per config
    def get_attributes_data(self,attribute,lang):
        git_file_location   =   f"{config.git_folder_prefix}{attribute}.json"
        data                =   utils.read_from_git(git_file_location)
        for i,attrib in enumerate(data):
            if isinstance(attrib["values"],list):
                locale_match        =   [val for val in attrib["values"] if val["locale"]==lang]
                attrib["values"]    =   locale_match

            else:
                data[i]             =   self.get_sub_master(attrib["values"])
        return data
            

    def get_sub_master(self,resource_link):
        url=validators.url(resource_link)
        if url == True:
            sub_data = utils.read_from_git(resource_link)
            for data in sub_data:
                if "values" in data.keys() and not isinstance(data["values"],list):
                    data["values"] = self.get_sub_master(data["values"])       
            return sub_data


        
        



                

