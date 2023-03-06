import DataTable from "../../components/common/DataTable";
import { withStyles, Button, Typography, Grid, Box, CircularProgress } from "@material-ui/core";
import Search from "../../components/Datasets&Model/Search";
import { translate } from "../../../assets/localisation";
import FilterListIcon from "@material-ui/icons/FilterList";
import { Cached } from "@material-ui/icons";
import DataSet from "../../styles/Dataset";
import Spinner from "../../components/common/Spinner";
import FetchApiKeysAPI from "../../../redux/actions/api/UserManagement/FetchApiKeys";
import RevokeApiKeyAPI from "../../../redux/actions/api/UserManagement/RevokeApiKey";
import Snackbar from '../../components/common/Snackbar';
import { useEffect, useState } from "react";

const MyProfile = (props) => {
  const {classes} = props;
  const [loading, setLoading] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });

  const getApiKeysCall = async () => {
    setLoading(true);
    const apiObj = new FetchApiKeysAPI();

    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    });

    const resp = await res.json();
    if (res.ok) {
      setTableData(resp?.data);
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

  useEffect(() => {
    getApiKeysCall();
  }, [])

  const revokeApiKeyCall = async (ulcaApiKey) => {
    setLoading(true);
    const apiObj = new RevokeApiKeyAPI(ulcaApiKey);

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
  };

  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={1} className={classes.Gridroot}>
        <Grid item xs={7} sm={8} md={8} lg={8} xl={8}>
          <Search value=""  />
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
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
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
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
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
          <Button
            color={"primary"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            // onClick={() => MyContributionListApi()}
          >
            <Cached className={classes.iconStyle} />
            {translate("button.refresh")}
          </Button>
        </Grid>
        <Grid item xs={3} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
          <Button
            color={"primary"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            // onClick={() => MyContributionListApi()}
          >
           
            <Cached className={classes.iconStyle} />
          </Button>
        </Grid>
      </Grid>
    );
  };

  const columns = [
    {
      name: "appName",
      label: "App Name",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "ulcaApiKey",
      label: "Ulca Api Key",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "action",
      label: "Action",
      options: {
        filter: false,
        sort: false,
        customBodyRender: (value, tableMeta) => {
          return (
            <Button
              variant="contained"
              className={classes.myProfileActionBtn}
              onClick={() => revokeApiKeyCall(tableMeta.rowData[1])}
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
  
  const options = {
    textLabels: {
      body: {
        noMatch: loading ? <Spinner /> : "No records",
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
    expandableRowsHeader: false,
    expandableRowsOnClick: true,
    expandableRows: false,
  };

  return (
    <div>
      <DataTable
        data={tableData}
        columns={columns}
        options={options}
      />

      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={setSnackbarInfo({ ...snackbar, open: false })}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )}
    </div>
  );
};

export default withStyles(DataSet)(MyProfile);