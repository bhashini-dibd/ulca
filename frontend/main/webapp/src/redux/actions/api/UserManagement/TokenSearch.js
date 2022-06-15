/**
 * TokenSearch API
 */
 import API from "../../api";

 import ENDPOINTS from "../../../../configs/apiendpoints";
 
 export default class TokenSearch extends API {
   constructor(token, timeout = 2000) {
     super("POST", timeout, false);
    this.token = token;
     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.tokenSearch}`;
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {
     let apiParam = {
        "token":this.token}
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
 