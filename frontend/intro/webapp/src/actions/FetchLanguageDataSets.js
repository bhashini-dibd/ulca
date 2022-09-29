// import API from "../../api";
// import C from "../../constants";
import CONFIG from "../configs/configs";


export default class CreateGlossary {
    
    constructor(dataType,value,criterions = [], timeout = 2000) {
       
        this.dataType       =   dataType;
        this.criterions     =   criterions
        this.value          =   value
        this.endpoint       =   `${CONFIG.BASE_URL_AUTO}/ulca/data-metric/v0/store/search`;
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
        request["criterions"]  = this.criterions?this.criterions:null;
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