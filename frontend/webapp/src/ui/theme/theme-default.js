import { createMuiTheme } from "@material-ui/core/styles";




const themeDefault = createMuiTheme({
  typography: {
    fontFamily: '"Lato"',
    fontWeight: "600",
  },
  overrides: {
    MuiTableRow:{root:{cursor: 'pointer'}},
    MuiTableCell: {
      head: {
              padding: '.5rem .5rem .5rem 1.5rem',
              backgroundColor : "#F8F8FA !important",
              marginLeft:"25px",
              letterSpacing:"0.74",
              fontWeight      :"bold",
              minHeight:"700px"
             
      }
},
MUIDataTableHeadCell: {
  root: {
          
    '&:nth-child(1)': {
      width: "50%"
    },
    
  }
},
MuiPaper: {
  root:{
          boxShadow       : 'none !important',
          borderRadius    : 0,
          border          : "1px solid rgb(224 224 224)"
  }
},

MUIDataTableBodyCell:{root : {padding: '.5rem .5rem .5rem .8rem', textTransform: "capitalize"}},
    MuiButton: {
      label: {
        textTransform: "capitalize",
        fontWeight: "600",
        lineHeight: "1.14",
        letterSpacing:"0.14px",
        textAlign: "center",
        height: "26px"
      },
    },
  },
  palette: {
    primary: {
      light: "#60568d",
      main: "#2C2799",
      dark: "#271e4f",
      contrastText: "#FFFFFF",
    },
    secondary: {
      light: "#FFFFFF",
      main: "#FFFFFF",
      dark: "#FFFFFF",
      contrastText: "#000000",
    },
    background: {
      default: "#2C2799",
    },
  },
});

themeDefault.typography.h1 = {
  fontSize: "2.25rem",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.h2 = {
  fontSize: "2rem",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.h3 = {
  fontSize: "1.6875rem",
  letterSpacing: "1.98px",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.h4 = {
  fontSize: "1.5rem",
  letterSpacing: "1.98px",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.h5 = {
  fontSize: "1.3125rem",
  
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.h6 = {
  fontSize: "1.125rem",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500"
};
themeDefault.typography.body1 = {
  fontSize: "1rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400"
};
themeDefault.typography.body2 = {
  fontSize: "0.875rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400"
};
themeDefault.typography.caption = {
  fontSize: "0.75rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400"
};





export default themeDefault;
