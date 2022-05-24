import React, { useState } from 'react';
import SelectionBenchmark from "../../../styles/SelectionBenchmark";
import { withStyles, Button, Divider, Grid, Backdrop, Popover, makeStyles, Checkbox, FormControlLabel, Typography } from "@material-ui/core";
import { MuiThemeProvider,createTheme } from '@material-ui/core/styles';
import createMuiTheme from "../../../styles/Datatable";
import MUIDataTable from "mui-datatables";
import CheckIcon from '@material-ui/icons/Check';
import FilterListIcon from '@material-ui/icons/FilterList';
import CloseIcon from '@material-ui/icons/Close';

const FilterList = (props) => {
    const { classes } = props;
    const { filter, selectedFilter, handleClose, apply,data } = props
    const [selectedValue, setSelectedValue] = useState([])
    const [selectedMetric, setSelectedMetric] = useState([])
    const [action, setAction] = useState(false)
    const [page, setPage] = useState(1)
    const [anchorEl, setAnchorEl] = React.useState(null);
        const popoverOpen = Boolean(anchorEl);

    const getMuiTheme = () => createTheme({
        overrides: {
                MuiToolbar: {
                        root: { 
                                display: "none" 
                               } 
                       },
                MuiButton: {
                        root: {
                          minWidth: "25",
                          borderRadius: 'none'
                        },
                        label: {
                  
                          textTransform: "none",
                          fontFamily: '"Roboto", "Segoe UI"',
                          fontSize: "16px",
                          //fontWeight: "500",
                          //lineHeight: "1.14",
                          letterSpacing: "0.16px",
                          textAlign: "center",
                          height: "19px",
                        },
                        sizeLarge: {
                          height: "40px",
                          borderRadius: "20px",
                  
                        },
                        sizeSmall: {
                          height: "30px",
                          borderRadius: "20px",
                  
                        },
                  
                  
                      },
                
                MuiTableCell: {
                        head    : {
                                
                                backgroundColor : "#c7c6c68a !important",
                                fontWeight      :"bold"
                        }
                },
                MUIDataTableBodyCell:{root : {textTransform: "capitalize"}},
               
                        MuiTableHead:{
                            root:{
                                background:"#F3F3F3"
                            }
                        },
                        
                MuiPaper: {
                        root:{
                                boxShadow       : 'none !important',
                                borderRadius    : 0,
                                border          : 0,
                                height:"560px",
                                minWidth:"900px",
                                margin:"10px"
                        }
                }
        },
        
        MUIDataTableBodyCell: {
            root: { padding: ".5rem .5rem .5rem .8rem", textTransform: "capitalize" },
          },
          

});

const handleShowFilter = (event) => {
        setAnchorEl(event.currentTarget);
}

    
const fetchHeaderButton= () => {
        return (
                <div className={classes.headerButtons}>
                        <Typography  variant="h5" ></Typography>
                        <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button>
                
                </div>
        );
}

const handleSelect = (id) =>{
    
    let val = selectedValue;
    let index = selectedValue.indexOf(id)
    if(index!==-1){
       val.splice(index,1)
    }else{
       val.push(id)
    }
    setSelectedValue(val)
    setAction(!action)  
}

const handleSelectMetric = (id) =>{
    
        let val = selectedMetric;
        let index = selectedMetric.indexOf(id)
        if(index!==-1){
           val.splice(index,1)
        }else{
           val.push(id)
        }
        setSelectedMetric(val)
        setAction(!action)  
    }

    const columns = [
        {
                name: "submitRefNumber",
                label: "s id",
                options: {
                        filter: false,
                        sort: false,
                        display: "excluded",
                },
        },
        {
                name: "modelName",
                label: "Model Name",
                options: {
                        filter: false,
                        sort: true,
                        display: "excluded",
                      
                },
        },
      

        {
                name: "modelName",
                label: "Model Name",
                options: {
                        filter: false,
                        sort: true,
                        customBodyRender: (value, tableMeta, updateValue) => {
                                if (tableMeta.rowData) {
                                return <Typography style={{fontSize:"1rem",fontFamily:"Rowdies,light"}}>{tableMeta.rowData[1]}</Typography>
                                }
                              },
                      
                },
        },
        {
                name: "domain",
                label: "Domain",
                options: {
                        filter: false,
                        sort: true,
                        
                },
        },
        
       
        {
                name: "status",
                label: "Description",
                options: {
                        filter: true,
                        sort: true,
                       

                },
        },
        {
                name: "Action",
                label: "Action",
                options: {
                  filter: true,
                  sort: false,
                  empty: true,
                  customBodyRender: (value, tableMeta, updateValue) => {
                    if (tableMeta.rowData) {
                            if(selectedValue.includes(tableMeta.rowData[0])){
                                return <Button variant="outlined" onClick= {()=>handleSelect(tableMeta.rowData[0])} style={{background:"#2A61AD",borderRadius:"1.25rem",textTransform:"Capitalize", color:"white",width:"79px"}} ><CheckIcon/></Button>;
                            }
                        else{
                                return <Button variant="outlined" onClick= {()=>handleSelect(tableMeta.rowData[0])} style={{background:"white",borderRadius:"1.25rem",textTransform:"Capitalize",width:"79px"}} >Select</Button>;   
                        }
                    }
                  },
                },
        }
             
];

const column2 = [
        {
                name: "submitRefNumber",
                label: "s id",
                options: {
                        filter: false,
                        sort: false,
                        display: "excluded",
                },
        },
        {
                name: "modelName",
                label: "Model Name",
                options: {
                        filter: false,
                        sort: true,
                        display: "excluded",
                      
                },
        },
      

        {
                name: "modelName",
                label: "Model Name",
                options: {
                        filter: false,
                        sort: true,
                        customBodyRender: (value, tableMeta, updateValue) => {
                                if (tableMeta.rowData) {
                                return <Typography variant="h6">{tableMeta.rowData[1]}</Typography>
                                }
                              },
                      
                },
        },
        {
                name: "domain",
                label: "Domain",
                options: {
                        filter: false,
                        sort: true,
                        
                },
        },
        
       
        {
                name: "status",
                label: "Description",
                options: {
                        filter: true,
                        sort: true,
                       

                },
        },
        {
                name: "Action",
                label: "Action",
                options: {
                  filter: true,
                  sort: false,
                  empty: true,
                  customBodyRender: (value, tableMeta, updateValue) => {
                    if (tableMeta.rowData) {
                            if(selectedMetric.includes(tableMeta.rowData[0])){
                                return <Button variant="outlined" onClick= {()=>handleSelectMetric(tableMeta.rowData[0])} style={{background:"#2A61AD",borderRadius:"1.25rem",textTransform:"Capitalize", color:"white",width:"79px"}} ><CheckIcon/></Button>;
                            }
                        else{
                                return <Button variant="outlined" onClick= {()=>handleSelectMetric(tableMeta.rowData[0])} style={{background:"white",borderRadius:"1.25rem",textTransform:"Capitalize",width:"79px"}} >Select</Button>;   
                        }
                    }
                  },
                },
        }
             
];

const handleChangeCount = (count) =>{
        setPage(page+count)
} 

const options = {
        textLabels: {
                body: {
                        noMatch: "No records "
                },
                toolbar: {
                        search: "Search",
                        viewColumns: "View Column",
                },
                pagination: {
                        rowsPerPage: "Rows per page",
                },
                options: { sortDirection: "desc" },
        },
        // onRowClick: rowData => handleRowClick(rowData[0],rowData[1],rowData[4]),
        // onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
        customToolbar: fetchHeaderButton,
        filter: false,
        displaySelectToolbar: false,
        selectableRows: "none",
        print: false,
        viewColumns: false,
        search:false,
        fixedHeader: true,
        filterType: "checkbox",
        download: false,
        pagination:false
      


};

const option2 = {
        textLabels: {
                body: {
                        noMatch: "No records "
                },
                toolbar: {
                        search: "Search",
                        viewColumns: "View Column",
                },
                pagination: {
                        rowsPerPage: "Rows per page",
                },
                options: { sortDirection: "desc" },
        },
        // onRowClick: rowData => handleRowClick(rowData[0],rowData[1],rowData[4]),
        // onCellClick     : (colData, cellMeta) => handleRowClick( cellMeta),
        customToolbar: fetchHeaderButton,
        filter: false,
        displaySelectToolbar: false,
        selectableRows: "none",
        print: false,
        viewColumns: false,
        search:false,
        fixedHeader: false,
        filterType: "checkbox",
        download: false,
        pagination:false
};
    return (
        <Backdrop style={{zIndex:"1000"}}  open={props.open}>
        <div >
            <Popover
                style={{  minHeight: '580px',width:"900px" }}
                id={props.id}
                open={props.open}
                
                onClose={props.handleClose}
                anchorReference={"none"}
                style={{display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',}}
            >

                

                <div>
                <MuiThemeProvider theme = {getMuiTheme()}> 
                <div className = {classes.headerButtons}>
                                <Button onClick = {props.handleClose}><CloseIcon /></Button>
                                
                        </div>
                <div className = {classes.headerButtons}>
                
                                {fetchHeaderButton()} 
                        </div>
                {page=== 1 ?
                <>
                <MUIDataTable
                
                                title={`Select Benchmark Dataset`}
                                data={data}
                                columns={columns}
                                options= {options}
                        /> 
                        <Button
                    // disabled={!(selectedDomain.length || selectedLanguage.length || selectedSubmitter.length)}
                    // onClick={() => apply({ domainFilter: selectedDomain, language: selectedLanguage, submitter: selectedSubmitter })}
                    onClick={() =>handleChangeCount(1)}
                    color="primary" size="small" variant="contained" className={classes.applyBtn}> Next
                </Button>
                </>
                :
                <>
                <MUIDataTable
                
                                title={`Select Metric Dataset`}
                                data={data}
                                columns={column2}
                                options= {option2}
                        /> 
               

                   
                  
               
                <Button
                    // disabled={!(selectedDomain.length || selectedLanguage.length || selectedSubmitter.length)}
                    // onClick={() => apply({ domainFilter: selectedDomain, language: selectedLanguage, submitter: selectedSubmitter })}
                    color="primary" size="small" variant="contained" className={classes.applyBtn}> Submit
                </Button>
                <Button
                    onClick= {()=>handleChangeCount(-1)}
                    size="small" variant="outlined" className={classes.clrBtn}> Back
                </Button>
                </>
                }
                </MuiThemeProvider>
        </div>
             

            </Popover>
            
            
        </div >
        </Backdrop>
    );
}
export default withStyles(SelectionBenchmark)(FilterList);