import { createMuiTheme } from '@material-ui/core/styles';

let style =  createMuiTheme({
                
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
              
            },
            sizeLarge: {
              height: "48px",
            },
            sizeSmall: {
              height: "36px",
            },
      
      
          }
      
        
          
    //       MUIDataTableHeadCell: {
    //         root: {
    //           "&:nth-child(1)": {
    //             width: "20%",
    //           },
    //           "&:nth-child(2)": {
    //             width: "18%",
    //           },
    //           "&:nth-child(3)": {
    //             width: "18%",
    //           },
    //           "&:nth-child(4)": {
    //             width: "18%",
    //           },
    //         },
    //       },
}

})

export default style;