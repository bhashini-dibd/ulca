#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 11:04:13 2021.

@author: dhiru579 @ Tarento.
"""
import time
import yaml
import requests
from termcolor import colored
from selenium.webdriver.common.keys import Keys
import config


ENV_DICT = {"stage": 'stage', 'dev': 'dev', 'prod': 'meity'}
LANGUAGE_DICT = {'bn': 'bengali', 'hi': 'hindi', 'en': 'english',
                 'mr': 'marathi', 'ta': 'tamil', 'te': 'telugu',
                 'kn': 'kannada', 'gu': 'gujarati', 'pa': 'punjabi',
                 'ml': 'malayalam', 'as': 'assamese', 'or': 'odia',
                 'ur': 'urdu'}


# ULCA-urls
ULCA_DOMAIN = "https://"+ENV_DICT[config.DEFAULT_ENV]+"."
ULCA_LOGIN_URL = ULCA_DOMAIN+"ulcacontrib.org/user/login"
ULCA_DASHBOARD_URL = ULCA_DOMAIN+"ulcacontrib.org/dashboard"

ULCA_DATASET_SUBMIT_URL = ULCA_DOMAIN + "ulcacontrib.org/dataset/upload"
ULCA_DATASET_CONTRIB_URL = ULCA_DOMAIN + \
    "ulcacontrib.org/dataset/my-contribution"
ULCA_DATASET_MYSEARCHS_URL = ULCA_DOMAIN + "ulcacontrib.org/my-searches"
ULCA_DATASET_SEARCH_URL = ULCA_DOMAIN+"ulcacontrib.org/search-and-" + \
    "download-rec/initiate/-1"

ULCA_MODEL_SUBMIT_URL = ULCA_DOMAIN+"ulcacontrib.org/model/upload"
ULCA_MODEL_EXPLORE_URL = ULCA_DOMAIN + "ulcacontrib.org/model/explore-models"
ULCA_MODEL_CONTRIB_URL = ULCA_DOMAIN + "ulcacontrib.org/model/my-contribution"


# xpaths-of-buttons/elements-in-ULCA-websites
#         list -> 0 -> name of the element
#         list -> 1 -> xpath of the element (only-support-xpaths)
#
ELE_USERNAME_INP = ["USERNAME-INPUT-FIELD", '//*[@id="outlined-required"]']
ELE_PASSWORD_INP = ["PASSWORD-INPUT-FIELD",
                    '//*[@id="outlined-adornment-password"]']
ELE_LOGIN_BTN = ["LOGIN-BUTTON", "//*[@id='root']/div/div/div/div/form/button"]
ELE_PROFILE_BTN = ["PROFILE-BUTTON",
                   '//*[@id="root"]/div/header/div/div/div[3]/button']
ELE_LOGOUT_BTN = ["LOGOUT_BUTTON", "//*[@id='data-set']/div[3]/ul"]

ELE_SEARCHTYPELIST_BTN = ["SEARCHPAGE-DATATYPE-LISTBTN",
                          '//*[@id="root"]/div/div/div/div/div/div[1]' +
                          '/div/div/div[1]/button']
ELE_SEARCHSOURCE_INP = ["SEARCHPAGE-SOURCE-INPUT-FEILD",
                        '//*[@id="source"]']
ELE_SEARCHTARGET_INP = ["SEARCHPAGE-TARGET-INPUT-FEILD",
                        '//*[@id="language-target"]']
ELE_SEARCHDOMAIN_INP = ["SEARCHPAGE-DOMAIN-INPUT-FEILD",
                        '//*[@id="domain"]']
ELE_SEARCHCOLL_INP = ["SEARCHPAGE-COLLECTION-METHOD-INPUT-FEILD",
                      '//*[@id="collectionMethod"]']
ELE_SEARCHMULANNO_CB = ["SEARCHPAGE-MULTIPLE-ANNOTATORS-CHECKVOX-FEILD",
                        '//*[@name="checkedA"]']
ELE_SEARCHMANTRANS_CB = ["SEARCHPAGE-MULTIPLE-TRANSLATORS-CHECKBOX-FEILD",
                         '//*[@name="checkedB"]']
ELE_SEARCHORGSOURCE_CB = ["SEARCHPAGE-ORGINAL-SOURCE-CHECKBOX-FEILD",
                          '//*[@name="checkedC"]']
ELE_SEARCHSUBMIT_CB = ["SEARCHPAGE-SUBMIT-BUTTON",
                       '//*[@id="root"]/div/div/div/div/div/div[1]/div/div' +
                       '/div[5]/div[2]/div/div[2]/button']
ELE_SEARCHSRN_CB = ["SEARCHED-SRN-NO",
                    "//*[@id='root']/div/div/div/div/div/"
                    + "div[2]/div/div/div/h5"]

ELE_MYSEARCHNAME_TXT = ["MYSEARCH-1STROW-NAME",
                        '//*[@data-testid="MUIDataTableBodyRow-0"]/td[1]/' +
                        'div[2]']
ELE_MYSEARCHCOUNT_TXT = ["MYSEARCH-1STROW-COUNT",
                         '//*[@data-testid="MUIDataTableBodyRow-0"]/td[3]/' +
                         'div[2]']
ELE_MYSEARCHSTATUS_TXT = ["MYSEARCH-1STROW-STATUS",
                          '//*[@data-testid="MUIDataTableBodyRow-0"]/td[4]/' +
                          'div[2]']
ELE_SAMPLEFILE_A = ["MYSEARCH-SAMPLEFILE-A-HREF",
                    '//*[@id="root"]/div/div/div/div/div/'
                    + 'div[2]/div/div/div[2]/a[1]']
ELE_ALLFILE_A = ["MYSEARCH-ALLFILE-A-HREF",
                 '//*[@id="root"]/div/div/div/div/div/div[2]/div/'
                 + 'div/div[2]/a[2]']

ELE_SUBMITNAME_INP = ["SUBMIT-DATASET-NAME-INPUT",
                      '//*[@id="root"]/div/div/div/div/div/div/div/div[3]' +
                      '/div/div/div[2]/div/div[1]/div/div/input']
ELE_SUBMITURL_INP = ["SUBMIT-DATASET-URL-INPUT",
                     '//*[@id="root"]/div/div/div/div/div/div/div/div[3]' +
                     '/div/div/div[2]/div/div[2]/div/div/input']
ELE_SUBMITDATA_BTN = ["SUBMIT-BUTTON",
                      '//*[@id="root"]/div/div/div/div/div/div/div/' +
                      'div[3]/div/button']
ELE_SUBMITSRN_TXT = ["SUBMIT-SRN-NO-H5",
                     '//*[@id="root"]/div/div/div/div/div/h5']

ELE_CONTRIBNAME_TXT = ["MYCONTRIB-1STROW-NAME-TXT",
                       '//*[@id="MUIDataTableBodyRow-0"]/td[1]/div[2]']
ELE_CONTRIBSTATUS_TXT = ["MYCONTRIB-1STROW-STATUS-TXT",
                         '//*[@id="MUIDataTableBodyRow-0"]/td[4]/div[2]']
ELE_CONTRIBREFRESH_BTN = ["MYCONTRIB-REFRESH-BTN",
                          '//*[@id="root"]/div/div/div/div/div[2]/div'
                          + '/button[1]']

ELE_CDOWNLOADSTATUS_TXT = ["MYCONTRIB-1STROW-DOWNLOAD-STATUS-TXT",
                           '//*[@id="MUIDataTableBodyRow-0"]/td[2]/div[2]']
ELE_CINGESTSTATUS_TXT = ["MYCONTRIB-2NDROW-INGEST-STATUS-TXT",
                         '//*[@id="MUIDataTableBodyRow-1"]/td[2]/div[2]']
ELE_CINGESTSC_TXT = ["MYCONTRIB-2NDROW-INGEST-SUCCESS-COUNT-TXT",
                     '//*[@id="MUIDataTableBodyRow-1"]/td[3]/div[2]']
ELE_CINGESTFC_TXT = ["MYCONTRIB-2NDROW-INGEST-FAILURE-COUNT-TXT",
                     '//*[@id="MUIDataTableBodyRow-1"]/td[4]/div[2]']
ELE_CVALIDATESTATUS_TXT = ["MYCONTRIB-3RDROW-VALIDATE-STATUS-TXT",
                           '//*[@id="MUIDataTableBodyRow-2"]/td[2]/div[2]']
ELE_CVALIDATESC_TXT = ["MYCONTRIB-3RDROW-VALIDATE-SUCCESS-COUNT-TXT",
                       '//*[@id="MUIDataTableBodyRow-2"]/td[3]/div[2]']
ELE_CVALIDATEFC_TXT = ["MYCONTRIB-3RDROW-VALIDATE-FAILURE-COUNT-TXT",
                       '//*[@id="MUIDataTableBodyRow-2"]/td[4]/div[2]']
ELE_CPUBLISHSTATUS_TXT = ["MYCONTRIB-4THROW-PUBLISH-STATUS-TXT",
                          '//*[@id="MUIDataTableBodyRow-3"]/td[2]/div[2]']
ELE_CPUBLISHSC_TXT = ["MYCONTRIB-4THROW-PUBLISH-SUCCESS-COUNT-TXT",
                      '//*[@id="MUIDataTableBodyRow-3"]/td[3]/div[2]']
ELE_CPUBLISHFC_TXT = ["MYCONTRIB-4THROW-PUBLISH-FAILURE-COUNT-TXT",
                      '//*[@id="MUIDataTableBodyRow-3"]/td[4]/div[2]']
ELE_CLOG_A = ["MYCONTRIB-ERROR-LOG-FILE-ATAG",
              '//*[@id="root"]/div/div/div/div/div[1]/div/a']

ELE_DASHDATATYPESELECT_BUTTON = ["DASHBOARD-DATATYPE-SELECT-BUTTON",
                                 '//*[@id="root"]/div/div/header/'
                                 + 'div/div/div[1]/button']
ELE_DASHDTPARALLEL_BTN = ["DASHBOARD-PARALLEL-DATATYPE-SELECT",
                          '//*[@value="parallel-corpus"]']
ELE_DASHDTMONO_BTN = ["DASHBOARD-MONOLINGUAL-DATATYPE-SELECT",
                      '//*[@value="monolingual-corpus"]']
ELE_DASHDTASRTTS_BTN = ["DASHBOARD-ASR-TTS-DATATYPE-SELECT",
                        '//*[@value="asr-corpus"]']
ELE_DASHDTOCR_BTN = ["DASHBOARD-OCR-DATATYPE-SELECT",
                     '//*[@value="ocr-corpus"]']
ELE_DASHDTASRUNLAB_BTN = ["DASHBOARD-ASR-UNLABLELED-DATATYPE-SELECT",
                          '//*[@value="asr-unlabeled-corpus"]']
ELE_DASHPARASRCLANG_INP = ["DASHBOARD-PARALLEL-SOURCE-LANG-INPUT",
                           '//*[@id="source"]']
ELE_DASHLANGNAMES_LI = ["DASHBOARD-CHART-LANGUAGE-NAMES-LIST",
                        '//*[@class="recharts-layer recharts-cartesian-axis' +
                        '-tick"]']
ELE_DASHLANGRECT_LI = ["DASHBOARD-CHART-RECTANGLE-LIST",
                       '//*[@class="recharts-layer recharts-bar-rectangle"]']
ELE_DASHLANGCOUNTS_LI = ["DASHBOARD-CHART-LANGUAGE-COUNT-LIST",
                         "//*[@class='recharts-text recharts-label']"]
ELE_DASHTOTCOUNTS_TXT = ["DASHBOARD-DATATYPE-TOTAL-COUNT-H6",
                         "//*[@id='root']/div/div/header/div/div/div[2]/h6"]
ELE_DASHSRCCOUNTS_TXT = ["DASHBOARD-DATATYPE-SRC-LANG-COUNT-H6",
                         '//*[@id="root"]/div/div/div/div[1]/div/div/h6']
ELE_DASHGROUPCOLL_BTN = ["DASHBOARD-GROUPBY-COLLECTION-BUTTON",
                         '//*[@id="root"]/div/div/header/div/div/div[4]' +
                         '/div/div/button[2]']
ELE_DASHGROUPSUBM_BTN = ["DASHBOARD-GROUPBY-SUBMITTER-BUTTON",
                         '//*[@id="root"]/div/div/header/div/div/div[4]' +
                         '/div/div/button[3]']


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
        status_str = "ELEMENT="+element_data[0]+" - NOT FOUND"
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
    driver = get_url(ULCA_DASHBOARD_URL, driver)
    status, status_str = perform_webpage_function(ELE_PROFILE_BTN, "click",
                                                  driver)
    if status:
        status, status_str = perform_webpage_function(ELE_LOGOUT_BTN, "click",
                                                      driver)
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
            ELE_USERNAME_INP, "input", driver, input_data=username)
        if status:
            status, status_str = perform_webpage_function(
                ELE_PASSWORD_INP, "input", driver, input_data=password)
        if status:
            status, status_str = perform_webpage_function(
                ELE_LOGIN_BTN, "click", driver, input_data=password)
        if status:
            try:
                driver.find_element_by_xpath(ELE_PROFILE_BTN[1])
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
    with open(config.SCHEMA_FILENAME, 'r') as obj:
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
        yaml_data = yaml_data['components']["schemas"]
        lng = yaml_data["LanguagePair"]["properties"]["sourceLanguage"]["enum"]
        domains = yaml_data["Domain"]["items"]["enum"]
        coll_methods = yaml_data["CollectionMethod"]["items"]["enum"]
        types = yaml_data["DatasetType"]["enum"]
        data = {"Languages": lng,
                "CollectionMethods": coll_methods, 'Domains': domains,
                'DataTypes': types}
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
        with open(filename, 'w') as wrt:
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


def select_chart_datatype(dataset_type, src, driver):
    """
    select_chart_datatype functions selects the datatype for the chart.

    Parameters
    ----------
    dataset_type : str
        type of dataset.
    src : str
        source language for chart (only for parallel dataset).
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
    if dataset_type in load_yaml_data("DataTypes"):
        dt_element = ['DATASET-TYPE='+dataset_type.upper(),
                      '//*[@value="'+dataset_type+'"]']
        status, status_str = perform_webpage_function(
            dt_element, "click", driver)
        if status:
            if dataset_type == "parallel-corpus":
                if src == "":
                    src = "en"
                if src not in load_yaml_data("Languages"):
                    status = False
                    status_str = "Not a valid source language."
                else:
                    status, status_str = perform_webpage_function(
                        ELE_DASHPARASRCLANG_INP, "dropdown", driver,
                        input_data=LANGUAGE_DICT[src])
                    if status:
                        status, status_str = perform_webpage_function(
                            ELE_DASHSRCCOUNTS_TXT, "text", driver)
                    if status:
                        status_str = " - "+src.capitalize()+"-count=" + \
                            status_str
    else:
        status = False
        status_str = "Not a valid DataType."
    return status, status_str, driver


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
            lang_names = driver.find_element_by_tag_name('svg')
            lang_names = lang_names.find_elements_by_xpath(
                ELE_DASHLANGNAMES_LI[1])
        except Exception:
            status = False
            status_str = "ELEMENT="+ELE_DASHLANGNAMES_LI[0]+" - NOT FOUND"
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
            lang_counts = driver.find_elements_by_xpath(
                ELE_DASHLANGCOUNTS_LI[1])
        except Exception:
            status = False
            status_str = "ELEMENT="+ELE_DASHLANGCOUNTS_LI[0]+" - NOT FOUND"
    if status:
        del lang_counts[0]
        del lang_counts[0]
        for i in range(len(lang_counts)):
            count = lang_counts[i].text.strip().replace(",", "")
            lang_counts[i] = str(count)

        status, status_str = perform_webpage_function(
            ELE_DASHTOTCOUNTS_TXT, "text", driver)
    if status:
        if status_str.find('.') > -1:
            status_str += " hours"
        status_str = "Total-count="+status_str + src_count
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
    status, status_str, names, counts, driver = get_chart_data_values(
        "1", driver)
    if status:
        tgt = LANGUAGE_DICT[tgt].capitalize()
        try:
            lang_index = names.index(tgt)
        except Exception:
            status = False
            status_str = "target Language not available in chart."
        if status:
            try:
                lang_names = driver.find_element_by_tag_name(
                    'svg')
                lang_names = lang_names.find_elements_by_xpath(
                    ELE_DASHLANGRECT_LI[1])
            except Exception:
                status = False
                status_str = "ELEMENT="+ELE_DASHLANGRECT_LI[0]+" - NOT FOUND"
    if status:
        lang_names[lang_index].click()
    groupby_list = ['domain', 'collection', 'submitter']
    if groupby != "":
        if groupby not in groupby_list:
            status = False
            status_str = "not a valid group by selector."
        if status:
            if groupby == 'submitter':
                status, status_str = perform_webpage_function(
                    ELE_DASHGROUPSUBM_BTN, "click", driver)
            elif groupby == 'collection':
                status, status_str = perform_webpage_function(
                    ELE_DASHGROUPCOLL_BTN, "click", driver)
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
    driver = get_url(ULCA_DASHBOARD_URL, driver)
    print("CHART-DATA : ", flush=True, end="")
    if status:
        status, status_str = perform_webpage_function(
            ELE_DASHDATATYPESELECT_BUTTON, "click", driver)
    if status:
        status, status_str, driver = select_chart_datatype(dataset_type, src,
                                                           driver)
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
            status, status_str, driver = select_group_chart(tgt, groupby,
                                                            driver)
    if status:
        status, status_str, names, counts, driver = get_chart_data_values(
            src_count, driver)

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

    {STILL_IN_PROGRESS}

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    print('TEST : test method still in-progress.')
    return driver
