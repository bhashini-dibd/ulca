import C from "../../actions/constants";

const initialState = {
  enrolledData: [],
};

const reducer = (state = initialState, action) => {
 console.log(state,"shhh");
 
  switch (action.type) {
    case C.ENROLL_SPEAKER_DATA:
      return {
        ...state,
        enrolledData: action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;