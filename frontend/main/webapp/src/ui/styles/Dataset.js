const DataSet = (theme) => ({
  paper: {
    minHeight: "674px",
    boxShadow: "0px 0px 2px #00000029",
    border: "1px solid #0000001F",
  },
  parentPaper: {
    minHeight: "56px",
    maxWidth: "1272px",
    width: "100%",
    margin: "17px auto",
    padding: "0",
    "@media (max-width:400px)": {
      overflow: " hidden",
      width: "360px",
      // padding: "2px"

    },

  },
  title: {
    marginBottom: "6vh",
  },
  description: {
    width: "95%",
  },
  form: {
    marginTop: "1vh",
    width: "90%",
  },
  radioGroup: {
    marginTop: "1vh",
  },
  computeBtn: {
    backgroundColor: "#FD7F23",
    float: "right",
    marginRight: "30px",
    marginTop: "33px",
    lineHeight:" 1.2em",
    "&:hover": {
      backgroundColor: "#FD7F23",
    },
    "@media (max-width:640px)": {
      fontSize: "12px",
      marginRight: "0px",
      marginTop: "25px",
    },
  },
  tableRow: { display: "none" },
  modelNameCard: {
    height: "100px",
    backgroundColor: "#0F2749",
    borderRadius: "8px",
    marginTop: "1%",
    "@media (max-width:640px)": {
      height: "80px",
    },
  },
  divStyle: {
    padding: "5% 10% 5% 3.125rem",
  },

  typography: { marginBottom: "14px" },
  marginValue: { marginTop: "18px", color: "#0C0F0FB3" },

  list: {
   marginLeft: "-20px",
  
  },
  center: {
    display: "flex",
    justifyContent: "center",
    marginLeft: "auto",
    marginTop: "10px",
    "@media (max-width:400px)": {
      paddingTop: "6px"
    },
  },
  centerAudio: {
    display: "flex",
    justifyContent: "center",
    marginLeft: "auto",
    "@media (max-width:400px)": {
      margin: "0px",
    },
  },
  titleCard: {
    display: "flex",
    // alignItems: 'flex-end'
    alignItems: "center",
    paddingLeft: "20px",
  },

  updateBtn: {
    display: "flex",
    justifyItems: "center",
    marginTop: "-4%",
  },

  submitBtn: {
    marginTop: "6vh",
    color: "white",
  },
  breadcrum: {
    marginBottom: "1.5rem",
  },
  link: {
    marginRight: "10px",
    cursor: "pointer",
    display: "flex",
    flexDirection: "row",
  },
  span: {
    color: "#0C0F0F",
    opacity: "0.5",
    margin: "-3px 0 0 1rem",
  },

  searchDataset: {
    maxHeight: "1.875rem",
    heigth: "auto",
  },
  submittedOn: {
    display: "block",
    marginTop: "-0.3rem",
  },
  updateDataset: {
    padding: "2rem",
    width: "21rem",
  },
  datasetName: {
    borderBottom: "1px solid #e0e1e0",
    borderTop: "1px solid #e0e1e0",
  },
  popOver: {
    marginTop: "0.3rem",
  },
  footerButtons: {
    display: "flex",
    justifyContent: "flex-end",
    width: "100%",
    padding: ".6rem 1rem",
    boxSizing: "border-box",
    border: "1px solid rgb(224 224 224)",
    background: "white",
    marginTop: "-3px",
  },

  headerButtons: {
    display: "flex",
    justifyContent: "flex-end",
    width: "100%",
    marginBottom: ".6rem",
    boxSizing: "border-box",
  },
  buttonStyles: {
    marginLeft: "0.7rem",
    // borderRadius: "1rem",
    "@media (max-width:400px)": {
      marginLeft: "-3rem"
    },
  },
  buttonStyle: {
    marginLeft: "0.7rem",
    // borderRadius: "1rem",
    "@media (max-width:400px)": {
      marginTop: "5px"
    },

  },
  iconStyle: { marginRight: ".5rem", },
  thumbsUpIcon: {
    margin: "24% 0 0 24%",
    fontSize: "3.7rem",
  },

  thumbsUpIconSpan: {
    width: "6.5rem",
    height: "6.5rem",
    backgroundColor: "#e0e1e0",
    borderRadius: "100%",
    display: "block",
  },
  submissionIcon: {
    "@media (max-width:1120px)": {
      display: "none",
    },
  },

  dataSubmissionGrid: {
    padding: "5%",
  },
  thankYouTypo: {
    marginTop: "20px",
    color: "#FD7F23",
  },
  reqNoTypo: {
    marginTop: "10px",
    marginBottom: "20px",
  },
  myContriBtn: {
    marginTop: "20px",
  },
  noteTypo: { marginTop: "40px", marginBottom: "20px" },
  ButtonRefresh: {
    marginLeft: "auto",
    borderRadius: "1rem",
    fontSize:"13px",
    whiteSpace:" nowrap",
    "@media (max-width:870px)": {
      display: "none",
     
    },
    
  },
  ButtonRefreshMobile: {
    display: "none",
    borderRadius: "1rem",
    "@media (max-width:870px)": {
      display: "block",

    },
    "@media (max-width:400px)": {
      marginLeft: "0px",

    },

  },
  refreshGrid: {
    display: 'block',
    "@media (max-width:870px)": {
      display: 'none'
    }
  },
  refreshGridMobile: {
    display: 'none',
    "@media (max-width:870px)": {
      display: 'block'
    }
  },
  filterGrid: {
    display: "block",
    "@media (max-width:870px)": {
      display: "none",
    },
  },
  filterGridMobile: {
    display: "none",
    "@media (max-width:870px)": {
      display: "block",
    },
  },
  searchDivStyle: {
    //padding: '0% 4%',
    margin: "1% 2.5% 0 2.5%",
  },
  buttonDiv: {
    margin: "0% 0 1.5rem 0",
  },
  innerButton: {
    margin: "0 0.938rem 0.625rem 0",
  },
  subHeader: {
    marginBottom: "1.313rem",
  },
  autoComplete: {
    marginBottom: "1.938rem",
  },
  clearNSubmit: {
    // marginTop: "20rem",
    float: "right",
  },
  parent: {
    display: "flex",
    alignItems: "center",
    flexDirection: "column",
    justifyContent: "center",
    height: window.innerHeight - 80,
  },
  modelTable: {
    marginTop: "2rem",
  },
  action: { display: "flex", flexDirection: "row" },
  FindInPageIcon: { fontSize: "8rem" },
  searchResult: {
    textAlign: "center",
    justifyContent: "center",
    flexDirection: "column",
    display: "flex",

    verticalAlign: "middle",
    "@media (max-width:850px)": {
      minWidth: "270px",
      width: "85%",
    },
  },
  reqPaper: {
    // padding:'10%',
    // width:'60%'
    marginTop: "25%",
  },
  alignTypo: {
    textAlign: "center",
  },
  yourSearchQuery: {
    marginBottom: "2%",
    color: "#FD7F23",
  },
  serReqNoTypo: {
    marginBottom: "7%",
  },
  mySearches: {
    marginTop: "3%",
    width: "60%",
    //  textTransform:'inherit'
  },
  downloadDiv: {
    marginTop: "4%",
  },
  downloadPaper: {
    marginTop: "4%",
    padding: "5% 14% 2% 4%",
    width: "70%",
    minHeight: "3.5rem",
    backgroundColor: "#D6EAF8",
  },
  downloadBtnDiv: {
    margin: "10%",
    marginLeft: "0",
    display: "flex",
    flexDirection: "row",
  },
  searchResultFinal: {
    width: "90%",
    marginTop: "-20%",
  },
  downloadBtn: {
    marginRight: "2%",
  },

  blurOut: {
    zIndex: -2,
    opacity: "0.5",
  },
  leftSection: {
    boxShadow: "4px 4px 4px -4px #00000029",
  },
  rightSection:{
    paddingLeft: "24px",
    "@media (max-width:650px)": {
      paddingLeft: "0px",
    },
  },
  popupDialog: {
    maxWidth: "46.125rem",
    height: "26.5rem",
  },
  clearAllBtn: {
    float: "right",
    margin: "9px 16px 0px auto",
    padding: "0",
    height: "15px",
  },
  filterContainer: {
    borderBottom: "1px solid #00000029",
    paddingLeft: "18.5px",
    marginTop: "20px",
    width: "600px",
    maxHeight: "270px",
    overflow: "auto",
    "@media (max-width:550px)": {
      width: "330px",
      maxHeight: "170px",
    },
  },
  filterTypo: {
    marginBottom: "9px",
  },
  applyBtn: {
    float: "right",
    borderRadius: "20px",
    margin: "9px 16px 9px auto",
    width: "80px",
  },
  clrBtn: {
    float: "right",
    borderRadius: "20px",
    margin: "9px 10px 9px auto",
    width: "100px",
  },
  menuStyle: {
    padding: "0px",
    justifyContent: "left",
    fontSize: "1.125rem",
    fontWeight: "500 !important",

    "&:hover": {
      backgroundColor: "white",
    },
    borderBottom: "1px solid rgba(0, 0, 0, 0.42)",
    // borderTop:"3px solid green",
    "& svg": {
      marginLeft: "auto",
      color: "rgba(0, 0, 0, 0.42)",
    },
  },
  container: {
    margin: "12.75px 0px 18px 15px",
  },
  browseBtn: {
    marginTop: "-20px",
  },
  contriCard: {
    width: "578px",
    minHeight: "100px",
    margin: "10px",
    padding: "0px 10px",
    "@media (max-width:1250px)": {
      width: "500px",
    },
    "@media (max-width:900px)": {
      width: "350px",
    },
    "@media (max-width:700px)": {
      width: "350px",
    },
  },
  typeTypo: {
    marginTop: "6.25px",
    opacity: ".5",
  },
  Typo: {
    marginTop: "6.25px",
  },
  nameTypo: {
    marginTop: "6.25px",
    fontWeight: "500",
  },

  gridHeader: { padding: "13px 24px 14px 24px", display: "flex" },
  gridTypo: { marginTop: "4px" },
  gridData: {
    display: "flex",
    flexWrap: "wrap",
    alignContent: "flex-start",
    marginLeft: "14px",
    minHeight: "550px",
  },
  styleHr: {
    maxWidth: "24px",
    justifyItems: "flex-start",
    display: "inline-flex",
    width: "100%",
    margin: 0,
    marginBottom: "7.5px",
    // border:"4px solid #1DB5D8",
    borderRadius: "3px",
    height: "4px",
    border: "none",
    background: "#FD7F23",
  },
  subType: {
    marginBottom: "18.5px",
  },
  computeGrid: {
    display: "flex",
    alignItems: "flex-end",
    // alignItems: 'center'
  },
  modelTitle: {
    marginTop: "20px",
    // padding: 0,

  },

  modeldescription: {
    "@media (max-width:400px)": {
      fontSize: " 1.11rem"
    },
  },
  mainTitle: { marginTop: "33px", marginLeft: "38px" },
  backButton: {
    boxShadow: "none",
    padding: "0",
  },
  gridCompute: {
    marginTop: "15px ",
    // paddingRight:"10px", marginLeft:"40px"
  },
  grid: {
    marginRight: "15px ", "@media (max-width:400px)": {
      marginLeft: "15px"
    },
  },
  hosted: {
    display: "flex",
    // alignItems: 'flex-end'
    alignItems: "center",
  },
  translatedCard: {
    height: "300px",
    borderColor: "#2D63AB",
    borderRadius: "8px",
    marginTop: "20px",
    marginRight: "24px",
    "@media (max-width:400px)": {
      marginRight: "1px",
    },
  },
  asrCard: {
    height: "300px",
    width: "100%",
    borderColor: "#2D63AB",
    borderRadius: "8px",
    margin: "20px 20px 20px 0px",
    "@media (max-width:400px)": {
      marginLeft: "-1px", marginRight: "1px",
    },
  },
  computeBtnUrl: {
    marginTop: "55px",
    "@media (max-width:1000px)": {
      marginTop: "10px",
    },
  },
  textArea: {
    backgroundColor: "inherit",
    border: "none",
    width: "100%",
    resize: "none",
    outline: "none",
    fontSize: "18px",
    lineHeight: "32px",
    color: "black",
    fontFamily: "Roboto",

    //  paddingLeft:'16px'
  },

  textAreaTransliteration: {
    backgroundColor: "inherit",
    border: "none",
    width: "100%",
    resize: "none",
    outline: "none",
    fontSize: "18px",
    lineHeight: "32px",
    color: "black",
    fontFamily: "Roboto",
    height : "14rem"

    //  paddingLeft:'16px'
  },
  
  hostedCard: {
    minHeight: "300px",
    borderColor: "#2D63AB",
    borderRadius: "8px",
    paddingBottom: "13px",
    marginRight: "24px",
    overflowY: "auto",
    "@media (max-width:400px)": {
      marginRight: "1px",
    },
  },
  cardHeader: {
    backgroundColor: "#F4F7FB",
    height: "52px",
    alignItems: "center",
    "@media (max-width:400px)": {
      height: "62px",
    },

  },
  headerContent: {
    marginLeft: "18px",
  },
  actionButtons: {
    marginBottom: "13px",
    float: "right",
    marginRight: "20px",
  },
  translateCard: {
    padding: 0,

  },
  modelPara: {
    //marginTop: "15px ",
    //textTransform: 'capitalize'
    "&:first-letter": { textTransform: "capitalize" },
    display: "-webkit-box",
    "-webkit-line-clamp": "2",
    "-webkit-box-orient": "vertical",
    overflow: "hidden",
    "@media (max-width:400px)": {
      marginLeft: "-14px",
      fontSize: "12px"

    },
  },
  cardTitle: {
    "@media (max-width:400px)": {
      marginLeft: "-14px",
    },
  },
  mainPaper: {
    border: "none",
  },
  submitPaper: {
    textAlign: "center",
    width: "624px",
    height: "620px",
    margin: "auto",
    padding: "70px 59px 0 59px",
    marginTop: "51px",
    marginBottom: "118px",
  },
  benchmarkActionButtons: {
    borderRadius: "16px",
    backgroundColor: "white",
    boxShadow: "none",
    "&:hover": {
      backgroundColor: "white",
      boxShadow: "none",
    },
  },
  search: {
    position: "relative",
    borderRadius: "24px",
    backgroundColor: "#F3F3F3",
    marginLeft: 0,
    width: "400px",
    textAlign: "left",
    float: "right",
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: "100%",
    position: "absolute",
    pointerEvents: "none",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    color: "#00000029",
  },
  gridAlign: {
    justifyContent: "flex-end",
    alignItems: "flex-end",
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(3)}px)`,
    transition: theme.transitions.create("width"),
    width: "100%",
    fontStyle: "italic",
    fontSize: "14px",
  },
  inputRoot: {
    width: "100%",
    height: "39px",
  },
  filterBtn: {
    borderRadius: "22px",
  },
  advanceFilter: {
    textAlign: "right",
    color: "#FD7F23",
  },
  advanceFilterContainer: {
    maxHeight: "16rem",
    overflowY: "auto",
    overflowX: "hidden",
    marginTop: "2rem",
  },
  appTab: {
    background: "transparent",
    border: "none",
    margin: 0,
    padding: "0% 0% 0% 0%",
    color: "#3A3A3A",

  },
  flexEndStyle: {
    display: "flex",
    justifyContent: "flex-end",
    "@media (max-width:400px)": {
      marginBottom: "20px",
    },

  },
  Gridroot:{
    "@media (max-width:650px)": {
      marginLeft: "15px",
    },
  },
  imagemodal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  imagepaper: {
    backgroundColor: theme.palette.background.paper,
    // border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(3, 3, 3),


    borderRadius: "4px"
  },
  stspart: {
    "@media (max-width:400px)": {
      paddingLeft: "2px",
      paddingRight: "3px"
    },
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
    paddingLeft: "85px",
    fontSize: "16px",
    backgroundColor: " transparent!",
    fontFamily: "Rowdies",
    color: "gray",
    "@media (max-width:400px)": {
      paddingLeft: 0,
    },
  },
  gridcard: {
    "@media (max-width:959px)": {
      //  marginLeft: "20%",
      // justifyContent:"center"
    },
  },
  genderdropdown: {
    fontSize: "16px",
    backgroundColor: " transparent!",
    // fontFamily:"Rowdies",
    color: "gray",
  },
  muiTable: {
    wordBreak: " break-all",

  },
  textField: {
    "@media (max-width:400px)": {
      paddingTop: "15px",
    },

  },
  audioCard: {
    justifyContent: "center",
    alignItems: "center",
    position: "relative",
     top: "15%",
    // "@media (max-width:400px)": {
    //   top:"36%",
    // },

  },
  feedbackPopover:{
    position:"relative",
    left:"700px",
    top:"-10px",
    "@media (max-width:400px)": {
      left: "220px",
      top:"0px",
    }

  },
 
  typography: {
    padding: theme.spacing(1),
    // marginTop: "15px"
    fontFamily: "Rowdies", 
    fontSize: "1.3rem",
    color:"#444542",
    "@media (max-width:650px)": {
      fontSize: "1.1rem",

    },

  },
  typographys: {
    padding: theme.spacing(1),
    fontSize: "14px",
    fontFamily:"Roboto ",
    "@media (max-width:650px)": {
      fontSize: "11px",
     
    
    //padding:"10px"
    }


  },
  typography2: {
    margin: "16px 10px 10px 0px",
    fontSize: "16px",
    "@media (max-width:650px)": {
      fontSize: "14px",
      // margin: "0px 0px 0px 10px",
    
    //padding:"10px"
    }
  },
  MuiRatingLabel: {
    paddingLeft: "19px"
  },
  feedbackbutton: {
    backgroundColor: "#FD7F23",
    position: "absolute",
    // right:"775px",
     top:"72px",
    height: "30px",
    "@media (max-width:650px)": {
       top:"68px",

    },
    '&:hover': {
      backgroundColor: "#FD7F23"

    }
  },
  feedbackIcon: {
    width: "12px",
    heigth: "10px",
    color: "white",


  },
  feedbackTitle: {
    fontSize: "10px",
    color: "white",
    paddingLeft: "3px"
  },
  feedbacktypography: {
    fontSize: "12px",
    borderBottom: "1px solid #ECE7E6  ",
    width: "225px",
    margin: "auto"
  },
  submitbutton: {
    width: "70px",
    margin: "10px 0px 0px 130px",
    textTransform: "capitalize"
  },
  rating: {
    position:"relative ",
    buttom:"20px",
    left:"70px",
    // marginLeft: "69px",
    "@media (max-width:650px)": {
      //  marginLeft: "35px",
      //buttom:"20px",
    left:"40px",
    width:"300px"

    },
    // display: 'flex',
    // justifyContent: 'center'

  },
  buttonsuggest: {
    float: "right",
    height: "25px",
    // marginLeft:"20px"
    marginRight: "10px",
    padding: "21px 7px 13px 6px",
   
    borderColor:"#3f51b5",
    "@media (max-width:650px)": {
      // height: "38px",
       marginRight: "0px",
       padding: "7px 12px 4px 13px",

    },

  },
  buttonsuggestlable:{
    textTransform:" none",
    lineHeight:"16px",
    whiteSpace: "nowrap",
    height: "25px",
    "@media (max-width:650px)": {
      lineHeight:"21px",
      wordBreak:"break-all"

    },
   
  },
  iconbutton:{
    position: "absolute",
     right: "15px",
      top: "5px",
  },
  typography1: {
    // marginLeft: "10px",
    fontSize: "16px",
    marginBottom: "6px",
    // margin: "10px 20px 5px 10px"
    "@media (max-width:650px)": {
      // marginLeft: "10px",
      fontSize: "12px",
      marginBottom: "0px",

    },
  },
  textareaAutosize: {
    backgroundColor: "inherit",
    margin: "auto",
    fontFamily: "Roboto",
    fontSize: "18px",
    width: "320px",
    // border: " none"
  },

  MuiButtonlabel: {
    fontSize: "11px"
  },
  suggestbutton: {
    margin: " 10px 0px 0px 30px",
  },
  buttonsubmit: {
    width: "70px",
    margin: "5px 0px 0px 55px",

  },
  textfield:{
    fontFamily: "Roboto",
    fontSize: "12px",
    margin: "10px 0px 10px 0px"
    
  },
  Addyourcomments:{
    margin: "10px 10px 10px 10px",
     fontSize: "16px",
     "@media (max-width:650px)": {
      // margin: "0px 10px 0px 10px",
     fontSize: "12px",
    },
  },
  border:{
    borderBottom: "1px solid #ECE7E6 ",
    width: "300px",
    margin: "auto", 
    paddingBottom: "10px"

  },
  submitbutton:{
    margin: "10px"

  },
  feedbackgrid:{
    maxWidth: "350px"
  },
  translatfeedbackbutton:{
    float: "right",
    marginTop:"14px" ,
    marginRight: "15px",
    backgroundColor:"#FD7F23",
    '&:hover': {
      backgroundColor: "#FD7F23"

    },
      "@media (max-width:650px)": {
        marginRight: "5px",
      
      },
  },
  feedbackbuttons:{
    backgroundColor:"#FD7F23",
    '&:hover': {
      backgroundColor: "#FD7F23"

    }
  },
  ocrfeedbackbutton:{
    float: "right",
    // marginTop: "10px",
     marginRight: "20px",
     backgroundColor:"#FD7F23",
     '&:hover': {
      backgroundColor: "#FD7F23"

    },
     "@media (max-width:650px)": {
       marginTop: "-10px",
    
    },

  },
  
  translationsuggestgrid:{
    padding:"0px 10px 10px 10px",
    textAlign: "center"

  },
  // translationtextfield:{
  //   marginLeft:"50px",
  //   "@media (max-width:650px)": {
  //     margin:"10px 0px 0px 30px",
    
  //   },

  // },
  textareas: {
    backgroundColor: "inherit",
    border: "none",
    width: "100%",
    resize: "none",
    outline: "none",
    fontSize: "14px",
    lineHeight: "25px",
    color: "black",
    fontFamily: "Roboto",

    //  paddingLeft:'16px'
  },
  Asrfeedback:{
    float: "right",
    marginTop: "38px",
    marginRight: "20px", 
    backgroundColor: "#FD7F23",
    '&:hover': {
      backgroundColor: "#FD7F23"

    },
  },
  Asrcard:{
    height:"130px",
    overflowY:"auto",
    fontFamily: "Roboto",
    fontSize: "14px",
     lineHeight: "25px",
      color: "black"
  },
  metricsSearchbar:{
    width:"100%",
    position:"absolute",
     right:"350px",
    top:"165px",
    zIndex:"1",

  },

  metricsParent:{
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
    position: "absolute",
    right: "21%",
    top: "160px",
    zIndex: "9999",
  },

  metricsbtn: {
    borderRadius: "50%",
    background: "none",
    cursor: "pointer",
    padding: "12px",
    marginLeft: '20px',
  },

  selectColumnContainer: {
      borderBottom: "1px solid #00000029",
      padding: "20px",
      width: "350px",
      height: "fit-content",
      maxHeight: "350px",
      "@media (max-width:550px)": {
        width: "330px",
        maxHeight: "170px",
      },
  },

  selectColumnHeader: {
    marginBottom: "25px",
    color: "#000",
  },

  descCardIcon: {
    display: "flex",
    borderRadius: "20%",
    padding: "15px",
    height: "fit-content",
  },

  myProfileActionBtn: {
    borderRadius: "16px",
    backgroundColor: "white",
    boxShadow: "none",
    color: "rgb(19, 157, 96)",
    padding: "10px 20px",
    fontSize: "1rem",
    width: "fit-content",
    height:"36px",
    cursor: "pointer",
    "&:hover": {
      backgroundColor: "white",
      boxShadow: "none",
    },
  }
});

export default DataSet;
