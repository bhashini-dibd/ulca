import C from "../../../actions/constants";

const initialState = [
  {
    datasetName: "D1",
    domain: "Legal",
    description: "Lorem Ipsum",
  },
  {
    datasetName: "D2",
    domain: "Legal",
    description: "Lorem Ipsum",
  },
  {
    datasetName: "D3",
    domain: "Legal",
    description: "Lorem Ipsum",
  },
];

const getBenchmarkDetails = () => {
  return initialState;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.RUN_BENCHMARK:
      return {
        result: getBenchmarkDetails(),
      };
    default:
      return {
        result: state,
      };
  }
};

export default reducer;
