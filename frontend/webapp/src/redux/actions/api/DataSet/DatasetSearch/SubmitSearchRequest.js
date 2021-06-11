import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class SubmitSearchRequest extends API {
    constructor(type = 'parallel-corpus', tgt = [], src = null, domain = [], collectionMethod = [], timeout = 2000) {
        super("POST", timeout, false);
        this.tgt = tgt
        this.src = src
        this.domain = domain
        this.collectionMethod = collectionMethod
        this.datasetType = type
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.submitSearchReq}`;
      
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        return {
            datasetType: this.datasetType,
            criteria: {
                sourceLanguage: this.src !== null ? this.src : this.tgt,
                targetLanguage: this.src === null ? null : this.tgt,
                domain: this.domain,
                collectionMethod: this.collectionMethod
            },
            // groupby: []
        }
    }

    getHeaders() {
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "userId": JSON.parse(localStorage.getItem('userDetails')).userID
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.credentials;
    }
}
