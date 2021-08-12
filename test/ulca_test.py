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
import boto3
import s3fs
from datetime import datetime



s3 = boto3.client('s3')
obj = s3.get_object(Bucket= 'anuvaad-raw-datasets', Key ='json.csv')
pd_reader = pd.read_csv(obj['Body'])
my_reader = pd_reader.to_dict('records')
outurl = 'https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/json_output.csv'
###
s33 = s3fs.S3FileSystem(anon=False)


class  UserBehaviour(SequentialTaskSet):
    def __init__(self,parent):
        super(UserBehaviour, self).__init__(parent)
        self.userid=""
        self.ssn=""
        self.private = ""
        self.crypt = ""
        self.public = ""
        self.crypt_url = ""
        self.ssnlist = []
        self.submit_rst = []
        self.submit_res = []
        self.md5_list=[]

    #Login 
    @task
    def login(self):
        headers={"Content-Type": "application/json"}
        paramdata= {
            "authenticator": "ULCA",
            "data": {
                "email": "siddanth.shaiva@tarento.com",
                "password": "Welcome@123",
                "checked" : False 
            }

        }
        csv_header = ['type', 'datasetname','url','Submit','Download','ingest','validate','publish']
    
        #Login
        with self.client.post("https://dev-auth.ulcacontrib.org/ulca/user-mgmt/v1/users/login",
                                json=paramdata, headers=headers, name="login") as response:
            json_res = response.json()
            self.userid =  json_res['data']['userDetails']['userID'] 
            self.public=json_res['data']['userKeys']['publicKey']
            self.private = json_res['data']['userKeys']['privateKey']
        print("login successful")
        print("public key",self.public)
        #create csv file
        with s33.open("anuvaad-raw-datasets/json_output.csv", 'w',newline='') as filee:
            writer = csv.writer(filee)
            writer.writerow(csv_header)
        print("PrivateKey is ",self.private)
        print("userID is ",self.userid)

        
    #md5 for submit
    @task
    def md5(self):
        self.md5_list=[]
        self.param = []
        #files
        for row in my_reader:
            date = datetime.now().strftime("%Y_%m_%d-%I:%M:%S_%p")
            smnth = row["datasetName"]
            row["datasetName"] = smnth +  date

            paramdata = {
                "userId": self.userid,
                "datasetName": row["datasetName"],
                "url": row["url"]
            }
            self.param.append(paramdata)

            data=(json.dumps(paramdata)).encode("utf-8")
            test = hashlib.md5(data).hexdigest()
            self.crypt= hashlib.md5((self.private + "|" + test).encode("utf-8")).hexdigest()

            self.md5_list.append(self.crypt)

    #Submit
    @task
    def submit(self):
        self.sub_val = []
        self.sub_list = []
        for idx, row in enumerate(my_reader):

            headers1 = {"key": self.public,"sig":self.md5_list[idx]}
            self.sub_val = [row['type'],self.param[idx]['datasetName'],row['url']]
           
            with self.client.post("https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/corpus/submit",
                                json=self.param[idx], headers=headers1, name="submit") as response:
                json_res = response.json()
               
                if 'data' in json_res.keys():
                    self.ssn = str(json_res['data']['serviceRequestNumber'])
                    self.submit_rst = 'successful'
                    self.ssnlist.append(self.ssn)
                    url_search = "https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/getByServiceRequestNumber?serviceRequestNumber="+self.ssn
                    data1 = str(url_search).encode("utf-8")
                    test1 = hashlib.md5(data1).hexdigest()
                    self.crypt_url = hashlib.md5((self.private + "|" + test1).encode("utf-8")).hexdigest()
                    self.sub_list.append(self.crypt_url)

                elif 'code' in json_res.keys():
                    print("dataset already submitted")
                    self.sub_list.append("cryptEmpty")
                    self.submit_rst = "failed"
                    self.varr = "SsnNotAvail"

                    self.ssnlist.append(self.varr)

        self.srch = self.sub_val


    @task
    def search(self):
        
        for idx, row in enumerate(my_reader):
            self.sub_val = [row['type'],row['datasetName'],row['url']]
            self.sbmtd = self.sub_val + ['datasetPresent','N/A','N/A','N/A','N/A']
            self.fail_var = self.sub_val + ['failed','failed','failed','failed','failed']
            
            if len(self.ssnlist) != 0 and self.ssnlist[idx] != 'SsnNotAvail':
                headers = {"key": self.public, "sig": self.sub_list[idx]}
                self.anotherlst = ['type','datasetName','url','download','ingest','validate','submit','ssn']
                url = "https://dev-auth.ulcacontrib.org/ulca/apis/v0/dataset/getByServiceRequestNumber?serviceRequestNumber="+self.ssnlist[idx]
                
                if self.submit_rst == 'successful':
                    while True:
                        self.sub_val = [row['type'],row['datasetName'],row['url']]
                        self.sbmtd = self.sub_val + ['datasetPresent','N/A','N/A','N/A','N/A']
                        self.fail_var = self.sub_val + ['failed','failed','failed','failed','failed']
                        with self.client.get(url, name="search",headers=headers) as finald :
                            finala = finald.json()

                        newvari = finala['data']
                        #print("this is to fix pending for long time issue",newvari)
                        if newvari[0]['tool'] == 'download' and newvari[0]['status'] == 'Completed' and len(newvari) > 1:
                            print("downlaod is completed and successful")
                            downvar = 'successful'
                            subvar = 'successful'
                            self.sub_val.append(subvar)
                            self.sub_val.append(downvar)

                            if newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'Completed' and len(newvari) >= 2 :#and len(finala) == 3:
                                print("ingest is completed and successful")
                                ingvar = 'successful'
                                self.sub_val.append(ingvar)

                                if newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Completed' and len(newvari) >=3 :#and len(finala) == 4:
                                    print("validate is completed and successful")
                                    valvar = 'successful'
                                    self.sub_val.append(valvar)
                                   
                                    if newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Completed' and len(newvari) >= 4:
                                        print("publish is completed and successful")
                                        pubvar = 'successful'
                                        self.sub_val.append(pubvar)
                                        with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                                            writer = csv.writer(filee)
                                            writer.writerow(self.sub_val)
                                        break

                                    elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Pending':
                                        print("publish  pending")
                                        time.sleep(5)
                                    elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'In-Progress':
                                        print("publish in progress")
                                    elif newvari[3]['tool'] == 'publish' and newvari[3]['status'] == 'Failed':
                                        pubvar = 'Failed'
                                        print("publish failed")
                                        self.sub_val.append(pubvar)
                                        with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                                            writer = csv.writer(filee)
                                            writer.writerow(pubvar)
                                        break

                                elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Pending':
                                    print("validate  pending")
                                    time.sleep(5)
                                elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'In-Progress':
                                    print("validate in progress")
                                elif newvari[2]['tool'] == 'validate' and newvari[2]['status'] == 'Failed':
                                    valvar = self.sub_val +['Failed','N/A']
                                    print("validate failed")
                                    self.sub_val.append(valvar)
                                    with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                                        writer = csv.writer(filee)
                                        writer.writerow(valvar)
                                    break

                            elif newvari[1]['tool'] == 'ingest' and newvari[1]['status'] == 'Failed':
                                print("ingest  failed")
                                newvar = self.sub_val + ['Failed','N/A','N/A']
                                self.sub_val.append(newvar)
                                with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                                    writer = csv.writer(filee)
                                    writer.writerow(newvar)
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
                            downvar = self.sub_val +['Failed','N/A','N/A','N/A']
                            self.sub_val.append(downvar)
                            with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                                writer = csv.writer(filee)
                                writer.writerow(downvar)
                            break

            elif self.ssnlist[idx] == 'SsnNotAvail':
                with s33.open("anuvaad-raw-datasets/json_output.csv", 'a',newline='') as filee:
                    writer = csv.writer(filee)
                    writer.writerow(self.sbmtd)
                print("submit failed")


    @task
    def done(self):
        print("Resultant csv output -- ",outurl)
        self.user.environment.runner.quit()



class MyUser(HttpUser):
    wait_time = between(5, 10)
    host = "https://dev-auth.ulcacontrib.org/"
    tasks = [UserBehaviour]