import C from "../../../actions/constants";
import moment from 'moment';

const initialState = {
  responseData: [],
  filteredData: [],
  refreshStatus: false,
  filter: { status: [], task: [], license: [], domain: [] },
  selectedFilter: { status: [], task: [], license: [], domain: [] },
};

const isFilterSelected = (keys, values) => {
  for (let i = 0; i < keys.length; i++) {
    if (values[i].length) {
      return true;
    }
  }
  return false;
};

const getUpdatedFilters = (data, values, keys) => {
  let updatedFilter = data;
  keys.forEach((key, i) => {
    if (values[i].length) {
      updatedFilter = updatedFilter.filter((val) => {
        return values[i].indexOf(val[key].toLowerCase()) > -1;
      });
    }
  });
  return updatedFilter;
};

const getFilterValue = (payload, data) => {
  let { filterValues } = payload;
  const filterKeys = Object.keys(filterValues);
  const filterValue = Object.values(filterValues).map((val) =>
    val.map((e) => e.toLowerCase())
  );
  if (isFilterSelected(filterKeys, filterValue)) {
    data.filteredData = Object.assign(
      [],
      JSON.parse(
        JSON.stringify(
          getUpdatedFilters(data.responseData, filterValue, filterKeys)
        )
      )
    );
  } else {
    data.filteredData = data.responseData;
  }
  data.selectedFilter = filterValues;
  return data;
};

const getModelHealthStatus = (state, payload) => {
  let responseData = [];
  let filter = { status: [], task: [], domain: [], license: [] };

  payload.modelHealthStatusList.forEach((element) => {
    responseData.push({
        callbackUrl: element.callbackUrl,
        modelId: element.modelId,
        modelName: element.modelName,
        status: element.status,
        taskType: element.taskType,
        lastStatusUpdate: moment(element.lastStatusUpdate).format("DD/MM/YYYY"),
    });
  });

  responseData = responseData.reverse();
  let filteredData = getFilterValue(
    { filterValues: state.selectedFilter },
    { responseData: responseData }
  );

  filteredData.filter = filter;
  return filteredData;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MODEL_HEALTH_STATUS:
      return getModelHealthStatus(state, action.payload);

    default:
      return {
        ...state,
      };
  }
};

export default reducer;
