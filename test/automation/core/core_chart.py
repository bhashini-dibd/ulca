from .loader import elements_dash as ele
from .url import ULCA_DASH_URL
from .core_main import print_task,print_output
from .core_main import get_url
from .core_web import perform_webpage_function
from .core_yaml import load_yaml_data
from .core_data import LANGUAGE_DICT

def select_chart_datatype(dataset_type, src, driver):
    status, s_str = True, ""
    if dataset_type in load_yaml_data("DataTypes"):
        # choose-required-datatype from menu
        ele.DASH_DTYPE_CHOSE_BTN["name"] += str(dataset_type.upper())
        ele.DASH_DTYPE_CHOSE_BTN["value"] = ele.DASH_DTYPE_CHOSE_BTN["value"].format(dataset_type)
        status, s_str = perform_webpage_function(ele.DASH_DTYPE_CHOSE_BTN, "click", driver)
        if status:
            # choose src if parallel-corpus
            if dataset_type == "parallel-corpus":
                if src == "":
                    src = "en"
                if src not in load_yaml_data("Languages"):
                    status = False
                    s_str = "Not a valid source language."
                else:
                    # select the src lang 
                    status, s_str = perform_webpage_function(ele.DASH_CHART_SRCLANG_INP,"dropdown",driver,inp_data=LANGUAGE_DICT[src])
                    if status:
                        # get src-lang-counts
                        status, s_str = perform_webpage_function(ele.DASH_CHART_SRCCNT_TXT, "text", driver)
                    if status:
                        s_str = " - " + src.capitalize() + "-count=" + s_str
    else:
        status = False
        s_str = "Not a valid DataType."
    return status, s_str, driver


def get_chart_data_values(src_count, driver):
    status, s_str = True, ""
    l_names = []
    l_counts = []
    if status:
        # load lang-names in chart
        status, s_str = perform_webpage_function(ele.DASH_CHART_LNAMES_LI, "text", driver, multi_ele=True)
        l_names = s_str
    if status:
        # load lang-counts in chart
        status, s_str = perform_webpage_function(ele.DASH_CHART_LCOUNTS_LI, "text", driver, multi_ele=True)
    if status:
        l_counts = s_str
        # formatting lang-counts
        del l_counts[0]
        del l_counts[0]
        for i in range(len(l_counts)):
            l_counts[i] = str(l_counts[i].replace(",", ""))
        # get total count
        status, s_str = perform_webpage_function(ele.DASH_CHART_TOTCNT_TXT, "text", driver)
    if status:
        # for asr data which is in hours
        if s_str.find(".") > -1:
            s_str += " hours"
        s_str = "Total-count=" + s_str + src_count
    return status, s_str, l_names, l_counts, driver


def select_group_of_chart(groupby, driver):
    status, s_str = True, ""
    if groupby != "":
        if groupby not in GROUP_LIST:
            status = False
            s_str = "not a valid group by selector."
        if status:
            if groupby == "submitter":
                status, s_str = perform_webpage_function(
                    ele.DASH_CHART_GROUPSUBM_BTN, "click", driver
                )
            elif groupby == "collection":
                status, s_str = perform_webpage_function(
                    ele.DASH_CHART_GROUPCOLLM_BTN, "click", driver
                )
            else:
                pass
    return status, s_str, driver


def select_tgt_of_chart(tgt, groupby, driver):
    status, s_str = True, ""
    # load-chart-data fro checking tgt available
    status, s_str, names, counts, driver = get_chart_data_values("1", driver)
    if status:
        tgt = LANGUAGE_DICT[tgt].capitalize()
        try:
            lang_index = names.index(tgt)
        except Exception:
            status = False
            s_str = "target Language not available in chart."
        if status:
            data1 = {"index": lang_index}
            status, s_str = perform_webpage_function(
                ele.DASH_CHART_LRECTS_LI,
                "click",
                driver,
                multi_ele=True,
                inp_data=data1,
            )
    if status:
        if groupby != "":
            if groupby in GROUP_LIST:
                status, s_str, driver = select_group_of_chart(groupby, driver)
            else:
                status = False
                s_str = "not a valid grouping for chart"
    return status, s_str, driver


def get_chart_data(dataset_type, groupby, src, tgt, driver):
    status, s_str = True, ""
    driver = get_url(ULCA_DASH_URL, driver)
    print_task("chart-data")
    #click-datatype-select-button
    status, s_str = perform_webpage_function(ele.DASH_DTYPE_SLCT_BTN, "click", driver)
    if status:
        #choosing-chart-datatype
        status, s_str, driver = select_chart_datatype(dataset_type, src, driver)
        src_count = s_str
        if src_count is None:
            src_count = ""
    if status:
        #check-tgt-lang-length
        if len(tgt) != 0:
            tgt = tgt[0]
            #check-tgt-lang-in-supported-langs
            if tgt not in load_yaml_data("Languages"):
                status = False
                s_str = "not a supported target language"
            else:
                status, s_str, driver = select_tgt_of_chart(tgt, groupby, driver)
    if status:
        #get-chart-values
        status, s_str, names, counts, driver = get_chart_data_values(src_count, driver)
    print_output(status, s_str)
    #print-chart-data
    if status is True:
        print()
        for i in range(min(len(names), len(counts))):
            print(names[i], "=", counts[i])
        print()
    return driver

