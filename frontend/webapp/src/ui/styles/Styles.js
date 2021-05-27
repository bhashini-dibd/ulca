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
    background: theme.palette.background.default,
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
});

export default GlobalStyles;
