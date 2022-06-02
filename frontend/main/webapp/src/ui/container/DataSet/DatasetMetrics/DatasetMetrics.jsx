import DataTable from "../../../components/common/DataTable";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetReportData from "../../../../redux/actions/api/DataSet/DatasetMetrics/GetReportData";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValue from "../../../../redux/actions/api/DataSet/DatasetSearch/GetSearchedValues";
import { withStyles, Grid } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";



const DatasetMetrics = (props) => {
  const { classes } = props;
  const dispatch = useDispatch();
  const datasetMetrics = useSelector((state) => state.datasetMetrics.result);

  
  const options = {
    download: true,
    viewColumns:false,
    print: false,
    search: false,
    selectableRows: false,
    filter: false,
    
    downloadOptions:{
      filterOptions:{
      useDisplayedColumnsOnly:true,
      }
    },
    sortOrder: {
      name: 'datasetType',
      direction: 'asc',
  },
  
  
};

 

  const columns = [
    { name: "datasetType", label: "Dataset Type" ,options:{"viewColumns":false } },
    { name: "sourceLanguage", label: "Source Language" },
    { name: "targetLanguage", label: "Target Language" },
    { name: "domain", label: "Domain" },
    { name: "collectionMethod", label: "Collection Method" },
    { name: "submitterName", label: "Submitter" },
    { name: "count", label: "Count",options:{ "viewColumns":false} },
  ];

  

  useEffect(() => {
    const obj = new GetReportData();
    dispatch(APITransport(obj));
  }, []);


  var target=[]
  datasetMetrics.forEach((element)=>{
    console.log()
    if(element.targetLanguage==null || element.targetLanguage== "" )
    {
      element.targetLanguage="-"
    }
    console.log(element ,"dataa")
    target.push(element)
  })

  const handleSearch = (value) => {
    console.log(value,"search value")
    
     dispatch(getSearchedValue(value));
  };
  

  return (
  <div>
   
       
   <div className={classes.metricsSearchbar}>
   <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
   </div>
  
   <DataTable
      title="Dataset Metrics"
      options={options}
      columns={columns}
      // filterOptions={filterOptions}
        data={target}

      
     
      />
  </div>
     
     
    
   
   

    
   
  );
};

export default withStyles(DataSet)( DatasetMetrics);
