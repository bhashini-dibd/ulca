## Translation model examples
Few examples to show how a submitter can publish a transliteration model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "input": [
        {
            "source": "vah aath saal ka tha jab usane apane maata-pita ko chhod diya."
        },
        {
            "source": "agar kofee zyaada kadavee hai to thoda aur paanee daalalo."
        }
    ],
    "config": {
        "isSentence": "True",
        "numSuggestions" : 1
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
        "source" : "vah aath saal ka tha jab usane apane maata-pita ko chhod diya.",       
        "target" : ["वह आठ साल का था जब उसने अपने माता-पिता को छोड़ दिया।" ]      
        },
        {
        "source" : "agar kofee zyaada kadavee hai to thoda aur paanee daalalo.",       
        "target" : ["अगर कॉफ़ी ज़्यादा कड़वी है तो थोड़ा और पानी डाललो।"]
        }
          ],           
    "status" : {  
        "statusCode" : 200 ,
        "message" : "success"     
    }
}

```