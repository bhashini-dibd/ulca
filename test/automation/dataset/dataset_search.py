from .loader import print_task,print_output,get_url
from .loader import perform_webpage_function
from .loader import load_yaml_data,wait_for_2_sec
from .loader import LANGUAGE_DICT
from .loader import ULCA_DS_SD_URL
from .loader import elements_ds_sd as ele

def perform_search(dataset_type, tgts, src, domain, driver):
    status, s_str = True, ""
    print_task("SEARCH")
    # check-if-datatype-is-valid
    if dataset_type not in load_yaml_data("DataTypes"):
        status = False
        s_str = "Not valid Datatype"
    if status:
        if dataset_type == "parallel-corpus":
            # check-if-source-lang-is-valid
            if src not in load_yaml_data("Languages"):
                status = False
                s_str = "Not valid Source Language."
    if status:
        # check-if-target-lang-is-valid
        if (not set(tgts).issubset(set(load_yaml_data("Languages")))) or len(tgts) == 0:
            status = False
            s_str = "Not valid Target Language/s."
    if status:
        if domain != "":
            # check-if-domain-lang-is-valid
            if domain not in load_yaml_data("Domains"):
                status = False
                s_str = "Not valid Domain"
    if status:
        # get-mysearch-url
        driver = get_url(ULCA_DS_SD_URL, driver)
        # click-typelist-menu
        status, s_str = perform_webpage_function(ele.DS_SD_TYPELIST_BTN, "click", driver)
    if status:
        # click-required-dataset-type
        ele.DS_SD_DTYPE_CHOSE_BTN["name"] += dataset_type.upper()
        ele.DS_SD_DTYPE_CHOSE_BTN["value"] = ele.DS_SD_DTYPE_CHOSE_BTN["value"].format(dataset_type)
        status, s_str = perform_webpage_function(ele.DS_SD_DTYPE_CHOSE_BTN, "click", driver)
    if status:
        if dataset_type == "parallel-corpus":
            # source-lang-input
            status, s_str = perform_webpage_function(ele.DS_SD_SRCLANG_INP,"dropdown",driver,inp_data=LANGUAGE_DICT[src],)
    if status:
        # target-lang-input
        for tgt in tgts:
            status, s_str = perform_webpage_function(ele.DS_SD_TGTLANG_INP,"dropdown",driver,inp_data=LANGUAGE_DICT[tgt],)
            if status is False:
                break
    if status:
        # domain-input
        if domain != "":
            status, s_str = perform_webpage_function(ele.DS_SD_DOMAIN_INP,"dropdown",driver,inp_data=domain.replace("-", ""),)
    if status:
        # submit-search
        status, s_str = perform_webpage_function(ele.DS_SD_SUBMIT_BTN, "click", driver)
    if status:
        # capture-searched-srn
        status, s_str = perform_webpage_function(ele.DS_SD_SRN_TXT, "text", driver)
        wait_for_2_sec()
    if status:
        s_str = "SRN=" + str(s_str.split(" ")[-1])
    print_output(status, s_str)
    return status, s_str, driver

