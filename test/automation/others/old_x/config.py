#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 10:42:43 2021.

@author: dhiru579 @ Tarento.
"""

# username-&-password-for-ULCA-website
ULCA_USERNAME = "dhiraj.suthar@tarento.com"
ULCA_PASSWORD = "Dhiraj@1234"

DEFAULT_ENV = "prod"  # environments-for-ULCA-website[stage,prod,dev]


# wait-time-for-waiting-in-sec
COMMON_WAIT_TIME = 2  # waitTime for elements inBROWSER (min=2, max=5)
PENDING_WAIT_TIME = 30  # waitTime for pending-requests (min=20, max=60)


# default-browser
DEFAULT_BROWSER = "chrome"
BROWSER_HEADLESS_MODE = True  # if True then no-browser-window(headless)
CHROME_DRIVER_PATH = "chromedriver"  # path for chromedriver
FIREFOX_DRIVER_PATH = "geckodriver"  # path for firefox-geckodriver
OPERA_DRIVER_PATH = "operadriver"  # path for operadriver

# wrapper-variables:
WRAPPER_DS_NAME = ""
WRAPPER_DS_URL = ""

# schema-related-things
SCHEMA_FILENAME = "schema.yml"
ULCA_SCHEMAFILE_URL = (
    "https://raw.githubusercontent.com/ULCA-IN/ulca/"
    + "master/specs/common-schemas.yml"
)
