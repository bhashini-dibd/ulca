import apiStatus from "./apistatus/apistatus";
import myContributionReport from "./DataSet/DatasetView/myContribution";
import detailedReport from "./DataSet/DatasetView/DetailedDataSet";
import dashboardReport from "./Dashboard/languageDatasets";
import mySearchReport from "./DataSet/DatasetSearch/MySearches";
import mySearchOptions from "./DataSet/DatasetSearch/SearchAndDownload";
import errorData from "./DataSet/DatasetView/ErrorDownload";
import pageChangeDetails from "./DataSet/DatasetView/PageInfo";
import searchPageDetails from "./DataSet/DatasetSearch/SearchPageInfo";
import tableView from "./DataSet/DatasetView/TableStatus";
import modelContributionReport from "./Model/ModelView/myContribution";
import modelPageChangeDetails from "./Model/ModelView/PageInfo";
import modelTableView from "./Model/ModelView/TableStatus";
import modelStatus from "./Model/ModelSubmit/SubmitModel";
import searchFilter from "./Model/ModelSearch/Benchmark";
import searchModel from "./Model/ModelSearch/SearchModel";
import BenchmarkSearch from "./Model/BenchmarkModel/Benchmark";
import BenchmarkList from "./Model/BenchmarkModel/BenchmarkModel";
import getMenuInfo from "./Common/getMenuInfo";
import getBenchMarkDetails from "./Model/ModelView/RunBenchmark";
import SearchReadymadeDataset from "./DataSet/ReadymadeDataset/SearchReadymade";
import searchReadymade from "./DataSet/ReadymadeDataset/Readymade";
import benchmarkDetails from './Model/BenchmarkModel/BenchmarkDetails';

const index = {
  apiStatus,
  myContributionReport,
  detailedReport,
  dashboardReport,
  mySearchReport,
  mySearchOptions,
  errorData,
  pageChangeDetails,
  searchPageDetails,
  tableView,
  modelContributionReport,
  modelPageChangeDetails,
  modelTableView,
  modelStatus,
  searchModel,
  SearchReadymadeDataset,
  searchFilter,
  getMenuInfo,
  getBenchMarkDetails,
  searchReadymade,
  BenchmarkSearch,
  BenchmarkList,
  benchmarkDetails
};

export default index;
