## Introduction
Its faily easy to contribute dataset to ULCA ecosystem. The submitter just have to upload a zip folder containing two textual files and optional reference files like audio or image. The textual file content can be in JSON or CSV format. 

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
  - [params schema](../../dataset-schema.yml#ParallelDatasetParamsSchema)
  - [data schema](../../dataset-schema.yml#ParallelDatasetRowSchema)

## data and params schema for monolingual dataset
  - [params schema](../../dataset-schema.yml#MonolingualParamsSchema)
  - [data schema](../../dataset-schema.yml#MonolingualRowSchema)

## data and params schema for asr / tts dataset
  - [params schema](../../dataset-schema.yml#ASRParamsSchema)
  - [data schema](../../dataset-schema.yml#ASRRowSchema)

## data and params schema for ocr dataset
  - [params schema](../../dataset-schema.yml#DocumentOCRParamsSchema)
  - [data schema](../../dataset-schema.yml#DocumentOCRRowSchema)


