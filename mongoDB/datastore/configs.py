file_name = "/data.json"
file_path = "/Users/mahulivishal/Desktop/ulca/ulcaevaluation/datastore"
result_path = "/Users/mahulivishal/Desktop/ulca/ulcaevaluation/datastore/dataset/result"
#mongo_server_host = "mongodb://localhost:27017/"
#mongo_server_host = "mongodb://172.30.0.96:27017,172.30.0.48:27017/"
mongo_server_host = "mongodb://cluster0-shard-00-00-2ldwo.mongodb.net:27017,cluster0-shard-00-01-2ldwo.mongodb.net:27017,cluster0-shard-00-02-2ldwo.mongodb.net:27017/test?replicaSet=Cluster0-shard-0"
mongo_ulca_db, mongo_ulca_dataset_col = "ulca", "dataset"
default_offset = 0
default_limit = 10000
no_of_process = 5
set_cluster = False