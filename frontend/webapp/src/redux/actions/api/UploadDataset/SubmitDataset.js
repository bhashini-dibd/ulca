/**
 * Login API
 */
 import API from "../../api";
 import C from "../../constants";
 import CONFIGS from "../../../../configs/configs";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 
 export default class LoginAPI extends API {
   constructor(fileDetails, timeout = 2000) {
     super("POST", timeout, false);
    this.fileDetails = fileDetails;
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.datasetSubmit}`;
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {
     return this.fileDetails;
   }
 
   getHeaders() {
     this.headers = {
       headers: {
         "Content-Type": "application/json"
       }
     };
     return this.headers;
   }
 
   getPayload() {
     return this.credentials;
   }
 }
 