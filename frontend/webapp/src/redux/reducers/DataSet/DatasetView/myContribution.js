import C from '../../../actions/constants';
import getDatasetName from '../../../../utils/getDataset';
const initialState = {
    responseData: [],
    filteredData:[],
    refreshStatus:false,
    filter : {status:[],datasetType:[]},
    selectedFilter : {status:[],datasetType:[]},
}

const dateConversion = (value) =>{
    
    var myDate = new Date(value);
    let result = (myDate.toLocaleString('en-IN', { day: '2-digit', month: '2-digit', year: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true }))
    return result.toUpperCase();
}

const getContributionList = (payload) => {
    let responseData = [];
    let statusFilter = [];
    let datatypeFilter = [];
    let filter = {status:[],datasetType:[]}
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
        !statusFilter.includes(element.status) && statusFilter.push(element.status)
        !datatypeFilter.includes(element.datasetName) && datatypeFilter.push(getDatasetName(element.datasetType))
        debugger
        if(element.status === "In-Progress" || element.status === "Pending"){
            refreshStatus = true
        }
    }); 

    filter.status = statusFilter;
    filter.datasetType = datatypeFilter;


    responseData = responseData.reverse()
    return {responseData ,filteredData:responseData, refreshStatus, filter, selectedFilter:{status:[],datasetType:[]}};
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