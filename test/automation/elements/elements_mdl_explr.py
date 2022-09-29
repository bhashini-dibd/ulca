from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector


# model-explore-page-elements
MDL_EXPLR_TRYM_BTN = {
    "name": "MODEL-EXPLORE-PAGE-TRYMODEL-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Try Model"]',
}
MDL_EXPLR_IA_TRS_INP = {
    "name": "MODEL-EXPLORE-PAGE-INPUT1-TEXTAREA",
    "by": By.XPATH,
    "value": '//textarea[@placeholder="Enter Text"]',
}
MDL_EXPLR_IA_ASROCR_INP = {
    "name": "MODEL-EXPLORE-PAGE-INPUT2-TEXTAREA",
    "by": By.TAG_NAME,
    "value": 'input',
}
MDL_EXPLR_OA_TRS_TXT = {
    "name": "MODEL-EXPLORE-PAGE-OUTPUT-TEXTAREA",
    "by": By.XPATH,
    "value": '//textarea[@disabled]',
}
MDL_EXPLR_OA_ASR_TXT = {
    "name": "MODEL-EXPLORE-PAGE-OUTPUT2-TEXTAREA",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div[2]/div[1]/div/div[4]/div/div[2]',
}
MDL_EXPLR_OA_OCR_TXT = {
    "name": "MODEL-EXPLORE-PAGE-OUTPUT2-TEXTAREA",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div[2]/div[1]/div/div[2]/div/div[2]',
}
MDL_EXPLR_TRANSLATE_BTN = {
    "name": "MODEL-EXPLORE-PAGE-TRANSALTE-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Translate"]',
}
MDL_EXPLR_CNVT_ASROCR_BTN = {
    "name": "MODEL-EXPLORE-PAGE-CONVERT-ASR/OCR-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Convert"]',
}
MDL_EXPLR_TRANSTAB_BTN = {
    "name": "MODEL-EXPLORE-PAGE-TRANSLATION-TAB-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="Translation"]',
}
MDL_EXPLR_ASRTAB_BTN = {
    "name": "MODEL-EXPLORE-PAGE-ASR-TAB-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="ASR"]',
}
MDL_EXPLR_TTSTAB_BTN = {
    "name": "MODEL-EXPLORE-PAGE-TTS-TAB-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="TTS"]',
}
MDL_EXPLR_OCRTAB_BTN = {
    "name": "MODEL-EXPLORE-PAGE-OCR-TAB-BUTTON",
    "by": By.XPATH,
    "value": '//button[. ="OCR"]',
}
MDL_EXPLR_MDLLI_TXT = {
    "name": "MODEL-EXPLORE-PAGE-MODEL-LIST-TEXT",
    "by": By.TAG_NAME,
    "value": "h6",
}
MDL_EXPLR_ASR_RECORD_BTN = {
    "name": "MODEL-EXPLORE-PAGE-ASR-LIVE-RECORD-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div[2]/div[1]/div/div[1]/div/div[2]/div[1]/img',
}
MDL_EXPLR_ASR_OPUT_TXT = {
    "name": "MODEL-EXPLORE-PAGE-ASR-LIVE-RECORD-OUTPUT-TEXT",
    "by": By.XPATH,
    "value": '//*[@id="asrCardOutput"]',
}
