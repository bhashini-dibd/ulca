import C from '../../../actions/constants';

const initialState = {
    responseData: []
}



const getMySearches = (payload) => {
   
    let dataObj = [{"sr_no":"0005770","search_criteria":"Parallel dataset : English, Hindi, Malayalam, Tamil","searched_on":"23/5/2011","status":"Inprogress"},{"sr_no":"0045770","search_criteria":"Monolinguel Dataset : English, General","searched_on":"3/5/2011","status":"Published"},{"sr_no":"0205770","search_criteria":"ARS/TTS Dataset; English, Legal","searched_on":"12/5/2011","status":"Published"},{"sr_no":"0005470","search_criteria":"Parallel Dataset; English; Bemgali ; News","searched_on":"2/5/2011","status":"Published"}]
    return dataObj;
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