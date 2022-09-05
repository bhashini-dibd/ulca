import C from "../../../actions/constants";
import { Language } from "../../../../configs/DatasetItems";

const initialState = {
  asr: [],
  tts: [],
  translation: [],
  transliteration:[],
  sourceLanguage: [],
  targetLanguage: [],
};

const getLanguage = (asrArr, translationArr, ttsArr) => {
  const asrLang = [...new Set(asrArr.map((asr) => asr.sourceLanguage))];
  const ttsLang = [...new Set(ttsArr.map((tts) => tts.sourceLanguage))];
  const language = translationArr.filter((translation) => {
    return (
      asrLang.indexOf(translation.sourceLanguage) > -1 &&
      ttsLang.indexOf(translation.targetLanguage) > -1
    );
  });
  let sourceLanguage = [];
  let targetLanguage = [];
  language.forEach((lang) => {
    Language.forEach((elem) => {
      if (elem.value === lang.sourceLanguage) {
        sourceLanguage.push(elem);
      }
    });
  });
  language.forEach((lang) => {
    Language.forEach((elem) => {
      if (elem.value === lang.targetLanguage) {
        targetLanguage.push(elem);
      }
    });
  });
  return {
    sourceLanguage: getUniqueListBy(sourceLanguage, "value"),
    targetLanguage: getUniqueListBy(targetLanguage, "value"),
  };
};

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
    updatedObj["asr"],
    updatedObj["translation"],
    updatedObj["tts"],
    updatedObj[" transliteration"]
  ).sourceLanguage;

  updatedObj["targetLanguage"] = getLanguage(
    updatedObj["asr"],
    updatedObj["translation"],
    updatedObj["tts"],
    updatedObj[" transliteration"]
  ).targetLanguage;
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
