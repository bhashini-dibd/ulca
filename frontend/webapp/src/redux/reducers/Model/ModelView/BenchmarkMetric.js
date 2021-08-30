import C from "../../../actions/constants";

const initialState = {
  result: [],
};

const getBenchmarkMetric = () => {
  const result = {
    result: [
      {
        metric: "M1",
        description: "Lorem Ipsum",
      },
      {
        metric: "M2",
        description: "Lorem Ipsum",
      },
      {
        metric: "M3",
        description: "Lorem Ipsum",
      },
    ],
  };
  return result;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.BENCHMARK_METRIC:
      return {
        ...getBenchmarkMetric(),
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
