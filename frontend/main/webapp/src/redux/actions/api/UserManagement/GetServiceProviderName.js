
 import API from "../../api";
 import C from "../../constants";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 import md5 from 'md5';
 
 export default class GetServiceProviderNameAPI extends API {
   constructor(timeout = 2000) {
     super("GET", timeout, false);
     this.type = C.GET_SERVICE_PROVIDER_NAME;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getServiceProviderName}?serviceProviderName=true`;
   }
 
   toString() {
    return `${super.toString()} email: ${this.email} token: ${this.token
        } expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
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
}

getHeaders() {
    let res = this.apiEndPoint();
    let urlSha = md5(res);
    let hash = md5(this.userDetails.privateKey + "|" + urlSha);
    this.headers = {
        headers: {
            key: this.userDetails.publicKey,
            sig: hash,
            payload: urlSha,
            "Content-Type": "application/json",
        },
    };
    return this.headers;
}

getPayload() {
    return this.report;
}
}
 