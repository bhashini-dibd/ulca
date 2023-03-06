from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys

# local-imports
from .loader import ULCA_USERNAME,ULCA_PASSWORD
from .core_main import print_task,print_output
from .core_main import get_url
from .core_main import wait_for_2_sec
from .loader import elements_login as ele
from .url import ULCA_LOGIN_URL


def perform_login(driver):
    status, s_str = True, ""
    username = ULCA_USERNAME
    password = ULCA_PASSWORD
    print_task("login")
    # check if username,password not empty
    if len(username.strip()) == 0 or len(password.strip()) == 0:
        status = False
        s_str = "username/password empty"
    else:
        driver = get_url(ULCA_LOGIN_URL, driver)
        # input-username
        status, s_str = perform_webpage_function(
            ele.LOGIN_USERNAME_INP, "input", driver, inp_data=username
        )
        if status:
            # input-password
            status, s_str = perform_webpage_function(
                ele.LOGIN_PASSWORD_INP, "input", driver, inp_data=password
            )
        if status:
            # submit-login
            status, s_str = perform_webpage_function(ele.LOGIN_BTN, "click", driver)
        if status:
            # check if success or not 
            status, s_str = perform_webpage_function(
                ele.DASH_PROFILE_BTN, "none", driver
            )
            if status:
                status = True
                s_str = ""
            if not status:
                status = False
                s_str = "Invalid Credentials"
    print_output(status, s_str)
    return status, driver
    
def perform_webpage_function(element_data, function, driver, inp_data=None, multi_ele=False):
    #supported funcs:
    #   single_ele:click,is_enabled,input,text,href,outerHTML,dropdown
    #   multi_ele: click[by_index,by_name],text
    status, status_str = True, ""
    try:
        if multi_ele:
            element = driver.find_elements(element_data["by"], element_data["value"])
        else:
            element = driver.find_element(element_data["by"], element_data["value"])
        status = True
        status_str = None
    except Exception as e:
        print("BUTTON NOT FOUND")
        print(e.args)
        status = False
        status_str = "ELEMENT=" + element_data["name"] + " - NOT FOUND"
    if status:
        try:
            if function == "click":
                if multi_ele:
                    try:
                        element[inp_data["index"]].click()
                    except Exception:
                        for i in range(len(element)):
                            if inp_data["name"] == element[i].text.strip().lower():
                                element[i].click()
                                break
                            if i == len(element) - 1:
                                status = False
                                status_str = "name-'" + inp_data["name"] + "' not found"
                else:
                    element.click()
                wait_for_2_sec()
            elif function == "is_enabled":
                status = element.is_enabled()
                status_str = ""
            elif function == "input":
                element.send_keys(inp_data)
                wait_for_2_sec()
            elif function == "text":
                if multi_ele:
                    for i in range(len(element)):
                        element[i] = element[i].text.strip()
                    status_str = element
                else:
                    status_str = element.text.strip()
            elif function == "href":
                status_str = element.get_attribute("href")
            elif function == "outerHTML":
                status_str = element.get_attribute("outerHTML")
            elif function == "dropdown":
                element.click()
                element.send_keys(inp_data)
                wait_for_2_sec()
                element.send_keys(Keys.DOWN)
                element.send_keys(Keys.ENTER)
                element.send_keys(Keys.ESCAPE)
                wait_for_2_sec()
            elif function == "none":
                pass
            else:
                status = False
                status_str = (
                    "NOT A VALID FUNCTION="
                    + function
                    + " FOR ELEMENT="
                    + element_data["name"]
                )
        except Exception as e:
            status = False
            status_str = e.args
    return status, status_str

def show_all_data_from_table(driver):
    # for showing all 100 -data in table !
    select_table_val = driver.find_element_by_id('pagination-rows')
    js_script="var clickEvent = new MouseEvent('mousedown', {view: window,bubbles: true,cancelable: true}); arguments[0].dispatchEvent(clickEvent);"
    driver.execute_script(js_script,select_table_val)
    wait_for_2_sec()
    xpath_100={
        'name':'100-value-option',
        'by':By.XPATH,
        'value':'//*[@data-value="100"]'
        }
    status,s_str=perform_webpage_function(xpath_100, 'click', driver)
    wait_for_2_sec()
    return None

