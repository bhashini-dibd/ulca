import C from '../../../actions/constants';

const initialState = {
    result: []
}

const getSearchOptions = (payload) => {
    return [
        {

            "sourceLang": [],
            "targetLang": [],

            // "filterBy": {
            //     "domain": [],
            //     "source": [],
            //     "collectionMethods": []
            // }
        }

    ]
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.SUBMIT_MODEL_SEARCH:
            return {
                result: getSearchOptions(action.payload.responseData)
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;