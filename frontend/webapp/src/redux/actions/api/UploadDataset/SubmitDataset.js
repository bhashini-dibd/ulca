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
     this.endpoint = `${super.apiEndPoint()}${ENDPOINTS.datasetSubmit}`;
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
         "Content-Type": "application/json",
         "userId": "6491af71d71b4f1d9cff293522260838"
       }
     };
     return this.headers;
   }
 
   getPayload() {
     return this.credentials;
   }
 }
 