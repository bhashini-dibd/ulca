import { Grid, withStyles, Tooltip, IconButton,Link, MuiThemeProvider, createMuiTheme } from "@material-ui/core";

import React, { useEffect, useState } from "react";
import DataSet from "../../styles/Dataset";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../redux/actions/api/DataSet/MyContribution";
import { useDispatch, useSelector } from "react-redux";
import { CloudDownload } from "@material-ui/icons";
import OpenInNewIcon from '@material-ui/icons/OpenInNew';



const ContributionList = (props) => {
  const myContributionReport = useSelector(
    (state) => state.myContributionReport
  );

  const dispatch = useDispatch();
  useEffect(() => {
    const userObj = new MyContributionList(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    dispatch(APITransport(userObj));
  }, []);
  

  const getMuiTheme = () => createMuiTheme({
    overrides: {
      MuiTableCell: {
        head: {
            backgroundColor: "#c7c6c6 !important"
        }
    }
    }
});

  const renderStatus = (value) => {
    if(value === "Inprogress"){
     return  <Link className= {classes.link}> In-progress </Link>
    }
    else{
      return <span className= {classes.span}>Published </span>
    }
  }

  const renderAction = (value) => {
    if(value === "Inprogress"){
     
    }
    else{
      return <div className= {classes.span}> <Link className= {classes.link}> Update </Link><Link className= {classes.link}> Delete </Link> </div> 
    }
  }

    
  const columns = [
    {
      name: "sr_no",
      label: "s id",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },
    {
      name: "sr_no",
      label: "Sr No.",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "Dataset",
      label: "Dataset Name",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "Submitted_on",
      label: "Submitted On",
      options: {
        filter: false,
        sort: false,
        
      },
    },{
      name: "Status",
      label: "Status",
      options: {
        filter: false,
        sort: false,
        display: "excluded",
      },
    },
    {
      name: "Status",
      label: "status",
      options: {
        filter: true,
        sort: false,
        empty: true,
        customBodyRender: (value, tableMeta, updateValue) => {
          if (tableMeta.rowData) {
            return <div>{renderStatus(tableMeta.rowData[4])}</div>;
          }
        },
      },
    },

    {
      name: "Action",
      label: "action",
      options: {
        filter: true,
        sort: false,
        empty: true,
        customBodyRender: (value, tableMeta, updateValue) => {
          if (tableMeta.rowData) {
            return <div>{renderAction(tableMeta.rowData[4])}</div>;
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

    
    filterType: "checkbox",
    download: false,
    print: false,
    fixedHeader: true,
    filter: false,
    selectableRows: "none",
  };

  const { classes } = props;
  return (
    <div className={classes.divStyle}>
      <MuiThemeProvider theme={getMuiTheme()}>  
      <MUIDataTable
        title={`User Event Report`}
        data={myContributionReport.responseData}
        columns={columns}
        options={options}
      />
      </MuiThemeProvider>
    </div>
  );
};

export default withStyles(DataSet)(ContributionList);
