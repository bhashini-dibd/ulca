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
    let datasetName = ["parallel", "monolingual", "ASR", "OCR", "AS1"]
    let datasetType = datasetTypeArray.map((type, i) => {
        return {
            label: datasetName[i],
            // label: payload[type].label,
            value: datasetTypeArray[i].replace('dataset', 'corpus')
        }
    })

    return {
        "datasetType": datasetType,
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

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case C.GET_SEARCH_OPTIONS:
            return {
                result: getSearchOptions(action.payload)
            }
        default:
            return {
                ...state
            }
    }
}

export default reducer;