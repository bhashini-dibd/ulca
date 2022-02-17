import requests

headers = {"content-Type": "application/json"}

PARAMDATA = {
  "modelId": "61cd55a81c65901133f66712",
  "task": "asr",
  "audioUri": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_hindi.wav",
  "source": "hi",
  "inferenceEndPoint": {
    "callbackUrl": "https://34.65.180.101:5000/infer_ulca_hi",
    "schema": {
      "request": {
        "config": {
          "audioFormat": "wav",
          "transcriptionFormat": {
            "value": "transcript"
          }
        }
      }
    }
  },
  "userId": "1eb83d279329426db1d1827c0e1abf93"
}
REQ_URL = "https://dev-auth.ulcacontrib.org/ulca/apis/asr/v1/model/compute"

def ASR_REC_TEST(PARAMDATA):
    response = requests.post(url=REQ_URL, headers=headers, json=PARAMDATA)
    result_json = response.json()
    print(result_json['data']['source'])
    return result_json['data']['source']

ASR_REC_TEST(PARAMDATA)