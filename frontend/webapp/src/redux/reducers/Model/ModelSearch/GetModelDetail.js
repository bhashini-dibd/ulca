import C from "../../../actions/constants";
import { getLanguageName, FilterByDomain } from "../../../../utils/getLabel";

const getModelDetails = (payload) => {
  const target = payload["languages"][0]["targetLanguage"];
  const source = payload["languages"][0]["sourceLanguage"];
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
  ];
};

const reducer = (state, action) => {
  switch (action.type) {
    case C.GET_MODEL_DETAIL:
      return {
        result: getModelDetails(action.payload),
        modelName: `${action.payload.name} ${action.payload.version}`,
      };
    default:
      return {
        result: [],
        modelName: "",
      };
  }
};
export default reducer;
