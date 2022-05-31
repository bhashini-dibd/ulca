import C from '../../../actions/constants';

const initialState = {
        page:0,
        count:10
    }

const reducer = (state = initialState, action) => {
    switch (action.type) {
        
        
            case C.SEARCH_PAGE_NO:{
                let result = state;
                result.page = action.payload;
                return result;
            }
            case C.SEARCH_ROW_COUNT_CHANGE:{
                let result = state;
                result.count = action.payload;
                return result;
            }
            case C.SEARCH_CLEAR_COUNT:return {
                ...initialState
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;