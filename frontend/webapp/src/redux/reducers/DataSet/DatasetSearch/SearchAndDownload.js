import C from "../../../actions/constants";
import * as Filters from "../../../../configs/filters.json";

const initialState = {
  result: {
    data: [],
    datasetType: [],
    languagePair: {
      sourceLang: [],
    },
    basicFilter: [],
    advFilter: [],
  },
  filterCategory: [],
};

const getSearchOptions = (payload, prevState) => {
  const dataset = payload[0].datasetType;
  return Filters.default[dataset].filters.map((filter) => {
    const values = payload[0].values.filter((val) => val.code === filter.value);
    return {
      ...filter,
      values: values.length && values[0].values,
    };
  });
};

const getSearchFilter = (datasetType, prevState, filterCategory) => {
  let newState = Object.assign({}, JSON.parse(JSON.stringify(prevState)));
  newState["languagePair"]["sourceLang"] = newState["data"][datasetType][
    "filters"
  ][0]["values"].map((value) => {
    return {
      value: value.value,
      label: value.label,
    };
  });
  let basicFilter = Object.assign(
    JSON.parse(JSON.stringify(filterCategory[datasetType].basicFilters))
  );
  let advFilter = Object.assign(
    JSON.parse(JSON.stringify(filterCategory[datasetType].advancedFilters))
  );

  basicFilter.forEach((base, i) => {
    prevState.data[datasetType].filters.forEach((filter) => {
      if (base.value === filter.filter) {
        basicFilter[i].values = filter.values;
        basicFilter[i].type = filter.type;
      }
    });
  });

  advFilter.forEach((base, i) => {
    prevState.data[datasetType].filters.forEach((filter) => {
      if (base.value === filter.filter) {
        advFilter[i].values = filter.values;
        advFilter[i].type = filter.type;
      }
    });
  });
  newState["basicFilter"] = basicFilter;
  newState["advFilter"] = advFilter;
  return newState;
};

const setEmptyValues = (data) => {
  return data.map((val) => {
    val["values"] = [];
    return val;
  });
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_SEARCH_OPTIONS:
      return {
        ...state,
        result: {
          ...state.result,
          data: getSearchOptions(action.payload, state.result),
        },
      };
    case C.GET_SEARCH_FILTERS:
      return {
        ...state,
        result: getSearchFilter(
          action.payload,
          state.result,
          state.filterCategory
        ),
      };

    case C.GET_FILTER_CATEGORY:
      const { data, type } = action.payload;
      return {
        ...state,
        result: {
          ...state.result,
          basicFilter: setEmptyValues(data[type].basicFilters),
          advFilter: setEmptyValues(data[type].advancedFilters),
        },
        filterCategory: data,
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
