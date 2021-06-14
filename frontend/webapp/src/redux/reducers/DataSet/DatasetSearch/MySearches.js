import C from '../../../actions/constants';

const initialState = {
    responseData: []
}


const dateConversion = (value) =>{
    
    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-US', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result;
}


const getMySearches = (payload) => {
    debugger
    let newArr = []
    payload.forEach(element =>{
        if(element.searchCriteria){
            let dataSet  = element.searchCriteria.datasetType === "parallel-corpus" ? "Parallel Dataset" : element.searchCriteria.datasetType;
            let langauge = element.searchCriteria.sourceLanguage
            let tLanguage = element.searchCriteria.targetLanguage
            let searchDetails = JSON.parse(element.status[0].details)
            newArr.push(
                {
                    sr_no : element.serviceRequestNumber,
                    search_criteria :`${dataSet} | ${langauge} | ${tLanguage}`,
                    searched_on   : dateConversion(element.timestamp),
                    status      : element.status[0].status === "successful" ? "Completed" : element.status[0].status,
                    count : searchDetails.count,
                    sampleUrl : searchDetails.datasetSample,
                    downloadUrl : searchDetails.dataset,
                    sourceLanguage : langauge,
                    targetLanguage : tLanguage

                }
                
            )
            
        }
        
    })
    console.log(newArr)
   
    let dataObj = [{"sr_no":"0005770","search_criteria":"Parallel dataset : English, Hindi, Malayalam, Tamil","searched_on":"23/5/2011","status":"Inprogress"},{"sr_no":"0045770","search_criteria":"Monolinguel Dataset : English, General","searched_on":"3/5/2011","status":"Published"},{"sr_no":"0205770","search_criteria":"ARS/TTS Dataset; English, Legal","searched_on":"12/5/2011","status":"Published"},{"sr_no":"0005470","search_criteria":"Parallel Dataset; English; Bemgali ; News","searched_on":"2/5/2011","status":"Published"}]
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