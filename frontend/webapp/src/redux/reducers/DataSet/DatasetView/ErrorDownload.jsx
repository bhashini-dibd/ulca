import C from '../../../actions/constants';

const initialState = {
    responseData: []
}





const reducer = (state = initialState, action) => {
    console.log(action.payload)
    switch (action.type) {
        case C.GET_ERROR_REPORT:
            debugger
            return  action.payload[0]
            
        case C.CLEAR_DETAILED_REPORT:
            return {
                ...initialState
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;