import API from "../../api";
import C from "../../constants";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";

export default class GetMasterData extends API {
  constructor(masterNamesArr, timeout = 200000) {
    super("POST", timeout, false);
    this.type = C.GET_MASTER_DATA;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getMasterData}`;
    this.masterNamesArr = masterNamesArr;
  }

  toString() {
    return `${super.toString()} email: ${this.email} token: ${
      this.token
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
    return {
      masterNames: this.masterNamesArr,
    };
  }

  getHeaders() {
    this.headers = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    return this.headers;
  }

  getPayload() {
    return this.report;
  }
}
