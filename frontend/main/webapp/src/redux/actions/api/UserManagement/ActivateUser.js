/**
 * Login API
 */
 import API from "../../api";
 import ENDPOINTS from "../../../../configs/apiendpoints";
 
 export default class LoginAPI extends API {
   constructor(email, userID, timeout = 2000) {
     super("POST", timeout, false);
    this.email = email;
    this.userID = userID;

     this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.activateUser}`;
   }
 
 
   apiEndPoint() {
     return this.endpoint;
   }
 
   getBody() {

    let apiParam = {"email":this.email, "userID":this.userID }
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
 