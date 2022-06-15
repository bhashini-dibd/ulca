#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Dec  4 17:35:27 2021.

@author: dhiru579 @ Tarento
"""
import os
from argparse import ArgumentParser

import config
import driver_script
from core import core_all as core
from test import test_all as test
from dataset import dataset_all as dataset
from model import model_all as model

if __name__ == "__main__":
    parser = ArgumentParser()
    # flag-arguments
    parser.add_argument("-u", "--update-schema", help="updating schema", action="store_true")
    parser.add_argument("-c", "--chart", help="gets chart data", action="store_true")
    parser.add_argument("-ta","--test-all", help="performs website testing", action="store_true")
    parser.add_argument("-tp","--test-part", help="performs website testing", action="store_true")
    parser.add_argument("-l", "--login", help="checking credentials", action="store_true")
    parser.add_argument("-d", "--dataset", help="flag for dataset functions", action="store_true")
    parser.add_argument("-m", "--model", help="flag for model functions", action="store_true")
    parser.add_argument("--publish", help="publish the model", action="store_true")
    parser.add_argument("--unpublish", help="unpublish the model", action="store_true")
    parser.add_argument("--dont-skip", action="store_true", help="stops skipping status check",)
    # value-argumenst
    parser.add_argument("-n", "--name", help="Dataset/Model Name", type=str, default="")
    parser.add_argument("-url", "--url", help="Dataset/Model URL", type=str, default="")
    parser.add_argument("-t", "--type", help="Dataset/Model Type", type=str, default="")
    parser.add_argument("-i", "--input", help="input csv/jsonfile,urls,seentence", type=str, default="",)
    parser.add_argument("-g","--group",help="group chart by ['domain', 'collection', 'submitter']",type=str,default="",)
    parser.add_argument("-src", "--source", help="source language", type=str, default="")
    parser.add_argument("-tgt", "--target", help="target languages", type=str, default="")
    parser.add_argument("--domain", help="domain for searched dataset", type=str, default="")
    parser.add_argument("-b", "--benchmark", help="Benchamrk Name", type=str, default="")
    parser.add_argument("--metric", help="Metric Name", type=str, default="")
    parser.add_argument("--list", help="listing models,benchmark,metrics", type=str, default="")

    args = parser.parse_args()

    update_schema_flag = args.update_schema
    test_a_flag = args.test_all
    test_p_flag = args.test_part
    chart_flag = args.chart
    groupby = args.group

    login_flag = args.login
    dataset_flag = args.dataset
    model_flag = args.model
    publish_flag = args.publish
    unpublish_flag = args.unpublish
    dont_skip_flag = args.dont_skip

    inp = args.input.strip()
    name = args.name.strip()
    url = args.url.strip()

    typex = args.type.lower().replace(" ", "-").strip()
    domain = args.domain.lower().replace(" ", "-").strip()
    benchmark = args.benchmark.strip()
    metric = args.metric.strip()
    src = args.source.lower()
    tgt = args.target.lower().split(",")  # spliting str into list
    tgt = list(filter(None, tgt))  # filtering list
    listby = args.list.lower()
    
    # enabling-terminal-color-in-windows-only
    if os.name.lower() == "nt":
        os.system("color")
    

    # updating schema
    if update_schema_flag:
        core.update_schema(config.ULCA_SCHEMAFILE_URL, config.SCHEMA_FILENAME)
        core.exit_program()

    # loading the driver
    driver = driver_script.load_driver(config.AUTOMATION_BROWSER)

    # getting chart data
    if chart_flag:
        if typex.find("-corpus") < 0:
            typex += "-corpus"
        driver = core.get_chart_data(typex, groupby, src, tgt, driver)
        driver_script.close_driver(driver)
        core.exit_program()

    # running public-web-test script
    if test_a_flag:
        driver = test.perform_testing_all(core.perform_login,driver)
        driver_script.close_driver(driver)
        core.exit_program()
    if test_p_flag:
        driver = test.perform_testing_partly(core.perform_login,driver)
        driver_script.close_driver(driver)
        core.exit_program()
    
    login_status = False
   
    # if just login then quit
    if login_flag:
        login_status, driver = core.perform_login(driver)
        driver_script.close_driver(driver)
        core.exit_program()
    # dataset related processes
    elif dataset_flag:
        login_status, driver = core.perform_login(driver)
        # adding -corpus to the dataset-type
        if typex.find("-corpus") < 0:
            typex += "-corpus"
        if name != "":
            # submit-dataset
            driver = dataset.perform_submit_get_status(name, url, driver)
        elif inp != "":
            # submit-dataset-using-csv
            driver = dataset.perform_upload_with_csv(inp, driver, d_skip=dont_skip_flag)
        else:
            # search-and-download-dataset
            driver = dataset.perform_search_and_download(typex, tgt, src, domain, driver)
    
    # model related processes
    elif model_flag:
        if typex != "":
            driver = model.perform_translate(name, typex, inp, driver)
        else:
            login_status, driver = core.perform_login(driver)    
            if inp != "":
                status, driver = model.perform_model_submit(name, inp, driver)
            elif listby != "":
                driver = model.list_public_model_data(True,driver)        
            elif (unpublish_flag or publish_flag): #status and
                driver = model.run_publish_task(name, publish_flag, unpublish_flag, driver)
            else:
                driver = model.run_benchmark(name, benchmark, metric, driver)
    
    else:
        core.print_no_flag_found()
        
    
    driver_script.close_driver(driver)
    
