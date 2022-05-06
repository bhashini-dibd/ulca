import DataTable from "../../../components/common/DataTable";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetReportData from "../../../../redux/actions/api/DataSet/DatasetMetrics/GetReportData";
import {  Grid } from "@material-ui/core";
import Search from "../../../components/Datasets&Model/Search";
import getSearchedValue from "../../../../redux/actions/api/DataSet/DatasetSearch/GetSearchedValues";



const DatasetMetrics = () => {
  const dispatch = useDispatch();
  const datasetMetrics = useSelector((state) => state.datasetMetrics.result);

  
  const options = {
    download: true,
    print: false,
    search: false,
    selectableRows: false,
    filter: false,
   
   
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
    dispatch(getSearchedValue(value));
  };
const searchTerm=""
  return (
  <div>
   
       
   <div style={{ width:"100%",position:"absolute",right:"350px",top:"165px",zIndex:"1"}}>
   <Search value="" handleSearch={(e) => handleSearch(e.target.value)} />
   </div>
  
   <DataTable
      title="Dataset Metrics"
      options={options}
      columns={columns}
        data={target}
      
     
      />
  </div>
     
     
    
   
   

    
   
  );
};

export default DatasetMetrics;
