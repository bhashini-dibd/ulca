import {
  createMuiTheme,
  MuiThemeProvider,
  withStyles,
} from "@material-ui/core/styles";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";
import DatasetStyle from "../../../styles/Dataset";
import SearchIcon from "@material-ui/icons/Search";

import {
  IconButton,
  Button,
  Grid,
  InputBase,
  TableCell,
  TableRow,
  TableBody,
  TableHead,
  Box,
  Table,
} from "@material-ui/core";
import { useState } from "react";
import MUIDataTable from "mui-datatables";
import { useDispatch, useSelector } from "react-redux";
import getBenchmarkMetric from "../../../../redux/actions/api/Model/ModelView/BenchmarkMetric";
import FilterBenchmark from "./FilterBenchmark";
import searchBenchmark from "../../../../redux/actions/api/Model/ModelView/SearchBenchmark";
import {
  filterBenchmark,
  clearFilterBenchmark,
} from "../../../../redux/actions/api/Model/ModelView/FilterBenchmark";
import CustomPagination from "../../../components/common/CustomPagination";
import { useEffect } from "react";

const BenchmarkModal = (props) => {
  const { classes } = props;
  const dispatch = useDispatch();
  const data = useSelector((state) => state.getBenchMarkDetails.filteredData);
  const benchmarkInfo = useSelector(
    (state) => state.getBenchMarkDetails.benchmarkInfo
  );
  const submitStatus = useSelector(
    (state) => state.getBenchMarkDetails.submitStatus
  );
  const selectedIndex = useSelector(
    (state) => state.getBenchMarkDetails.selectedIndex
  );
  const availableFilters = useSelector(
    (state) => state.getBenchMarkDetails.availableFilters
  );
  const [anchorEl, setAnchorEl] = useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;
  const [selectedFilters, setSelectedFilters] = useState([]);

  useEffect(() => {
    document.addEventListener("keydown", function (event) {
      if (event.key === "Escape") {
        props.handleCloseModal();
      }
    });
  }, []);

  useEffect(() => {
    return () => {
      document.removeEventListener("keydown", props.handleCloseModal);
    };
  }, []);

  const handleClose = () => {
    setAnchorEl(null);
  };

  const apply = () => {
    handleClose();
    dispatch(filterBenchmark(selectedFilters));
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const fetchModalFooter = () => {
    return (
      <Button
        color="primary"
        style={{ float: "right", marginTop: "20px", borderRadius: "22px" }}
        variant="contained"
        onClick={props.makeSubmitAPICall}
        disabled={!submitStatus}
      >
        Submit
      </Button>
    );
  };

  const handleSearch = (event) => {
    dispatch(searchBenchmark(event.target.value));
  };
  const fetchModalToolBar = () => {
    return (
      <Grid container spacing={2} className={classes.gridAlign}>
        <Grid item>
          <div className={classes.search}>
            <div className={classes.searchIcon}>
              <SearchIcon fontSize="small" />
            </div>
            <InputBase
              placeholder="Search..."
              onChange={(e) => handleSearch(e)}
              value={props.searchValue}
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              inputProps={{ "aria-label": "search" }}
            />
          </div>
        </Grid>
        <Grid item>
          {/* <Button
            variant="outlined"
            size="medium"
            className={classes.filterBtn}
            onClick={handleShowFilter}
            disabled={count ? false : true}
          >
            <FilterListIcon className={classes.iconStyle} />
            Filter
          </Button> */}
        </Grid>
      </Grid>
    );
  };

  const renderSelectButton = (
    type,
    index,
    status,
    parentIndex,
    disabled = false
  ) => {
    return (
      <Button
        variant="outlined"
        style={{
          backgroundColor: status ? "#2A61AD" : "white",
          width: "85px",
        }}
        className={classes.filterBtn}
        onClick={() => {
          dispatch(getBenchmarkMetric(type, index, parentIndex));
        }}
        disabled={disabled}
      >
        {status ? <CheckIcon style={{ color: "#FFFFFF" }} /> : "Select"}
      </Button>
    );
  };
  const columns = [
    {
      name: "benchmarkId",
      label: "Benchmark Id",
      options: {
        display: "excluded",
      },
    },
    {
      name: "datasetName",
      label: "Benchmark Dataset",
      options: {
        setCellProps: () => ({ style: {width:"350px"}}),
        filter: false,
        sort: false,
      },
    },
    {
      name: "domain",
      label: "Domain",
      options: {
        setCellProps: () => ({ style: {width:"350px"}}),
        filter: false,
        sort: false,
      },
    },
    {
      name: "description",
       label: "Description",
      options: {
        setCellProps: () => ({ style: {width:"330px"}}),
        filter: false,
        sort: false,
      },
    },
    {
      name: "selected",
      label: "Selected",
      options: {
        display: "excluded",
      },
    },
    {
      name: "Action",
      options: {
        setCellProps: () => ({ style: {width:"350px"}}),
        filter: false,
        sort: false,
        customBodyRender: (value, tableMeta, updateValue) => {
          return renderSelectButton(
            "DATASET",
            tableMeta.rowData[0],
            tableMeta.rowData[4],
            tableMeta.rowIndex
          );
        },
      },
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No benchmark dataset available",
      },
    },
    customToolbar: fetchModalToolBar,
    print: false,
    viewColumns: false,
    selectableRows: false,
    download: false,
    search: false,
    filter: false,
    // pagination: true,
    // customFooter: fetchModalFooter,
    customFooter: (
      count,
      page,
      rowsPerPage,
      changeRowsPerPage,
      changePage,
      textLabels
    ) => {
      return (
        <Grid container>
          <Grid item xs={10} sm={11} md={11} lg={11} xl={11}>
            <CustomPagination
              count={count}
              page={page}
              rowsPerPage={rowsPerPage}
              changeRowsPerPage={changeRowsPerPage}
              changePage={changePage}
              textLabels={textLabels}
            />
          </Grid>
          <Grid
            style={{ display: "flex", alignItems: "center" }}
            item
            xs={2}
            sm={1}
            md={1}
            lg={1}
            xl={1}
          >
            {fetchModalFooter()}
          </Grid>
        </Grid>
      );
    },
    expandableRows: true,
    rowsExpanded: selectedIndex,
    customRowRenderer: (data, dataIndex, rowIndex) => {},
    renderExpandableRow: (rowData, rowMeta) => {
      const rows = data[rowMeta.dataIndex].metric;
      return (
        <>
          <TableRow>
            <TableCell colSpan={6}>
              <>
                <Box style={{ margin: "0 80px" }}>
                  <Table size="small" aria-label="purchases">
                    <TableHead>
                      <TableRow>
                        <TableCell>
                          <strong>Metric</strong>
                        </TableCell>
                        {/* <TableCell align="left">Description</TableCell> */}
                        <TableCell>
                          <strong>Action</strong>
                        </TableCell>
                        <TableCell></TableCell>
                        <TableCell></TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {rows.map((row, i) => {
                        return (
                          <TableRow
                            key={i}
                            style={{
                              backgroundColor: "rgba(254, 191, 44, 0.1)",
                            }}
                          >
                            {/* <TableCell></TableCell> */}
                            <TableCell>
                              {row.metricName.toUpperCase()}
                            </TableCell>
                            {/* <TableCell align="left">{row.description}</TableCell> */}
                            <TableCell>
                              {renderSelectButton(
                                "METRIC",
                                i,
                                row.selected,
                                rowMeta.dataIndex,
                                row.isMetricDisabled
                              )}
                            </TableCell>
                            <TableCell></TableCell>
                            <TableCell></TableCell>
                          </TableRow>
                        );
                      })}
                    </TableBody>
                  </Table>
                </Box>
              </>
            </TableCell>
          </TableRow>
          <TableRow className={classes.tableRow}></TableRow>
        </>
      );
    },
    isRowExpandable: (dataIndex, expandedRows) => {
      if (dataIndex === 3 || dataIndex === 4) return false;

      // Prevent expand/collapse of any row if there are 4 rows expanded already (but allow those already expanded to be collapsed)
      if (
        expandedRows.data.length > 4 &&
        expandedRows.data.filter((d) => d.dataIndex === dataIndex).length === 0
      )
        return false;
      return true;
    },
  };

  const getMuiTheme = () =>
    createMuiTheme({
      overrides: {
        MuiTable: {
          root: {
            width: "100%",
          },
        },
        MUIDataTableToolbar: {
          root: {
            "@media (max-width: 599.95px)": {
             // display: "flex !important",
              whiteSpace:" nowrap",
              marginBottom:"5px"
             
            },
          },
        },
        MUIDataTableBodyRow: {
          root: {
            "&:nth-child(odd)": {
              backgroundColor: "#D6EAF8",
            },
            "&:nth-child(even)": {
              backgroundColor: "#E9F7EF",
            },
          },
        },
        MUIDataTable: {
          paper: {
            padding: "21px",
            width: "65.375rem",
            "@media (max-width:1090px)": {
              width: "55.375rem",
            },
            "@media (max-width:930px)": {
              width: "45.375rem",
            },
            "@media (max-width:760px)": {
              width: "35.375rem",
            },
            "@media (max-width:605px)": {
              width: "30.375rem",
            },
            "@media (max-width:530px)": {
              width: "25.375rem",
            },
            "@media (max-width:450px)": {
              width: "20.375rem",
            },
          },
          responsiveBase: {
            oveflow: "initial",
            overflowX: "hidden",
            overflowY: "auto",
            minHeight: "51vh",
            maxHeight: "51vh",
          },
        },
        MUIDataTableSelectCell: {
          icon: {
            display: "none",
          },
        },
        MuiTableCell: {
          head: {
            // padding: ".6rem .5rem .6rem 1.5rem",
            backgroundColor: "#F8F8FA !important",
            marginLeft: "25px",
            letterSpacing: "0.74",
            fontWeight: "bold",
            minHeight: "700px",
          },
          paddingCheckbox: {
            display: "none",
          },
        },
        MUIDataTableHeadCell: {
          root: {
            minWidth: "100%",
          },
          fixedHeader: {
            position: "initial",
            "&:nth-child(2)": {
              width: "30%",
            },
            "&:nth-child(3)": {
              width: "10%",
            },
            "&:nth-child(4)": {
              width: "50%",
            },
            "&:nth-child(5)": {
              width: "10%",
            },
          },
        },
        MuiToolbar: {
          root: {
            marginTop: "8px",
          },
          gutters: {
            padding: "0",
            "@media (min-width:600px)": {
              paddingLeft: "0",
              paddingRight: "0",
            },
          },
        },
        MUIDataTableHeadRow: {
          root: {
            border: "none",
            borderBottom: "5px solid white",
            // backgroundColor: "#F3F3F3",
          },
        },
        MuiTableRow: {
          root: {
            border: "1px solid #3A3A3A1A",
            opacity: 1,
            "&$hover:hover:nth-child(odd)": { backgroundColor: "#D6EAF8" },
            "&$hover:hover:nth-child(even)": { backgroundColor: "#E9F7EF" },
          },
        },
        MUIDataTableBodyCell: {
          root: {
             }
        
        }
      },
    });

  const handleCheckboxClick = (e) => {
    if (selectedFilters.indexOf(e.target.name) < 0) {
      setSelectedFilters([...selectedFilters, e.target.name]);
    } else if (selectedFilters.indexOf(e.target.name) > -1) {
      let existingFilter = Object.assign([], selectedFilters);
      existingFilter.splice(selectedFilters.indexOf(e.target.name), 1);
      setSelectedFilters(existingFilter);
    }
  };

  const clearAll = () => {
    setSelectedFilters([]);
    setAnchorEl(null);
    dispatch(clearFilterBenchmark());
  };

  return (
    <div
      style={{
        width: "fit-content",
        margin: "auto",
      }}
    >
      <div
        style={{ width: "100%", display: "flex", justifyContent: "flex-end" }}
      >
        <IconButton
          onClick={props.handleCloseModal}
          style={{ position: "absolute", padding: "10px" }}
        >
          <CloseIcon color="action" />
        </IconButton>
      </div>
      <MuiThemeProvider theme={getMuiTheme()}>
        <MUIDataTable
      
         id="benchmarkDataTable"
          options={options}
          data={data}
          columns={columns}
          title={"Select Benchmark Dataset and Metric"}
        ></MUIDataTable>
      </MuiThemeProvider>
      {popoverOpen && (
        <FilterBenchmark
          id={id}
          open={popoverOpen}
          anchorEl={anchorEl}
          handleClose={handleClose}
          filter={availableFilters}
          selectedFilter={selectedFilters}
          clearAll={clearAll}
          apply={apply}
          handleCheckboxClick={handleCheckboxClick}
        />
      )}
    </div>
  );
};

export default withStyles(DatasetStyle)(BenchmarkModal);
