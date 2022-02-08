import C from '../../../actions/constants';

const initialState = {
    type: "translation",
    source: "",
    target: ""
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.UPDATE_BENCHMARK_FILTER:
            return {
                ...action.payload
            }
        case C.INITIAL_BENCHMARK_FILTER:
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