
# Introduction
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
- Transliteration dataset
- Glossary dataset

## Data and Params schema for parallel dataset
- [ParallelDatasetParamsSchema schema](../../dataset-schema.yml#ParallelDatasetParamsSchema)
- [ParallelDatasetRowSchema schema](../../dataset-schema.yml#ParallelDatasetRowSchema)

## Data and Params schema for monolingual dataset
- [MonolingualParamsSchema schema](../../dataset-schema.yml#MonolingualParamsSchema)
- [MonolingualRowSchema schema](../../dataset-schema.yml#MonolingualRowSchema)

## Data and Params schema for transliteration dataset
- [TransliterationDatasetParamsSchema schema](../../dataset-schema.yml#TransliterationDatasetParamsSchema)
- [TransliterationDatasetRowSchema schema](../../dataset-schema.yml#TransliterationDatasetRowSchema)

## Data and Params schema for asr / tts dataset
- [ASRParamsSchema schema](../../dataset-schema.yml#ASRParamsSchema)
- [ASRRowSchema schema](../../dataset-schema.yml#ASRRowSchema)

## Data and Params schema for ocr dataset
- [DocumentOCRParamsSchema schema](../../dataset-schema.yml#DocumentOCRParamsSchema)
- [DocumentOCRRowSchema schema](../../dataset-schema.yml#DocumentOCRRowSchema)

## Data and Params schema for glossary dataset
- [GlossaryDatasetParamsSchema schema](../../dataset-schema.yml#GlossaryDatasetParamsSchema)
- [GlossaryDatasetRowSchema schema](../../dataset-schema.yml#GlossaryDatasetRowSchema)

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
