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
    console.log(result)
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
                description: element.description,
                submitRefNumber: element.modelId,
                modelName: element.name,
                // submittedOn: dateConversion(element.submittedOn),
                publishedOn: dateConversion(element.publishedOn),
                task: element.task.type,
                domain: getDomainDetails(element.domain),
                status: "Published",
                language: lang,
                licence: element.license,
                submitter: element.submitter.name,
                trainingDataset: element.trainingDataset,
                color: element.status === "Completed" ? "#139D60" : element.status === "In-Progress" ? "#139D60" : element.status === "Failed" ? "#139D60" : "green"
            }

        )
        !statusFilter.includes(element.status) && statusFilter.push(element.status)
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

        case C.SUBMIT_MODEL_SEARCH:
            return getContributionList(state, action.payload);
        default:
            return {
                ...state
            }
    }
}

export default reducer;



