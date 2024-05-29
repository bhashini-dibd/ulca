import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";
import C from "../../constants";

export default class DeleteGlossaryApi extends API {
  constructor(appName,serviceProviderName,sourceLanguage, targetLanguage, sourceText, targetText,timeout = 2000) {
    console.log(sourceLanguage, targetLanguage, sourceText, targetText,"tabb");
    super("POST", timeout, false);
    this.type = C.DELETE_GLOSSARY_DATA;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    // this.userID = userID;
    this.appName = appName;
    this.serviceProviderName = serviceProviderName;
    this.sourceLanguage = sourceLanguage;
    this.targetLanguage = targetLanguage;
    this.sourceText = sourceText;
    this.targetText = targetText;
    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.deleteGlossaryData}`;
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
        glossary:[
          {
            sourceText : this.sourceLanguage ,
            targetText: this.targetLanguage ,
            sourceLanguage: this.sourceText ,
            targetLanguage : this.targetText ,

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
