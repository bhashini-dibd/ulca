import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";
import C from "../../constants";

export default class AddSpeakerEnrollmentDataApi extends API {
  constructor(appName,serviceProviderName,base64Audio, base64Recording, url, inputValue,timeout = 2000) {
    console.log(base64Audio,"hell");
    
    super("POST", timeout, false);
    this.type = C.ENROLL_SPEAKER_DATA;
    this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
    // this.userID = userID;
    this.appName = appName;
    this.serviceProviderName = serviceProviderName;
    this.base64Audio = base64Audio;
    this.base64Recording = base64Recording;
    this.url = url;
    this.inputValue = inputValue;

    this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.enrollSpeakerData}`;
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
    const payload = {
      config: {
        serviceId: "bhashini/iitdharwad/speaker-enrollment",
        speakerName: this.inputValue,
        preProcessors: ["vad", "denoiser"],
      },
      audio: [],
    };
  
    if (this.base64Audio) {
      // If base64Recording exists, use it
      payload.audio.push({
        audioContent: this.base64Audio,
      });
    } else if (this.base64Recording) {
      // If base64Recording exists and base64Recording does not, use it
      payload.audio.push({
        audioContent: this.base64Recording.split("base64,")[1],
      });
    } else if (this.url.startsWith("http") || this.url.startsWith("https")) {
      // If the input is a URL, set the key to audioUri
      payload.audio.push({
        audioUri: this.url,
      });
    }
  
    return payload;
    // return {
    //     // userID: JSON.parse(localStorage.getItem("userDetails")).userID,
    //     appName:this.appName,
    //     serviceProviderName: this.serviceProviderName,
       
    // };
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
