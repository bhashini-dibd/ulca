import { Grid, withStyles, Tooltip, IconButton,Link, MuiThemeProvider, createMuiTheme } from "@material-ui/core";

import React, { useEffect, useState } from "react";
import DataSet from "../../styles/Dataset";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import DetailedDatasetStatus from "../../../redux/actions/api/DataSet/DetailedDataset";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";


const DetailedStatus = (props) => {

  const history = useHistory();

  const detailedReport = useSelector(
    (state) => state.detailedReport
  );

  console.log("-------------",detailedReport)

  const dispatch = useDispatch();
  useEffect(() => {
    const userObj = new DetailedDatasetStatus(
      "SAVE",
      "A_FBTTR-VWSge-1619075981554",
      "241006445d1546dbb5db836c498be6381606221196566"
    );
    detailedReport.responseData.length==0  && dispatch(APITransport(userObj));
  }, []);
  

  const getMuiTheme = () => createMuiTheme({
    overrides: {
      MuiTableCell: {
        // head: {
        //     backgroundColor: "#c7c6c6 !important"
        // }
    }
    }
});

  const renderStatus = (id,value) => {
    if(value === "Inprogress"){
     return  <Link className= {classes.link} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/dataset-status/${id}}`)}}> In-progress </Link>
    }
    else{
      return <span className= {classes.span}>Published </span>
    }
  }

  const renderAction = (id,value) => {
    if(value === "Inprogress"){
     
    }
    else{
      return <div className= {classes.span}> <Link className= {classes.link} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/submit-dataset/upload`)}}> Update </Link><Link className= {classes.link}> Delete </Link> </div> 
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
      name: "stage",
      label: "Stage",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "status",
      label: "Status",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "Record Count",
      label: "Record Count",
      options: {
        filter: false,
        sort: false,
        
      },
    }
    
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
        title={`My Contribution`}
        data={detailedReport.responseData}
        columns={columns}
        options={options}
      />
      </MuiThemeProvider>
    </div>
  );
};

export default withStyles(DataSet)(DetailedStatus);
