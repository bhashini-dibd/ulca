/**
 * Login API
 */
 import API from "../../api";
 import C from "../../constants";
 import CONFIGS from "../../../../configs/configs";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 import { sha256 } from 'js-sha256';

 export default class LoginAPI extends API {
   constructor(fileDetails, timeout = 2000) {
     super("POST", timeout, false);
    this.fileDetails = fileDetails;
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.datasetSubmit}`;
     this.userDetails = JSON.parse(localStorage.getItem('userInfo'))
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {
     return this.fileDetails;
   }
 
   getHeaders() {
    let urlSha = sha256(JSON.stringify(this.getBody()))
    this.headers = {
      headers: {
        "Content-Type": "application/json",
        "key" :this.userDetails.publicKey,
        "sig"  : sha256(this.userDetails.privateKey+"|"+urlSha),
        "userId": JSON.parse(localStorage.getItem('userDetails')).userID
      }
    };
    return this.headers;
  }
 
   getPayload() {
     return this.credentials;
   }
 }
 