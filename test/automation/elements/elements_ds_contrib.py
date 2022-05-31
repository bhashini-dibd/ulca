from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dataset-mycontribution-page-elements
DS_CONTRB_MAIN_TABLE = {
    "name": "DATASET-MYCONTRIB-MAIN-TABLE",
    "by": By.XPATH,
    "value": '//table[@role="grid"]',
}
DS_CONTRB_NAME_BTN = {
    "name": "DATASET-MYCONTRIB-PAGE-DS-NAME-BUTTON",
    "by": By.XPATH,
    "value": '//*[@data-testid="MuiDataTableBodyCell-1-{0}"]',
}
DS_CONTRIB_LOG_A = {
    "name":"DATASET-MYCONTRIB-PAGE-ERROR-LOG-FILE-ATAG",
    "by": By.XPATH,
    "value": '//*[@class="jss649"]//a',
}
