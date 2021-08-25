import C from '../../actions/constants';

const initialState = {
    type: "",
    optionSelected: ""
}

export default (state = initialState, action) => {
    switch (action.type) {
        case C.GET_MENU_TYPE:
            if (state.type !== action.payload) {
                return {
                    ...state,
                    type: action.payload,
                }
            }
            return {
                ...state,
                type: action.payload
            }
        case C.GET_MENU_OPTION:
            return {
                ...state,
                optionSelected: action.payload
            }

        default:
            return {
                ...state
            }
    }
}