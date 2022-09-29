import pandas
pandas.options.mode.chained_assignment = None  # chained_warning_disbaled

# local-imports
from .loader import print_task,get_url,ULCA_MDL_CONTRIB_URL,perform_webpage_function
from .loader import show_all_data_from_table,print_output
from .loader import elements_mdl_contrib as ele


def repair_df(df):
    # remove repeated string from table-data
    for i in df.columns:
        for j in range(len(df)):
            df[i][j]=str(df[i][j]).replace(str(i),'')
    return df
    
def get_table_row_status(name,t_ele,driver):
    status,s_str=True,""
    # get the table data
    status, s_str = perform_webpage_function(t_ele, "outerHTML", driver)
    index=-1
    if status:
        df=pandas.read_html(s_str)[0]
        repair_df(df)
        # check if dataser_name available on table
        for i in range(len(df)):
            if name.lower() == df['Model Name'][i].lower():
                s_str=df['Status'][i].lower()
                index=i
                break
        if index <0:
            status = False
            s_str = "name not found."
        del df
    return status,s_str,index
    
def run_publish_task(name, publish_flag, unpublish_flag, driver):
    status, s_str = True, ""
    print_task("MODEL-STATUS")
    driver = get_url(ULCA_MDL_CONTRIB_URL, driver)
    show_all_data_from_table(driver)
    # get-index-of-the-model-name
    status,s_str,m_index=get_table_row_status(name,ele.MDL_CONTRIB_MAIN_TABLE,driver)
    if status:
        current_status=s_str
        # check-if-publish/un-is_diabled
        publish_btn = ele.MDL_CONTRIB_PUBLISH_BTN
        publish_btn['value'] = publish_btn['value'].format(m_index)
        status, s_str = perform_webpage_function(publish_btn, "is_enabled", driver)
        if not status:
            s_str = "publish button is disabled."
    if status and publish_flag:
        if current_status == "unpublished":
            status, s_str = perform_webpage_function(publish_btn, "click", driver)
            if status:
                status, s_str = perform_webpage_function(ele.MDL_CONTRIB_PUBLISH2_BTN, "click", driver)
            if status:
                s_str = "model published."
        else:
            s_str = "already published."
    if status and unpublish_flag:
        if current_status == "published":
            status, s_str = perform_webpage_function(publish_btn, "click", driver)
            if status:
                status, s_str = perform_webpage_function(ele.MDL_CONTRIB_PUBLISH2_BTN, "click", driver)
            if status:
                s_str = "model unpublished."
        else:
            s_str = "already unpublished."
    print_output(status, s_str)
    return driver
