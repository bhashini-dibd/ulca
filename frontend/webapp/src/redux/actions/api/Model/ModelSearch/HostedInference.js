/**
 * Model Search API
 */
import API from "../../../api";
import C from "../../../constants";
import CONFIGS from "../../../../../configs/configs";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from 'md5';

export default class HostedInference extends API {
    constructor(modelId, input, task,record, timeout = 2000) {
        super("POST", timeout, false);
        this.modelId = modelId;
        this.input = input;
        this.task = task;
        this.record = record;
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.hostedInference}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'));
    }

    toString() {
        return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
    }



    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.report = res.data;
        }
    }

    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let bodyData = {
            modelId: this.modelId,
            task: this.task
        }
        debugger
        if (this.task === 'translation') {
            bodyData.input = [{ source: this.input }]
        } else if (this.task === 'asr') {
            if (this.record) {
                bodyData.audioContent = this.input
            }else{
                bodyData.audioUri = this.input
            }
            
        }
        bodyData.userId = JSON.parse(localStorage.getItem('userDetails')).userID
        return bodyData;
    }


    getHeaders() {
        this.headers = {
            headers: {
                "Content-Type": "application/json",

            }
        };
        return this.headers;
    }


    getPayload() {
        return this.report;

    }
}
