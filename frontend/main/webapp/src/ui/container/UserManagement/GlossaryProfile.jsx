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
import GlossaryBanner from '../../../assets/GlossaryNewBanner.png'
import { useLocation } from 'react-router-dom';
import FetchGlossaryDetails from "../../../redux/actions/api/UserManagement/FetchGlossaryDetails";
import AddGlossaryDataApi from "../../../redux/actions/api/UserManagement/AddGlossaryData";
import DeleteGlossaryApi from "../../../redux/actions/api/UserManagement/DeleteGlossary";
import MultiDeleteGlossaryApi from "../../../redux/actions/api/UserManagement/MultiDeleteGlossary";
import { IndicTransliterate, getTransliterationLanguages, getTransliterateSuggestions } from "@ai4bharat/indic-transliterate";
// import "@ai4bharat/indic-transliterate/dist/index.css";
import { useRef } from "react";

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



  const languages = [
    { "code": "as", "label": "Assamese" },
    { "code": "en", "label": "English" },
    { "code": "bn", "label": "Bengali" },
    { "code": "brx", "label": "Bodo" },
    { "code": "doi", "label": "Dogri" },
    { "code": "gom", "label": "Goan Konkani" },
    { "code": "gu", "label": "Gujarati" },
    { "code": "hi", "label": "Hindi" },
    { "code": "kn", "label": "Kannada" },
    { "code": "ks", "label": "Kashmiri" },
    { "code": "mai", "label": "Maithili" },
    { "code": "ml", "label": "Malayalam" },
    { "code": "mni", "label": "Manipuri" },
    { "code": "mr", "label": "Marathi" },
    { "code": "ne", "label": "Nepali" },
    { "code": "or", "label": "Odia" },
    { "code": "pa", "label": "Punjabi" },
    { "code": "sa", "label": "Sanskrit" },
    { "code": "sat", "label": "Santali" },
    { "code": "sd", "label": "Sindhi" },
    { "code": "ta", "label": "Tamil" },
    { "code": "te", "label": "Telugu" },
    { "code": "ur", "label": "Urdu" }
  ];
  

const GlossaryProfile = (props) => {
  const { classes } = props;
  const isMobile = useMediaQuery("(max-width:600px)")
  const dispatch = useDispatch();
  const apiKeys = useSelector((state) => state.getApiKeys.apiKeys);
  const [searchKey, setSearchKey] = useState("");
  const [loading, setLoading] = useState(false);
  const AppGlossaryData = useSelector((state)=>state?.getGlossaryData?.glossaryData);
  const [tableData, setTableData] = useState(AppGlossaryData || []);
  // const [filteredData, setFilteredData] = useState(initialData); // State for filtered data
  const [searchText, setSearchText] = useState('');

  const [modal, setModal] = useState(false);
  const [text, setText] = useState("");
  const [debouncedText, setDebouncedText] = useState("");
  const debouncedTextRef = useRef("");
  const newKeystrokesRef = useRef(null)
  const keystrokesRef = useRef(null)
  const suggestionRef = useRef([null]);
  const [prev, setprev] = useState(false);
  const [formState, setFormState] = useState({
    sourceLanguage: 'en',
    targetLanguage: 'hi',
    sourceText: '',
    targetText: '',
  });
  const UserDetails = JSON.parse(localStorage.getItem("userDetails"));
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });

  const location = useLocation();
  const { serviceProviderName, inferenceApiKey, appName} = location.state || {};
  console.log(UserDetails?.userID,serviceProviderName, inferenceApiKey, appName,"neeww");
//   useEffect(() => {
//     if (apiKeys) {
//       setTableData(apiKeys);
//     }
//   }, [apiKeys]);


useEffect(() => { 
  if (debouncedTextRef.current.trim()!="" && suggestionRef.current.length>1) {
    console.log("nnn",suggestionRef.current);
    console.log("nnn",debouncedTextRef.current,text);
    const words = debouncedTextRef.current.split(/\s+/).filter(word => word.trim() !== "");

      const optedWord = suggestionRef.current.find((item) => item === words[words.length-1]) || "";

      const newKeystroke = {
        keystrokes: debouncedTextRef.current,
        results: suggestionRef.current,
        opted:optedWord,
        created_at: new Date().toISOString(),
      };
      newKeystrokesRef.current = newKeystroke
      if (
        keystrokesRef.current.length > 0 &&
        keystrokesRef.current[keystrokesRef.current.length - 1].keystrokes === newKeystroke.keystrokes
      ) {
        keystrokesRef.current[keystrokesRef.current.length - 1] = newKeystroke;
      } else {
        keystrokesRef.current = [...keystrokesRef.current, newKeystroke];
      }
      console.log("nnn", keystrokesRef.current,newKeystrokesRef.current);
      // const finalJson = {
      //   word: debouncedTextRef.current,
      //   steps: keystrokesRef.current,
      //   language: selectedLang.LangCode!=undefined?selectedLang.LangCode:"hi",
      // };
      // localStorage.setItem('TransliterateLogging', JSON.stringify(finalJson));
  }
}, [suggestionRef.current,prev,formState.sourceLanguage]);

  const onKeyDown = (event) => {
    if (event.keyCode == 27) {
      setModal(false);
    }
  };


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

  useEffect(() => {
    setTableData(AppGlossaryData);
  }, [AppGlossaryData]);

  useEffect(() => {
    const filtered = AppGlossaryData.filter((row) => row.sourceText.toLowerCase().includes(searchText.toLowerCase()));
    setTableData(filtered);
  }, [searchText]);

  const handleSearch = (value) => {
    setSearchText(value);
    // const filtered = tableData.filter((row) => row.glossary.toLowerCase().includes(value.toLowerCase()));
    // setFilteredData(filtered);
  };

 
  const getApiGlossaryData = async () => {
    const apiObj = new FetchGlossaryDetails(appName,serviceProviderName);
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    getApiGlossaryData();
  }, []);


  const fetchHeaderButton = () => {
    return (
      <Grid container style={{justifyContent:"space-between", fontFamily:"Noto-Regular"}}>
        <Grid
          item
          xs={8}
          sm={8}
          md={3}
          lg={3}
          xl={3}
          style={{ display: "flex", justifyContent: "space-between" }}
        >
          {/* <Search value="" handleSearch={(e) => handleSearch(e.target.value)} /> */}
          <Typography variant="h5" style={{fontFamily:"Noto-Regular"}}>Glossary List</Typography>
        </Grid>
      

        <Grid
          item
          xs={3}
          sm={3}
          md={5}
          lg={5}
          xl={5}
          className={classes.filterGrid}
          style={{ marginLeft: "100px", gap:"20px", display:"flex" }}
        >
         <Search value='' handleSearch={(e) => handleSearch(e.target.value)}/>
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
              borderRadius:"4px",
              fontFamily: "Noto-Regular",
               fontWeight:"400",
               fontSize:"15px",
               padding:"14px 36px"
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
    const apiObj = new AddGlossaryDataApi(appName,serviceProviderName,formState);
    dispatch(APITransport(apiObj));
    // getApiGlossaryData()
    setTimeout(() => {

      getApiGlossaryData() 
      setSnackbarInfo({
        open: true,
        message: "Glossary Added Successfully",
        variant: "success",
      }); 
    },1500)
    // window.location.reload()
    setModal(false)
    setFormState({
      sourceLanguage: 'en',
      targetLanguage: 'hi',
      sourceText: '',
      targetText: '',
    })
  };

  const isFormValid = () => {
    return (
      formState.sourceText !== '' &&
      formState.targetText !== '' &&
      formState.sourceLanguage !== '' &&
      formState.targetLanguage !== ''
    );
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
        selectableRows: "multiple",
        selectableRowsOnClick: false,
        fixedHeader: false,
        download: false,
        search: false,
        customToolbarSelect: (selectedRows, displayData, setSelectedRows) => (
            <Tooltip title="">
              <IconButton
                onClick={() => {
                  const selectedData = selectedRows.data.map(row => tableData[row.dataIndex]);
                  console.log("Selected Rows' Data:", selectedData);
                  const apiObj = new MultiDeleteGlossaryApi(appName,serviceProviderName,selectedData);
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
                <DeleteIcon />
              </IconButton>
            </Tooltip>
          ),
        filter: false,
        onRowSelectionChange: (currentRowsSelected, allRowsSelected) => {
          const selectedData = allRowsSelected.map(row => tableData[row.dataIndex]);
          console.log("Selected Rows' Datahhh:", selectedData);
        //   setSelectedRowsData(selectedData);
        }
      };
    
  
    
      const columns = [
        
        {
          name: "source",
          label: "Source",
          options: {
            filter: true,
            sort: true,
          }
        },
        {
          name: "target",
          label: "Target",
          options: {
            filter: true,
            sort: true,
          }
        },
        {
          name: "sourceLanguage",
          label: "Source Language",
          options: {
            filter: true,
            sort: true,
          }
        },
        {
          name: "targetLanguage",
          label: "Target Language",
          options: {
            filter: true,
            sort: true,
          }
        },
        // {
        //   name: "glossary",
        //   label: "Glossary",
        //   options: {
        //     customBodyRender: (value) => (
        //       <Box display='flex' alignItems="center">
        //         <Box>{value}</Box>
               
        //       </Box>
        //     )
        //   }
        // },
        {
          name: "action",
          label: "Action",
          options: {
            customBodyRender: (value, tableMeta) => (
              <Button
                // variant="contained"
                onClick={() => {
                  console.log('Action clicked for row:', tableMeta.rowData)
                  const resultString = tableMeta.rowData.filter(item => item !== undefined);
                  const [sourceLanguage, targetLanguage, sourceText, targetText] = resultString;
                  const apiObj = new DeleteGlossaryApi(appName,serviceProviderName,sourceLanguage, targetLanguage, sourceText, targetText);
                  dispatch(APITransport(apiObj));
                  setTimeout(() => {

                    getApiGlossaryData() 
                    setSnackbarInfo({
                      open: true,
                      message: "Glossary Deleted Successfully",
                      variant: "success",
                    });  
                  },1500)
                }
              }
              >
                <img src={Delete} alt="delete img"/>
              </Button>
            )
          }
        }
      ];

      const renderTextarea = (props, heading) => {
        return (
          <>
          <Typography variant="h6" style={{fontFamily: "Noto-Bold", fontWeight:"600",marginBottom:"15px"}}>{heading}</Typography>
          <textarea
            {...props}
            placeholder={"Enter text here..."}
            rows={2}
            // className={classes.textAreaTransliteration}
            style={{border:"1px solid lightGray", backgroundColor: "inherit", width: "90%", resize: "none",
            fontSize: "18px",
            lineHeight: "32px",
            color: "black",
            fontFamily: "Roboto",
            // height : "14rem",
            padding : "1rem", borderRadius:"8px"}}

          />
          
          </>
        );
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
      <Box style={{ width: '50%', padding: '0px', textAlign: 'start', marginBottom: '20px' }}>
        <Typography variant="h4">App Integration Details</Typography>
        <Box style={{display:"flex", flexDirection: isMobile ? "column" : 'row', justifyContent:isMobile ? "" :"space-between", alignItems:isMobile ? '' :"center", marginTop:"10px"}}>
          <Box>
            <Typography variant="body1">{appName}</Typography>
            <Typography variant="body2">App Name</Typography>
          </Box>
          <Box>
            <Typography variant="body1">{UserDetails.userID}</Typography>
            <Typography variant="body2">ULCA API Key</Typography>
          </Box>
          {/* <Box>
            <Typography variant="body2">h1</Typography>
            <Typography variant="body1">jjj</Typography>
          </Box> */}
        </Box>
      </Box>
      <Box style={{ width: '100%', padding: '0px', textAlign: 'center', marginBottom: '20px' }}>
      <div style={styles.bannerContainer}>
        <img src={GlossaryBanner} alt="banner" style={styles.bannerImage} />
        {/* <div style={styles.textContainer}>
          <h1 style={styles.heading}>Glossary</h1>
          <p style={styles.paragraph}>Glossary is a custom dictionary defined by the user that is utilized in Bhashini Translations to translate the customer's domain-specific terminology. For example, when translating from English to Hindi, Bhashini Division maybe translated to भाषिनी प्रभाग by default but given it's an organization name, we need the translation as भाषिणी डिवीज़न. Such word and phrase level translations can be performed using glossaries for your custom domain specific needs.</p>
        </div> */}
      </div>
      </Box>

      <MuiThemeProvider theme={getMuiTheme}>
      <MUIDataTable
        //   title={"Glossary List"}
          data={ tableData?.map(row => [
            // row.checkbox,
            row?.sourceText,
            row?.targetText,
            row?.sourceLanguage,
            row?.targetLanguage,
            row?.action
          ])}
          columns={columns}
          options={options}
        />
      </MuiThemeProvider>
      <Dialog
      open={modal}
      onClose={() => setModal(false)}
      aria-labelledby="dialog-title"
      aria-describedby="dialog-description"
      maxWidth="md"
    //   style={{fontFamily: "Noto Sans"}}
      fullWidth
    >
      <DialogTitle id="dialog-title">
        <div style={{fontFamily: "Noto-Bold", fontWeight:"600"}}>
        Create a New Glossary
        </div>
        </DialogTitle>
      <DialogContent>
        <Container>
          <form noValidate autoComplete="off" onSubmit={handleSubmit}>
            <Grid container spacing={2} alignItems="center">
              {/* First Select Box */}
            
              <Grid item xs={12} sm={12} md={6}>
              <Typography variant="h6" style={{fontFamily: "Noto-Bold", fontWeight:"600",marginBottom:"15px"}}>Select Source Language</Typography>
                <FormControl fullWidth variant="outlined">
                  <InputLabel id="select-source-label">Select Source Language</InputLabel>
                  <Select
                    labelId="select-source-label"
                    id="select-source"
                    name="sourceLanguage"
                    value={formState.sourceLanguage}
                    onChange={handleChange}
                    label="Select Source Language"
                  >
                     {languages.map((lang) => (
                      <MenuItem key={lang.code} value={lang.code}>
                        {lang.label}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>

              {/* Second Select Box */}
             
              <Grid item xs={12} sm={12} md={6}>
                
                <Typography variant="h6" style={{fontFamily: "Noto-Bold", fontWeight:"600",marginBottom:"15px"}}>Select Target Language</Typography>
                <FormControl fullWidth variant="outlined">
                  <InputLabel id="select-target-label">Select Target Language</InputLabel>
                  <Select
                    labelId="select-target-label"
                    id="select-target"
                    name="targetLanguage"
                    value={formState.targetLanguage}
                    onChange={handleChange}
                    label="Select Target Language"
                  >
                    {languages.map((lang) => (
                      <MenuItem key={lang.code} value={lang.code} disabled={lang.code === formState.sourceLanguage}>
                        {lang.label}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>

              {/* First Text Field */}
            
              <Grid item xs={12} sm={12} md={6}>
              {/* <Typography variant="h6" style={{fontFamily: "Noto-Bold", fontWeight:"600",marginBottom:"15px"}}>Enter Source Text</Typography>
                <TextField
                  variant="outlined"
                  fullWidth
                  label="Enter Source Text"
                  name="sourceText"
                  value={formState.sourceText}
                  onChange={handleChange}
                /> */}
                 <IndicTransliterate
        lang={formState.sourceLanguage}
        value={formState.sourceText}
        onChangeText={(val) => {
          setText(val)
          setDebouncedText(val);
          setFormState((prevState) => ({
            ...prevState,
            sourceText: val,
          }));
          debouncedTextRef.current=val
          if(!debouncedTextRef.current.toString().includes(debouncedText)){
            setprev(true)
          }
          else{
            setprev(false)
          }
          console.log("nnn",text,debouncedText,debouncedTextRef.current);
        }}
        renderComponent={(props) => renderTextarea(props,"Source Text")}
        showCurrentWordAsLastSuggestion={true}
      />
              </Grid>

              {/* Second Text Field */}
            
              <Grid item xs={12} sm={12} md={6}>
              {/* <Typography variant="h6" style={{fontFamily: "Noto-Bold", fontWeight:"600",marginBottom:"15px"}}>Enter Target Text</Typography>
                <TextField
                  variant="outlined"
                  fullWidth
                  label="Enter Target Text"
                  name="targetText"
                  value={formState.targetText}
                  onChange={handleChange}
                /> */}
                 <IndicTransliterate
        lang={formState.targetLanguage}
        value={formState.targetText}
        onChangeText={(val) => {
          setText(val)
          setDebouncedText(val);
          setFormState((prevState) => ({
            ...prevState,
            targetText: val,
          }));
          debouncedTextRef.current=val
          if(!debouncedTextRef.current.toString().includes(debouncedText)){
            setprev(true)
          }
          else{
            setprev(false)
          }
          console.log("nnn",text,debouncedText,debouncedTextRef.current);
        }}
        renderComponent={(props) => renderTextarea(props,"Target Text")}
        showCurrentWordAsLastSuggestion={true}
      />
              </Grid>
            </Grid>
          </form>
        </Container>
      </DialogContent>
      <DialogActions style={{ display: "flex", justifyContent: "space-between", gap: "20px", marginTop: "10px", marginRight:"40px", marginLeft:"40px",marginBottom:"10px",fontFamily: "Noto-Regular", fontWeight:"400" }}>
        <Button
          variant="contained"
          color="primary"
          onClick={() => {
            setModal(false); 
            setFormState({
            sourceLanguage: 'en',
            targetLanguage: 'hi',
            sourceText: '',
            targetText: '',
          })}}
          style={{ padding: "12px 24px" }}
        >
          Cancel
        </Button>
        <Button
          variant="contained"
          color="primary"
          type="submit"
          style={{ padding: "12px 24px" }}
          onClick={handleSubmit}
          disabled={!isFormValid()}
        >
          Submit
        </Button>
      </DialogActions>
    </Dialog>

   

     
    </>
  );
};

export default withStyles(DataSet)(GlossaryProfile);
