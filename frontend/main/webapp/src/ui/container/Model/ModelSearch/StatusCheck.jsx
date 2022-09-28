import { Typography } from "@material-ui/core";
import MUIDataTable from "mui-datatables";
import React from "react";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import ModelStatusCheck from "../../../../redux/actions/api/Model/ModelView/ModelStatusCheck";
import APITransport from "../../../../redux/actions/apitransport/apitransport";

const columns = [
  {
    name: "modelName",
    label: "Model",
    options: {
      filter: false,
      sort: false,
    }
  },
  {
    name: "taskType",
    label: "Task Type",
    options: {
      sort: true,
      filter: true,
    }
  },
  {
    name: "status",
    label: "Status",
    options: {
      sort: true,
      filter: true,
      customBodyRender: (value, tableMeta, updateValue) => {
        if (tableMeta.rowData) {
          return (
            <Typography
              style={
                tableMeta.rowData[2] === "unavailable" ? { color: "red" } : null
              }
              variant="body2"
            >
              {tableMeta.rowData[2]}
            </Typography>
          );
        }
      },
    },
  },
  {
    name: "lastStatusUpdate",
    label: "Last Updated Timestamp",
    options: {
      sort: false,
      filter: false,
    }
  },
  {
    name: "callbackUrl",
    label: "Callback URL",
    options: {
      sort: false,
      filter: false,
      customBodyRender: (value, tableMeta, updateValue) => {
        if (tableMeta.rowData) {
          return (
            <Typography
              style={{textTransform: "none"}}
              variant="body2"
            >
              {tableMeta.rowData[4]}
            </Typography>
          );
        }
      },
    },
  },
];

const StatusCheck = () => {
  const dispatch = useDispatch();
  const statusCheckResult = useSelector(
    (state) => state.getModelHealthCheckStatus
    );

  useEffect(() => {
    const apiObj = new ModelStatusCheck("");
    dispatch(APITransport(apiObj));
  }, []);

  const options = {
    download: true,
    viewColumns: true,
    print: false,
    search: true,
    selectableRows: false,
    filter: true,

    downloadOptions: {
      filterOptions: {
        useDisplayedColumnsOnly: true,
      },
    },

    sortOrder: {
      name: "model",
      direction: "asc",
    },
  };

  return (
    <MUIDataTable
      title={"Models Health Check"}
      options={options}
      columns={columns}
      data={statusCheckResult.filteredData}
    />
  );
};

export default StatusCheck;