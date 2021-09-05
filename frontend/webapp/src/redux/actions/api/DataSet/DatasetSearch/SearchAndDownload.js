import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import CONFIGS from "../../../../../configs/configs";
import md5 from 'md5';

export default class SearchAndDownload extends API {
    constructor(timeout = 200000) {
        super("GET", timeout, false);
        this.type = C.GET_SEARCH_OPTIONS;
       // this.endpoint= "https://jsonplaceholder.typicode.com/posts";
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getSearchOptions}`;
        console.log(this.endpoint)
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
        let res = this.apiEndPoint()
        let urlSha = md5(res)
        let hash = md5(this.userDetails.privateKey+"|"+urlSha)
        this.headers = {
            headers: {
                "key" :this.userDetails.publicKey,
                "sig"  : hash
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.report
    }

}
