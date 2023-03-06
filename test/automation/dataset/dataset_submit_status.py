import pandas 

# local-imports
from .loader import ULCA_DS_CONTRIB_URL
from .loader import print_task,print_output,get_url,show_all_data_from_table,get_file
from .loader import perform_webpage_function,wait_for_pending_request,PENDING_WAIT_TIME,wait_for_2_sec
from .loader import elements_ds_contrib as ele


def generate_log_file(srn, driver):
    filename = "logFile="
    # get href of error-file
    status, status_str = perform_webpage_function(ele.DS_CONTRIB_LOG_A, "href", driver)
    if status:
        # download file
        logfile = get_file("{}-log.csv".format(srn), status_str)
        filename += logfile
    else:
        # if not downloaded then N/A
        filename += "N/A"
    return filename, driver
    
    
def print_contrib_data(status, srn,df, driver):
    s_str=""
    if status:
        # wait if the publish is not completed ! 
        while df['Status'][3].lower() != "completed":
            print("PENDING",df['Status'][3].lower())
            print()
            print(df.to_string(index=False))
            print()
            print("waiting for",PENDING_WAIT_TIME, "seconds.")
            wait_for_pending_request()
            print_task("CONTRIB-STATUS")
            # reload the data
            status, s_str = perform_webpage_function(ele.DS_CONTRB_MAIN_TABLE, "outerHTML", driver)
            if status:
                df=pandas.read_html(s_str)[0]
            else:
                break
        s_str="\n"
    else:
        # if upload has failed status 
        s_str, driver = generate_log_file(srn, driver)
    s_str=s_str+df.to_string(index=False)
    return s_str


def get_table_row_status(name,t_ele,driver):
    status,s_str=True,""
    # get the table data
    status, s_str = perform_webpage_function(t_ele, "outerHTML", driver)
    if status:
        df=pandas.read_html(s_str)[0]
        index=-1
        # check if dataser_name available on table
        for i in range(len(df)):
            if name.lower() == df['Dataset Name'][i].lower()[12:]:
                index=i
                break
        if index <0:
            status = False
            s_str = "name not found."
    if status:
        s_str=str(df['Status'][int(index)]).replace('Status','').lower()
    return status,s_str,index
    
    
def repair_df(df):
    # remove repeated string from table-data
    for i in df.columns:
        for j in range(len(df)):
            df[i][j]=str(df[i][j]).replace(str(i),'')
    return df


def get_upload_status(srn, dataset_name, driver):
    status, s_str = True, ""
    srn=srn.replace('SRN=','')
    driver = get_url(ULCA_DS_CONTRIB_URL, driver)
    print_task("CONTRIB-STATUS")
    # load-all-data
    show_all_data_from_table(driver)
    status,s_str,df_index=get_table_row_status(dataset_name,ele.DS_CONTRB_MAIN_TABLE,driver)
    if status:
        # if the dataset is still in progress
        while s_str == "in-progress":
            print("PENDING\n")
            print("waiting for", PENDING_WAIT_TIME, "seconds\n")
            wait_for_pending_request()
            print_task("CONTRIB-STATUS")
            driver.refresh()
            wait_for_2_sec()
            status,s_str,df_index=get_table_row_status(dataset_name,ele.DS_CONTRB_MAIN_TABLE,driver)
        final_status = s_str
    if status:
        # clicking the dataset-name
        DS_CONTRB_NAME_BTN=ele.DS_CONTRB_NAME_BTN
        DS_CONTRB_NAME_BTN['value']=DS_CONTRB_NAME_BTN['value'].format(df_index)
        status, s_str = perform_webpage_function(DS_CONTRB_NAME_BTN, "click", driver)
    if status: 
        status, s_str = perform_webpage_function(ele.DS_CONTRB_MAIN_TABLE, "outerHTML", driver)
    if status:
        df=pandas.read_html(s_str)[0]
        df=repair_df(df)
        if final_status.lower() == "failed":
            status = False
        else:
            status = True
        s_str = print_contrib_data(status, srn,df, driver)
    print_output(status, s_str)
    return driver
