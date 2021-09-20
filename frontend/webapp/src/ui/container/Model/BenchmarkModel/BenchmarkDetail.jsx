import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../styles/Dataset";
import { ArrowBack } from "@material-ui/icons";
import { useHistory, useParams } from "react-router";
import ModelDescription from "../ModelSearch/ModelDetail/ModelDescription";
import { useLocation } from "react-router-dom";
import React, { useEffect, useState } from "react";
import Header from "../../../components/common/Header";
import Footer from "../../../components/common/Footer";
import Theme from "../../../theme/theme-default";
import { MuiThemeProvider } from "@material-ui/core/styles";
import APITransport from "../../../../redux/actions/apitransport/apitransport";

import {
  Grid,
  Typography,
  Button,
  Divider,
  Tabs,
  Tab,
  AppBar,
  Box,
} from "@material-ui/core";
import PropTypes from "prop-types";
import MUIDataTable from "mui-datatables";
import BenchmarkDetails from "../../../../redux/actions/api/Model/BenchmarkModel/BenchmarkDetails";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
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

const SearchModelDetail = (props) => {
  const { classes } = props;
  const history = useHistory();
  const params = useParams();
  const benchmarkId = params.benchmarkId;
  const data = useSelector((state) => state.benchmarkDetails.data);
  const dispatch = useDispatch();
  useEffect(() => {
    const apiObj = new BenchmarkDetails(benchmarkId);
    dispatch(APITransport(apiObj));
  }, []);

  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  const [index, setIndex] = useState(0);
  const [modelTry, setModelTry] = useState(false);
  const location = useLocation();
  const metricArray = data.metricArray ? data.metricArray : [];
  //   useEffect(() => {
  //     setData(location.state);
  //   }, [location]);
  const description = [
    {
      title: "Description",
      para: data.description,
    },
    {
      title: "Source URL",
      para: data.refUrl,
    },
    {
      title: "Task",
      para: data.task,
    },

    {
      title: "Languages",
      para: data.language,
    },
    {
      title: "Domain",
      para: data.domain,
    },
    {
      title: "Metric",
      para: data.metric,
    },
  ];
  //   const { prevUrl } = location.state;
  //   const handleCardNavigation = () => {
  //     // const { prevUrl } = location.state
  //     if (prevUrl === "explore-models") {
  //       history.push(`${process.env.PUBLIC_URL}/model/explore-models`);
  //     } else {
  //       history.push(`${process.env.PUBLIC_URL}/model/my-contribution`);
  //     }
  //   };

  const handleClick = () => {
    history.push({
      pathname: `${process.env.PUBLIC_URL}/search-model/${params.srno}/model`,
      state: data,
    });
  };

  const tableData = [
    {
      position: "1",
      modelName: "Model 1",
      score: "1",
    },
  ];

  const columns = [
    {
      name: "position",
      label: "#Position",
    },
    {
      name: "modelName",
      label: "Model Name",
    },
    {
      name: "score",
      label: "Score",
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No benchmark dataset available",
      },
    },
    print: false,
    viewColumns: false,
    selectableRows: "none",
    displaySelectToolbar: false,
    fixedHeader: false,
    download: false,
    search: false,
    filter: false,
  };

  const handleIndexChange = (metric) => {
    setIndex(metricArray.indexOf(metric));
  };

  const handleCardNavigation = () => {
    history.push(`${process.env.PUBLIC_URL}/model/benchmark-models`);
  };

  return (
    <MuiThemeProvider theme={Theme}>
      <Header style={{ marginBottom: "10px" }} />
      {data && (
        <div className={classes.parentPaper}>
          <Button
            size="small"
            color="primary"
            className={classes.backButton}
            startIcon={<ArrowBack />}
            onClick={() => handleCardNavigation()}
          >
            {/* {prevUrl === "explore-models"
              ? "Back to Model List"
              : "Back to My Contribution"} */}
            Back to Benchmark Model
          </Button>

          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <Typography variant="h5" className={classes.mainTitle}>
              {data.modelName}
            </Typography>
          </div>
          <Divider className={classes.gridCompute} />
          <Grid container>
            <Grid item xs={12} sm={12} md={9} lg={9} xl={9}>
              {description.map((des) => (
                <ModelDescription title={des.title} para={des.para} />
              ))}
            </Grid>
          </Grid>
          {metricArray.length ? (
            <Grid container>
              <Grid item xs={12} sm={12} md={9} lg={9} xl={9}>
                <Box
                  sx={{
                    bgcolor: "background.paper",
                    width: 500,
                  }}
                >
                  <AppBar
                    color="transparent"
                    style={{ border: "none", marginTop: "3%" }}
                    position="static"
                  >
                    <Tabs
                      value={value}
                      onChange={handleChange}
                      variant="scrollable"
                      scrollButtons={false}
                      aria-label="scrollable prevent tabs example"
                    >
                      {metricArray.map((metric) => (
                        <Tab
                          label={metric}
                          onClick={() => handleIndexChange(metric)}
                        />
                      ))}
                    </Tabs>
                  </AppBar>

                  <TabPanel value={value} index={index}>
                    <MUIDataTable
                      data={tableData}
                      columns={columns}
                      options={options}
                    />
                  </TabPanel>
                </Box>
              </Grid>
            </Grid>
          ) : (
            <div></div>
          )}
        </div>
      )}
      <Footer />
    </MuiThemeProvider>
  );
};

export default withStyles(DatasetStyle)(SearchModelDetail);
