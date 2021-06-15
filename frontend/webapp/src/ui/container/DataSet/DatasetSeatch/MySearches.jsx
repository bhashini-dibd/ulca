import { withStyles, Typography, MuiThemeProvider, createMuiTheme, Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect} from "react";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MySearchReport from "../../../../redux/actions/api/DataSet/DatasetSearch/MySearches";
import { useDispatch, useSelector } from "react-redux";
import {Cached} from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import {  useHistory } from "react-router-dom";

const MySearches = (props) => {

        const detailedReport            =       useSelector((state) => state.mySearchReport);
        const dispatch                  = useDispatch();
        const history                   = useHistory();
        
        useEffect(() => {
                MySearchListApi()   
        }, []);

  
        const  MySearchListApi  = () =>{
                
                const userObj         = new MySearchReport()
                dispatch(APITransport(userObj));
        }

        const getMuiTheme = () => createMuiTheme({
                overrides: {
                        MuiTableCell: {
                                head    : {
                                        backgroundColor : "#c7c6c68a !important"
                                }
                        },
                        // MuiToolbar: {
                        //          root: { 
                        //                  display: "none" 
                        //                 } 
                        //         },
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
            return (
                    <div className={classes.headerButtons}>
                            {/* <Typography  variant="h5" >My Searches</Typography> */}
                            <Button color={"primary" } size="medium" variant="outlined" className={classes.ButtonRefresh}  onClick={() =>MySearchListApi() }><Cached className ={classes.iconStyle}/>Refresh</Button>
                     </div>
            )
    }

    const renderAction = (rowData) =>{

        const status = rowData[4].toLowerCase();
        history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/${status}/${rowData[0]}`)
    }

        
        const columns = [
                {
                        name    : "sr_no",
                        label   : "SR No",
                        options : {
                                filter  : false,
                                sort    : false,
                                display : "excluded"
                                
                        },
                },
                {
                        name    : "search_criteria",
                        label   : "Search Criteria",
                        options : {
                                filter  : false,
                                sort    : true,
                        },
                },
                {
                        name    : "searched_on",
                        label   : "Searched On",
                        options : {
                                filter  : false,
                                sort    : true,
                        },
                },{
                        name    : "count",
                        label   : "#Record",
                        options : {
                                filter  : false,
                                sort    : true,
                                
                  }  },
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
                filter                  :       false,
                selectableRows          :       "none",


                onRowClick                 : (rowData, rowMeta) => renderAction(rowData)
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
                                        title   =       {`My Searches`}
                                        data    =       {detailedReport.responseData}
                                        columns =               {columns}
                                        options ={options}
                                />
                        </MuiThemeProvider>
                        
                </div>
        );
};

export default withStyles(DataSet)(MySearches);
