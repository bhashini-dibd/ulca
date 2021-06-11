import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class SubmitSearchRequest extends API {
    constructor(type = 'parallel-corpus', tgt = [], src = null, domain = [], collectionMethod = [], timeout = 2000) {
        super("POST", timeout, false);
        this.tgt = tgt
        this.src = src
        this.domain = domain
        this.collectionMethod = collectionMethod
        this.datasettype = type
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.submitSearchReq}`;
      
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        return {
            type: this.datasettype,
            criterion: {
                sourceLanguage: this.src !== null ? this.src : this.tgt,
                targetLanguage: this.src === null ? null : this.tgt
            },
            groupby: {
                domain: this.domain,
                collectionMethod: this.collectionMethod
            }
        }
    }

    getHeaders() {
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "userId": "6491af71d71b4f1d9cff293522260838"
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.credentials;
    }
}
