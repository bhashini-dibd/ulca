import DataTable from "../../components/common/DataTable";
import {
  withStyles,
  createMuiTheme,
  Button,
  Typography,
  Grid,
  Box,
  CircularProgress,
  TextField,
  TableCell,
  Table,
  Switch,
} from "@material-ui/core";
import Search from "../../components/Datasets&Model/Search";
import { MuiThemeProvider } from "@material-ui/core/styles";
// import createMuiTheme from "../../styles/Datatable";
import MUIDataTable from "mui-datatables";
import { translate } from "../../../assets/localisation";
import FilterListIcon from "@material-ui/icons/FilterList";
import { Cached } from "@material-ui/icons";
import DataSet from "../../styles/Dataset";
import Modal from "../../components/common/Modal";
import { useEffect,  useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import AddBoxIcon from "@material-ui/icons/AddBox";
import GenerateAPI from "../../../redux/actions/api/UserManagement/GenerateApiKey";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import CustomizedSnackbars from "../../components/common/Snackbar";
import Spinner from "../../components/common/Spinner";
import FetchApiKeysAPI from "../../../redux/actions/api/UserManagement/FetchApiKeys";
import RevokeApiKeyAPI from "../../../redux/actions/api/UserManagement/RevokeApiKey";
import Snackbar from "../../components/common/Snackbar";
import RevokeDialog from "../../components/common/RevokeDialog";
import TableRow from "@material-ui/core/TableRow";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import getSearchedValue from "../../../redux/actions/api/DataSet/DatasetSearch/GetSearchedValues";
import ServiceProviderDialog from "../../components/common/ServiceProviderDialog";
import removeServiceProviderKeyAPI from "../../../redux/actions/api/UserManagement/RemoveServiceProviderKey";
import GenerateServiceProviderKeyAPI from "../../../redux/actions/api/UserManagement/GenerateServiceProviderKey";
import DataTrackingToggleAPI from "../../../redux/actions/api/UserManagement/DataTrackingToggle";

const SwitchCases = ({
  dataTrackingValue,
  setSnackbarInfo,
  setLoading,
  serviceProviderName,
  Ulcakey,
}) => {
  const [checked, setChecked] = useState(dataTrackingValue);
  useEffect(() => {
    setChecked(dataTrackingValue);
  }, [dataTrackingValue]);

  const handleChangedataTrackingToggle = async (event) => {
    setChecked((pre) => !pre);
    setLoading(true);
    const apiObj = new DataTrackingToggleAPI(
      Ulcakey,
      serviceProviderName,
      event.target.checked
    );
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });

    const resp = await res.json();
    if (res.ok) {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "success",
      });
      setLoading(false);
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });

      setLoading(false);
    }
  };

  return (
    <>
      <Switch
        size="small"
        color="primary"
        checked={checked}
        onChange={handleChangedataTrackingToggle}
        inputProps={{ "aria-label": "Switch demo" }}
      />
    </>
  );
};

const MyProfile = (props) => {
  const { classes } = props;
  const dispatch = useDispatch();

  const apiKeys = useSelector((state) => state.getApiKeys.apiKeys);

  const [loading, setLoading] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [modal, setModal] = useState(false);
  const [appName, setAppName] = useState("");
  const [message, setMessage] = useState("Are sure u want to Revoke ?");
  const [open, setOpen] = useState(false);
  const [UlcaApiKey, setUlcaApiKey] = useState("");
  const [searchKey, setSearchKey] = useState("");
  const userDetails = JSON.parse(localStorage.getItem("userDetails"));
  const [openServiceProviderDialog, setOpenServiceProviderDialog] =
    useState(false);
  const [serviceProviderName, setServiceProviderName] = useState("");
  const [expandableRow, setExpandableRow] = useState([]);


  useEffect(() => {
    if (apiKeys) {
      setTableData(apiKeys);
    }
  }, [apiKeys]);

  const handlecChangeAddName = (e) => {
    setAppName(e.target.value);
  };
  const handleClose = () => {
    setModal(false);
    setAppName("");
    setOpen(false);
    setOpenServiceProviderDialog(false);
  };

  const onKeyDown = (event) => {
    if (event.keyCode == 27) {
      setModal(false);
    }
  };

  const UserDetails = JSON.parse(localStorage.getItem("userDetails"));

  useEffect(() => {
    window.addEventListener("keydown", onKeyDown);
    return () => window.removeEventListener("keydown", onKeyDown);
  }, [onKeyDown]);

  const handleSearch = (value) => {
    setSearchKey(value);
    // prepareDataforTable(columns, tableData, value);
    dispatch(getSearchedValue(value));
  };

  const handleSubmitGenerateApiKey = async () => {
    const data = {
      userID: userDetails?.userID,
      appName: appName,
    };
    const userObj = new GenerateAPI(data);
    const res = await fetch(userObj.apiEndPoint(), {
      method: "POST",
      headers: userObj.getHeaders().headers,
      body: JSON.stringify(userObj.getBody()),
    });

    const resp = await res.json();
    console.log(resp, "");
    if (res.ok) {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "success",
      });
      await getApiKeysCall();
      setLoading(false);
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });
      setLoading(false);
    }
    setModal(false);
    setAppName("");
  };

  const getApiKeysCall = async () => {
    const apiObj = new FetchApiKeysAPI();
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    getApiKeysCall();
  }, []);

  const revokeApiKeyCall = async () => {
    setLoading(true);
    const apiObj = new RevokeApiKeyAPI(UlcaApiKey);

    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });

    const resp = await res.json();
    if (res.ok) {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "success",
      });
      setLoading(false);
      await getApiKeysCall();
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });
      setLoading(false);
    }
    setOpen(false);
  };
  const handleDialogSubmit = (ulcaApiKey) => {
    setOpen(true);
    setUlcaApiKey(ulcaApiKey);
  };

  const pageSearch = () => {
    return tableData.filter((el) => {
      if (searchKey == "") {
        return el;
      } else if (el.appName?.toLowerCase().includes(searchKey?.toLowerCase())) {
        return el;
      }
    });
  };
  const handleSubmitServiceProviderKey = (serviceProviderName, ulcaApiKey) => {
    setOpenServiceProviderDialog(true);
    setServiceProviderName(serviceProviderName);
    setUlcaApiKey(ulcaApiKey);
  };

  const handleRemoveServiceProviderKey = async () => {
    const data = {
      userID: UserDetails.userID,
      ulcaApiKey: UlcaApiKey,
      serviceProviderName: serviceProviderName,
    };
    setLoading(true);
    const apiObj = new removeServiceProviderKeyAPI(
      UlcaApiKey,
      serviceProviderName
    );
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });

    const resp = await res.json();
    if (res.ok) {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "success",
      });
      setLoading(false);
      await getApiKeysCall();
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });

      setLoading(false);
    }
    setOpenServiceProviderDialog(false);
  };

  const fetchHeaderButton = () => {
    return (
      <Grid container>
        <Grid
          item
          xs={9}
          sm={8}
          md={8}
          lg={9}
          xl={9}
          style={{ display: "flex", justifyContent: "flex-start" }}
        >
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
        </Grid>
        {/* <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGridMobile}
        >
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            // onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
          </Button>
        </Grid> */}
        {/* <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGrid}
        >
          <Button
            color={"default"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            // onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
            {translate("button.filter")}
          </Button>
        </Grid> */}

        <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGrid}
          style={{ marginLeft: "100px" }}
        >
          <Button
            color="primary"
            size="medium"
            variant="contained"
            className={classes.ButtonRefresh}
            onClick={() => {
              setModal(true);
            }}
            style={{
              height: "36px",
              textTransform: "capitalize",
              fontSize: "1rem",
            }}
          >
            {" "}
            {translate("button.generate")}
          </Button>
        </Grid>

        <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGridMobile}
        >
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            onClick={() => {
              setModal(true);
            }}
            style={{ height: "37px" }}
          >
            {" "}
            <AddBoxIcon color="primary" className={classes.iconStyle} />
          </Button>
        </Grid>
      </Grid>
    );
  };

  const getMuiTheme = () =>
    createMuiTheme({
      overrides: {
        MuiTable: {
          root: {
            borderCollapse: "hidden",
          },
        },
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
            maxWidth: "100%",
            minHeight: "560px",
            boxShadow: "0px 0px 0px #00000029",
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
        },
        MuiTableRow: {
          root: {
            border: "1px solid #3A3A3A1A",
            opacity: 1,
            "&$hover:hover:nth-child(odd)": { backgroundColor: "#D6EAF8" },
            "&$hover:hover:nth-child(even)": { backgroundColor: "#E9F7EF" },
          },
        },
        MUIDataTableBodyCell: {
          stackedCommon: {
            "@media (max-width: 400px)": {
              width: " 30%",
              height: "auto",
            },
          },
        },
        MUIDataTableHeadCell: {
          root: {
            "&$nth-child(1)": {
              width: "3%",
            },
          },
        },
        MuiTypography: {
          h6: {
            fontSize: "1.125rem",
            fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
            fontWeight: "300",
            paddingTop: "4px",
            lineHeight: "1.6px",
          },
        },
        MUIDataTableToolbar: {
          left: {
            flex: 0,
          },
        },
      },
    });

  const columns = [
    {
      name: "appName",
      label: "App Name",
      options: {
        filter: false,
        sort: false,
        align: "center",
      },
    },
    {
      name: "ulcaApiKey",
      label: "ULCA API Key",
      options: {
        filter: false,
        sort: false,
        align: "center",
      },
    },
    {
      name: "serviceProviderKeys",
      label: "Service Provider Keys",
      options: {
        display: "excluded",
      },
    },
    {
      name: "action",
      label: "Action",
      options: {
        filter: false,
        sort: false,
        align: "center",
        setCellHeaderProps: () => ({
          style: {
            paddingLeft: "37px",
          },
        }),
        customBodyRender: (value, tableMeta) => {
          return (
            <Button
              variant="contained"
              className={classes.myProfileActionBtn}
              onClick={() => handleDialogSubmit(tableMeta.rowData[1])}
              style={{ color: "red", textTransform: "capitalize" }}
              // style={{ textTransform: "capitalize" }}
            >
              {loading ? (
                <CircularProgress color="primary" size={20} />
              ) : (
                "Revoke"
              )}
            </Button>
          );
        },
      },
    },
  ];

  const handleGenerateInferenceAPIKey = async (providerName, ulcaKey) => {
    const apiObj = new GenerateServiceProviderKeyAPI(ulcaKey, providerName);
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });

    const resp = await res.json();
    if (res.ok) {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "success",
      });
      await getApiKeysCall();
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });
    }
  };

  const data =
    tableData && tableData.length > 0
      ? pageSearch().map((el, i) => {
          return [el.appName, el.ulcaApiKey, el.serviceProviderKeys];
        })
      : [];

  const handleRowExpand = (_currentRow, allRow) => {
    let temp = [];
    allRow.forEach((element) => {
      temp.push(element.dataIndex);
    });

    setExpandableRow(temp);
  };

  const options = {
    textLabels: {
      body: {
        noMatch: "No records",
      },
      toolbar: {
        search: "Search",
        viewColumns: "View Column",
      },
      pagination: {
        rowsPerPage: "Rows per page",
      },
    },
    customToolbar: fetchHeaderButton,
    print: false,
    viewColumns: false,
    rowsPerPageOptions: false,
    selectableRows: "none",
    fixedHeader: false,
    download: false,
    search: false,
    filter: false,
    expandableRowsOnClick: false,
    expandableRows: true,
    expandableRowsHeader: true,
    displaySelectToolbar: false,
    disableToolbarSelect: "none",
    onRowExpansionChange: (currentRowsExpanded, allRowsExpanded) => {
      handleRowExpand(currentRowsExpanded, allRowsExpanded);
    },
    rowsExpanded: expandableRow,
    renderExpandableRow: (rowData, rowMeta) => {
      const data = rowData[2];
      if (data?.length)
        return (
          <>
            <TableRow>
              <TableCell colSpan={5}>
                <>
                  <Box style={{ margin: "0 80px" }}>
                    <Table size="small" aria-label="purchases">
                      <TableHead style={{ height: "60px" }}>
                        <TableCell style={{ whiteSpace: "nowrap" }}>
                          Service Provider Name
                        </TableCell>
                        <TableCell style={{ whiteSpace: "nowrap" }}>
                          Inference API Key Name
                        </TableCell>
                        <TableCell style={{ width: "60%" }}>
                          Inference API Key Value
                        </TableCell>
                        <TableCell style={{ whiteSpace: "nowrap" }}>
                          Data Tracking
                        </TableCell>
                        <TableCell
                          style={{ paddingLeft: "50px", width: "15%" }}
                        >
                          Action
                        </TableCell>
                      </TableHead>
                      <TableBody>
                        {data.map((row, i) => {
                          return (
                            <TableRow
                              style={{
                                backgroundColor: "rgba(254, 191, 44, 0.1)",
                              }}
                              key={i}
                            >
                              <TableCell style={{ width: "18%" }}>
                                {row?.serviceProviderName}
                              </TableCell>
                              <TableCell style={{ width: "19%" }}>
                                {row?.inferenceApiKey?.name ?? "-"}
                              </TableCell>
                              <TableCell style={{ width: "60%" }}>
                                {row?.inferenceApiKey?.value ?? "-"}
                              </TableCell>
                              <TableCell style={{ width: "60%" }}>
                                {row?.inferenceApiKey?.value ? (
                                  <SwitchCases
                                    dataTrackingValue={row?.dataTracking}
                                    setLoading={setLoading}
                                    setSnackbarInfo={setSnackbarInfo}
                                    serviceProviderName={
                                      row?.serviceProviderName
                                    }
                                    Ulcakey={rowData[1]}
                                  />
                                ) : (
                                  <Switch
                                    disabled
                                    size="small"
                                    inputProps={{ "aria-label": "Switch demo" }}
                                  />
                                )}
                              </TableCell>
                              <TableCell style={{ width: "15%" }}>
                                {row?.inferenceApiKey?.value ? (
                                  <Button
                                    variant="contained"
                                    className={classes.myProfileActionBtn}
                                    onClick={() =>
                                      handleSubmitServiceProviderKey(
                                        row?.serviceProviderName,
                                        rowData[1]
                                      )
                                    }
                                    style={{
                                      height: "30px",
                                      margin: "5px",
                                      color: "red",
                                      textAlign: "center",
                                      textTransform: "capitalize",
                                    }}
                                  >
                                    Revoke
                                  </Button>
                                ) : (
                                  <Button
                                    color="primary"
                                    variant="contained"
                                    className={classes.myProfileGenerateButton}
                                    onClick={() =>
                                      handleGenerateInferenceAPIKey(
                                        row?.serviceProviderName,
                                        rowData[1]
                                      )
                                    }
                                  >
                                    Generate
                                  </Button>
                                )}
                              </TableCell>
                            </TableRow>
                          );
                        })}
                      </TableBody>
                    </Table>
                  </Box>
                </>
              </TableCell>
            </TableRow>
          </>
        );
      return <></>;
    },
  };

  const renderSnackBar = () => {
    return (
      <CustomizedSnackbars
        open={snackbar.open}
        handleClose={() =>
          setSnackbarInfo({ open: false, message: "", variant: "" })
        }
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
        variant={snackbar.variant}
        message={snackbar.message}
      />
    );
  };

  return (
    <>
      {renderSnackBar()}
      {loading && <Spinner />}
      <Grid container direction="row" spacing={2}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Typography variant="h3" component="h2" align="center">
            App Integration Details
          </Typography>
        </Grid>
        <Typography
          variant="body"
          style={{
            margin: "30px 10px 12px",
            fontSize: "16px",
            marginLeft: "auto",
          }}
        >
          User ID : {UserDetails.userID}
        </Typography>
      </Grid>

      <MuiThemeProvider theme={getMuiTheme}>
        <MUIDataTable data={data} columns={columns} options={options} />
      </MuiThemeProvider>
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <Grid
          container
          direction="row"
          spacing={0}
          style={{ textAlign: "end", marginTop: "25px" }}
        >
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
            <TextField
              fullWidth
              id="outlined-basic"
              label="Enter App Name"
              variant="outlined"
              value={appName}
              onChange={handlecChangeAddName}
            />
          </Grid>
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
            <Button
              variant="text"
              color="primary"
              style={{
                borderRadius: "20px",
                marginTop: "20px",
                marginRight: "10px",
              }}
              onClick={handleClose}
            >
              Cancel
            </Button>
            <Button
              variant="contained"
              color="primary"
              style={{ borderRadius: "20px", marginTop: "20px" }}
              onClick={handleSubmitGenerateApiKey}
              disabled={appName ? false : true}
            >
              Submit
            </Button>
          </Grid>
        </Grid>
      </Modal>

      {open && (
        <RevokeDialog
          open={open}
          handleClose={handleClose}
          submit={() => revokeApiKeyCall()}
        />
      )}

      {openServiceProviderDialog && (
        <ServiceProviderDialog
          open={openServiceProviderDialog}
          handleClose={handleClose}
          submit={() => handleRemoveServiceProviderKey()}
        />
      )}

      {/* {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={setSnackbarInfo({ ...snackbar, open: false })}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )} */}
    </>
  );
};

export default withStyles(DataSet)(MyProfile);
