import pandas
pandas.options.mode.chained_assignment = None  # chained_assignemnt_warning_disabled

from .loader import print_task,print_output
from .loader import get_url,perform_webpage_function
from .loader import ULCA_MDL_CONTRIB_URL
from .loader import wait_for_2_sec
from .loader import elements_mdl_contrib as ele


def repair_df(df):
    # remove repeated string from table-data
    for i in df.columns:
        for j in range(len(df)):
            df[i][j]=str(df[i][j]).replace(str(i),'')
    return df
    
def get_table_row_status(name,t_ele,name_column,val_column,driver):
    status,s_str=True,""
    # get the table data
    status, s_str = perform_webpage_function(t_ele, "outerHTML", driver)
    index=-1
    if status:
        df=pandas.read_html(s_str)[0]
        repair_df(df)
        # check if dataser_name available on table
        for i in range(len(df)):
            if name.lower() == df[name_column][i].lower():
                s_str=df[val_column][i].lower()
                index=i
                break
        if index <0:
            status = False
            s_str = "'{0}'-name not found.".format(name)
    return status,s_str,index

def run_benchmark(mdl_name, b_name, metric, driver):
    status, s_str = True, ""
    print_task("BENCHMARK: ")
    driver = get_url(ULCA_MDL_CONTRIB_URL, driver)
    # get-index-of-model
    status,s_str,mdl_index=get_table_row_status(mdl_name,ele.MDL_CONTRIB_MAIN_TABLE,'Model Name','Status',driver)
    if status:
        # select-model
        run_benchmark = ele.MDL_CONTRIB_RUNBENCH_BTN
        run_benchmark['value'] = run_benchmark['value'].format(int(mdl_index))
        status, s_str = perform_webpage_function(run_benchmark, "click", driver)
    if status:
        # get-index-of-benchmark
        status,s_str,b_index=get_table_row_status(b_name,ele.MDL_CONTRIB_BMARK_TABLE,'Benchmark Dataset','Domain',driver)
    if status:
        # select-benchmark
        select_bmark = ele.MDL_CONTRIB_BSELECT_BTN
        select_bmark['value'] = select_bmark['value'].format(int(b_index)+1)
        status, s_str = perform_webpage_function(select_bmark, "click", driver)
    if status:
        # get-index-of-metric
        status,s_str,metric_ind=get_table_row_status(metric,ele.MDL_CONTRIB_METRIC_TABLE,'Metric','Action',driver)
    if status:
        # select-metric
        select_metric = ele.MDL_CONTRIB_METRICSELECT_BTN
        select_metric['value'] = select_metric['value'].format(int(metric_ind)+1)
        status, s_str = perform_webpage_function(select_metric, "click", driver)
    if status:
        # submit-benchmarking
        status, s_str = perform_webpage_function(ele.MDL_CONTRIB_BSUBMIT_BTN, "click", driver)
    for i in range(5):
        wait_for_2_sec()
    print_output(status, s_str)
    return driver

