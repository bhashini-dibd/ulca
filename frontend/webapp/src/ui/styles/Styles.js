const GlobalStyles = (theme) => ({
  container: {

    maxWidth: "1272px",
    width: "100%",
    margin: "2rem auto",
    // background: theme.palette.background.default,
    fontFamily: theme.typography.fontFamily
  },

  headerContainer: {
    height: "70px"
  },
  root: {
    // background: "#F8F8F8",
    flexGrow: 1,
    height: window.innerHeight,
    zIndex: 1,
    position: "relative",
    minHeight: "720px",
    display: "flex",
    flexDirection:"column"
  },
  appBar: {
    backgroundColor: theme.palette.primary.dark,
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },

  typeTypo: {
    color: 'black',
    backgroundColor: "#FFD981",
    borderRadius: '24px',
    padding: '5px 10px',
    width: 'fit-content',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  },
  card: {
    marginBottom: '20px', height: '270px'
  },
  cardGrid: {
    marginTop: '20px'
  },
  modelname: {
    boxSizing:'border-box',
    marginTop: '15px',
    height: '64px',
    backgroundColor: 'white',
    maxWidth: '350px',
    width: 'auto',
    display: 'flex',
    alignItems: 'center',
    padding: '0 15px',
    borderRadius: '12px'
  }


});

export default GlobalStyles;
