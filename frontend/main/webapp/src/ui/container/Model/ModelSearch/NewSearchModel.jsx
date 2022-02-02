import Tab from "../../../components/common/Tab";
import DatasetItems, { ModelTask } from "../../../../configs/DatasetItems";
import { Suspense, useState } from "react";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
// import CardComponent from "../../../components/common/CardComponent";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import SearchModel from "../../../../redux/actions/api/Model/ModelSearch/SearchModel";
import updateFilter from "../../../../redux/actions/api/Model/ModelSearch/Benchmark";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import {
  FilterModel,
  clearFilterModel,
} from "../../../../redux/actions/api/Model/ModelView/DatasetAction";
import Record from "../../../../assets/no-record.svg";
import { useHistory } from "react-router-dom";
import SearchList from "../../../../redux/actions/api/Model/ModelSearch/SearchList";

import C from "../../../../redux/actions/constants";
import FilterList from "./ModelDetail/Filter";
import React from "react";
import SpeechToSpeech from "../ModelSearch/SpeechToSpeech/SpeechToSpeech";

const CardComponent = React.lazy(() =>
  import("../../../components/common/CardComponent")
);

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
  const filter = useSelector((state) => state.searchFilter);
  const type = ModelTask.map((task) => task.value);
  const [value, setValue] = useState(type.indexOf(filter.type));
  const { searchValue } = useSelector((state) => state.BenchmarkList);
  const [anchorEl, setAnchorEl] = useState(null);

  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;

  const handleChange = (event, newValue) => {
    setValue(newValue);
    makeModelSearchAPICall(ModelTask[newValue].value);
    dispatch(SearchList(""));
  };
  const dispatch = useDispatch();
  const searchModelResult = useSelector((state) => state.searchModel);
  const history = useHistory();
  useEffect(() => {
    makeModelSearchAPICall(filter.type);
  }, []);

  const makeModelSearchAPICall = (type) => {
    if (type !== "sts") {
      const apiObj = new SearchModel(type, "", "");
      dispatch(APITransport(apiObj));
    }
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const clearAll = (data) => {
    dispatch(clearFilterModel(data, C.CLEAR_FILTER_MODEL));
  };
  const apply = (data) => {
    handleClose();
    dispatch(FilterModel(data, C.SEARCH_FILTER));
  };

  const handleClick = (data) => {
    data.prevUrl = "explore-models";
    dispatch(updateFilter({ source: "", filter: "", type: data.task }));
    history.push({
      pathname: `${process.env.PUBLIC_URL}/search-model/${data.submitRefNumber}`,
      state: data,
    });
  };

  const handleSearch = (event) => {
    dispatch(SearchList(event.target.value));
  };

  const renderTabs = () => {
    if (ModelTask[value].value === "sts") {
      return <SpeechToSpeech />;
    }
    if (searchModelResult.filteredData.length)
      return (
        <Suspense fallback={<div>Loading Models...</div>}>
          <CardComponent onClick={handleClick} value={searchModelResult} />
        </Suspense>
      );
    return (
      <div
        style={{
          background: `url(${Record}) no-repeat center center`,
          height: "287px",
          marginTop: "20vh",
        }}
      ></div>
    );
  };

  return (
    <Tab
      handleSearch={handleSearch}
      handleShowFilter={handleShowFilter}
      searchValue={searchValue}
      handleChange={handleChange}
      value={value}
      tabs={ModelTask}
      showFilter={ModelTask[value].value}
    >
      <TabPanel value={value} index={value}>
        {renderTabs()}
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
