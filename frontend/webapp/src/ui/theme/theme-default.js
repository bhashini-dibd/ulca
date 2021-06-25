import { createMuiTheme } from "@material-ui/core/styles";

const themeDefault = createMuiTheme({
  typography: {
    fontFamily: '"Lato"',
    fontWeight: "600",
  },
  overrides: {
    MuiTableRow: {
      root: { cursor: "pointer" ,
      '&.MuiTableRow-hover:hover': { 
        
        
          backgroundColor: "#F4F4FA"
        
    }

    

    },
      hover: {
      //   "&:hover": {
      //     color: '#2C2799',
      // backgroundColor: " rgba(44,39,153, .05)"
      // }
       
      }
    },
    MUIDataTable: {
      paper: {
        height: '670px',
        boxShadow: "0px 0px 2px #00000029",
border: "1px solid #0000001F"
      }
    },
    MuiTableCell: {
      head: {
        padding: ".55rem .5rem .55rem 1.5rem",
        backgroundColor: "#F8F8FA !important",
        marginLeft: "25px",
        letterSpacing: "0.74",
        fontWeight: "bold",
        minHeight: "700px",
      },
    },
    MUIDataTableHeadCell: {
      root: {
        "&:nth-child(1)": {
          width: "50%",
        },
      },
    },
    MuiPaper: {
      root: {
        boxShadow: "none !important",
        borderRadius: 0,
        border: "1px solid rgb(224 224 224)",
      },
    },
    MuiAppBar:{
      root:{
        boxSizing:"none",
        margin:"-1px",
        padding:"0px"
      }
    },
    MuiToolbar:{
      root:{
        padding:0
      }
    },

    MUIDataTableBodyCell: {
      root: { padding: ".5rem .5rem .5rem .8rem", textTransform: "capitalize" },
    },
    MuiButton: {
      label: {
        textTransform: "capitalize",
        fontWeight: "600",
        lineHeight: "1.14",
        letterSpacing: "0.14px",
        textAlign: "center",
        height: "26px",
      },
      sizeLarge: {
        height: "48px",
      },
      sizeSmall: {
        height: "36px",
      },
    
    }
   
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
  fontWeight: "500",
};
themeDefault.typography.h2 = {
  fontSize: "2rem",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500",
};
themeDefault.typography.h3 = {
  fontSize: "1.6875rem",
  letterSpacing: "1.98px",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500",
};
themeDefault.typography.h4 = {
  fontSize: "1.5rem",
  // letterSpacing: "1.98px",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500",
};
themeDefault.typography.h5 = {
  fontSize: "1.3125rem",

  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500",
};
themeDefault.typography.h6 = {
  fontSize: "1.125rem",
  fontFamily: '"Poppins","lato" ,sans-serif',
  fontWeight: "500",
};
themeDefault.typography.body1 = {
  fontSize: "1rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400",
};
themeDefault.typography.body2 = {
  fontSize: "0.875rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400",
};
themeDefault.typography.caption = {
  fontSize: "0.75rem",
  fontFamily: '"lato" ,sans-serif',
  fontWeight: "400",
};

export default themeDefault;
