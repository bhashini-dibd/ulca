import C from "../../../actions/constants";
import getDatasetName from "../../../../utils/getDataset";
import {
  getLanguageName,
  FilterByDomain,
  FilterByCollection,
} from "../../../../utils/getLabel";
const initialState = {
  responseData: [],
  filteredData: [],
  refreshStatus: false,
  filter: { status: [], task: [], license: [], domain: [] },
  selectedFilter: { status: [], task: [], license: [], domain: [] },
};

const dateConversion = (value) => {
  var myDate = new Date(value);
  let result = myDate.toLocaleString("en-IN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    // hour: "numeric",
    // minute: "numeric",
    // second: "numeric",
    // hour12: true,
  });
  return result.toUpperCase();
};

const getFilterValue = (payload, data) => {
  let { filterValues } = payload;
  let statusFilter = [];
  let filterResult = [];
  if (
    filterValues &&
    filterValues.hasOwnProperty("status") &&
    filterValues.status.length > 0
  ) {
    filterResult = data.responseData.filter((value) => {
      // statusFilter = Object.assign([], JSON.stringify(JSON.parse(data.responseData))).filter((value) => {
      if (value.status && filterValues.status.includes(value.status)) {
        return value;
      }
    });
  } else {
    filterResult = data.responseData;
  }
  if (
    filterValues &&
    filterValues.hasOwnProperty("task") &&
    filterValues.task.length > 0
  ) {
    console.log("filterValues of task", filterValues);
    console.log("filterResult of task", filterResult);

    filterResult = filterResult.filter((value) => {
      if (value.task && filterValues.task.includes(value.task.toLowerCase())) {
        return true;
      }
    });
  }
  // else {
  //   filterResult = statusFilter
  // }

  if (
    filterValues &&
    filterValues.hasOwnProperty("license") &&
    filterValues.license.length > 0
  ) {
    console.log("filterValues of license", filterValues);
    console.log("filterResult of license", filterResult);
    filterResult = filterResult.filter((value) => {
      if (
        value.licence &&
        filterValues.license.includes(value.licence.toLowerCase())
      ) {
        return true;
      }
    });
  }
  // else {
  //   filterResult = statusFilter;
  // }

  // if (
  //   filterValues &&
  //   filterValues.hasOwnProperty("domain") &&
  //   filterValues.domain.length > 0
  // ) {
  //   filterResult =statusFilter.filter((value) => {
  //     if (value.domain && filterValues.domain.includes(value.domain)) {
  //       return true;
  //     }
  //   });
  // } else {
  //   filterResult = statusFilter;
  // }
  // console.log(filterResult);
  data.filteredData = filterResult;
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
  return data.map((element) => {
    element.createdOn = element.createdOn
      ? dateConversion(element.createdOn)
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
  payload.forEach((element) => {
    let sLanguage =
      element.languages.length > 0 &&
      element.languages[0].sourceLanguage &&
      getLanguageName(element.languages[0].sourceLanguage);
    let tLanguage =
      element.languages &&
      element.languages.length > 0 &&
      element.languages[0].targetLanguage &&
      getLanguageName(element.languages[0].targetLanguage);
    let lang = tLanguage ? sLanguage + " - " + tLanguage : sLanguage;
    responseData.push({
      benchmarkPerformance: convertDate(element.benchmarkPerformance),
      version: element.version ? element.version : "v1.0",
      submitRefNumber: element.modelId,
      modelName: element.name,
      description: element.description,
      submittedOn: dateConversion(element.submittedOn),
      task:
        element.task.type !== "translation"
          ? element.task.type.toUpperCase()
          : element.task.type,
      domain: getDomainDetails(element.domain),
      status: element.status,
      endPoint: element.inferenceEndPoint,
      language: lang,
      source:
        element.languages.length > 0 && element.languages[0].sourceLanguage,
      target:
        element.languages &&
        element.languages.length > 0 &&
        element.languages[0].targetLanguage,
      license: element.license.toUpperCase(),
      submitter: element.submitter.name,
      trainingDataset: element.trainingDataset,
      action: "View Result",
      color:
        element.status === "Completed"
          ? "#139D60"
          : element.status === "In-Progress"
          ? "#2C2799"
          : element.status === "Failed"
          ? "#F54336"
          : "green",
    });
    !statusFilter.includes(element.status) &&
      element.status &&
      statusFilter.push(element.status);

    !modelFilter.includes(element.task.type) &&
      element.task.type &&
      modelFilter.push(element.task.type);

    !license.includes(element.license) &&
      element.license &&
      license.push(element.license);

    !domain.includes(...element.domain) &&
      element.domain &&
      domain.push(...element.domain);
    if (element.status === "In-Progress" || element.status === "Pending") {
      refreshStatus = true;
    }
  });

  filter.status = [...new Set(statusFilter)];
  filter.task = [...new Set(modelFilter)];
  filter.license = [...new Set(license)];
  filter.domain = [...new Set(domain)];

  responseData = responseData.reverse();
  let filteredData = getFilterValue(
    { filterValues: state.selectedFilter },
    { responseData: responseData }
  );
  filteredData.filter = filter;
  return filteredData;
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
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
