import C from '../../../actions/constants';

const initialState = {
    responseData: []
}


//{"CurrentRecordIndex": 2, "ProcessedCount": [{"Type": "Success", "Count": 3}, {"Type": "Failed", "TypeDetails": {}, "Count": 0}], "TimeStamp": "2021-06-12 11:47:23.861540"}

const getRecordCount = (value) => {
    console.log(value);
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
    payload.forEach(element => {
        if (element.id) {
            if (element.tool === 'pseudo') {
                responseData.push({
                    srNo: element.serviceRequestNumber,
                    datasetId: element.datasetName,
                    recordCount: null,
                    failedCount: null,
                    stage: element.tool,
                    status: element.status
                })
                const data = JSON.parse(element.details);
                const stages = ['ingest', 'validate', 'publish'];
                stages.forEach(e => {
                    responseData.push({
                        srNo: null,
                        datasetId: null,
                        recordCount: data[e]['processedCount'][0]['count'],
                        failedCount: data[e]['processedCount'][1]['count'],
                        stage: e,
                        status: null
                        //  status: data[e]['processedCount'][1]['count'] === 0 ? 'Completed' : 'Failed'
                    })
                })

            } else {
                responseData.push({
                    srNo: element.serviceRequestNumber,
                    datasetId: element.datasetName,
                    recordCount: null,
                    failedCount: null,
                    stage: element.tool,
                    status: element.status
                })
            }
        }
    })
    return { responseData, refreshStatus };
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