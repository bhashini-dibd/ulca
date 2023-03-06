import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";

export default class FetchApiKeysAPI extends API {
  constructor(timeout = 2000) {
    super("POST", timeout, false);
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getApiKeys}`;
  }

  apiEndPoint() {
    return this.endpoint;
  }

  getBody() {
    return {
      userID: JSON.parse(localStorage.getItem("userDetails")).userID,
    };
  }

  getHeaders() {
    return {
      headers: {
        "Content-Type": "application/json",
      },
    };
  }

  getPayload() {
    return this.credentials;
  }
}
