import { withStyles, createMuiTheme } from "@material-ui/core/styles";
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
  const data = useSelector((state) => state.benchmarkDetails);
  const dispatch = useDispatch();
  useEffect(() => {
    const apiObj = new BenchmarkDetails(benchmarkId);
    dispatch(APITransport(apiObj));
  }, [benchmarkId]);

  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const [index, setIndex] = useState(0);
  const [modelTry, setModelTry] = useState(false);
  const location = useLocation();
  const { prevUrl } = location.state ? location.state : "";
  const metricArray = data.metricArray;
  const [metric, setMetric] = useState("");
  const tableData = useSelector(
    (state) => state.benchmarkDetails.benchmarkPerformance
  );
  useEffect(() => {
    window.scrollTo(0, 0);
  });

  useEffect(() => {
    setMetric(metricArray[0]);
  }, [metricArray]);

  const description = [
    {
      title: "",
      para: "",
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
      title: "Submitter",
      para: data.submitter,
    },
  ];
  const handleCardNavigation = () => {
    if (prevUrl === "explore-models") {
      history.push(`${process.env.PUBLIC_URL}/model/benchmark-datasets`);
    } else {
      // history.push(`${process.env.PUBLIC_URL}/model/explore-models`);
      history.goBack();
    }
  };

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
        noMatch: "No records available",
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
    setMetric(metric);
  };

  const getMuiTheme = () =>
    createMuiTheme({
      overrides: {
        MUIDataTableBodyRow: {
          root: {
            "&:nth-child(odd)": {
              backgroundColor: "#D6EAF8",
            },
            "&:nth-child(even)": {
              backgroundColor: "#E9F7EF",
            },
          },
        },
        MUIDataTable: {
          paper: {
            minHeight: "560px",
            boxShadow: "0px 0px 2px #00000029",
            border: "1px solid #0000001F",
          },
          responsiveBase: {
            minHeight: "560px",
          },
        },
        MuiTableCell: {
          head: {
            // padding: ".6rem .5rem .6rem 1.5rem",
            backgroundColor: "#F8F8FA !important",
            marginLeft: "25px",
            letterSpacing: "0.74",
            fontWeight: "bold",
            minHeight: "700px",
          },
          paddingCheckbox: {
            display: "none",
          },
        },
        MuiTableRow: {
          root: {
            border: "1px solid #3A3A3A1A",
            opacity: 1,
            "&$hover:hover:nth-child(odd)": { backgroundColor: "#D6EAF8" },
            "&$hover:hover:nth-child(even)": { backgroundColor: "#E9F7EF" },
          },
        },
        MUIDataTableHeadCell: {
          root: {
            "&$nth-child(1)": {
              width: "3%",
            },
          },
        },
      },
    });

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
            {prevUrl === "explore-models"
              ? "Back to Benchmark Datasets"
              : "Back to Model Description"}
            {/* Back to Benchmark Datasets */}
          </Button>

          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <Typography variant="h5" className={classes.mainTitle}>
              {data.modelName}
            </Typography>
          </div>
          <Divider className={classes.gridCompute} />
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
            <Typography variant="h6" className={classes.modelTitle}>Description</Typography>
            <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }}
              className={classes.modelPara}>{data.description}</Typography>
          </Grid>
          <Grid container style={{ marginTop: '30px' }}>
            <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
              <Grid container spacing={1}>
                {description.map((des, i) => (
                  <>{
                    des.title !== "" &&
                    <Grid item xs={3} sm={3} md={3} lg={3} xl={3}>
                      <ModelDescription title={des.title} para={des.para} index={i} />
                    </Grid>
                  }
                  </>
                ))}
              </Grid>
            </Grid>
          </Grid>
          {metricArray.length ? (
            <Grid container>
              <Grid item xs={12} sm={12} md={9} lg={9} xl={9}>
                <Typography
                  style={{ marginTop: "3%" }}
                  variant="h5"
                  className={classes.mainTitle}
                >
                  Model Leaderboard
                </Typography>
              </Grid>
              <Grid item xs={12} sm={12} md={9} lg={9} xl={9}>
                <Box
                  sx={{
                    bgcolor: "background.paper",
                    width: 500,
                  }}
                >
                  <AppBar
                    color="transparent"
                    style={{ border: "none" }}
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
                    <MuiThemeProvider theme={getMuiTheme()}>
                      <MUIDataTable
                        data={tableData[metric]}
                        columns={columns}
                        options={options}
                      />
                    </MuiThemeProvider>
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
