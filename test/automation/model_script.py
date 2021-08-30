#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 11:46:43 2021.

@author: dhiru579 @ Tarento.
"""

import time
import config
import core_script as core

ELE_MODELSUBMITNAME_INP = ['MODEL-SUBMIT-NAME-INPUT-FIELD',
                           '//*[@id="root"]/div/div/div/div/div/div[2]/div/' +
                           'div[3]/div/div/div[2]/div/div[1]/div/div/input']
ELE_MODELSUBMITFILE_INP = ['MODEL-SUBMIT-FILE-INPUT-FIELD',
                           '//*[@id="root"]/div/div/div/div/div/div[2]/div' +
                           '/div[3]/div/div/div[2]/div/div[2]/div/div/' +
                           'div/div/input']
ELE_MODELSUBMIT_BTN = ['MODEL-SUBMIT-BUTTON',
                       '//*[@id="root"]/div/div/div/div/div/div[2]/' +
                       'div/div[3]/div/button']

ELE_MODELTASK_BTN = ['MODEL-SEARCH-TASK-SELECT-BuTtoN'
                     '//*[@id="root"]/div/div/div/div/div[1]/div/div[2]' +
                     '/div[1]/button']
ELE_MODELTRYM_BTN = ['MODEL-TRY_MODEL-BUTTON',
                     '//*[@id="root"]/div/div/div[1]/button']
ELE_MODELINPUTAREA_INP = ['MODEL-INPUT-TEXTAREA',
                          '//*[@id="root"]/div/div/div/div[1]/div/div[1]/' +
                          'div[2]/textarea']
ELE_MODELOUTPUTAREA_TXT = ['MODEL-OUTPUT-TEXTAREA',
                           '//*[@id="root"]/div/div/div/div[1]/div/div[2]/' +
                           'div[2]/textarea']
ELE_MODELTRANSALTE_BTN = ['MODEL-OUTPUT-TEXTAREA',
                          '//*[@id="root"]/div/div/div/div[1]/div/div[1]/' +
                          'div[3]/div/div[2]/button']
ELE_MODELTRANSLATION_BTN = ['MODEL-TRANSALTION-BTN', '//*[@id="simple-tab-0"]']
ELE_MODELASR_BTN = ['MODEL-ASR-BUTTON', '//*[@id="simple-tab-1"]']
ELE_MODELTTS_BTN = ['MODEL-TTS-BUTTON', '//*[@id="simple-tab-2"]']
ELE_MODELOCR_BTN = ['MODEL-OCR-BUTTON', '//*[@id="simple-tab-3"]']


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
    driver = core.get_url(core.ULCA_MODEL_SUBMIT_URL, driver)
    # input for submit-model-name
    status, status_str = core.perform_webpage_function(
        ELE_MODELSUBMITNAME_INP, "input", driver, input_data=name)
    if status:
        # input for submit-model-file
        status, status_str = core.perform_webpage_function(
            ELE_MODELSUBMITFILE_INP, "input", driver, input_data=file)
    if status:
        # click submit-model-button
        status, status_str = core.perform_webpage_function(
            ELE_MODELSUBMIT_BTN, "click", driver)
    if status:
        status_str = "submitted"
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
            ELE_MODELTRANSLATION_BTN, "click", driver)
    elif type1 == "asr-corpus":
        # click asr-model-tab
        status, status_str = core.perform_webpage_function(
            ELE_MODELASR_BTN, "click", driver)
    elif type1 == "tts-corpus":
        # click tts-model-tab
        status, status_str = core.perform_webpage_function(
            ELE_MODELTTS_BTN, "click", driver)
    elif type1 == "ocr-corpus":
        # click ocr-model-tab
        status, status_str = core.perform_webpage_function(
            ELE_MODELOCR_BTN, "click", driver)
    else:
        status = False
        status_str = "not a valid type"
    return status, status_str, driver


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
    driver = core.get_url(core.ULCA_MODEL_EXPLORE_URL, driver)
    print("TRANSLATE : ", end="", flush=True)
    status, status_str, driver = select_translate_type(type1, driver)
    if status:
        try:
            model_list = driver.find_elements_by_tag_name('h6')
        except Exception:
            status = False
            status_str = "could not load/find model name."
    if status:
        for i in range(len(model_list)):
            mname = model_list[i].text.lower().strip()
            if mname == name.lower():
                model_list[i].click()
                break
            if i == len(model_list)-1:
                status = False
                status_str = 'model name not found.'
    time.sleep(2)
    if status:
        # click model-trynow-button
        status, status_str = core.perform_webpage_function(
            ELE_MODELTRYM_BTN, "click", driver)
    if status:
        # input for translation
        status, status_str = core.perform_webpage_function(
            ELE_MODELINPUTAREA_INP, "input", driver, input_data=inp)
    if status:
        # click model-translate-button
        status, status_str = core.perform_webpage_function(
            ELE_MODELTRANSALTE_BTN, "click", driver)
    if status:
        # text at output field of translation
        time.sleep(config.COMMON_WAIT_TIME*5)
        status, status_str = core.perform_webpage_function(
            ELE_MODELOUTPUTAREA_TXT, "text", driver)
    if status:
        # preparing the string for output
        status_str = '\nINPUT="'+inp+'"\nOUTPUT="'+status_str+'"'

    driver = core.print_status(status, status_str, driver)
    return driver
