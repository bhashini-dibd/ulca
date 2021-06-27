import { withStyles,Link, MuiThemeProvider, createMuiTheme,Button } from "@material-ui/core";
import BreadCrum from '../../../components/common/Breadcrum';
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";
import DataSet from "../../../styles/Dataset";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import MUIDataTable from "mui-datatables";
import MyContributionList from "../../../../redux/actions/api/DataSet/DatasetView/MyContribution";
import ClearReport from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import Dialog from "../../../components/common/Dialog"
import {Cached, DeleteOutline, VerticalAlignTop} from '@material-ui/icons';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useParams } from "react-router";

const ContributionList = (props) => {

        const history                 = useHistory();
        const dispatch                = useDispatch(ClearReport);
        const myContributionReport    = useSelector( (state) => state.myContributionReport);
        const [open, setOpen]         = useState(false)
        const [message, setMessage]   = useState("Do you want to delete")
        const [title, setTitle]       = useState("Delete")
        const {added}                 = useParams()
        const data                    = myContributionReport.responseData

        
        useEffect(()                  => {
                (myContributionReport.responseData.length === 0 || myContributionReport.refreshStatus || added) && MyContributionListApi()
        }, []);
  
        const  MyContributionListApi  = () =>{
                dispatch(ClearReport());
                const userObj         = new MyContributionList( "SAVE", "A_FBTTR-VWSge-1619075981554", "241006445d1546dbb5db836c498be6381606221196566");
                dispatch(APITransport(userObj));
        }

        const fetchHeaderButton= () => {
                return <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh}  onClick={() => MyContributionListApi()}><Cached className ={classes.iconStyle}/>Refresh</Button>
                        
        }

        const handleSetValues = (name) => {
                setTitle        (`Delete ${name}  `)
                setMessage      (`Do you want to delete ${name} ? `)
                setOpen         (true)
        }

        const renderStatus = (id,name,value) => {
                if(value === "In-Progress"){
                        return  <Link className = {classes.link} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/dataset-status/${value}/${name}/${id}`)}}> In-Progress </Link>
                }
                else{
                        return <span
                        >{value} </span>
                }
        }

        const renderAction = (name,value) => {
                if(value === "In-Progress"){}
                else{
                        return (<div className = {classes.action}> 
                                        <div className= {classes.link}>
                                        <Link className= {classes.link} color={"primary"} onClick={()=>{history.push(`${process.env.PUBLIC_URL}/submit-dataset/upload`)}}> Update <div ><VerticalAlignTop style={{"height":"0.8em"}} onClick={()=>{handleSetValues(name)}}/>  </div></Link>
                                                                              </div>
                                        <div className = {classes.span
                                        }>
                                        <DeleteOutline onClick={()=>{handleSetValues(name)}}/> 
                                        </div>
                                </div>)
                }
        }

        const handleRowClick = ( rowData) => {

                history.push(`${process.env.PUBLIC_URL}/dataset-status/${rowData[4]}/${rowData[2]}/${rowData[0]}`)

                // if(rowMeta.colIndex !== 6){
                //         const value = data[rowMeta.rowIndex].submitRefNumber;
                //         const status = data[rowMeta.rowIndex].status.toLowerCase();
                //         const name = data[rowMeta.rowIndex].datasetName;
                //         history.push(`${process.env.PUBLIC_URL}/dataset-status/${status}/${name}/${value}`)
                // }
        };

        const handleDialogSubmit = () =>{

        }


    
        const columns = [
                {
                name: "submitRefNumber",
                label: "s id",
                options: {
                        filter  : false,
                        sort    : false,
                        display : "excluded",
                        },
                },
                {
                name    : "submitRefNumber",
                label   : "SR No.",
                options : {
                                filter  : false,
                                sort    : false,
                                display : "excluded",
                        },
                },
                {
                name    : "datasetName",
                label   : "Dataset Name",
                options: {
                        filter  : false,
                        sort    : true,
                        },
                },
                {
                name    : "submittedOn",
                label   : "Submitted On",
                options : {
                                filter  : false,
                                sort    : true,
                        
                        },
                },
                {
                name    : "status",
                label   : "Status",
                options : {
                        filter  : true,
                        sort    : true,
                       
                        },
                },
                // {
                // name: "Status",
                // label: "Status",
                // options: {
                //         filter  : true,
                //         sort    : false,
                //         empty   : true,
                //         customBodyRender: (value, tableMeta, updateValue) => {
                //                         if (tableMeta.rowData) {
                //                                 return <div>{renderStatus(tableMeta.rowData[0],tableMeta.rowData[2],tableMeta.rowData[4])}</div>;
                //                         }
                //                 },
                //         },
                // },

                // {
                // name    : "Action",
                // label   : "Action",
                // options: {
                //                 filter  : true,
                //                 sort    : false,
                //                 empty   : true,
                //                 customBodyRender: (value, tableMeta, updateValue) => {
                //                         if (tableMeta.rowData) {
                //                                 return <div>{renderAction(tableMeta.rowData[2], tableMeta.rowData[4])}</div>;
                //                         }
                //         },
                // },
                // },
        ];
      

        const options = {

                
                textLabels: {
                        body            : {

                                noMatch: "No records"
                        },
                        toolbar         : {
                                search          : "Search",
                                viewColumns     : "View Column",
                        },
                        pagination      : {
                                rowsPerPage     : "Rows per page",
                        },
                        options         : { sortDirection: "desc" },
                        },
                        onRowClick: rowData => handleRowClick(rowData),
                        // onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
                        customToolbar: fetchHeaderButton,
                filterType      : "checkbox",
                download        : false,
                print           : false,
                fixedHeader     : false,
                filter          : true,
                viewColumns     : false,
                selectableRows  : "none",
        };

        const { classes } = props;
        return (
                <div>
                        <div className = {classes.breadcrum}>
                                <BreadCrum links={[UrlConfig.dataset]} activeLink="My Contribution" />
                        </div>
                       
                        {/* <div className={classes.title}>
                                
                        </div> */}

                         
                                <MUIDataTable
                                        title           =       {`My Contribution`}
                                        data            =       {data}
                                        columns         =       {columns}
                                        options         =       {options}
                                />
                        
                        {open && <Dialog
                                message         =       {message}
                                handleClose     ={() => {setOpen(false)}}
                                open 
                                title           =       {title}
                                handleSubmit    ={() =>        {handleDialogSubmit()}}
                        /> }
                </div>
        );
};

export default withStyles(DataSet)(ContributionList);
