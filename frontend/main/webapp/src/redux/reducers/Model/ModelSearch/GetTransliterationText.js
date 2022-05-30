import C from "../../../actions/constants";

const initialState = {
  result: [],
  currentText: "",
  transliterationText: "",
};

export default (state = initialState, action) => {
  switch (action.type) {
    case C.GET_TRANSLITERATION_TEXT:
      const { result } = action.payload;
      return {
        ...state,
        result,
      };
    case C.SET_CURRENT_TEXT:
      const currentText =
        action.payload.indexOf(" ") > -1
          ? action.payload.split(" ")[action.payload.split(" ").length - 1]
          : action.payload;
      return {
        ...state,
        currentText,
      };
    case C.SET_TRANSLITERATION_TEXT:
      const prevTextArr = action.payload?.prevText.split("");
      prevTextArr.length > 0 ? prevTextArr.splice(action.payload?.startIndex, action.payload?.endIndex-action.payload?.startIndex, action.payload?.newWord) : prevTextArr.push(action.payload?.newWord);
      // prevTextArr.splice(prevTextArr.length - 1, 1);
      // prevTextArr.push(action.payload?.newWord);
      const transliterationText = prevTextArr.join("");

      return {
        ...state,
        transliterationText,
      };

    case C.CLEAR_TRANSLITERATION_RESULT:
      return {
        ...state,
        result: [],
      };
      
    default:
      return {
        ...state,
      };
  }
};