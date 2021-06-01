const drawerWidth = 240;
//let logo = require('../../../assets/logo.png')

const GlobalStyles = (theme) => ({
  container: {
    margin: "4em 0em 0em 0em",
    width: "100%",
    // background: theme.palette.background.default,
    fontFamily: theme.typography.fontFamily,
    minHeight: "calc(100vh - 5em)",
  },
  root: {
    
    flexGrow: 1,
    // height: 430,
    zIndex: 1,
    overflow: "hidden",
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
  content: {
    background: theme.palette.background.default,
    flexGrow: 1,
    backgroundColor: theme.palette.background.default,
    padding: theme.spacing(3),
    marginTop: "3%",
  },
  loaderStyle:{
    position: 'fixed',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',      
      zIndex: 1000,
    width:'100%',
    height:'100%',  
      top:0,
    left:0,
    
  },
  progress: {   
    position:'relative',
    top:'40%',
    left:'46%'
          
  }
});

export default GlobalStyles;
