import time
import sys
import requests
from termcolor import colored

# local-imports
from .loader import PENDING_WAIT_TIME
from .loader import DOWNLOAD_FOLDER


def exit_program():
    sys.exit(0)
    return None

def wait_for_2_sec():
    time.sleep(2)
    return None
    
def wait_for_pending_request():
    time.sleep(PENDING_WAIT_TIME)
    return None

def print_task(string_a):
    print("{0} : ".format(string_a.upper()), flush=True, end="")
    return None


def print_status(status):
    status_dict = {True: "SUCCESS", False: "FAILED"}
    color_dict = {True: "green", False: "red"}
    try:
        print(colored(status_dict[status], color_dict[status]), end="")
    except Exception:
        print(status_dict[status], end="")
    return None


def print_output(status, data):
    print_status(status)
    if data != "":
        dash = " -"
    else:
        dash = ""
    print(dash, data)
    return None

def get_url(url, driver):
    if driver.current_url != url:
        driver.get(url)
    else:
        driver.refresh()
    wait_for_2_sec()
    return driver
    
def get_file(name, URL):
    try:
        r = requests.get(URL)
        with open("{0}/{1}".format(DOWNLOAD_FOLDER,name), "wb") as f:
            f.write(r.content)
        status_str = name
    except Exception:
        status_str = "N/A"
    status_str = str(status_str)
    return status_str

def print_no_flag_found():
    print_task("FLAG")
    print_output(False, "no flag found")

