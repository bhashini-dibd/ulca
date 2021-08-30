#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 10:45:14 2021.

@author: dhiru579 @ Tarento.
"""
import os
import sys
from argparse import ArgumentParser

import config
import driver_script
import core_script as core
import dataset_script as dataset
import model_script as model


if __name__ == "__main__":
    parser = ArgumentParser()

    parser.add_argument("--update-schema",
                        help="updates the schema", action="store_true")
    parser.add_argument("--chart-data",
                        help="gets chart data", action="store_true")
    parser.add_argument("-g", "--groupby", help="groupby in chart " +
                        "['domain', 'collection', 'submitter']",
                        type=str, default="")
    parser.add_argument("--test-website",
                        help="performs website testing on browser",
                        action="store_true")

    parser.add_argument("-l", "--login", help="checks if credentials valid",
                        action="store_true")
    parser.add_argument("-d", "--dataset", help="flag for dataset functions",
                        action="store_true")
    parser.add_argument("-m", "--model", help="flag for model functions",
                        action="store_true")
    parser.add_argument("-w", "--wrapper", help="complete automation flag",
                        action="store_true")

    parser.add_argument("-i", "--input",
                        help="input file [dataset=csv|model=json]",
                        type=str, default="")
    parser.add_argument("-n", "--name", help="Dataset Name",
                        type=str, default="")
    parser.add_argument("-url", "--url", help="Dataset URL",
                        type=str, default="")\

    parser.add_argument("-t", "--type", help="Dataset Type",
                        type=str, default="")
    parser.add_argument("-src", "--source-lang", help="source language",
                        type=str, default="")
    parser.add_argument("-tgt", "--target-lang",
                        help="target language eg:hi,mr", type=str, default="")
    parser.add_argument("-dom", "--domain", help="domain for searched dataset",
                        type=str, default="")
    parser.add_argument("-coll", "--collection-method",
                        help="collection method for dataset",
                        type=str, default="")
    parser.add_argument("-ma", "--m-annotators", action="store_true",
                        help="multiple annotators for downloading")
    parser.add_argument("-mt", "--m-translators", action="store_true",
                        help="multiple translators for downloading")
    parser.add_argument("-org", "--org-source", action="store_true",
                        help="orginal source sentences")

    args = parser.parse_args()
    update_schema_flag = args.update_schema
    test_flag = args.test_website
    chart_flag = args.chart_data
    groupby = args.groupby

    login_flag = args.login
    dataset_flag = args.dataset
    model_flag = args.model
    wrapper_flag = args.wrapper

    inpfile = args.input.strip()
    name = args.name.strip()
    url = args.url.strip()

    typex = args.type.lower().replace(' ', "-").strip()
    tgt = args.target_lang.lower().split(',')
    tgt = list(filter(None, tgt))
    src = args.source_lang.lower()
    coll_mtd = args.collection_method.lower().replace(' ', "-").strip()
    domain = args.domain.lower().replace(' ', "-").strip()
    m_transl = args.m_translators
    m_anno = args.m_annotators
    org_source = args.org_source

    # enabling-terminal-color-in-windows-only
    if os.name.lower() == 'nt':
        os.system('color')
    # adding -corpus to the dataset-type
    if typex.find("-corpus") < 0:
        typex += "-corpus"

    # updating schema
    if update_schema_flag:
        core.update_schema(config.ULCA_SCHEMAFILE_URL,
                           config.SCHEMA_FILENAME)
        sys.exit(0)

    # loading the driver
    driver = driver_script.load_driver(config.DEFAULT_BROWSER)

    # running test script
    if test_flag:
        driver = core.test_elements_with_browser(driver)
        driver_script.close_driver(driver)
        sys.exit(0)

    # getting chart data
    if chart_flag:
        driver = core.get_chart_data(typex, groupby, src, tgt, driver)
        driver_script.close_driver(driver)
        sys.exit(0)

    # login process
    login_status, driver = core.perform_login(driver)

    if login_flag or (not login_status):
        driver_script.close_driver(driver)
        sys.exit(0)
    # dataset related processes
    elif dataset_flag:
        if name != "":
            driver = dataset.perform_upload_with_status(name, url, driver)
        elif inpfile != "":
            driver = dataset.perform_upload_with_csv(
                inpfile, driver)
        else:
            driver = dataset.perform_search_and_download(typex, tgt, src,
                                                         domain, coll_mtd,
                                                         m_anno, m_transl,
                                                         org_source, driver)
    # model related processes
    elif model_flag:
        driver = model.perform_translate(name, typex, inpfile, driver)
        # driver = model.submit_model(dataset_name, csvfile, driver)
    # wrapper process
    elif wrapper_flag:
        print('wrapper method still in-progress.')
    else:
        print("no argument found. choices=[-l,-s,-d]")
        print("-h for help")

    # performs logount and closes browser-driver
    core.perform_logout(driver)
    driver_script.close_driver(driver)
