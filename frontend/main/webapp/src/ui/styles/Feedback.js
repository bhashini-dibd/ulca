const Feedback = (theme) => ({


  typography: {
    padding: theme.spacing(1),
    // marginTop: "15px"


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
    height: "28px",
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
  
     },
    
  },
  typography1: {
   // margin: "10px 20px 5px 10px"
  },
  textareaAutosize: {
    backgroundColor: "inherit",
    margin: "auto",
    // border: " none"
  },

  MuiButtonlabel: {
    fontSize: "11px"
  },
  

});

export default Feedback;
