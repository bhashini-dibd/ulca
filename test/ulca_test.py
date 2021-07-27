from os import write
from locust import  between,SequentialTaskSet,HttpUser,task,between
import json
import requests
import re
import sys
import csv
import pandas as pd
import time
from typing import NewType, final
sys.path.append("C:\GITLOCUSTCODE\Locust")
import hashlib
from utilities.csvreader import CSVreader
my_reader=CSVreader.read_data(r"C:\Users\Test\Downloads\data\json.csv")


fill = r'C:\Users\Test\Desktop\ULCA-develop\python_auto\outtt.csv'

print(my_reader)
print(type(my_reader))

class  UserBehaviour(SequentialTaskSet):
    def __init__(self,parent):
        super(UserBehaviour, self).__init__(parent)
        self.userid=""
        self.ssn=""
        self.filename="output"
        self.private = ""
        self.crypt = ""
        self.public = ""


    @task
    def login(self):
        headers={"Content-Type": "application/json"}
        paramdata= {
            "authenticator": "ULCA",
            "data": {
                "email": "siddanth.shaiva@tarento.com",
                "password": "Welcome@123"
            }

        }
       

        with self.client.post("https://dev-auth.ulcacontrib.org/ulca/user-mgmt/v1/users/login",
                              json=paramdata, headers=headers, name="login") as response:
            #print(response)
            json_res = response.json()
            #print(json_res)
            self.userid = json_res['data']['userDetails']['userID']
            self.public=json_res['data']['userKeys']['publicKey']
            self.private = json_res['data']['userKeys']['privateKey']



    @task
    def md5(self):
        for row in my_reader:
            paramdata = {
                "userId": self.userid,
                "datasetName": row["datasetName"],
                "url": row["url"]
            }
            data=(json.dumps(paramdata)).encode("utf-8")
            test = hashlib.md5(data).hexdigest()
            self.crypt= hashlib.md5((self.private + "|" + test).encode("utf-8")).hexdigest()
            print("md5 of POST ",self.crypt)
            #print(self.crypt1)
    

    @task
    def submit(self):
        headers = {"key": self.public,"sig":self.crypt}
        self.result = []
        for row in my_reader:
            paramdata = {
                "userId": self.userid,#userid
                "datasetName": row["datasetName"],
                "url": row["url"]
            }
            with self.client.post("https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/corpus/submit",
                                json=paramdata, headers=headers, name="submit") as response:
                json_res = response.json()
                print(json_res)
                self.ssn = str(json_res['data']['serviceRequestNumber'])
                print("datasubmitted")
                print("=================>")
                print("ssn is ",self.ssn)
                print("PublicKey is ",self.public)
                print("PrivateKey is ",self.private)
                print("userID is ",self.userid)
                url_search = "https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/getByServiceRequestNumber?serviceRequestNumber="+self.ssn
                data1 = str(url_search).encode("utf-8")
                test1 = hashlib.md5(data1).hexdigest()
                self.crypt_url = hashlib.md5((self.private + "|" + test1).encode("utf-8")).hexdigest()
                print("md5 of GET  ", self.crypt_url)
                

    @task
    def search(self):
        headers = {"key": self.public, "sig": self.crypt_url}
        self.anotherlst = ['type','datasetName','url','download','ingest','validate','submit','ssn']
        #print(self.ssn)
        url = "https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/getByServiceRequestNumber?serviceRequestNumber="+self.ssn
        for row in my_reader:
            paramdata = {
                "type": row["type"],
                "datasetName": row["datasetName"],
                "url": row["url"]
            }
            empty_list = [row["type"],row["datasetName"],row["url"]]
            new_list = ['type','datasetName','url','download','ingest','validate','publish','ssn']
            new_emptylist = []
            while True:
                with self.client.get(url, name="search",headers=headers) as finald :
                    finala = finald.json()
                #print("this is 109",finala)
                #print("type of finala", type(finala))
                #print("this is ", finala['data'])
                newvari = finala['data']
                #print("newvarii",newvari)
                neww = newvari[0]['tool']
                #print("newwwww",neww)
                if newvari[0]['tool'] == 'download' and newvari[0]['status'] == 'Completed' and len(newvari) > 1:
                    print("downlaod is completed and successful")
                    downvar = 'successful'
                    #downstarttime = finala[0]['startTime']
                    #downendtime = finala[0]['endTime']

                    if newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'Completed' and len(newvari) >= 2 :#and len(finala) == 3:
                        print("ingest is completed and successful")
                        ingvar = 'successful'

                        if newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Completed' and len(newvari) >=3 :#and len(finala) == 4:
                            print("validate is completed and successful")
                            valvar = 'successful'

                            if newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Completed' and len(newvari) >= 4:
                                print("publish is completed and successful")
                                pubvar = 'successful'
                                new_emptylist.insert(0,row['type'])
                                new_emptylist.insert(1,row['datasetName'])
                                new_emptylist.insert(2,row['url'])
                                new_emptylist.insert(3,downvar)
                                new_emptylist.insert(4,ingvar)
                                new_emptylist.insert(5,valvar)
                                new_emptylist.insert(6,pubvar)
                                new_emptylist.insert(7,self.ssn)
                                ### print timestamp
                                print(new_emptylist)
                                print(new_list)
                                with open(fill, 'w',newline='') as filee:
                                    writer = csv.writer(filee)
                                    writer.writerow(new_list)
                                    writer.writerow(new_emptylist)
                                break
                            elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Pending':
                                print("publish  pending")
                                time.sleep(5)
                            elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'In-Progress':
                                print("publish in progress")
                            elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Failed':
                                print("publish failed")
                                break

                        elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Pending':
                            print("validate  pending")
                            time.sleep(5)
                        elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'In-Progress':
                            print("validate in progress")
                        elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Failed':
                            print("validate failed")
                            break

                    elif newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'Failed':
                        print("ingest  failed")
                        break
                    elif newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'Pending':
                        print("ingest  pending")
                        time.sleep(5)
                    elif newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'In-Progress':
                        print("ingest in progress")
                    
                elif newvari[0]['tool'] == 'download' and newvari[0]['status'] == 'Pending':
                    print("download  pending")
                    time.sleep(5)
                elif newvari[0]['tool'] == 'download' and newvari[0]['status'] == 'In-Progress':
                    print("download in progress")
                elif newvari[0]['tool'] == 'download' and newvari[0]['status'] == 'Failed':
                    print("download  failed")
                    break
 

    @task
    def done(self):
        self.user.environment.runner.quit()



class MyUser(HttpUser):
    wait_time = between(5, 10)
    host = "https://dev-auth.ulcacontrib.org/"
    tasks = [UserBehaviour]