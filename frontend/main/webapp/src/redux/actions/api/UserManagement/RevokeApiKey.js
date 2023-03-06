import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";

export default class RevokeApiKeyAPI extends API {
  constructor(ulcaApiKey, timeout = 2000) {
    super("POST", timeout, false);
    this.ulcaApiKey = ulcaApiKey;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.revokeApiKey}`;
  }

  apiEndPoint() {
    return this.endpoint;
  }

  getBody() {
    return {
      userID: JSON.parse(localStorage.getItem("userDetails")).userID,
      ulcaApiKey: this.ulcaApiKey,
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
      },
    };

    return this.headers;
  }

  getPayload() {
    return this.credentials;
  }
}
