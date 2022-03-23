import enum


class StaticConfigs(enum.Enum):

    DS_SEARCH_COMPLETE                  =   "ULCA - Search result is ready"
    DS_SUBMIT_SUCCESS                   =   "ULCA - Dataset published" 
    DS_SUBMIT_FAILED                    =   "ULCA - Failed to publish dataset"
    BM_RUN_SUCCESS                      =   "ULCA - Benchmark run is completed"
    BM_RUN_FAILED                       =   "ULCA - Benchmark run failed"
    MD_INFR_FAILED                      =   "ULCA - Model inference unavailable"
