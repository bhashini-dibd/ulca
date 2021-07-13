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
USR_MONGO_COLLECTION            =   os.environ.get('UMS_USR_COLLECTION', 'ulca-users')
USR_KEY_MONGO_COLLECTION        =   os.environ.get('UMS_USR_KEY_COLLECTION', 'ulca-user-keys')
USR_TEMP_TOKEN_MONGO_COLLECTION =   os.environ.get('UMS_USR_TEMP_TOKEN_COLLECTION', 'ulca-user-tokens')
USR_ORG_MONGO_COLLECTION        =   os.environ.get('UMS_ORG_COLLECTION', 'organization')

 
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

#external file read configs
ROLE_CODES_URL          =   os.environ.get('UMS_ROLE_CODES_URL','https://raw.githubusercontent.com/project-anuvaad/ULCA/zuul-gw/backend/api/ulca-zuul-api-gw/dev-configs/roles.json')
ROLE_CODES_DIR_PATH     =   os.environ.get('UMS_ROLE_DIR_PATH','/app/configs/') 
ROLE_CODES_FILE_NAME    =   os.environ.get('UMS_ROLES_FILE_NAME','roles.json')

#gmail server configs
MAIL_SETTINGS           =   {
                                "MAIL_SERVER"   : 'smtp.gmail.com',
                                "MAIL_PORT"     : 465,
                                "MAIL_USE_TLS"  : False,
                                "MAIL_USE_SSL"  : True,
                                "MAIL_USERNAME" : os.environ.get('ULCA_SUPPORT_EMAIL','xxxxxxx'),
                                "MAIL_PASSWORD" : os.environ.get('ULCA_SUPPORT_EMAIL_PASSWORD','xxxxx')
                            }
USER_VERIFY_LINK_EXPIRY =   os.environ.get('ULCA_VERIFY_LINK_EXP_HRS',48)
USER_API_KEY_EXPIRY     =   os.environ.get('ULCA_API_KEY_EXP_DAYS',30)
#React-app base url
BASE_URL                =   os.environ.get('ULCA_REACT_APP_BASE_URL','https://dev.ulcacontrib.org/')
RESET_PWD_ENDPOINT      =   os.environ.get('ULCA_RESET_PWD_ENDPOINT','user/reset-password/')