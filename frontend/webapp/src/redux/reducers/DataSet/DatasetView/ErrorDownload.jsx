import C from '../../../actions/constants';

const initialState = {
    responseData: []
}





const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_ERROR_REPORT:
            return  action.payload.length> 0 ?  action.payload[0] :[]
            
        case C.CLEAR_DETAILED_REPORT:
            return {
                ...initialState
            }
            case C.PAGE_CHANGE:
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