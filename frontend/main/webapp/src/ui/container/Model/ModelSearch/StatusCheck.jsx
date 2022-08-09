import MUIDataTable from "mui-datatables";
import React from "react";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import ModelStatusCheck from "../../../../redux/actions/api/Model/ModelView/ModelStatusCheck";
import APITransport from "../../../../redux/actions/apitransport/apitransport";

const columns = [
  {
    name: "model",
    label: "Model",
  },
  {
    name: "taskType",
    label: "Task Type",
  },
  {
    name: "status",
    label: "Status",
  },
  {
    name: "callbackURL",
    label: "Callback URL",
  },
];

const StatusCheck = () => {
  const dispatch = useDispatch();

  useEffect(() => {
    // const apiObj = new ModelStatusCheck(type);
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

  return <MUIDataTable options={options} columns={columns} data={[]} />;
};

export default StatusCheck;
