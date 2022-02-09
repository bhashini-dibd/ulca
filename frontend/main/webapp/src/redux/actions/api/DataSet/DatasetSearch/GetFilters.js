import C from '../../../constants';

export const getFilter = (datasetType) => {
    return {
        type: C.GET_SEARCH_FILTERS,
        payload: datasetType
    }
}

export const getFilterCategory = (payload,datasetType)=>{
    return {
        type:C.GET_FILTER_CATEGORY,
        payload:{
            data:payload,
            type:datasetType
        }
    }
}