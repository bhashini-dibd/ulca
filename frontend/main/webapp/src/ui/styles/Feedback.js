const Feedback = (theme) => ({


  typography: {
    padding: theme.spacing(1),
    // marginTop: "15px"
    fontFamily: "Rowdies", 
    fontSize: "1.3rem",
    color:"#444542",
   


  },
  typographys: {
    padding: theme.spacing(1),
    fontSize: "12px"

  },
  typography2: {
     margin: "25px 10px 10px 10px",
    fontSize: "15px"
    //padding:"10px"
  },
  MuiRatingLabel: {
    paddingLeft: "19px"
  },
  feedbackbutton: {
    backgroundColor: "#FD7F23",
    position: "absolute",
    textTransform: 'none',
    // right:"775px",
    height: "29px",
    
    '&:hover': {
      backgroundColor: "#FD7F23",

    },
    "@media (max-width:400px)": {
      top:"-14px",
      left:"7px"
    }
  },
  feedbackIcon: {
    width: "12px",
    heigth: "13px",
    color: "white",


  },
  feedbackTitle: {
    fontSize: "12px",
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
    margin: "10px 0px 0px 130px"
  },
  rating: {
    marginLeft:"60px",
    // display: 'flex',
    // justifyContent: 'center'

  },
  buttonsuggest: {
    float: "right",
    height: "25px",
    // marginLeft:"20px"
    marginRight: "10px",
    "@media (max-width:400px)": {
      height: "38px",
      marginRight: "5px",
  
     },
    
  },
  typography1: {
    marginLeft: "10px",
   // margin: "10px 20px 5px 10px"
   "@media (max-width:400px)": {
    marginLeft: "10px",

   },
  },
  textareaAutosize: {
    backgroundColor: "inherit",
    margin: "auto",
    // border: " none"
  },

  MuiButtonlabel: {
    fontSize: "11px"
  },
  suggestbutton:{
    margin:" 10px 0px 0px 30px",
    "@media (max-width:400px)": {
      margin:" 5px 0px 0px 48px",
  
     },
  },
  buttonsubmit:{
    width: "70px",
    margin: "5px 0px 0px 55px",
    
  },
  feedbackbuttonroot:{
    // position:"relative",
    // left:"700px",
    // top:"-10px",
    "@media (max-width:400px)": {
      left: "220px",
     
  
     },
  }
  
  

});

export default Feedback;
