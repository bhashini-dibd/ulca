const styles = (theme) => ({
  toolbar: {
    minHeight: "48px",
    maxWidth: "1272px",
    width: "98%",
    margin: "0 auto",
    display: "flex",
    alignItems: "center",
    padding: "0",
    boxSizing: "border-box",
  },
  search: {
    position: "relative",
    borderRadius: "24px",
    backgroundColor: "#F3F3F3",
    marginLeft: 0,
    width: "220px",
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: "100%",
    position: "absolute",
    pointerEvents: "none",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    color: "#00000029",
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(3)}px)`,
    transition: theme.transitions.create("width"),
    width: "100%",
    fontStyle: "italic",
    fontSize: "14px",
  },
  appTab: {
    borderTop: "none",
    borderRight: "none",
    borderLeft: "none",
    "@media (max-width:1287px)": {
     
      marginLeft: "3%",
    },
  },
  gridAlign: {
    justifyContent: "flex-end",
    alignItems: "flex-end",
    "@media (max-width:750px)": {
      justifyContent: "flex-start",
      marginBottom: "1%",
    },
  },
  iconStyle: { marginRight: ".5rem" },
  filterBtn: { borderRadius: "22px" },
  tablabel: {
    fontSize: "20px",
    fontWeight: "300",
    letterSpacing: "0px",
    fontFamily: "Rowdies",
    // '&:first-child':{
    padding: "0",
    marginRight: "54px",
    "@media (min-width:600px)": {
      minWidth: "auto",
    },

    "@media (max-width:600px)": {
      marginRight: "20px",
      minWidth: "auto",
    },
    "@media (max-width:550px)": {
      fontSize: "1rem",
    },
    "&.MuiTab-textColorInherit.Mui-selected": {
      fontWeight: "300",
    },
  },
});

export default styles;
