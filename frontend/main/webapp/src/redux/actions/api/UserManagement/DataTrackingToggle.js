import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";
import C from "../../constants";

export default class DataTrackingToggleAPI extends API {
  constructor(ulcaApiKey,serviceProviderName,dataTracking,timeout = 2000) {
    super("POST", timeout, false);
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    this.ulcaApiKey = ulcaApiKey;
    this.serviceProviderName = serviceProviderName;
    this.dataTracking = dataTracking
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.dataTrackingToggle}`;
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
    return {
        userID: JSON.parse(localStorage.getItem("userDetails")).userID,
        ulcaApiKey: this.ulcaApiKey,
        serviceProviderName: this.serviceProviderName,
        dataTracking:this.dataTracking
    };
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
        "Content-Type": "application/json"
      },
    };

    return this.headers;
  }

  getPayload() {
    return this.report;
  }
}
