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
};

const getBenchmarkDetails = (data) => {
  return {
    description: data.description,
    refUrl: data.dataset,
    language: `${data.languages[0].sourceLanguage} - ${data.languages[0].targetLanguage}`,
    domain: data.domain ? data.domain.join(", ") : "",
    modelName: data.name,
    metric: data.metric ? data.metric.join(", ") : "",
    task: data.task.type,
    metricArray: data.metric,
    benchmarkPerformance: data.benchmarkPerformance,
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
