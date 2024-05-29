import C from "../../actions/constants";

const initialState = {
  glossaryData: [],
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_GLOSSARY_DATA:
      return {
        ...state,
        glossaryData: action.payload
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;