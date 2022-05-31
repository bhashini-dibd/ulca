import pandas

from .loader import print_task,get_url,print_output,perform_webpage_function
from .test_data import TEST_BMARK as ele


def check_sorted(df):
    status=df['Score'].is_monotonic_decreasing
    if status:
        s_str="SORTED"
    else:
        s_str="NOT SORTED"
    return status,s_str

def test_leaderboard(driver):
    status, s_str = True, ""
    print_task("BENCHMARK-LEADERBOARDS")
    driver = get_url(ele['url'], driver)
    data1 = {"name": ele["dataset"].lower()}
    status, s_str = perform_webpage_function(
        ele['elements'][0], "click", driver, inp_data=data1, multi_ele=True
    )
    if status:
        status, s_str = perform_webpage_function(
            ele['elements'][1], "outerHTML", driver)
    if status:
        df=pandas.read_html(s_str)[0]
        if df['#Position'][0] == 'No records available':
            status = False
            s_str = "No Record in the table."
        else:
            status,s_str = check_sorted(df) 
    print_output(status, s_str)
    return driver
