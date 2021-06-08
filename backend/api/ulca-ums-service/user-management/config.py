import os
import time

#CROSS_MODULE_COMMON_CONFIGS
MONGO_SERVER_HOST   =   os.environ.get('MONGO_CLUSTER_URL', 'mongodb://localhost:27017')#,localhost:27018/?replicaSet=foo


#MODULE-SPECIFIC-CONFIGS

#module configs
DEBUG   =   False
CONTEXT_PATH    =   "/ulca/user-mgmt"
HOST    =   '0.0.0.0'
PORT    =   5001
ENABLE_CORS =   False

#mongodb-configs
MONGO_DB_SCHEMA                 =   os.environ.get('MONGO_DB_IDENTIFIER', 'ulca-user-management')
USR_MONGO_COLLECTION            =   os.environ.get('ULCA_USR_COLLECTION', 'ulca-users')
USR_KEY_MONGO_COLLECTION        =   os.environ.get('ULCA_USR_KEY_COLLECTION', 'ulca-user-keys')
USR_TEMP_TOKEN_MONGO_COLLECTION =   os.environ.get('ULCA_USR_TEMP_TOKEN_COLLECTION', 'usertemptoken')
USR_ORG_MONGO_COLLECTION        =   os.environ.get('ULCA_ORG_COLLECTION', 'organization')

 
#common-variables
PWD_MIN_LENGTH          =   os.environ.get('ULCA_PASSWORD_MIN_LENGTH', 8)
PWD_MAX_LENGTH          =   os.environ.get('ULCA_PASSWORD_MAX_LENGTH', 25)
EMAIL_MAX_LENGTH        =   os.environ.get('ULCA_PASSWORD_MAX_LENGTH', 256)
NAME_MAX_LENGTH         =   os.environ.get('ULCA_PASSWORD_MAX_LENGTH', 256)
OFFSET_VALUE            =   os.environ.get('ULCA_OFFSET_VALUE', 0)
LIMIT_VALUE             =   os.environ.get('ULCA_LIMIT_VALUE', 20)
AUTH_TOKEN_EXPIRY_HRS   =   os.environ.get('ULCA_TOKEN_EXP_HRS', 24)
ADMIN_ROLE_KEY          =   os.environ.get('ULCA_ADMIN_ROLE_KEY', 'ADMIN')
LOGIN_AUTENTICATOR      =   os.environ.get('ULCA_LOGIN_AUTENTICATORS',['ULCA'])

#external file read configs
ROLE_CODES_URL          =   os.environ.get('UMS_ROLE_CODES_URL','https://raw.githubusercontent.com/project-anuvaad/anuvaad/zuul_gateway/anuvaad-api/anuvaad-zuul-api-gw/dev-configs/roles.json')
ROLE_CODES_DIR_PATH     =   os.environ.get('UMS_ROLE_DIR_PATH','/app/configs/') 
ROLE_CODES_FILE_NAME    =   os.environ.get('UMS_FILE_NAME','roles.json')

#gmail server configs
MAIL_SETTINGS           =   {
                                "MAIL_SERVER"   : 'smtp.gmail.com',
                                "MAIL_PORT"     : 465,
                                "MAIL_USE_TLS"  : False,
                                "MAIL_USE_SSL"  : True,
                                "MAIL_USERNAME" : os.environ.get('SUPPORT_EMAIL','xxxxxxxxxxx'),
                                "MAIL_PASSWORD" : os.environ.get('SUPPORT_EMAIL_PASSWORD','xxxxx')
                            }
USER_VERIFY_LINK_EXPIRY =   os.environ.get('ULCA_VERIFY_LINK_EXP_HRS',48)
USER_API_KEY_EXPIRY     =   os.environ.get('ULCA_API_KEY_EXP_DAYS',30)
#React-app base url
BASE_URL                =   os.environ.get('ULCA_REACT_APP_BASE_URL','https://developers.ulca.org/')