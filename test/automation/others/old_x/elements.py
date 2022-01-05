#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Sep  1 10:31:41 2021.

@author: dhiru579 @ Tarento.
"""

# xpaths-of-buttons/elements-in-ULCA-websites
#         list -> 0 -> name of the element
#         list -> 1 -> xpath of the element (only-support-xpaths)
#
LOGIN_USERNAME_INP = ["LOGIN-PAGE-USERNAME-INPUT-FIELD", '//*[@id="outlined-required"]']
LOGIN_PASSWORD_INP = [
    "LOGIN-PAGE-PASSWORD-INPUT-FIELD",
    '//*[@id="outlined-adornment-password"]',
]
LOGIN_BTN = ["LOGIN-PAGE-LOGIN-BUTTON", "//*[@id='root']/div/div/div/div/form/button"]


DS_SD_TYPELIST_BTN = [
    "DATASET-S/D-PAGE-DATATYPE-LIST-BUTTON",
    '//*[@id="root"]/div/div/div/div/div/div[1]/div/div/div[1]/button',
]
DS_SD_SRCLANG_INP = ["DATASET-S/D-PAGE-SOURCE-LANG-INPUT-FEILD", '//*[@id="source"]']
DS_SD_TGTLANG_INP = [
    "DATASET-S/D-PAGE-TARGET-LANG-INPUT-FEILD",
    '//*[@id="language-target"]',
]
DS_SD_DOMAIN_INP = ["DATASET-S/D-PAGE-DOMAIN-INPUT-FEILD", '//*[@id="domain"]']
DS_SD_COLLM_INP = [
    "DATASET-S/D-PAGE-COLLECTION-METHOD-INPUT-FEILD",
    '//*[@id="collectionMethod"]',
]
DS_SD_MA_CB = [
    "DATASET-S/D-PAGE-MULTIPLE-ANNOTATORS-CHECKVOX-FEILD",
    '//*[@name="checkedA"]',
]
DS_SD_MT_CB = [
    "DATASET-S/D-PAGE-MULTIPLE-TRANSLATORS-CHECKBOX-FEILD",
    '//*[@name="checkedB"]',
]
DS_SD_OS_CB = [
    "DATASET-S/D-PAGE-ORGINAL-SOURCE-CHECKBOX-FEILD",
    '//*[@name="checkedC"]',
]
DS_SD_SUBMIT_BTN = [
    "DATASET-S/D-PAGE-SUBMIT-BUTTON",
    '//*[@id="root"]/div/div/div/div/div/div[1]/div/div/div[7]/div[2]/div/div[2]/button',
]
DS_SD_SRN_TXT = [
    "DATASET-S/D-PAGE-SRN-NO",
    "//*[@id='root']/div/div/div/div/div/div[2]/div/div/div/h5",
]




DS_SUBMIT_NAME_INP = [
    "DATASET-SUBMIT-PAGE-NAME-INPUT",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[1]/div/div/input',
]
DS_SUBMIT_URL_INP = [
    "DATASET-SUBMIT-PAGE-URL-INPUT",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[2]/div/div/input',
]
DS_SUBMIT_SUBMIT_BTN = [
    "DATASET-SUBMIT-PAGE-SUBMIT-BUTTON",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/button',
]
DS_SUBMIT_SRN_TXT = [
    "DATASET-SUBMIT-PAGE-SRN-TXT",
    '//*[@id="root"]/div/div/div/div/div/h5',
]


DS_CONTRB_NAME_TXT = [
    "DATASET-MYCONTRIB-PAGE-1STROW-NAME-TXT",
    '//*[@id="MUIDataTableBodyRow-0"]/td[1]/div[2]',
]
DS_CONTRB_STATUS_TXT = [
    "DATASET-MYCONTRIB-PAGE-1STROW-STATUS-TXT",
    '//*[@id="MUIDataTableBodyRow-0"]/td[4]/div[2]',
]
DS_CONTRB_RFRSH_BTN = [
    "DATASET-MYCONTRIB-PAGE-REFRESH-BUTTON",
    '//*[@id="root"]/div/div/div/div/div[2]/div/button[1]',
]
DS_CONTRIB_DWN_STTS_TXT = [
    "DATASET-MYCONTRIB-PAGE-DOWNLOAD-STATUS-TXT",
    '//*[@id="MUIDataTableBodyRow-0"]/td[2]/div[2]',
]
DS_CONTRIB_ING_STTS_TXT = [
    "DATASET-MYCONTRIB-PAGE-INGEST-STATUS-TXT",
    '//*[@id="MUIDataTableBodyRow-1"]/td[2]/div[2]',
]
DS_CONTRIB_ING_SC_TXT = [
    "DATASET-MYCONTRIB-PAGE-INGEST-SUCCESS-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-1"]/td[3]/div[2]',
]
DS_CONTRIB_ING_FC_TXT = [
    "DATASET-MYCONTRIB-PAGE-INGEST-FAILURE-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-1"]/td[4]/div[2]',
]
DS_CONTRIB_VLD_STTS_TXT = [
    "DATASET-MYCONTRIB-PAGE-VALIDATE-STATUS-TXT",
    '//*[@id="MUIDataTableBodyRow-2"]/td[2]/div[2]',
]
DS_CONTRIB_VLD_SC_TXT = [
    "DATASET-MYCONTRIB-PAGE-VALIDATE-SUCCESS-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-2"]/td[3]/div[2]',
]
DS_CONTRIB_VLD_FC_TXT = [
    "DATASET-MYCONTRIB-PAGE-VALIDATE-FAILURE-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-2"]/td[4]/div[2]',
]
DS_CONTRIB_PBL_STTS_TXT = [
    "DATASET-MYCONTRIB-PAGE-PUBLISH-STATUS-TXT",
    '//*[@id="MUIDataTableBodyRow-3"]/td[2]/div[2]',
]
DS_CONTRIB_PBL_SC_TXT = [
    "DATASET-MYCONTRIB-PAGE-PUBLISH-SUCCESS-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-3"]/td[3]/div[2]',
]
DS_CONTRIB_PBL_FC_TXT = [
    "DATASET-MYCONTRIB-PAGE-PUBLISH-FAILURE-COUNT-TXT",
    '//*[@id="MUIDataTableBodyRow-3"]/td[4]/div[2]',
]
DS_CONTRIB_LOG_A = [
    "DATASET-MYCONTRIB-PAGE-ERROR-LOG-FILE-ATAG",
    '//*[@id="root"]/div/div/div/div/div[1]/div/a',
]


DASH_PROFILE_BTN = [
    "DASHBOARD-PAGE-PROFILE-BUTTON",
    '//*[@id="root"]/div/header/div/div/div[3]/button',
]
DASH_LOGOUT_BTN = ["DASHBOARD-PAGE-LOGOUT-BUTTON", "//*[@id='data-set']/div[3]/ul"]
DASH_DTYPE_SLCT_BTN = [
    "DASHBOARD-DATATYPE-SELECT-BUTTON",
    '//*[@id="root"]/div/div/header/div/div/div[1]/button',
]
DASH_CHART_SRCLANG_INP = ["DASHBOARD-PARALLEL-SOURCE-LANG-INPUT", '//*[@id="source"]']
DASH_CHART_LNAMES_LI = [
    "DASHBOARD-CHART-LANGUAGE-NAMES-LIST",
    '//*[@class="recharts-layer recharts-cartesian-axis-tick"]',
]
DASH_CHART_LRECTS_LI = [
    "DASHBOARD-CHART-RECTANGLE-LIST",
    '//*[@class="recharts-layer recharts-bar-rectangle"]',
]
DASH_CHART_LCOUNTS_LI = [
    "DASHBOARD-CHART-LANGUAGE-COUNT-LIST",
    "//*[@class='recharts-text recharts-label']",
]
DASH_CHART_TOTCNT_TXT = [
    "DASHBOARD-DATATYPE-TOTAL-COUNT-H6",
    "//*[@id='root']/div/div/header/div/div/div[2]/h6",
]
DASH_CHART_SRCCNT_TXT = [
    "DASHBOARD-DATATYPE-SRC-LANG-COUNT-H6",
    '//*[@id="root"]/div/div/div/div[1]/div/div/h6',
]
DASH_CHART_GROUPCOLLM_BTN = [
    "DASHBOARD-GROUPBY-COLLECTION-BUTTON",
    '//*[@id="root"]/div/div/header/div/div/div[4]/div/div/button[2]',
]
DASH_CHART_GROUPSUBM_BTN = [
    "DASHBOARD-GROUPBY-SUBMITTER-BUTTON",
    '//*[@id="root"]/div/div/header/div/div/div[4]/div/div/button[3]',
]

MDL_SUBMIT_NAME_INP = [
    "MODEL-SUBMIT-PAGE-NAME-INPUT-FIELD",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[1]/div/div/input',
]
MDL_SUBMIT_FILE_INP = [
    "MODEL-SUBMIT-PAGE-FILE-INPUT-FIELD",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/div/div[2]/div/div[2]/div/div/div/div/input',
]
MDL_SUBMIT_BTN = [
    "MODEL-SUBMIT-PAGE-SUBMIT-BUTTON",
    '//*[@id="root"]/div/div/div/div/div/div/div/div[3]/div/button',
]
MDL_SUBMIT_SRN_TXT = [
    "MODEL-SUBMIT-PAGE-SRN-H5-TEXT",
    '//*[@id="root"]/div/div/div/div/div/h5',
]

MDL_EXPLR_TRYM_BTN = [
    "MODEL-EXPLORE-PAGE-TRYMODEL-BUTTON",
    '//*[@id="root"]/div/div/div[1]/div/div/div/div/div[2]/button',
]
MDL_EXPLR_IA_TRS_INP = [
    "MODEL-EXPLORE-PAGE-INPUT1-TEXTAREA",
    '//*[@id="root"]/div/div/div/div[1]/div/div[1]/div[2]/textarea',
]
MDL_EXPLR_IA_ASR_INP = [
    "MODEL-EXPLORE-PAGE-INPUT2-TEXTAREA",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[3]/div/div[2]/div/div/input',
]
MDL_EXPLR_IA_OCR_INP = [
    "MODEL-EXPLORE-PAGE-INPUT2-TEXTAREA",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[1]/div/div[2]/div/div/input',
]
MDL_EXPLR_OA_TRS_TXT = [
    "MODEL-EXPLORE-PAGE-OUTPUT-TEXTAREA",
    '//*[@id="root"]/div/div/div/div[1]/div/div[2]/div[2]/textarea',
]
MDL_EXPLR_OA_ASR_TXT = [
    "MODEL-EXPLORE-PAGE-OUTPUT2-TEXTAREA",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[4]/div/div[2]',
]
MDL_EXPLR_OA_OCR_TXT = [
    "MODEL-EXPLORE-PAGE-OUTPUT2-TEXTAREA",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[2]/div/div[2]',
]
MDL_EXPLR_TRANSLATE_BTN = [
    "MODEL-EXPLORE-PAGE-TRANSALTE-BUTTON",
    '//*[@id="root"]/div/div/div/div[1]/div/div[1]/div[3]/div/div[2]/button',
]
MDL_EXPLR_CNVT_ASR_BTN = [
    "MODEL-EXPLORE-PAGE-CONVERT-BUTTON",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[3]/div/div[3]/button',
]
MDL_EXPLR_CNVT_OCR_BTN = [
    "MODEL-EXPLORE-PAGE-CONVERT-BUTTON",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[1]/div/div[3]/button',
]
MDL_EXPLR_TRANSTAB_BTN = [
    "MODEL-EXPLORE-PAGE-TRANSLATION-TAB-BUTTON",
    "/html/body/div/div/div/div/div/div/div/header/div/div[1]/div/div/div/button[1]",
]
MDL_EXPLR_ASRTAB_BTN = [
    "MODEL-EXPLORE-PAGE-ASR-TAB-BUTTON",
    "/html/body/div/div/div/div/div/div/div/header/div/div[1]/div/div/div/button[2]",
]
MDL_EXPLR_TTSTAB_BTN = [
    "MODEL-EXPLORE-PAGE-TTS-TAB-BUTTON",
    "/html/body/div/div/div/div/div/div/div/header/div/div[1]/div/div/div/button[3]",
]
MDL_EXPLR_OCRTAB_BTN = [
    "MODEL-EXPLORE-PAGE-OCR-TAB-BUTTON",
    "/html/body/div/div/div/div/div/div/div/header/div/div[1]/div/div/div/button[4]",
]
MDL_EXPLR_MDLLI_TXT = ["MODEL-EXPLORE-PAGE-MODEL-LIST-TEXT", "//h6"]
MDL_EXPLR_EXPLR_TAB1_BTN = ["MODEL-PAGE-EXPLORE-TAB-BUTTON", '//*[@id="simple-tab-1"]']
MDL_EXPLR_BDS_TAB2_BTN = [
    "MODEL-PAGE-BENCHMARK-DATASETS-TAB-BUTTON",
    '//*[@id="simple-tab-2"]',
]
MDL_EXPLR_ASR_RECORD_BTN = [
    "MODEL-EXPLORE-PAGE-ASR-LIVE-RECORD-BUTTON",
    '//*[@id="root"]/div/div/div[2]/div[1]/div/div[1]/div/div[2]/div[1]/img',
]
MDL_EXPLR_ASR_OPUT_TXT = [
    "MODEL-EXPLORE-PAGE-ASR-LIVE-RECORD-OUTPUT-TEXT",
    '//*[@id="asrCardOutput"]',
]
MDL_CONTRIB_NAME_TXT = [
    "MODEL-CONTRIB-PAGE-MDLNAME-TEXT",
    '//*[@id="MUIDataTableBodyRow-{}"]/td[3]/div[2]',
]
MDL_CONTRIB_STTS_TXT = [
    "MODEL-CONTRIB-PAGE-STATUS-TEXT",
    '//*[@id="MUIDataTableBodyRow-0"]/td[7]/div[2]/p',
]
MDL_CONTRIB_RUNBENCH_BTN = [
    "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    '//*[@id="MUIDataTableBodyRow-{}"]/td[9]/div[2]/div/div[1]/button',
]
MDL_CONTRIB_PUBLISH_BTN = [
    "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    '//*[@id="MUIDataTableBodyRow-{}"]/td[9]/div[2]/div/div[2]/button',
]
MDL_CONTRIB_PUBLISH2_BTN = [
    "MODEL-CONTRIB-PAGE-RUN-BENCHMARCH-BUTTON",
    "/html/body/div[2]/div[3]/div/div[3]/button[2]",
]
MDL_CONTRIB_BNAME_TXT = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-NAME-TEXT",
    "/html/body/div[2]/div[3]/div[2]/div[3]/table/tbody/tr[{}]/td[2]/div[2]",
]
MDL_CONTRIB_BSELECT_BTN = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-SELECT-BUTTON",
    "/html/body/div[2]/div[3]/div[2]/div[3]/table/tbody/tr[{}]/td[5]/div[2]/button",
]
MDL_CONTRIB_MNAME_TXT = [
    "MODEL-CONTRIB-PAGE-METRIC-NAME-TEXT",
    '//*[@id="benchmarkTable"]/div[3]/div[2]/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[1]',
]
MDL_CONTRIB_MSELECT_BTN = [
    "MODEL-CONTRIB-PAGE-METRIC-SELECT-BUTTON",
    '//*[@id="benchmarkTable"]/div[3]/div[2]/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[2]/button',
]
MDL_CONTRIB_BSUBMIT_BTN = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-SUBMIT-BUTTON",
    '//*[@id="benchmarkTable"]/div[3]/div[2]/table/div/div[2]/button',
]
MDL_CONTRIB_EXP_RECORD_BTN = [
    "MODEL-CONTRIB-PAGE-EXPAND-RECORD-BUTTON",
    '//*[@id="MUIDataTableBodyRow-{}"]/td[1]/div/button',
]
MDL_CONTRIB_METRICNAME_TXT = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICNAME-TEXT",
    '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[1]',
]
MDL_CONTRIB_METRICTYPE_TXT = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICTYPE-TEXT",
    '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[2]',
]
MDL_CONTRIB_METRICSCOR_TXT = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICSCORE-TEXT",
    '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[3]',
]
MDL_CONTRIB_METRICSTTS_TXT = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-METRICSTATUS-TEXT",
    '//*[@id="root"]/div/div/div/div/div/div[3]/table/tbody/tr[2]/td/div/table/tbody/tr[{}]/td[5]',
]
MDL_CONTRIB_REFRESH_BTN = [
    "MODEL-CONTRIB-PAGE-BENCHMARCH-REFRESH-BUTTON",
    '//*[@id="root"]/div/div/div/div/div/div[1]/div[2]/button[2]',
]

# variables for testing purpose
TEST_DASH = [DASH_PROFILE_BTN, DASH_DTYPE_SLCT_BTN, DASH_CHART_TOTCNT_TXT]
TEST_DS_SUBMIT = [DS_SUBMIT_NAME_INP, DS_SUBMIT_URL_INP, DS_SUBMIT_SUBMIT_BTN]
TEST_DS_SD = [
    DS_SD_TYPELIST_BTN,
    DS_SD_SRCLANG_INP,
    DS_SD_TGTLANG_INP,
    DS_SD_DOMAIN_INP,
    DS_SD_SUBMIT_BTN,
]
TEST_DS_MYSRCH = []
TEST_DS_CONTRIB = []
TEST_MDL_SUBMIT = [MDL_SUBMIT_FILE_INP, MDL_SUBMIT_NAME_INP, MDL_SUBMIT_BTN]

TEST_PUBLIC_WEB = [
    MDL_EXPLR_EXPLR_TAB1_BTN,
    MDL_EXPLR_BDS_TAB2_BTN,
    MDL_EXPLR_OCRTAB_BTN,
    MDL_EXPLR_ASRTAB_BTN,
    MDL_EXPLR_TTSTAB_BTN,
    MDL_EXPLR_TRANSTAB_BTN,
    MDL_EXPLR_MDLLI_TXT,
]

# asr-testing data

ASR_EN = {
    "name": "Vakyansh ASR - English",
    "lang": "en",
    "url": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_english.wav",
    "sentence": "so the second step is that somebody has to specify this in the project request and write",
}
ASR_HI = {
    "name": "Vakyansh ASR - Hindi",
    "lang": "hi",
    "url": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_hindi.wav",
    "sentence": "में आग लगाई जा रही है",
}
ASR_TA = {
    "name": "Vakyansh ASR - Tamil",
    "lang": "ta",
    "url": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_tamil.wav",
    "sentence": "மற்ற படங்களைப் பற்றிய செய்திகள் எதுவும் வெளிவரவில்லை",
}
ASR_MR = {
    "name": "Vakyansh ASR - Marathi",
    "lang": "mr",
    "url": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_marathi.wav",
    "sentence": "गाण्याचा आवाज थोडा वाढव ना",
}
ASR_BN = {
    "name": "Vakyansh ASR - Bengali",
    "lang": "bn",
    "url": "https://anuvaad-raw-datasets.s3-us-west-2.amazonaws.com/vakyansh_bengali.wav",
    "sentence": "মান্নান সৈয়দের মৃত্যুর পর বেরিয়েছে",
}

TEST_ASR_LANGS = [ASR_EN, ASR_HI, ASR_TA, ASR_MR, ASR_BN]
