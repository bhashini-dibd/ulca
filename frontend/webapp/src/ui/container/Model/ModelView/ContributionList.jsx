import { withStyles, Button, Typography } from "@material-ui/core";
import { MuiThemeProvider } from '@material-ui/core/styles';
import createMuiTheme from "../../../styles/Datatable"
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
import RenderExpandTable from "./ExpandTable";
import SelectionList from "./BenchmarkSelection";

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
        const [selectionOpen, setSelectionOpen] = React.useState(null);
        
        const id = popoverOpen ? 'simple-popover' : undefined;

        useEffect(() => {
                (myContributionReport.filteredData.length === 0 || myContributionReport.refreshStatus || added) && MyContributionListApi()
        }, []);

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
                let result = ""
                let sId = event.currentTarget.id;
                myContributionReport.filteredData.forEach(item =>{
                       if( item.submitRefNumber === sId){
                               result = item
                       }
                })

               result && history.push({
                pathname: `${process.env.PUBLIC_URL}/search-model/${sId}`,
                state: result }) 
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

        const handleDocumentView = (srNo) =>{
                let result = ""
                myContributionReport.filteredData.forEach(item =>{
                       if( item.submitRefNumber === srNo){
                               result = item
                       }
                })
                if(result){
                        result.prevUrl = 'my-contri'
                }
               result && history.push({
                pathname: `${process.env.PUBLIC_URL}/search-model/${srNo}`,
                state: result }) 
                  
        }
        const renderEventList = (srNo) =>{
                return <Typography style={{cursor:"pointer"}}color="primary" onClick={() => handleDocumentView(srNo)}>View Card</Typography>
        }

        const handleRowClickSelection = (event) =>{

                setSelectionOpen(true)        
        }
        const handleCloseSelection = () =>{
                setSelectionOpen(false)
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
                        name: "Action",
                        label: "Action",
                        options: {
                          filter: true,
                          sort: false,
                          empty: true,
                          customBodyRender: (value, tableMeta, updateValue) => {
                            if (tableMeta.rowData) {
                        //       return <Button style={{background:"white",borderRadius:"1rem"}} onClick = {(event)=>handleRowClickSelection(event)}>Run Benchmark</Button>;
                              return <Button style={{background:"white",borderRadius:"1rem"}} >{renderEventList(tableMeta.rowData[0])}</Button>;
                            }
                          },
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
                // expandableRows: true,
                
      expandableRowsHeader: true,
      expandableRowsOnClick: false,
//       isRowExpandable: (dataIndex, expandedRows) => {
//         if (dataIndex === 3 || dataIndex === 4) return false;

//         // Prevent expand/collapse of any row if there are 4 rows expanded already (but allow those already expanded to be collapsed)
//         if (expandedRows.data.length > 4 && expandedRows.data.filter(d => d.dataIndex === dataIndex).length === 0) return false;
//         return true;
//       },
     
      renderExpandableRow: (rowData, rowMeta) => {
        const colSpan = rowData.length + 1;
        return (
               
          <RenderExpandTable/>
        );
      },
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
        return (
               
                <div>
                        {/* <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.model]} activeLink="My Contribution" />
                        </div> */}

                        {/* <div className={classes.title}>
                                
                        </div> */}


                        {view ? (data.length>0 && <GridView data= {data} rowChange={tableRowchange} handleRowClick = {handleRowClick} handleViewChange = {handleViewChange} handleShowFilter = {handleShowFilter} MyContributionListApi= {MyContributionListApi} view= {view} page ={PageInfo.page}handleCardClick = {handleCardClick} handleChangePage = {processTableClickedNextOrPrevious} rowsPerPage = {PageInfo.count}></GridView>)
                        :  <MuiThemeProvider theme={createMuiTheme}><MUIDataTable
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
                        {selectionOpen && <SelectionList
                                id={id}
                                open={selectionOpen}
                                data= {data}
                                handleClose={handleCloseSelection}
                                
                                clearAll={clearAll}
                                apply={apply}
                        />
                        }
                </div>
                
        );
};

export default withStyles(DataSet)(ContributionList);