import os
import time

#CROSS_MODULE_COMMON_CONFIGS
MONGO_SERVER_HOST   =   os.environ.get('ULCA_MONGO_CLUSTER', 'mongodb://localhost:27017')#,localhost:27018/?replicaSet=foo

#MODULE-SPECIFIC-CONFIGS

#module configs
DEBUG   =   False
CONTEXT_PATH    =   "/ulca/user-mgmt"
HOST    =   '0.0.0.0'
PORT    =   5001
ENABLE_CORS =   False

#mongodb-configs
MONGO_DB_SCHEMA                 =   os.environ.get('UMS_MONGO_IDENTIFIER', 'ulca-user-management')
MONGO_PROCESS_DB_SCHEMA         =   os.environ.get('ULCA_PROCESS_IDENTIFIER', 'ulca-process-tracker')
USR_MONGO_COLLECTION            =   os.environ.get('UMS_USR_COLLECTION', 'ulca-users')
USR_KEY_MONGO_COLLECTION        =   os.environ.get('UMS_USR_KEY_COLLECTION', 'ulca-user-keys')
USR_TEMP_TOKEN_MONGO_COLLECTION =   os.environ.get('UMS_USR_TEMP_TOKEN_COLLECTION', 'ulca-user-tokens')
USR_ORG_MONGO_COLLECTION        =   os.environ.get('UMS_ORG_COLLECTION', 'organization')
USR_MONGO_PROCESS_COLLECTION    =   os.environ.get('ULCA_PROCESS_COLLECTION', 'pipeline-model')
AES_SECRET_KEY                  =   os.environ.get('AES_SECRET_KEY_FOR_UMS', 'secretKey')
ONBOARDING_AUTH_HEADER          =   os.environ.get('ONBOARDING_AUTH_HEADER',None)
 
#common-variables
PWD_MIN_LENGTH          =   os.environ.get('UMS_PASSWORD_MIN_LENGTH', 8)
PWD_MAX_LENGTH          =   os.environ.get('UMS_PASSWORD_MAX_LENGTH', 25)
EMAIL_MAX_LENGTH        =   os.environ.get('UMS_EMAIL_MAX_LENGTH', 256)
NAME_MAX_LENGTH         =   os.environ.get('UMS_NAME_MAX_LENGTH', 256)
OFFSET_VALUE            =   os.environ.get('UMS_OFFSET_VALUE', 0)
LIMIT_VALUE             =   os.environ.get('UMS_LIMIT_VALUE', 20)
AUTH_TOKEN_EXPIRY_HRS   =   os.environ.get('UMS_TOKEN_EXP_HRS', 24)
ADMIN_ROLE_KEY          =   os.environ.get('UMS_ADMIN_ROLE_KEY', 'ADMIN')
LOGIN_AUTENTICATOR      =   os.environ.get('UMS_LOGIN_AUTENTICATORS',['ULCA'])
MAX_API_KEY             =   os.environ.get('MAX_ULCA_API_KEYS', 10)
SECRET_KEY              =   os.environ.get('API_SECRET_KEY', 'TjWnZr4u7xD*G-KaPdRgUkXp2s5v8acd')
SPECIAL_CHARS           =   "!@#$%^''&*() -+?=,<>/"
if isinstance(MAX_API_KEY,str):
    MAX_API_KEY = int(MAX_API_KEY)

#external file read configs
ROLE_CODES_URL          =   os.environ.get('UMS_ROLE_CODES_URL','https://raw.githubusercontent.com/bhashini-dibd/ulca/zuul-gw/backend/api/ulca-zuul-api-gw/dev-configs/roles.json')
ROLE_CODES_DIR_PATH     =   os.environ.get('UMS_ROLE_DIR_PATH','/app/configs/') 
ROLE_CODES_FILE_NAME    =   os.environ.get('UMS_ROLES_FILE_NAME','roles.json')

#gmail server configs
MAIL_SETTINGS           =   {
                                "MAIL_SERVER"   : os.environ.get('ULCA_EMAIL_SERVER','smtp.gmail.com'),
                                "MAIL_PORT"     : eval(os.environ.get('ULCA_EMAIL_SECURE_PORT','465')),
                                "MAIL_USE_TLS"  : True,
                                "MAIL_USE_SSL"  : False,
                                "MAIL_USERNAME" : os.environ.get('ULCA_EMAIL','xxxxxxx'),
                                "MAIL_PASSWORD" : os.environ.get('ULCA_EMAIL_PASSWORD','xxxxx')
                            }
MAIL_SENDER             =   os.environ.get('ULCA_SENDER_EMAIL','ulca@tarento.com')

SENDER_EMAIL            =   os.environ.get('SENDER_EMAIL','None')
SENDER_PASSWORD         =   os.environ.get('SENDER_PASSWORD','None')
SENDER_USERNAME         =   os.environ.get('SENDER_USERNAME','None')

USER_VERIFY_LINK_EXPIRY =   os.environ.get('ULCA_VERIFY_LINK_EXP_HRS',48)
USER_API_KEY_EXPIRY     =   os.environ.get('ULCA_API_KEY_EXP_DAYS',30)
#React-app base url
BASE_URL                =   os.environ.get('ULCA_REACT_APP_BASE_URL','https://dev.ulcacontrib.org/')
RESET_PWD_ENDPOINT      =   os.environ.get('ULCA_RESET_PWD_ENDPOINT','user/reset-password/')
#dhruva data toggle patch request
PATCH_URL               =   "https://api.dhruva.ai4bharat.org/auth/api-key/ulca"
BHAHSINI_GLOSSARY_CREATE_URL = os.environ.get('DHRUVA_GLOSSARY_CREATE_ENDPOINT',"https://dhruva-api.bhashini.gov.in/services/glossary/v1/create")
BHAHSINI_GLOSSARY_FETCH_URL = os.environ.get('DHRUVA_GLOSSARY_FETCH_ENDPOINT',"https://dhruva-api.bhashini.gov.in/services/glossary/v1/fetch-all")
BHAHSINI_GLOSSARY_DELETE_URL = os.environ.get('DHRUVA_GLOSSARY_DELETE_ENDPOINT',"https://dhruva-api.bhashini.gov.in/services/glossary/v1/delete")

BHAHSINI_SPEAKER_ENROLL_CREATE_URL = os.environ.get('DHRUVA_SPEAKER_ENROLL_ENDPOINT',"https://app-svc-dhruva-dev-cin-001-evf4h9adfdb9eyd2.centralindia-01.azurewebsites.net/services/inference/pipeline")
BHAHSINI_SPEAKER_VERIFICATION_URL = os.environ.get('DHRUVA_SPEAKER_VERIFICATION_ENDPOINT',"https://app-svc-dhruva-dev-cin-001-evf4h9adfdb9eyd2.centralindia-01.azurewebsites.net/services/inference/pipeline")
BHAHSINI_SPEAKER_DELETE_URL = os.environ.get('DHRUVA_SPEAKER_DELETE_ENDPOINT',"https://app-svc-dhruva-dev-cin-001-evf4h9adfdb9eyd2.centralindia-01.azurewebsites.net/services/inference/speakers/delete")
BHAHSINI_SPEAKER_FETCH_URL = os.environ.get('DHRUVA_SPEAKER_FETCH_ENDPOINT',"https://app-svc-dhruva-dev-cin-001-evf4h9adfdb9eyd2.centralindia-01.azurewebsites.net/services/inference/speakers/list")