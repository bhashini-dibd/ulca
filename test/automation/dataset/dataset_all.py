from .loader import wait_for_2_sec
from .dataset_csv import perform_submit_get_status
from .dataset_csv import perform_upload_with_csv
from .dataset_search import perform_search
from .dataset_download import perform_download


def perform_search_and_download(typex, tgt, src, domain, driver):
    # search-function
    status, srn, driver=perform_search(typex, tgt, src, domain, driver)
    wait_for_2_sec()
    if status:
        # download-function
        driver=perform_download(typex, tgt, srn, driver)
    return driver
    

