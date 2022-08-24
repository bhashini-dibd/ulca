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
  },
  {
    name: "taskType",
    label: "Task Type",
  },
  {
    name: "status",
    label: "Status",
    options: {
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
    name: "callbackUrl",
    label: "Callback URL",
    options: {
      customBodyRender: (value, tableMeta, updateValue) => {
        if (tableMeta.rowData) {
          return (
            <Typography
              style={{textTransform: "none"}}
              variant="body2"
            >
              {tableMeta.rowData[3]}
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
    filter: false,

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
      options={options}
      columns={columns}
      data={statusCheckResult.filteredData}
    />
  );
};

export default StatusCheck;