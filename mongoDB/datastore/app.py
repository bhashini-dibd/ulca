from api import ulcdatastoreapp
from datastore import Datastore


if __name__ == '__main__':
    datastore = Datastore()
    datastore.set_mongo_cluster(False)
    ulcdatastoreapp.run(host="localhost", port=5010, threaded=True)