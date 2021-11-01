import { withStyles, Button, Grid } from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import DataSet from "../../../styles/Dataset";
import MUIDataTable from "mui-datatables";
import {
  PageChange,
  RowChange,
  FilterTable,
  clearFilter,
  tableView,
} from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import Dialog from "../../../components/common/Dialog";
import { Cached } from "@material-ui/icons";
import C from "../../../../redux/actions/constants";
import FilterListIcon from "@material-ui/icons/FilterList";
import FilterList from "./FilterList";
import GridView from "./GridView";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValue from "../../../../redux/actions/api/DataSet/DatasetView/GetSearchedValues";

const ContributionList = (props) => {
  const history = useHistory();
  const dispatch = useDispatch();
  const [anchorEl, setAnchorEl] = useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;
  const {
    data,
    myContributionReport,
    MyContributionListApi,
    added,
    clearAll,
    apply,
    PageInfo,
  } = props;

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const [open, setOpen] = useState(false);
  const view = useSelector((state) => state.tableView.view);
  const [message, setMessage] = useState("Do you want to delete");
  const [title, setTitle] = useState("Delete");

  // useEffect(() => {
  //   for (let i = 0; i < data.length; i++) {
  //     if (data[i].submitRefNumber === added) {
  //       let page = Math.floor(i / 10);
  //       async function dispatchPageAction(i) {
  //         await dispatch(PageChange(page, C.PAGE_CHANGE));
  //         let element = await document.getElementById(
  //           `MUIDataTableBodyRow-${i}`
  //         );
  //         if (element) {
  //           element.scrollIntoView({
  //             behavior: "smooth",
  //           });
  //           element.animate([{ backgroundColor: "rgba(254, 191, 44, 0.1)" }], {
  //             duration: 1500,
  //             iterations: 5,
  //             easing: "ease-in-out",
  //           });
  //         }
  //       }
  //       dispatchPageAction(i);
  //       return;
  //     }
  //   }
  // }, []);

  useEffect(() => {
    window.scrollTo(0, 0);
  });

  const handleViewChange = () => {
    dispatch(tableView(!view, C.CONTRIBUTION_TABLE_VIEW));
  };
  const handleCardClick = (event) => {
    let sId = event.currentTarget.id;
    data.forEach((element) => {
      if (element.submitRefNumber == sId) {
        history.push(
          `${process.env.PUBLIC_URL}/dataset-status/${element.status}/${element.datasetName}/${element.submitRefNumber}`
        );
      }
    });
  };

  const handleSearch = (value) => {
    dispatch(getSearchedValue(value));
  };

  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={0}>
        <Grid item xs={8} sm={8} md={8} lg={8} xl={8}>
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
          <Button
            color={"default"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
            Filter
          </Button>
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
          <Button
            color={"primary"}
            size="medium"
            variant="outlined"
            className={classes.buttonStyle}
            onClick={() => MyContributionListApi()}
          >
            <Cached className={classes.iconStyle} />
            Refresh
          </Button>
        </Grid>
      </Grid>
      //       {/* <Button color={"default"} size="medium" variant="default"  className={classes.buttonStyle} onClick={handleViewChange}> {view ? <List size = "large" /> : <GridOn />}</Button> */}
    );
  };
  const handleRowClick = (id, name, status) => {
    history.push(
      `${process.env.PUBLIC_URL}/dataset-status/${status}/${name}/${id}`
    );
  };

  const handleDialogSubmit = () => {};

  const processTableClickedNextOrPrevious = (sortOrder, page) => {
    dispatch(PageChange(page, C.PAGE_CHANGE));
  };

  const tableRowchange = (event) => {
    rowChange(event.target.value);
  };

  const rowChange = (rowsPerPage) => {
    dispatch(RowChange(rowsPerPage, C.ROW_COUNT_CHANGE));
  };

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
        display: view ? "excluded" : true,
      },
    },
    {
      name: "datasetType",
      label: "Dataset Type",
      options: {
        filter: false,
        sort: true,
        display: view ? "excluded" : true,
      },
    },
    {
      name: "submittedOn",
      label: "Submitted On",
      options: {
        filter: false,
        sort: true,
        display: view ? "excluded" : true,
      },
    },
    {
      name: "status",
      label: "Status",
      options: {
        filter: true,
        sort: true,
        display: view ? "excluded" : true,
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
      pagination: {
        rowsPerPage: "Rows per page",
      },
      options: { sortDirection: "desc" },
    },
    onRowClick: (rowData) =>
      rowData[2] !== "Benchmark" &&
      handleRowClick(rowData[0], rowData[1], rowData[4]),
    // onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
    customToolbar: fetchHeaderButton,
    search: false,
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
          processTableClickedNextOrPrevious("", tableState.page);
          break;
        case "changeRowsPerPage":
          rowChange(tableState.rowsPerPage);
          break;
        default:
      }
    },
  };

  const { classes } = props;
  return (
    <div>
      <MUIDataTable
        title={`My Contribution`}
        data={data}
        columns={columns}
        options={options}
      />

      {open && (
        <Dialog
          message={message}
          handleClose={() => {
            setOpen(false);
          }}
          open
          title={title}
          handleSubmit={() => {
            handleDialogSubmit();
          }}
        />
      )}
      {popoverOpen && (
        <FilterList
          id={id}
          open={popoverOpen}
          anchorEl={anchorEl}
          handleClose={handleClose}
          filter={myContributionReport.filter}
          selectedFilter={myContributionReport.selectedFilter}
          clearAll={(data) => clearAll(data, handleClose)}
          apply={(data) => apply(data, handleClose)}
        />
      )}
    </div>
  );
};

export default withStyles(DataSet)(ContributionList);
