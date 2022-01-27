import { getLanguageName } from "../../../../utils/getLabel";
import C from "../../../actions/constants";

const initialState = {
  benchmarkPerformance: [],
  benchmarkId: "",
  name: "",
  description: "",
  metric: null,
  dataset: "",
  domain: [],
  task: "",
  languages: [],
  createdOn: null,
  submittedOn: null,
  metricArray: [],
};

const addPositions = (data) => {
  const keys = Object.keys(data);
  keys.forEach((key) => {
    data[key].forEach((val, i) => {
      val["position"] = i + 1;
    });
  });

  return data;
};

const updateBenchmarkPerformance = (performanceData) => {
  let obj = {};
  performanceData.forEach((data) => {
    if (obj[data.metric] === undefined) {
      obj[data.metric] = [data];
    } else {
      obj[data.metric].push(data);
    }
  });
  let resultObj = addPositions(obj);
  return resultObj;
};

const getBenchmarkDetails = (data) => {
  return {
    description: data.description,
    refUrl: data.dataset,
    language:
      data.languages && data.languages.targetLanguage !== null
        ? `${getLanguageName(
            data.languages.sourceLanguage
          )} - ${getLanguageName(data.languages.targetLanguage)}`
        : getLanguageName(data.languages.sourceLanguage),
    domain: data.domain ? data.domain.join(", ") : "",
    modelName: `${data.name}`,
    metric: data.metric ? data.metric.join(", ") : "",
    task: data.task.type,
    metricArray: data.metric,
    benchmarkPerformance: updateBenchmarkPerformance(data.benchmarkPerformance),
    submitter: data.submitter !== null ? data.submitter.name : "-",
  };
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_BENCHMARK_DETAILS:
      const data = getBenchmarkDetails(action.payload);
      return {
        ...data,
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
