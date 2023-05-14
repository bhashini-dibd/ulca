## Translation model examples
Few examples to show how a submitter can publish a translation model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
    "input": [
        {
            "source": "How are you doing"
        },
        {
            "source": "what is your name"
        }
    ],
    "config": {
        "language": {
               "sourceLanguage" : "en",
               "targetLanguage" : "hi"
        }
    }
}
```

**Sample Response:**
```json
{
    "output": [
        {
            "source": "How are you doing",
            "target": "कैसे हैं?"
        },
        {
            "source": "what is your name",
            "target": "आपका नाम क्या है?"
        }
    ],
    "config": {
        "language": {
            "sourceLanguage": "en",
            "targetLanguage": "hi"
        }
    }
}

```