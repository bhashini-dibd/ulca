import C from '../../../actions/constants';

const initialState = {
    type: "sts",
    source: "",
    target: ""
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.UPDATE_SEARCH_FILTER:
            return {
                ...action.payload
            }
        case C.INITIAL_SEARCH_FILTER:
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