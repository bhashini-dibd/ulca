from .dataset_submit import perform_submit
from .dataset_submit_status import get_upload_status
from core.core_main import wait_for_2_sec
import pandas
import core


def perform_submit_get_status(dataset_name, dataset_url, driver,d_skip=True):
    # submit-function
    status,srn,driver=perform_submit(dataset_name, dataset_url, driver)
    wait_for_2_sec()
    if status and d_skip:
        # get-status-function
        driver=get_upload_status(srn, dataset_name, driver)
    return driver

def perform_upload_with_csv(csvfile, driver, d_skip=False):
    # read-csv
    try:
        df = pandas.read_csv(csvfile)
    except Exception as e:
        core.print_task("CSV")
        core.print_output(False, e.args)
        return driver
    # check if columns are available 
    if "Dataset Name" not in df.columns or "Dataset URL" not in df.column:
        core.print_task("CSV")
        core.print_output(False, "columns[Dataset Name,Dataset URL] not found")
        return driver
    for i in range(len(df)):
        print()
        print("-" * 45)
        name, url = df["Dataset Name"][i], df["Dataset URL"][i]
        print("DATASET_NAME =", name)
        driver = perform_submit_get_status(name, url, driver, d_skip=d_skip)
        if i == len(df)-1:
            print("-" * 45)
            print()
    return driver
