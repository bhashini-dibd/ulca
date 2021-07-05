import C from '../../../actions/constants';
import getDatasetName from '../../../../utils/getDataset';
const initialState = {
    responseData: []
}

const dateConversion = (value) =>{
    
    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-IN', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result.toUpperCase();
}

const getContributionList = (payload) => {
    let responseData = [];
    let refreshStatus = false;
    payload.forEach(element => {
        responseData.push(
            {
                     submitRefNumber      : element.serviceRequestNumber,
                     datasetName          : element.datasetName,
                     submittedOn          : dateConversion(element.submittedOn),
                     datasetType :          getDatasetName(element.datasetType),
                     status               : element.status
            }
        )
        if(element.status === "In-Progress" || "Pending"){
            refreshStatus = true
        }
    }); 
    responseData = responseData.reverse()
    return {responseData , refreshStatus};
}

const reducer = (state = initialState, action) => {
    
    switch (action.type) {

        case C.GET_CONTRIBUTION_LIST:
            return getContributionList(action.payload);
        case C.CLEAR_CONTRIBUTION_LIST:
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