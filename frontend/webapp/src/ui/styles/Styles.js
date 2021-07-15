const GlobalStyles = (theme) => ({
  container: {
    
    maxWidth: "1272px",
    width : "100%",
    margin :"4.1rem auto",
    // background: theme.palette.background.default,
    fontFamily: theme.typography.fontFamily
  },

  headerContainer :{
    height:"70px"
  },
  root: {
    background: "#F8F8F8",
    flexGrow: 1,
    height: window.innerHeight,
    zIndex: 1,
    position: "relative",
    minHeight: "720px",
    display: "flex",
  },
  appBar: {
    backgroundColor: theme.palette.primary.dark,
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  
  

});

export default GlobalStyles;
