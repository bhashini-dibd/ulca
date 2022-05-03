import DataTable from "../../../components/common/DataTable";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import GetReportData from "../../../../redux/actions/api/DataSet/DatasetMetrics/GetReportData";

const DatasetMetrics = () => {
  const dispatch = useDispatch();
  const datasetMetrics = useSelector((state) => state.datasetMetrics.result);

  const options = {
    download: false,
    print: false,
    search: false,
    selectableRows: false,
    filter: false,
  };

  const columns = [
    { name: "datasetType", label: "Dataset Type" },
    { name: "sourceLanguage", label: "Source Language" },
    { name: "targetLanguage", label: "Target Language" },
    { name: "domain", label: "Domain" },
    { name: "collectionMethod", label: "Collection Method" },
    { name: "submitterName", label: "Submitter" },
    { name: "count", label: "Count" },
  ];

  useEffect(() => {
    const obj = new GetReportData();
    dispatch(APITransport(obj));
  }, []);

  return (
    <DataTable
      title="Dataset Metrics"
      options={options}
      columns={columns}
      data={datasetMetrics}
    />
  );
};

export default DatasetMetrics;
