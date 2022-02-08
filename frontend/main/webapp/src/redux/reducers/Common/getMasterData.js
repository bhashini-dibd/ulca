import C from "../../actions/constants";

const initialState = {
  languages: [],
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MASTER_DATA:
      const { languages, inferenceEndpoints } = action.payload;
      return {
        languages,
        inferenceEndpoints,
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
