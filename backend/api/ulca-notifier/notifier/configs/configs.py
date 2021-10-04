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
ds_notifier_event_input_topic           =   os.environ.get('KAFKA_ULCA_DATASET_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-ds-notifier-ip-v0')
bm_notifier_event_input_topic           =   os.environ.get('KAFKA_ULCA_BENCHMARK_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-bm-notifier-ip-v0')
search_notifier_event_input_topic       =   os.environ.get('KAFKA_ULCA_SEARCH_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-search-notifier-ip-v0')

#mongo configs
ulca_db_cluster                         =   os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
user_db                                 =   os.environ.get('UMS_MONGO_IDENTIFIER', "ulca-user-management")
user_collection                         =   os.environ.get('UMS_USR_COLLECTION', "ulca-users")
process_db                              =   os.environ.get('PROCESS_DB', "ulca-process-tracker")
process_collection                      =   os.environ.get('PROCESS_COL', "ulca-pt-processes")

#gmail server configs
MAIL_SETTINGS                           =   {
                                                "MAIL_SERVER"   : os.environ.get('ULCA_EMAIL_SERVER','smtp.gmail.com'),
                                                "MAIL_PORT"     : eval(os.environ.get('ULCA_EMAIL_SECURE_PORT','465')),
                                                "MAIL_USE_TLS"  : False,
                                                "MAIL_USE_SSL"  : True,
                                                "MAIL_USERNAME" : os.environ.get('ULCA_EMAIL','xxxxxxxxxx'),
                                                "MAIL_PASSWORD" : os.environ.get('ULCA_EMAIL_PASSWORD','xxxxxxxx')
                                            }
MAIL_SENDER                             =   os.environ.get('ULCA_SENDER_EMAIL','xxxxxxxxx')
