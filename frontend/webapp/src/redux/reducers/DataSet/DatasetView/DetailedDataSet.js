import C from '../../../actions/constants';

const initialState = {
    responseData: []
}


//{"CurrentRecordIndex": 2, "ProcessedCount": [{"Type": "Success", "Count": 3}, {"Type": "Failed", "TypeDetails": {}, "Count": 0}], "TimeStamp": "2021-06-12 11:47:23.861540"}

const getRecordCount = (value) =>
{
    
    let valueArray = value.processedCount;
    var countDetails = {}
    valueArray.length> 0 && valueArray.forEach(element =>{
        
        if(element.type==="success"){
            if(element.hasOwnProperty("validateSuccessSeconds")){
                countDetails["success"] = (Number(element.validateSuccessSeconds)/3600).toFixed(3) + " hrs"
            }else if(element.hasOwnProperty("publishSuccessSeconds")){
                let hrs =(Number(element.publishSuccessSeconds)/3600).toFixed(3) + " hrs"
                countDetails["success"] =  element.publishSuccessSeconds
            }
            else{
                countDetails["success"] = String(element.count);
            }
            
        }
        else if(element.type==="failed"){
            if(element.hasOwnProperty("validateErrorSeconds")){
                countDetails["failed"] = (Number(element.validateErrorSeconds)/3600).toFixed(3) + " hrs"
            }else if(element.hasOwnProperty("publishErrorSeconds")){
                countDetails["failed"] = (Number(element.publishErrorSeconds)/3600).toFixed(3) + " hrs"
            }else{
                countDetails["failed"] = String(element.count);
            }
            
        }
    })

    return countDetails;

}



const getDetailedReport = (payload) => {
    
    let responseData = [];
    let refreshStatus = false;
    payload.forEach(element => {
        let count = element.details ? getRecordCount( JSON.parse(element.details)):""
    
        
        responseData.push(
            {
                    srNo                    : element.serviceRequestNumber,
                     datasetId              : element.datasetName,
                     recordCount            : count && count.success,
                     failedCount            : count && count.failed,
                     stage                  : element.tool,
                     status                 :  element.status
                    }
        )
        if(element.status === "Completed" || "Pending"){
            refreshStatus = true
        }
    }); 

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