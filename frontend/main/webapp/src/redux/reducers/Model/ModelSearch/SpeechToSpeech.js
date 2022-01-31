import C from "../../../actions/constants";
import { Language } from "../../../../configs/DatasetItems";

const initialState = {
  asr: [],
  tts: [],
  translation: [],
  sourceLanguage: [],
  targetLanguage: [],
};

const getLanguage = (prop, asrArr, translationArr, ttsArr) => {
  let src = [];
  asrArr.forEach((asr) =>
    Language.forEach((lang) => {
      if (asr[prop] === lang.value) {
        src.push(lang);
      }
    })
  );
  translationArr.forEach((asr) =>
    Language.forEach((lang) => {
      if (asr[prop] === lang.value) {
        src.push(lang);
      }
    })
  );
  ttsArr.forEach((asr) =>
    Language.forEach((lang) => {
      if (asr[prop] === lang.value) {
        src.push(lang);
      }
    })
  );
  return getUniqueListBy(src, "value");
};
const getTargetLanguage = (tgtArr, asr, translation, tts) => {};

const updateModelType = (data, prevState) => {
  const updatedObj = JSON.parse(JSON.stringify(prevState));
  data.forEach((elem) => {
    updatedObj[elem.task.type].push({
      value: elem.modelId,
      label: elem.name,
      inferenceEndPoint: elem.inferenceEndPoint,
      sourceLanguage:
        elem.languages[0] && elem.languages[0].hasOwnProperty("sourceLanguage")
          ? elem.languages[0].sourceLanguage
          : "",
      targetLanguage:
        elem.languages[0] && elem.languages[0].hasOwnProperty("targetLanguage")
          ? elem.languages[0].targetLanguage
          : "",
    });
    updatedObj[elem.task.type] = getUniqueListBy(
      updatedObj[elem.task.type],
      "value"
    );
  });
  updatedObj["sourceLanguage"] = getLanguage(
    "sourceLanguage",
    updatedObj["asr"],
    updatedObj["translation"],
    updatedObj["tts"]
  );
  updatedObj["targetLanguage"] = getLanguage(
    "targetLanguage",
    updatedObj["asr"],
    updatedObj["translation"],
    updatedObj["tts"]
  );
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
        sourceLanguage: [],
        targetLanguage: [],
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
