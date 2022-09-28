## Textual Language detection model examples
Few examples to show how a submitter can publish a language detection model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "input": [
        {
            "source": "vah आठ साल का था जब उसने अपने माता-पिता को छोड़ दिया।"
        },

    ],
}
```

**Sample Response:**
```json
{
    "output" :  [
        {        
        "source" : "vah आठ साल का था जब उसने अपने माता-पिता को छोड़ दिया।",       
        "langPrediction" : [{"langCode" : "hi", "langScore" : 90 } ]    
        },
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```