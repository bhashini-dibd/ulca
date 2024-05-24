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

  Tooltip,
  IconButton,
  Container,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from "@material-ui/core";
import InfoIcon from '@material-ui/icons/Info';
import Search from "../../components/Datasets&Model/Search";
import { MuiThemeProvider } from "@material-ui/core/styles";
// import createMuiTheme from "../../styles/Datatable";
import MUIDataTable from "mui-datatables";

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



const GlossaryProfile = (props) => {
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
 
  const [formState, setFormState] = useState({
    option1: '',
    option2: '',
    firstName: '',
    lastName: '',
  });


  useEffect(() => {
    if (apiKeys) {
      setTableData(apiKeys);
    }
  }, [apiKeys]);




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



  const getApiKeysCall = async () => {
    const apiObj = new FetchApiKeysAPI();
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    getApiKeysCall();
  }, []);

 

  const fetchHeaderButton = () => {
    return (
      <Grid container>
        <Grid
          item
          xs={8}
          sm={8}
          md={8}
          lg={8}
          xl={8}
          style={{ display: "flex", justifyContent: "flex-start" }}
        >
          {/* <Search value="" handleSearch={(e) => handleSearch(e.target.value)} /> */}
          <Typography variant="h5">Glossary List</Typography>
        </Grid>
      

        <Grid
          item
          xs={3}
          sm={3}
          md={3}
          lg={3}
          xl={3}
          className={classes.filterGrid}
          style={{ marginLeft: "100px" }}
        >
          <Button
            color="primary"
            size="medium"
            variant="contained"
            className={classes.ButtonRefresh}
            // onClick={() => {
            //   setModal(true);
            // }}
            style={{
              height: "36px",
              textTransform: "capitalize",
              fontSize: "1rem",
              marginRight:"10px"
            }}
          >
            {" "}
            Bulk Upload
          </Button>
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
            Create Glossary
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

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Form Data:', formState);
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
          name: "source",
          label: "Source",
        },
        {
          name: "target",
          label: "Target",
        },
        {
          name: "sourceLanguage",
          label: "Source Language",
        },
        {
          name: "targetLanguage",
          label: "Target Language",
        },
        {
          name: "glossary",
          label: "Glossary",
          options: {
            customBodyRender: (value) => (
              <Box display='flex' alignItems="center">
                <Box>{value}</Box>
               
              </Box>
            )
          }
        },
        {
          name: "action",
          label: "Action",
          options: {
            customBodyRender: (value, tableMeta) => (
              <Button
                variant="contained"
                onClick={() => console.log('Action clicked for row:', tableMeta.rowIndex)}
              >
                Delete
              </Button>
            )
          }
        }
      ];
      
      const Glossarydata = [
        {
          source: "Dummy Source 1",
          target: "Dummy Target 1",
          sourceLanguage: "English",
          targetLanguage: "French",
          glossary: "Dummy Glossary 1",
          action: "Dummy Action 1",
        },
        {
          source: "Dummy Source 2",
          target: "Dummy Target 2",
          sourceLanguage: "Spanish",
          targetLanguage: "German",
          glossary: "Dummy Glossary 2",
          action: "Dummy Action 2",
        },
        // Add more dummy data as needed
      ];






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
     

      <MuiThemeProvider theme={getMuiTheme}>
        <MUIDataTable data={Glossarydata} columns={columns} options={options} />
      </MuiThemeProvider>
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
      
    <Container>
      <form noValidate autoComplete="off" onSubmit={handleSubmit}>
        <Typography variant="h4" fontWeight="bold" style={{marginBottom:"20px", marginTop:"20px"}}>Create Glossary</Typography>
        <Grid container spacing={2} alignItems="center">
          {/* First Select Box */}
          <Grid item xs={12} sm={6}>
            <Typography variant="h6">Select Source Language</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth variant="outlined">
              <InputLabel id="demo-simple-select-label">Select Source Language</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                name="option1"
                value={formState.option1}
                onChange={handleChange}
                label="Select Source Language"
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem value="option1.1">Option 1.1</MenuItem>
                <MenuItem value="option1.2">Option 1.2</MenuItem>
                <MenuItem value="option1.3">Option 1.3</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          {/* Second Select Box */}
          <Grid item xs={12} sm={6}>
            <Typography variant="h6">Select Target Language</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth variant="outlined">
              <InputLabel id="select-option-2-label">Select Target Language</InputLabel>
              <Select
                labelId="select-option-2-label"
                id="select-option-2"
                name="option2"
                value={formState.option2}
                onChange={handleChange}
                label="Select Target Language"
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem value="option2.1">Option 2.1</MenuItem>
                <MenuItem value="option2.2">Option 2.2</MenuItem>
                <MenuItem value="option2.3">Option 2.3</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          {/* First Text Field */}
          <Grid item xs={12} sm={6}>
            <Typography variant="h6">Enter Source Text</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              fullWidth
              label="Enter Source Text"
              name="firstName"
              value={formState.firstName}
              onChange={handleChange}
            />
          </Grid>

          {/* Second Text Field */}
          <Grid item xs={12} sm={6}>
            <Typography variant="h6">Enter Target Text</Typography>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              fullWidth
              label="Enter Target Text"
              name="lastName"
              value={formState.lastName}
              onChange={handleChange}
            />
          </Grid>

          {/* Submit Button */}
          <Grid item xs={12} style={{display:"flex", justifyContent:"end", gap:"20px", marginTop:"20px"}} >
            <Button
              variant="contained"
              color="primary"
              onClick={() => setModal(false)}
              style={{padding:"12px 24px"}}
            >
              Cancel
            </Button>
            <Button
              variant="contained"
              color="primary"
              type="submit"
              style={{padding:"12px 24px"}}
            >
              Submit
            </Button>
          </Grid>
        </Grid>
      </form>
    </Container>
      </Modal>

   

     
    </>
  );
};

export default withStyles(DataSet)(GlossaryProfile);
