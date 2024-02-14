const FooterStyle = (theme) => ({
  grid: {
    display: "flex",
    background: "#DFEAFB",
    padding: "30px 50px",
    color: "#16337B",
    alignItems: "center",
  },
  grid2: {
    display: "flex",
    background: "#C6D9F4",
    padding: "30px 50px",
    color: "#16337B",
    alignItems: "center",
  },
  grid3: {
    display: "flex",
    background: "#B3CAED",
    padding: "30px 50px",
    color: "#16337B",
    alignItems: "center",
  },
  social: {
    display: "flex",
    marginLeft: "auto",
    width: "40%",
    justifyContent: "space-between",
    "@media  (max-device-width : 650px)": {
      marginTop: "20px",
      width: "100%",
      justifyContent: "center",
      gap: "8px",
    },
  },
  container: {
    width: "100%",
    display: "flex",
    boxSizing: "border-box",
    flexWrap: "wrap",
  },
  bhasini: {
    display: "flex",
    marginRight: "auto",
    width: "60%",
    justifyContent: "space-between",
    marginTop: "10px",
    "@media  (max-device-width : 650px)": {
      width: "100%",
      justifyContent: "center",
      alignItems: "center",
      flexDirection: "column",
      margin: "0 auto",
      gap: "10px",
    },
  },
  info: {
    display: "flex",
    marginLeft: "auto",
    width: "70%",
    justifyContent: "space-between",
    marginTop: "30px",
    marginBottom: "30px",
    "@media  (max-device-width : 650px)": {
      width: "100%",
      justifyContent: "space-evenly",
      margin: "0 auto",
    },
    "@media  (max-device-width : 1100px) and (min-device-width: 650px)": {
      width: "50%",
    },
  },
  infoNew: {
    display: "flex",
    marginLeft: "auto",
    width: "74%",
    justifyContent: "space-between",
    marginTop: "20px",
    "@media  (max-device-width : 650px)": {
      width: "100%",
      justifyContent: "space-evenly",
      margin: "0 auto",
    },
  },
  copy: {
    display: "flex",
    marginRight: "auto",
    width: "80%",
    marginTop: "30px",
  },
  parent: { background: "#0F2749 !important" },
  parentDiv: {
    maxWidth: "1240px",
    display: "flex",
    margin: "auto",
    "@media  (max-device-width : 1100px) and (min-device-width: 650px)": {
      maxWidth: "100%",
      padding: "12px 50px",
      overFlow:"hidden",
    },
  },
  textAlign: { textAlign: "center", marginTop: "2rem" },
  image: { marginRight: "1rem" },
  link: {
    // fontWeight: "bold",
    color: "black",
    marginTop: "10px",
    overflowWrap: "anywhere",
  },
  tdlLogoSection: {
    "@media  (max-device-width : 650px)": {
      width: "100%",
      display: "flex",
      justifyContent: "center",
      position: "relative",
      bottom: "25px",
    },
  },
  tdlLogo: {
    "@media  (max-device-width : 650px)": { width: "90%", marginTop: "20px" },
  },
  mobileDesigned: {
    "@media  (max-device-width : 650px)": {
      position: "relative",
      top: "135px",
    },
    "@media  (max-device-width : 1000px) and (min-device-width: 650px)": {
      position: "relative",
      right: "-65px",
    },
    "@media  (max-device-width : 1200px) and (min-device-width: 1001px)": {
      position: "relative",
      right: "-95px",
    },
  },
  FooterLogo: {
    display: "flex",
    marginTop: "2rem",
    justifyContent: "flex-end",
    marginBottom: "1rem",
    "@media  (max-device-width : 650px)": {
      width: "100%",
      justifyContent: "center",
      marginTop: "7rem",
    },
  },
  BottomText: {
    display: "flex",
    marginLeft: "3rem",
    marginTop: "1rem",
    justifyContent: "flex-start",

    marginBottom: "2rem",
    "@media  (max-device-width : 650px)": {
      width: "90%",
      justifyContent: "center",
      marginLeft: "1rem",
      textAlign: "center",
    },
  },
});
export default FooterStyle;
