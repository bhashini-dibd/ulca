import { withStyles, Typography, MuiThemeProvider, createMuiTheme, Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect,useState} from "react";
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
        const [page, setPage]           = useState(0);
        
        useEffect(() => {
                MySearchListApi()   
        }, []);

  
        const  MySearchListApi  = () =>{
                
                const userObj         = new MySearchReport()
                dispatch(APITransport(userObj));
        }

        const processTableClickedNextOrPrevious = (page, sortOrder) => {
               setPage(page)
                }
              

       

        const fetchHeaderButton= () => {
            return <Button color={"primary" } size="medium" variant="outlined" className={classes.ButtonRefresh}  onClick={() =>MySearchListApi() }><Cached className ={classes.iconStyle}/>Refresh</Button>
    }

    const renderAction = (rowData) =>{

        const status = rowData[4].toLowerCase();

        history.push({ 
                pathname: `/search-and-download-rec/${status}/${rowData[0]}`,
                pageInfo: page
               });
        // history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/${status}/${rowData[0]}`)
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
                        body            :       {
                                noMatch: "No records"
                        },
                        toolbar         :       {
                                search          : "Search",
                                viewColumns     : "View Column",
                        },
                        pagination      :       { rowsPerPage     : "Rows per page"},
                        options         :       { sortDirection   : "desc" },
                },
                customToolbar: fetchHeaderButton,
                displaySelectToolbar    :       false,
                fixedHeader             :       false,
                filterType              :       false,
                download                :       false,
                print                   :       false,
                filter                  :       false,
                page: page,
                viewColumns     : false,
                selectableRows          :       "none",
                onTableChange: (action, tableState) => {
                        switch (action) {
                          case "changePage":
                            processTableClickedNextOrPrevious(
                              tableState.page
                            );
                            break;
                          default:
                        }
                      },

                onRowClick                 : (rowData, rowMeta) =>rowData[3] &&  renderAction(rowData)
        };

        const { classes }               = props;

        return (
                <div >
                        <div className  = {classes.breadcrum}>
                                <BreadCrum links = {[UrlConfig.dataset]} activeLink = "My Searches" />
                        </div>

                                <MUIDataTable
                                        title   =       {`My Searches`}
                                        data    =       {detailedReport.responseData}
                                        columns =               {columns}
                                        options ={options}
                                />

                        
                </div>
        );
};

export default withStyles(DataSet)(MySearches);
