import C from '../../../actions/constants';

const initialState = {
    result: []
}

const getSearchOptions = (payload) => {
    return [
        {
            "languagePair": {
                "sourceLang": [],
                "targetLang": []
            },
            "scripts": ["Indus", "Brahmi", "Gupta", "Tamil", "Devanagari"],
            "filterBy": {
                "domain": [],
                "source": [],
                "collectionMethods": []
            }
        }

    ]
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_SEARCH_OPTIONS:
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