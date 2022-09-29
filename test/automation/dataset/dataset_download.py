from .loader import print_task,print_output
from .loader import get_file,get_url
from .loader import perform_webpage_function
from .loader import ULCA_DS_MYSRCH_URL
from .loader import elements_ds_mysearchs as ele
from .loader import LANGUAGE_DICT
from .loader import wait_for_2_sec,wait_for_pending_request,PENDING_WAIT_TIME

def perform_download(dataset_type, tgt, srn, driver):
    status, s_str = True, ""
    srn=srn.replace('SRN=','')
    print_task("DOWNLOAD")
    # get-mysearch-url
    driver = get_url(ULCA_DS_MYSRCH_URL, driver)
    # get-name-of-first-search-value
    status, s_str = perform_webpage_function(ele.DS_MYSRCH_NAME_TXT, "text", driver)
    if status:
        # check the name
        search_dataset = s_str.strip().split(" ")[0].lower()[:3]
        tgt_index = -1
        if len(s_str.strip().split("|")) > 2:
            if not (search_dataset == "par" and len(s_str.strip().split("|")) ==3):
                tgt_index=-2
        search_tgt = s_str.strip().split("|")[tgt_index].lower().strip()
        org_dataset = dataset_type.split("-")[0][:3].lower()
        org_tgt = LANGUAGE_DICT[tgt[-1]].lower()
    if status:
        if (search_dataset == org_dataset) and (search_tgt == org_tgt):
            # get-status-of-first-search-value
            status, s_str = perform_webpage_function(ele.DS_MYSRCH_STTS_TXT, "text", driver)
            if status:
                # status=in-progress
                while s_str == "In-Progress":
                    print("PENDING")
                    print()
                    print("waiting for", PENDING_WAIT_TIME, "seconds")
                    wait_for_pending_request()
                    print_task("\nDOWNLOAD")
                    driver.refresh()
                    wait_for_2_sec()
                    status, s_str = perform_webpage_function(ele.DS_MYSRCH_STTS_TXT, "text", driver)
                # status=Failed
                if s_str == "Failed":
                    status = False
                    s_str = "searching failed"
                # status=completed
                else:
                    status, s_str = perform_webpage_function(ele.DS_MYSRCH_CNT_TXT, "text", driver)
                if status:
                    # if count is 0
                    if s_str == "0":
                        status = True
                        s_str = "found a null dataset."
                    # if count is not 0
                    else:
                        count_str = "count=" + str(s_str)
                        status, s_str = perform_webpage_function(ele.DS_MYSRCH_NAME_TXT, "click", driver)
                        wait_for_2_sec()
                        if status:
                            # get-link-of-sample-file
                            status, s_str = perform_webpage_function(ele.DS_MYSRCH_SMPFILE_A, "href", driver)
                        if status:
                            # get-link-of-all-file
                            status, s_str = perform_webpage_function(ele.DS_MYSRCH_ALLFILE_A, "href", driver)
                        if status:
                            # download-sample-file
                            smple = get_file("{}-sample.json".format(srn), s_str)
                            # download-all-file
                            all_file = get_file("{}-all.json".format(srn), s_str.strip())
                            s_str = "{0} - sampleFile={1} - allFile={2}".format(str(count_str),str(smple),str(all_file))
        else:
            status = False
            s_str = "could not find searched name."
    print_output(status, s_str)
    return driver
