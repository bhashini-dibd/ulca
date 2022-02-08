/**
 * Login API
 */
 import API from "../../api";
 import C from "../../constants";
 import CONFIGS from "../../../../configs/configs";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 
 export default class LoginAPI extends API {
   constructor(userLogin, timeout = 2000) {
     super("POST", timeout, false);
    this.credentials = userLogin;
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.login}`;
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {
     let apiParam = {"authenticator": "ULCA",
     "data": this.credentials}
     return apiParam;
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
 