import C from '../../../actions/constants';

const initialState = {
    responseData: []
}



const getContributionList = (payload) => {
   
    let dataObj = [{"stage":"File Download","status":"Completed"},{"stage":"Sanity Check","status":"Completed","Record Count":"5000/5000"},{"stage":"Record validation","status":"In Progress","Record Count":"50001/1000000"},{"stage":"Record Publish","status":"In Progress","Record Count":"40000/100000"}]
    // let latestEvent = removeDuplicates(result, 's_id')

    // return latestEvent;
    return dataObj;
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_DETAILED_REPORT:
            return {
                responseData: getContributionList(action.payload.responseData)  
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