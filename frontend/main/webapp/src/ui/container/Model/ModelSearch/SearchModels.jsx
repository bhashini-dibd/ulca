import { withStyles, Button } from "@material-ui/core";
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
import { PageChange, RowChange, FilterTable, clearFilter, tableView } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction"
import ClearReport from "../../../../redux/actions/api/Model/ModelView/DatasetAction";
import Dialog from "../../../components/common/Dialog"
import { Cached, GridOn, List } from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useParams } from "react-router";
import C from "../../../../redux/actions/constants";
import FilterListIcon from '@material-ui/icons/FilterList';
import updateFilter from '../../../../redux/actions/api/Model/ModelSearch/Benchmark';


const ContributionList = (props) => {
        const history = useHistory();
        const dispatch = useDispatch(ClearReport);
        const myContributionReport = useSelector((state) => state.searchModel);
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

        const handleViewChange = () => {
                dispatch(tableView(!view, C.MODEL_CONTRIBUTION_TABLE_VIEW))
        }
        const handleCardClick = (event) => {
                let sId = event.currentTarget.id;
                data.forEach((element) => {
                        if (element.submitRefNumber == sId) {
                                history.push(`${process.env.PUBLIC_URL}/dataset-status/${element.status}/${element.datasetName}/${element.submitRefNumber}`)
                        }
                })
        }
        const fetchHeaderButton = () => {

                return <>

                        {/* <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button> */}
                        {/* <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={() => MyContributionListApi()}><Cached className={classes.iconStyle} />Refresh</Button>
                        <Button color={"default"} size="medium" variant="default"  className={classes.buttonStyle} onClick={handleViewChange}> {view ? <List size = "large" /> : <GridOn />}</Button> */}


                </>
        }
        const handleRowClick = (id, name, status) => {
                const {source,target,type} = props;
                const payload = {source,target,type};
                dispatch(updateFilter(payload));
                let result = ""
                myContributionReport.filteredData.forEach(item => {
                        if (item.submitRefNumber === id) {
                                result = item
                        }
                })
                result && history.push({
                        pathname: `${process.env.PUBLIC_URL}/search-model/${id}`,
                        state: result
                })
        };

        const handleDialogSubmit = () => {

        }

        const processTableClickedNextOrPrevious = (sortOrder, page) => {
                dispatch(PageChange(page, C.MODEL_PAGE_CHANGE));

        }

        const tableRowchange = (event) => {
                rowChange(event.target.value)
        }

        const rowChange = (rowsPerPage) => {
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

                        },
                },

                {
                        name: "modelName",
                        label: "Model Name",
                        options: {
                                filter: false,
                                sort: true,

                        },
                },
                {
                        name: "language",
                        label: "Language",
                        options: {
                                filter: false,
                                sort: true,

                        },
                },
                {
                        name: "domain",
                        label: "Domain",
                        options: {
                                filter: false,
                                sort: true,

                        },
                },
                {
                        name: "submitter",
                        label: "Submitter",
                        options: {
                                filter: false,
                                sort: true,



                        }
                },
                {
                        name: "publishedOn",
                        label: "Published On",
                        options: {
                                filter: false,
                                sort: true,


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
                onRowClick: rowData => handleRowClick(rowData[0], rowData[1], rowData[4]),
                customToolbar: fetchHeaderButton,
                filter: false,
                displaySelectToolbar: false,
                fixedHeader: false,
                filterType: "checkbox",
                download: false,
                print: false,
                viewColumns: false,

                rowsPerPageOptions: [10, 25, 50, 100],
                selectableRows: "none",



        };
        return (

                <div>
                        <MuiThemeProvider theme={createMuiTheme}><MUIDataTable
                                title={`Search result for Models`}
                                data={data}
                                columns={columns}
                                options={options}
                        /> </MuiThemeProvider>

                        {open && <Dialog
                                message={message}
                                handleClose={() => { setOpen(false) }}
                                open
                                title={title}
                                handleSubmit={() => { handleDialogSubmit() }}
                        />}

                </div>

        );
};

export default withStyles(DataSet)(ContributionList);