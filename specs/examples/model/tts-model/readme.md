## NER model examples
Few examples to show how a submitter can publish a Text To Speech model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

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
        "gender": "male",
        "language": {
               "sourceLanguage" : "en"
        }
    }
}
```

**Sample Response:**
```json
{
   "audio": [
     {
       "audioContent": "AUDIOCONTENT--UklGRjoRAQBXQVZFZm10IBIAAA--AUDIOCONTENT"
     }
   ],
   "config": {
     "language": {
       "sourceLanguage": "en"
     },
     "audioFormat": "wav"
   }
 }

```