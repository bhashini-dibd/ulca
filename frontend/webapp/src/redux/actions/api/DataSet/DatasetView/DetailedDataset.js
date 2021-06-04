/**
 * User Event Report API
 */
 import API from "../../../api";
 import C from "../../../constants";
 import ENDPOINTS from "../../../../../configs/apiendpoints";
 import CONFIGS from "../../../../../configs/configs";

 export default class DeatailedDataset extends API {ENDOINTS
     constructor(action, jobId, uid, timeout = 200000) {
         super("POST", timeout, false);
         this.action = action
         this.jobId = jobId
         this.uid = uid
         this.type = C.GET_DETAILED_REPORT;
         this.endpoint = `${CONFIGS.API_URL}${ENDPOINTS.getContributionList}`;
     }
 
     toString() {
         return `${super.toString()} email: ${this.email} token: ${this.token} expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
     }
 
     processResponse(res) {
         super.processResponse(res);
         this.report = res
     }
 
     apiEndPoint() {
         return this.endpoint;
     }
 
     getBody() {
         return {
             "events.edata.action.keyword": this.action,
             "events.object.job_id.keyword": this.jobId,
             "events.actor.uid.keyword": this.uid
         }
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
         return this.report
     }
 
 }
 