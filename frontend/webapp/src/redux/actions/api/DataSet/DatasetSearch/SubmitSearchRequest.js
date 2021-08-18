import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from 'md5';

export default class SubmitSearchRequest extends API {
    constructor(type = 'parallel-corpus', tgt = [], src = null, domain = [], collectionMethod = [], originalSourceSentence, timeout = 2000) {
        super("POST", timeout, false);
        this.tgt = tgt
        this.src = src
        this.domain = domain
        this.collectionMethod = collectionMethod
        this.datasetType = type
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.submitSearchReq}`;
        this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
        this.originalSourceSentence = originalSourceSentence
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        return {
            userId: JSON.parse(localStorage.getItem('userDetails')).userID,
            datasetType: this.datasetType,
            criteria: {
                sourceLanguage: this.src !== null ? [this.src] : this.tgt,
                targetLanguage: this.src === null ? null : this.tgt,
                domain: this.domain && this.domain.length > 0 ? this.domain : null,
                collectionMethod: this.collectionMethod && this.collectionMethod.length > 0 ? this.collectionMethod : null,
                originalSourceSentence: this.originalSourceSentence
            },
            // groupby: []
        }
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
        return this.credentials;
    }
}
