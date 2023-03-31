import os
from copy import deepcopy
import json
BASE_JSON = json.load(open("base.json", encoding="utf-8"))
OUTPUT_FOLDER = '../sync'
os.makedirs(OUTPUT_FOLDER, exist_ok=True)

NATIVE_LANGUAGES = [
    ('as' , ['Beng'], 'Assamese', ['আপোনাৰ কেনে?']),
    ('bn' , ['Beng'], 'Bengali', ['আপনি কেমন আছেন?']),
    ('brx', ['Deva'], 'Bodo', ['नों माबोरै दं?']),
    ('doi', ['Deva'], 'Dogri', ['थुआढ़ा केह् हाल ऐ?']),
    ('gom', ['Deva'], 'Konkani', ['तूं कसो आसा?']),
    ('gu' , ['Gujr'], 'Gujarati', ['તમે કેમ છો?']),
    ('hi' , ['Deva'], 'Hindi', ['आप कैसे हैं?']),
    ('kn' , ['Knda'], 'Kannada', ['ನೀವು ಹೇಗಿದ್ದೀರಿ?']),
    ('ks' , ['Aran', 'Deva'], 'Kashmiri', ['کیتھ کٕن چھ ؟', 'तिमी कसरी छौ?']),
    ('mai', ['Deva'], 'Maithili', ['अहांक कोना छी?']),
    ('ml' , ['Mlym'], 'Malayalam', ['സുഖമാണോ?']),
    ('mni', ['Mtei', 'Beng'], 'Manipuri', ['ꯑꯗꯣꯝ ꯀꯝꯗꯧꯔꯤ?', 'অদোম কম্দৌরি?']),
    ('mr' , ['Deva'], 'Marathi', ['तू कसा आहेस?']),
    ('ne' , ['Deva'], 'Nepali', ['तिमीलाई कस्तो छ?']),
    ('or' , ['Orya'], 'Odia', ['କେମିତି ଅଛନ୍ତି, କେମିତି ଅଛ?']),
    ('pa' , ['Guru'], 'Punjabi', ['ਤੁਸੀ ਕਿਵੇਂ ਹੋ?']),
    ('sa' , ['Deva'], 'Sanskrit', ['भवान्‌ कथमसि?']),
    ('sat', ['Olck'], 'Santali', ['ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱠᱟᱱᱟ?']),
    ('sd' , ['Arab', 'Deva'], 'Sindhi', ['تون ڪيئن آهين؟', 'तवहां कएं आहिनि?']),
    ('ta' , ['Taml'], 'Tamil', ['எப்படி இருக்கிறீர்கள்?']),
    ('te' , ['Telu'], 'Telugu', ['మీరు ఎలా ఉన్నారు?']),
    ('ur' , ['Aran'], 'Urdu', ['آپ کیسے ہو؟']),
]

ENGLISH = ('en', ['Latn'], 'English', ['How are you?'])

SCRIPT_CODE_TO_SHORT_NAME = {
    "Arab": "Arabic",
    "Aran": "Arabic",
    "Beng": "Bengali",
    "Deva": "Devanagari",
    "Mtei": "Meitei"
}

def gen_json(
        source_lang_obj, target_lang_obj,
        base_json, output_folder,
):
    source_lang_code, source_lang_scripts, source_lang_name, source_sample_sentences = source_lang_obj
    target_lang_code, target_lang_scripts, target_lang_name, target_sample_sentences = target_lang_obj

    is_source_digraphic, is_target_digraphic = len(source_lang_scripts) > 1, len(target_lang_scripts) > 1

    for source_lang_script, source_sample_sentence in zip(source_lang_scripts, source_sample_sentences):
        _source_lang_name = source_lang_name+'_'+SCRIPT_CODE_TO_SHORT_NAME[source_lang_script] if is_source_digraphic else source_lang_name

        for target_lang_script, target_sample_sentence in zip(target_lang_scripts, target_sample_sentences):
            if source_lang_code == target_lang_code and source_lang_script == target_lang_script:
                continue
            
            _target_lang_name = target_lang_name+'_'+SCRIPT_CODE_TO_SHORT_NAME[target_lang_script] if is_target_digraphic else target_lang_name
            nmt_json = deepcopy(base_json)

            nmt_json["name"] += f': {_source_lang_name}-{_target_lang_name}'
            
            nmt_json["languages"][0]["sourceLanguage"] = source_lang_code
            nmt_json["inferenceEndPoint"]["schema"]["request"]["config"]["language"]["sourceLanguage"] = source_lang_code

            nmt_json["languages"][0]["sourceScriptCode"] = source_lang_script
            if is_source_digraphic:
                nmt_json["inferenceEndPoint"]["schema"]["request"]["config"]["language"]["sourceScriptCode"] = source_lang_script
            
            nmt_json["languages"][0]["targetLanguage"] = target_lang_code
            nmt_json["inferenceEndPoint"]["schema"]["request"]["config"]["language"]["targetLanguage"] = target_lang_code
            
            nmt_json["languages"][0]["targetScriptCode"] = target_lang_script
            if is_target_digraphic:
                nmt_json["inferenceEndPoint"]["schema"]["request"]["config"]["language"]["targetScriptCode"] = target_lang_script
            
            nmt_json["inferenceEndPoint"]["schema"]["request"]["input"][0]["source"] = source_sample_sentence
            nmt_json["inferenceEndPoint"]["schema"]["response"]["output"][0]["source"] = source_sample_sentence
            nmt_json["inferenceEndPoint"]["schema"]["response"]["output"][0]["target"] = target_sample_sentence

            output_filename = os.path.join(output_folder, f'{source_lang_code}_{source_lang_script}--{target_lang_code}_{target_lang_script}.json')
            with open(output_filename, 'w', encoding="utf-8") as f:
                f.write(json.dumps(nmt_json, indent=2, ensure_ascii=False))
    
    return

# English-to-X and X-to-English
for lang_obj in NATIVE_LANGUAGES:
    gen_json(
        ENGLISH, lang_obj,
        BASE_JSON, OUTPUT_FOLDER,
    )
    gen_json(
        lang_obj, ENGLISH,
        BASE_JSON, OUTPUT_FOLDER,
    )

# X-to-X
for source_lang_obj in NATIVE_LANGUAGES:
    for target_lang_obj in NATIVE_LANGUAGES:
        gen_json(
            source_lang_obj, target_lang_obj,
            BASE_JSON, OUTPUT_FOLDER
        )
