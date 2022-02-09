import C from "../../constants";

export default function logOut(){
    return {
        type: C.LOGOUT,
        payload: {
        }
    }
}