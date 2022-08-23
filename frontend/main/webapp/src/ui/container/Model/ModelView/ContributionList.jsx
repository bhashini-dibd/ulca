import { withStyles, Button, Typography, Grid, Box } from "@material-ui/core";
import { MuiThemeProvider } from "@material-ui/core/styles";
import createMuiTheme from "../../../styles/Datatable";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../../redux/actions/api/Model/ModelView/MyContribution";
import Modal from "@material-ui/core/Modal";
import {
  PageChange,
  RowChange,
  FilterTable,
  clearFilter,
  tableView,
} from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import ClearReport from "../../../../redux/actions/api/Model/ModelView/DatasetAction";
import Dialog from "../../../components/common/Dialog";
import { Cached, GridOn, List } from "@material-ui/icons";
import { useParams } from "react-router";
import C from "../../../../redux/actions/constants";
import FilterList from "./FilterList";
import GridView from "./GridView";
import RenderExpandTable from "./ExpandTable";
import SelectionList from "./BenchmarkSelection";
import BenchmarkModal from "./BenchmarkModal";
import clearBenchMark from "../../../../redux/actions/api/Model/ModelView/ClearBenchmark";
import SubmitBenchmark from "../../../../redux/actions/api/Model/ModelView/SubmitBenchmark";
import Spinner from "../../../components/common/Spinner";
import RunBenchmarkAPI from "../../../../redux/actions/api/Model/ModelView/RunBenchmark";
import SwitchModelStatus from "../../../../redux/actions/api/Model/ModelView/SwitchModelStatus";
import FilterListIcon from "@material-ui/icons/FilterList";
import myContribFilter from "../../../../redux/actions/api/Model/ModelView/myContribFilter";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValues from "../../../../redux/actions/api/Model/ModelView/GetSearchedValues";
import { translate } from "../../../../assets/localisation";
import { useRef } from "react";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import LightTooltip from "../../../components/common/LightTooltip";

const ContributionList = (props) => {
  const history = useHistory();
  const dispatch = useDispatch(ClearReport);
  const [loading, setLoading] = useState(false);
  const myContributionReport = useSelector(
    (state) => state.modelContributionReport
  );
  const PageInfo = useSelector((state) => state.modelPageChangeDetails);
  const [open, setOpen] = useState(false);
  const [benchmarkInfo, setBenchmarkInfo] = useState({
    type: "",
    domain: [],
    modelId: "",
  });
  const view = useSelector((state) => state.modelTableView.view);
  const [message, setMessage] = useState("Do you want to delete");
  const [title, setTitle] = useState("Delete");
  const [index, setIndex] = useState([]);
  const { added } = useParams();
  const data = myContributionReport.filteredData;
  const totalCount = myContributionReport.totalCount;
  const [searchValue, setSearchValue] = useState("");
  const [anchorEl, setAnchorEl] = React.useState(null);
  const popoverOpen = Boolean(anchorEl);
  const [selectionOpen, setSelectionOpen] = React.useState(null);
  const benchmark = useSelector(
    (state) => state.getBenchMarkDetails.benchmarkInfo
  );
  const id = popoverOpen ? "simple-popover" : undefined;
  const [openModal, setOpenModal] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [modelStatusInfo, setModelStatusInfo] = useState({
    modelId: "",
    status: "",
    reason: "",
  });

  const status = useSelector((state) => state.getBenchMarkDetails.status);
  const refHook = useRef(false);

  // useEffect(() => {
  //   (myContributionReport.filteredData.length === 0 ||
  //     myContributionReport.refreshStatus ||
  //     added) &&
  //     MyContributionListApi();
  // }, []);

  useEffect(() => {
    if (status === "completed") {
      setLoading(false);
      setOpenModal(true);
    }
  });

  useEffect(() => {
    if (!refHook.current) {
      MyContributionListApi();
      refHook.current = true;
    }
  }, []);

  useEffect(() => {
    return () => {
      refHook.current = false;
    };
  }, []);

  useEffect(() => {
    for (let i = 0; i < data.length; i++) {
      if (data[i].submitRefNumber === added) {
        let page = Math.floor(i / PageInfo.count);
        async function dispatchPageAction(i) {
          await dispatch(PageChange(page, C.MODEL_PAGE_CHANGE));
          let element = document.querySelector(
            `[data-testid=MUIDataTableBodyRow-${i}]`
          );
          let oldIndex = index;
          setIndex([...oldIndex, i]);
          if (element) {
            element.scrollIntoView({
              behavior: "smooth",
            });
            let previousColor = element.style.backgroundColor;
            element.style.backgroundColor = "rgba(254, 191, 44, 0.1)";
            element.style.transitionTimingFunction = "ease-out";
            element.style.transitionDelay = "0.1s";
            element.style.transition = "0.2s";
            setTimeout(() => {
              element.style.backgroundColor = previousColor;
              element.style.transitionTimingFunction = "";
              element.style.transitionDelay = "";
              element.style.transition = "";
            }, 4000);
          }
        }
        dispatchPageAction(i);
        return;
      }
    }
  }, [data]);

  // useEffect(() => {
  //   window.scrollTo(0, 0);
  // });
  // useEffect(() => {
  //   document.querySelectorAll(`button`).forEach((element) => {
  //     element.classList.forEach((list) => {
  //       if (list.includes("MUIDataTableHeadCell-toolButton-")) {
  //         document.querySelector(`.${list}`).removeAttribute("title");
  //       }
  //     });
  //   });
  // }, []);

  const MyContributionListApi = async (start = 1, end = 1) => {
    dispatch(ClearReport());
    const userObj = new MyContributionList(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      start,
      end,
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    dispatch(APITransport(userObj));
    refHook.current = false;
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const clearAll = (data) => {
    dispatch(clearFilter(data, C.CLEAR_MODEL_FILTER));
  };
  const apply = (data) => {
    handleClose();
    dispatch(FilterTable(data, C.MODEL_CONTRIBUTION_TABLE));
  };

  const handleViewChange = () => {
    dispatch(tableView(!view, C.MODEL_CONTRIBUTION_TABLE_VIEW));
  };
  const handleCardClick = (event) => {
    let result = "";
    let sId = event.currentTarget.id;
    myContributionReport.filteredData.forEach((item) => {
      if (item.submitRefNumber === sId) {
        result = item;
      }
    });

    result &&
      history.push({
        pathname: `${process.env.PUBLIC_URL}/search-model/${sId}`,
        state: result,
      });
  };
  const handleSearch = (value) => {
    setSearchValue(value);
    processTableClickedNextOrPrevious("", 0);
    dispatch(getSearchedValues(value));
  };
  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={1} className={classes.Gridroot}>
        <Grid item xs={7} sm={8} md={8} lg={8} xl={8}>
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
          </Button>
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
          <Button
            color={"default"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
            {translate("button.filter")}
          </Button>
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
          <Button
            color={"primary"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            onClick={() => MyContributionListApi()}
          >
            <Cached className={classes.iconStyle} />
            {translate("button.refresh")}
          </Button>
        </Grid>
        <Grid item xs={3} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
          <Button
            color={"primary"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            onClick={() => MyContributionListApi()}
          >
           
            <Cached className={classes.iconStyle} />
          </Button>
        </Grid>
      </Grid>
    );
  };
  const handleRowClick = (id, name, status) => {
    // history.push(`${process.env.PUBLIC_URL}/dataset-status/${status}/${name}/${id}`)
  };

  const handleDialogSubmit = () => { };

  const processTableClickedNextOrPrevious = (sortOrder, page) => {
    window.scrollTo(0, 0);
    dispatch(PageChange(page, C.MODEL_PAGE_CHANGE));
  };

  const tableRowchange = (event) => {
    rowChange(event.target.value);
  };

  const rowChange = (rowsPerPage) => {
    dispatch(RowChange(rowsPerPage, C.MODEL_ROW_COUNT_CHANGE));
  };

  const handleDocumentView = (srNo) => {
    let result = "";
    myContributionReport.filteredData.forEach((item) => {
      if (item.submitRefNumber === srNo) {
        result = item;
      }
    });
    if (result) {
      result.prevUrl = "my-contri";
    }
    result &&
      history.push({
        pathname: `${process.env.PUBLIC_URL}/search-model/${srNo}`,
        state: result,
      });
  };
  // const renderEventList = (srNo) =>{
  //         return <Typography style={{cursor:"pointer"}}color="primary" onClick={() => handleDocumentView(srNo)}>View Card</Typography>
  // }

  const handleRowClickSelection = (event) => {
    setSelectionOpen(true);
  };
  const handleCloseSelection = () => {
    setSelectionOpen(false);
  };

  const handleCloseModal = () => {
    dispatch(clearBenchMark());
    setOpenModal(false);
  };

  const renderBenchmarkModal = () => {
    return (
      <Modal
        id="benchmarkTable"
        open={openModal}
        onClose={handleCloseModal}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <BenchmarkModal
          makeSubmitAPICall={makeSubmitAPICall}
          handleCloseModal={handleCloseModal}
          type={benchmarkInfo.type}
          domain={benchmarkInfo.domain}
          
        />
      </Modal>
      
    );
  };

  const handleRunBenchMarkClick = (type, domain, modelId) => {
    setLoading(true);
    dispatch(clearBenchMark());
    setBenchmarkInfo({ type, domain: [domain], modelId });
    const apiObj = new RunBenchmarkAPI(type, [domain], modelId);
    dispatch(APITransport(apiObj));
  };

  const makeSubmitAPICall = () => {
    const apiObj = new SubmitBenchmark(benchmarkInfo.modelId, benchmark);
    fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    }).then(async (res) => {
      // let rsp_data = await res.json();
      if (res.ok) {
        let benchmarkIndex = 0;
        data.forEach((model, i) => {
          if (model.submitRefNumber === benchmarkInfo.modelId) {
            benchmarkIndex = i;
          }
        });
        MyContributionListApi();
        setIndex([benchmarkIndex]);
      }
      handleCloseModal();
    });
  };

  const toggleModelStatusAPI = (modelId, status, reason) => {
    const toggledStatus =
      status === "unpublished"
        ? "published"
        : status === "published" && "unpublished";
    const apiObj = new SwitchModelStatus(modelId, toggledStatus, reason);
    fetch(apiObj.apiEndPoint(), {
      method: "POST",
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers,
    })
      .then(async (res) => {
        handleDialogClose();
        if (res.ok) {
          const userObj = new MyContributionList(
            "SAVE",
            "A_FBTTR-VWSge-1619075981554",
            `${PageInfo.page+1}`, 
            `${PageInfo.page+1}`,
            "241006445d1546dbb5db836c498be6381606221196566"
          );
          fetch(userObj.apiEndPoint(), {
            method: "get",
            headers: userObj.getHeaders().headers,
          }).then(async (resp) => {
            let resp_data = await resp.json();
            if (resp.ok) {
              dispatch({
                type: "TOGGLE_MODEL_STATUS",
                payload: {
                  data: resp_data.data,
                  searchValue,
                },
              });
            }
          });
        }
      })
      .catch((err) => {
        handleDialogClose();
      });
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
  };

  const openConfirmationDialog = (status, modelId) => {
    setOpenDialog(true);
    setModelStatusInfo({ status, modelId });
  };

  const handleTextBox = (input) => {
    setModelStatusInfo({ ...modelStatusInfo, reason: input });
  }

  const isDisabled = (benchmarkPerformance) => {
    for (let i = 0; i < benchmarkPerformance.length; i++) {
      if (benchmarkPerformance[i].status === "In-Progress") {
        return true;
      }
    }
    return false;
  };

  const renderActionButtons = (
    benchmarkPerformance,
    status,
    type,
    domain,
    modelId
  ) => {
    if (status !== "failed" && status !== "In Progress") {
      return (
        <Grid container spacing={1}>
          <Grid item>
            {status !== "published" && (
              <Button
                className={classes.benchmarkActionButtons}
                style={{ color: "#FD7F23", fontSize: "1rem" }}
                size="small"
                variant="contained"
                onClick={() => handleRunBenchMarkClick(type, domain, modelId)}
              >
                Run Benchmark
              </Button>
            )}
          </Grid>
          <Grid item xs={3} sm={3} md={3} lg={3} xl={3}>
            <Button
              size="small"
              variant="contained"
              className={classes.benchmarkActionButtons}
              disabled={
                status === "failed" ||
                  status === "In Progress" ||
                  isDisabled(benchmarkPerformance)
                  ? true
                  : false
              }
              style={{
                color: status === "published" ? "#F54336" : "#139D60",
                fontSize: "1rem",
              }}
              onClick={() => openConfirmationDialog(status, modelId)}
            >
              {status === "published" ? "Unpublish" : "Publish"}
            </Button>
          </Grid>
        </Grid>
      );
    }
    return <Typography>--</Typography>;
  };

  const returnColor = (status) => {
    switch (status) {
      case "failed":
        return "#F54336";
      case "completed":
        return "#139D60";
      case "submitted":
        return "#139D60";
      case "published":
        return "#2A61AD";
      default:
        return "#FD7F23";
    }
  };

  const renderStatus = (reason, status) => {
    return (
      <Box display="flex">
        <Typography
          variant="body1"
          style={{
            color: returnColor(status),
          }}
        >
          {status}{" "}
        </Typography>
        {
            status !== "published" && reason ? (
              <LightTooltip
                arrow
                placement="right"
                title={reason}
              >
                <InfoOutlinedIcon
                  className={classes.buttonStyle}
                  fontSize="small"
                  color="disabled"
                />
              </LightTooltip>
            ) : null
          }
      </Box>
    );
  };

  const renderExpandTableStatus = (status) => {
    return (
      <Typography
        variant="body1"
        style={{
          color: returnColor(status),
        }}
      >
        {status}
      </Typography>
    );
  }

  const convertDate = (date) => {
    return date
      .toLocaleString("en-IN", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      })
      .toUpperCase();
  };

  const renderConfirmationDialog = () => {
    const { status, modelId, reason } = modelStatusInfo;
    return (
      <Dialog
        title={`${status === "published" ? "Unpublish Model" : "Publish Model"
          }`}
        message={`${status === "published"
            ? "After the model is unpublished, it will not be available for public use. Are you sure you want to unpublish the model?"
            : "After the model is published, it will be available for public use. Are you sure you want to publish the model?"
          }`}
        handleSubmit={() => toggleModelStatusAPI(modelId, status, reason)}
        handleClose={handleDialogClose}
        actionButton="Cancel"
        actionButton2="Yes"
        open={openDialog} 
        showTextBox={status === "published" ? true : false}
        handleTextBox={(e) => handleTextBox(e)}
      />
    );
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
      name: "task",
      label: "Task",
      options: {
        filter: false,
        sort: true,
       display: view ? "excluded" : true,
       
      },
    },
    {
      name: "modelName",
      label: "Model Name",
      
      options: {
       
        filter: false,
        // setCellProps: sort  => ({ style: { width:"100px" } }),
        sort: true,
        display: view ? "excluded" : true,
      },
    },
    {
      name: "version",
      label: "Version",
      options: {
        filter: false,
        sort: true,
        display: view ? "excluded" : true,
        customBodyRender: (value, tableMeta, updateValue) => {
          if (tableMeta.rowData) {
            return (
              <Typography style={{ textTransform: "none" }} variant="body2">
                {tableMeta.rowData[3]}
              </Typography>
            );
          }
        },
      },
    },
    {
      name: "domain",
      label: "Domain",
      options: {
        filter: false,
        sort: true,
        display: view ? "excluded" : true,
      },
    },
    {
      name: "license",
      label: "License",
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
        customBodyRender: (rowData) => {
          const date = new Date(rowData);
          return <>{convertDate(date)}</>;
        },
        sortDirection: "desc",
      },
    },
    {
      name: "status",
      label: "Status",
      options: {
        filter: true,
        sort: true,
        display: view ? "excluded" : true,
        customBodyRender: (value, tableMeta, updateValue) => {
          if (tableMeta.rowData) {
            return renderStatus(tableMeta.rowData[10], tableMeta.rowData[7]);
          }
        },
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
            return renderActionButtons(
              tableMeta.rowData[9],
              tableMeta.rowData[7],
              tableMeta.rowData[1],
              tableMeta.rowData[4],
              tableMeta.rowData[0]
            );
          }
        },
      },
    },
    {
      name: "benchmarkPerformance",
      label: "Benchmark Performance",
      options: {
        display: "excluded",
      },
    },
    {
      name: "unpublishReason",
      label: "unPublishReason",
      options: {
        display: false,
      },
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No records ",
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
    // onRowClick: (rowData) => {
    //   handleDocumentView(rowData[0]);
    // },
    //     onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
    customToolbar: fetchHeaderButton,
    search: false,
    filter: false,
    displaySelectToolbar: false,
    filterType: "checkbox",
    fixedHeader: false,
    download: false,
    expandableRows: true,
    onRowExpansionChange: (
      currentRowsExpanded,
      allRowsExpanded,
      rowsExpanded
    ) => {
      let newIndex = [];
      if (!allRowsExpanded.length) {
        setIndex([]);
      }
      allRowsExpanded.forEach((row) => {
        newIndex.push(row.dataIndex);
      });
      setIndex(newIndex);
    },
    rowsExpanded: index,
    expandableRowsHeader: true,
    expandableRowsOnClick: false,
    // isRowExpandable: (dataIndex, expandedRows) => expandedRows.data.filter(d=>index.indexOf(d.index) > -1),
    renderExpandableRow: (rowData, rowMeta) => {
      const colSpan = rowData.length + 1;
      const even_odd = rowMeta.rowIndex % 2 === 0;
      if (rowData[9].length)
        return (
          <RenderExpandTable
            rows={rowData[9]}
            color={even_odd}
            renderStatus={renderExpandTableStatus}
          />                                        
        );
    },
    print: false,
    viewColumns: false,
    rowsPerPage: data.length,
    // rowsPerPageOptions: [10, 25, 50, 100],
    rowsPerPageOptions: false,
    selectableRows: "none",
    page: PageInfo.page,
    count: totalCount,
    serverSide: true,
    onTableChange: (action, tableState) => {
      switch (action) {
        case "changePage":
          MyContributionListApi(`${tableState.page+1}`, `${tableState.page+1}`)
          processTableClickedNextOrPrevious("", tableState.page);
          break;
        case "changeRowsPerPage":
          rowChange(tableState.rowsPerPage);
          break;
        default:
      }
    },
  };

  const handleCheckboxClick = (value, prop) => {
    dispatch(myContribFilter(value, prop));
  };

  const { classes } = props;
  return (
    <div>
      {loading && <Spinner />}
      {/* <div className={classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.model]} activeLink="My Contribution" />
                        </div> */}

      {/* <div className={classes.title}>
                                
                        </div> */}

      {view ? (
        data.length > 0 && (
          <GridView
            data={data}
            rowChange={tableRowchange}
            handleRowClick={handleRowClick}
            handleViewChange={handleViewChange}
            handleShowFilter={handleShowFilter}
            MyContributionListApi={MyContributionListApi}
            view={view}
            page={PageInfo.page}
            handleCardClick={handleCardClick}
            handleChangePage={processTableClickedNextOrPrevious}
            rowsPerPage={PageInfo.count}
          ></GridView>
        
        )
      ) : (
        <MuiThemeProvider theme={createMuiTheme}>
          <MUIDataTable
           // title={`My Contribution`}
            data={data}
            columns={columns}
            options={options}
          />{" "}
        </MuiThemeProvider>
      )}

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
          clearAll={clearAll}
          handleCheckboxClick={handleCheckboxClick}
          apply={apply}
        />
      )}
      {selectionOpen && (
        <SelectionList
          id={id}
          open={selectionOpen}
          data={data}
          handleClose={handleCloseSelection}
          clearAll={clearAll}
          apply={apply}
        />
      )}
      {openModal && renderBenchmarkModal()}
      {openDialog && renderConfirmationDialog()}
    </div>
  );
};

export default withStyles(DataSet)(ContributionList);
