from .loader import print_task,print_output
from .loader import get_url,perform_webpage_function
from .loader import ULCA_MDL_EXPLR_URL
from .loader import load_yaml_data
from .loader import wait_for_2_sec

from .loader import elements_mdl_explr as ele
from .model_task import select_translate_type

def list_public_model_data(show_models,driver):
    status, s_str = True, ""
    print_task("LIST")
    driver = get_url(ULCA_MDL_EXPLR_URL, driver)
    main_str="\n"
    m_dict=dict()
    for type1 in load_yaml_data('ModelTasks'):
        status, s_str = select_translate_type(type1, driver)
        if status:
            status, s_str = perform_webpage_function(ele.MDL_EXPLR_MDLLI_TXT, "text", driver, multi_ele=True)
        if status:
            wait_for_2_sec()
            m_dict[type1.upper()]=str(len(s_str))
            if show_models and len(s_str)>0:
                main_str+=type1.upper()+":"
                for i in range(len(s_str)):
                    main_str+='\n'+str(i+1)+". "+str(s_str[i])
                main_str+="\n\n"
        else:
            break
    if status:
        s_str='model-counts: '+str(m_dict)
        if show_models: s_str+='\n'+main_str.rstrip()
    print_output(status,s_str)
    return driver

