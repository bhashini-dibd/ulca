import { withStyles, Typography,Link, MuiThemeProvider, createMuiTheme,Button } from "@material-ui/core";
import BreadCrum from '../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";
import DataSet from "../../styles/Dataset";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../redux/actions/api/DataSet/MyContribution";
import Dialog from "../../components/common/Dialog"
import {Cached, SaveAlt} from '@material-ui/icons';

const ContributionList = (props) => {

        const history                 = useHistory();
        const dispatch                = useDispatch();
        const myContributionReport    = useSelector( (state) => state.myContributionReport);

        useEffect(()                  => {
                myContributionReport.responseData.length == 0  && MyContributionListApi()
        }, []);
  
        const  MyContributionListApi  = () =>{
                const userObj         = new MyContributionList( "SAVE", "A_FBTTR-VWSge-1619075981554", "241006445d1546dbb5db836c498be6381606221196566");
                dispatch(APITransport(userObj));
        }

        const getMuiTheme = () => createMuiTheme({
                overrides: {
                MuiTableCell: {
                        head: {
                                backgroundColor : "#c7c6c68a !important",
                                cursor: 'default'
                        }
                },
                MuiPaper: {
                        root:{
                                boxShadow       : 'none !important',
                                borderRadius    : 0,
                                border          : "1px solid rgb(224 224 224)"
                        }
                },
                MUIDataTableToolbar:{ root: { display: "none" } },
                MuiTableRow:{root:{cursor: 'pointer'}}
                }
        });

        const HandleDelete = () =>{
                return <Dialog
                // message={message}
                // type={dialogType}
                // handleClose={this.handleDialogClose.bind(this)}
                // open
                // title={dialogTitle}
                // handleSubmit={this.handleDialogSubmit.bind(this)}
                // value={value}
                /> 
        }


        const fetchHeaderButton= () => {
                return (
                         <Button color={"primary" } size="medium" variant="outlined" className={classes.ButtonRefresh}  onClick={() => MyContributionListApi()}><Cached className ={classes.iconStyle}/>Refresh</Button>
                )
              }

        const renderStatus = (id,value) => {
                if(value === "Inprogress"){
                        return  <Link className = {classes.link} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/dataset-status/${id}}`)}}> In-progress </Link>
                }
                else{
                        return <span className = {classes.span}>Published </span>
                }
        }

        const renderAction = (id,value) => {
                if(value === "Inprogress"){}
                else{
                        return <div className= {classes.span}> <Link className= {classes.link} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/submit-dataset/upload`)}}> Update </Link><Link className= {classes.link} onClick={()=>{HandleDelete()}}> Delete </Link> </div> 
                }
        }

        const handleRowClick = ( rowMeta) => {
                if(rowMeta.colIndex!=6){
                        const value = myContributionReport.responseData[rowMeta.rowIndex].sr_no;
                        history.push(`${process.env.PUBLIC_URL}/dataset-status/${value}}`)
                }
            };

    
        const columns = [
                {
                name: "sr_no",
                label: "s id",
                options: {
                        filter  : false,
                        sort    : false,
                        display : "excluded",
                        },
                },
                {
                name    : "sr_no",
                label   : "SR No.",
                options : {
                                filter  : false,
                                sort    : false,
                        },
                },
                {
                name    : "Dataset",
                label   : "Dataset Name",
                options: {
                        filter  : false,
                        sort    : false,
                        },
                },
                {
                name    : "Submitted_on",
                label   : "Submitted On",
                options : {
                                filter  : false,
                                sort    : false,
                        
                        },
                },
                {
                name    : "Status",
                label   : "Status",
                options : {
                        filter  : false,
                        sort    : false,
                        display : "excluded",
                        },
                },
                {
                name: "Status",
                label: "status",
                options: {
                        filter  : true,
                        sort    : false,
                        empty   : true,
                        customBodyRender: (value, tableMeta, updateValue) => {
                                        if (tableMeta.rowData) {
                                                return <div>{renderStatus(tableMeta.rowData[0],tableMeta.rowData[4])}</div>;
                                        }
                                },
                        },
                },

                {
                name    : "Action",
                label   : "action",
                options: {
                                filter  : true,
                                sort    : false,
                                empty   : true,
                                customBodyRender: (value, tableMeta, updateValue) => {
                                        if (tableMeta.rowData) {
                                                return <div>{renderAction(tableMeta.rowData[0], tableMeta.rowData[4])}</div>;
                                        }
                        },
                },
                },
        ];

        const options = {
                textLabels: {
                        body: {},
                        toolbar: {
                                search          : "Search",
                                viewColumns     : "View Column",
                        },
                        pagination: {
                                rowsPerPage     : "Rows per page",
                        },
                        options: { sortDirection: "desc" },
                        },
                        onCellClick: (colData, cellMeta) => handleRowClick( cellMeta),
               
                filterType      : "checkbox",
                download        : false,
                print           : false,
                fixedHeader     : true,
                filter          : false,
                selectableRows  : "none",
        };

        const { classes } = props;
        
        return (
                <div className = {classes.divStyle}>
                        <div className = {classes.breadcrum}>
                                <BreadCrum links={["Dataset"]} activeLink="My Contribution" />
                        </div>

                        <div className={classes.headerButtons}>
                        <Typography variant="b" component="h2" >My Contribution</Typography>
                                {fetchHeaderButton()} 
                        </div>

                        {/* <div className={classes.title}>
                                
                        </div> */}

                        <MuiThemeProvider theme={getMuiTheme()}>  
                                <MUIDataTable
                                        title           =       {`My Contribution`}
                                        data            =       {myContributionReport.responseData}
                                        columns         =       {columns}
                                        options         =       {options}
                                />
                        </MuiThemeProvider>
                </div>
        );
};

export default withStyles(DataSet)(ContributionList);
