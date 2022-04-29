## Translation model examples
Few examples to show how a submitter can publish a transliteration model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "input": [
        {
            "source": "mera naam ram he"
        },
        {
            "source": "mem dilli mem rahatha he"
        }
    ],
    "config": {
        "language": {
               "sourceLanguage" : "en",
               "targetLanguage": "hi"
        }
    }
}
```

**Sample Response:**
```json
{
    "output" :  [
        {        
        "source" : "mera naam ram he",       
        "target" : "मेरा नाम राम हे "       
        },
        {
        "source" : "mem dilli mem rahatha he",       
        "target" : "मेम दिल्ली मेम रहता हे "
        }
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```