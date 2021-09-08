import C from '../../../actions/constants';

const initialState = {
    result: {
        "datasetType": [],
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
}

const getSearchOptions = (payload) => {
    let datasetTypeArray = Object.keys(payload.data);
    let datasetType = datasetTypeArray.map((type) => {
        return {
            label: payload.data[type].label,
            value: type
        }
    })

    let sourceLanguage = payload.data[datasetTypeArray[0]].filters[0].values.map(lang => {
        return {
            value: lang.value,
            label: lang.label
        }
    })

    return {
        "data": payload.data,
        "datasetType": datasetType,
        "languagePair": {
            "sourceLang": sourceLanguage,
            "targetLang": []
        },
        "scripts": ["Indus", "Brahmi", "Gupta", "Tamil", "Devanagari"],
        "filterBy": {
            "domain": [],
            "source": [],
            "collectionMethods": []
        }
    }
}

const getSearchFilter = (datasetType, prevState) => {
    let newState = Object.assign({}, JSON.parse(JSON.stringify(prevState)));
    newState["languagePair"]["sourceLang"] = newState["data"][datasetType]["filters"][0]['values'].map(value => {
        return {
            value: value.value,
            label: value.label
        }
    });
    return newState;

}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_SEARCH_OPTIONS:
            return {
                result: getSearchOptions(action.payload)
            }
        case C.GET_SEARCH_FILTERS:
            return {
                result: getSearchFilter(action.payload, state.result)
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;