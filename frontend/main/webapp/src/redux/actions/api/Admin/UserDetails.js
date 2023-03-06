import API from "../../api";
import C from "../../constants";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";

export default class UserDetails extends API {
  constructor(
    userIDs = [],
    emails = [],
    roleCodes = [],
    orgCodes = [],
    offset = "",
    limit = "",
    skip_pagination = true,
    timeout = 200000
  ) {
    super("POST", timeout, false);
    this.type = C.GET_USER_DETAILS;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.getUserDetails}`;
    this.userIDs = userIDs;
    this.emails = emails;
    this.roleCodes = roleCodes;
    this.orgCodes = orgCodes;
    this.offset = offset;
    this.limit = limit;
    this.skip_pagination = skip_pagination;
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
    const {
      userIDs,
      emails,
      roleCodes,
      orgCodes,
      offset,
      limit,
      skip_pagination,
    } = this;
    return {
      userIDs,
      emails,
      roleCodes,
      orgCodes,
      offset,
      limit,
      skip_pagination,
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
        "Content-Type": "application/json",
      },
    };
    return this.headers;
  }

  getPayload() {
    return this.report;
  }
}
