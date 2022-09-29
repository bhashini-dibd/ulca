
import enum

#lang abbreviations
LANG_CODES        =    {'pa'        :'Punjabi', 
                        'bn'        :'Bengali',
                        'en'        :'English',
                        'ta'        :'Tamil', 
                        'ml'        :'Malayalam', 
                        'te'        :'Telugu', 
                        'kn'        :'Kannada', 
                        'hi'        :'Hindi', 
                        'mr'        :'Marathi',
                        'gu'        :'Gujarati',
                        'or'        :'Odia',
                        'as'        :'Assamese',
                        'kok'       :'Konkani',
                        'sa'        :'Sanskrit',
                        'ks'        :'Kashmiri',
                        'ne'        :'Nepali',
                        'ur'        :'Urdu',
                        'sd'        :'Sindhi',
                        'si'        :'Sinhala',
                        'doi'       :'Dogri',
                        'mai'       :'Maithili',
                        'mni'       :'Manipuri',
                        'brx'       :'Bodo',
                        'sat'       :'Santali'  ,
                        'lus'       :'Lushai',
                        'njz'       :'Ngungwel',
                        'pnr'       :'Panim',
                        'kha'       :'Khasi',
                        'grt'       :'Garo'}

DATA_TYPES         =  {
                            'parallel-corpus'      :    'Parallel Dataset',
                            'monolingual-corpus'   :    'Monolingual Dataset',
                            'asr-corpus'           :    'ASR / TTS Dataset',
                            'ocr-corpus'           :    'OCR Dataset',
                            'asr-unlabeled-corpus' :    'ASR Unlabeled Dataset'
                        }

class APIStatus(enum.Enum):

    SUCCESS                         =   {"message" : "Request successful"}
    ERR_GLOBAL_MISSING_PARAMETERS   =   {"message" : "Data Missing"}

class DataEnums (enum.Enum):
    test = "Test"
