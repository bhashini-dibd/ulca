from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dataset-mysearchs-elements
DS_MYSRCH_NAME_TXT = {
    "name": "DATASET-MYSEARCH-PAGE-1STROW-NAME-TEXT",
    "by": By.XPATH,
    "value": '//*[@data-testid="MUIDataTableBodyRow-0"]/td[1]/div[2]',
}
DS_MYSRCH_CNT_TXT = {
    "name": "DATASET-MYSEARCH-PAGE-1STROW-COUNT",
    "by": By.XPATH,
    "value": '//*[@data-testid="MUIDataTableBodyRow-0"]/td[3]/div[2]',
}
DS_MYSRCH_STTS_TXT = {
    "name": "DATASET-MYSEARCH-PAGE-1STROW-STATUS",
    "by": By.XPATH,
    "value": '//*[@data-testid="MUIDataTableBodyRow-0"]/td[4]/div[2]',
}
DS_MYSRCH_SMPFILE_A = {
    "name": "DATASET-MYSEARCH-PAGE-SAMPLEFILE-A-HREF",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div[2]/div/div/div[2]/a[1]',
}
DS_MYSRCH_ALLFILE_A = {
    "name": "DATASET-MYSEARCH-PAGE-ALLFILE-A-HREF",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div/div/div[2]/div/div/div[2]/a[2]',
}
