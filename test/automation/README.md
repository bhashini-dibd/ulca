# ULCA-AUTOMATION

## Overview 

The code in this repo could be utilized to automate procedures for submitting,searching and downloading datasets from ULCA website.

>IMPORTANT : This Script requires Browser and its Respective Driver [Supported Browsers - Chrome, Firefox, Opera].

## Usage

* ### For Login

        python3 automate.py -l

    Arguments:

    * -l    (--login) : flag for login credentials.

* ### For Submitting

    1. **Single Dataset:**

            python3 automate.py -s -n "dataset-1" -url "https://example.com/dataset-01.zip"

        Arguments:

        * -s (--submit) : Flag for Submitting Dataset
        * -n (--name) : Dataset Name
        * -url (--url)  : Valid Dataset URL

     2. **Multiple Datasets (using csv):**

            python3 automate.py -s -i "1.csv"

        Arguments:

        * -s (--submit) : Flag for Submitting Dataset
        * -i (--input) : input csv file (default-encoding: utf-8)

* ### For Search and Download

        python3 automate.py -d -src english -tgt tamil,telugu -t parallel

    Arguments:

    * -d (--download) : Flag for Searching and Downloading Dataset
    * -t (--type) : Valid Dataset Type
    * -src (--source) : Valid Source Language
    * -tgt (--target) : Valid Target Language
    * [optional] -dom (--domain) : domain of the dataset
    * [optional] -col (--collection-method) : collection method for the dataset
    * [optional] -ma (--m-annotators) : flag for Vetted by multiple annotators
    * [optional] -mt (--m-translators) : flag for manually translated by multiple translators

* ### For Supported stuff

        python3 automate.py --support

    Arguments:

    * --support : Flag for printing supported

To view script usage help from terminal, run:

    python3 automate.py -h

### Content

1. config.py - contains data used for automation.
2. driver_script.py - contains code for loading browsers/driver.
3. core_script.py - contains core functions for automation.
4. automate.py - main file for automation.

### Requirements

To install necessary packages for the script, run:

    pip install -r requirements.txt

## Notes

- update username/password [`ULCA_USERNAME` / `ULCA_PASSWORD`] in config.py file.
- For changing the Browser and Driver path, Update the config.py file
- default column names are ["Dataset Name"], ["Dataset URL"]
- Required Drivers for Browser:
    - Google Chrome - chromedriver
    - Mozilla Firefox - geckodriver
    - Opera - operadriver

## Resources

