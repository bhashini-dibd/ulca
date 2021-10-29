# ULCA-AUTOMATION

## Overview 

The code in this repo could be utilized to automate procedures for submitting/searching datasets and submitting/benchmarking/publishing models from ULCA website.

>IMPORTANT : This Script requires Browser and its Respective Driver [Supported Browsers - Chrome, Firefox, Opera].

## Usage

* ### For Login

        python3 automate.py -l

    Arguments:

    * -l    (--login) : flag for login credentials.

* ### For Dataset

    1. **Uploading Single Dataset:**

            python3 automate.py -d -n "dataset-1" -url "https://example.com/dataset-01.zip"

        Arguments:

        * -d (--dataset) : Flag for Dataset Functions
        * -n (--name) : Dataset Name
        * -url (--url)  : Valid Dataset URL

     2. **Uploading Multiple Datasets (using csv):**

            python3 automate.py -d -i "1.csv"

        Arguments:

        * -d (--dataset) : Flag for Dataset Functions
        * -i (--input) : input csv file (default-encoding: utf-8)

     3. **Searching and Downloading Dataset**

            python3 automate.py -d -src en -tgt ta,te -t parallel

        Arguments:

        * -d (--dataset) : Flag for Dataset Functions
        * -t (--type) : Valid Dataset Type
        * -src (--source) : Valid Source Language
        * -tgt (--target) : Valid Target Language
        * [optional] -dom (--domain) : domain of the dataset
        * [optional] -col (--collection-method) : collection method for the dataset
        * [optional] -ma (--m-annotators) : flag for Vetted by multiple annotators
        * [optional] -mt (--m-translators) : flag for manually translated by multiple translators
        * [optional] -org (--org-source) : flag for original sentencence in source language

* ### For Model

    1. **Uploading a Model:**

            python3 automate.py -m -n 'model-1' -i 'model-1.json'

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * -i (--input)  : Valid Model File (.json)
  
    2. **Running Benchmark on a Model:**

            python3 automate.py -m -n 'model-1' -b 'benchmark1' --metric 'metric1'

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * -b (--benchmark) : Benchamrk Name to be run
        * --metric  : Metric name

    3. **Publish/Unpublish a Model:**

            python3 automate.py -m -n 'model-1' --publish

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * --publish : flag fo publishing a model
        * --unpublish  : flag fo unpublishing a model
     
    4. **Model Task functions: (eg:translation)**

            python3 automate.py -m -t 'translation' -n "model-name" -i "input-string-or-url"

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * -i (--input)  : Input for Model Task (eg: sentence for Translation Task,URL for OCR/ASR task)

* ### For Updating Schema

        python3 automate.py --update-schema

    Arguments:

    * --update-schema : Flag for updating schema 

* ### For Chart Data

        python3 automate.py --chart-data

    Arguments:

    * --chart-data : Flag for getting chart data[languages with its counts] 

* ### For running test on website

        python3 automate.py --test-website

    Arguments:

    * --test-website : Flag for testing elements of website on a browser 

To view script usage help from terminal, run:

    python3 automate.py -h

### Content

1. config.py - contains data used for automation.
2. driver_script.py - contains code for loading browsers/driver.
3. elements.py - contains xpaths of elements in the website.
4. core_script.py - contains core functions for automation.
5. dataset_script.py - contains functions for dataset related automation.
6. model_script.py - contains functions for model related automation.
7. automate.py - main file for automation.
8. schema.yml - contains the schema used for automation.
9. requirements.txt - contains python-packages required to run automation. 

### Requirements

To install necessary packages for the script, run:

    pip install -r requirements.txt

## Notes

- update username/password [`ULCA_USERNAME` / `ULCA_PASSWORD`] in config.py file.
- For changing the Browser and Driver path, Update the config.py file
- default column names for CSV file are ["Dataset Name"], ["Dataset URL"]
- Required Drivers for Browser:
    - Google Chrome - chromedriver
    - Mozilla Firefox - geckodriver
    - Opera - operadriver

## Resources

