/**
 * Login API
 */
 import API from "../../api";
 import C from "../../constants";
 import CONFIGS from "../../../../configs/configs";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 
 export default class RegisterAPI extends API {
   constructor(userLogin, timeout = 2000) {
     super("POST", timeout, false);
    this.details = userLogin;
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.register}`;
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {
     this.details.roles =  ["CONTRIBUTOR-USER"]
     let apiParam = {
        "users": [this.details]}
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
 