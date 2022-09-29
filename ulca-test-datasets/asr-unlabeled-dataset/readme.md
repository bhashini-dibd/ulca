# ULCA Test Datasets : ASR Unlabeled Corpus


## [positive-testcase-01](./positive-testcase-01) 

**Description** : Valid case of audios at utterance level.
> `params.json` : VALID
> `data.json`   : VALID

## [positive-testcase-02](./positive-testcase-02)

**Description** : Valid case of extended audio.
> `params.json` : VALID
> `data.json`   : VALID

## [negative-testcase-01](./negative-testcase-01)

**Description** : 
Incorrect dataset type is specified in the params file.
> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
    "datasetType": "asr-corpus",
```
**Expected correction :**
```json
    "datasetType": "asr-unlabeled-corpus",
```
Refer to [datasetType schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/DatasetType) for the usage.

## [negative-testcase-02](./negative-testcase-01)

**Description** : 

'*collectionMethod*' field is not a part of schema for unlabled corpus. 

> `params.json` : INVALID
> `data.json`   : VALID

**Error :**
```json
        "collectionMethod": {
        "collectionDescription": [
            "auto-aligned"
        ],
        "collectionDetails": {
            "alignmentTool": "AENEAS",
            "alignmentToolVersion": "1.4.0.0"
        }
    }
```
**Expected correction :** This field is not a part of unlabled speech corpus.

Refer to [datasetType schema](https://raw.githubusercontent.com/project-anuvaad/ULCA/develop/specs/common-schemas.yml#/components/schemas/DatasetType) for the usage.