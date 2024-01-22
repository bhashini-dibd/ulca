import C from "../../../actions/constants";
import getDatasetName from "../../../../utils/getDataset";
import {
  getLanguageName,
  FilterByDomain,
  FilterByCollection,
  getTaskName,
} from "../../../../utils/getLabel";
import moment from "moment";
const initialState = {
  responseData: [],
  filteredData: [],
  refreshStatus: false,
  filter: { status: [], task: [], license: [], domain: [] },
  selectedFilter: { status: [], task: [], license: [], domain: [] },
};

const dateConversion = (value) => {
  var myDate = new Date(value);
  // let result = myDate.toLocaleString("en-IN", {
  //   day: "2-digit",
  //   month: "2-digit",
  //   year: "numeric",
  //   // hour: "numeric",
  //   // minute: "numeric",
  //   // second: "numeric",
  //   // hour12: true,
  // });
  // return result.toUpperCase();
  return myDate.getTime();
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

const getDomainDetails = (data) => {
  if (data.length === 1) {
    return data[0];
  } else {
    let result = "";
    data.length > 1 &&
      data.forEach((element, i) => {
        if (i !== data.length) {
          result = result + element + "|";
        } else {
          result = result + element;
        }
      });
    return result;
  }
};

const getClearFilter = (data) => {
  data.filteredData = data.responseData;
  data.selectedFilter = { status: [], task: [], license: [], domain: [] };
  return data;
};

const convertDate = (data) => {
  return data?.map((element) => {
    element.createdOn = element?.createdOn
      ? dateConversion(element?.createdOn)
      : "";
    return element;
  });
};

const getContributionList = (state, payload) => {
  let responseData = [];
  let statusFilter = [];
  let modelFilter = [];
  let domain = [];
  let license = [];
  let filter = { status: [], task: [], domain: [], license: [] };
  let refreshStatus = false;
  payload?.data?.forEach((element) => {
        responseData.push({
      benchmarkPerformance: convertDate(element?.benchmarkPerformance),
      version: element.version ? element.version : "v1.0",
      submitRefNumber: element?.modelId,
      modelName: element?.name,
      submittedOn: moment(element?.submittedOn).format("DD/MM/YYYY"),
      task:
        element.task?.type !== "translation"
          ? element.task?.type.toUpperCase()
          : element.task?.type,
      domain: getDomainDetails(element?.domain),
      status: element?.status, 
      license: element?.license?.toUpperCase(),
      action: "View Result",
      color:
        element?.status === "Completed"
          ? "#139D60"
          : element?.status === "In-Progress"
          ? "#2C2799"
          : element?.status === "Failed"
          ? "#F54336"
          : "green",
    });
    !statusFilter.includes(element.status) &&
      element.status &&
      statusFilter.push(capitalizeLetter(element.status));

    !modelFilter.includes(element.task.type) &&
      element.task.type &&
      modelFilter.push(getTaskName(element.task.type));

    !license.includes(element.license) &&
      element.license &&
      license.push(element.license.toUpperCase());

    !domain.includes(...element.domain) &&
      element.domain &&
      domain.push(capitalizeLetter(...element.domain));
    if (element.status === "In-Progress" || element.status === "Pending") {
      refreshStatus = true;
    }
  });

  filter.status = [...new Set(statusFilter)];
  filter.task = [...new Set(modelFilter)];
  filter.license = [...new Set(license)];
  filter.domain = [...new Set(domain)];

  let filteredData = getFilterValue(
    { filterValues: state.selectedFilter },
    { responseData: responseData }
  );
  filteredData.filter = filter;
  filteredData.totalCount = payload.totalCount;
  return filteredData;
};

const capitalizeLetter = (data) => {
  return data.length ? data.replace(data[0], data[0].toUpperCase()) : "";
};

const updateSelectedFilter = (obj, prevState) => {
  const updatedState = Object.assign({}, JSON.parse(JSON.stringify(prevState)));
  updatedState[obj.prop].indexOf(obj.value) > -1
    ? updatedState[obj.prop].splice(updatedState[obj.prop].indexOf, 1)
    : updatedState[obj.prop].push(obj.value);
  return updatedState;
};

const getSearchedValues = (value, data) => {
  const newState = data.filter((val) => {
    return (
      (val["version"] &&
        val["version"].toLowerCase().includes(value.toLowerCase())) ||
      (val["status"] &&
        val["status"].toLowerCase().includes(value.toLowerCase())) ||
      (val["domain"] &&
        val["domain"].toLowerCase().includes(value.toLowerCase())) ||
      (val["license"] &&
        val["license"].toLowerCase().includes(value.toLowerCase())) ||
      (val["task"] &&
        val["task"].toLowerCase().includes(value.toLowerCase())) ||
      (val["modelName"] &&
        val["modelName"].toLowerCase().includes(value.toLowerCase()))
    );
  });

  return newState;
};

const updateModelStatus = (respData, searchValue) => {
  if (searchValue === "") {
    return respData;
  }
  return getSearchedValues(searchValue, respData);
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MODEL_CONTRIBUTION_LIST:
      return getContributionList(state, action.payload);
    case C.MODEL_CONTRIBUTION_TABLE:
      return getFilterValue(action.payload, state);
    case C.CLEAR_MODEL_CONTRIBUTION_LIST:
      return {
        ...initialState,
      };
    case C.GET_MODEL_SEARCH_VALUES:
      return {
        ...state,
        filteredData: getSearchedValues(action.payload, state.responseData),
      };
    case C.GET_SELECTED_FILTER:
      return {
        ...state,
        selectedFilter: updateSelectedFilter(
          action.payload,
          state.selectedFilter
        ),
      };
    case C.CLEAR_MODEL_FILTER:
      return getClearFilter(state);

    case C.TOGGLE_MODEL_STATUS:
      return {
        ...state,
        responseData: getContributionList(state, action.payload).responseData,
        filteredData: updateModelStatus(
          getContributionList(state, action.payload).responseData,
          action.payload.searchValue
        ),
      };

    default:
      return {
        ...state,
      };
  }
};

export default reducer;
