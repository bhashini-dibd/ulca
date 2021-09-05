#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 10:42:43 2021.

@author: dhiru579 @ Tarento.
"""

# username-&-password-for-ULCA-website
ULCA_USERNAME = "dhiraj.suthar@tarento.com"  # "aravinth.bheemaraj@tarento.com"
ULCA_PASSWORD = "Dhiraj@1234"                     # "Ulca@123"

DEFAULT_ENV = "stage"        # environments-for-ULCA-website[stage,prod,dev]


# wait-time-for-waiting-in-sec
COMMON_WAIT_TIME = 2        # waitTime for elements inBROWSER (min=2, max=5)
PENDING_WAIT_TIME = 30      # waitTime for pending-requests (min=50, max=200)


# default-browser
DEFAULT_BROWSER = "chrome"
BROWSER_HEADLESS_MODE = True        # if True then no-browser-window(headless)
CHROME_DRIVER_PATH = "chromedriver"  # path for chromedriver
FIREFOX_DRIVER_PATH = "geckodriver"  # path for firefox-gecko-driver
OPERA_DRIVER_PATH = "operadriver"    # path for operadriver


# schema-related-things
SCHEMA_FILENAME = "schema.yml"
ULCA_SCHEMAFILE_URL = "https://raw.githubusercontent.com/project-anuvaad" + \
    "/ULCA/master/specs/common-schemas.yml"
