# ULCA-AUTOMATION


| Service | Build Status |
|---------| ----------- |
| Login Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/model-tests/) |
| Dataset Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/model-tests/) |
| Model Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/model-tests/) |
| Benchmark Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/model-tests/) |
| Leaderboard Test  |  [![Build Status](https://jenkins.ulcacontrib.org/buildStatus/icon?job=ULCA%2Fdevelop%2Ftests%2Frun-tests)](https://jenkins.ulcacontrib.org/job/ULCA/job/develop/job/tests/job/model-tests/) |


## Overview 

The code in this repo could be utilized to automate procedures for submitting/searching datasets and submitting/benchmarking/publishing models from ULCA website.

>IMPORTANT : This Script requires Browser and its Respective Driver [Supported Browsers - Chrome, Firefox, Opera].

## Tests :

    * Login : Tests User Login to ULCA.
    * Search : Tests Searching of Datasets.
    * Download : Tests Downloading of Datasets.  
    * Benchmark : Tests Benchmarking of Datasets against Models with Metrics.
    * Publish/Unpublish : Tests Publish/Unpublish of Models.
    * Chart/LeaderBoard : Tests Sorting of Chart/LeaderBoard.    
    * ASR-Recording : Tests Recording for ASR Models.  
    
>Note : All examples/usage are given below

## Usage

* ### For Login

        python3 automate.py -l

    Arguments:

    * -l    (--login) : flag for login credentials.

* ### For Dataset

     1. **Searching and Downloading Dataset**

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
  
    1. **Running Benchmark on a Model:**

            python3 automate.py -m -n 'modelName' -b 'benchmarkDatasetName' --metric 'metricName'

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * -b (--benchmark) : Benchamrk Name to be run
        * --metric  : Metric name

    2. **Publish/Unpublish a Model:**

            python3 automate.py -m -n 'modelName' --publish
            python3 automate.py -m -n ''modelName' --unpublish

        Arguments:

        * -m (--model) : Flag for Model Functions
        * -n (--name) : Model Name
        * --publish : flag fo publishing a model
        * --unpublish  : flag fo unpublishing a model

* ### For Updating Schema

        python3 automate.py --update-schema

    Arguments:

    * --update-schema : Flag for updating schema[updates schema values for ref in code] 

* ### For Chart Data

        python3 automate.py --chart-data

    Arguments:

    * --chart-data : Flag for getting chart data[languages with its counts] 

* ### For running test on website

        python3 automate.py --test-all
        
    * test functions : 
            1. asr-recording
            2. Leaderboards (sorted)
            3. cards
            4. public pages elements testing

    Arguments:

    * --test-all : Flag for testing elements of website on a browser 

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

