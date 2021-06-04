import { Grid, withStyles, Tooltip, IconButton,Typography, MuiThemeProvider, createMuiTheme, Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MySearchReport from "../../../../redux/actions/api/DataSet/DatasetSearch/MySearches";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";
import {Cached, SaveAlt} from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';

const MySearches = (props) => {

        const history                 =       useHistory();
        const detailedReport          =       useSelector((state) => state.mySearchReport);
        const dispatch = useDispatch();
        useEffect(() => {
                
                detailedReport.responseData.length == 0  && DetailedDataSetStatusApi()
        }, []);

        const DetailedDataSetStatusApi  = () =>{
                const userObj           = new MySearchReport(  "SAVE", "A_FBTTR-VWSge-1619075981554","241006445d1546dbb5db836c498be6381606221196566");
                dispatch(APITransport(userObj));
        }
        

        const getMuiTheme = () => createMuiTheme({
                overrides: {
                        MuiTableCell: {
                                head    : {
                                        backgroundColor : "#c7c6c68a !important"
                                }
                        },
                        MuiToolbar: {
                                 root: { 
                                         display: "none" 
                                        } 
                                },
                        MuiPaper: {
                                root:{
                                        boxShadow       : 'none !important',
                                        borderRadius    : 0,
                                        border          : "1px solid rgb(224 224 224)"
                                }
                        }
                },
        
        });

        const fetchHeaderButton= () => {
                return <div className={classes.reqNoTypo}> <Typography variant="b" component="h2" >My Searches</Typography> </div> 
        }

        
        const columns = [
                {
                        name    : "sr_no",
                        label   : "SR No",
                        options : {
                                filter  : false,
                                sort    : false,
                                
                        },
                },
                {
                        name    : "search_criteria",
                        label   : "Search Criteria",
                        options : {
                                filter  : false,
                                sort    : false,
                        },
                },
                {
                        name    : "searched_on",
                        label   : "Searched On",
                        options : {
                                filter  : false,
                                sort    : false,
                        },
                },
                {
                        name    : "status",
                        label   : "Status",
                        options : {
                                filter  : false,
                                sort    : false,
                                
                        },
                }
        
        ];

        const options = {
                textLabels              :       {
                        body            :       {},
                        toolbar         :       {
                                search          : "Search",
                                viewColumns     : "View Column",
                        },
                        pagination      :       { rowsPerPage     : "Rows per page"},
                        options         :       { sortDirection   : "desc" },
                },
                displaySelectToolbar    :       false,
                fixedHeader             :       false,
                filterType              :       "checkbox",
                download                :       false,
                print                   :       false,
                fixedHeader             :       true,
                filter                  :       false,
                selectableRows          :       "none",
        };

        const { classes }               = props;
        return (
                <div className  =       {classes.divStyle}>
                        <div className  = {classes.breadcrum}>
                                <BreadCrum links = {[UrlConfig.dataset]} activeLink = "My Searches" />
                        </div>
                        {fetchHeaderButton()} 
                        <MuiThemeProvider theme = {getMuiTheme()}>  
                                <MUIDataTable
                                        title   =       {`My Contribution`}
                                        data    =       {detailedReport.responseData}
                                        columns =               {columns}
                                        options ={options}
                                />
                        </MuiThemeProvider>
                        
                </div>
        );
};

export default withStyles(DataSet)(MySearches);
