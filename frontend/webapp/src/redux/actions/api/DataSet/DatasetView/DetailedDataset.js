import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import { sha256 } from 'js-sha256';

export default class MyCOntribution extends API {
    constructor(id, timeout = 200000) {
        super("GET", timeout, false);
        this.id = id
        this.type = C.GET_DETAILED_REPORT;
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getDetailReport}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
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
        let url = `${this.endpoint}?serviceRequestNumber=${this.id}` 
         return url;
    }
    getHeaders() {
        this.headers = {
            headers: {
                "key" :this.userDetails.publicKey,
                "sig"  : sha256(this.userDetails.privateKey +"|"+sha256(this.apiEndPoint())),
                "Content-Type": "application/json"
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report;
    }
}