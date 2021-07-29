import C from '../../../actions/constants';
import getDatasetName from '../../../../utils/getDataset';
const initialState = {
    responseData: [],
    filteredData: [],
    refreshStatus: false,
    filter: { status: [], modelType: [] },
    selectedFilter: { status: [], modelType: [] },
}

const dateConversion = (value) => {

    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-IN', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result.toUpperCase();
}

const getFilterValue = (payload, data) => {
    let { filterValues } = payload
    let statusFilter = []
    let filterResult = []
    if (filterValues && filterValues.hasOwnProperty("status") && filterValues.status.length > 0) {
        statusFilter = data.responseData.filter(value => {
            if (filterValues.status.includes(value.status)) {
                return value
            }
        })

    } else {
        statusFilter = data.responseData
    }
    if (filterValues && filterValues.hasOwnProperty("modelType") && filterValues.modelType.length > 0) {
        filterResult = statusFilter.filter(value => {
            if (filterValues.modelType.includes(value.modelType)) {
                return value
            }
        })
    }
    else {
        filterResult = statusFilter
    }
    data.filteredData = filterResult;
    data.selectedFilter = filterValues;
    return data;

}

const getClearFilter = (data) => {
    data.filteredData = data.responseData;
    data.selectedFilter = { status: [], modelType: [] }
    return data;
}

const getContributionList = (state, payload) => {
    let responseData = [];
    let statusFilter = [];
    let modelFilter = [];
    let filter = { status: [], modelType: [] }
    let refreshStatus = false;
    payload.forEach(element => {
        responseData.push(
            {
                submitRefNumber: element.serviceRequestNumber,
                modelName: element.datasetName,
                submittedOn: dateConversion(element.submittedOn),
                task: getDatasetName(element.datasetType),
                domain: getDatasetName(element.datasetType),
                status: element.status,
                licence: element.serviceRequestNumber,
                action: "View Result",
                color: element.status === "Completed" ? "#139D60" : element.status === "In-Progress" ? "#2C2799" : element.status === "Failed" ? "#F54336" : "#FEA52C"
            }

        )
        !statusFilter.includes(element.status) && statusFilter.push(element.status)
        !modelFilter.includes(element.datasetName) && modelFilter.push(getDatasetName(element.datasetType))
        if (element.status === "In-Progress" || element.status === "Pending") {
            refreshStatus = true
        }
    });

    filter.status = [...(new Set(statusFilter))];
    filter.modelType = [...(new Set(modelFilter))];


    responseData = responseData.reverse()
    let filteredData = getFilterValue({ "filterValues": state.selectedFilter }, { "responseData": responseData })
    filteredData.filter = filter
    return filteredData
}

const reducer = (state = initialState, action) => {
    switch (action.type) {

        case C.GET_MODEL_CONTRIBUTION_LIST:
            return getContributionList(state, action.payload);
        case C.MODEL_CONTRIBUTION_TABLE:
            return getFilterValue(action.payload, state);
        case C.CLEAR_MODEL_CONTRIBUTION_LIST:
            return {
                ...initialState
            }
        case C.CLEAR_MODEL_FILTER:
            return getClearFilter(state);
        default:
            return {
                ...state
            }
    }
}

export default reducer;



