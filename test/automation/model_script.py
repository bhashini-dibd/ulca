#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 11:46:43 2021.

@author: dhiru579 @ Tarento.
"""

import os
import time
import config
from termcolor import colored

import elements as ele
import core_script as core


def submit_model(name, file, driver):
    """
    submit_model function performs uploading a model.

    Parameters
    ----------
    name : str
        name of the model.
    file : str
        valid model-file in json format.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ''
    print("SUBMIT-MODEL :", end="", flush=True)
    file = os.path.abspath(file)
    driver = core.get_url(core.ULCA_MDL_SUBMIT_URL, driver)
    # input for submit-model-name
    status, status_str = core.perform_webpage_function(
        ele.MDL_SUBMIT_NAME_INP, "input", driver, input_data=name)
    if status:
        # input for submit-model-file
        status, status_str = core.perform_webpage_function(
            ele.MDL_SUBMIT_FILE_INP, "input", driver, input_data=file)
    if status:
        # click submit-model-button
        status, status_str = core.perform_webpage_function(
            ele.MDL_SUBMIT_BTN, "click", driver)
    if status:
        time.sleep(5)
        status, status_str = core.perform_webpage_function(
            ele.MDL_SUBMIT_SRN_TXT, "text", driver)
    if not status:
        status_str = 'Unknown ERROR occured after submitting.'
    driver = core.print_status(status, status_str, driver)
    return status, driver


def select_from_benchmark_list(blist):
    """
    select_from_benchmark_list function gives i/o for benchmarks list.

    Parameters
    ----------
    blist : list
        benchmarks names.

    Returns
    -------
    index : int
        index of the item from the benchmark_list.

    """
    print(colored("PENDING", "blue"))
    print()
    for i, j in enumerate(blist):
        print(i+1, j)
    print()
    while True:
        try:
            index = int(input('ENTER THE BENCHMARK NO FROM THE ABOVE LIST:'))
            if 0 < index < len(blist)+1:
                index -= 1
                break
            else:
                raise Exception
        except Exception:
            print('WRONG OPTION')
    print()
    print("BENCHMARK :", end="", flush=True)
    return index


def select_benchmark(bname, driver):
    """
    select_benchamrk function selects the approppriate benchmarking.

    Parameters
    ----------
    bname : str
        benchmark name.
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
    status_str = ''
    b_name_list = ()
    time.sleep(5)
    for i in range(0, 10):
        try:
            name_x = driver.find_element_by_xpath(
                ele.MDL_CONTRIB_BNAME_TXT[1].format(i)).text.lower()
            b_name_list += tuple([name_x])
        except Exception:
            break
    if len(b_name_list) == 0:
        status = False
        status_str = 'coudnt load/find benchmark list '
    if status:
        MDL_CONTRIB_BSELECT_BTN = ele.MDL_CONTRIB_BSELECT_BTN
        if bname in b_name_list:
            index = b_name_list.index(bname)
        else:
            index = select_from_benchmark_list(b_name_list)
        MDL_CONTRIB_BSELECT_BTN[1] = MDL_CONTRIB_BSELECT_BTN[1].format(index)
        status, status_str = core.perform_webpage_function(
            MDL_CONTRIB_BSELECT_BTN, "click", driver)
    if status:
        MDL_CONTRIB_METRICSELECT_BTN = ele.MDL_CONTRIB_METRICSELECT_BTN
        MDL_CONTRIB_METRICSELECT_BTN[1] = MDL_CONTRIB_METRICSELECT_BTN[1].format(
            index+2)
        status, status_str = core.perform_webpage_function(
            MDL_CONTRIB_METRICSELECT_BTN, "click", driver)
    if status:
        status, status_str = core.perform_webpage_function(
            ele.MDL_CONTRIB_BSUBMIT_BTN, "click", driver)
    if status:
        status_str = 'started for ' + str(b_name_list[index])
    return status, status_str, driver


def run_benchmark(name, bname, driver):
    """
    run_benchmark function starts the benchmarking for a submitted model.

    Parameters
    ----------
    name : str
        model name.
    bname : str
        benchmark name.
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
    status_str = ''
    driver = core.get_url(core.ULCA_MDL_CONTRIB_URL, driver)
    print("BENCHMARK : ", end="", flush=True)
    status, status_str = core.perform_webpage_function(
        ele.MDL_CONTRIB_STTS_TXT, "text", driver)
    if status:
        while status_str.lower() == 'in-progress':
            print(colored("PENDING", "blue"))
            print("waiting for", config.PENDING_WAIT_TIME, "seconds.")
            time.sleep(config.PENDING_WAIT_TIME)
            print("BENCHMARK : ", end="", flush=True)
            driver = core.get_url(core.ULCA_MDL_CONTRIB_URL, driver)
            status, status_str = core.perform_webpage_function(
                ele.MDL_CONTRIB_STTS_TXT, "text", driver)
            if not status:
                break
    if status:
        if status_str.lower() == 'failed':
            status = False
            status_str = 'Model is not published/Failed.'
        elif status_str.lower() == 'published':
            status, status_str = core.perform_webpage_function(
                ele.MDL_CONTRIB_RUNBENCH_BTN, "click", driver)
            if status:
                status, status_str, driver = select_benchmark(bname, driver)

        else:
            status = False
            status_str = 'status returned something unusual - '+status_str
    driver = core.print_status(status, status_str, driver)
    return status, driver


def get_benchmark_data(length, driver):
    """
    get_benchmark_data function fetches the data of bechmarking submitted.

    Parameters
    ----------
    length : int
        total no of benchmrkings available.
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
    status_str = ''
    data_list = []
    for i in range(1, length+1):
        m_name = ele.MDL_CONTRIB_METRICNAME_TXT[1]
        m_type = ele.MDL_CONTRIB_METRICTYPE_TXT[1]
        m_score = ele.MDL_CONTRIB_METRICSCOR_TXT[1]
        m_status = ele.MDL_CONTRIB_METRICSTTS_TXT[1]
        m_name = m_name.format(i)
        m_type = m_type.format(i)
        m_score = m_score.format(i)
        m_status = m_status.format(i)
        status, status_str = core.perform_webpage_function(
            ['METRIC_NAME', m_name], "text", driver)
        if status:
            x_name = status_str
            status, status_str = core.perform_webpage_function(
                ['METRIC_TYPE', m_type], "text", driver)
        if status:
            x_type = status_str
            status, status_str = core.perform_webpage_function(
                ['METRIC_SCORE', m_score], "text", driver)
        if status:
            x_score = status_str
            status, status_str = core.perform_webpage_function(
                ['METRIC_STATUS', m_status], "text", driver)
        if status:
            x_status = status_str
            data_list.append([x_name, x_type, x_score, x_status])
            status_str = data_list
        else:
            break
    return status, status_str, driver


def print_benchmarking_data(data_list):
    """
    print_benchmarking_data function prints the benchmarking status available.

    Parameters
    ----------
    data_list : list/dict
        dict containing benchamrking values/status.

    Returns
    -------
    None.

    """
    print("NAME\t\t\t\t\tTYPE\tSCORE\tSTATUS")
    for i in data_list:
        print('{: <25}'.format(i[0])+i[1]+'\t'+i[2]+'\t'+i[3])
    print("@time= "+str(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())))
    print()


def check_benchmark_status(driver):
    """
    check_benchmark_status function checks the status for benchmark submitted.

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
    status_str = ''
    driver = core.get_url(core.ULCA_MDL_CONTRIB_URL, driver)
    print("BENCHMARK-STATUS : ", end="", flush=True)
    m_names = ()
    status, status_str = core.perform_webpage_function(
        ele.MDL_CONTRIB_EXP_RECORD_BTN, "click", driver)
    if status:
        for i in range(1, 10):
            try:
                name_x = driver.find_element_by_xpath(
                    ele.MDL_CONTRIB_METRICNAME_TXT[1].format(i)).text.lower()
                m_names += tuple([name_x])
            except Exception:
                break
        no_of_bmarks = len(m_names)
        if no_of_bmarks == 0:
            status = False
            status_str = 'Found no benchmarks.'
    if status:
        status, status_str, driver = get_benchmark_data(no_of_bmarks, driver)
    if status:
        while True:
            if 'In-Progress' in [i[3] for i in status_str]:
                print(colored("PENDING", "blue"))
                print_benchmarking_data(status_str)
                print("waiting for", config.PENDING_WAIT_TIME, "seconds.")
                time.sleep(config.PENDING_WAIT_TIME)
                status, status_str = core.perform_webpage_function(
                    ele.MDL_CONTRIB_REFRESH_BTN, "click", driver)
                print("BENCHMARK-STATUS : ", end="", flush=True)
                status, status_str, driver = get_benchmark_data(no_of_bmarks,
                                                                driver)
                if status:
                    continue
                else:
                    break
            else:
                break
    if status:
        status_str = 'Done'
    driver = core.print_status(status, status_str, driver)
    return driver


def select_translate_type(type1, driver):
    """
    select_translate_type function selects the type of model in the driver.

    Parameters
    ----------
    type1 : str
        type of model.
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
    if type1 == "translation-corpus":
        # click translation-model-tab
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_TRANSTAB_BTN, "click", driver)
    elif type1 == "asr-corpus":
        # click asr-model-tab
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_ASRTAB_BTN, "click", driver)
    elif type1 == "tts-corpus":
        # click tts-model-tab
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_TTSTAB_BTN, "click", driver)
    elif type1 == "ocr-corpus":
        # click ocr-model-tab
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_OCRTAB_BTN, "click", driver)
    else:
        status = False
        status_str = "not a valid type"
    return status, status_str, driver


def select_from_model_list(mlist):
    """
    select_from_model_list function selects model from the list of models.

    Parameters
    ----------
    mlist : list
        models list.

    Returns
    -------
    name : str
        selected model name.

    """
    print(colored("PENDING", "blue"))
    print()
    for i, j in enumerate(mlist):
        print(i+1, j)
    print()
    while True:
        try:
            index = int(input('ENTER THE BENCHMARK NO FROM THE ABOVE LIST:'))
            if 0 < index < len(mlist)+1:
                for i, j in enumerate(mlist):
                    if i == index-1:
                        name = j
                        break
                break
            else:
                raise Exception
        except Exception:
            print('WRONG OPTION')
    print()
    print("TRANSLATION :", end="", flush=True)
    return name


def perform_translate(name, type1, inp, driver):
    """
    perform_translate function translate the input sting based on the model.

    Parameters
    ----------
    name : str
        name of the model.
    type1 : str
        type of model.
    inp : TYPE
        input string to be converted.
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    status = True
    status_str = ""
    driver = core.get_url(core.ULCA_MDL_EXPLR_URL, driver)
    print("TRANSLATE : ", end="", flush=True)
    m_list = dict()
    status, status_str, driver = select_translate_type(type1, driver)
    if status:
        try:
            model_list = driver.find_elements_by_xpath(
                ele.MDL_EXPLR_MDLLI_TXT[1])
        except Exception:
            status = False
            status_str = "could not load/find model name."
    if status:
        for i in range(len(model_list)):
            mname = model_list[i].text.lower().strip()
            m_list[mname] = model_list[i]
        if name.lower() not in m_list.keys():
            name = select_from_model_list(m_list)
        m_list[name.lower()].click()
    time.sleep(2)
    if status:
        # click model-trynow-button
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_TRYM_BTN, "click", driver)
    if status:
        # input for translation
        status, status_str = core.perform_webpage_function(
            ele.MDL_EXPLR_IAREA_INP, "input", driver, input_data=inp)
        flag = True
        if not status:
            status, status_str = core.perform_webpage_function(
                ele.MDL_EXPLR_IAREA2_INP, "input", driver, input_data=inp)
            flag = False
    if status:
        # click model-translate-button
        if flag:
            status, status_str = core.perform_webpage_function(
                ele.MDL_EXPLR_TRANSALTE_BTN, "click", driver)
        else:
            status, status_str = core.perform_webpage_function(
                ele.MDL_EXPLR_CONVERT_BTN, "click", driver)
    if status:
        # text at output field of translation
        time.sleep(config.COMMON_WAIT_TIME*5)
        if flag:
            status, status_str = core.perform_webpage_function(
                ele.MDL_EXPLR_OAREA_TXT, "text", driver)
        else:
            status, status_str = core.perform_webpage_function(
                ele.MDL_EXPLR_OAREA2_TXT, "text", driver)
    if status:
        # preparing the string for output
        status_str = '\nINPUT="'+inp+'"\nOUTPUT="'+status_str+'"'

    driver = core.print_status(status, status_str, driver)
    return driver
