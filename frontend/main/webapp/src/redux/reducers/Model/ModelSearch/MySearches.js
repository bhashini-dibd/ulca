import C from '../../../actions/constants';
import { getLanguageLabel, FilterByDomain, FilterByCollection } from '../../../../utils/getLabel';
import getDatasetName from '../../../../utils/getDataset';

const initialState = {
    responseData: []
}


const dateConversion = (value) => {

    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-IN', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result.toUpperCase();
}


const getMySearches = (payload) => {
    let newArr = []
    payload.forEach(element => {
        if (element.searchCriteria) {
            let dataSet = getDatasetName(element.searchCriteria.datasetType);
            let langauge = element.searchCriteria.sourceLanguage && getLanguageLabel(element.searchCriteria.sourceLanguage).map(val => val.label)[0]
            let tLanguage = element.searchCriteria.targetLanguage && getLanguageLabel(element.searchCriteria.targetLanguage).map(val => val.label).join(', ')
            let searchDetails = JSON.parse(element.status.length > 0 && element.status[0].details)
            let domain = element.searchCriteria.domain && FilterByDomain(element.searchCriteria.domain).map(val => val.label).join(', ')
            let collection = element.searchCriteria.collectionMethod && FilterByCollection(element.searchCriteria.collectionMethod).map(val => val.label).join(', ')
            newArr.push(
                {
                    sr_no: element.serviceRequestNumber,
                    search_criteria: `${dataSet} | ${langauge} ${tLanguage ? " | " + tLanguage : ""} ${domain ? " | " + domain : ""} ${collection ? " | " + collection : ""}`,
                    searched_on: dateConversion(element.timestamp),
                    status: element.status.length > 0 && element.status[0].status,
                    endPoint: element.inferenceEndPoint,
                    refUrl: element.refUrl ? element.refUrl : "NA",
                    inferenceEndPoint: element.inferenceEndPoint,
                    count: searchDetails && searchDetails.count,
                    sampleUrl: searchDetails && searchDetails.datasetSample,
                    downloadUrl: searchDetails && searchDetails.dataset,
                    sourceLanguage: element.searchCriteria.sourceLanguage,
                    targetLanguage: element.searchCriteria.targetLanguage,
                    datasetType: element.searchCriteria.datasetType,
                    domain: element.searchCriteria.domain,
                    collection: element.searchCriteria.collectionMethod


                }

            )

        }

    })
    newArr = newArr.reverse()

    return newArr;
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_MY_REPORT:
            return {
                responseData: getMySearches(action.payload)
            }
        case C.CLEAR_USER_EVENT:
            return {
                ...initialState
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;