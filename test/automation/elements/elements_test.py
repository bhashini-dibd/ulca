from . import elements_dash as dash
from . import elements_ds_submit as ds_submit
from . import elements_ds_sd as ds_sd
from . import elements_mdl_submit as mdl_submit
from . import elements_mdl_explr as mdl_explr
from . import elements_mdl_bmark as mdl_bmark

# variables for testing purpose
TEST_DASH = [
    dash.DASH_PROFILE_BTN,
    dash.DASH_DTYPE_SLCT_BTN,
    dash.DASH_CHART_TOTCNT_TXT
    ]
TEST_DS_SUBMIT = [
    ds_submit.DS_SUBMIT_NAME_INP,
    ds_submit.DS_SUBMIT_URL_INP,
    ds_submit.DS_SUBMIT_SUBMIT_BTN
    ]
TEST_DS_SD = [
    ds_sd.DS_SD_TYPELIST_BTN,
    ds_sd.DS_SD_SRCLANG_INP,
    ds_sd.DS_SD_TGTLANG_INP,
    ds_sd.DS_SD_DOMAIN_INP,
    ds_sd.DS_SD_SUBMIT_BTN,
]
TEST_MDL_SUBMIT = [
    mdl_submit.MDL_SUBMIT_FILE_INP,
    mdl_submit.MDL_SUBMIT_NAME_INP,
    mdl_submit.MDL_SUBMIT_BTN
    ]
TEST_EXPLR_WEB = [
    mdl_explr.MDL_EXPLR_OCRTAB_BTN,
    mdl_explr.MDL_EXPLR_ASRTAB_BTN,
    mdl_explr.MDL_EXPLR_TTSTAB_BTN,
    mdl_explr.MDL_EXPLR_TRANSTAB_BTN,
    mdl_explr.MDL_EXPLR_MDLLI_TXT,
]

TEST_BMARK_WEB = [
    mdl_bmark.MDL_BMARK_DS_LI_TXT,
    mdl_bmark.MDL_BMARK_METRIC_TABLE
]
