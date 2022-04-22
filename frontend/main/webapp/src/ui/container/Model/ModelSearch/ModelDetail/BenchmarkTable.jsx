import { withStyles } from "@material-ui/core";
import React,{ useEffect} from "react";
import { useHistory } from "react-router-dom";
import DataSet from "../../../../styles/Dataset";
import MUIDataTable from "mui-datatables";

const BenchmarkTable = (props) => {
  const history = useHistory();
  
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const convertDate = (date) => {
    return date
      .toLocaleString("en-IN", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
        hour12: true,
      })
      .toUpperCase();
  };
  const columns = [
    {
      name: "benchmarkDatasetId",
      label: "Benchmark ID",
      options: {
        filter: false,
        sort: true,
        display: "excluded",
      },
    },

    {
      name: "benchmarkDatasetName",
      label: "Benchmark Dataset",
      options: {
        filter: false,
        sort: true,
      },
    },

    {
      name: "metric",
      label: "Metric",
      options: {
        filter: false,
        sort: true,
      },
    },
    {
      name: "score",
      label: "Score",
      options: {
        filter: false,
        sort: true,
      },
    },
    {
      name: "createdOn",
      label: "Benchmark Run On",
      options: {
        filter: false,
        sort: true,
        customBodyRender: (rowData) => {
          const date = new Date(rowData);
          return <>{convertDate(date)}</>;
        },
        sortDirection: "desc",
      },
    },
  ];

  const options = {
    textLabels: {
      body: {
        noMatch: "No records  ",
      },
      toolbar: {
        search: "Search",
        viewColumns: "View Column",
      },
      pagination: {
        rowsPerPage: "Rows per page",
      },
    },
    onRowClick: (rowData) =>
      history.push(
        `${process.env.PUBLIC_URL}/model/benchmark-details/${rowData[0]}`
      ),
    // customToolbar: fetchHeaderButton,
    search: false,
    filter: false,
    displaySelectToolbar: false,
    fixedHeader: false,
    filterType: "checkbox",
    download: false,
    print: false,
    viewColumns: false,
    selectableRows: "none",
  };

  const { data } = props;

  return <MUIDataTable data={data} columns={columns} options={options} />;
};

export default withStyles(DataSet)(BenchmarkTable);
