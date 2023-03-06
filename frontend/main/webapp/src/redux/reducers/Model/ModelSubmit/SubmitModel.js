import C from '../../../actions/constants';


const initialState = {
    modelId: null
}
const reducer = (state = initialState, action) => {
    
const getSearchOptions = (payload) => {
    
   return {modelId:payload}
}

    switch (action.type) {
       
        case C.SUBMIT_MODEL:
            return  getSearchOptions(action.payload.modelId)
        default:
            return state;
    }
}

export default reducer;

