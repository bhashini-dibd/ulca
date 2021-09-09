import C from '../../../actions/constants';

const initialState = {
    result: {
        "data":[],
        "datasetType": [],
        "languagePair": {
            "sourceLang": [],
        },
    },
    "basicFilter":[],
    "advFilter":[]
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

    // let basicFilter = payload.data[datasetTypeArray[0]].filters.map

    return {
        "data": payload.data,
        "datasetType": datasetType,
        "languagePair": {
            "sourceLang": sourceLanguage,
        },
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
                ...state,
                result: getSearchOptions(action.payload)
            }
        case C.GET_SEARCH_FILTERS:
            return {
                ...state,
                result: getSearchFilter(action.payload, state.result)
            }

        case C.GET_FILTER_CATEGORY:
            return{
                ...state,
                
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;