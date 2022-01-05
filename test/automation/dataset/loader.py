from config import PENDING_WAIT_TIME

from core.core_main import print_task,print_output
from core.core_main import get_url,get_file
from core.core_main import wait_for_2_sec,wait_for_pending_request

from core.core_web import perform_webpage_function
from core.core_web import show_all_data_from_table

from core.url import ULCA_DS_MYSRCH_URL
from core.url import ULCA_DS_SD_URL
from core.url import ULCA_DS_SUBMIT_URL
from core.url import ULCA_DS_CONTRIB_URL

from elements import elements_ds_sd
from elements import elements_ds_submit
from elements import elements_ds_mysearchs
from elements import elements_ds_contrib

from core.core_yaml import load_yaml_data

from core.core_data import LANGUAGE_DICT
