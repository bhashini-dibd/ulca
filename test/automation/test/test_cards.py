from .loader import ULCA_MDL_EXPLR_URL,perform_webpage_function,print_task,print_output,ULCA_MDL_BMARK_DS_URL
from .loader import get_url
from .loader import load_yaml_data
from .loader import elements_mdl_explr as ele

task_tab_dict={
    'translation':ele.MDL_EXPLR_TRANSTAB_BTN,
    'tts':ele.MDL_EXPLR_TTSTAB_BTN,
    'asr':ele.MDL_EXPLR_ASRTAB_BTN,
    'ocr':ele.MDL_EXPLR_OCRTAB_BTN
    }
mdl_data_dict={
    'source/lang':'div[1]/div[1]/p',
    'target':'div[1]/div[2]/p',
    'domain':'div[2]/div[1]/p',
    'submitter':'div[2]/div[2]/p',
    'published_on':'div[2]/div[3]/p',
    }
bmark_data_dict={
    'source/lang':'div[1]/div[1]/p',
    'target':'div[1]/div[2]/p',
    'domain':'div[2]/div[1]/p',
    'submitter':'div[2]/div[2]/p',
    }
def test_modelcards(show_str,driver):
    status, s_str = True, ""
    print_task("MODEL-CARDS")
    driver = get_url(ULCA_MDL_EXPLR_URL, driver)
    fail_str="\n"
    fail_count=0
    for task in load_yaml_data('ModelTasks'):
        status, s_str = perform_webpage_function(task_tab_dict[task], "click", driver)
        cards=driver.find_elements_by_xpath('//h6/..')
        for card in cards:
            c_name=card.find_element_by_xpath('h6').text.strip()
            if c_name == "":c_name='empty_name'
            fail_list=list()
            for data in mdl_data_dict.keys():
                if data == 'target' and task != 'translation':
                    continue
                value=card.find_element_by_xpath(mdl_data_dict[data]).text.strip()
                if value == "": 
                    fail_list.append(data)
                    fail_count+=1
            if len(fail_list)>0:
                c_str="{0}:ModelName='{1}'- ".format(task.upper(),c_name)
                c_str+=str(fail_list)+" data not avaialble !\n"
                fail_str+=c_str
    if fail_str.rstrip() == "":
        status,s_str=True,"all data available"
    else:
        status=False
        s_str='fail-count:'+str(fail_count)
        if show_str:
            s_str+='\n'+fail_str.rstrip()
    print_output(status,s_str)
    return driver
    
    
def test_bmarkcards(show_str,driver):
    status, s_str = True, ""
    print_task("BENCHMARK-CARDS")
    driver = get_url(ULCA_MDL_BMARK_DS_URL, driver)
    fail_str="\n"
    fail_count=0
    for task in load_yaml_data('ModelTasks'):
        status, s_str = perform_webpage_function(task_tab_dict[task], "click", driver)
        cards=driver.find_elements_by_xpath('//h6/..')
        for card in cards:
            c_name=card.find_element_by_xpath('h6').text.strip()
            if c_name == "":c_name='empty_name'
            fail_list=list()
            for data in bmark_data_dict.keys():
                if data == 'target' and task != 'translation':
                    continue
                value=card.find_element_by_xpath(bmark_data_dict[data]).text.strip()
                if value == "":
                    fail_list.append(data)
                    fail_count+=1
            if len(fail_list)>0:
                c_str="{0}:BenchmarkName='{1}'- ".format(task.upper(),c_name)
                c_str+=str(fail_list)+" data not avaialble !\n"
                fail_str+=c_str
    if fail_str.rstrip() == "":
        status,s_str=True,"all data available"
    else:
        status=False
        s_str='fail-count:'+str(fail_count)
        if show_str:
            s_str+='\n'+fail_str.rstrip()
    print_output(status,s_str)
    return driver

def test_cards(driver,show_str=True):
    driver=test_modelcards(show_str,driver)
    driver=test_bmarkcards(show_str,driver)
    return driver
