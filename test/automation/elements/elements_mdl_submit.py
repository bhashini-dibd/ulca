from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dashboard-page-elements
MDL_SUBMIT_NAME_INP = {
    "name": "MODEL-SUBMIT-PAGE-NAME-INPUT-FIELD",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[1]/div/div/input',
}
MDL_SUBMIT_FILE_INP = {
    "name": "MODEL-SUBMIT-PAGE-FILE-INPUT-FIELD",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/input',
}
MDL_SUBMIT_BTN = {
    "name": "MODEL-SUBMIT-PAGE-SUBMIT-BUTTON",
    "by": By.XPATH,
    "value": '//button[. = "Submit"]',
}
MDL_SUBMIT_SRN_TXT = {
    "name": "MODEL-SUBMIT-PAGE-SRN-H5-TEXT",
    "by": By.TAG_NAME,
    "value": 'h5',
}
