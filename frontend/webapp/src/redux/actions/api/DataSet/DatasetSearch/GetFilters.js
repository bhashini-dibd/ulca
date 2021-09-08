import C from '../../../constants';

export const getFilter = (datasetType) => {
    return {
        type: C.GET_SEARCH_FILTERS,
        payload: datasetType
    }
}