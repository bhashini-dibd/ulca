import C from "../../actions/constants";

const initialState = {
  speakerData: [],
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.FETCH_SPEAKER_DATA:
      return {
        ...state,
        speakerData: action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;