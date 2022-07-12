# ULCA #
Universal Language Contribution APIs (ULCA) is an open-sourced scalable data platform, supporting various types of dataset for Indic languages, along with a user interface for interacting with the datasets.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Why ULCA?
* Be the premier data and models repository for Indic language resources
* Collect datasets for MT (Machine Translation), ASR (Automatic Speech Recognition) , TTS (Text To Speech), OCR (Optical Character Recognition) and various NLP tasks in standardized but extensible formats. Please refer to the Datasets section.
* Collect extensive metadata related to dataset for various analysis
* Proper attribution for every contributor at the record level
* Deduplication capability built-in
* Simple interface to search and download datasets based on various filters
* Perform various quality checks on the submitted datasets


## Supported entities in ULCA
### `Datasets`
ULCA allows users to contribute various types of datasets including and not limited to the following :

Dataset Type  | Description
------------- | -------------
Parallel Dataset  | Consists of bi-lingual sentence pairs which are meaningfully the same.
ASR/TTS Dataset  | Consists of audio to text mapping
ASR Unlabeled Dataset | These are raw ASR datasets without transcript value.
OCR Dataset | Consists of image to text mapping
Monolingual Dataset | Consists of sentences in a single language

#### Supported functionalities :
* Submit a new dataset from the above mentioned types
* Delete any of the submitted datasets.
* Upload a newer version of the submitted dataset with more information. (Ex : v2 of PIB dataset)
* Enhance the quality of the datasets submitted by others (Ex : add alignment score etc)


### `Models`
Users can contribute various types of models 
*(Note : ULCA doesn’t host the models, rather it refers to the inference endpoints specified by the contributors)*

Model Type  | Description
------------- | -------------
Translation Model |  Model to translate a given sentence in one language into the sentence in another language.
ASR Model | Model to convert audio into respective transcript.
TTS Model | Model to convert a text into respective audio.
OCR Model | Model to convert a given image to text.

#### Supported functionalities :
* Submit any new model from the above mentioned types
* Inference support for the model
* Run benchmarking for the submitted models
* Publish a model for anyone to infer


### `Benchmarking suite`
As part of ULCA, qualified subject matter experts can submit the benchmarking datasets, which can be used to evaluate various models. The process of benchmarking will be available for any submitted model.

#### Supported functionalities :
Submit any new model from the above mentioned types


## Codebase & Deployment 
ULCA code base is published as an open-sourced project (MIT license) under the following repository :
https://github.com/ULCA-IN/ulca 

#### `Important links`
* ULCA data/model contracts : https://github.com/ULCA-IN/ulca/tree/master/specs  
* Sample usages : https://github.com/ULCA-IN/ulca/tree/master/specs/examples 
* Test datasets : https://github.com/ULCA-IN/ulca/tree/master/ulca-test-datasets  


| Service | Build Status |
|---------| ----------- |
|  Ingest  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fstaging%2Fbuild-master%2Fdataset-ingest)](https://jenkins.ulcacontrib.org/job/ULCA/job/staging/job/build-master/job/dataset-ingest/) |
| Publish | [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fstaging%2Fbuild-master%2Fpublish)](https://jenkins.ulcacontrib.org/job/ULCA/job/staging/job/build-master/job/publish/) |
| User Management | [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fstaging%2Fbuild-master%2Fuser-management)](https://jenkins.ulcacontrib.org/job/ULCA/job/staging/job/build-master/job/user-management/) |
| Validate | [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fstaging%2Fbuild-master%2Fvalidate)](https://jenkins.ulcacontrib.org/job/ULCA/job/staging/job/build-master/job/validate/) |
|  Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/run-tests/) |


# Contribution
It's fairly easy to contribute dataset to ULCA ecosystem. The submitter just have to upload a zip folder containing two textual files and optional reference files like audio or image. The textual file content can be in JSON or CSV format.
The naming convention of textual file should be :
- `params.json` or `params.csv`
- `data.json` or `data.csv`

## Supported Dataset Types
ULCA system currently supports the following type of datasets :
- Parallel dataset
- Monolingual dataset
- ASR / TTS dataset
- OCR dataset
- Document Layout dataset

## Data and Params schema for parallel dataset
- [ParallelDatasetParamsSchema schema](../../dataset-schema.yml#ParallelDatasetParamsSchema)
- [ParallelDatasetRowSchema schema](../../dataset-schema.yml#ParallelDatasetRowSchema)

## Data and Params schema for monolingual dataset
- [MonolingualParamsSchema schema](../../dataset-schema.yml#MonolingualParamsSchema)
- [MonolingualRowSchema schema](../../dataset-schema.yml#MonolingualRowSchema)

## Data and Params schema for asr / tts dataset
- [ASRParamsSchema schema](../../dataset-schema.yml#ASRParamsSchema)
- [ASRRowSchema schema](../../dataset-schema.yml#ASRRowSchema)

## Data and Params schema for ocr dataset
- [DocumentOCRParamsSchema schema](../../dataset-schema.yml#DocumentOCRParamsSchema)
- [DocumentOCRRowSchema schema](../../dataset-schema.yml#DocumentOCRRowSchema)

## Representing a dataset `params`
ULCA relies upon the submitter to explain their dataset, so that it can be beneficial to the large community, following some of the suggestions will surely benefit the community at large.
 
`params` file should contain the discussed attributes.
 
Dataset should have the following mandatory attributes, we will cover each of them individually. Please note the mandatory attributes and values assigned to these attributes are _strictly_ enforced.
- datasetType
- languages
- collectionSource
- domain
- license
- submitter

Following are the optional attributes :
- version

### datasetType
This defines the type of dataset (parallel/monolingual/asr etc). The values can be referred in [DatasetType](../../common-schemas.yml#DatasetType)
>
Sample usage :
```json

 "dataset-type": "parallel-corpus"

```

### languages
It is important to convey what language the dataset is directed towards. The structure of `languages` attributes should be followed. Same parameter can be used to define a single language or a language pair. Let's look at the following example where the `languages` defines a parallel dataset that typically has a language pair where `sourceLanguage` is `English` and `targetLanguage` is `Bengali`. The defined language code are per ISO 639-1 & 639-2 and can be referred in [LanguagePair](../../common-schemas.yml#LanguagePair)

```json
{
   "sourceLanguage": "en",
   "targetLanguage": "bn"
}
```
Monolingual, ASR/TTS, OCR dataset typically uses a single language and the following example can be used to define the `languages` attribute.

```json
 "sourceLanguage": "en" 
```

### domain
This attribute defines that `relevant business area or domain` under which dataset is curated. ULCA _ONLY_ accepts  one values that are defined under [Domain schema](../../common-schemas.yml#Domain).
>
Sample usage :
 - domain specifically for `legal` domain

```json
 "domain": "legal"
```
- dataset meant for `news` domain
```json
 "domain": "news"
```

### license
This attribute is bit straight forward, dataset submitter should choose on from available [License](../../common-schemas.yml#License).
>
Sample usage:
```json
  "license": "cc-by-4.0"
```

### collectionSource
This attribute is mostly free text and optional, however we recommend it to be descriptive so that community users should be able to look at the sources from where the dataset has been curated. Mostly putting a URL along with some description should suffice. 
>
Sample usage : 
```json
  "collectionSource": [
     "https://main.sci.gov.in",
     "42040.pdf",
     "SCI judgment pdfs"
  ]
```

### submitter
The attribute holds the description of the user who submitted the dataset as well as the team members who are part of the project, we suggest acknowledging all team members how small the contribution could be. Typically it should describe the project or team's goal. 
>
Sample usage : 
 
```json
 {
        "submitter": {
            "name": "Project Anuvaad",
            "aboutMe": "Open source project run by ekStep foundation, part of Sunbird project"
        },
        "team": [
            {
                "name": "Ajitesh Sharma",
                "aboutMe": "NLP team lead at Project Anuvaad"
            },
            {
                "name": "Vishal Mauli",
                "aboutMe": "Backend team lead at Project Anuvaad"
            },
            {
                "name": "Aravinth Bheemraj",
                "aboutMe": "Data engineering team lead at Project Anuvaad"
            },
            {
                "name": "Rimpa Mondal",
                "aboutMe": "Freelancer Bengali translator at Project Anuvaad"
            }
        ]
    }
```

## Representing a specific type dataset `params`
This section explains the `params` specific to supported dataset type. We will go through each dataset type individually and in detail.
 
## Parallel Dataset specific `params`
Parallel dataset `params` have few specific attributes defined below

 - collectionMethod
 
#### collectionMethod
This attribute is an optional field in `params` for the parallel dataset. It's a combination of `collectionDescription` and `collectionDetails`. `collectionDescription` is a mandatory property if a `collectionMethod` is included, which actually defines the methods the user has used for creating the dataset.
>
Sample usage :
```json
    "collectionMethod": {
        "collectionDescription": [
            "machine-translated-post-edited"
        ],
        "collectionDetails": {
            "translationModel": "Google",
            "translationModelVersion": "v2",
            "editingTool": "Anuvaad",
            "editingToolVersion": "v1.4",
            "contributor": {
                "name": "Aravinth Bheemaraj",
                "aboutMe": "NLP Data team lead at Project Anuvaad"
            }
        }
    }
```
The values for the `collectionDescription` can be found [here](../../dataset-schema.yml#ParallelDatasetCollectionMethod)
Based on the collection method defined, the `collectionDetails` can one of the 4 available schemas.
See detailed sample usage at [data.json](./parallel-dataset/machine-translated-target-validated/data.json) and [params.json](./parallel-dataset/machine-translated-target-validated/params.json)

In order to do bitext mining at large scale, submitters often leverage strategies like LaBSE, LASER etc. to align and generate parallel corpus. This strategy at large scale bitext mining has helped the community at large. Use this property in `params` to indicate your bitext mining strategy and also report `alignmentScore` property in `data` for every record. A sample record is defined below :
```json
    {
        "sourceText": "In the last 24 hours, 4,987 new confirmed cases have been added.",
        "targetText": "उन्होंने बताया कि पिछले 24 घंटे में 4987 नए मामलों की पुष्टि हुई है।",
        "collectionMethod": {
            "collectionDetails": {
                "alignmentScore": 0.79782
            }
        }
    }   }
    }
```

ULCA will reject those records not satisfying the mentioned criterion.
We have explained this scenario in the example, [data.json](./parallel-dataset/web-scrapped-labse-aligned/data.json) and [params.json](./parallel-dataset/web-scrapped-labse-aligned/params.json)


## OCR Dataset specific `params`
Listed properties are specific to OCR dataset.
  - format
  - dpi
  - imageTextType

### format
Describe the image file format present in the submitted dataset, choose from following image type. Also refer to the example provided.
  - jpeg
  - bmp
  - png
  - tiff

```json
  "format": "tiff" 
```
### dpi
Describes the standard image metadata about pixel density.
  - 300_dpi
  - 72_dpi

```json
  "dpi": "72_dpi" 
```

### imageTextType
This property defines the presence of text on various categories of image. For example a text region can be present on scene or let's say on a document. Following are various defined possibilities here.
  - scene-text
  - typewriter-typed-text
  - computer-typed-text
  - handwritten-text

user can use these options as follows based upon text annotation done on the image type.

```json
  "imageTextType": "computer-typed-text" 
```
