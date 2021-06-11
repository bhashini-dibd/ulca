import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class MyCOntribution extends API {
    constructor(file_name, user_id, timeout = 200000) {
        super("GET", timeout, false);
        this.user_id = user_id
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

        
        let url = `${this.endpoint}/listByUserId?userId=6491af71d71b4f1d9cff293522260838` 
        
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