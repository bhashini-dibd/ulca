from api import ulcdatastoreapp
from datastore import Datastore
from configs import set_cluster


if __name__ == '__main__':
    datastore = Datastore()
    datastore.set_mongo_cluster(set_cluster)
    ulcdatastoreapp.run(host="52.39.16.42", port=5010, threaded=True)