import C from "../../actions/constants";

const initialState = {
  apiKeys: [],
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_API_KEYS:
      return {
        ...state,
        apiKeys: action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
