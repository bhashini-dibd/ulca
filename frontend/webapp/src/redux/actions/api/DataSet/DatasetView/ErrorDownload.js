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
    super.processResponse(res);
    if (res) {
        this.report = [
          {
              "file": "http://ulca-datasets.s3.amazonaws.com/datasets/srn-search-1-ds.json",
              "id": "506201c1-b066-4f1b-924c-3ac71a684491",
              "lastModifiedTime": "2021-06-11 15:05:12.752379",
              "serviceRequestNumber": "srn-submit-p-ds-2",
              "startTime": "2021-06-11 15:04:09.278500",
              "status": "inprogress"
          }
      ];
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
