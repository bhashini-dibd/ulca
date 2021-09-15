import { withStyles, Link, Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../../redux/actions/api/DataSet/DatasetView/MyContribution";
import { PageChange, RowChange, FilterTable, clearFilter,tableView } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction"
import ClearReport from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import Dialog from "../../../components/common/Dialog"
import { Cached, DeleteOutline, VerticalAlignTop,GridOn,List } from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useParams } from "react-router";
import C from "../../../../redux/actions/constants";
import FilterListIcon from '@material-ui/icons/FilterList';
import FilterList from "./FilterList";
import GridView from "./GridView";

const ContributionList = (props) => {

        const history = useHistory();
        const dispatch = useDispatch(ClearReport);
        const myContributionReport = useSelector((state) => state.myContributionReport);
        const PageInfo = useSelector((state) => state.pageChangeDetails);
        const [open, setOpen] = useState(false)
        const view = useSelector((state) => state.tableView.view);
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
                dispatch(clearFilter(data, C.CLEAR_FILTER))
        }
        const apply = (data) => {
                handleClose()
                dispatch(FilterTable(data, C.CONTRIBUTION_TABLE))
        }

        const handleViewChange = () =>{
                dispatch(tableView(!view, C.CONTRIBUTION_TABLE_VIEW))      
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
                        
                        <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button>
                        <Button color={"primary"} size="medium" variant="outlined" className={classes.buttonStyle} onClick={() => MyContributionListApi()}><Cached className={classes.iconStyle} />Refresh</Button>
                        <Button color={"default"} size="medium" variant="default"  className={classes.buttonStyle} onClick={handleViewChange}> {view ? <List size = "large" /> : <GridOn />}</Button>
                       
                        
                </>
        }
        const handleRowClick = (id,name,status) => {
                history.push(`${process.env.PUBLIC_URL}/dataset-status/${status}/${name}/${id}`)
        };

        const handleDialogSubmit = () => {

        }

        const processTableClickedNextOrPrevious = ( sortOrder,page) => {
                dispatch(PageChange(page, C.PAGE_CHANGE));

        }

        const tableRowchange = (event) =>{
                rowChange(event.target.value)
        }

        const rowChange=(rowsPerPage)=>{
                dispatch(RowChange(rowsPerPage, C.ROW_COUNT_CHANGE))
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
                        name: "datasetName",
                        label: "Dataset Name",
                        options: {
                                filter: false,
                                sort: true,
                                display: view ? "excluded": true,
                        },
                },
                {
                        name: "datasetType",
                        label: "Dataset Type",
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
                        label: "Status",
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
                onRowClick: rowData => handleRowClick(rowData[0],rowData[1],rowData[4]),
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
        return (
                <div>
                        {/* <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.dataset]} activeLink="My Contribution" />
                        </div> */}

                        {/* <div className={classes.title}>
                                
                        </div> */}


                        {view ? (data.length>0 && <GridView data= {data} rowChange={tableRowchange} handleRowClick = {handleRowClick} handleViewChange = {handleViewChange} handleShowFilter = {handleShowFilter} MyContributionListApi= {MyContributionListApi} view= {view} page ={PageInfo.page}handleCardClick = {handleCardClick} handleChangePage = {processTableClickedNextOrPrevious} rowsPerPage = {PageInfo.count}></GridView>)
                        : <MUIDataTable
                                title={`My Contribution`}
                                data={data}
                                columns={columns}
                                options= {options}
                        /> }

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