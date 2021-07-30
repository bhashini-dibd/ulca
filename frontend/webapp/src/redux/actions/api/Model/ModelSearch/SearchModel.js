/**
 * Model Search API
 */
import API from "../../../api";
import C from "../../../constants";
import CONFIGS from "../../../../../configs/configs";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from 'md5';

export default class SearchModel extends API {
    constructor(task = "translation", sourceLanguage = null, targetLanguage = [], timeout = 2000) {
        super("POST", timeout, false);
        this.task = task;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage[0];
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.modelSearch}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'));
        this.type = C.SUBMIT_MODEL_SEARCH;
    }

    toString() {
        return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
    }



    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.report = res.data;
        }
        debugger
        console.log('inside processResponse', res)
    }

    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let bodyData = {
            task: this.task,
            sourceLanguage: this.sourceLanguage,
            targetLanguage: this.targetLanguage
        }
        bodyData.userId = JSON.parse(localStorage.getItem('userDetails')).userID
        return bodyData;
    }


    getHeaders() {
        let urlSha = md5(JSON.stringify(this.getBody()))
        let hash = md5(this.userDetails.privateKey + "|" + urlSha)
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "key": this.userDetails.publicKey,
                "sig": hash
            }
        };
        return this.headers;
    }


    getPayload() {
        return this.report;

    }
}
