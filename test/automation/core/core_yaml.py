import yaml
import requests
from .core_main import print_task,print_output
from .loader import SCHEMA_FILENAME

def get_yaml_data(URL):
    try:
        #get-yaml-file-from-url
        yaml_file = requests.get(URL)
        #load-yaml-data
        y_data = yaml.safe_load(yaml_file.text)["components"]["schemas"]
        #required-yaml-data
        lng = y_data["LanguagePair"]["properties"]["sourceLanguage"]["enum"]
        domains = y_data["Domain"]["items"]["enum"]
        d_types = y_data["DatasetType"]["enum"]
        m_tasks = y_data["ModelTask"]["properties"]["type"]["enum"]
        #removing-unwanted-data
        try:
            d_types.remove('document-layout-corpus')
            m_tasks.remove('document-layout')
        except Exception:
            pass
        #prepare-yaml-data
        status = True
        data = {
            "Languages": lng,
            "Domains": domains,
            "DataTypes": d_types,
            "ModelTasks": m_tasks,
        }
    except Exception as e:
        status = False
        data = "url/schema has been changed."
    return status, data


def write_yaml_data(data, filename):
    try:
        with open(filename, "w") as wrt:
            yaml.dump(data, wrt, default_flow_style=False, allow_unicode=True)
        status = True
        status_str = "updated into 'schema.yml'"
    except Exception as e:
        status = False
        status_str = str(e)
    return status, status_str


def update_schema(URL, filename):
    print_task("schema")
    status, string = get_yaml_data(URL)
    data = string
    if status:
        status, string = write_yaml_data(data, filename)
    print_output(status, string)
    if status:
        print()
        for i in data:
            print(i, "=", data[i])
            print()
    return None
    
def load_yaml_data(string):
    # read-data-schema-file
    with open(SCHEMA_FILENAME, "r") as obj:
        data_list = yaml.safe_load(obj)
    data_list = data_list[string]
    return data_list
