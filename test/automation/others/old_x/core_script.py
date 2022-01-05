#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 11:04:13 2021.

@author: dhiru579 @ Tarento.
"""
import time
import io
import yaml
import requests
from termcolor import colored
import simpleaudio as sa
from sacrebleu.metrics import BLEU
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By

import config
import elements as ele

ENV_DICT = {"stage": "stage", "dev": "dev", "prod": "meity"}
LANGUAGE_DICT = {
    "bn": "bengali",
    "hi": "hindi",
    "en": "english",
    "mr": "marathi",
    "ta": "tamil",
    "te": "telugu",
    "kn": "kannada",
    "gu": "gujarati",
    "pa": "punjabi",
    "ml": "malayalam",
    "as": "assamese",
    "or": "odia",
    "ur": "urdu",
}


# ULCA-urls
ULCA_DOMAIN = "https://" + ENV_DICT[config.DEFAULT_ENV] + "."
ULCA_LOGIN_URL = ULCA_DOMAIN + "ulcacontrib.org/user/login"
ULCA_DASH_URL = ULCA_DOMAIN + "ulcacontrib.org/dashboard"

ULCA_DS_SUBMIT_URL = ULCA_DOMAIN + "ulcacontrib.org/dataset/upload"
ULCA_DS_CONTRIB_URL = ULCA_DOMAIN + "ulcacontrib.org/dataset/my-contribution"
ULCA_DS_MYSRCH_URL = ULCA_DOMAIN + "ulcacontrib.org/my-searches"
ULCA_DS_SD_URL = (
    ULCA_DOMAIN + "ulcacontrib.org/search-and-" + "download-rec/initiate/-1"
)

ULCA_MDL_SUBMIT_URL = ULCA_DOMAIN + "ulcacontrib.org/model/upload"
ULCA_MDL_EXPLR_URL = ULCA_DOMAIN + "ulcacontrib.org/model/explore-models"
ULCA_MDL_CONTRIB_URL = ULCA_DOMAIN + "ulcacontrib.org/model/my-contribution"
ULCA_MDL_BMARK_DS_URL = ULCA_DOMAIN + "ulcacontrib.org/model/benchmark-datasets"


def get_url(url, driver):
    """
    get_url function loads the URL on the browser/driver.

    Parameters
    ----------
    url : str
        url that has to be loaded.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    if driver.current_url != url:
        driver.get(url)
    else:
        driver.refresh()
    time.sleep(config.COMMON_WAIT_TIME)
    return driver


def perform_webpage_function(element_data, function, driver, input_data=None):
    """
    perform_webpage_functions executes reuiresd functions on webpage.

    supported functions - click,input,dropdown,href,text
    Parameters
    ----------
    element_data : list
        element with name,xpath.
    function : str
        functions like click,input.
    driver : selenium.driver
        A Browser window object.
    input_data : str, optional
        input data for input_fields. The default is None.

    Returns
    -------
    status : bool
        True if no error.
    status_str : str
        string for status.

    """
    try:
        element = driver.find_element_by_xpath(element_data[1])
        status = True
        status_str = None
        if function == "click":
            element.click()
            time.sleep(config.COMMON_WAIT_TIME)
        if function == "is_enabled":
            status = element.is_enabled()
            status_str = ""
        if function == "input":
            element.send_keys(input_data)
            time.sleep(config.COMMON_WAIT_TIME)
        if function == "text":
            status_str = element.text.strip()
        if function == "href":
            status_str = element.get_attribute("href")
        if function == "dropdown":
            element.click()
            element.send_keys(input_data)
            time.sleep(1)
            element.send_keys(Keys.DOWN)
            element.send_keys(Keys.ENTER)
            element.send_keys(Keys.ESCAPE)
            time.sleep(1)
    except Exception:
        status = False
        status_str = "ELEMENT=" + element_data[0] + " - NOT FOUND"
        # print(e)
    return status, status_str


def perform_logout(driver):
    """
    perform_logout functions automates the logout process.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    print("LOGOUT : ", flush=True, end="")
    driver = get_url(ULCA_DASH_URL, driver)
    status, status_str = perform_webpage_function(ele.DASH_PROFILE_BTN, "click", driver)
    if status:
        status, status_str = perform_webpage_function(
            ele.DASH_LOGOUT_BTN, "click", driver
        )
    if status is False:
        print(colored("FAILED", "red"))
    else:
        print(colored("SUCCESS", "green"))
    return driver


def print_status(status, status_str, driver):
    """
    print_status function prints if failed/success on the terminal/console.

    Parameters
    ----------
    status : bool
        True if no error.
    status_str : str
        string for status.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    if status is False:
        print(colored("FAILED", "red"), end=" - ")
        print(status_str)
    else:
        end = " "
        if status_str != "":
            end = " - "
        print(colored("SUCCESS", "green"), end=end)
        if isinstance(status_str, int):
            print("SRN=", end="")
        if status_str is not None:
            print(status_str)
    return driver


def perform_login(driver):
    """
    perform_login automates the login process.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    status : bool
        True if no error.
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    username = config.ULCA_USERNAME
    password = config.ULCA_PASSWORD
    print("LOGIN  : ", flush=True, end="")
    if len(username.strip()) == 0 or len(password.strip()) == 0:
        status = False
        status_str = "username/password empty"
    else:
        driver = get_url(ULCA_LOGIN_URL, driver)
        status, status_str = perform_webpage_function(
            ele.LOGIN_USERNAME_INP, "input", driver, input_data=username
        )
        if status:
            status, status_str = perform_webpage_function(
                ele.LOGIN_PASSWORD_INP, "input", driver, input_data=password
            )
        if status:
            status, status_str = perform_webpage_function(
                ele.LOGIN_BTN, "click", driver, input_data=password
            )
        if status:
            try:
                driver.find_element_by_xpath(ele.DASH_PROFILE_BTN[1])
                status = True
                login_str = ""
            except Exception:
                status = False
                login_str = "Invalid Credentials"

    driver = print_status(status, login_str, driver)

    return status, driver


def load_yaml_data(string):
    """
    load_yaml_data fetches the data from yaml schema file based on input-str.

    Parameters
    ----------
    string : str
        name of the data.

    Returns
    -------
    data_list : list
        list containing data.

    """
    with open(config.SCHEMA_FILENAME, "r") as obj:
        data_list = yaml.safe_load(obj)
    data_list = data_list[string]
    return data_list


def get_file(name, URL):
    """
    get_file downloads the file in the url.

    Parameters
    ----------
    name : str
        name of the file to be given.
    URL : str
        url of the file.

    Returns
    -------
    status_str : str
        name given to the downloaded file else N/A.

    """
    try:
        r = requests.get(URL)
        with open(name, "wb") as f:
            f.write(r.content)
        status_str = name
    except Exception:
        status_str = "N/A"
    status_str = str(status_str)
    return status_str


def get_yaml_data(URL):
    """
    get_yaml_data url retrieves data from the url specified.

    Parameters
    ----------
    URL : str
        url of the yaml schema file.

    Returns
    -------
    status : bool
        True if no error.
    status_str : str
        string for status.
    data : dict
        dict containing yaml data.

    """
    try:
        yaml_file = requests.get(URL)
        yaml_data = yaml.safe_load(yaml_file.text)
        yaml_data = yaml_data["components"]["schemas"]
        lng = yaml_data["LanguagePair"]["properties"]["sourceLanguage"]["enum"]
        domains = yaml_data["Domain"]["items"]["enum"]
        coll_methods = yaml_data["CollectionMethod"]["items"]["enum"]
        types = yaml_data["DatasetType"]["enum"]
        data = {
            "Languages": lng,
            "CollectionMethods": coll_methods,
            "Domains": domains,
            "DataTypes": types,
        }
        status = True
        status_str = ""
    except Exception:
        status = False
        status_str = "URL/Schema has Changed"
        data = None
    return status, status_str, data


def write_yaml_data(data, filename):
    """
    write_yaml_data function writes the data inth specified file.

    Parameters
    ----------
    data : dict
        dict containing yaml data.
    filename : str
        name of the file.

    Returns
    -------
    status : bool
        True if no error.
    status_str : str
        string for status.

    """
    try:
        with open(filename, "w") as wrt:
            yaml.dump(data, wrt, default_flow_style=False, allow_unicode=True)
        status = True
        status_str = "updated into 'schema.yml'"
    except Exception:
        status = False
        status_str = "coudnot write into 'schema.yml'"
    return status, status_str


def update_schema(URL, filename):
    """
    update_schema functions updates the schema for the automation.

    Parameters
    ----------
    URL : str
        url for the data.
    filename : str
        file name where the data has to be saved.

    Returns
    -------
    None.

    """
    print("SCHEMA : ", flush=True, end="")
    status, string, data = get_yaml_data(URL)
    if status is True:
        status, string = write_yaml_data(data, filename)
    driver = print_status(status, string, None)
    if status is True:
        print()
        for i in data:
            print(i, "=", data[i])
            print()
    del driver
    return None





def get_chart_data_values(src_count, driver):
    """
    get_chart_data_values fetches the name and counts list feo, the chart.

    Parameters
    ----------
    src_count : str
        count of the source languages if available else "" (nothing).
    driver : TYPE
        DESCRIPTION.

    Returns
    -------
    status : bool
        True if no error.
    status_str : str
        string for status.
    lang_names : list
        lang-names from the chart.
    lang_counts : list
        lang-counts from the chart..
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    lang_names = []
    lang_counts = []
    if status:
        try:
            time.sleep(2)
            lang_names = driver.find_element_by_tag_name("svg")
            lang_names = lang_names.find_elements_by_xpath(ele.DASH_CHART_LNAMES_LI[1])
        except Exception:
            status = False
            status_str = "ELEMENT=" + ele.DASH_CHART_LNAMES_LI[0] + " - NOT FOUND"
    if status:
        for i in range(len(lang_names)):
            lang = lang_names[i].text.strip()
            if lang == "0":
                del lang_names[i:]
                break
            else:
                lang_names[i] = lang
    if status:
        try:
            lang_counts = driver.find_elements_by_xpath(ele.DASH_CHART_LCOUNTS_LI[1])
        except Exception:
            status = False
            status_str = "ELEMENT=" + ele.DASH_CHART_LCOUNTS_LI[0] + " - NOT FOUND"
    if status:
        del lang_counts[0]
        del lang_counts[0]
        for i in range(len(lang_counts)):
            count = lang_counts[i].text.strip().replace(",", "")
            lang_counts[i] = str(count)

        status, status_str = perform_webpage_function(
            ele.DASH_CHART_TOTCNT_TXT, "text", driver
        )
    if status:
        if status_str.find(".") > -1:
            status_str += " hours"
        status_str = "Total-count=" + status_str + src_count
    return status, status_str, lang_names, lang_counts, driver


def select_group_chart(tgt, groupby, driver):
    """
    select_group_chart find the tgt and changes the grouping for chart.

    Parameters
    ----------
    tgt : str
        target language.
    groupby : str
        groupby [submitter,domain,collection].
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    status : bool
        True if no error.
    status_str : str
        string for status.
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    status, status_str, names, counts, driver = get_chart_data_values("1", driver)
    if status:
        tgt = LANGUAGE_DICT[tgt].capitalize()
        try:
            lang_index = names.index(tgt)
        except Exception:
            status = False
            status_str = "target Language not available in chart."
        if status:
            try:
                lang_names = driver.find_element_by_tag_name("svg")
                lang_names = lang_names.find_elements_by_xpath(
                    ele.DASH_CHART_LRECTS_LI[1]
                )
            except Exception:
                status = False
                status_str = "ELEMENT=" + ele.DASH_CHART_LRECTS_LI[0] + " - NOT FOUND"
    if status:
        lang_names[lang_index].click()
    groupby_list = ["domain", "collection", "submitter"]
    if groupby != "":
        if groupby not in groupby_list:
            status = False
            status_str = "not a valid group by selector."
        if status:
            if groupby == "submitter":
                status, status_str = perform_webpage_function(
                    ele.DASH_CHART_GROUPSUBM_BTN, "click", driver
                )
            elif groupby == "collection":
                status, status_str = perform_webpage_function(
                    ele.DASH_CHART_GROUPCOLLM_BTN, "click", driver
                )
            else:
                pass
    return status, status_str, driver


def get_chart_data(dataset_type, groupby, src, tgt, driver):
    """
    get_chart_data function retrieves the data from dashboard-chart.

    Parameters
    ----------
    dataset_type : str
        type of dataset.
    groupby : str
        groupby method.
    src : str
        source language.
    tgt : str
        target language.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    driver = get_url(ULCA_DASH_URL, driver)
    print("CHART-DATA : ", flush=True, end="")
    if status:
        status, status_str = perform_webpage_function(
            ele.DASH_DTYPE_SLCT_BTN, "click", driver
        )
    if status:
        status, status_str, driver = select_chart_datatype(dataset_type, src, driver)
        src_count = status_str
        if src_count is None:
            src_count = ""
    if status:
        if len(tgt) != 0:
            if len(tgt) > 1:
                status = False
                status_str = "only one target language can be used."
            if status:
                tgt = tgt[0]
            if status:
                if tgt not in load_yaml_data("Languages"):
                    status = False
                    status_str = "not a supported target language"
    if status:
        if tgt in load_yaml_data("Languages"):
            status, status_str, driver = select_group_chart(tgt, groupby, driver)
    if status:
        status, status_str, names, counts, driver = get_chart_data_values(
            src_count, driver
        )

    driver = print_status(status, status_str, driver)

    if status is True:
        print()
        for i in range(min(len(names), len(counts))):
            print(names[i], "=", counts[i])
        print()

    return driver


def test_elements_with_browser(driver):
    """
    test_elements_with_browser functions tests all the elements if available.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    TEST_DASH = ["DASHBOARD-PAGE", ULCA_DASH_URL, ele.TEST_DASH]
    TEST_DS_SUBMIT = ["DATASET-SUBMIT-PAGE", ULCA_DS_SUBMIT_URL, ele.TEST_DS_SUBMIT]
    TEST_DS_SD = ["DATASET-S/DOWNLOAD-PAGE", ULCA_DS_SD_URL, ele.TEST_DS_SD]
    TEST_MDL_SUBMIT = ["MODEL-SUBMIT-PAGE", ULCA_MDL_SUBMIT_URL, ele.TEST_MDL_SUBMIT]

    test_list = [TEST_DASH, TEST_DS_SD, TEST_DS_SUBMIT, TEST_MDL_SUBMIT]
    for test in test_list:
        status = True
        status_str = ""
        print("{} : ".format(test[0]), end="", flush=True)
        driver = get_url(test[1], driver)
        fail_list = []
        for t in test[2]:
            try:
                wait = WebDriverWait(driver, 2)
                wait.until(EC.presence_of_element_located((By.XPATH, t[1])))
            except Exception:
                fail_list.append(t[0])
        if len(fail_list) == 0:
            status = True
            status_str = "{0}/{0}".format(len(test[2]))
        else:
            status = False
            status_str = "{0}/{1} - {2}".format(
                len(fail_list), len(test[2]), str(fail_list)
            )
        driver = print_status(status, status_str, driver)
    return driver


def print_no_flag_found(driver):
    """
    print_no_flag_found functions returns no flag found error.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = False
    status_str = "No flag found - available flags [model -m, dataset -d]"
    print("FLAG : ", flush=True, end="")
    driver = print_status(status, status_str, driver)
    return driver


def test_public_website(driver):
    print("PUBLIC-MODEL-PAGE: ", end="", flush=True)
    driver = get_url(ULCA_MDL_EXPLR_URL, driver)
    fail_list = []
    for t in ele.TEST_PUBLIC_WEB:
        try:
            wait = WebDriverWait(driver, 2)
            wait.until(EC.presence_of_element_located((By.XPATH, t[1])))
        except Exception:
            fail_list.append(t[0])
    if len(fail_list) == 0:
        status = True
        status_str = "{0}/{0}".format(len(ele.TEST_PUBLIC_WEB))
    else:
        status = False
        status_str = "{0}/{1} - {2}".format(
            len(fail_list), len(ele.TEST_PUBLIC_WEB), str(fail_list)
        )
    driver = print_status(status, status_str, driver)
    return driver


def download_audio_data(url):
    data = requests.get(url)
    audio_obj = io.BytesIO(data.content)
    return audio_obj


def play_audio_data(audio_obj):
    wave_obj = sa.WaveObject.from_wave_file(audio_obj)
    play_obj = wave_obj.play()
    play_obj.wait_done()
    return None


def test_asr_live_record(driver):
    status = True
    status_str = ""
    fail_dict = dict()
    print("MODEL-ASR-RECORDING-STATUS: ", end="", flush=True)
    for data in ele.TEST_ASR_LANGS:
        driver = get_url(ULCA_MDL_EXPLR_URL, driver)
        status, status_str = perform_webpage_function(
            ele.MDL_EXPLR_ASRTAB_BTN, "click", driver
        )
        if status:
            try:
                model_list = driver.find_elements_by_xpath(ele.MDL_EXPLR_MDLLI_TXT[1])
            except Exception:
                status = False
                status_str = "could not load model names."
        m_list = dict()
        if status:
            for i in range(len(model_list)):
                mname = model_list[i].text.lower().strip()
                m_list[mname] = model_list[i]
            if data["name"].lower() not in m_list.keys():
                status = False
                status_str = "could not find model name - " + data["name"]
            else:
                m_list[data["name"].lower()].click()
            time.sleep(2)
        if status:
            # click model-trynow-button
            status, status_str = perform_webpage_function(
                ele.MDL_EXPLR_TRYM_BTN, "click", driver
            )
            time.sleep(2)
        if status:
            audio = download_audio_data(data["url"])
            status, status_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_RECORD_BTN, "click", driver
            )
            time.sleep(5)
        if status:
            play_audio_data(audio)
            time.sleep(2)
            status, status_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_RECORD_BTN, "click", driver
            )
        if status:
            # text at output field of translation
            time.sleep(config.COMMON_WAIT_TIME * 5)
            status, status_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_OPUT_TXT, "text", driver
            )
        fail_count = 0
        if not status:
            break
        else:
            # if status_str != data["sentence"]:
            bleu = BLEU()
            value = bleu.corpus_score([status_str], [[data["sentence"]]]).score
            fail_count += 1
            fail_dict[data["lang"]] = {
                "expected": data["sentence"],
                "inferred": status_str,
                "bleuScore": value,
            }
    if status:
        if len(fail_dict) == 0:
            status = True
            status_str = "{0}/{0}".format(len(ele.TEST_ASR_LANGS))
        else:
            status = False
            status_str = "{0}/{1} - {2}".format(
                len(fail_dict), len(ele.TEST_ASR_LANGS), str(fail_dict)
            )
    driver = print_status(status, status_str, driver)
    return driver
