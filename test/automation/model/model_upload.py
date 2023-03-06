import os

# local-imports
from .loader import print_task,print_output,get_url
from .loader import ULCA_MDL_SUBMIT_URL
from .loader import perform_webpage_function,wait_for_2_sec
from .loader import elements_mdl_submit as ele

def perform_model_submit(name, filex, driver):
    status, s_str = True, ""
    print_task("SUBMIT-MODEL")
    filex = os.path.abspath(filex)
    driver = get_url(ULCA_MDL_SUBMIT_URL, driver)
    # input for submit-model-name
    status, s_str = perform_webpage_function(ele.MDL_SUBMIT_NAME_INP, "input", driver, inp_data=name)
    if status:
        # input for submit-model-file
        status, s_str = perform_webpage_function(ele.MDL_SUBMIT_FILE_INP, "input", driver, inp_data=filex)
    if status:
        # click submit-model-button
        status, s_str = perform_webpage_function(ele.MDL_SUBMIT_BTN, "click", driver)
    if status:
        # capture submit-srn_no
        for i in range(3):
            wait_for_2_sec()
        status, s_str = perform_webpage_function(ele.MDL_SUBMIT_SRN_TXT, "text", driver)
        if not status:
            s_str = "ERROR occured after submitting"
    print_output(status, s_str)
    return status, driver
