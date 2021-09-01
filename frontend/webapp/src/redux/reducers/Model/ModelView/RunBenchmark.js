import C from "../../../actions/constants";

const initialState = {
  result: [],
  selectedIndex: [],
  benchmarkInfo: [],
  filteredData: [],
  count: 0,
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
    payload.benchmark.forEach((dataset, i) => {
      result.push({
        datasetName: dataset.name + i,
        description: dataset.description === null ? "" : dataset.description,
        domain: dataset.domain.join(","),
        metric,
        selected: false,
        benchmarkId: dataset.benchmarkId,
      });
    });
  }
  return result;
};

const getUpdatedBenchMark = (type, prevState, index, parentIndex = "") => {
  let result = Object.assign([], JSON.parse(JSON.stringify(prevState)));
  if (type === "DATASET") {
    result.result[index].selected = !result.result[index].selected;
    result.filteredData[index].selected = !result.filteredData[index].selected;
    if (result.selectedIndex.indexOf(index) > -1) {
      result.result[index].metric.forEach((val) => {
        val.selected = false;
      });
      result.filteredData[index].metric.forEach((val) => {
        val.selected = false;
      });
      result.benchmarkInfo.splice(result.selectedIndex.indexOf(index), 1);
      result.selectedIndex.splice(result.selectedIndex.indexOf(index), 1);
    } else {
      result.selectedIndex.push(index);
    }
    return result;
  } else {
    result.result[parentIndex].metric[index].selected =
      !result.result[parentIndex].metric[index].selected;
    result.filteredData[parentIndex].metric[index].selected =
      !result.filteredData[parentIndex].metric[index].selected;
    let updatedBenchmarkInfo = [];
    result.result.forEach((val) => {
      if (val.selected) {
        val.metric.forEach((e) => {
          if (e.selected) {
            updatedBenchmarkInfo.push({
              benchmarkId: val.benchmarkId,
              metric: e.metricName,
            });
          }
        });
      }
    });
    result.benchmarkInfo = updatedBenchmarkInfo;
    return result;
  }
};

const getFilteredData = (payload, searchValue) => {
  let filteredData = payload.filter((dataset) =>
    dataset.datasetName.toLowerCase().includes(searchValue.toLowerCase())
  );
  return filteredData;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.RUN_BENCHMARK:
      return {
        ...state,
        result: getBenchmarkDetails(action.payload),
        filteredData: getBenchmarkDetails(action.payload),
        count: action.payload.count,
        selectedIndex: [],
      };
    case C.SELECT_DATASET:
      return {
        ...state,
        result: getUpdatedBenchMark("DATASET", state, action.payload.index)
          .result,
        filteredData: getUpdatedBenchMark(
          "DATASET",
          state,
          action.payload.index
        ).filteredData,
        selectedIndex: getUpdatedBenchMark(
          "DATASET",
          state,
          action.payload.index
        ).selectedIndex,
      };
    case C.SELECT_METRIC:
      return {
        ...state,
        ...getUpdatedBenchMark(
          "METRIC",
          state,
          action.payload.index,
          action.payload.parentIndex
        )
      };
    case C.CLEAR_BENCHMARK: {
      return {
        ...initialState,
      };
    }
    case C.SEARCH_BENCHMARK: {
      getFilteredData(state.result, action.payload);
      return {
        ...state,
        filteredData: getFilteredData(state.result, action.payload),
      };
    }
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
