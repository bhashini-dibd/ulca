import Tab from "../../../components/common/Tab";
import { BenchmarkModelTask } from "../../../../configs/DatasetItems";
import { useState } from "react";
import Box from "@material-ui/core/Box";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import BenchmarkModelSearch from "../../../../redux/actions/api/Model/BenchmarkModel/SearchBenchmark";
import updateFilter from "../../../../redux/actions/api/Model/BenchmarkModel/Benchmark";
import { Suspense } from "react";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import {
  FilterModel,
  clearFilterModel,
} from "../../../../redux/actions/api/Model/ModelView/DatasetAction";
import Record from "../../../../assets/no-record.svg";
import { useHistory } from "react-router-dom";
import SearchList from "../../../../redux/actions/api/Model/ModelSearch/SearchList";
import GridView from "./GridView";

import C from "../../../../redux/actions/constants";
import FilterList from "./Filter";

function TabPanel(props) {
  const { children, value, index, ...other } = props;
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box p={3}>{children}</Box>}
    </div>
  );
}

const NewSearchModel = () => {
  const filter = useSelector((state) => state.BenchmarkSearch);

  const type = BenchmarkModelTask.map((task) => task.value);
  const [value, setValue] = useState(type.indexOf(filter.type));
  const { searchValue } = useSelector((state) => state.BenchmarkList);
  const [anchorEl, setAnchorEl] = useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;
  const [rowsPerPage, setRowsPerPage] = useState(9)

  const handleChange = (event, newValue) => {
    setValue(newValue);
    makeModelSearchAPICall(BenchmarkModelTask[newValue].value);
    dispatch(SearchList(""));
    dispatch({ type: "CLEAR_FILTER_BENCHMARK" });
  };
  const dispatch = useDispatch();
  const searchModelResult = useSelector((state) => state.BenchmarkList);
  const history = useHistory();
  useEffect(() => {
    makeModelSearchAPICall(filter.type);
  }, []);

  const makeModelSearchAPICall = (type) => {
    const apiObj = new BenchmarkModelSearch(type, "", "");
    dispatch(SearchList(searchValue));
    dispatch(APITransport(apiObj));
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const clearAll = (data) => {
    dispatch(clearFilterModel(data, C.CLEAR_FILTER_BENCHMARK));
  };
  const apply = (data) => {
    handleClose();
    dispatch(FilterModel(data, C.SEARCH_BENCHMARK));
    dispatch({ type: C.BENCHMARK_PAGE_NO, payload: 0 });

  };

  const handleClick = (data) => {
    data.prevUrl = "explore-models";
    dispatch(updateFilter({ source: "", filter: "", type: data.task }));
    history.push({
      pathname: `${process.env.PUBLIC_URL}/model/benchmark-details/${data.submitRefNumber}`,
      state: data,
    });
  };

  const handleSearch = (event) => {
    dispatch(SearchList(event.target.value));
    dispatch({ type: C.BENCHMARK_PAGE_NO, payload: 0 })
  };
  const handleRowsPerPageChange = (e, page) => {
    setRowsPerPage(page.props.value);
  };


  // const handleChangePage = (e, page) => {
  //   dispatch({ type: "BENCHMARK_PAGE_NO", payload: page });
  // };

  return (
    <Tab
      handleSearch={handleSearch}
      handleShowFilter={handleShowFilter}
      searchValue={searchValue}
      handleChange={handleChange}
      value={value}
      tabs={BenchmarkModelTask}
      showFilter={BenchmarkModelTask[value].value}
    >
      <TabPanel value={value} index={value}>
        {searchModelResult.filteredData.length ? (
          <Suspense fallback={<div>Loading Models...</div>}>
            <GridView
              data={searchModelResult}
              handleCardClick={handleClick}
              //  rowsPerPage={9}
              page={searchModelResult.page}
              // handleChangePage={handleChangePage}
              rowsPerPage={rowsPerPage}
              handleRowsPerPageChange={handleRowsPerPageChange}
              onPageChange={(e, page) => dispatch({ type: C.BENCHMARK_PAGE_NO, payload: page })}
            />
           
          </Suspense>
        
        ) : (
          <div
            style={{
              background: `url(${Record}) no-repeat center center`,
              height: "287px",
              marginTop: "20vh",
            }}
          ></div>
        )}
      </TabPanel>

      {popoverOpen && (
        <FilterList
          id={id}
          open={popoverOpen}
          anchorEl={anchorEl}
          handleClose={handleClose}
          filter={searchModelResult.filter}
          selectedFilter={searchModelResult.selectedFilter}
          clearAll={clearAll}
          apply={apply}
        />
      )}
    </Tab>
  );
};

export default NewSearchModel;
