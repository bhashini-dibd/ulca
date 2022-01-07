import io
import time
import requests
import simpleaudio as sa
from sacrebleu.metrics import BLEU

from .loader import print_task,get_url,print_output,perform_webpage_function
from .test_data import TEST_ASR_LANGS
from .loader import elements_mdl_explr as ele

def download_audio_data(url):
    data = requests.get(url)
    audio_obj = io.BytesIO(data.content)
    return audio_obj


def play_audio_data(audio_obj):
    wave_obj = sa.WaveObject.from_wave_file(audio_obj)
    play_obj = wave_obj.play()
    play_obj.wait_done()
    return None
    
    
def test_asr_record(driver):
    status, s_str = True, ""
    fail_dict = dict()
    print_task("ASR-RECORDING-STATUS")
    for lang in TEST_ASR_LANGS['list']:
        driver = get_url(TEST_ASR_LANGS['url'], driver)
        status, s_str = perform_webpage_function(
            ele.MDL_EXPLR_ASRTAB_BTN, "click", driver
        )
        if status:
            data1 = {"name": lang["name"].lower()}
            status, s_str = perform_webpage_function(
                ele.MDL_EXPLR_MDLLI_TXT, "click", driver, inp_data=data1, multi_ele=True
            )
        if status:
            # click model-trynow-button
            status, s_str = perform_webpage_function(
                ele.MDL_EXPLR_TRYM_BTN, "click", driver
            )
        if status:
            audio = download_audio_data(lang["url"])
            status, s_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_RECORD_BTN, "click", driver
            )
            time.sleep(5)
        if status:
            play_audio_data(audio)
            time.sleep(2)
            status, s_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_RECORD_BTN, "click", driver
            )
        if status:
            # text at output field of translation
            time.sleep(2 * 5)
            status, s_str = perform_webpage_function(
                ele.MDL_EXPLR_ASR_OPUT_TXT, "text", driver
            )
        fail_count = 0
        if not status:
            break
        else:
            # if status_str != data["sentence"]:
            bleu = BLEU()
            value = bleu.corpus_score([s_str], [[lang["sentence"]]]).score
            fail_count += 1
            fail_dict[lang["lang"]] = {
                "expected": lang["sentence"],
                "inferred": s_str,
                "bleuScore": value,
            }
    if status:
        if len(fail_dict) == 0:
            status = True
            s_str = "{0}/{0}".format(len(TEST_ASR_LANGS['list']))
        else:
            status = False
            s_str = "{0}/{1} - {2}".format(
                len(fail_dict), len(TEST_ASR_LANGS['list']), str(fail_dict)
            )
    print_output(status, s_str)
    return driver

