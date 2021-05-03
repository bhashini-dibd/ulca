## Introduction
It's fairly easy to contribute dataset to ULCA ecosystem. The submitter just have to upload a zip folder containing two textual files and optional reference files like audio or image. The textual file content can be in JSON or CSV format.
 
The naming convention of textual file should be
 - params.json or params.csv
 - data.json or data.csv
 
## Supported dataset types
ULCA currently allow following type of dataset
 - parallel dataset
 - monolingual dataset
 - asr / tts dataset
 - ocr dataset
 
## data and params schema for parallel dataset
 - [ParallelDatasetParamsSchema schema](../../dataset-schema.yml#ParallelDatasetParamsSchema)
 - [ParallelDatasetRowSchema schema](../../dataset-schema.yml#ParallelDatasetRowSchema)
 
## data and params schema for monolingual dataset
 - [MonolingualParamsSchema schema](../../dataset-schema.yml#MonolingualParamsSchema)
 - [MonolingualRowSchema schema](../../dataset-schema.yml#MonolingualRowSchema)
 
## data and params schema for asr / tts dataset
 - [ASRParamsSchema schema](../../dataset-schema.yml#ASRParamsSchema)
 - [ASRRowSchema schema](../../dataset-schema.yml#ASRRowSchema)
 
## data and params schema for ocr dataset
 - [DocumentOCRParamsSchema schema](../../dataset-schema.yml#DocumentOCRParamsSchema)
 - [DocumentOCRRowSchema schema](../../dataset-schema.yml#DocumentOCRRowSchema)
 
## Representing a dataset
ULCA relies upon the submitter to explain their dataset so that it can be beneficial to the large community, following some of the suggestions will surely benefit the community at large.
 
Dataset should have the following mandatory attributes, we will cover each of them individually. Please note the mandatory attributes and values assigned to these attributes are _strictly_ enforced.
 - languages
 - domain
 - collectionMethod
 - license
 
Following are optional attributes
 - collectionSource
 
## languages
It is important to convey what language the dataset is directed toward. The structure of `languages` attributes should be followed. Same parameter can be used to define a single language or or a language pair. Let's look at the following example where the `languages` defines a parallel dataset that typically has a language pair where `sourceLanguage` is `English` and `targetLanguage` is `Bengali`. The defined language code are per ISO 639-1 & 639-2 and can be referred in [LanguagePair](../../common-schemas.yml#LanguagePair)
 
```
 {
         "sourceLanguage": {
             "value": "en",
             "name": "English"
         },
         "targetLanguage": {
             "value": "bn",
             "name": "Bengali"
         }
  }
```
Monolingual or ASR/TTS or OCR dataset typically uses a single language and the following example can be used to define the `languages` attribute.
 
```
 {
         "sourceLanguage": {
             "value": "en",
             "name": "English"
         }
 }
```
 
## domain
This attribute defines that `relevant business area or domain` under which dataset is curated. ULCA _ONLY_ accepts  one or more values that are defined under [Domain schema](../../common-schemas.yml#Domain).
 
Few examples are following
 
domain specifically for `legal` domain
```
[
 "legal"
]
```
or
 
dataset meant for `legal`, `news` domain
```
[
 "legal", "news"
]
```
 
### collectionMethod
The attribute defines `how the dataset has been curated or created ?`. ULCA _ONLY_ accepts  one or more values that are defined under [CollectionMethod schema](../../common-schemas.yml#CollectionMethod).
 
Let's take a few examples to understand the same.
### [parallel dataset examples](./examples/dataset/parallel-dataset)
 
 - Let's say that team A has scrapped the pages from [PIB website](https://www.pib.gov.in/Allrel.aspx), identified various parallel html pages, extracted the textual data, tokenized to get sentences and used an alignment strategy like LaBSE to align the sentences.
 The textual data has been extracted from html tags so we use `web-scrapping-machine-readable` and finally sentence alignment has been done using LaBSE that is represented as `algorithm-auto-aligned`. This can be expressed as:
 
 ```
   [
     "web-scrapping-machine-readable", "algorithm-auto-aligned"
   ]
 ```
 
 - Let's take another example, team B has downloaded a judgment from [Supreme Court of India](https://main.sci.gov.in), assume that using OCR technique textual data has been extracted from the judgment document, tokenized to get sentences and used an alignment strategy like LaBSE to align the sentences.
 The textual data has been extracted from html tags so we use `web-scrapping-ocr` and finally sentence alignment has been done using LaBSE that is represented as `algorithm-auto-aligned`. This can be expressed as:
 
 ```
   [
     "web-scrapping-ocr", "algorithm-auto-aligned"
   ]
 ```
### license
This attribute is bit straight forward, dataset submitter should choose on from available [Licenses](../../common-schemas.yml#License).

```
[
  "cc-by-4.0"
]
```
### collectionSource
This attribute is mostly free text and optional, however we recommend it to be descriptive so that community user should able to look at the sources from where the dataset has been curated. Mostly putting a URL along with some description should suffice. Have look at the example.

```
[
  "https://main.sci.gov.in", "42040.pdf", "SCI judgment pdfs", 
]
```