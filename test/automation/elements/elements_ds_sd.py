from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dataset-searchAndDownload-page-elements
DS_SD_TYPELIST_BTN = {
    "name": "DATASET-S/D-PAGE-DATATYPE-LIST-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Parallel Dataset"]',
}
DS_SD_DTYPE_CHOSE_BTN = {
    "name": "DATASET-S/D-PAGE-DATATYPE-CHOOSE-BUTTON=",
    "by": By.XPATH,
    "value": '//*[@value="{0}"]',
}
DS_SD_SRCLANG_INP = {
    "name": "DATASET-S/D-PAGE-SOURCE-LANG-INPUT-FEILD",
    "by": By.ID,
    "value": "source",
}
DS_SD_TGTLANG_INP = {
    "name": "DATASET-S/D-PAGE-TARGET-LANG-INPUT-FEILD",
    "by": By.ID,
    "value": "language-target",
}
DS_SD_DOMAIN_INP = {
    "name": "DATASET-S/D-PAGE-DOMAIN-INPUT-FEILD",
    "by": By.ID,
    "value": "domain",
}
DS_SD_SUBMIT_BTN = {
    "name": "DATASET-S/D-PAGE-SUBMIT-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Submit"]',
}
DS_SD_SRN_TXT = {
    "name": "DATASET-S/D-PAGE-SRN-NO",
    "by": By.TAG_NAME,
    "value": "h5",
}
