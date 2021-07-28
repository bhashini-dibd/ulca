import { withStyles, Button } from "@material-ui/core";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../../redux/actions/api/Model/ModelView/MyContribution";
import { PageChange, RowChange, FilterTable, clearFilter,tableView } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction"
import ClearReport from "../../../../redux/actions/api/Model/ModelView/DatasetAction";
import Dialog from "../../../components/common/Dialog"
import { Cached, GridOn,List } from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useParams } from "react-router";
import C from "../../../../redux/actions/constants";
import FilterListIcon from '@material-ui/icons/FilterList';
import FilterList from "./FilterList";
import GridView from "./GridView";

const ContributionList = (props) => {

        const history = useHistory();
        const dispatch = useDispatch(ClearReport);
        const myContributionReport = useSelector((state) => state.modelContributionReport);
        const PageInfo = useSelector((state) => state.modelPageChangeDetails);
        const [open, setOpen] = useState(false)
        const view = useSelector((state) => state.modelTableView.view);
        const [message, setMessage] = useState("Do you want to delete")
        const [title, setTitle] = useState("Delete")
        const { added } = useParams()
        const data = myContributionReport.filteredData
        const [anchorEl, setAnchorEl] = React.useState(null);
        const popoverOpen = Boolean(anchorEl);
        const id = popoverOpen ? 'simple-popover' : undefined;

        useEffect(() => {
                (myContributionReport.filteredData.length === 0 || myContributionReport.refreshStatus || added) && MyContributionListApi()
        }, []);

        const getMuiTheme = () => createMuiTheme({
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

        const MyContributionListApi = () => {
                dispatch(ClearReport());
                const userObj = new MyContributionList("SAVE", "A_FBTTR-VWSge-1619075981554", "241006445d1546dbb5db836c498be6381606221196566");
                dispatch(APITransport(userObj));
        }

        const handleShowFilter = (event) => {
                setAnchorEl(event.currentTarget);
        }
        const handleClose = () => {
                setAnchorEl(null);
        };
        const clearAll = (data) => {
                dispatch(clearFilter(data, C.CLEAR_MODEL_FILTER))
        }
        const apply = (data) => {
                handleClose()
                dispatch(FilterTable(data, C.MODEL_CONTRIBUTION_TABLE))
        }

        const handleViewChange = () =>{
                dispatch(tableView(!view, C.MODEL_CONTRIBUTION_TABLE_VIEW))      
        }
        const handleCardClick = (event) =>{
               let sId = event.currentTarget.id;
               data.forEach((element)=>{
                       if(element.submitRefNumber== sId){
                        history.push(`${process.env.PUBLIC_URL}/dataset-status/${element.status}/${element.datasetName}/${element.submitRefNumber}`)    
                       }
               })
        }




        const fetchHeaderButton = () => {

                return <>
                        
                        {/* <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button> */}
                        <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={() => MyContributionListApi()}><Cached className={classes.iconStyle} />Refresh</Button>
                        <Button color={"default"} size="medium" variant="default"  className={classes.buttonStyle} onClick={handleViewChange}> {view ? <List size = "large" /> : <GridOn />}</Button>
                       
                        
                </>
        }
        const handleRowClick = (id,name,status) => {
                // history.push(`${process.env.PUBLIC_URL}/dataset-status/${status}/${name}/${id}`)
        };

        const handleDialogSubmit = () => {

        }

        const processTableClickedNextOrPrevious = ( sortOrder,page) => {
                dispatch(PageChange(page, C.MODEL_PAGE_CHANGE));

        }

        const tableRowchange = (event) =>{
                rowChange(event.target.value)
        }

        const rowChange=(rowsPerPage)=>{
                dispatch(RowChange(rowsPerPage, C.MODEL_ROW_COUNT_CHANGE))
        }

    


        const columns = [
                {
                        name: "submitRefNumber",
                        label: "s id",
                        options: {
                                filter: false,
                                sort: false,
                                display: "excluded",
                        },
                },
                {
                        name: "task",
                        label: "Task",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,
                        },
                },

                {
                        name: "modelName",
                        label: "Model Name",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,
                        },
                },
                {
                        name: "domain",
                        label: "Domain",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,
                        },
                },
                {
                        name: "licence",
                        label: "Licence",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,
                        },
                },
                {
                        name: "submittedOn",
                        label: "Submitted On",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,

                        },
                },
                {
                        name: "status",
                        label: "Submission Status",
                        options: {
                                filter: true,
                                sort: true,
                                display: view ? "excluded": true,

                        },
                },
                {
                        name: "action",
                        label: "Action",
                        options: {
                                filter: true,
                                sort: true,
                                display: view ? "excluded": true,

                        },
                }
        ];

        


        const options = {
                textLabels: {
                        body: {
                                noMatch: "No records"
                        },
                        toolbar: {
                                search: "Search",
                                viewColumns: "View Column",
                        },
                        pagination: {
                                rowsPerPage: "Rows per page",
                        },
                        options: { sortDirection: "desc" },
                },
                // onRowClick: rowData => handleRowClick(rowData[0],rowData[1],rowData[4]),
                // onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
                customToolbar: fetchHeaderButton,
                filter: false,
                displaySelectToolbar: false,
                fixedHeader: false,
                filterType: "checkbox",
                download: false,
                print: false,
                viewColumns: false,
                rowsPerPage: PageInfo.count,
                rowsPerPageOptions: [10, 25, 50, 100],
                selectableRows: "none",
                page: PageInfo.page,
                onTableChange: (action, tableState) => {
                        switch (action) {
                                case "changePage":
                                        processTableClickedNextOrPrevious(
                                                "", tableState.page
                                        );
                                        break;
                                case "changeRowsPerPage":
                                        rowChange(tableState.rowsPerPage)
                                        break;
                                default:
                        }
                },


        };

        const { classes } = props;

        console.log(myContributionReport)
        return (
               
                <div>
                        <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.model]} activeLink="My Contribution" />
                        </div>

                        {/* <div className={classes.title}>
                                
                        </div> */}


                        {view ? (data.length>0 && <GridView data= {data} rowChange={tableRowchange} handleRowClick = {handleRowClick} handleViewChange = {handleViewChange} handleShowFilter = {handleShowFilter} MyContributionListApi= {MyContributionListApi} view= {view} page ={PageInfo.page}handleCardClick = {handleCardClick} handleChangePage = {processTableClickedNextOrPrevious} rowsPerPage = {PageInfo.count}></GridView>)
                        :  <MuiThemeProvider theme={getMuiTheme()}><MUIDataTable
                                title={`My Contribution`}
                                data={data}
                                columns={columns}
                                options= {options}
                        /> </MuiThemeProvider>}

                        {open && <Dialog
                                message={message}
                                handleClose={() => { setOpen(false) }}
                                open
                                title={title}
                                handleSubmit={() => { handleDialogSubmit() }}
                        />}
                        {popoverOpen && <FilterList
                                id={id}
                                open={popoverOpen}
                                anchorEl={anchorEl}
                                handleClose={handleClose}
                                filter={myContributionReport.filter}
                                selectedFilter={myContributionReport.selectedFilter}
                                clearAll={clearAll}
                                apply={apply}
                        />
                        }
                </div>
                
        );
};

export default withStyles(DataSet)(ContributionList);