const FileUploadStyles = (theme) => ({
  

  breadcrum: {
    marginBottom: "1.5rem",
  },

  cursor: {
    cursor: "pointer",
  },
  titleBar: {
    margin: "0 0 1vh 1vh",
    display: "flex",
    flexDirection: "row",
  },
  paper: {
    padding: "3%",
  },
 
  select: {
    width: "20%",
    minWidth: "10rem",
    color:"grey"
  },
  filterButton:{
    marginLeft: 'auto',
    paddingRight: '5%',
    minWidth:"auto",
    display: "flex",
    flexDirection: "row",

    // "@media (max-width:800px)": {
    //   display: 'none'
    // }
  },
  filterButtonIcon:{
    
    display: 'none',
    "@media (max-width:800px)": {
      marginLeft: 'auto',
      display: 'block',
    marginRight: '5%',
    maxWidth:"3rem",
    maxHeight:"2.3rem"
    }
  },

  langPairButtons: {
    display: "flex",
    justifyContent: "flex-end",
    width: "100%",
    padding: ".6rem 1rem",
    boxSizing: "border-box",
  },
  cardHeader: {
    display: "flex",
    alignItems: "center",
    borderBottom: "1px solid #EEEEF0",
    padding: ".6rem 1rem",
    width: "100%",
    boxSizing: "border-box",
  },
  backButton: {
    boxShadow: "none",
    backgroundColor: "#F0F1F3",
    color: "#0C0F0F",
    marginRight: ".5rem",
  },
  seperator: {
    width: "1px",
    height: "2rem",
    backgroundColor: "#DADCE0",
    margin: "0 1rem",
    fontSize: ".75rem",
  },
  cardHeaderContainer: {
    display: "flex",
    flexDirection: "row",
    minHeight: "2.3rem",
  },
  iconStyle:{
    marginRight:".7rem",
    "@media (max-width:800px)": {marginRight:"0"}
  },
  title:{
    textAlign: "left",
    margin: "4.5vh 0 8.5vh 1vh",
    "@media (max-width:600px)": {textAlign: "center",}
  }
});

export default FileUploadStyles;
