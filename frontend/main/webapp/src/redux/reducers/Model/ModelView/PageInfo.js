import C from '../../../actions/constants';

const initialState = {
        page:0,
        count:10
    }

const reducer = (state = initialState, action) => {
    switch (action.type) {
        
        
            case C.MODEL_PAGE_CHANGE:{
                let result = state;
                result.page = action.payload;
                return result;
            }
            case C.MODEL_ROW_COUNT_CHANGE:{
                let result = state;
                result.count = action.payload;
                return result;
            }
            case C.CLEAR_COUNT:return {
                ...initialState
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;