import DataTable from "../../../components/common/DataTable";
const DatasetMetrics = () => {
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
    { name: "submitter", label: "Submitter" },
    { name: "count", label: "Count" },
  ];

  return (
    <DataTable
      title="Dataset Metrics"
      options={options}
      columns={columns}
      data={[]}
    />
  );
};

export default DatasetMetrics;
