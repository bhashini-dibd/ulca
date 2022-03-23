import { createMuiTheme } from "@material-ui/core/styles";

const themeDefault = createMuiTheme({
  typography: {
    fontFamily: '"Roboto"',
    fontWeight: "400",
  },
  overrides: {
    MuiTableRow: {
      root: {
        cursor: "pointer",
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
    MUIDataTableFilterList: {
      chip: {
        display: 'none'
      }
    },
    MuiMenu: {
      list: {
        minWidth: "210px"
      }
    },
    MUIDataTableFilter: {
      root: {
        backgroundColor: "white",
        width: "80%",
        fontFamily: '"Roboto" ,sans-serif',
      },
      checkboxFormControl: {
        minWidth: '200px'
      }
    },
    MuiList: {
      root: {
        fontFamily: '"Roboto" ,sans-serif',
      }

    },
    MUIDataTable: {
      paper: {
        minHeight: '674px',
        boxShadow: "0px 0px 2px #00000029",
        border: "1px solid #0000001F"
      },
      responsiveBase: {
        minHeight: "560px"
      }
    },
    MUIDataTableToolbar: {
      filterPaper: {
        width: "310px"
      },
      MuiButton: {
        root: {
          display: "none"
        }
      }

    },
    MuiGrid: {
      grid: {

        maxWidth: "100%"

      }
    },

    MuiTableCell: {
      head: {
        padding: ".6rem .5rem .6rem 1.5rem",
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
          width: "46%",
        },
        "&:nth-child(2)": {
          width: "18%",
        },
        "&:nth-child(3)": {
          width: "18%",
        },
        "&:nth-child(4)": {
          width: "18%",
        },
      },
    },

    // MuiTableFooter:{
    //   root:{

    //     "@media (min-width: 1050px)": {
    //       position:"absolute",
    //       right:"15%",
    //       top:"46.5rem",
    //       borderBottom:"1px"

    //     },

    //   }
    // },
    MuiPaper: {
      root: {
        boxShadow: "none !important",
        borderRadius: 0,
        border: "1px solid rgb(224 224 224)",
      },
    },
    MuiDialog: {
      paper: { minWidth: "360px", minHeight: "116px" }
    },
    MuiAppBar: {
      root: {
        boxSizing: "none",
        margin: "-1px",
        padding: "0px"
      }
    },
    MuiToolbar: {
      root: {
        padding: 0
      }
    },
    MuiFormControlLabel: {
      root: {
        height: '36px'
      },
      label: {
        fontFamily: '"Roboto" ,sans-serif',
        fontSize: '0.875rem'
      }

    },

    MUIDataTableBodyCell: {
      root: { padding: ".5rem .5rem .5rem .8rem", textTransform: "capitalize" },
    },
    MuiButton: {
      root: {
        minWidth: "25",
        borderRadius: "0",

      },
      label: {
        textTransform: "none",
        fontFamily: '"Segoe UI","Roboto"',
        fontSize: "15px",
        fontWeight: "500",
        lineHeight: "1.14",
        letterSpacing: "0.5px",
        textAlign: "center",
        height: "26px",
        "@media (max-width:550px)": {
          fontSize: ".75rem",
        }
      },
      sizeLarge: {
        height: "48px",
      },
      // sizeMedium: {
      //   height: "40px",
      // },
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
  fontSize: "3.125rem",
  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
};
themeDefault.typography.h2 = {
  fontSize: "2.5rem",
  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
};
themeDefault.typography.h3 = {
  fontSize: "1.6875rem",
  letterSpacing: "1.98px",
  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
};
themeDefault.typography.h4 = {
  fontSize: "1.5rem",
  // letterSpacing: "1.98px",
  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
};
themeDefault.typography.h5 = {
  fontSize: "1.3125rem",

  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
};
themeDefault.typography.h6 = {
  fontSize: "1.3125rem",
  fontFamily: '"Rowdies", cursive,"Roboto" ,sans-serif',
  fontWeight: "300",
  paddingTop: "4px",
  "@media (max-width:550px)": {
    fontSize: "1rem",
  }
};
themeDefault.typography.body1 = {
  fontSize: "1rem",
  fontFamily: '"Roboto", sans-serif ,sans-serif',
  fontWeight: "400"

};
themeDefault.typography.body2 = {
  fontSize: "0.875rem",
  fontFamily: '"Roboto", sans-serif',
  fontWeight: "400",
  color: "#0C0F0F",
  lineHeight: "22px",
  "@media (max-width:550px)": {
    fontSize: ".7rem",
  }
};
themeDefault.typography.caption = {
  fontSize: "0.75rem",
  fontFamily: "'Roboto', sans-serif",
  fontWeight: "400",
};
themeDefault.typography.subtitle1 = {
  fontSize: "1.125rem",
  fontFamily: "'Roboto', sans-serif",
  fontWeight: "400",
  "@media (max-width:550px)": {
    fontSize: ".9rem",
  }
};


export default themeDefault;
