import MyDatasetList from "./MyDatasetList";
import { Tabs, Tab, Box, Typography } from "@material-ui/core";
import PropTypes from "prop-types";
import MyBencmarkList from "./MyBencmarkList";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import MyContributionList from "../../../../redux/actions/api/DataSet/DatasetView/MyContribution";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useParams } from "react-router";
import ClearReport from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";

const ContributionList = (props) => {
  const [value, setValue] = useState(0);
  const { added } = useParams();

  const dispatch = useDispatch();

  const myContributionReport = useSelector(
    (state) => state.myContributionReport
  );
  const data = myContributionReport.filteredData;

  const tabs = [
    { label: "Dataset", index: 0 },
    { label: "Benchmark Dataset", index: 1 },
  ];

  const MyContributionListApi = () => {
    dispatch(ClearReport());
    const userObj = new MyContributionList(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    dispatch(APITransport(userObj));
  };

  useEffect(() => {
    (myContributionReport.filteredData.length === 0 ||
      myContributionReport.refreshStatus ||
      added) &&
      MyContributionListApi();
  }, []);

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
          {tabs.map((tab) => (
            <Tab label={tab.label} {...a11yProps(tab.index)} />
          ))}
        </Tabs>
      </Box>
      <TabPanel value={value} index={0}>
        <MyDatasetList
          data={data}
          myContributionReport={myContributionReport}
        />
      </TabPanel>
      {/* <TabPanel value={value} index={1}>
        <MyBencmarkList />
      </TabPanel> */}
    </Box>
  );
};

export default ContributionList;
