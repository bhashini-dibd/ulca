import C from '../../../actions/constants';
import getLanguageLabel from '../../../../utils/getLabel';

const initialState = {
    responseData: []
}


const dateConversion = (value) =>{
    
    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-US', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result;
}


const getMySearches = (payload) => {
    let newArr = []
    payload.forEach(element =>{
        if(element.searchCriteria){
            let dataSet  = element.searchCriteria.datasetType === "parallel-corpus" ? "Parallel Dataset" : element.searchCriteria.datasetType;
            let langauge = getLanguageLabel(element.searchCriteria.sourceLanguage).map(val=>val.label)[0]
            let tLanguage = getLanguageLabel(element.searchCriteria.targetLanguage).map(val=>val.label).join(', ')
            let searchDetails = JSON.parse(element.status[0].details)
            newArr.push(
                {
                    sr_no : element.serviceRequestNumber,
                    search_criteria :`${dataSet} | ${langauge} | ${tLanguage}`,
                    searched_on   : dateConversion(element.timestamp),
                    status      : element.status[0].status === "successful" ? "Completed" : element.status[0].status === "inprogress" ? "In-Progress": element.status[0].status,
                    count : searchDetails.count,
                    sampleUrl : searchDetails.datasetSample,
                    downloadUrl : searchDetails.dataset,
                    sourceLanguage : element.searchCriteria.sourceLanguage,
                    targetLanguage : element.searchCriteria.targetLanguage,
                    datasetType : dataSet

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