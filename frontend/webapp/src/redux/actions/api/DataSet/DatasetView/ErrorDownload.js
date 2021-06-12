import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class LoginAPI extends API {
  constructor(userId, timeout = 2000) {
    super("POST", timeout, false);
   this.userId = userId;
   this.type = C.GET_ERROR_REPORT;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.errorReport}`;
  }

  toString() {
    return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
}

  apiEndPoint() {
    return this.endpoint;
  }

  processResponse(res) {
    super.processResponse(res);
    if (res) {

        this.reportValue = res;
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
    return this.reportValue;
  }
}
