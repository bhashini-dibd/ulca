## Translation model examples
Few examples to show how a submitter can publish an OCR model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "image" : [
               { 
                  "imageUri": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/anuvaad_ocr_hindi.jpg"
               }
            ],
    "config": {
        "language": {
               "sourceLanguage" : "hi"
        }
    }
}
```

**Sample Response:**
```json
{
    "output" :  [
        {        
        "source" : "बिपिन रावत का एक माचिस की डिबिया के कारण हुआ था"
        }
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```