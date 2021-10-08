import { withStyles, Button, Typography, Grid } from "@material-ui/core";
import BreadCrum from "../../../components/common/Breadcrum";
import React, { useEffect, useState } from "react";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MySearchReport from "../../../../redux/actions/api/DataSet/DatasetSearch/MySearches";
import {
  PageChange,
  RowChange,
} from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import C from "../../../../redux/actions/constants";
import { useDispatch, useSelector } from "react-redux";
import { Cached } from "@material-ui/icons";
import UrlConfig from "../../../../configs/internalurlmapping";
import { useHistory } from "react-router-dom";
import FilterListIcon from "@material-ui/icons/FilterList";
import FilterList from "../DatasetView/FilterList";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValue from '../../../../redux/actions/api/DataSet/DatasetSearch/GetSearchedValues';
import { useParams } from "react-router";


const MySearches = (props) => {
  const detailedReport = useSelector((state) => state.mySearchReport);
  const PageInfo = useSelector((state) => state.searchPageDetails);
  const ApiStatus = useSelector((state) => state.apiStatus);
  const dispatch = useDispatch();
  const history = useHistory();
  const { added } = useParams();
  const [page, setPage] = useState(0);
    const data = detailedReport.filteredData
  const [anchorEl, setAnchorEl] = React.useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;

  useEffect(() => {
    MySearchListApi();
  }, []);
  const MySearchListApi = () => {
    const userObj = new MySearchReport();
    dispatch(APITransport(userObj));
  };

  useEffect(() => {
    for (let i = 0; i < data.length; i++) {
      if (data[i].sr_no === added) {
        let page = Math.floor(i / 10);
        async function dispatchPageAction(i) {
          await dispatch(PageChange(page, C.SEARCH_PAGE_NO));
          let element = await document.getElementById(
            `MUIDataTableBodyRow-${i}`
          );
          element &&
            element.scrollIntoView({
              behavior: "smooth",
            });
          element.animate([{ backgroundColor: "rgba(254, 191, 44, 0.1)" }], {
            duration: 1500,
            iterations: 5,
            easing: "ease-in-out",
          });
        }
        dispatchPageAction(i);
        return;
      }
    }
  }, [data]);

  const processTableClickedNextOrPrevious = (page, sortOrder) => {
    dispatch(PageChange(page, C.SEARCH_PAGE_NO));
    setPage(page)
  };
  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const clearAll = (data) => {
    //  dispatch(clearFilter(data, C.CLEAR_FILTER))
  };
  const apply = (data) => {
    handleClose();
    //    dispatch(FilterTable(data, C.CONTRIBUTION_TABLE))
  };

  const handleSearch = (value)=>{
    dispatch(getSearchedValue(value));
  }
  

  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={1}>
        <Grid item xs={10} sm={10} md={10} lg={10} xl={10}>
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
          <Button
            color={"primary"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            onClick={() => MySearchListApi()}
          >
            <Cached className={classes.iconStyle} />
            Refresh
          </Button>
        </Grid>
      </Grid>
    );
  };

  const renderAction = (rowData) => {
    const status = rowData[4].toLowerCase();

    history.push({
      pathname: `/search-and-download-rec/${status}/${rowData[0]}`,
      pageInfo: page,
    });
    // history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/${status}/${rowData[0]}`)
  };

  const columns = [
    {
      name: "sr_no",
      label: "SR No",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
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
    },
    {
      name: "count",
      label: "#Record",
      options: {
        filter: false,
        sort: true,
        customBodyRender: (value, tableMeta, updateValue) => {
          return (
            <div style={{ textTransform: "none" }}>
              {tableMeta.rowData[5] === "asr-corpus" ||
              tableMeta.rowData[5] === "asr-unlabeled-corpus"
                ? `${tableMeta.rowData[3]} hrs`
                : tableMeta.rowData[3]}
            </div>
          );
        },
      },
    },
    {
      name: "status",
      label: "Status",
      options: {
        filter: true,
        sort: true,
      },
    },
    {
      name: "datasetType",
      label: "Dataset Type",
      options: {
        display: "excluded",
      },
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No records",
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
    search: false,
    onTableChange: (action, tableState) => {
      switch (action) {
        case "changePage":
          processTableClickedNextOrPrevious(tableState.page);
          break;
        case "changeRowsPerPage":
          dispatch(
            RowChange(tableState.rowsPerPage, C.SEARCH_ROW_COUNT_CHANGE)
          );
          break;
        default:
      }
    },

    onRowClick: (rowData, rowMeta) => rowData[3] && renderAction(rowData),
  };

  const { classes } = props;
  return (
    <div>
      {/* <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.dataset]} activeLink="My Searches" />
                        </div> */}

      <MUIDataTable
        title={`My Searches`}
        data={detailedReport.filteredData}
        columns={columns}
        options={options}
      />
      {popoverOpen && (
        <FilterList
          id={id}
          open={popoverOpen}
          anchorEl={anchorEl}
          handleClose={handleClose}
          filter={{ datasetType: [], status: [] }}
          selectedFilter={{ datasetType: [], status: [] }}
          //  filter={{ datasetType: [], status: ['completed', 'in-progress', 'failed'] }}
          // selectedFilter={{ datasetType: [], status: ['completed', 'in-progress'] }}
          clearAll={clearAll}
          apply={apply}
        />
      )}
    </div>
  );
};

export default withStyles(DataSet)(MySearches);
