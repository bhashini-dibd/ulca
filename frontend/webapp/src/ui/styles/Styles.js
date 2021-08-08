const GlobalStyles = (theme) => ({
  container: {

    maxWidth: "1272px",
    width: "100%",
    margin: "4.1rem auto",
    // background: theme.palette.background.default,
    fontFamily: theme.typography.fontFamily
  },

  headerContainer: {
    height: "70px"
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

  typeTypo: {
    color: 'black',
    backgroundColor: "#FFD981",
    borderRadius: '24px',
    padding: '5px 10px',
    width: 'fit-content',
    fontSize: '12px',
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
    marginTop: '15px',
    height: '64px',
    backgroundColor: 'white',
    maxWidth: '340px',
    width: 'auto',
    display: 'flex',
    alignItems: 'center',
    paddingLeft: '15px',
    fontWeight: '600',
    borderRadius: '12px'
  }


});

export default GlobalStyles;
