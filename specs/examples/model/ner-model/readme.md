## NER model examples
Few examples to show how a submitter can publish a Named Entity Recognition model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "input": [
        {
            "source": "my name is ram and i live in chennai"
        }
    ],
    "config": {
        "language": {
               "sourceLanguage" : "en"
        }
    }
}
```

**Sample Response:**
```json
{
    "output" :  [
        {        
        "source": "my name is ram and i live in chennai",       
        "nerPrediction" : [
            {"token" : "ram", "tag" : "PER","tokenStartIndex": 11,"tokenEndIndex": 13 },
            {"token" : "chennai", "tag" : "LOC","tokenStartIndex": 29,"tokenEndIndex": 35 } 
            ]    
        }
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```
