import C from "../../../actions/constants";
import * as Filters from "../../../../configs/filters.json";

const initialState = {
  result: [],
  filters: [],
};

const getFilters = (payload, datasetType) => {
  return Filters.default[datasetType].filters.map((filter) => {
    const values = payload
      .filter((e) => e.datasetType === datasetType)[0]
      .values.filter((val) => val.code === filter.value);
    return {
      ...filter,
      values: values.length && values[0].values,
     
    };
  });
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_SEARCH_OPTIONS:
      return {
        ...state,
        result: action.payload,
        filters: getFilters(action.payload, "parallel-corpus"),
      };
    case C.GET_SEARCH_FILTERS:
      return {
        ...state,
        filters: getFilters(state.result, action.payload),
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
