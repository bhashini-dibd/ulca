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
        "nerPrediction" : [{"token" : "ram", "tag" : "PER" },{"token" : "chennai", "tag" : "LOC" } ]    
        }
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```