import { Grid, withStyles, Tooltip, IconButton } from "@material-ui/core";

import React, { useEffect, useState } from "react";
import DataSet from "../../styles/Dataset";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../redux/actions/api/DataSet/MyContribution";
import { useDispatch, useSelector } from "react-redux";
import { CloudDownload } from "@material-ui/icons";


const ContributionList = (props) => {

  const myContributionReport = useSelector((state) => state.myContributionReport);
  

  const dispatch = useDispatch();
  useEffect(() => {
    const userObj = new MyContributionList(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    dispatch(APITransport(userObj));
  }, []);
  const AddJsonDownload = () => (
    <Tooltip disableFocusListener title="Download Json">
      <IconButton onClick={() => this.handleDownLoad()}>
        <CloudDownload />
      </IconButton>
    </Tooltip>
  );


  
  const columns = [
    {
      name: "s_id",
      label: "Sentence Id",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },
    {
      name: "src",
      label: "Source",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "mt",
      label: "Machine Translation",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "tgt",
      label: "Target",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "bleu_score",
      label: "Bleu Score",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "time_spent",
      label: "Time Spent",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "user_events",
      label: "User Events",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },

    {
      name: "Action",
      label:"action",
      options: {
        filter: true,
        sort: false,
        empty: true,
        customBodyRender: (value, tableMeta, updateValue) => {
          if (tableMeta.rowData) {
            return <div></div>;
          }
        },
      },
    },
  ];

  const options = {
    textLabels: {
      body: {},
      toolbar: {
        search: "Search",
        viewColumns: "View Column",
      },
      pagination: {
        rowsPerPage: "Rows per page",
      },
      options: { sortDirection: "desc" },
    },

    customToolbar: AddJsonDownload,
    filterType: "checkbox",
    download: false,
    print: false,
    fixedHeader: true,
    filter: false,
    selectableRows: "none",
  };

  const { classes } = props;
  return (
    <div style={{ margin: "0% 3% 3% 3%", paddingTop: "7vh" }}>
    <MUIDataTable
    title={`User Event Report`}
    data={myContributionReport.responseData}
    columns={columns}
    options={options}
  />
  </div>
  );
};

export default withStyles(DataSet)(ContributionList);
