from .core_data import DOMAIN_DICT
from .loader import ULCA_DOMAIN

ULCA_DOMAIN = "https://" + DOMAIN_DICT[ULCA_DOMAIN] + "."

# ULCA-URLs
ULCA_LOGIN_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/user/login"
ULCA_DASH_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/dashboard"

# ULCA-DATASET-URLs
ULCA_DS_SUBMIT_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/dataset/upload"
ULCA_DS_CONTRIB_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/dataset/my-contribution"
ULCA_DS_MYSRCH_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/my-searches"
ULCA_DS_SD_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/search-and-download-rec/initiate/-1"

# ULCA-MODEL-URLs
ULCA_MDL_SUBMIT_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/model/upload"
ULCA_MDL_EXPLR_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/model/explore-models"
ULCA_MDL_CONTRIB_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/model/my-contribution"
ULCA_MDL_BMARK_DS_URL = ULCA_DOMAIN + "ulcacontrib.org/ulca/model/benchmark-datasets"
