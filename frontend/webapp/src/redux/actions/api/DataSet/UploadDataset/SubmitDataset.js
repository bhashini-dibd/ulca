/**
 * Login API
 */
 import API from "../../../api";
 import C from "../../../constants";
 import CONFIGS from "../../../../../configs/configs";
 import ENDPOINTS from "../../../../../configs/apiendpoints";
 import md5 from 'md5';

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
     let bodyData = this.fileDetails
     bodyData.userId =  JSON.parse(localStorage.getItem('userDetails')).userID
     return bodyData;
   }
 

   getHeaders() {
    let urlSha = md5(JSON.stringify(this.getBody()))
    let hash = md5(this.userDetails.privateKey+"|"+urlSha)
    this.headers = {
      headers: {
        "Content-Type": "application/json",
        "key" :this.userDetails.publicKey,
        "sig"  : hash
      }
    };
    return this.headers;
  }
   
 
   getPayload() {
     return this.credentials;
   }
 }
 