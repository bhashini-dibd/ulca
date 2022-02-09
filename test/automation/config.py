#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Dec  4 17:35:27 2021.

@author: dhiru579 @ Tarento
"""
# username-&-password-for-ULCA-website
ULCA_USERNAME = "ulca.master@gmail.com"
ULCA_PASSWORD = "u1ca@Master"

ULCA_DOMAIN = "stage"  # environment/domain-for-ULCA-website[stage,meity,dev]

AUTOMATION_BROWSER = "chrome"  # supported-chrome,firefox,opera
BROWSER_HEADLESS_MODE = True  # if True then hidden-mode
CHROME_DRIVER_PATH = "chromedriver"  # path for chromedriver
FIREFOX_DRIVER_PATH = "./geckodriver"  # path for firefox-geckodriver
OPERA_DRIVER_PATH = "./operadriver"  # path for operadriver

PENDING_WAIT_TIME = 30  # waitTime for pending-requests (min=20, max=60)

# wrapper-variables:
WRAPPER_DS_NAME = ""
WRAPPER_DS_URL = ""
DOWNLOAD_FOLDER = 'downloads'

# schema-related-things
SCHEMA_FILENAME = "schema.yml"
ULCA_SCHEMAFILE_URL = (
    "https://raw.githubusercontent.com/ULCA-IN/ulca/"
    + "master/specs/common-schemas.yml"
)
