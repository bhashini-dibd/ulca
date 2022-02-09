from .loader import print_task,print_output,get_url
from .loader import perform_webpage_function
from .loader import ULCA_DS_SUBMIT_URL
from .loader import elements_ds_submit as ele

def perform_submit(dataset_name, dataset_url, driver):
    status, s_str = True, ""
    print_task("SUBMIT")
    # check-if-url-empty
    if dataset_url == "":
        status = False
        s_str = "DATASET-URL EMPTY !"
    if status:
        # get-submit-dataset-url
        driver = get_url(ULCA_DS_SUBMIT_URL, driver)
        # dataset-name-input
        status, s_str = perform_webpage_function(ele.DS_SUBMIT_NAME_INP, "input", driver, inp_data=dataset_name)
    if status:
        # dataset-url-input
        status, s_str = perform_webpage_function(ele.DS_SUBMIT_URL_INP, "input", driver, inp_data=dataset_url)
    if status:
        # submit-dataset
        status, s_str = perform_webpage_function(ele.DS_SUBMIT_SUBMIT_BTN, "click", driver)
    if status:
        # capture-submitted-srn
        status, s_str = perform_webpage_function(ele.DS_SUBMIT_SRN_TXT, "text", driver)
        if status:
            s_str = "SRN=" + str(s_str.strip().split(" ")[-1])
        else:
            s_str = ("not submitted - check url/name/[service-temporary-unavailable] ")
    print_output(status, s_str)
    return status, s_str, driver

