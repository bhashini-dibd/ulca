const LoginStyle = (theme) => ({
  appInfo: {
    background: "rgba(44, 39, 153, 1)",
    minHeight: "100vh",
    color: theme.palette.primary.contrastText,
    "@media (max-width:650px)": {
      background: "white",
      minHeight: "15vh",
     
    },
    
  
  },
  title: {
    width: "20%",
    height: "auto",
    margin: "22% 294px 10% 39px",
    cursor:"pointer",
    lineHeight: "1.53",
    letterSpacing: "3.9px",
    textAlign: "left",
    color: theme.palette.primary.contrastText,
    "@media (max-width:650px)": {
      // margin: "5% 10% 5% 40%",
      color: "black",
     
    },
  

  },
  subTitle: {
    width: "80%",
    height: "auto",
    maxWidth: "300px",
    margin: "20% 70px 15% 39px",
    lineHeight: "1.5",
    letterSpacing: "1.6px",
    textAlign: "left",
    color: theme.palette.primary.contrastText,
    "@media (max-width:1040px)": {
      letterSpacing: "1px",
      maxWidth: "280px",
      width: "80%",
    },
    "@media (min-width:1790px)": {
      width: "68%",
    },
  },
  body: {
    width: "80%",
    height: "auto",
    margin: "30px 0px 50px 39px",
    lineHeight: "1.5",
    letterSpacing: "1.6px",
    textAlign: "left",
    color: "#f2f2f4",
    "@media (max-width:1040px)": {
      letterSpacing: "1px",
      maxWidth: "280px",
    },
    "@media (min-width:1790px)": {
      
      width: "85%",
    },
  },
  expButton: {
    background: theme.palette.primary.contrastText,
    marginLeft: "39px",
  },
  parent: {
    display: "flex",
    alignItems: "center",
    flexDirection: "column",
    justifyContent: "center",
  },
  loginGrid: {
    width: "462px",
    flexDirection: "column",
    display: "flex",
    height: "auto",
    verticalAlign: "middle",
    "@media (max-width:850px)": {
      minWidth: "270px",
      width: "85%",
      margin:"30px 0px"
    },
  },
  body2: {
   
    paddingBottom: "25px",
    opacity: "1",
    
    height: "37px",
    color: "0C0F0F",
  },
  fullWidth: {
    width: "100%",
    marginTop: "13px",
    textAlign: "Left",
  },
  line: {
    flexDirection: "row",
    display: "flex",
  },
  gmailStyle: {
    width: "100%",
    marginTop: "20px",
    background: theme.palette.primary.contrastText,
  },
  linkedStyle: {
    width: "100%",
    marginTop: "20px",
    background: "#0E67C2",
    color: theme.palette.primary.contrastText,
  },
  githubStyle: {
    width: "100%",
    marginTop: "20px",
    background: "black",
    color: theme.palette.primary.contrastText,
  },
  labelWidth: {
    width: "100%",
  },
  dividerFullWidth: {
    margin: `40px 0 0 0`,
    width: "43%",
  },
  divider: {
    color: "#0c0f0f",
    marginTop: "32px",
    padding: "0 4% 0 5%",
    opacity: "56%",
  },
  createLogin: {
    marginTop: "10%",
    display: "flex",
    justifyContent: "center",
    flexDirection: "row" /* change this to row instead of 'column' */,
    flexWrap: "wrap",
    width: "100%",
  },
  width: {
    marginRight: "6px",
  },
  forgotPassword: {
    marginTop: "20px",
    width: "100%",
    display: "flex",
    justifyContent: "space-between",
    float: 'right'
  },
  forgoLink: {
    textAlign: "right",
    paddingTop: "10px",
    marginLeft:'auto'
  },
  link: {
    cursor: "pointer",
    width: "100%",
    color: "#2C2799",
    float: "right",
    fontSize: "0.875rem",
    fontFamily: '"lato" ,sans-serif',
    fontWeight: "600",
  },
  subText: {
    marginTop: "-20px",
    opacity: "0.7",
  },
  subTypo:{
    marginTop:'15px'
  },
  loginLink: {
    display: "flex",
    flexDirection: "column",
    flex: 1,
    marginTop: "18px",
    alignItems: "flex-end",
  },

  textField: {
    width: "100%",
    marginTop: "30px",
  },
  passwordHint: {
    opacity: "0.5",
    marginTop: "10px",
  },
  privatePolicy: {
    marginTop: "20px",
    width: "100%",
    display: "flex",
    flexDirection: "row",
  },
  policy: {
    marginTop: "10px",
    marginLeft: "-10px",
  },
  footer: {
    backgroundColor: "#F8F8F8",
    borderTop: "1px solid #E7E7E7",
    textAlign: "center",
    padding: "20px",
    position: "fixed",
    left: "25%",
    bottom: "0",
    height: "80px",
    width: "75%",
    "@media (max-width:600px)": {
      left: "0",
      width: "100%",
      padding: "0",
    },
    "@media (max-height:750px)": {
      display: "none"
    },
    "@media (max-width: 1000px) and (min-width: 600px)": {
      left: "34%",
      width: "66%",
    },
  },

  InputLabel: {
    marginLeft: "-15px",
    paddingBottom: "19px"
  },

  buttonProgress: {
    color: 'green[500]',
    position: 'absolute',
    top: '50%',
    left: '50%',
    marginTop: -12,
    marginLeft: -12,
  },
  typoDiv: {
    display: "flex",
    justifyContent: "center",
    flexDirection: "row",
    "@media (max-width:900px)": {
      flexDirection: "column",
    },
  },
  typoFooter: { opacity: "0.5", marginTop: "10px" },
  typoBold: { marginLeft: "3px", marginTop: "10px" },
  ActiveUserPaper: {
    margin: 'auto',
    marginTop: '5rem',
    width: '70%',
    minHeight: "3.5rem"
  },

  congrats: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
  },
});
export default LoginStyle;
