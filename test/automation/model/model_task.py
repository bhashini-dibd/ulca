from .loader import print_task,print_output,get_url
from .loader import ULCA_MDL_EXPLR_URL
from .loader import perform_webpage_function,load_yaml_data,wait_for_2_sec
from .loader import elements_mdl_explr as ele

def select_translate_type(type1, driver):
    status, s_str = True, ""
    if type1 in load_yaml_data('ModelTasks'):
        if type1 == "translation":
            # click translation-model-tab
            status, s_str = perform_webpage_function(ele.MDL_EXPLR_TRANSTAB_BTN, "click", driver)
        elif type1 == "asr":
            # click asr-model-tab
            status, s_str = perform_webpage_function(ele.MDL_EXPLR_ASRTAB_BTN, "click", driver)
        elif type1 == "tts":
            # click tts-model-tab
            status, s_str = perform_webpage_function(ele.MDL_EXPLR_TTSTAB_BTN, "click", driver)
        else:
            # click ocr-model-tab
            status, s_str = perform_webpage_function(ele.MDL_EXPLR_OCRTAB_BTN, "click", driver)
    else:
        status = False
        s_str = "not a valid type"
    return status, s_str

def get_io_t_ele(type1):
    if type1 == "translation":
        i_area = ele.MDL_EXPLR_IA_TRS_INP
        o_area = ele.MDL_EXPLR_OA_TRS_TXT
        t_btn = ele.MDL_EXPLR_TRANSLATE_BTN
    elif type1 == "asr":
        i_area = ele.MDL_EXPLR_IA_ASROCR_INP
        o_area = ele.MDL_EXPLR_OA_ASR_TXT
        t_btn = ele.MDL_EXPLR_CNVT_ASROCR_BTN
    elif type1 == "tts":
        i_area = ele.MDL_EXPLR_IA_TRS_INP   #not-valid-element
        o_area = ele.MDL_EXPLR_OA_TRS_TXT   #not-valid-element
        t_btn = ele.MDL_EXPLR_TRANSLATE_BTN #not-valid-element
    else:
        #ocr
        i_area = ele.MDL_EXPLR_IA_ASROCR_INP
        o_area = ele.MDL_EXPLR_OA_OCR_TXT
        t_btn = ele.MDL_EXPLR_CNVT_ASROCR_BTN
    return i_area,o_area,t_btn

def perform_translate(name, type1, inp, driver):
    status, s_str = True, ""
    print_task("TRANSLATE")
    driver = get_url(ULCA_MDL_EXPLR_URL, driver)
    status, s_str = select_translate_type(type1, driver)
    if status:
        i_area,o_area,t_btn = get_io_t_ele(type1)
        if name.strip() == "":
            status=False
            s_str="model-name not provided"
    if status:
        data1 = {"name": name.lower()}
        status, s_str = perform_webpage_function(ele.MDL_EXPLR_MDLLI_TXT, "click", driver, multi_ele=True, inp_data=data1)
    wait_for_2_sec()
    if status:
        # click model-trynow-button
        status, s_str = perform_webpage_function(ele.MDL_EXPLR_TRYM_BTN, "click", driver)
    if status:
        # input for translation
        status, s_str = perform_webpage_function(i_area, "input", driver, inp_data=inp)
    if status:
        # click model-translate-button
        status, s_str = perform_webpage_function(t_btn, "click", driver)
    if status:
        # text at output field of translation
        for i in range(5):
            wait_for_2_sec() 
        status, s_str = perform_webpage_function(o_area, "text", driver)
    if status:
        # preparing the string for output
        if type1 == "translation":
            s_str = '\nINPUT="' + inp + '"\nOUTPUT="' + s_str + '"'
        else:
            s_str = '"\nOUTPUT="' + s_str + '"'

    print_output(status, s_str)
    return driver

