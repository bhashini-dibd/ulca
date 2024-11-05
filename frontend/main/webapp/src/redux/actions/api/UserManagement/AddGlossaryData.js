import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";
import C from "../../constants";

export default class AddGlossaryDataApi extends API {
  constructor(appName,serviceProviderName,formState,timeout = 2000) {
    super("POST", timeout, false);
    this.type = C.ADD_GLOSSARY_DATA;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    // this.userID = userID;
    this.appName = appName;
    this.serviceProviderName = serviceProviderName;
    this.formState = formState;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.addGlossaryData}`;
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
        // userID: JSON.parse(localStorage.getItem("userDetails")).userID,
        appName:this.appName,
        serviceProviderName: this.serviceProviderName,
        glossary:[{
          sourceLanguage:this.formState.sourceLanguage,
          targetLanguage:this.formState.targetLanguage,
          sourceText : this.formState.sourceText.trimEnd(),
          targetText : this.formState.targetText.trimEnd(),
        }
        ]
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
        "Content-Type": "application/json"
      },
    };

    return this.headers;
  }

  getPayload() {
    return this.report;
  }
}
