import C from "../../../actions/constants";
import { getLanguageName , FilterByDomain} from "../../../../utils/getLabel";


const initialState = {
  responseData: [],
  filteredData: [],
  refreshStatus: false,
  filter: { status: [], modelType: [] },
  selectedFilter: { language: [], domainFilter: [], submitter: [] },
  searchValue: "",
  page: 0,
};

const getFilterValue = (payload, data) => {
  let { filterValues } = payload;
  let languageFilter = [];
  let domainFilterValue = [];
  let filterResult = [];
  if (
    filterValues &&
    filterValues.hasOwnProperty("language") &&
    filterValues.language.length > 0
  ) {
    languageFilter = data.responseData.filter((value) => {
      if (
        filterValues.language.includes(value.tLanguage) ||
        filterValues.language.includes(value.sLanguage)
      ) {
        return value;
      }
      return false;
    });
  } else {
    languageFilter = data.responseData;
  }
  if (
    filterValues &&
    filterValues.hasOwnProperty("domainFilter") &&
    filterValues.domainFilter.length > 0
  ) {
    domainFilterValue = languageFilter.filter((value) => {
      if (filterValues.domainFilter.includes(value.domain)) {
        return value;
      }
      return false;
    });
  } else {
    domainFilterValue = languageFilter;
  }
  if (
    filterValues &&
    filterValues.hasOwnProperty("submitter") &&
    filterValues.submitter.length > 0
  ) {
    filterResult = domainFilterValue.filter((value) => {
      if (filterValues.submitter.includes(value.submitter)) {
        return value;
      }
      return false;
    });
  } else {
    filterResult = domainFilterValue;
  }
  data.filteredData = filterResult;
  data.selectedFilter = filterValues;
  return data;
};

const getDomainDetails = (data) => {
  if (data.length === 1) {
    //console.log("checkkk",FilterByDomain(data)[0].label)
    // return data[0];
   return  FilterByDomain(data)[0].label
    } else {
    // let result = "";
    // data.length > 1 &&
    //   data.forEach((element, i) => {
    //     console.log("checkkk",element)
    //     if (i < data.length-1) {
    //       result = result +  FilterByDomain([element])[0].label + "|";
    //     } else {
    //       result = result + FilterByDomain([element])[0].label;
    //     }
    //   });
    // return result;
    return "Multiple"
  }
};

const getClearFilter = (data) => {
  data.filteredData = data.responseData;
  data.selectedFilter = { language: [], domainFilter: [], submitter: [] };
  data.page = 0;
  return data;
};

const getContributionList = (state, payload) => {
  let responseData = [];
  let languageFilter = [];
  let submitterFilter = [];
  let domainFilter = [];
  let filter = { language: [], domainFilter: [], submitter: [] };

  payload.forEach((element) => {
    let sLanguage =
      element.languages &&
      element.languages.sourceLanguage &&
      getLanguageName(element.languages.sourceLanguage);
    let tLanguage =
      element.languages &&
      element.languages.targetLanguage &&
      getLanguageName(element.languages.targetLanguage);
    let lang = tLanguage ? sLanguage + " - " + tLanguage : sLanguage;
    let domain = getDomainDetails(element.domain);
    let metrics = element.metric && getDomainDetails(element.metric);
    responseData.push({
      description: element.description,
      submitRefNumber: element.benchmarkId,
      modelName: element.name,
      // submittedOn: dateConversion(element.submittedOn),
      // publishedOn: dateConversion(element.publishedOn),
      metrics: metrics ? metrics : "-",
      task: element.task.type,
      domain: domain,
      status: "Published",
      sLanguage,
      tLanguage,
      language: lang,
      refUrl: element.refUrl ? element.refUrl : "NA",
      inferenceEndPoint: element.inferenceEndPoint,
      source: element.languages && element.languages.sourceLanguage,
      target: element.languages && element.languages.targetLanguage,
      licence: element.license,
      submitter: element.submitter ? element.submitter.name : "-",
      trainingDataset: element.trainingDataset,
      color:
        element.status === "Completed"
          ? "#139D60"
          : element.status === "In-Progress"
          ? "#139D60"
          : element.status === "Failed"
          ? "#139D60"
          : "green",
    });
    !languageFilter.includes(sLanguage) &&
      sLanguage &&
      languageFilter.push(sLanguage);
    !languageFilter.includes(tLanguage) &&
      tLanguage &&
      languageFilter.push(tLanguage);
    !domainFilter.includes(domain) && domain && domainFilter.push(domain);
    !submitterFilter.includes(element.submitter && element.submitter.name) &&
      element.submitter &&
      element.submitter.name &&
      submitterFilter.push(element.submitter.name);
  });

  filter.language = [...new Set(languageFilter)];
  filter.domainFilter = [...new Set(domainFilter)];
  filter.submitter = [...new Set(submitterFilter)];

  responseData = responseData.reverse();
  let filteredData = getFilterValue(
    { filterValues: initialState.selectedFilter },
    { responseData: responseData }
  );
  filteredData.filter = filter;
  return state.searchValue ? { ...state } : filteredData;
};

const getSearchedList = (state, searchValue) => {
  let results = [];
  let present = {};
  let searchKey = [
    "domain",
    "modelName",
    "status",
    "submitter",
    "sLanguage",
    "tLanguage",
  ];
  for (let i = 0; i < state.responseData.length; i++) {
    Object.keys(state.responseData[i]).forEach((key) => {
      if (searchKey.indexOf(key) > -1) {
        if (
          state.responseData[i][key] !== null &&
          state.responseData[i][key]
            .toLowerCase()
            .includes(searchValue.toLowerCase())
        ) {
          present[i] = i;
        }
      }
    });
  }

  Object.keys(present).forEach((key, i) => {
    results.push(state.responseData[key]);
  });
  return {
    ...state,
    filteredData: !searchValue ? state.responseData : results,
    searchValue,
  };
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.BENCHMARK_MODEL_SEARCH:
      return {
        ...state,
        ...getContributionList(state, action.payload),
      };
    case "BENCHMARK_PAGE_NO": {
      return { ...state, page: action.payload };
    }
    case C.GET_SEARCHED_LIST:
      return { ...state, ...getSearchedList(state, action.payload) };
    case C.SEARCH_BENCHMARK:
      return { ...state, ...getFilterValue(action.payload, state) };
    case C.CLEAR_FILTER_BENCHMARK:
      return { ...state, ...getClearFilter(state) };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
