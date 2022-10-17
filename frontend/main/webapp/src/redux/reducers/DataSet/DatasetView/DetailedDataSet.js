import C from '../../../actions/constants';

const initialState = {
    responseData: []
}


//{"CurrentRecordIndex": 2, "ProcessedCount": [{"Type": "Success", "Count": 3}, {"Type": "Failed", "TypeDetails": {}, "Count": 0}], "TimeStamp": "2021-06-12 11:47:23.861540"}

const getRecordCount = (value) => {

    let valueArray = value.processedCount;
    var countDetails = {}
    Array.isArray(valueArray) && valueArray.length > 0 && valueArray.forEach(element => {

        if (element.type === "success") {
            if (element.hasOwnProperty("validateSuccessSeconds")) {
                countDetails["success"] = (Number(element.validateSuccessSeconds) / 3600).toFixed(3) + " hrs"
            } else if (element.hasOwnProperty("publishSuccessSeconds")) {
                let hrs = (Number(element.publishSuccessSeconds) / 3600).toFixed(3) + " hrs"
                countDetails["success"] = hrs
            }
            else {
                countDetails["success"] = String(element.count);
            }

        }
        else if (element.type === "failed") {
            if (element.hasOwnProperty("validateErrorSeconds")) {
                countDetails["failed"] = (Number(element.validateErrorSeconds) / 3600).toFixed(3) + " hrs"
            } else if (element.hasOwnProperty("publishErrorSeconds")) {
                countDetails["failed"] = (Number(element.publishErrorSeconds) / 3600).toFixed(3) + " hrs"
            } else {
                countDetails["failed"] = String(element.count);
            }

        }
    })

    return countDetails;

}



const getDetailedReport = (payload) => {

    let responseData = [];
    let refreshStatus = false;
    const datasetName = payload.datasetName;

    payload.data.forEach(element => {
        // if (element.tool !== 'pseudo') {
            let count = element.details ? getRecordCount(JSON.parse(element.details)) : ""
            responseData.push(
                {
                    srNo: element.serviceRequestNumber,
                    datasetId: element.datasetName,
                    recordCount: count && count.success,
                    failedCount: count && count.failed,
                    stage: element.tool,
                    status: element.status
                }
            )
        // } 
        // else if (element.tool === 'pseudo') {
        //     const data = element.details ? JSON.parse(element.details) : "";
        //     const success = data ? data['publish']['processedCount'][0]['count'] : null;
        //     const failure = data ? (data['ingest']['processedCount'][0]['count'] +
        //         data['ingest']['processedCount'][1]['count']) - data['publish']['processedCount'][0]['count'] : null
        //     responseData.push(
        //         {
        //             srNo: element.serviceRequestNumber,
        //             datasetId: element.datasetName,
        //             recordCount: null,
        //             failedCount: failure,
        //             stage: element.tool,
        //             status: element.status
        //         }
        //     )
        // }
        if (element.status === "Completed" || "Pending") {
            refreshStatus = true
        }
    });

    return { responseData, refreshStatus, datasetName };
}


const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_DETAILED_REPORT:
            return getDetailedReport(action.payload)

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