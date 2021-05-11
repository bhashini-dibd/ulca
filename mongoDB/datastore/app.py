from api import ulcdatastoreapp


if __name__ == '__main__':
    ulcdatastoreapp.run(host='0.0.0.0', port=5010, threaded=True)