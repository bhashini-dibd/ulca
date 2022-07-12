import C from '../../../actions/constants';

const initialState = {
        view:false
    }

const reducer = (state = initialState, action) => {
    switch (action.type) {
        
        
            case C.CONTRIBUTION_TABLE_VIEW:{
                
                return {view: action.payload.token};
            }
           
        default:
            return {
                ...state
            }
    }
}

export default reducer;