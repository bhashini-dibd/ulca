import C from "../../../actions/constants";

const initialState = {
  benchmarkPerformance: [],
  metricArray: [],
};

const updateDate = (data) => {
  return data.map((value) => {
    value.createdOn = dateConversion(value.createdOn);
    return value;
  });
};

const getBenchmarkTableDetails = (data) => {
  if (data !== undefined) {
    data.benchmarkPerformance = updateDate(data.benchmarkPerformance);
    return {
      benchmarkPerformance: data.benchmarkPerformance,
      metricArray:data.metric
    };
  }
};

const dateConversion = (value) => {
  var myDate = new Date(value);
  return myDate.getTime();
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.BENCHMARK_TABLE:
      const data = getBenchmarkTableDetails(action.payload);
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
