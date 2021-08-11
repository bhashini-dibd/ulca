const HeaderStyles = (theme) => ({

  toolbar: {
    minHeight: "54px",
    maxWidth: "1272px",
    width: "98%",
    margin: "0 auto",
    display: 'flex',
    alignItems: 'center',
    padding:"0",
    boxSizing :"border-box",
  
  },
  appBar:{
   boxSizing:"border-box",
   backgroundColor:"#DEECFF"
  },
  title: {
    color: 'white',
    marginLeft: "-6px",
    "@media (max-width:670px)": {
      display: 'none'
    },
  },
  alignItems: {
    display: 'flex',
    alignItems: 'center',
  },
  iconButton: {
    color: 'black',
    borderRadius: 0,
    maxHeight: '100%'
  },
  menu: {
    width: '100%',
    display: 'flex',
    alignItems: "center",
    cursor: "pointer"
  },
  datasetOption: {
    marginLeft: '8.4%',
    "@media (max-width:670px)": {
      marginLeft: '2%'
    }
  },
  options: {
    marginLeft: '1.875%'
  },
  profile: {
    marginLeft: 'auto',
    marginRight: '-1%'
  },
  menuBtn: {
    height: "54px",
    minWidth: "110px",
    color: 'white'
  },
  menuBtn2: {
    height: "37px",
    width: "88px",
    marginRight: "10px",
    backgroundColor: 'white',
    "&:hover": {

      backgroundColor: 'white',
    }
  },
  styledMenu: {
    padding: "9px",
    marginTop: "5px",
    "&:hover": {
      color: '#2C2799',
      backgroundColor: " rgba(44,39,153, .05)"
    }
  },
  styledMenu1: {
    padding: "9px",
    marginTop: "10px",
    boxShadow: "0px 3px 6px #00000029 !important"
  },
  profileName: {
    marginLeft: '0.5rem',
    "@media (max-width:800px)": {
      display: 'none'
    },
  },
  homeBtn: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  },
  avatar: {
    width: "36px",
    height: "36px",
    backgroundColor: "white",
    fontSize: "14px"
  },

  dataset: {
    "@media (max-width:425px)": {
      display: 'none'
    }
  },
  datasetMobile: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  },
  model: {
    "@media (max-width:425px)": {
      display: 'none'
    }
  },
  modelMobile: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  },
  signIn: {

    color: 'white'
  },
  signUp: {

    color: 'white'
  },
  desktopAuth: {
    "@media (max-width:400px)": {
      display: 'none'
    }
  },
  mobileAuth: {
    display: 'none',
    "@media (max-width:400px)": {
      display: 'block'
    }
  },
  selectGrid: {
    boxShadow: '3px 0 2px -2px #00000029',
    height: '54px',
    alignContent: 'center',
    display: 'grid'
  },
  toolGrid: {
    alignItems: 'center'
  },
  tempGrid: {
    paddingLeft: '1rem',
    boxShadow: '3px 0 2px -2px #00000029',
    height: '54px',
    alignContent: 'center',
    display: 'grid',
    "@media (max-width:800px)": {
      display:"none"
    }
  },
  btnStyle: {
    padding: '0px',
    justifyContent: 'left',
    fontSize: "1.125rem",
 // fontFamily: '"Poppins","Roboto" ,sans-serif',
  fontWeight: "500 !important",
    
  }
});
export default HeaderStyles;
