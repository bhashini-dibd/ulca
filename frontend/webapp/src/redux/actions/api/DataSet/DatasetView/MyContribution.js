import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class MyCOntribution extends API {
    constructor(file_name, user_id, timeout = 200000) {
        super("GET", timeout, false);
        this.user_id = JSON.parse(localStorage.getItem('userDetails')).userID
        this.type = C.GET_CONTRIBUTION_LIST;
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getContributionList}`;
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

        
        let url = `${this.endpoint}?userId=${this.user_id}` 
        
         return url;
    }

    getBody() {
        return {};
    }

    getHeaders() {
        this.headers = {
            headers: {
                 
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report;
    }
}