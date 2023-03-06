import {
  withStyles,
  MuiThemeProvider,
  createTheme,
  Button,
  Typography,
  Grid
} from "@material-ui/core";
import BreadCrum from "../../../components/common/Breadcrum";
import React, { useEffect } from "react";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import DetailedDatasetStatus from "../../../../redux/actions/api/DataSet/DatasetView/DetailedDataset";
import ErrorFileDownload from "../../../../redux/actions/api/DataSet/DatasetView/ErrorDownload";
import { useDispatch, useSelector } from "react-redux";
import { Cached, SaveAlt } from "@material-ui/icons";
import UrlConfig from "../../../../configs/internalurlmapping";
import { useParams } from "react-router";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import { translate } from "../../../../assets/localisation";

const DetailedStatus = (props) => {
  const { detailedReport, errorData } = useSelector((state) => state);
  const dispatch = useDispatch();
  const { id } = useParams();

  const getStatusDisable = () => {
    const res = detailedReport.responseData.some(
      (element) => ["pending", "in-progress"].includes(element.status.toLowerCase())
    );
    return !res;
  };

  useEffect(() => {
    DetailedDataSetStatusApi();
  }, []);

  const DetailedDataSetStatusApi = () => {
    const userObj = new DetailedDatasetStatus(id);
    const userId = new ErrorFileDownload(id);
    dispatch(APITransport(userObj));
    dispatch(APITransport(userId));
  };

  const handleDownload = () => { };

  const getMuiTheme = () =>
    createTheme({
      overrides: {
        MuiTableCell: {
          head: {
            backgroundColor: "#c7c6c68a !important",
            fontWeight: "bold",
          },
        },
        MUIDataTableBodyCell: { root: { textTransform: "capitalize" } },
        MuiToolbar: {
          root: {
            display: "none",
          },
        },
        MuiPaper: {
          root: {
            boxShadow: "none !important",
            borderRadius: 0,
            border: "1px solid rgb(224 224 224)",
          },
        },
      },
    });

  const fetchHeaderButton = () => {
    return (
      <div className={classes.headerButtons}>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={12} md={7} lg={7} xl={7}>
            <Typography variant="h5">{detailedReport.datasetName}</Typography>
          </Grid>
          <Grid item>
            {
              <><Button
                color={"primary"}
                size="medium"
                className={classes.ButtonRefresh}
                variant="outlined"
                disabled={getStatusDisable()}
                onClick={() => DetailedDataSetStatusApi()}
              >
                <Cached className={classes.iconStyle} />
                Refresh
              </Button>
                <Button
                  color={"primary"}
                  size="medium"
                  className={classes.ButtonRefreshMobile}
                  variant="outlined"
                  disabled={getStatusDisable()}
                  onClick={() => DetailedDataSetStatusApi()}
                >
                  <Cached className={classes.iconStyle} />
                  Refresh 
                </Button>
              </>
            }
            <Button
              color={"primary"}
              href={errorData.consolidated_file}
              target="_self"
              size="medium"
              variant="outlined"
              disabled={
                errorData.hasOwnProperty("consolidated_file") &&
                  errorData.consolidated_file
                  ? false
                  : true
              }
              className={
                !(errorData.status !== "completed")
                  ? classes.ButtonRefresh
                  : classes.buttonStyle
              }
              onClick={() => handleDownload()}
            >
              <SaveAlt className={classes.iconStyle} />
              {translate("button.errorSummary")}
            </Button>

            <Button
              color={"primary"}
              href={errorData.file}
              target="_self"
              size="medium"
              variant="outlined"
              disabled={
                errorData.hasOwnProperty("file") && errorData.file ? false : true
              }
              className={
                !(errorData.status !== "completed")
                  ? classes.ButtonRefresh
                  : classes.buttonStyle
              }
              onClick={() => handleDownload()}
            >
              <SaveAlt className={classes.iconStyle} />
              {translate("button.detailErrorLog")}
            </Button>
          </Grid>
        </Grid>
      </div>
    );
  };

  const handleAbort = () => {
    alert("Still in progress");
  };

  const columns = [
    {
      name: "srNo",
      label: "s id",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },
    {
      name: "datasetId",
      label: "Dataset ID",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },
    {
      name: "stage",
      label: "Stage",
      options: {
        filter: false,
        sort: false,
        customBodyRender: (value, tableMeta, updateValue) => {
          return (
            <div>
              {/* <Typography className={classes.hosted}>{tableMeta.rowData[2]} {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography> */}
              <Typography className={classes.hosted}>
                {tableMeta.rowData[2]}
              </Typography>
            </div>
          );
        },
      },
    },
    {
      name: "status",
      label: "Status",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "recordCount",
      label: "Success Count",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "failedCount",
      label: "Failed Count",
      options: {
        filter: false,
        sort: false,
      },
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No records ",
      },
      toolbar: {
        search: "Search",
        viewColumns: "View Column",
      },
      pagination: { rowsPerPage: "Rows per page" },
      options: { sortDirection: "desc" },
    },
    displaySelectToolbar: false,
    fixedHeader: false,
    filterType: "checkbox",
    download: false,
    print: false,
    filter: false,
    selectableRows: "none",
  };

  const { classes } = props;
  return (
    <div>
      {/* <div className  = {classes.breadcrum}>
                                <BreadCrum links = {[UrlConfig.dataset,UrlConfig.myContribution]} activeLink = "Dataset details" />
                        </div> */}
      <div className={classes.headerButtons}>{fetchHeaderButton()}</div>
      <MuiThemeProvider theme={getMuiTheme()}>
        <MUIDataTable
          title={`My Contribution`}
          data={detailedReport.responseData}
          columns={columns}
          options={options}
        />
      </MuiThemeProvider>
      {/* {status === "in-progress" && <div className = {classes.footerButtons}>
                                <Button color = {"primary" } size = "medium" variant = "outlined" className = {classes.backButton} onClick = {() => handleAbort()}>Abort Process</Button>
                                
                        </div>} */}
    </div>
  );
};

export default withStyles(DataSet)(DetailedStatus);
