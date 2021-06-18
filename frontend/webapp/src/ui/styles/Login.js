const LoginStyle = (theme) => ({
  appInfo: {
    background: "#392C71",
    minHeight: "100vh",
  },
  title: {
    width: "20%",
    height: "auto",
    margin: "22% 294px 10% 39px",
    fontSize: "1.875rem",
    fontWeight: "600",
    cursor:"pointer",
    fontStretch: "normal",
    fontStyle: "normal",
    lineHeight: "1.53",
    letterSpacing: "3.9px",
    textAlign: "left",
    color: theme.palette.primary.contrastText,
  },
  subTitle: {
    width: "80%",
    height: "auto",
    maxWidth: "300px",
    margin: "20% 70px 15% 39px",
    fontSize: "1.9rem",
    fontWeight: "500",
    fontStretch: "normal",
    fontStyle: "normal",
    lineHeight: "1.5",
    letterSpacing: "1.6px",
    textAlign: "left",
    color: theme.palette.primary.contrastText,
    "@media (max-width:1040px)": {
      fontSize: "1.6rem",
      letterSpacing: "1px",
      maxWidth: "280px",
      width: "80%",
    },
    "@media (min-width:1790px)": {
      fontSize: "2rem",
      width: "68%",
    },
  },
  body: {
    width: "80%",
    height: "auto",
    margin: "30px 0px 50px 39px",
    fontFamily: "Lato",
    fontSize: "70%",
    fontWeight: "normal",
    fontStretch: "normal",
    fontStyle: "normal",
    lineHeight: "1.5",
    letterSpacing: "1.6px",
    textAlign: "left",
    color: "#f2f2f4",
    "@media (max-width:1040px)": {
      fontSize: "56%",
      letterSpacing: "1px",
      maxWidth: "280px",
    },
    "@media (min-width:1790px)": {
      fontSize: "90%",
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
sub:{
  width: "95%",
    height: "auto",
    maxWidth: "300px",
    margin: "20% 70px 0 39px",
    fontSize: "1.3rem",
    fontWeight: "500",
    fontStretch: "normal",
    fontStyle: "normal",
    lineHeight: "1.5",
    letterSpacing: "1.6px",
    textAlign: "left",
    color: theme.palette.primary.contrastText,
    "@media (max-width:1040px)": {
      fontSize: "1.2rem",
      letterSpacing: "1px",
      maxWidth: "280px",
      width: "80%"}

},
  loginGrid: {
    width: "33%",
    flexDirection: "column",
    display: "flex",
    height: "auto",
    verticalAlign: "middle",
    "@media (max-width:850px)": {
      minWidth: "270px",
      width: "85%",
    },
  },
  body2: {
    fontWeight: "550",
    
    // fontFamily: "Poppins Medium",
    paddingBottom: "25px",
    opacity: "1",
    fontSize: "1.7rem",
    height: "37px",
    color: "0C0F0F",
  },
  fullWidth: {
    width: "100%",
    marginTop: "30px",
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
    marginRight: "25px",
  },
  forgotPassword: {
    marginTop: "20px",
    width: "100%",
    display: "flex",
    justifyContent: "space-between",
  },
  forgoLink: {
    textAlign: "right",
    paddingTop: "10px",
  },
  link: {
    cursor: "pointer",
    width: "100%",
    color: "#922D88",
    float: "right",
  },
  subText: {
    marginTop: "-20px",
    opacity: "0.7",
  },
  loginLink: {
    display: "flex",
    flexDirection: "column",
    flex: 1,
    marginTop: "20px",
    alignItems: "flex-end",
  },

  textField: {
    width: "100%",
    marginTop: "20px",
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
  typoBold: { fontWeight: "bold", marginLeft: "3px", marginTop: "10px" },
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
