/**
 * ResetPassword API
 */
import API from "../../api";

import ENDPOINTS from "../../../../configs/apiendpoints";

export default class ResetPassword extends API {
    constructor(userName, password, xUserId, timeout = 2000) {
        super("POST", timeout, false);
        this.userName = userName;
        this.password = password;
        this.xUserId = xUserId
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.resetPassword}`;
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let apiParam = {
            "userName": this.userName,
            "password":this.password
        }
        return apiParam;
    }

    getHeaders() {
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "x-user-id":this.xUserId
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.credentials;
    }
}
