from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

from .loader import print_task,get_url,print_output,wait_for_2_sec
from .test_data import TEST_ELEMENT_LIST

def test_elements_with_browser(driver):
    wait = WebDriverWait(driver, 2)
    for test in TEST_ELEMENT_LIST:
        status, s_str = True, ""
        print_task(test["name"]+'-elements')
        driver = get_url(test["url"], driver)
        fail_list = []
        wait_for_2_sec()
        for t in test["elements"]:
            try:
                wait.until(EC.presence_of_element_located((t["by"], t["value"])))
            except Exception as e:
                print(e.args)
                fail_list.append(t["name"])
        if len(fail_list) == 0:
            status = True
            s_str = "{0}/{0}".format(len(test["elements"]))
        else:
            status = False
            s_str = "{0}/{1} - {2}".format(len(fail_list), len(test["elements"]), str(fail_list))
        print_output(status, s_str)
    return driver
