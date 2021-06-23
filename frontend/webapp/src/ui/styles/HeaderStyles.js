const HeaderStyles = (theme) => ({

  toolbar: {
    minHeight: "56px",
    maxWidth: "1272px",
    width: "100%",
    margin: "0 auto",
    display: 'flex',
    alignItems: 'center',
    padding: "0"
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
    cursor:"pointer"
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
    fontSize: '1rem',
    color: 'white'
  },
  styledMenu: {
    padding: "9px",
    marginTop: "5px",
    // borderTop: "1px solid #D6D6D6"
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
    fontSize: '1rem',
    color: 'white'
  },
  signUp: {
    fontSize: '1rem',
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
  }
});
export default HeaderStyles;
