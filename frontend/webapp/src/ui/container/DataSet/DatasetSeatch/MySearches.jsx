import { withStyles, Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MySearchReport from "../../../../redux/actions/api/DataSet/DatasetSearch/MySearches";
import { PageChange, RowChange } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import C from "../../../../redux/actions/constants";
import { useDispatch, useSelector } from "react-redux";
import { Cached } from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useHistory } from "react-router-dom";
import FilterListIcon from '@material-ui/icons/FilterList';
import FilterList from "../DatasetView/FilterList";

const MySearches = (props) => {

        const detailedReport = useSelector((state) => state.mySearchReport);
        const PageInfo = useSelector((state) => state.searchPageDetails);
        const ApiStatus = useSelector((state) => state.apiStatus);
        const dispatch = useDispatch();
        const history = useHistory();
        const [page, setPage] = useState(0);
        //   const data = myContributionReport.filteredData
        const [anchorEl, setAnchorEl] = React.useState(null);
        const popoverOpen = Boolean(anchorEl);
        const id = popoverOpen ? 'simple-popover' : undefined;

        useEffect(() => {
                MySearchListApi()
        }, []);
        const MySearchListApi = () => {

                const userObj = new MySearchReport()
                dispatch(APITransport(userObj));
        }

        const processTableClickedNextOrPrevious = (page, sortOrder) => {
                // dispatch(PageChange(page, C.SEARCH_PAGE_NO));
                // setPage(page)
        }
        const handleShowFilter = (event) => {
                setAnchorEl(event.currentTarget);
        }
        const handleClose = () => {
                setAnchorEl(null);
        };
        const clearAll = (data) => {
                //  dispatch(clearFilter(data, C.CLEAR_FILTER))
        }
        const apply = (data) => {
                handleClose()
                //    dispatch(FilterTable(data, C.CONTRIBUTION_TABLE))
        }



        const fetchHeaderButton = () => {
                return <>
                        <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={() => MySearchListApi()}><Cached className={classes.iconStyle} />Refresh</Button>
                        {/* <Button color={"default"} size="medium" variant="outlined" className={classes.buttonStyle} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button> */}
                </>
        }

        const renderAction = (rowData) => {

                const status = rowData[4].toLowerCase();

                history.push({
                        pathname: `/search-and-download-rec/${status}/${rowData[0]}`,
                        pageInfo: page
                });
                // history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/${status}/${rowData[0]}`)
        }


        const columns = [
                {
                        name: "sr_no",
                        label: "SR No",
                        options: {
                                filter: false,
                                sort: false,
                                display: "excluded"

                        },
                },
                {
                        name: "search_criteria",
                        label: "Search Criteria",
                        options: {
                                filter: false,
                                sort: true,
                        },
                },
                {
                        name: "searched_on",
                        label: "Searched On",
                        options: {
                                filter: false,
                                sort: true,
                        },
                }, {
                        name: "count",
                        label: "#Record",
                        options: {
                                filter: false,
                                sort: true,

                        }
                },
                {
                        name: "status",
                        label: "Status",
                        options: {
                                filter: true,
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
                        pagination: { rowsPerPage: "Rows per page" },
                        options: { sortDirection: "desc" },
                },
                customToolbar: fetchHeaderButton,
                displaySelectToolbar: false,
                fixedHeader: false,
                filterType: "checkbox",
                download: false,
                print: false,
                rowsPerPageOptions: [10, 25, 50, 100],
                rowsPerPage: PageInfo.count,
                filter: false,
                page: PageInfo.page,
                viewColumns: false,
                selectableRows: "none",
                onTableChange: (action, tableState) => {
                        switch (action) {
                                case "changePage":
                                        processTableClickedNextOrPrevious(
                                                tableState.page
                                        );
                                        break;
                                case "changeRowsPerPage":
                                        dispatch(RowChange(tableState.rowsPerPage, C.SEARCH_ROW_COUNT_CHANGE))
                                        break;
                                default:
                        }
                },

                onRowClick: (rowData, rowMeta) => rowData[3] && renderAction(rowData)
        };

        const { classes } = props;
        return (
                <div >
                        {/* <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.dataset]} activeLink="My Searches" />
                        </div> */}

                        <MUIDataTable
                                title={`My Searches`}
                                data={detailedReport.responseData}
                                columns={columns}
                                options={options}
                        />
                        {popoverOpen && <FilterList
                                id={id}
                                open={popoverOpen}
                                anchorEl={anchorEl}
                                handleClose={handleClose}
                                filter={{datasetType: [], status:[]} }
                                selectedFilter={{datasetType: [], status:[]} }
                                //  filter={{ datasetType: [], status: ['completed', 'in-progress', 'failed'] }}
                                // selectedFilter={{ datasetType: [], status: ['completed', 'in-progress'] }}
                                clearAll={clearAll}
                                apply={apply}
                        />
                        }

                </div>
        );
};

export default withStyles(DataSet)(MySearches);
