/**
 * ForgotPassword API
 */
import API from "../../api";
import ENDPOINTS from "../../../../configs/apiendpoints";

export default class ForgotPassword extends API {
    constructor(email, timeout = 2000) {
        super("POST", timeout, false);
        this.email = email
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.forgotPassword}`;
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let apiParam = {
            "email": this.email
        }
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
