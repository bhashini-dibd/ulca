import C from "../../../actions/constants";

const initialState = {
  result: [],
};

const getBenchmarkDetails = (payload) => {
  let result = [];
  if (payload.count) {
    payload.benchmark.forEach((dataset) => {
      result.push({
        datasetName: dataset.name,
        description: dataset.description === null ? "" : dataset.description,
        domain: dataset.domain.join(","),
        metric: payload.metric,
      });
    });
  }
  return result;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.RUN_BENCHMARK:
      return {
        result: getBenchmarkDetails(action.payload),
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
