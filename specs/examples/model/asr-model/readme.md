## ASR model examples
Few examples to show how a submitter can publish a Audio Recognition model to ULCA. The submitter can create a params.json file according to the contract and submit via the ULCA portal.

* [Basic example](./basic)

**Sample Request:**
```json
{
                "audio": [
                    {
                        "audioUri": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_english.wav"
                    }
                ],
                "config": {
                    "audioFormat": "wav",
                    "language": {
                        "sourceLanguage": "en"
                    }
                }
}
```

**Sample Response:**
```json
{
  "output": [
    {
      "source": "So the second step is that somebody has to specify this in the project request and write"
    }
  ],
  "status": "SUCCESS"
}

```
