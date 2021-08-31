import {
  createMuiTheme,
  MuiThemeProvider,
  withStyles,
} from "@material-ui/core/styles";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";
import DatasetStyle from "../../../styles/Dataset";
import FilterListIcon from "@material-ui/icons/FilterList";
import SearchIcon from "@material-ui/icons/Search";

import {
  IconButton,
  Button,
  Divider,
  Grid,
  InputBase,
  TableCell,
  TableRow,
} from "@material-ui/core";
import { useState } from "react";
import MUIDataTable from "mui-datatables";
import { useDispatch, useSelector } from "react-redux";
import getBenchmarkMetric from "../../../../redux/actions/api/Model/ModelView/BenchmarkMetric";
import { useEffect } from "react";
import RunBenchmarkAPI from "../../../../redux/actions/api/Model/ModelView/RunBenchmark";
import FilterList from "./FilterList";
import FilterBenchmark from "./FilterBenchmark";
import APITransport from "../../../../redux/actions/apitransport/apitransport";

const BenchmarkModal = (props) => {
  const { classes, type, domain } = props;
  const [index, setIndex] = useState([]);
  const [subIndex, setSubIndex] = useState([]);
  const dispatch = useDispatch();
  const data = useSelector((state) => state.getBenchMarkDetails.result);
  const [anchorEl, setAnchorEl] = useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;

  useEffect(() => {
    const apiObj = new RunBenchmarkAPI(type, domain);
    dispatch(APITransport(apiObj));
  }, []);

  const handleClose = () => {
    setAnchorEl(null);
  };
  const clearAll = (data) => {
    console.log("clearAll clicked");
  };
  const apply = (data) => {
    handleClose();
    console.log("apply called");
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const fetchModalFooter = () => {
    return (
      <>
        <Divider style={{ margin: "5px" }} />
        <Button
          style={{ float: "right", marginTop: "5px", borderRadius: "22px" }}
          variant="outlined"
          disabled
        >
          Submit
        </Button>
      </>
    );
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
              onChange={(e) => props.handleSearch(e)}
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
          <Button
            variant="outlined"
            size="medium"
            className={classes.filterBtn}
            onClick={handleShowFilter}
          >
            <FilterListIcon className={classes.iconStyle} />
            Filter
          </Button>
        </Grid>
      </Grid>
    );
  };

  const renderSelectButton = (rowIndex, setFnc, index) => {
    return (
      <Button
        variant="outlined"
        size="small"
        style={{
          backgroundColor: index.indexOf(rowIndex) > -1 ? "#2A61AD" : "white",
        }}
        className={classes.filterBtn}
        onClick={() => {
          dispatch(getBenchmarkMetric());
          let exisitingIndex = Object.assign([], index);
          if (index.indexOf(rowIndex) > -1) {
            exisitingIndex.splice(index.indexOf(rowIndex), 1);
            setFnc(exisitingIndex);
          } else if (index.indexOf(rowIndex) === -1) {
            setFnc([...index, rowIndex]);
          }
        }}
      >
        {index.indexOf(rowIndex) > -1 ? (
          <CheckIcon style={{ color: "#FFFFFF" }} />
        ) : (
          "Select"
        )}
      </Button>
    );
  };

  const columns = [
    {
      name: "datasetName",
      label: "Dataset Name",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "domain",
      label: "Domain",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "description",
      label: "Description",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "Action",
      options: {
        filter: false,
        sort: false,
        customBodyRender: (value, tableMeta, updateValue) => {
          return renderSelectButton(tableMeta.rowIndex, setIndex, index);
          // return (
          //   <Button
          //     variant="outlined"
          //     size="small"
          //     className={classes.filterBtn}
          //     onClick={() => dispatch(getBenchmarkMetric())}
          //   >
          //     Select
          //   </Button>
          // );
        },
      },
    },
  ];

  const options = {
    customToolbar: fetchModalToolBar,
    customFooter: fetchModalFooter,
    print: false,
    viewColumns: false,
    selectableRows: false,
    download: false,
    search: false,
    filter: false,
    expandableRows: true,
    rowsExpanded: index,
    customRowRenderer: (data, dataIndex, rowIndex) => {},
    renderExpandableRow: (rowData, rowMeta) => {
      const rows = data[rowMeta.rowIndex].metric;
      const colSpan = rowData.length + 1;
      return (
        <>
          <TableRow>
            <TableCell />
            <TableCell align="center">Metric</TableCell>
            {/* <TableCell align="left">Description</TableCell> */}
            <TableCell align="left">Action</TableCell>
            <TableCell />
          </TableRow>
          {rows.map((row, i) => {
            return (
              <TableRow>
                <TableCell />
                <TableCell align="center">{row}</TableCell>
                {/* <TableCell align="left">{row.description}</TableCell> */}
                <TableCell align="left">
                  {/* {renderSelectButton(i, setSubIndex, subIndex)} */}
                  <Button
                    variant="outlined"
                    size="small"
                    style={{ backgroundColor: "white" }}
                    className={classes.filterBtn}
                    // onClick={() => dispatch(getBenchmarkMetric())}
                  >
                    Select
                  </Button>
                </TableCell>
                <TableCell align="right" />
              </TableRow>
            );
          })}
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
        MUIDataTable: {
          paper: {
            padding: "21px",
            width: "64.375rem",
          },
          responsiveBase: {
            minHeight: "35rem",
            maxHeight: "35rem",
          },
        },
        MUIDataTableSelectCell: {
          icon: {
            display: "none",
          },
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
        MuiToolbar: {
          root: {
            marginTop: "18px",
          },
          gutters: {
            padding: "0",
            "@media (min-width:600px)": {
              paddingLeft: "0",
              paddingRight: "0",
            },
          },
        },
        MuiTableRow: {
          root: {
            border: "1px solid #00000029",
          },
        },
      },
    });
  return (
    <div
      style={{
        width: "fit-content",
        margin: "auto",
        marginTop: "120px",
      }}
    >
      <div
        style={{ width: "100%", display: "flex", justifyContent: "flex-end" }}
      >
        <IconButton
          onClick={props.handleCloseModal}
          style={{ position: "absolute", padding: "20px" }}
        >
          <CloseIcon color="action" />
        </IconButton>
      </div>
      <MuiThemeProvider theme={getMuiTheme()}>
        <MUIDataTable
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
          filter={{ datasetType: ["Legal", "News"] }}
          selectedFilter={{ datasetType: [{ type: "asr", status: true }] }}
          clearAll={clearAll}
          apply={apply}
        />
      )}
    </div>
  );
};

export default withStyles(DatasetStyle)(BenchmarkModal);
