import os

#service configs
DEBUG                                   =   False
ENABLE_CORS                             =   False
context_path                            =   os.environ.get('NOTIFIER_SERVICE_CONTEXT_PATH','/ulca/notifier')
app_host                                =   os.environ.get('ULCA_NOTIFIER_SERVICE_HOST', '0.0.0.0')
app_port                                =   os.environ.get('ULCA_NOTIFIER_SERVICE_PORT', 5001)

#kafka configs
publish_consumer_grp                    =   os.environ.get('KAFKA_ULCA_DS_NOTIFIER_CONSUMER_GRP', 'ulca-ds-notifier-consumer-group-v0')
kafka_bootstrap_server_host             =   os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
notifier_event_input_topic              =   os.environ.get('KAFKA_ULCA_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-notifier-ip-v0')

#mongo configs
ulca_db_cluster                         =   os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
user_db                                 =   os.environ.get('UMS_MONGO_IDENTIFIER', "ulca-user-management")
user_collection                         =   os.environ.get('UMS_USR_COLLECTION', "ulca-users")
process_db                              =   os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
process_collection                      =   os.environ.get('ULCA_PROC_TRACKER_PROC_COL', "ulca-pt-processes")
#React-app base url
base_url                                =   os.environ.get('ULCA_REACT_APP_BASE_URL','https://dev.ulcacontrib.org/')
ds_contribution_endpoint                =   os.environ.get('ULCA_DS_MYCONTRIBUTION_PATH','dataset/my-contribution/')
model_bm_contribution_endpoint          =   os.environ.get('ULCA_MODELS_MYCONTRIBUTION_PATH','model/my-contribution/')
ds_search_list_endpoint                 =   os.environ.get('ULCA_DS_MYSEARCHES_PATH','my-searches/')
#gmail server configs
MAIL_SETTINGS                           =   {
                                                "MAIL_SERVER"   : os.environ.get('ULCA_EMAIL_SERVER','smtp.gmail.com'),
                                                "MAIL_PORT"     : eval(os.environ.get('ULCA_EMAIL_SECURE_PORT','465')),
                                                "MAIL_USE_TLS"  : False,
                                                "MAIL_USE_SSL"  : True,
                                                "MAIL_USERNAME" : os.environ.get('ULCA_EMAIL','xxxxxxxxxx'),
                                                "MAIL_PASSWORD" : os.environ.get('ULCA_EMAIL_PASSWORD','xxxxxxx')
                                             }

MAIL_SENDER                             =   os.environ.get('ULCA_SENDER_EMAIL','xxxxxxxxxx')

#events
ds_completed                            =   os.environ.get('ULCA_NOTIFIER_DS_SUBMIT_COMPLETED_STATUS','dataset-submit-completed')
ds_failed                               =   os.environ.get('ULCA_NOTIFIER_DS_SUBMIT_FAILED_STATUS','dataset-submit-failed')
bm_completed                            =   os.environ.get('ULCA_NOTIFIER_BM_RUN_COMPLETED_STATUS','benchmark-run-completed')
bm_failed                               =   os.environ.get('ULCA_NOTIFIER_BM_RUN_FAILED_STATUS','benchmark-run-failed')
search_completed                        =   os.environ.get('ULCA_NOTIFIER_DS_SEARCH_COMPLETED_STATUS','search-records-completed')
inference_check                         =   os.environ.get('ULCA_NOTIFIER_MD_INF_CHECK_STATUS','inference-check-failed')


#models
receiver_email_ids                      =   os.environ.get('ULCA_USER_GROUP',"ulca-dev@tarento.com")