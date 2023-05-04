#all models files to be uploaded must be present within the same directory as the current file.
#Add your sig, payload, key and userId to upload.

import requests
import os
import json 

url = 'https://meity-auth.ulcacontrib.org/ulca/apis/v0/model/upload'

header_dictionary = {
    "sig": "xxxxxxxxxx",
    "payload": "xxxxxxxxxx",
    "key": "xxxxxxxxxx"
}

params = {'userId': 'xxxxxxxxxx'}

files_list = os.listdir()
print(files_list)
for each_file in files_list:
    print(each_file,end=" ")
    payload = open(each_file, "r")
    files = {
            'json': (None, payload, 'application/json'),
            'file': (os.path.basename(each_file), open(each_file, 'rb'), 'application/octet-stream'),
    }

    x = requests.post(url, files=files, headers = header_dictionary, params=params)

    
    print(x.json()['message'])
