import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import { sha256 } from 'js-sha256';

export default class MyCOntribution extends API {
    constructor( user_id, timeout = 200000) {
        super("GET", timeout, false);
        this.user_id        = user_id
        this.type           = C.GET_MY_REPORT;
        this.endpoint       = `${super.apiEndPointAuto()}${ENDPOINTS.mySearches}`;
        let userInf                     = localStorage.getItem("userDetails")
        this.userId              = JSON.parse(userInf).userID;
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

        
        let url = `${this.endpoint}?userId=${this.userId }` 
        
         return url;
    }

    getBody() {
        return {};
    }

    getHeaders() {
        this.headers = {
            headers: {
                "key" :this.userDetails.publicKey,
                "sig"  : sha256(this.userDetails.privateKey+"|"+sha256(this.apiEndPoint())),
                "Content-Type": "application/json"
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report;
    }
}