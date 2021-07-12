
/**
 * ResetPassword API
 */
import API from "../../api";

import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from 'md5';

export default class ResetPassword extends API {
    constructor(userName, password, publicKey, privateKey, timeout = 2000) {
        super("POST", timeout, false);
        this.userName = userName;
        this.password = password;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.resetPassword}`;
    }


    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        let apiParam = {
            "userName": this.userName,
            "password": this.password
        }
        return apiParam;
    }

    getHeaders() {
        let urlSha = md5(JSON.stringify(this.getBody()))
        let hash = md5(this.privateKey + "|" + urlSha)
        this.headers = {
            headers: {
                "Content-Type": "application/json",
                "key": this.publicKey,
                "sig": hash
            }
        };
        return this.headers;
    }

    getPayload() {
        return this.credentials;
    }
}
