import C from "../../../actions/constants";

const initialState = {
  result: [],
  selectedIndex: [],
};

const getBenchmarkDetails = (payload) => {
  let result = [];
  if (payload.count) {
    let metric = payload.metric.map((value) => {
      return {
        metricName: value,
        selected: false,
      };
    });
    payload.benchmark.forEach((dataset) => {
      result.push({
        datasetName: dataset.name,
        description: dataset.description === null ? "" : dataset.description,
        domain: dataset.domain.join(","),
        metric,
        selected: false,
      });
    });
  }
  return result;
};

const getUpdatedBenchMark = (type, prevState, index, parentIndex = "") => {
  let result = Object.assign([], JSON.parse(JSON.stringify(prevState)));
  if (type === "DATASET") {
    result.result[index].selected = !result.result[index].selected;
    if (result.selectedIndex.indexOf(index) > -1) {
      result.result[index].metric.forEach((val) => {
        val.selected = false;
      });
      result.selectedIndex.splice(result.selectedIndex.indexOf(index), 1);
    } else {
      result.selectedIndex.push(index);
    }
    return result;
  } else {
    console.log(index, parentIndex, result.result);
    result.result[parentIndex].metric[index].selected =
      !result.result[parentIndex].metric[index].selected;
    return result;
  }
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.RUN_BENCHMARK:
      return {
        result: getBenchmarkDetails(action.payload),
        selectedIndex: [],
      };
    case C.SELECT_DATASET:
      return {
        ...state,
        ...getUpdatedBenchMark("DATASET", state, action.payload.index),
      };
    case C.SELECT_METRIC:
      return {
        ...state,
        ...getUpdatedBenchMark(
          "METRIC",
          state,
          action.payload.index,
          action.payload.parentIndex
        ),
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
