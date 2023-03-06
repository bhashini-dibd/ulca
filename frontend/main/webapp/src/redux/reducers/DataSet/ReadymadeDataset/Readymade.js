import C from '../../../actions/constants';

const initialState = {
    type: "parallel-corpus",
    source: "",
    target: ""
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.READYMADE_SEARCH_FILTER:
            return {
                ...action.payload
            }
        case C.INITIAL_READYMADE_FILTER:
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