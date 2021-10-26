import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from 'md5';

export default class SubmitSearchRequest extends API {
    constructor(type = 'parallel-corpus', criteria, timeout = 2000) {
        super("POST", timeout, false);
        this.criteria = criteria;
        this.datasetType = type
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.submitSearchReq}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        return {
            userId: JSON.parse(localStorage.getItem('userDetails')).userID,
            datasetType: this.datasetType,
            criteria: {...this.criteria}
        }
    }

    getHeaders() {
        let urlSha = md5(JSON.stringify(this.getBody()))
        let hash = md5(this.userDetails.privateKey + "|" + urlSha)
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "key": this.userDetails.publicKey,
                "sig": hash,
                "payload": urlSha
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.credentials;
    }
}
