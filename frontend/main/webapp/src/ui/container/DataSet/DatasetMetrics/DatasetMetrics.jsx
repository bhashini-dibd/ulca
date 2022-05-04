import DataTable from "../../../components/common/DataTable";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetReportData from "../../../../redux/actions/api/DataSet/DatasetMetrics/GetReportData";

const DatasetMetrics = () => {
  const dispatch = useDispatch();
  const datasetMetrics = useSelector((state) => state.datasetMetrics.result);

  const options = {
    download: true,
    print: false,
    search: true,
    selectableRows: false,
    filter: false,
  };

  const columns = [
    { name: "datasetType", label: "Dataset Type" ,options:{"viewColumns":false} },
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
      element.targetLanguage="NA"
    }
    console.log(element ,"dataa")
    target.push(element)
  })
  return (

    <DataTable
      title="Dataset Metrics"
      options={options}
      columns={columns}
       data={target}
     
    />
   
  );
};

export default DatasetMetrics;
