# NLTM Architecture

Following is the proposed architecture for Bhashini

![NLTM Architecture](https://raw.githubusercontent.com/ULCA-IN/ulca/develop/ulca-documentation/images/nltm_architecture_2.png)

### Components  ###

Component     | Details
--------------| -------------
NLTM cloud  | Where different components of the foundation layer and data repository will be hosted
Data Repository  | contains training and benchmark datasets
Model Repository  | contains models of different types from several contributors
ULCA |  ULCA will standardize all data and model contributions for benchmarking
Foundation layer | set applications and utilities which will enable orchestration among the ecosystem partners
Reference apps layer | The MT, ASR, TTS and OCR models developed in the foundation layer can be used to create applications in various domains.
Ecosystem Apps Layer | consists of the tools and applications available in the market that leverage the Bhashini architecture


# ULCA #
Universal Language Contribution APIs (ULCA) is an open-sourced scalable data platform, supporting various types of dataset for Indic languages, along with a user interface for interacting with the datasets.




# Why ULCA?
* Be the premier data and models repository for Indic language resources
* Collect datasets for MT (Machine Translation), ASR (Automatic Speech Recognition) , TTS (Text To Speech), OCR (Optical Character Recognition) and various NLP tasks in standardized but extensible formats. Please refer to the Datasets section.
* Collect extensive metadata related to dataset for various analysis
* Proper attribution for every contributor at the record level
* Deduplication capability built-in
* Simple interface to search and download datasets based on various filters
* Perform various quality checks on the submitted datasets


# Supported entities in ULCA
## `Datasets`
ULCA allows users to contribute various types of datasets including and not limited to the following :

Dataset Type  | Description
------------- | -------------
Parallel Dataset  | Consists of bi-lingual sentence pairs which are meaningfully the same.
ASR/TTS Dataset  | Consists of audio to text mapping
ASR Unlabeled Dataset | These are raw ASR datasets without transcript value.
OCR Dataset | Consists of image to text mapping
Monolingual Dataset | Consists of sentences in a single language
Transliteration Dataset  | Consists of same sentence pairs but in different languages
Glossary Dataset  | Consists of domain-specific terms with accompanying definitions.

### Supported functionalities :
* Submit a new dataset from the above mentioned types
* Delete any of the submitted datasets.
* Upload a newer version of the submitted dataset with more information. (Ex : v2 of PIB dataset)
* Enhance the quality of the datasets submitted by others (Ex : add alignment score etc)


## `Models`
Users can contribute various types of models 
*(Note : ULCA doesn’t host the models, rather it refers to the inference endpoints specified by the contributors)*

Model Type  | Description
------------- | -------------
Translation Model |  Model to translate a given sentence in one language into the sentence in another language.
ASR Model | Model to convert audio into respective transcript.
TTS Model | Model to convert a text into respective audio.
OCR Model | Model to convert a given image to text.
NER Model | Model to recognize named entities from a given sentence.
Transliteration Model |  Model to transliterate a given sentence in one language into another script.
Txt-Language detection Model | Model to detect the language of a given text. 


### Supported functionalities :
* Submit any new model from the above mentioned types
* Inference support for the model
* Run benchmarking for the submitted models
* Publish a model for anyone to infer


## `Benchmarking suite`
As part of ULCA, qualified subject matter experts can submit the benchmarking datasets, which can be used to evaluate various models. The process of benchmarking will be available for any submitted model.

### Supported functionalities :
Submit any new model from the above mentioned types


# Codebase & Deployment 
ULCA code base is published as an open-sourced project (MIT license) under the following repository :
https://github.com/bhashini-dibd/ulca 

#### `Important links`
* ULCA data/model contracts : https://github.com/bhashini-dibd/ulca/tree/master/specs  
* Sample usages : https://github.com/bhashini-dibd/ulca/tree/master/specs/examples 
* Test datasets : https://github.com/bhashini-dibd/ulca/tree/master/ulca-test-datasets  

# Overview and Demo Video
[![ULCA Overview & Demo](https://img.youtube.com/vi/vyOOAViD6Sk/0.jpg)](https://youtu.be/vyOOAViD6Sk)
