import DataTable from "../../../components/common/DataTable";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetReportData from "../../../../redux/actions/api/DataSet/DatasetMetrics/GetReportData";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValue from "../../../../redux/actions/api/DataSet/DatasetSearch/GetSearchedValues";
import { withStyles, Grid, Button } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import SelectColumn from "../../../components/Datasets&Model/SelectColumn";
import ViewColumnIcon from "@material-ui/icons/ViewColumn";

const selectColumnData = [
  {
    name: "datasetType",
    label: "Dataset Type",
    options: { viewColumns: false },
    disable: true,
    customHidden: false,
    checked: true,
  },
  {
    name: "sourceLanguage",
    label: "Source Language",
    disable: false,
    customHidden: false,
    checked: true,
  },
  {
    name: "targetLanguage",
    label: "Target Language",
    disable: false,
    customHidden: false,
    checked: true,
  },
  {
    name: "domain",
    label: "Domain",
    disable: false,
    customHidden: false,
    checked: true,
  },
  {
    name: "collectionMethod",
    label: "Collection Method",
    disable: false,
    customHidden: false,
    checked: true,
  },
  {
    name: "submitterName",
    label: "Submitter",
    disable: false,
    customHidden: false,
    checked: true,
  },
  {
    name: "count",
    label: "Count",
    options: { viewColumns: false },
    disable: true,
    customHidden: false,
    checked: true,
  },
];

const DatasetMetrics = (props) => {
  const { classes } = props;
  const dispatch = useDispatch();

  const [anchorEl, setAnchorEl] = useState(null);
  const [columns, setColumns] = useState([]);
  const [totalColumns, setTotalColumns] = useState(selectColumnData);
  const [tableData, setTableData] = useState([]);
  const [searchKey, setSearchKey] = useState("");
  const openSelector = Boolean(anchorEl);

  const datasetMetrics = useSelector((state) => state.datasetMetrics.result);

  useEffect(() => {
    const target = [];
    datasetMetrics.forEach((element) => {
      if (element.targetLanguage == null || element.targetLanguage == "") {
        element.targetLanguage = "-";
      }
      target.push(element);
    });

    setTableData(target);
  }, [datasetMetrics]);

  const options = {
    download: true,
    viewColumns: false,
    print: false,
    search: false,
    selectableRows: false,
    filter: false,

    downloadOptions: {
      filterOptions: {
        useDisplayedColumnsOnly: true,
      },
    },
    sortOrder: {
      name: "datasetType",
      direction: "asc",
    },
  };

  useEffect(() => {
    const obj = new GetReportData();
    dispatch(APITransport(obj));

    setColumns(selectColumnData);
  }, []);

  const handleSearch = (value) => {
    setSearchKey(value);
    prepareDataforTable(columns, datasetMetrics, value);
    dispatch(getSearchedValue(value));
  };

  const handleShowSelectColumn = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseSelectColumn = () => {
    setAnchorEl(null);
  };

  const handleColumnSelection = (e) => {
    let tempTotalColumn = [...totalColumns];

    tempTotalColumn.forEach((element) => {
      if (element.name === e.target.name) {
        element.customHidden = !element.customHidden;
      }
    });

    selectColumnData.forEach((element) => {
      if (element.name === e.target.name) {
        element.checked = !element.checked;
      }
    });

    const filteredArr = tempTotalColumn.filter((col) => !col.customHidden);
    setTotalColumns(tempTotalColumn);
    setColumns(filteredArr);
    prepareDataforTable(filteredArr, datasetMetrics, searchKey);
  };

  const prepareDataforTable = (column, data, key) => {
    const ActiveColumns = column
      .filter((item) => item.name !== "count")
      .map((col) => col.name);
    const OutputData = [];

    const compareObject = (oldObject, newObject) =>
      Object.keys(oldObject).every(
        (key) =>
          oldObject[key] === newObject[key] || !ActiveColumns.includes(key)
      );

    data.forEach((row) => {
      let isSame = false;
      OutputData.forEach((data) => {
        if (compareObject(data, row)) {
          data.count = data.count + row.count;
          isSame = true;
        }
      });
      if (!isSame) {
        OutputData.push({...row});
      }
    });

    const res = OutputData.filter((e) => {
      return (
        e.collectionMethod.toLowerCase().match(key.trim().toLowerCase()) ||
        e.datasetType.toLowerCase().match(key.trim().toLowerCase()) ||
        e.domain.toLowerCase().match(key.trim().toLowerCase()) ||
        e.sourceLanguage.toLowerCase().match(key.trim().toLowerCase()) ||
        e.submitterName.toLowerCase().match(key.trim().toLowerCase()) ||
        e.targetLanguage.toLowerCase().match(key.trim().toLowerCase())
      );
    });

    setTableData(res);
  };
  return (
    <div>
      <div className={classes.metricsParent}>
        <div>
          <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
        </div>
        <div>
          <Button
            className={classes.metricsbtn}
            onClick={(e) => handleShowSelectColumn(e)}
          >
            <ViewColumnIcon />
          </Button>
        </div>
      </div>

      <DataTable
        title="Dataset Metrics"
        options={options}
        columns={columns}
        // filterOptions={filterOptions}
        data={tableData}
      />

      {openSelector ? (
        <SelectColumn
          anchorEl={anchorEl}
          open={openSelector}
          handleClose={handleCloseSelectColumn}
          columns={selectColumnData}
          handleColumnSelection={handleColumnSelection}
        />
      ) : null}
    </div>
  );
};

export default withStyles(DataSet)(DatasetMetrics);
