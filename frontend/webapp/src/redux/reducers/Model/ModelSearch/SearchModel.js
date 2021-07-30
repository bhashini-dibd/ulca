import C from '../../../actions/constants';

const initialState = {
    result: []
}

const getSearchOptions = (payload) => {
    return [
        {
           ...payload
        }

    ]
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.SUBMIT_MODEL_SEARCH:
            return {
                result: action.payload.data
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;