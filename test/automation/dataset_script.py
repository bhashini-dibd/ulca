#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 11:47:15 2021.

@author: dhiru579 @ Tarento.
"""
import time
import pandas
import core_script as core
import config
from termcolor import colored


def perform_upload(dataset_name, dataset_url, driver):
    """
    perform_upload fumction uploads the dataset.

    Parameters
    ----------
    dataset_name : str
        name of the dataset.
    dataset_url : str
        url of the dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    status : bool
        true if function completed successfully.
    status_str : str
        string for the status.
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    print("SUBMIT : ", end="", flush=True)
    if dataset_name == "":
        status = False
        status_str = "DATASET-NAME EMPTY !"
    if status:
        if dataset_url == "":
            status = False
            status_str = "DATASET-URL EMPTY !"
    if status:
        driver = core.get_url(core.ULCA_DATASET_SUBMIT_URL, driver)
        status, status_str = core.perform_webpage_function(
            core.ELE_SUBMITNAME_INP, "input", driver, input_data=dataset_name)
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_SUBMITURL_INP, "input", driver, input_data=dataset_url)
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_SUBMITDATA_BTN, "click", driver)
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_SUBMITSRN_TXT, "text", driver)
        if status:
            status_str = int(status_str.strip().split(" ")[-1])
        else:
            status_str = "not submitted - check url/name or [service-" + \
                "temporary-unavailable] "
    driver = core.print_status(status, status_str, driver)
    return status, status_str, driver


def generate_log_file(srn, driver):
    """
    generate_log_file downloads the log fiel is available.

    Parameters
    ----------
    srn : str
        srn_no of the dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    filename : str
        name of the log file.
    driver : selenium.driver
        A Browser window object.

    """
    filename = "logFile="
    driver.refresh()
    time.sleep(config.COMMON_WAIT_TIME)
    status, status_str = core.perform_webpage_function(core.ELE_CLOG_A,
                                                       "href", driver)
    if status:
        logfile = core.get_file("{}-log.csv".format(srn), status_str)
        filename += logfile
    else:
        filename += "N/A"
    return filename, driver


def get_contrib_data(driver):
    """
    getc_ontrib_data function generates contrib-page data.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    status : bool
        true if function completed successfully.
    status_str : str
        string for the status.
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    driver.refresh()
    time.sleep(config.COMMON_WAIT_TIME)
    status, status_str = core.perform_webpage_function(
        core.ELE_CDOWNLOADSTATUS_TXT, "text", driver)
    if status:
        download_status = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CINGESTSTATUS_TXT, "text", driver)
    if status:
        ingest_status = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CINGESTSC_TXT, "text", driver)
    if status:
        ingest_sc = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CINGESTFC_TXT, "text", driver)
    if status:
        ingest_fc = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CVALIDATESTATUS_TXT, "text", driver)
    if status:
        validate_status = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CVALIDATESC_TXT, "text", driver)
    if status:
        validate_sc = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CVALIDATEFC_TXT, "text", driver)
    if status:
        validate_fc = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CPUBLISHSTATUS_TXT, "text", driver)
    if status:
        publish_status = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CPUBLISHSC_TXT, "text", driver)
    if status:
        publish_sc = status_str
        status, status_str = core.perform_webpage_function(
            core.ELE_CPUBLISHFC_TXT, "text", driver)
    if status:
        publish_fc = status_str
        status_str = {"DOWNLOAD": download_status,
                      "INGEST": [ingest_status, ingest_sc, ingest_fc],
                      "VALIDATE": [validate_status, validate_sc, validate_fc],
                      "PUBLISH": [publish_status, publish_sc, publish_fc]
                      }
    return status, status_str, driver


def generate_contrib_string(datadict):
    """
    generate_contrib_string function generate a tabel-tabed(/t)-string.

    Parameters
    ----------
    datadict : dict
        dict containing the values for download,ingest,validate,publish.

    Returns
    -------
    string : str
        tabel-tabed(/t)-string.

    """
    string = "\n\n" + "Stage\t\t\tStatus\t\t\tSuccess Count\tFailed Count\n" \
        + "DOWNLOAD\t\t" + str(datadict["DOWNLOAD"]) + "\nINGEST\t\t\t" \
        + str(datadict["INGEST"][0]) + "\t\t" \
        + str(datadict["INGEST"][1]) + "\t\t" + str(datadict["INGEST"][2]) \
        + "\nVALIDATE\t\t" + str(datadict["VALIDATE"][0]) + "\t\t" \
        + str(datadict["VALIDATE"][1]) + "\t\t" \
        + str(datadict["VALIDATE"][2]) + "\nPUBLISH\t\t\t" \
        + str(datadict["PUBLISH"][0]) + "\t\t" + str(datadict["PUBLISH"][1]) \
        + "\t\t" + str(datadict["PUBLISH"][2]) + "\n@time= " \
        + str(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))+"\n"
    return string


def print_contrib_data(status, srn, driver):
    """
    print_contrib_data prints the values of contrib status.

    Parameters
    ----------
    status : bool
        function returs true if passed.
    srn : str
        srn of the uploaded-dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    print_string : str
        tabel-tabed(/t)-string.
    driver : selenium.driver
        A Browser window object.

    """
    p_status, datadict, driver = get_contrib_data(driver)
    if p_status:
        if status is False:
            print_string = generate_contrib_string(datadict)
        else:
            while datadict["PUBLISH"][0].lower() != "completed":
                print(colored("PENDING", "blue"))
                print_string = generate_contrib_string(datadict)
                print(print_string)
                print("waiting for", config.PENDING_WAIT_TIME, "seconds.")
                time.sleep(config.PENDING_WAIT_TIME)
                print("CONTRIB-STATUS : ", end="", flush=True)
                p_status, datadict, driver = get_contrib_data(driver)
                if p_status is False:
                    print_string = datadict
                    return print_string, driver
            print_string = generate_contrib_string(datadict)
        logfile, driver = generate_log_file(srn, driver)
        print_string = logfile+print_string
    else:
        print_string = datadict
    return print_string, driver


def get_upload_status(srn, dataset_name, driver):
    """
    get_upload_status checks the status of the uploaded-dataset.

    Parameters
    ----------
    srn : str
        srn-no the dataset.
    dataset_name : name
        name of the dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    driver = core.get_url(core.ULCA_DATASET_CONTRIB_URL, driver)
    print("CONTRIB-STATUS : ", end="", flush=True)
    status, status_str = core.perform_webpage_function(
        core.ELE_CONTRIBNAME_TXT, "text", driver)
    if status:
        if status_str.lower() != dataset_name.lower():
            status = False
            status_str = "dataset name not found."
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_CONTRIBSTATUS_TXT, "text", driver)
        while status_str.lower() == "in-progress":
            print(colored("PENDING", "blue"))
            print()
            print("waiting for", config.PENDING_WAIT_TIME, 'seconds')
            print()
            time.sleep(config.PENDING_WAIT_TIME)
            print("CONTRIB-STATUS : ", end="", flush=True)
            driver.refresh()
            time.sleep(config.COMMON_WAIT_TIME)
            status, status_str = core.perform_webpage_function(
                core.ELE_CONTRIBSTATUS_TXT, "text", driver)
        final_status = status_str
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_CONTRIBNAME_TXT, "click", driver)
    if status:
        if final_status.lower() == "failed":
            status = False
            status_str, driver = print_contrib_data(status, srn, driver)
        else:
            status = True
            status_str, driver = print_contrib_data(status, srn, driver)
    driver = core.print_status(status, status_str, driver)
    return driver


def perform_upload_with_status(name, url, driver):
    """
    perform_upload_with_status function uploads the dataset with status.

    Parameters
    ----------
    name : str
        name of the dataset.
    url : str
        url of the dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status, srn, driver = perform_upload(name, url, driver)
    if status:
        driver = get_upload_status(srn, name, driver)
    return driver


def perform_upload_with_csv(csvfile, driver):
    """
    perform_upload_with_csv function uploads datasets through the csvfile.

    Parameters
    ----------
    csvfile : str
        csv file name.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    try:
        df = pandas.read_csv(csvfile)
    except Exception:
        print("CSV :", end="", flush=True)
        print(colored("FAILED", "red"), end="")
        print("CSV NOT READABLE")
        return driver
    df_len = df.shape[0]-1
    for i, j in df.iterrows():
        print()
        print("-"*45)
        name, url = j["Dataset Name"], j["Dataset URL"]
        print("DATASET_NAME =", name)
        driver = perform_upload_with_status(name, url, driver)
        if i == df_len:
            print("-"*45)
            print()
    return driver


def perform_search(dataset_type, tgts, src, domain, collection_method,
                   multiple_annotators, manually_translated, original_source,
                   driver):
    """
    perform_search function searches the required-dataset.

    Parameters
    ----------
    dataset_type : str
        type of the dataset.
    tgt : str
        target language.
    src : str
        source language.
    domain : str
        domain of the dataset.
    coll_method : str
        collection-method.
    multi_anno : bool
        multiple anotators.
    manual_trans : bool
        manuall translation.
    original_source : bool
        original source.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    status : bool
        true if function completed successfully.
    status_str : str
        string for the status.
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    print("SEARCH : ", flush=True, end="")
    if dataset_type not in core.load_yaml_data("DataTypes"):
        status = False
        status_str = "Not valid Datatype"
    if status:
        if dataset_type == 'parallel-corpus':
            if src not in core.load_yaml_data("Languages"):
                status = False
                status_str = "Not valid Source Language."
    if status:
        if (not set(tgts).issubset(set(core.load_yaml_data(
                "Languages")))) or len(tgts) == 0:
            status = False
            status_str = "Not valid Target Languages."
    if status:
        if domain != "":
            if domain not in core.load_yaml_data("Domains"):
                status = False
                status_str = "Not valid Domain"
    if status:
        if collection_method != "":
            if collection_method not in core.load_yaml_data(
                    "CollectionMethods"):
                status = False
                status_str = "Not valid Collection Method."
    if status:
        driver = core.get_url(core.ULCA_DATASET_SEARCH_URL, driver)
        status, status_str = core.perform_webpage_function(
            core.ELE_SEARCHTYPELIST_BTN, "click", driver)
    if status:
        dt_element = ['DATASET-TYPE='+dataset_type.upper(),
                      '//*[@value="'+dataset_type+'"]']
        status, status_str = core.perform_webpage_function(
            dt_element, "click", driver)
    if status:
        if dataset_type == "parallel-corpus":
            status, status_str = core.perform_webpage_function(
                core.ELE_SEARCHSOURCE_INP, "dropdown", driver,
                input_data=core.LANGUAGE_DICT[src])
        if status:
            if original_source:
                status, status_str = core.perform_webpage_function(
                    core.ELE_SEARCHORGSOURCE_CB, "click", driver)
        if status:
            if manually_translated:
                status, status_str = core.perform_webpage_function(
                    core.ELE_SEARCHMANTRANS_CB, "click", driver)
    if status:
        for tgt in tgts:
            status, status_str = core.perform_webpage_function(
                core.ELE_SEARCHTARGET_INP, "dropdown", driver,
                input_data=core.LANGUAGE_DICT[tgt])
            if status is False:
                break
    if status:
        if domain != "":
            status, status_str = core.perform_webpage_function(
                core.ELE_SEARCHDOMAIN_INP, "dropdown", driver,
                input_data=domain.replace("-", ""))
    if status:
        if collection_method != "":
            status, status_str = core.perform_webpage_function(
                core.ELE_SEARCHCOLL_INP, "dropdown", driver,
                input_data=collection_method.replace("-", ""))
    if status:
        if multiple_annotators:
            status, status_str = core.perform_webpage_function(
                core.ELE_SEARCHMULANNO_CB, "click", driver)

    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_SEARCHSUBMIT_CB, "click", driver)
    if status:
        status, status_str = core.perform_webpage_function(
            core.ELE_SEARCHSRN_CB, "text", driver)
        time.sleep(config.COMMON_WAIT_TIME)
    if status:
        status_str = int(status_str.split(" ")[-1])
    driver = core.print_status(status, status_str, driver)
    return status, status_str, driver


def perform_download(dataset_type, tgt, srn, driver):
    """
    perform_download function downloads the searched-dataset.

    Parameters
    ----------
    dataset_type : str
        type of dataset.
    tgt : str
        target language.
    srn : str
        srn no of the searched-dataset.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    print("DOWNLOAD : ", flush=True, end="")
    driver = core.get_url(core.ULCA_DATASET_MYSEARCHS_URL, driver)
    status, status_str = core.perform_webpage_function(
        core.ELE_MYSEARCHNAME_TXT, "text", driver)
    if status:
        search_dataset = status_str.strip().split(" ")[0].lower()
        search_tgt = status_str.strip().split(" ")[-1].lower()
        org_dataset = dataset_type.split('-')[0]
        org_tgt = core.LANGUAGE_DICT[tgt[-1]]
    if status:
        if ((search_dataset == org_dataset) and (search_tgt == org_tgt)):
            status, status_str = core.perform_webpage_function(
                core.ELE_MYSEARCHSTATUS_TXT, "text", driver)
            if status:
                while status_str == "In-Progress":
                    print(colored("PENDING", "blue"))
                    print()
                    print("waiting for", config.PENDING_WAIT_TIME, "seconds")
                    time.sleep(config.PENDING_WAIT_TIME)
                    print("\nDOWNLOAD : ", end="", flush=True)
                    driver.refresh()
                    time.sleep(config.COMMON_WAIT_TIME)
                    status, status_str = core.perform_webpage_function(
                        core.ELE_MYSEARCHSTATUS_TXT, "text", driver)
                if status_str == "Failed":
                    status = False
                    status_str = "searching failed"
                else:
                    status, status_str = core.perform_webpage_function(
                        core.ELE_MYSEARCHCOUNT_TXT, "text", driver)
                if status:
                    if status_str == "0":
                        status = True
                        status_str = "found a null dataset."
                    else:
                        count_str = "count="+str(status_str)
                        status, status_str = core.perform_webpage_function(
                            core.ELE_MYSEARCHNAME_TXT, "click", driver)
                        if status:
                            status, status_str = core.perform_webpage_function(
                                core.ELE_SAMPLEFILE_A, "href", driver)
                        if status:
                            smple = core.get_file("{}-sample.json".format(srn),
                                                  status_str)
                            status, status_str = core.perform_webpage_function(
                                core.ELE_ALLFILE_A, "href", driver)
                        if status:
                            all_file = core.get_file("{}-all.json".format(srn),
                                                     status_str.strip())
                        status_str = count_str + " - sampleFile=" + \
                            str(smple)+" - allFile=" + str(all_file)
        else:
            status = False
            status_str = "could not find searched name."
    driver = core.print_status(status, status_str, driver)
    return driver


def perform_search_and_download(dataset_type, tgt, src, domain, coll_method,
                                multi_anno, manual_trans, org_source, driver):
    """
    perform_search_and_download function.

    is super-function for searching
    and downloading which performs all abovefunctions.

    Parameters
    ----------
    dataset_type : str
        type of the dataset.
    tgt : str
        target language.
    src : str
        source language.
    domain : str
        domain of the dataset.
    coll_method : str
        collection-method.
    multi_anno : bool
        multiple anotators.
    manual_trans : bool
        manuall translation.
    org_source : bool
        original source.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status, srn, driver = perform_search(dataset_type, tgt, src, domain,
                                         coll_method, multi_anno, manual_trans,
                                         org_source, driver)
    if status:
        driver = perform_download(dataset_type, tgt, srn, driver)
    return driver
