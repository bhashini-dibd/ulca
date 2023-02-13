## Audio Language detection model examples
Few examples to show how a submitter can publish an audio language detection model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "audio": [
                    {
                        "audioUri": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_english.wav"
                    }
                ]
}
```

**Sample Response:**
```json
{
    "output" :  [
        {        
        "langPrediction" : [{"langCode" : "en", "langScore" : 90 } ]    
        },
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```