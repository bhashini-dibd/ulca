from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dataser-submit-page-elements
DS_SUBMIT_NAME_INP = {
    "name": "DATASET-SUBMIT-PAGE-NAME-INPUT",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[1]/div/div/input',
}
DS_SUBMIT_URL_INP = {
    "name": "DATASET-SUBMIT-PAGE-URL-INPUT",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[2]/div/div/input',
}
DS_SUBMIT_SUBMIT_BTN = {
    "name": "DATASET-SUBMIT-PAGE-SUBMIT-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div/div/div[3]/div/button',
}
DS_SUBMIT_SRN_TXT = {
    "name": "DATASET-SUBMIT-PAGE-SRN-TXT",
    "by": By.TAG_NAME,
    "value": 'h5',
}
