file_name = "/data.json"
file_path = "/Users/mahulivishal/Desktop/ulca/ulcaevaluation/datastore"
result_path = "/Users/mahulivishal/Desktop/ulca/ulcaevaluation/datastore/dataset/result"
#mongo_server_host = "mongodb://localhost:27017/"
mongo_server_host = "mongodb://172.30.0.96:27017,172.30.0.48:27017/"
#mongo_server_host = "mongodb://172.30.0.96:27017,172.30.0.48:27017/?replicaSet=ulcashard&connectTimeoutMS=300000"
mongo_ulca_db, mongo_ulca_dataset_col = "ulca", "dataset-m3"
default_offset = 0
default_limit = 10000
no_of_process = 3