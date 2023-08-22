import C from "../../../actions/constants";
import {
  getLanguageLabel,
  FilterByDomain,
  FilterByCollection,
  getLanguageName,
} from "../../../../utils/getLabel";
import getDatasetName from "../../../../utils/getDataset";
import { translate } from "../../../../assets/localisation";
import imageArray from "../../../../utils/getModelIcons";
import AdvanceFilterIcon from "../../../../assets/training-dataset.svg";
import LanguageIcon from "../../../../assets/LanguageIcon.svg";
import DomainIcon from "../../../../assets/domainIcon.svg";
import TaskIcon from "../../../../assets/task_alt_black_24dp.svg";

const initialState = {
  responseData: [],
  filteredData: [],
};

const parsedFilter = require('../../../../configs/filters.json');

const colorArr = imageArray.map((image) => image.color);
const dateConversion = (value) => {
  var myDate = new Date(value);
  let result = myDate.toLocaleString("en-IN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "numeric",
    minute: "numeric",
    second: "numeric",
    hour12: true,
  });
  return result.toUpperCase();
};

const getLanguages = (arr) => {
  return arr.map((val) => getLanguageName(val)).join(", ");
};

const getSearchInfo = (searchCriteria) => {
  let result = [];
  const keys = Object.keys(searchCriteria);
  const keysToIgnore = [
    "groupBy",
    "multipleContributors",
    "originalSourceSentence",
    "serviceRequestNumber",
    "userId",
  ];
  keys.forEach((key, i) => {
    if (keysToIgnore.indexOf(key) < 0 && searchCriteria[key] !== undefined) {
      if (key === "datasetType") {
        result.push({
          title: translate(key),
          para: getDatasetName(searchCriteria[key]),
          key,
          color:
            colorArr.length - 1 >= i
              ? colorArr[i]
              : colorArr[i - (colorArr.length - 1)],
          imageUrl: TaskIcon,
          datasetType: searchCriteria['datasetType']
        });
      } else if (key === "sourceLanguage" || key === "targetLanguage") {
        result.push({
          title: translate(key),
          para: getLanguages(searchCriteria[key]),
          key,
          color:
            colorArr.length - 1 >= i
              ? colorArr[i]
              : colorArr[i - (colorArr.length - 1)],
          imageUrl: LanguageIcon,
        });
      } else if (key === "license") {
        result.push({
          title: translate(key),
          para: Array.isArray(searchCriteria[key])
            ? searchCriteria[key].join(",").toUpperCase()
            : searchCriteria[key],
          key,
          color:
            colorArr.length - 1 >= i
              ? colorArr[i]
              : colorArr[i - (colorArr.length - 1)],
          imageUrl: AdvanceFilterIcon,
        });
      } else if (key === "collectionMethod") {
        result.push({
          title: translate(key),
          para: Array.isArray(searchCriteria[key])
            ? searchCriteria[key].join(",").replace("-", " ")
            : searchCriteria[key],
          key,
          color:
            colorArr.length - 1 >= i
              ? colorArr[i]
              : colorArr[i - (colorArr.length - 1)],
          imageUrl: AdvanceFilterIcon,
        });
      } else {
        result.push({
          title: translate(key),
          para: Array.isArray(searchCriteria[key])
            ? searchCriteria[key].join(",")
            : searchCriteria[key],
          key,
          color:
            colorArr.length - 1 >= i
              ? colorArr[i]
              : colorArr[i - (colorArr.length - 1)],
          imageUrl: key === "domain" ? DomainIcon : AdvanceFilterIcon,
        });
      }
    }
  });
  return result;
};

const getMySearches = (state, payload) => {
  let existingResponseData = state.responseData;
  let newArr = [];
  payload.data.forEach((element) => {
    if (element.searchCriteria) {
      let dataSet = getDatasetName(element.searchCriteria.datasetType);
      let langauge =
        element.searchCriteria.sourceLanguage &&
        getLanguageLabel(element.searchCriteria.sourceLanguage).map(
          (val) => val.label
        )[0];
      let tLanguage =
        element.searchCriteria.targetLanguage &&
        getLanguageLabel(element.searchCriteria.targetLanguage)
          .map((val) => val.label)
          .join(", ");
      let searchDetails = JSON.parse(
        element.status.length > 0 && element.status[0].details
      );
      let domain =
        element.searchCriteria.domain &&
        FilterByDomain(element.searchCriteria.domain)
          .map((val) => val.label)
          .join(", ");
      let collection =
        element.searchCriteria.collectionMethod &&
        FilterByCollection(element.searchCriteria.collectionMethod)
          .map((val) => val.label)
          .join(", ");
      newArr.push({
        sr_no: element.serviceRequestNumber,
        search_criteria: `${dataSet} | ${langauge} ${tLanguage ? " | " + tLanguage : ""
          } ${domain ? " | " + domain : ""} ${collection ? " | " + collection : ""
          }`,
        searched_on: dateConversion(element.timestamp),
        status: element.status.length > 0 && element.status[0].status,

        count: searchDetails && searchDetails.count,
        sampleUrl: searchDetails && searchDetails.datasetSample,
        downloadUrl: searchDetails && searchDetails.dataset,
        // sourceLanguage: element.searchCriteria.sourceLanguage,
        // targetLanguage: element.searchCriteria.targetLanguage,
        datasetType: element.searchCriteria.datasetType,
        // domain: element.searchCriteria.domain,
        // collection: element.searchCriteria.collectionMethod,
        searchValues: element.searchCriteria,
        searchInfo: updateFilterSequence(getSearchInfo(element.searchCriteria)),
      });
    }
  });
  // newArr = newArr.reverse();
  
  if (existingResponseData.length > 0) {
    let newArrStartIndex = (payload.startPage * 10) - 10;
    existingResponseData.splice(newArrStartIndex, 10, newArr);
    return existingResponseData.flat();
  }
  return newArr;
};

const updateFilterSequence = (data) => {
  let basicFilter = ['datasetType', ...parsedFilter[data[0]['datasetType']].filters.filter(elem => elem.filterType === 'basic' || elem.filterType === 'language').map(val => val.value)];
  const firstSequence = data.filter(val => {
    return basicFilter.includes(val.key);
  })

  let advFilter = parsedFilter[data[0]['datasetType']].filters.filter(elem => elem.filterType === 'advance').map(val => val.value);
  const secondSequence = [];
  advFilter = [...new Set(advFilter)];

  advFilter.forEach(filter => {
    data.forEach(val => {
      if (filter === val.key) {
        secondSequence.push(val);
      }
    })
  })
  return [...firstSequence, ...secondSequence];
}

const getFilteredData = (value, data) => {
  const newState = data.filter((val) => {
    return (
      (val["search_criteria"] &&
        val["search_criteria"].toLowerCase().includes(value.toLowerCase())) ||
      (val["count"] !== undefined && val["count"].toString().includes(value)) ||
      (val["status"] &&
        val["status"].toLowerCase().includes(value.toLowerCase()))
    );
  });

  return newState;
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_MY_REPORT:
      const data = getMySearches(state, action.payload);
      return {
        responseData: data,
        filteredData: data,
        totalCount: action.payload.totalCount
      };
    case C.GET_SEARCHED_VALUES:
      return {
        ...state,
        filteredData: getFilteredData(action.payload, state.responseData),
      };
    case C.CLEAR_USER_EVENT:
      return {
        ...initialState,
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;
