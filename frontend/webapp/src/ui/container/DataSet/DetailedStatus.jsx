import { Grid, withStyles, Tooltip, IconButton,Link, MuiThemeProvider, createMuiTheme, Button } from "@material-ui/core";

import React, { useEffect, useState } from "react";
import DataSet from "../../styles/Dataset";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import DetailedDatasetStatus from "../../../redux/actions/api/DataSet/DetailedDataset";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";
import {Cached, SaveAlt} from '@material-ui/icons';

const DetailedStatus = (props) => {

  const history = useHistory();

  const detailedReport = useSelector(
    (state) => state.detailedReport
  );


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
        head: {
            backgroundColor: "#f2f2f4 !important"
        }
    },
    MuiToolbar: { root: { display: "none" } },
    MuiPaper: {
      root:{
      boxShadow: 'none !important',
      borderRadius: 0,
      border: "1px solid rgb(224 224 224)"
      }
      }
    },
     
});

const fetchHeaderButton= () => {
  

  return (
      <div>
           <Button color={"primary" } size="medium" variant="outlined"  onClick={() => this.handleLanguageChange("domain")}><Cached className ={classes.iconStyle}/>Refresh</Button>
       
           <Button color={"primary" } size="medium" variant="outlined" className={classes.buttonStyle} onClick={() => this.handleLanguageChange("domain")}><SaveAlt className ={classes.iconStyle}/>Error Logs</Button>
       
      </div>
  )
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
    displaySelectToolbar : false,
    fixedHeader :false,
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
      <div className={classes.headerButtons}>
      {fetchHeaderButton()} 
                        </div>
      <MuiThemeProvider theme={getMuiTheme()}>  
      <MUIDataTable
        title={`My Contribution`}
        data={detailedReport.responseData}
        columns={columns}
        options={options}
      />
      </MuiThemeProvider>
      <div className={classes.footerButtons}>
      <Button color={"primary" } size="medium" variant="outlined" className={classes.backButton} onClick={() => this.handleLanguageChange("domain")}>Abort Process</Button>
         
                        </div>
    </div>
  );
};

export default withStyles(DataSet)(DetailedStatus);
