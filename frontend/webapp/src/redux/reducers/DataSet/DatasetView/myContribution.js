import C from '../../../actions/constants';

const initialState = {
    responseData: []
}

const dateConversion = (value) =>{
    
    let date = value.toString()
        let timestamp = date.substring(0, 13)
        var d = new Date(parseInt(timestamp))
        let dateStr = d.toISOString()
        var myDate = new Date(dateStr);
        let result = (myDate.toLocaleString('en-US', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result;
}

const getContributionList = (payload) => {
    let responseData = [];
    let refreshStatus = false;
    payload.forEach(element => {
        responseData.push(
            {
            submitRefNumber       : element.submitRefNumber,
            datasetName      : element.datasetName,
            submittedOn : dateConversion(element.submittedOn),
            status       : element.status
            }
        )
        if(element.status === "Inprogress"){
            refreshStatus = true
        }
    }); 
    return {responseData , refreshStatus};
}
////[{"submitRefNumber":"0005771","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"},{"submitRefNumber":"0005770","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"},{"submitRefNumber":"0005772","datasetName":"Tourism Set-1","submittedOn":"16228006077741","status":"Published"},{"submitRefNumber":"0005770","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"}]
const reducer = (state = initialState, action) => {
    
    switch (action.type) {
        case C.GET_CONTRIBUTION_LIST:
            return getContributionList([{"submitRefNumber":"0005771","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"},{"submitRefNumber":"0005770","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"},{"submitRefNumber":"0005772","datasetName":"Tourism Set-1","submittedOn":"16228006077741","status":"Published"},{"submitRefNumber":"0005770","datasetName":"Tourism Set-1","submittedOn":"1622800607774","status":"Published"}]);
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