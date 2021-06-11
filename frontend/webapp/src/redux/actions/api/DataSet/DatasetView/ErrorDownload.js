import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class LoginAPI extends API {
  constructor(userId, timeout = 2000) {
    super("POST", timeout, false);
   this.userId = userId;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.errorReport}`;
  }


  apiEndPoint() {
    return this.endpoint;
  }

  processResponse(res) {
      debugger
    super.processResponse(res);
    if (res) {
        this.report = res;
    }
}

  getBody() {
    return {"serviceRequestNumber":this.userId}
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
    return this.credentials;
  }
}
