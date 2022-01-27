/**
 * SubmitDataset API
 */
import API from "../../../api";
import ENDPOINTS from "../../../../../configs/apiendpoints";
import md5 from "md5";

export default class SubmitDatasetAPI extends API {
  constructor(fileDetails, isChecked, timeout = 2000) {
    super("POST", timeout, false);
    this.fileDetails = fileDetails;
    this.isChecked = isChecked;
    this.endpoint = `${super.apiEndPointAuto()}${
      isChecked ? ENDPOINTS.datasetBenchmarkSubmit : ENDPOINTS.datasetSubmit
    }`;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
  }

  apiEndPoint() {
    return this.endpoint;
  }

  getBody() {
    let bodyData = {
      datasetName: this.fileDetails.name,
      url: this.fileDetails.url.trim(),
    };
    bodyData.userId = JSON.parse(localStorage.getItem("userDetails")).userID;
    return bodyData;
  }

  getHeaders() {
    let urlSha = md5(JSON.stringify(this.getBody()));
    let hash = md5(this.userDetails.privateKey + "|" + urlSha);
    this.headers = {
      headers: {
        "Content-Type": "application/json",
        key: this.userDetails.publicKey,
        sig: hash,
        payload: urlSha,
      },
    };
    return this.headers;
  }

  getPayload() {
    return this.credentials;
  }
}
