import {
  createMuiTheme,
  MuiThemeProvider,
  withStyles,
} from "@material-ui/core/styles";
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
  Table,
  TableContainer,
  Paper,
} from "@material-ui/core";
import { useState } from "react";
import MUIDataTable, { TableHead, TableHeadCell } from "mui-datatables";

const BenchmarkModal = (props) => {
  const { classes } = props;
  const [index, setIndex] = useState([]);
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
          >
            <FilterListIcon className={classes.iconStyle} />
            Filter
          </Button>
        </Grid>
      </Grid>
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
          console.log(tableMeta);
          return (
            <Button
              variant="outlined"
              size="small"
              className={classes.filterBtn}
              onClick={() => {
                const existingIndex = [...index];
                if (index.indexOf(tableMeta.rowIndex) > -1) {
                  let slicedIndex = existingIndex.map((data, i) => {
                    if (i !== tableMeta.rowIndex) {
                      return data;
                    }
                  });
                  setIndex(slicedIndex);
                } else {
                  setIndex([...existingIndex, tableMeta.rowIndex]);
                }
              }}
            >
              Select
            </Button>
          );
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
    renderExpandableRow: (rowData, rowMeta) => {
      const colSpan = rowData.length + 1;
      return (
        <TableRow style={{ border: "1px solid #00000029", width: "98%" }}>
          <TableCell />
          <TableCell align="center">Metrics</TableCell>
          <TableCell align="left">Domain</TableCell>
          <TableCell align="left">Action</TableCell>
        </TableRow>
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
  const data = [
    {
      datasetName: "1",
      domain: "Legal",
      description: "test",
    },
    {
      datasetName: "1",
      domain: "Legal",
      description: "test",
    },
    {
      datasetName: "1",
      domain: "Legal",
      description: "test",
    },
    {
      datasetName: "1",
      domain: "Legal",
      description: "test",
    },
  ];

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
    </div>
  );
};

export default withStyles(DatasetStyle)(BenchmarkModal);
