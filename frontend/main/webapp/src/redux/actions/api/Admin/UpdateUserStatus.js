import API from "../../api";
import C from "../../constants";
import ENDPOINTS from "../../../../configs/apiendpoints";
import md5 from "md5";

export default class UpdateUserStatus extends API {
    constructor(email, status, timeout = 200000) {
        super("POST", timeout, false);
        this.type = C.UPDATE_USER_STATUS;
        this.userDetails = JSON.parse(localStorage.getItem("userInfo"));
        this.endpoint = `${super.apiEndPointAuto()}${ENDPOINTS.updateUserStatus}`;
        this.email = email;
        this.status = status;

    }

    toString() {
        return `${super.toString()} email: ${this.email} token: ${this.token
            } expires: ${this.expires} userid: ${this.userid}, type: ${this.type}`;
    }

    processResponse(res) {
        super.processResponse(res);
        if (res) {
            this.report = res.data;
        }
    }

    apiEndPoint() {
        return this.endpoint;
    }

    getBody() {
        return {
            email: this.email,
            is_active: this.status,
        };
    }

    getHeaders() {
        let res = this.apiEndPoint();
        let urlSha = md5(res);
        let hash = md5(this.userDetails.privateKey + "|" + urlSha);
        this.headers = {
            headers: {
                key: this.userDetails.publicKey,
                sig: hash,
                payload: urlSha,
                "Content-Type": "application/json",
            },
        };
        return this.headers;
    }

    getPayload() {
        return this.report;
    }
}
