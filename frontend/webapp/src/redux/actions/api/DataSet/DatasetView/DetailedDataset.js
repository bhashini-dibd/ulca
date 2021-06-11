import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class MyCOntribution extends API {
    constructor(id, timeout = 200000) {
        super("GET", timeout, false);
        this.id = id
        this.type = C.GET_DETAILED_REPORT;
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getDetailReport}`;
    }

    toString() {
        return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
    }

    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.report = res;
        }
    }

    apiEndPoint() {

        
        let url = `${this.endpoint}?record_id=${this.id}` 
        
         return url;
    }

    getBody() {
        return {};
    }

    getHeaders() {
        this.headers = {
            headers: {
                 'auth-token': `${decodeURI(localStorage.getItem("token"))}`
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report;
    }
}