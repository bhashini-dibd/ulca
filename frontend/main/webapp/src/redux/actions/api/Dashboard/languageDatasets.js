import API from "../../api";
import C from "../../constants";
import ENDPOINTS from "../../../../configs/apiendpoints";


export default class CreateGlossary extends API {
    
    constructor(dataType,value,criterions = [], timeout = 2000) {
        super('POST', timeout, false);
        this.type           =   C.DASHBOARD_DATASETS;
        this.dataType       =   dataType;
        this.criterions     =   criterions
        this.value          =   value
        this.endpoint       =   `${super.apiEndPointAuto()}${ENDPOINTS.dataSetSearchApi}`;
    }

    toString() {
        return `${super.toString()} , type: ${this.type}`;
    }

    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.response = res;
        }
    }

    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let request = {}
        request["type"]        = this.dataType;
        request["criterions"]  = this.criterions;
         request["groupby"]     = this.value ? [{"field":this.value, "value": null}]: null
        return request;
    }

    getHeaders() {
        this.headers = {
            headers: {
                
                "Content-Type": "application/json"
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.response;
    }
}