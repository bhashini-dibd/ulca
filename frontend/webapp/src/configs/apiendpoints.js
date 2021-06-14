const endpoints = {

  getContributionList: '/dataset/listByUserId',
  getDetailReport: '/dataset/getByServiceRequestNumber',
  dataSetSearchApi: '/ulca/data-metric/v0/store/search',
  login: '/ulca/user-mgmt/v1/users/login',
  datasetSubmit: '/dataset/corpus/submit',
  getSearchOptions: '/ulca/user-mgmt/v1/users/login',
  mySearches: "/dataset/corpus/search/listByUserId",
  submitSearchReq: '/dataset/corpus/search',
  errorReport: "/ulca/publish/v0/error/report",
  register:"/ulca/user-mgmt/v1/users/signup"
};

export default endpoints;
