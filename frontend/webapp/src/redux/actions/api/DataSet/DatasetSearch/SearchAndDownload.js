import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import CONFIGS from "../../../../../configs/configs";
import { sha256 } from 'js-sha256';
export default class SearchAndDownload extends API {
    constructor(timeout = 200000) {
        super("GET", timeout, false);
        this.type = C.GET_SEARCH_OPTIONS;
        this.endpoint= "https://jsonplaceholder.typicode.com/posts";
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
        //this.endpoint = `${CONFIGS.API_URL}${ENDPOINTS.getSearchOptions}`;
    }

    toString() {
        return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
    }

    processResponse(res) {
        super.processResponse(res);
        this.report = res
    }

    apiEndPoint() {
        return this.endpoint;
    }


    getHeaders() {
        this.headers = {
            headers: {
                "key" :this.userDetails.publicKey,
                "sig"  : sha256(this.userDetails.privateKey +"|"+sha256(this.endpoint)),
                "Content-Type": "application/json"
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report
    }

}
