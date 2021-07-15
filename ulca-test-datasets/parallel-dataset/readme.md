
# ULCA Test Datasets : Parallel Corpus


## [positive-testcase-01](./positive-testcase-01) 

**Description** : Basic valid dataset
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-02](./positive-testcase-02)

**Description** : Machine translated & target validated dataset
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-03](./positive-testcase-03)

**Description** : Machine translated, target validated & post-edited dataset
> `params.json` : VALID
> `data.json`   : VALID

## [positive-testcase-04](./positive-testcase-04)

**Description** : Human translated dataset
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-05](./positive-testcase-05)

**Description** : Web scrapped & LaBSE aligned dataset
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-06](./positive-testcase-06)

**Description** : 
Duplicate pairs in the data file. The system should handle the duplicates.
Ex : The values for '*sourceText*' and '*targetText*' are repeating.
> `params.json` : VALID
> `data.json`   : VALID

**Input Data :**
```json
    {
        "sourceText": "This bridge is of strategic importance along the India-Nepal border",
        "targetText": "भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है"
    },
    {
        "sourceText": "This bridge is of strategic importance along the India-Nepal border",
        "targetText": "भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है"
    },
    {
        "sourceText": "This bridge is of strategic importance along the India-Nepal border",
        "targetText": "भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है"
    }
```
**Expected behaviour :**
System should ingest only one instance of this pair.


## [negative-testcase-01](./negative-testcase-01)

**Description** : 
Incorrect dataset type is specified in the params file.
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "datasetType": "Parallel Corpus",
```
**Expected correction :**
```json
    "datasetType": "parallel-corpus",
```
Refer to [datasetType schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/DatasetType) for the usage.


## [negative-testcase-02](./negative-testcase-02)

**Description** : 
Incorrect language code is specified in the params file under '*languages*' property.
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "languages": {
        "sourceLanguage": "English",
        "targetLanguage": "Hindi"
    },
```
**Expected correction :**
```json
    "languages": {
        "sourceLanguage": "en",
        "targetLanguage": "hi"
    },
```
Refer to [LanguagePair schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/LanguagePair) for the usage.


## [negative-testcase-03](./negative-testcase-03)

**Description** : 
The params file is missing the required fields (Ex : license)
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    <license> is not included in params.
```
**Expected correction :**
```json
    "license": "cc-by-4.0",
```
Refer to [ParallelDatasetParamsSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ParallelDatasetParamsSchema) for the usage.


## [negative-testcase-04](./negative-testcase-04)

**Description** : 
Incorrect schema is specified. 
Ex : For '*domain*' in params file, a string is specified, whereas it accepts only array of string)
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "domain": "news",
```
**Expected correction :**
```json
    "domain":[
        "news"
    ],
```
Refer to [Domain schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/Domain) for the usage.

## [negative-testcase-05](./negative-testcase-05)

**Description** : 
Usage of a value outside of what is specified under enum of that property.
Ex : *collectionDescription* defined as '*unknown*', which is not part of the enum

> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "collectionMethod": {
        "collectionDescription": [
            "unknown"
        ],
```
**Expected correction :**
```json
    "collectionMethod": {
        "collectionDescription": [
            "auto-aligned"
        ],
```
Refer to [Domain schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/Domain) for the usage.


## [negative-testcase-06](./negative-testcase-06)

**Description** : 
Usage of an array of length outside what is specified by minLength and maxLength of that property.
Ex : For '*collectionSource*', the minItems & maxItems values are defined as 1 & 10 respectively. If collectionSource array size is > 10, it would fail.
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "collectionSource": [
        "http://pib.gov.in/",
        "https://www.mykhel.com/",
        "https://www.drivespark.com/",
        "https://www.goodreturns.in/",
        "https://indianexpress.com/",
        "http://www.catchnews.com/",
        "https://dw.com/",
        "http://ddnews.gov.in/",
        "https://www.financialexpress.com/",
        "https://www.zeebiz.com/",
        "https://www.sakshi.com/",
        "https://marketfeed.news/"
    ],
```
**Expected correction :**
Reduce the number of values to be <= maxItems
```json
    "collectionSource": [
        "http://pib.gov.in/",
        "https://www.mykhel.com/",
        "https://www.drivespark.com/",
        "https://www.goodreturns.in/",
        "https://indianexpress.com/",
        "http://www.catchnews.com/",
        "https://dw.com/",
        "https://marketfeed.news/"
    ],
```
Refer to [collectionSource schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/Source) for the usage.


## [negative-testcase-07](./negative-testcase-07)

**Description** : 
Incorrect keys are specified in the data file.
> `params.json` : VALID
> `data.json`   : INVALID

**Error :**
```json
    {
        "src": "This bridge is of strategic importance along the India-Nepal border",
        "tgt": "भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है"
    },
```
**Expected correction :**
```json
    {
        "sourceText": "This bridge is of strategic importance along the India-Nepal border",
        "targetText": "भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है"
    },
```
Refer to [ParallelDatasetRowSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ParallelDatasetRowSchema) for the usage.


## [negative-testcase-08](./negative-testcase-08)

**Description** : 
Overridden value have incorrect structure in data file. 
Ex : '*collectionDetails*' is not defined under '*collectionMethod*'.
> `params.json` : VALID
> `data.json`   : INVALID

**Error :**
```json
    {
        "sourceText": "Cabinet approves MoU of Cooperation between India and Morocco",
        "targetText": "मंत्रिमंडल ने भारत और मोरक्को के बीच सहयोग के लिए समझौता ज्ञापन को मंजूरी दी",
        "collectionDetails": {
            "timeSpentInSeconds": 22,
            "numOfKeysPressed": 5,
            "contributor": {
                "name": "Monika Chauhan",
                "aboutMe": "Freelance translator working with Project Anuvaad"
            }
        }
    },
```
**Expected correction :**
```json
    {
        "sourceText": "Cabinet approves MoU of Cooperation between India and Morocco",
        "targetText": "मंत्रिमंडल ने भारत और मोरक्को के बीच सहयोग के लिए समझौता ज्ञापन को मंजूरी दी",
        "collectionMethod": {
            "collectionDetails": {
                "timeSpentInSeconds": 22,
                "numOfKeysPressed": 5,
                "contributor": {
                    "name": "Monika Chauhan",
                    "aboutMe": "Freelance translator working with Project Anuvaad"
                }
            }
        }
    },
```
Refer to [ParallelDatasetRowSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ParallelDatasetRowSchema) for the usage.
