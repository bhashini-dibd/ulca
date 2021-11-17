import MyDatasetList from "./MyDatasetList";
import { Tabs, Tab, Box, Typography } from "@material-ui/core";
import PropTypes from "prop-types";
import MyBencmarkList from "./MyBencmarkList";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import MyContributionList from "../../../../redux/actions/api/DataSet/DatasetView/MyContribution";
import MyBenchmarkList from "../../../../redux/actions/api/DataSet/DatasetView/MyBenchmarkList";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useParams } from "react-router";
import ClearReport from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import {
  FilterTable,
  clearFilter,
  PageChange,
} from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import C from "../../../../redux/actions/constants";
import getSearchedValue from "../../../../redux/actions/api/DataSet/DatasetView/GetBenchMarkSearch";

const ContributionList = (props) => {
  const [value, setValue] = useState(0);
  const { added } = useParams();
  const { roles } = JSON.parse(localStorage.getItem("userDetails"));
  const dispatch = useDispatch();

  const myContributionReport = useSelector(
    (state) => state.myContributionReport
  );

  const myBenchmarkReport = useSelector((state) => state.myBenchmarkReport);

  const data = myContributionReport.filteredData;
  const benchmarkData = myBenchmarkReport.filteredData;
  const PageInfo = useSelector((state) => state.pageChangeDetails);
  const BenchmarkPageInfo = useSelector((state) => state.benchmarkPageDetails);
  const clearAllDataset = (data, handleClose) => {
    handleClose();
    dispatch(clearFilter(data, C.CLEAR_FILTER));
  };
  const applyDataset = (data, handleClose) => {
    handleClose();
    dispatch(FilterTable(data, C.CONTRIBUTION_TABLE));
  };

  const clearAllBenchmark = (data, handleClose) => {
    handleClose();
    dispatch(clearFilter(data, C.CLEAR_BENCHMARK_FILTER));
  };
  const applyBenchmark = (data, handleClose) => {
    handleClose();
    dispatch(FilterTable(data, C.CONTRIBUTION_BENCHMARK_TABLE));
  };

  const tabs = [
    {
      label: "Submitted Dataset",
      index: 0,
      roles: ["CONTRIBUTOR-USER", "BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    {
      label: "Benchmark Dataset",
      index: 1,
      roles: ["BENCHMARK-DATASET-CONTRIBUTOR"],
    },
  ];

  const handleSearch = (value) => {
    dispatch(getSearchedValue(value));
  };

  const handleBenchmarkSearch = (value) => {
    dispatch(getSearchedValue(value));
  };

  const MyContributionListApi = () => {
    dispatch(ClearReport());
    const userObj = new MyContributionList(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    dispatch(APITransport(userObj));
  };

  const MyBenchmarkListApi = () => {
    dispatch(ClearReport());
    if (tabs[1].roles.includes(roles[0])) {
      const userObj = new MyBenchmarkList(
        "SAVE",
        "A_FBTTR-VWSge-1619075981554",
        "241006445d1546dbb5db836c498be6381606221196566"
      );
      dispatch(APITransport(userObj));
    }
  };

  useEffect(() => {
    (myContributionReport.filteredData.length === 0 ||
      myContributionReport.refreshStatus ||
      added) &&
      MyContributionListApi();
    (myBenchmarkReport.filteredData.length === 0 ||
      myBenchmarkReport.refreshStatus) &&
      MyBenchmarkListApi();
  }, []);

  useEffect(() => {
    for (let i = 0; i < data.length; i++) {
      if (data[i].submitRefNumber === added) {
        let page = Math.floor(i / 10);
        async function dispatchPageAction(i) {
          await dispatch(PageChange(page, C.PAGE_CHANGE));
          let element = await document.getElementById(
            `MUIDataTableBodyRow-${i}`
          );
          element &&
            element.scrollIntoView({
              behavior: "smooth",
            });
          let previousColor = element.style.backgroundColor;
          element.style.backgroundColor = "rgba(254, 191, 44, 0.1)";
          element.style.transitionTimingFunction = "ease-out";
          element.style.transitionDelay = "0.1s";
          element.style.transition = "0.2s";
          setTimeout(() => {
            element.style.backgroundColor = previousColor;
            element.style.transitionTimingFunction = "";
            element.style.transitionDelay = "";
            element.style.transition = "";
          }, 4000);
        }
        dispatchPageAction(i);
        return;
      }
    }
  }, [data]);

  function a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      "aria-controls": `simple-tabpanel-${index}`,
    };
  }

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
        {value === index && (
          <Box sx={{ p: 3 }}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }

  TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
  };

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ width: "100%" }}>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="basic tabs example"
        >
          {tabs.map((tab) => {
            if (tab.roles.includes(roles[0])) {
              return <Tab label={tab.label} {...a11yProps(tab.index)} />;
            }
          })}
        </Tabs>
      </Box>
      <TabPanel value={value} index={0}>
        <MyDatasetList
          data={data}
          myContributionReport={myContributionReport}
          clearAll={clearAllDataset}
          apply={applyDataset}
          PageInfo={PageInfo}
          added={added}
          MyContributionListApi={MyContributionListApi}
        />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <MyBencmarkList
          data={benchmarkData}
          myContributionReport={myBenchmarkReport}
          clearAll={clearAllBenchmark}
          apply={applyBenchmark}
          PageInfo={BenchmarkPageInfo}
          MyContributionListApi={MyBenchmarkListApi}
          getSearchedValue={getSearchedValue}
          handleSearch={handleSearch}
        />
      </TabPanel>
    </Box>
  );
};

export default ContributionList;
