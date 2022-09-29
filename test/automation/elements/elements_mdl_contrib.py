from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dataset-mycontribution-page-elements

MDL_CONTRIB_MAIN_TABLE = {
    "name":  "MODEL-CONTRIB-PAGE-MAIN-DATA-TABLE",
    "by": By.XPATH,
    "value": '//table[@role="grid"]'
}
MDL_CONTRIB_RUNBENCH_BTN = {
    "name": "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    "by": By.XPATH,
    "value": '//*[@data-testid="MUIDataTableBodyRow-{0}"]/td[9]/div[2]/div/div[1]/button'
}
MDL_CONTRIB_PUBLISH_BTN = {
    "name": "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    "by": By.XPATH,
    "value": '//*[@data-testid="MUIDataTableBodyRow-{0}"]/td[9]/div[2]/div/div[2]/button'
}
MDL_CONTRIB_PUBLISH2_BTN = {
    "name": "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    "by": By.XPATH,
    "value":  "/html/body/div[2]/div[3]/div/div[3]/button[2]",
}
MDL_CONTRIB_BMARK_TABLE = {
    "name": "MODEL-CONTRIB-PAGE-BENCHMARCH-DATA-TABLE",
    "by": By.XPATH,
    "value":'//caption[. ="Select Benchmark Dataset and Metric"]/..',
}
MDL_CONTRIB_BSELECT_BTN = {
    "name":"MODEL-CONTRIB-PAGE-BENCHMARCH-SELECT-BUTTON",
    "by": By.XPATH,
    "value": '//caption[. ="Select Benchmark Dataset and Metric"]/../tbody/tr[{0}]/td[5]/div[2]/button'
}
MDL_CONTRIB_METRIC_TABLE = {
    "name": "MODEL-CONTRIB-PAGE-METRIC-DATA-TABLE",
    "by": By.XPATH,
    "value":'//table[@aria-label="purchases"]',
}
MDL_CONTRIB_METRICSELECT_BTN = {
    "name":"MODEL-CONTRIB-PAGE-METRIC-SELECT-BUTTON",
    "by": By.XPATH,
    "value": '//table[@aria-label="purchases"]/tbody/tr[{0}]/td[2]/button'
}
MDL_CONTRIB_BSUBMIT_BTN = {
    "name":  "MODEL-CONTRIB-PAGE-BENCHMARCH-SUBMIT-BUTTON",
    "by": By.XPATH,
    "value":'//button[.="Submit"]',
}
MDL_CONTRIB_EXP_RECORD_BTN = {
    "name": "MODEL-CONTRIB-PAGE-EXPAND-RECORD-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="MUIDataTableBodyRow-{}"]/td[1]/div/button',
}
MDL_CONTRIB_METRICNAME_TXT = {
    "name":  "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICNAME-TEXT",
    "by": By.XPATH,
    "value":'//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[1]',
}
MDL_CONTRIB_METRICTYPE_TXT = {
    "name": "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICTYPE-TEXT",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[2]',
}
MDL_CONTRIB_METRICSCOR_TXT = {
    "name":  "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICSCORE-TEXT",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[3]',
}
MDL_CONTRIB_METRICSTTS_TXT = {
    "name":  "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICSTATUS-TEXT",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[5]',
}
MDL_CONTRIB_REFRESH_BTN = {
    "name": "MODEL-CONTRIB-PAGE-BENCHMARCH-REFRESH-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div[1]/div[2]/button[2]',
}
