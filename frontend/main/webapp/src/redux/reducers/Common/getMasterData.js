import C from "../../actions/constants";

const initialState = {
  languages: [],
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MASTER_DATA:
      return {
        ...state,
        ...action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
