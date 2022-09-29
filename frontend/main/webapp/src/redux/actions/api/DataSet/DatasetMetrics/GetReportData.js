import API from "../../../api";
import C from "../../../constants";
import ENDPOINTS from "../../../../../configs/apiendpoints";

export default class GetReportData extends API {
  constructor(timeout = 200000) {
    super("POST", timeout, false);
    this.type = C.GET_DATASET_METRICS;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.datasetMetrics}`;
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
    return {};
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
