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
              fontWeight      :"bold"
             
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
      light: "#000000",
      main: "#000000",
      dark: "#000000",
      contrastText: "#FFFFFF",
    },
    background: {
      default: "#2C2799",
    },
  },
});

themeDefault.typography.h4 = {
  fontSize: "1.875rem",
  fontWeight: "500",

  fontFamily: '"Poppins","lato" ,sans-serif',
  textAlign: "Left",
  "@media (min-width:600px)": {},
  [themeDefault.breakpoints.up("md")]: {},
};


export default themeDefault;
