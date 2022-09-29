import C from "../../../actions/constants";
import { getLanguageName } from "../../../../utils/getLabel";

const initialState = {
  result: [],
};

const parseDatasetMetricsReport = (payload) => {
  return payload.map((load) => {
    return {
      sourceLanguage: getLanguageName(load.sourceLanguage),
      targetLanguage: getLanguageName(load.targetLanguage),
      count: load.total,
      collectionMethod: load.collectionMethod_collectionDescriptions,
      domain: load.domains,
      submitterName: load.primarySubmitterName,
      datasetType: load.datasetType,
    };
  });
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_DATASET_METRICS:
      const result = parseDatasetMetricsReport(action.payload);
      return { ...state, result };
    default:
      return { ...state };
  }
};

export default reducer;
