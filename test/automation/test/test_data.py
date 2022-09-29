from .loader import url
from .loader import elements_test as ele

TEST_DASH = {
    "name": "DASHBOARD-PAGE",
    "url": url.ULCA_DASH_URL,
    "elements": ele.TEST_DASH,
}
TEST_DS_SUBMIT = {
    "name": "DATASET-SUBMIT-PAGE",
    "url": url.ULCA_DS_SUBMIT_URL,
    "elements": ele.TEST_DS_SUBMIT,
}
TEST_DS_SD = {
    "name": "DATASET-S/DOWNLOAD-PAGE",
    "url": url.ULCA_DS_SD_URL,
    "elements": ele.TEST_DS_SD,
}
TEST_MDL_SUBMIT = {
    "name": "MODEL-SUBMIT-PAGE",
    "url": url.ULCA_MDL_SUBMIT_URL,
    "elements": ele.TEST_MDL_SUBMIT,
}

TEST_ELEMENT_LIST = [TEST_DASH, TEST_DS_SUBMIT, TEST_DS_SD,TEST_MDL_SUBMIT]

#public-pages
TEST_MDL_EXPLR = {
    "name": "MODEL-EXPLORE-PAGE",
    "url": url.ULCA_MDL_EXPLR_URL,
    "elements": ele.TEST_EXPLR_WEB,
}

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

TEST_ASR_LANGS = {
    "url":url.ULCA_MDL_EXPLR_URL,
    "list":[ASR_TA] #ASR_EN, ASR_HI, , ASR_MR, ASR_BN
    }

TEST_BMARK={
    "url":url.ULCA_MDL_BMARK_DS_URL,
    "dataset":"FLORES-113 en-te Evaluation Benchmark",
    "elements":ele.TEST_BMARK_WEB
    }

