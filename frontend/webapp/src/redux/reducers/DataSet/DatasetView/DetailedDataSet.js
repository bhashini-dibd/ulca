import C from '../../../actions/constants';

const initialState = {
    responseData: []
}


const getDetailedReport = (payload) => {
    debugger
    let responseData = [];
    let refreshStatus = false;
    payload.forEach(element => {
        responseData.push(
            {
                    srNo                    : element.serviceRequestNumber,
                     datasetId              : element.datasetName,
                     recordCount            : element.details? element.details: 0,
                     stage                  : element.tool,
                     status                 : element.status 
                    }
        )
        if(element.status === "INPROGRESS" || "NOTSTARTED"){
            refreshStatus = true
        }
    }); 
    responseData = responseData.reverse()
    return {responseData , refreshStatus};
}


const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_DETAILED_REPORT:
            return  getDetailedReport(action.payload)  
            
        case C.CLEAR_DETAILED_REPORT:
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