/**
 * Model Search API
 */
import API from "../../../api";
import C from "../../../constants";
import CONFIGS from "../../../../../configs/configs";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from 'md5';

export default class SearchModel extends API {
    constructor(task = "translation", sourceLanguage = null, targetLanguage = [], domain, submitter, timeout = 2000) {
        super("POST", timeout, false);
        this.task = task;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.domain = domain;
        this.submitter = submitter
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.modelSearch}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'));
        this.type = C.SUBMIT_MODEL_SEARCH;
    }


    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.report = res;
        }
    }

    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let bodyData = {
            task: this.task,
            sourceLanguage: this.sourceLanguage,
            targetLanguage: this.targetLanguage,
            domain: this.domain,
            submitter: this.submitter
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
        console.log(this.report)
        return this.report;

    }
}
