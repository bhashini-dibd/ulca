import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";
import C from "../../constants";

export default class FetchGlossaryDetails extends API {
  constructor(appName,serviceProviderName,timeout = 2000) {
    super("GET", timeout, false);
    this.type = C.GET_GLOSSARY_DATA;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getGlossaryData}?appName=${appName}&serviceProviderName=${serviceProviderName}`;
  }

  processResponse(res) {
    super.processResponse(res);
    if (res) {
      this.report = res.glossary;
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
        "Content-Type": "application/json"
      },
    };

    return this.headers;
  }

  getPayload() {
    return this.report;
  }
}
