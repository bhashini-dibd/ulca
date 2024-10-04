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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  useMediaQuery,
  Divider,
  DialogContentText,
} from "@material-ui/core";
import DeleteIcon from '@material-ui/icons/Delete';
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
import Delete from '../../../assets/deleteIcon.svg'
import Spe from '../../../assets/speakerbanner.jpg'
import { useLocation } from 'react-router-dom';
import FetchGlossaryDetails from "../../../redux/actions/api/UserManagement/FetchGlossaryDetails";
import AddGlossaryDataApi from "../../../redux/actions/api/UserManagement/AddGlossaryData";
import { useRef } from "react";
import deleteImg from '../../../assets/glossary/delete.svg';

import addImg from '../../../assets/glossary/add.svg';

import FileUpload from "./FileUpload";
import DeleteSpeakerApi from "../../../redux/actions/api/UserManagement/DeleteSpeaker";
import FetchSpeakerDetailsApi from "../../../redux/actions/api/UserManagement/FetchSpeakerDetails";

const styles = {
  bannerContainer: {
    position: 'relative',
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  bannerImage: {
    width: '100%',
    height: 'auto',
  },
  textContainer: {
    position: 'absolute',
    color: 'white',
    textAlign: 'center',
    padding: '100px',
    display:"flex",
    flexDirection:"column",
    gap:"20px"
  },
  heading: {
    fontSize: '2rem',
    margin: '0',
    fontFamily:"Noto-Bold",
    color:"black"
  },
  paragraph: {
    fontSize: '1rem',
    margin: '0',
    fontFamily:"Noto-Regular",
    color:"black",
    lineHeight:"21.79px"
  },
  // Media queries for responsiveness
  '@media (max-width: 768px)': {
    heading: {
      fontSize: '1.5rem',
    },
    paragraph: {
      fontSize: '0.875rem',
    },
  },
  '@media (max-width: 480px)': {
    heading: {
      fontSize: '1.2rem',
    },
    paragraph: {
      fontSize: '0.75rem',
    },
  },
};



  

const SpeakerEnrollment = (props) => {
  const { classes } = props;
  const isMobile = useMediaQuery("(max-width:600px)")
  const dispatch = useDispatch();
  const apiKeys = useSelector((state) => state.getApiKeys.apiKeys);
  const [searchKey, setSearchKey] = useState("");
  const [loading, setLoading] = useState(false);
  const AppSpeakerData = useSelector((state)=>state?.getSpeakerData?.speakerData?.speakers);
  const apiStatus = useSelector((state) => state.apiStatus);
  
  const [tableData, setTableData] = useState(AppSpeakerData || []);
  // const [filteredData, setFilteredData] = useState(initialData); // State for filtered data
  const [searchText, setSearchText] = useState('');
  // const [modal, setModal] = useState(false);
  // const [text, setText] = useState("");
  // const [debouncedText, setDebouncedText] = useState("");
  // const debouncedTextRef = useRef("");
  // const newKeystrokesRef = useRef(null)
  // const keystrokesRef = useRef(null)
  // const suggestionRef = useRef([null]);
  // const [prev, setprev] = useState(false);
  // const [formState, setFormState] = useState({
  //   sourceLanguage: '',
  //   targetLanguage: '',
  //   sourceText: '',
  //   targetText: '',
  // });
  const UserDetails = JSON.parse(localStorage.getItem("userDetails"));
  const [selectedFile, setSelectedFile] = useState(null);
  const [inputValue, setInputValue] = useState('');
  const [audioURL, setAudioURL] = useState('');
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
    const [open, setOpen] = useState(false);

  const location = useLocation();
  const { serviceProviderName, appName,UlcaApiKey} = location.state || {};
  console.log(appName,serviceProviderName,"neeww");
//   useEffect(() => {
//     if (apiKeys) {
//       setTableData(apiKeys);
//     }
//   }, [apiKeys]);

const [uploadDialogOpen, setUploadDialogOpen] = useState(false);
const [exportDialogOpen, setExportDialogOpen] = useState(false);
const [LocalVerifyOpenDialog, setLocalVerifyOpenDialog] = useState(false);
const [openDeleteBox, setOpenDeleteBox] = useState(false);
const [deletePopupdata, setDeletePopupData] = useState(null)
const [base64Audio, setBase64Audio] = useState('');
const [base64Recording, setBase64Recording] = useState('');
const [fetchUserId, setFetchUserId] = useState('');
const [enrollmentSuccess, setEnrollmentSuccess] = useState(false)
const [deletePopupLoading, setDeletePopupLoading] = useState(false)
const [verificationData,setVerificationData] = useState(false)
const [url, setUrl] = useState('');
const handleUploadDialogOpen = () => {
  setUploadDialogOpen(true);
};

const handleSpeakerEnrollmentClose = () => {
  setUploadDialogOpen(false);
  setSelectedFile(null)
  setAudioURL('')
  setBase64Audio('')
  setInputValue('')
  setBase64Recording('')
  setUrl('')
  setEnrollmentSuccess(false)
};

const handleVerifyGlobalDialogOpen = () => {
  setExportDialogOpen(true);
};

const handleVerifyLocalDialogOpen = (value) => {
  setFetchUserId(value?.[0])
  setLocalVerifyOpenDialog(true);
};

const handleDeletePopupModal = (data) => {
  setOpenDeleteBox(true)
  setDeletePopupData(data)
};

const handleSpeakerVerificationClose = () => {
  setExportDialogOpen(false);
  setSelectedFile(null)
  setLocalVerifyOpenDialog(false)
  setBase64Recording('')
  setAudioURL('')
  setVerificationData(true)
  setUrl('')
  setFetchUserId('')
};

const handleUpload = (file) => {
  // Handle file upload logic here
  console.log('Uploading file:', file);
  // handleSpeakerEnrollmentClose();
};

const handleExport = (file) => {
  // Handle file export logic here
  console.log('Exporting file:', file);
  handleSpeakerVerificationClose();
};


  const getApiKeysCall = async () => {
    const apiObj = new FetchApiKeysAPI();
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    getApiKeysCall();
  }, []);

  useEffect(() => {
    setTableData(AppSpeakerData);
  }, [AppSpeakerData]);

  useEffect(() => {
    const filtered = AppSpeakerData?.filter((row) => row?.speakerName.toLowerCase().includes(searchText.toLowerCase()));
    setTableData(filtered);
  }, [searchText]);

  const handleSearch = (value) => {
    setSearchText(value);
    // const filtered = tableData.filter((row) => row.glossary.toLowerCase().includes(value.toLowerCase()));
    // setFilteredData(filtered);
  };

 
  const getApiSpeakerData = async () => {
    // const apiObj = new FetchGlossaryDetails(appName,serviceProviderName);
    const apiObj = new FetchSpeakerDetailsApi(appName,serviceProviderName);
    // dispatch(APITransport(apiObj));
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "GET",
      headers: apiObj.getHeaders().headers,
    });
    // dispatch(APITransport(apiObj));
    const resp = await res.json();
    if (res.ok) {
      dispatch({
        type: "FETCH_SPEAKER_DATA",
        payload: resp, // assuming the response has the enrollment data
      });
    } else {
      setTableData([])
      setSnackbarInfo({
        open: true,
        message: resp?.message,
        variant: "error",
      });

    }
  };

  useEffect(() => {
    getApiSpeakerData();
  }, []);


  const fetchHeaderButton = () => {
    return (
      <Grid container style={{justifyContent:"space-between", fontFamily:"Noto-Regular", width: isMobile ? '88%' : '100%'}} >
        <Grid
          item
          xs={8}
          sm={8}
          md={3}
          lg={3}
          xl={3}
          style={{ display: "flex", justifyContent: "space-between" }}
        >
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
          {/* <Typography variant="h5" style={{fontFamily:"Noto-Regular"}}>Glossary List</Typography> */}
        </Grid>
      

        <Grid
          item
          xs={3}
          sm={3}
          md={5}
          lg={3}
          xl={4}
          className={classes.filterGrid}
          style={{ marginLeft: "100px", gap:"20px", display:isMobile ? 'none' : "flex" }}
        >
         
        
          <Button
            color="primary"
            size="medium"
            variant="contained"
            className={classes.ButtonRefresh}
            onClick={handleUploadDialogOpen}
            style={{
              height: "36px",
              textTransform: "capitalize",
              fontSize: "1rem",
              borderRadius:"4px",
              fontFamily: "Noto-Regular",
               fontWeight:"400",
               fontSize:"12px",
              //  padding:"14px 28px",
               marginLeft:"0px",
               width:"100%",
            }}
          >
             <Box sx={{display:"flex", gap:"6px"}}>
                  <img src={addImg} />
                  <span style={{fontSize:"16px"}}>Enroll New</span>
                </Box>
          </Button>
          <Button
            color="primary"
            size="medium"
            variant="contained"
            className={classes.ButtonRefresh}
            onClick={handleVerifyGlobalDialogOpen}
            style={{
              height: "36px",
              textTransform: "capitalize",
              fontSize: "1rem",
              borderRadius:"4px",
              fontFamily: "Noto-Regular",
               fontWeight:"400",
               fontSize:"12px",
              //  padding:"14px 28px",
               marginLeft:"0px",
               width:"100%",
            }}
          >
            <Box sx={{display:"flex", gap:"6px"}}>
                  {/* <img src={addImg} /> */}
                  <span style={{fontSize:"16px"}} >Verify Speaker</span>
                </Box>
          </Button>
        </Grid>

        <Grid
          item
          xs={2}
          sm={2}
          md={2}
          lg={2}
          xl={2}
          className={classes.filterGridMobile1}
        >
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            onClick={handleUploadDialogOpen}
            style={{ height: "37px" }}
          >
            {" "}
            <AddBoxIcon color="primary" className={classes.iconStyle} />
          </Button>
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            // className={classes.ButtonRefreshMobile}
            onClick={handleVerifyGlobalDialogOpen}
            style={{ height: "37px" }}
          >
            {" "}
            <AddBoxIcon color="primary" className={classes.iconStyle} />
          </Button>
        </Grid>
      </Grid>
    );
  };



  // const handleSubmit = async(event) => {
  //   event.preventDefault();
  //   console.log('Form Data:', formState);
  //   setLoading(true);
  //   const apiObj = new AddGlossaryDataApi(appName,serviceProviderName,formState);
  //   // dispatch(APITransport(apiObj));
  //   // // getApiGlossaryData()
  //   // setTimeout(() => {

  //   //   getApiGlossaryData() 
  //   //   setSnackbarInfo({
  //   //     open: true,
  //   //     message: "Glossary Added Successfully",
  //   //     variant: "success",
  //   //     }); 
  //   //   window.location.reload()
  //   //   },2500)
  //   // setModal(false)
  //   // setFormState({
  //   //   sourceLanguage: '',
  //   //   targetLanguage: '',
  //   //   sourceText: '',
  //   //   targetText: '',
  //   // })
  //   const res = await fetch(apiObj.apiEndPoint(), {
  //     method: "POST",
  //     headers: apiObj.getHeaders().headers,
  //     body: JSON.stringify(apiObj.getBody()),
  //   });

  //   const resp = await res.json();
  //   if (res.ok) {
  //     // setSnackbarInfo({
  //     //   open: true,
  //     //   message: resp?.message,
  //     //   variant: "success",
  //     // });
  //     setTimeout(() => {

  //     getApiGlossaryData() 
  //     setSnackbarInfo({
  //       open: true,
  //       message: resp?.message,
  //       variant: "success",
  //       }); 
  //     // window.location.reload()
  //     },1500)
  //   setModal(false)
  //   setFormState({
  //     sourceLanguage: '',
  //     targetLanguage: '',
  //     sourceText: '',
  //     targetText: '',
  //   })
  //     setLoading(false);
  //   } else {
  //     setSnackbarInfo({
  //       open: true,
  //       message: resp?.message,
  //       variant: "error",
  //     });

  //     setLoading(false);
  //   }
  // };

 

    
    const handleDeleteClose = () => {
      setOpenDeleteBox(false);
    };
  
    // Function to handle confirm deletion
    const handleDeleteConfirm = async() => {
      // Handle delete logic here
      console.log(`Deleting Speaker ID: $1111`, deletePopupdata[0]);
      setDeletePopupLoading(true)
      const apiObj = new DeleteSpeakerApi(appName,serviceProviderName,deletePopupdata?.[0]);

      const res = await fetch(apiObj.apiEndPoint(), {
        method: "POST",
        headers: apiObj.getHeaders().headers,
        body: JSON.stringify(apiObj.getBody()),
      });
      // dispatch(APITransport(apiObj));
      const resp = await res.json();
      if (res.ok) {
        dispatch({
          type: "DELETE_SPEAKER_DATA",
          payload: resp, // assuming the response has the enrollment data
        });
  
        setDeletePopupLoading(false)
        setOpenDeleteBox(false);
        setSnackbarInfo({
          open: true,
          message: resp?.message,
          variant: "success",
        });
        // setInputValue('')
        setTimeout(() => {
          getApiSpeakerData();
        }, 1500)
      } else {
        setSnackbarInfo({
          open: true,
          message: resp?.message,
          variant: "error",
        });
  
  
        setDeletePopupLoading(false)
  
    
    }
      
    };

  const getMuiTheme = () =>
    createMuiTheme({
      overrides: {
        MuiTable: {
          root: {
            borderCollapse: "hidden",
            fontFamily:"Noto-Regular"
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
        selectableRows: false,
        selectableRowsOnClick: false,
        fixedHeader: false,
        download: false,
        search: false,
        customToolbarSelect: (selectedRows, displayData, setSelectedRows) => (
          <div style={{display:"flex", gap:"20px", marginRight:"20px"}}>
          <Tooltip title="">
              <IconButton
                  style={{borderRadius:"4px", backgroundColor:"white", padding:"5px 12px"}}
                onClick={() => {
                  const selectedData = selectedRows.data.map(row => tableData[row.dataIndex]);
                  console.log("Selected Rows' Data:", selectedData);
                  const apiObj = new DeleteSpeakerApi(appName,serviceProviderName,selectedData);
                  dispatch(APITransport(apiObj));
                  setTimeout(() => {

                    getApiGlossaryData() 
                    setSnackbarInfo({
                      open: true,
                      message: "Multiple Glossaries Deleted Successfully",
                      variant: "success",
                    });  
                  },1500)
                  // alert(JSON.stringify(selectedData, null, 2)); // Display selected data in an alert
                }}
              >
                <Box sx={{display:"flex", gap:"6px"}}>
                  <img src={deleteImg} />
                 {!isMobile && <span style={{fontSize:"16px", color:"#D40808"}}>Delete</span>}
                </Box>
                {/* <DeleteIcon />  */}
              </IconButton>
            </Tooltip>
         
            </div>
          ),
        filter: false,
        onRowSelectionChange: (currentRowsSelected, allRowsSelected) => {
          const selectedData = allRowsSelected.map(row => tableData[row.dataIndex]);
          console.log("Selected Rows' Datahhh:", selectedData);
        }
      };
    
  
    
      const columns = [
        
        {
          name: "speaker_id",
          label: "Speaker ID",
          options: {
            filter: true,
            sort: true,
          }
        },
        {
          name: "speaker_name",
          label: "Speaker Name",
          options: {
            filter: true,
            sort: true,
          }
        },
        // {
        //   name: "audio_file",
        //   label: "Audio File",
        //   options: {
        //     filter: true,
        //     sort: true,
        //   }
        // },
        
        
        {
          name: "action",
          label: "Action",
          options: {
            customBodyRender: (value, tableMeta) => (
              // <Button
              //   // variant="contained"
              // //   onClick={() => {
              // //     console.log('Action clicked for row:', tableMeta.rowData)
              // //     const resultString = tableMeta.rowData.filter(item => item !== undefined);
              // //     const [sourceLanguage, targetLanguage, sourceText, targetText] = resultString;
              // //     const apiObj = new DeleteGlossaryApi(appName,serviceProviderName,sourceLanguage, targetLanguage, sourceText, targetText);
              // //     dispatch(APITransport(apiObj));
              // //     setTimeout(() => {

              // //       getApiGlossaryData() 
              // //       setSnackbarInfo({
              // //         open: true,
              // //         message: "Glossary Deleted Successfully",
              // //         variant: "success",
              // //       });  
              // //     },1500)
              // //   }
              // // }
             
              // >
                <Box style={{display:"flex", gap:"10px"}}>

                <Button variant="outlined" style={{border:"1px solid #2947A3", color:"#2947A3"}} onClick={() => handleVerifyLocalDialogOpen(tableMeta.rowData)}>Verify</Button>
                <Button variant="outlined" style={{border:"1px solid #626262", color:"#626262"}}  
                onClick={() => handleDeletePopupModal(tableMeta.rowData)}>Delete</Button>
                {/* <img src={Delete} alt="delete img"/> */}
                </Box>
              // </Button>
            )
          }
        }
      ];

    

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
      <Box style={{ width: '100%', padding: isMobile ? "20px 25px" :  '20px 0px', textAlign: 'start', marginBottom: '10px' }}>
        <Typography variant="h4" style={{padding:"10px 0px"}}>App Integration Details</Typography>
        <Box style={{display:"flex", flexDirection: isMobile ? "column" : 'row', justifyContent:isMobile ? "" :"space-between", alignItems:isMobile ? '' :"center", marginTop:"10px"}}>
          <Box>
            <Typography variant="body1">{appName}</Typography>
            <Typography variant="body2" fontWeight="400">App Name</Typography>
          </Box>
          {!isMobile && <Divider orientation="vertical" flexItem style={{ marginLeft: '10px', marginRight: '10px', borderRight: "3px solid #C9C9C9", height: "auto", backgroundColor:"transparent" }} />}
          <Box>
            <Typography variant="body1">{UlcaApiKey}</Typography>
            <Typography variant="body2" fontWeight="400">Udyat API Key</Typography>
          </Box>
          {!isMobile && <Divider orientation="vertical" flexItem style={{ marginLeft: '10px', marginRight: '10px', borderRight: "3px solid #C9C9C9", height: "auto", backgroundColor:"transparent" }} />}
          <Box>
            <Typography variant="body1">{UserDetails.userID}</Typography>
            <Typography variant="body2" fontWeight="400">User ID</Typography>
          </Box>
        </Box>
      </Box>
      <Box style={{marginBottom: '30px'}}>
      <Typography variant="body1" style={{padding:"5px 0px 0px 0px"}}>Disclaimer:</Typography>
      <Typography variant="body2" >The APIs provided here are intended for use exclusively within the Udyat platform. For any external or standalone usage of these APIs, please contact the DIBD Onboarding Manager for further assistance and access permissions.</Typography>
      </Box>
      <Box style={{ width: '100%', padding: '0px', textAlign: 'center', marginBottom: '20px' }}>
      <div style={styles.bannerContainer}>
        <img src={Spe} alt="banner" style={styles.bannerImage} />
      </div>
      </Box>

      <MuiThemeProvider theme={getMuiTheme}>
      <MUIDataTable
        //   title={"Glossary List"}
          data={tableData && tableData?.map(row => [
            // row.checkbox,
            row?.speakerId,
            row?.speakerName,
            row?.action
          ])}
          columns={columns}
          options={options}
        />
      </MuiThemeProvider>
   
    <FileUpload
        open={uploadDialogOpen}
        setOpen={setUploadDialogOpen}
        handleClose={handleSpeakerEnrollmentClose}
        title="Speaker Enrolllment"
        description="Upload Audio"
        buttonText=" Enroll Speaker"
        handleAction={handleUpload}
        status={true}
        value="none"
        selectedFile={selectedFile}
        setSelectedFile={setSelectedFile}
        inputValue={inputValue}
        setInputValue={setInputValue}
        audioURL={audioURL}
        setAudioURL={setAudioURL}
        base64Audio={base64Audio}
        setBase64Audio={setBase64Audio}
        base64Recording={base64Recording}
        setBase64Recording={setBase64Recording}
        serviceProviderName={serviceProviderName}
        appName={appName}
        fetchUserId={fetchUserId}
        setSnackbarInfo={setSnackbarInfo}
        getApiSpeakerData={getApiSpeakerData}
        handleVerifyGlobalDialogOpen={handleVerifyGlobalDialogOpen}
        setEnrollmentSuccess={setEnrollmentSuccess}
        enrollmentSuccess={enrollmentSuccess}
        verificationData={verificationData}
        setVerificationData={setVerificationData}
        url={url}
        setUrl={setUrl}
      />
      <FileUpload
        open={exportDialogOpen}
        setOpen = {setExportDialogOpen}
        handleClose={handleSpeakerVerificationClose}
        title="Speaker Verification"
        description="Upload Audio"
        buttonText="Verify Speaker"
        handleAction={handleExport}
        status={false}
        value='global'
        selectedFile={selectedFile}
        setSelectedFile={setSelectedFile}
        inputValue={inputValue}
        setInputValue={setInputValue}
        audioURL={audioURL}
        setAudioURL={setAudioURL}
        base64Audio={base64Audio}
        setBase64Audio={setBase64Audio}
        base64Recording={base64Recording}
        setBase64Recording={setBase64Recording}
        serviceProviderName={serviceProviderName}
        appName={appName}
        fetchUserId={fetchUserId}
        setSnackbarInfo={setSnackbarInfo}
        getApiSpeakerData={getApiSpeakerData}
        handleVerifyGlobalDialogOpen={handleVerifyGlobalDialogOpen}
        setEnrollmentSuccess={setEnrollmentSuccess}
        enrollmentSuccess={enrollmentSuccess}
        verificationData={verificationData}
        setVerificationData={setVerificationData}
        url={url}
        setUrl={setUrl}
      />
       <FileUpload
        open={LocalVerifyOpenDialog}
        setOpen = {setLocalVerifyOpenDialog}
        handleClose={handleSpeakerVerificationClose}
        title="Speaker Verification"
        description="Upload Audio"
        buttonText="Verify Speaker"
        handleAction={handleExport}
        status={false}
        value='local'
        selectedFile={selectedFile}
        setSelectedFile={setSelectedFile}
        inputValue={inputValue}
        setInputValue={setInputValue}
        audioURL={audioURL}
        setAudioURL={setAudioURL}
        base64Audio={base64Audio}
        setBase64Audio={setBase64Audio}
        base64Recording={base64Recording}
        setBase64Recording={setBase64Recording}
        serviceProviderName={serviceProviderName}
        appName={appName}
        fetchUserId={fetchUserId}
        setSnackbarInfo={setSnackbarInfo}
        getApiSpeakerData={getApiSpeakerData}
        handleVerifyGlobalDialogOpen={handleVerifyGlobalDialogOpen}
        setEnrollmentSuccess={setEnrollmentSuccess}
        enrollmentSuccess={enrollmentSuccess}
        verificationData={verificationData}
        setVerificationData={setVerificationData}
        url={url}
        setUrl={setUrl}
      />

<Dialog
      open={openDeleteBox}
      onClose={handleDeleteClose}
      aria-labelledby="delete-dialog-title"
      aria-describedby="delete-dialog-description"
      fullWidth
      maxWidth="sm"
    >
      {/* Dialog title */}
      <DialogTitle id="delete-dialog-title" >
        <Box style={{ display:"flex", justifyContent:"space-between", alignItems:"center", width:"100%"}}>
      <Typography variant="h5" fontweight={600}  fontFamily="Noto-Regular" style={{color:"black"}}> Delete</Typography>
      {/* <Typography variant="h6" fontweight={600} style={{color:"black"}}> X</Typography> */}
          </Box>
      </DialogTitle>
      
      {/* Dialog content */}
      <DialogContent>
        <DialogContentText id="delete-dialog-description">
          <Typography variant="h6" fontweight={600}  fontFamily="Noto-Regular" style={{color:"black"}}> Are you sure you want to delete?</Typography>
          <Typography variant="body2"  fontFamily="Noto-Regular" style={{marginTop:"10px"}}>You are deleting the Speaker Id <span style={{fontWeight:"bold", padding:"0px 3px"}}> {deletePopupdata?.[0]} </span> entry, and this action cannot be undone</Typography>
          
        </DialogContentText>
      </DialogContent>

      {/* Dialog actions */}
      <DialogActions style={{marginBottom:"25px", display:"flex", justifyContent:"space-between", padding:"0px 25px"}}>
        {/* Cancel button */}
        <Button onClick={handleDeleteClose} color="primary" variant="outlined">
          Cancel
        </Button>
        {/* Delete button */}
        <Button onClick={handleDeleteConfirm} color="error" variant="contained">
        {deletePopupLoading && <CircularProgress color="primary" size={24} style={{ marginRight: "10px" }} />} Delete
        </Button>
      </DialogActions>
    </Dialog>
   
   
     
    </>
  );
};

export default withStyles(DataSet)(SpeakerEnrollment);
