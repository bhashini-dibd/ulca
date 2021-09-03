# ULCA-AUTOMATION

## Overview 

The code in this repo could be utilized to automate procedures for submitting,searching and downloading datasets from ULCA website.

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

* ### For Model (Transalation purpose)

        python3 automate.py -m -n "model-name" -i "input-string"

    Arguments:

    * -d (--model) : Flag for Model functions.
    * -n (--name) : Name of the model
    * -i (--input) : Input string that has to be translated.

* ### For Updating Schema

        python3 automate.py --update-schema

    Arguments:

    * --update-schema : Flag for updating schema 

* ### For Chart Data

        python3 automate.py --chart-data

    Arguments:

    * --chart-data : Flag for getting chart data[languages with its counts] 

To view script usage help from terminal, run:

    python3 automate.py -h

### Content

1. config.py - contains data used for automation.
2. driver_script.py - contains code for loading browsers/driver.
3. core_script.py - contains core functions for automation.
4. dataset_script.py - contains functions for dataset related automation.
5. model_script.py - contains functions for model related automation.
6. automate.py - main file for automation.
7. schema.yml - contains the schema used for automation.
8. requirements.txt - contains python-packages required to run automation. 

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

