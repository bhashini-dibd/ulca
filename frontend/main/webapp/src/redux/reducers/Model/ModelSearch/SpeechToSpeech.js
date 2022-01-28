import C from "../../../actions/constants";

const initialState = {
  asr: [],
  tts: [],
  translation: [],
};

const updateModelType = (data, prevState) => {
  const updatedObj = JSON.parse(JSON.stringify(prevState));
  data.forEach((elem) => {
    updatedObj[elem.task.type].push({
      value: elem.modelId,
      label: elem.name,
      inferenceEndPoint: elem.inferenceEndPoint,
    });
    updatedObj[elem.task.type] = getUniqueListBy(
      updatedObj[elem.task.type],
      "value"
    );
  });
  return updatedObj;
};

function getUniqueListBy(arr, key) {
  return [...new Map(arr.map((item) => [item[key], item])).values()];
}

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_BULK_MODEL_SEARCH:
      return {
        ...state,
        ...updateModelType(action.payload, state),
      };
    case C.CLEAR_BULK_MODEL_SEARCH:
      return {
        asr: [],
        tts: [],
        translation: [],
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
