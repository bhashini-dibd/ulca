import DataTable from "../../components/common/DataTable";
import { withStyles, Button, Typography, Grid, Box, CircularProgress , TextField,} from "@material-ui/core";
import Search from "../../components/Datasets&Model/Search";
import { translate } from "../../../assets/localisation";
import FilterListIcon from "@material-ui/icons/FilterList";
import { Cached } from "@material-ui/icons";
import DataSet from "../../styles/Dataset";
import Modal from  "../../components/common/Modal"
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import AddBoxIcon from '@material-ui/icons/AddBox';
import GenerateAPI from "../../../redux/actions/api/UserManagement/GenerateApiKey";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import CustomizedSnackbars from "../../components/common/Snackbar";
import Spinner from "../../components/common/Spinner";
import FetchApiKeysAPI from "../../../redux/actions/api/UserManagement/FetchApiKeys";
import RevokeApiKeyAPI from "../../../redux/actions/api/UserManagement/RevokeApiKey";
import Snackbar from '../../components/common/Snackbar';
import RevokeDialog from "../../components/common/RevokeDialog";


const MyProfile = (props) => {
  const {classes} = props;
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
  const [UlcaApiKey, setUlcaApiKey] = useState('');
  const userDetails = JSON.parse(localStorage.getItem("userDetails"));

  const handlecChangeAddName = (e) =>{
    setAppName(e.target.value)
  }
  const handleClose = () =>{
    setModal(false)
    setAppName("")
    setOpen(false)
  }

const handleSubmitGenerateApiKey =  async() =>{
  const data={
    userID : userDetails?.userID,
    appName : appName
  }
  const userObj = new GenerateAPI(data);
  const res = await fetch(userObj.apiEndPoint(), {
    method: "POST",
    headers: userObj.getHeaders().headers,
    body: JSON.stringify(userObj.getBody()),
  });

  const resp = await res.json();
  console.log(resp,"")
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
  setModal(false)
  setAppName("")
}

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
    setOpen(false)
  };
  const handleDialogSubmit = (ulcaApiKey) => {
    setOpen(true)
    setUlcaApiKey(ulcaApiKey)
   };

  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={1} className={classes.Gridroot}>
        <Grid item xs={7} sm={8} md={8} lg={8} xl={8}>
          <Search value="" />
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
            // onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
          </Button>
        </Grid>
        <Grid
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
        </Grid>
       
        <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGrid}
        >
          <Button
            color="primary"
            size="medium"
            variant="contained"
            className={classes.ButtonRefresh}
            onClick={() => {
              setModal(true);
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
          >
            {" "}
            <AddBoxIcon color="primary" className={classes.iconStyle} />
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
        align: "center",
      },
    },
    {
      name: "ulcaApiKey",
      label: "Ulca Api Key",
      options: {
        filter: false,
        sort: false,
        align: "center",
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
          style: { paddingLeft: "46px" },
        }),
        customBodyRender: (value, tableMeta) => {
          return (
            <Button
              variant="contained"
              className={classes.myProfileActionBtn}
              onClick={ () => handleDialogSubmit(tableMeta.rowData[1])}
              style={{color:"red"}}
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
    <div>
       {renderSnackBar()}
      <DataTable
        data={tableData}
        columns={columns}
        options={options}
      />
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <Grid container direction="row" spacing={0} style={{textAlign: "end",marginTop:"25px"}} >
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
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
              variant="contained"
              color="primary"
              style={{ borderRadius: "20px", marginTop: "20px",marginRight:"10px" }}
              onClick={handleClose}
            >
              Close
            </Button>
            <Button
              variant="contained"
              color="primary"
              style={{ borderRadius: "20px", marginTop: "20px" }}
              onClick={handleSubmitGenerateApiKey}
              disabled={
                appName 
                  ? false
                  : true
              }
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
    

      {/* {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={setSnackbarInfo({ ...snackbar, open: false })}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )} */}
    </div>
  );
};

export default withStyles(DataSet)(MyProfile);
