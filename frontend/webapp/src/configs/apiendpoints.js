const endpoints = {

  getContributionList: 'dataset/listByUserId',
  getDetailReport: 'dataset/getByDatasetId',
  dataSetSearchApi: 'ulca/data-metric/v0/store/search',
  login: 'ulca/user-mgmt/v1/users/login',
  datasetSubmit: 'dataset/corpus/submit',
  getSearchOptions: '/ulca/user-mgmt/v1/users/login',
  mySearches :"/corpus/search/listByUserId",
  submitSearchReq:'dataset/corpus/submit-search',
  errorReport:"ulca/publish/v0/error/report"
};

export default endpoints;
