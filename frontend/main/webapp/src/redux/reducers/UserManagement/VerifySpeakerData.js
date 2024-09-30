import C from "../../actions/constants";

const initialState = {
  verifyData: [],
};

const reducer = (state = initialState, action) => {
 console.log(state,"shhh");
 
  switch (action.type) {
    case C.VERIFY_SPEAKER_DATA:
      return {
        ...state,
        verifyData: action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;