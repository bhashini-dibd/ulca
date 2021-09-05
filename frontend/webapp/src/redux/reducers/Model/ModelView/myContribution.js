import C from '../../../actions/constants';
import getDatasetName from '../../../../utils/getDataset';
import { getLanguageName, FilterByDomain, FilterByCollection } from '../../../../utils/getLabel';
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
            if (value.status && filterValues.status.includes(value.status)) {
                return value
            }
        })

    } else {
        statusFilter = data.responseData
    }
    if (filterValues && filterValues.hasOwnProperty("modelType") && filterValues.modelType.length > 0) {
        filterResult = statusFilter.filter(value => {
            if (value.modelType && filterValues.modelType.includes(value.modelType)) {
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

const getDomainDetails = (data) => {
    if (data.length === 1) {
        return data[0]
    } else {
        let result = ""
        data.length > 1 && data.forEach((element, i) => {
            if (i !== data.length) {
                result = result + element + "|"
            } else {
                result = result + element
            }

        })
        return result;
    }
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
        let sLanguage = element.languages.length > 0 && element.languages[0].sourceLanguage && getLanguageName(element.languages[0].sourceLanguage)
        let tLanguage = element.languages && element.languages.length > 0 && element.languages[0].targetLanguage && getLanguageName(element.languages[0].targetLanguage)
        let lang = tLanguage ? (sLanguage + " - " + tLanguage) : sLanguage
        responseData.push(
            {
                submitRefNumber: element.modelId,
                modelName: element.name,
                description: element.description,
                submittedOn: dateConversion(element.submittedOn),
                task: element.task.type,
                domain: getDomainDetails(element.domain),
                status: "Published",
                endPoint: element.inferenceEndPoint,
                language: lang,
                source: element.languages.length > 0 && element.languages[0].sourceLanguage,
                target:element.languages && element.languages.length > 0 && element.languages[0].targetLanguage,
                licence: element.license,
                submitter: element.submitter.name,
                trainingDataset: element.trainingDataset,
                action: "View Result",
                color: element.status === "Completed" ? "#139D60" : element.status === "In-Progress" ? "#2C2799" : element.status === "Failed" ? "#F54336" : "green"
            }

        )
        ! statusFilter.includes(element.status) && element.status && statusFilter.push(element.status)
        ! modelFilter.includes(element.datasetName) && element.datasetName && modelFilter.push(getDatasetName(element.datasetType))
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



