import C from '../../../constants';

const action = (updatedState) => {
    return {
        type: C.READYMADE_SEARCH_FILTER,
        payload: updatedState
    }
};

export const initialSearchFilter = () => {
    return {
        type: C.INITIAL_READYMADE_FILTER
    }
};

export default action;