from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

# local-imports
from .loader import print_task,get_url,print_output
from .test_data import TEST_MDL_EXPLR as ele_list

def test_public_website(driver):
    status, s_str = True, ""
    print_task("PUBLIC-URLS-ELEMENTS")
    driver = get_url(ele_list['url'], driver)
    fail_list = []
    wait = WebDriverWait(driver, 2)
    for t_ele in ele_list['elements']:
        try:
            wait.until(EC.presence_of_element_located((t_ele["by"], t_ele["value"])))
        except Exception:
            fail_list.append(t_ele["name"])
    if len(fail_list) == 0:
        status = True
        status_str = "{0}/{0}".format(len(ele_list['elements']))
    else:
        status = False
        status_str = "{0}/{1} - {2}".format(len(fail_list), len(ele_list['elements']), str(fail_list))
    print_output(status, status_str)
    return driver
