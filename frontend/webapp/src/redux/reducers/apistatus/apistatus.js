import C from '../../actions/constants';

const apistatus = (state = 0 , action) => {
    switch (action.type) {
        case C.APISTATUS:
            return action.payload;
        default:
            return state;
    }
}

export default apistatus;
