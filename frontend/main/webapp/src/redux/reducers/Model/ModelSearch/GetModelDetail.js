import C from "../../../actions/constants";
import { getLanguageName, FilterByDomain } from "../../../../utils/getLabel";

const initialState = {
  result: [],
  modelName: "",
  task: "",
  source: "",
  inferenceEndPoint: "",
  submitter: "",
  language: "",
  metricArray: [],
  benchmarkPerformance: [],
  version: ""
};

const getModelDetails = (payload) => {
  const target = payload["languages"] && payload["languages"][0]["targetLanguage"];
  const source = payload["languages"] && payload["languages"][0]["sourceLanguage"];
  return [
    { title: "Source URL", para: payload["refUrl"] },
    { title: "Task", para: payload["task"]["type"] },
    {
      title: "Languages",
      para: target
        ? `${getLanguageName(source)}-${getLanguageName(target)}`
        : `${getLanguageName(source)}`,
    },
    { title: "Submitter", para: payload["submitter"]["name"] },
    { title: "Published On", para: payload["publishedOn"] },
    {
      title: "Training Dataset",
      para: payload["trainingDataset"]["description"],
    },
    { title: "Domain", para: FilterByDomain(payload["domain"])[0].label },
    { title: "Model Id", para: payload["modelId"] },
  ];
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MODEL_DETAIL:
      const target = action.payload["languages"]&& action.payload["languages"][0]["targetLanguage"];
      const source = action.payload["languages"] && action.payload["languages"][0]["sourceLanguage"];
      return {
        result: getModelDetails(action.payload),
        description: action.payload.description,
        modelName: `${action.payload.name} ${action.payload.version}`,
        task: action.payload["task"]["type"],
        source: action.payload["languages"] && action.payload["languages"][0]["sourceLanguage"],
        target: action.payload["languages"] && action.payload["languages"][0]["targetLanguage"],
        inferenceEndPoint: action.payload.inferenceEndPoint,
        submitter: action.payload.submitter.name,
        language: target
          ? `${getLanguageName(source)}-${getLanguageName(target)}`
          : `${getLanguageName(source)}`,
        benchmarkPerformance: action.payload.benchmarkPerformance,
        metricArray: action.payload.metric,
        version: action.payload.version
      };
    default:
      return {
        ...state,
      };
  }
};
export default reducer;
