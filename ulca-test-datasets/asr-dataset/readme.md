
# ULCA Test Datasets : ASR Corpus


## [positive-testcase-01](./positive-testcase-01) 

**Description** : Valid case of Audio auto-aligned dataset.
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-02](./positive-testcase-02)

**Description** : Valid case of Audio machine-transcribed dataset.
> `params.json` : VALID
> `data.json`   : VALID


## [positive-testcase-03](./positive-testcase-03)

**Description** : Valid case of Audio manual-transcribed dataset.
> `params.json` : VALID
> `data.json`   : VALID

## [positive-testcase-04](./positive-testcase-04)

**Description** : Valid case of Midlands English SNR dataset.
> `params.json` : VALID
> `data.json`   : VALID


## [negative-testcase-01](./negative-testcase-01)

**Description** : 
Incorrect dataset type is specified in the params file.
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "datasetType": "ASR Corpus",
```
**Expected correction :**
```json
    "datasetType": "asr-corpus",
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
        "targetLanguage": null
    },
```
**Expected correction :**
```json
    "languages": {
        "sourceLanguage": "en",
        "targetLanguage": null
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
Ex : *channel* defined as '*5.1*', which is not part of the enum

> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "channel": "5.1",
```
**Expected correction :**
```json
    "channel": "mono",
```
Refer to [AudioChannel](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/AudioChannel) for the usage.


## [negative-testcase-06](./negative-testcase-06)

**Description** : 
Usage of an array of length outside what is specified by minLength and maxLength of that property.
Ex : For '*collectionSource*', the minItems & maxItems values are defined as 1 & 10 respectively. If collectionSource array size is > 10, it would fail.

> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "collectionSource": [
        "openslr",
        "https://www.youtube.com?v=121212121",
        "https://www.mymp3world.com/",
        "https://www.mannkibaath.com/",
        "https://www.newsonair.in/",
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
        "openslr",
        "https://www.youtube.com?v=121212121",
        "https://www.mymp3world.com/",
        "https://www.mannkibaath.com/",
        "https://www.newsonair.in/",
        "https://indianexpress.com/",
        "https://www.zeebiz.com/",
        "https://www.sakshi.com/",
        "https://marketfeed.news/"
    ],
```
Refer to [collectionSource schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/Source) for the usage.

## [negative-testcase-07](./negative-testcase-07)

**Description** : 
Incorrect keys are specified in the data file.
Ex : '*audioFilename*' incorrectly added as '*asrFile*'

> `params.json` : VALID
> `data.json`   : INVALID

**Error :**
```json
    {
        "asrFile": "audios/mif_03397_01795413856.wav",
        "text": "Flights on American Airlines from Swansea to Surat leaving August 17 and coming back August 28 start at 600 pounds",
        "snr": {
            "methodType": "WadaSnr",
            "methodDetails": {
                "snr": 32
            }
        }
    }
```
**Expected correction :**
```json
    {
        "audioFilename": "audios/mif_03397_01795413856.wav",
        "text": "Flights on American Airlines from Swansea to Surat leaving August 17 and coming back August 28 start at 600 pounds",
        "snr": {
            "methodType": "WadaSnr",
            "methodDetails": {
                "snr": 32
            }
        }
    }
```
Refer to [ASRRowSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ASRRowSchema) for the usage.


## [negative-testcase-08](./negative-testcase-08)

**Description** : 
Overridden value have incorrect structure in data file. 
Ex : '*collectionDetails*' is not defined under '*collectionMethod*'.

> `params.json` : VALID
> `data.json`   : INVALID

**Error :**
```json
    {
        "asrFile": "audios/mif_02484_01626966997.wav",
        "text": "McKinley was open to persuasion by United States expansionists and by annexationists from Hawaii",
        "methodType": "WadaSnr",
        "methodDetails": {
            "snr": 32
        }
    }
```
**Expected correction :**
```json
    {
        "asrFile": "audios/mif_02484_01626966997.wav",
        "text": "McKinley was open to persuasion by United States expansionists and by annexationists from Hawaii",
        "snr": {
            "methodType": "WadaSnr",
            "methodDetails": {
                "snr": 40
            }
        }
    }
```
Refer to [ASRRowSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ASRRowSchema) for the usage.



## [negative-testcase-08](./negative-testcase-09)

**Description** : 
Format of hour, minutes and seconds is incorrect. 
Ex : '*startTime*' and '*endTime*' is in incorrect format.


**Error :**

```json
    {
        "audioFilename": "mkb_june2020_one_minute.wav",
        "text": "में अपना आधा सफ़र अब पूरा कर लिया है",
        "startTime": "00-00-00",
        "endTime": "00-00-6.96",
        "channel": "stereo",
        "samplingRate": 44.1,
        "bitsPerSample": "sixteen",
        "gender": "male",
        "age": "60-100",
        "snr": {
            "methodType": "WadaSnr",
            "methodDetails": {
                "snr": 55
            }
        }
    }
```
**Expected correction :**

```json
    {
        "audioFilename": "mkb_june2020_one_minute.wav",
        "text": "में अपना आधा सफ़र अब पूरा कर लिया है",
        "startTime": "00:00:00",
        "endTime": "00:00:6.96",
        "channel": "stereo",
        "samplingRate": 44.1,
        "bitsPerSample": "sixteen",
        "gender": "male",
        "age": "60-100",
        "snr": {
            "methodType": "WadaSnr",
            "methodDetails": {
                "snr": 55
            }
        }
    }
```

Refer to [ASRRowSchema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/dataset-schema.yml#/components/schemas/ASRRowSchema) for the usage.