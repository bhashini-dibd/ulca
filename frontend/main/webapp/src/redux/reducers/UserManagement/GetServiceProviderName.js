import C from "../../actions/constants";

const initialState = {
    serviceProviders:[]
};

const getServiceProviderName = (state=initialState, action) =>{
    switch(action.type){
        case C.GET_SERVICE_PROVIDER_NAME:
            return {
                ...state,
                serviceProviders: action.payload
            }
        default:
            return {
                ...state
            }
    }
};

export default getServiceProviderName;