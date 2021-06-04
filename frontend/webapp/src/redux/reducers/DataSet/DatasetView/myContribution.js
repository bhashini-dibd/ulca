import C from '../../../actions/constants';

const initialState = {
    responseData: []
}



const getContributionList = (payload) => {
   
    let dataObj = [{"sr_no":"0005770","Dataset":"Tourism Set-1","Submitted_on":"23/5/2011","Status":"Inprogress"},{"sr_no":"0045770","Dataset":"Tourism Set-7","Submitted_on":"3/5/2011","Status":"Published"},{"sr_no":"0205770","Dataset":"Tourism Set-5","Submitted_on":"12/5/2011","Status":"Published"},{"sr_no":"0005470","Dataset":"Tourism Set-4","Submitted_on":"2/5/2011","Status":"Published"}]
    // let latestEvent = removeDuplicates(result, 's_id')

    // return latestEvent;
    
    return dataObj;
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_CONTRIBUTION_LIST:
            return {
                responseData: getContributionList(action.payload.responseData),
                
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