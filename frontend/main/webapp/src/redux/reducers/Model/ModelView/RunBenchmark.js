import C from "../../../actions/constants";

const initialState = {
  result: [],
  selectedIndex: [],
  benchmarkInfo: [],
  filteredData: [],
  availableFilters: [],
  count: 0,
  status: "progress",
  submitStatus: false,
};

const getMetric = (metricData = [], availableMetric = []) => {
  let metric = metricData.map((value) => {
    return {
      metricName: value,
      selected: false,
      isMetricDisabled: availableMetric.indexOf(value) > -1 ? false : true,
    };
  });
  return metric;
};

const getBenchmarkDetails = (payload) => {
  let result = [];
  if (payload.count) {
    payload.benchmark.forEach((dataset, i) => {
      result.push({
        datasetName: dataset.name,
        description: dataset.description === null ? "" : dataset.description,
        domain: dataset.domain.join(","),
        metric: getMetric(dataset.metric, dataset.availableMetric),
        // metric: getMetric(["bleu", "sacrebleu"], ["bleu"]),
        selected: false,
        benchmarkId: dataset.benchmarkId,
      });
    });
  }
  return result;
};

const getIndex = (data, benchmarkId) => {
  let index = 0;
  data.forEach((val, i) => {
    if (val.benchmarkId === benchmarkId) {
      index = i;
    }
  });
  return index;
};

const getUpdatedBenchMark = (type, prevState, index, parentIndex = "") => {
  let result = Object.assign([], JSON.parse(JSON.stringify(prevState)));

  if (type === "DATASET") {
    index = getIndex(result.result, index);

    result.result[index].selected = !result.result[index].selected;
    result.filteredData[parentIndex].selected = !result.filteredData[parentIndex].selected;
    
    if (result.selectedIndex.indexOf(parentIndex) > -1) {  //if its already selected
      result.result[index].metric.forEach((val) => {
        val.selected = false;
      });

      result.filteredData[parentIndex].metric.forEach((val) => {
        val.selected = false;
      });
      
      result.benchmarkInfo.splice(result.selectedIndex.indexOf(index), 1);
      result.selectedIndex.splice(result.selectedIndex.indexOf(index), 1);
    } else {
      result.selectedIndex = [parentIndex];

      result.filteredData.forEach((element, index) => {
        if(index !== parentIndex) {
          element.selected = false;
        }
      })

      result.result.forEach((element, index) => {
        if(index !== parentIndex) {
          element.selected = false;
        }
      })
    }
  } else {
    result.result[parentIndex].metric[index].selected = !result.result[parentIndex].metric[index].selected;
    result.filteredData[parentIndex].metric[index].selected = !result.filteredData[parentIndex].metric[index].selected;
  }

  let updatedBenchmarkInfo = [];
  
  result.result = result.result.map((data) => {
    let updatedData = {};
    
    result.filteredData.forEach((value) => {
      if (value.benchmarkId === data.benchmarkId) {
        updatedData = Object.assign({}, JSON.parse(JSON.stringify(value)));
      } else {
        updatedData = Object.assign({}, JSON.parse(JSON.stringify(data)));
      }
    });
    
    return updatedData;
  });
  
  result.result.forEach((val) => {
    if (val.selected) {
      updatedBenchmarkInfo.push({ benchmarkId: val.benchmarkId });
      
      val.metric.forEach((e) => {
        if (e.selected) {
          if (
            updatedBenchmarkInfo[updatedBenchmarkInfo.length - 1].metric ===
            undefined
          ) {
            updatedBenchmarkInfo[updatedBenchmarkInfo.length - 1].metric =
              e.metricName;
          } else {
            updatedBenchmarkInfo.push({
              benchmarkId: val.benchmarkId,
              metric: e.metricName,
            });
          }
        }
      });
    }
  });
  
  result.benchmarkInfo = updatedBenchmarkInfo;
  result.submitStatus = updatedBenchmarkInfo.length > 0 ? true : false;
  return result;
};

const getSubmitStatus = (benchmarkInfo) => {
  let benchmarkObj = {};
  benchmarkInfo.forEach((data) => {
    if (benchmarkObj.hasOwnProperty(data.benchmarkId)) {
      benchmarkObj[data.benchmarkId].push(data.metric);
    } else {
      benchmarkObj[data.benchmarkId] = data.metric ? [data.metric] : "";
    }
  });
  let values = [...Object.values(benchmarkObj)];
  if (values.length) {
    for (let i = 0; i < values.length; i++) {
      if (!values[i].length) {
        return true;
      }
    }
    return false;
  }
  return true;
};

const getFilteredData = (payload, searchValue) => {
  let filteredData = payload.filter((dataset) =>
    dataset.datasetName.toLowerCase().includes(searchValue.toLowerCase())
  );
  let newSelectedIndex = [];
  filteredData.forEach((data, i) => {
    if (data.selected) {
      newSelectedIndex.push(i);
    }
  });
  return { filteredData, selectedIndex: newSelectedIndex };
};

const getAvailableFilters = (payload) => {
  let availableFilters = [];
  payload.benchmark.forEach((data) => {
    data.domain.forEach((val) => availableFilters.push(val));
  });
  let uniqueFilters = [];
  for (let i = 0; i < availableFilters.length; i++) {
    if (uniqueFilters.indexOf(availableFilters[i]) < 0) {
      uniqueFilters.push(availableFilters[i]);
    }
  }
  return uniqueFilters;
};

const filterByDomain = (prevState, payload) => {
  let filteredDomain = [];
  let newState = Object.assign({}, JSON.parse(JSON.stringify(prevState)));
  prevState.result.forEach((result) => {
    if (payload.indexOf(result.domain) > -1) {
      filteredDomain.push(result);
    }
  });
  newState.filteredData = filteredDomain;
  return newState;
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
        availableFilters: getAvailableFilters(action.payload),
        status: "completed",
      };
    case C.SELECT_DATASET:
      return {
        ...state,
        ...getUpdatedBenchMark(
          "DATASET",
          state,
          action.payload.index,
          action.payload.parentIndex
        ),
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
    case C.CLEAR_BENCHMARK: {
      return {
        ...initialState,
      };
    }
    case C.SEARCH_BENCHMARK: {
      const { filteredData, selectedIndex } = getFilteredData(
        state.result,
        action.payload
      );
      return {
        ...state,
        filteredData: action.payload === "" ? state.result : filteredData,
        selectedIndex,
      };
    }
    case C.FILTER_BENCHMARK: {
      return {
        ...state,
        ...filterByDomain(state, action.payload),
      };
    }

    case C.CLEAR_FILTER_BENCHMARK: {
      return {
        ...state,
        filteredData: state.result,
      };
    }
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
