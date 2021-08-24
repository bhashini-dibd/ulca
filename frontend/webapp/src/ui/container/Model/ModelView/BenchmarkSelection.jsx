import React, { useState } from 'react';
import SelectionBenchmark from "../../../styles/SelectionBenchmark";
import { withStyles, Button, Divider, Grid, Backdrop, Popover, makeStyles, Checkbox, FormControlLabel, Typography } from "@material-ui/core";
import { MuiThemeProvider,createTheme } from '@material-ui/core/styles';
import createMuiTheme from "../../../styles/Datatable";
import MUIDataTable from "mui-datatables";
import CheckIcon from '@material-ui/icons/Check';

const FilterList = (props) => {
    const { classes } = props;
    const { filter, selectedFilter, handleClose, apply,data } = props
    const [selectedValue, setSelectedValue] = useState([])
    const [action, setAction] = useState(false)
    const [page, setPage] = useState(1)

    const getMuiTheme = () => createTheme({
        overrides: {
                
                MuiTableCell: {
                        head    : {
                                
                                backgroundColor : "#c7c6c68a !important",
                                fontWeight      :"bold"
                        }
                },
                MUIDataTableBodyCell:{root : {textTransform: "capitalize"}},
                MuiToolbar: {
                         root: { 
                                 display: "none" 
                                } 
                        },
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
    const fetchHeaderButton = () => {

        return <>
                
                {/* <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterListIcon className={classes.iconStyle} />Filter</Button> */}
                {/* <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={() => MyContributionListApi()}><Cached className={classes.iconStyle} />Refresh</Button>
                <Button color={"default"} size="medium" variant="default"  className={classes.buttonStyle} onClick={handleViewChange}> {view ? <List size = "large" /> : <GridOn />}</Button>         */}
        </>
}

const handleSelect = (id) =>{
    
    let val = selectedValue;
   debugger
    let index = selectedValue.indexOf(id)
    if(index!==-1){
       val.splice(index,1)
    }else{
       val.push(id)
    }
    setSelectedValue(val)
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
                            console.log()
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
                            console.log()
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

const handleChangeCount = (count) =>{
        debugger
        setPage(page+count)
} 



console.log(selectedValue)
const options = {
        textLabels: {
                body: {
                        noMatch: "No records"
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

const option2 = {
        textLabels: {
                body: {
                        noMatch: "No records"
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

        console.log(page)
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
                {page=== 1 ?
                <>
                <MUIDataTable
                
                                title={`My Contribution`}
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
                
                                title={`My Contribution`}
                                data={data}
                                columns={column2}
                                options= {option2}
                        /> 
               

                   
                  
               
                <Button
                    // disabled={!(selectedDomain.length || selectedLanguage.length || selectedSubmitter.length)}
                    // onClick={() => apply({ domainFilter: selectedDomain, language: selectedLanguage, submitter: selectedSubmitter })}
                    color="primary" size="small" variant="contained" className={classes.applyBtn}> Next
                </Button>
                <Button
                    onClick= {()=>handleChangeCount(-1)}
                    size="small" variant="outlined" className={classes.clrBtn}> back
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