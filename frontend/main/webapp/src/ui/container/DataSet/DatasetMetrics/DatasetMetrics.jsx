import DataTable from "../../../components/common/DataTable";
const DatasetMetrics = () => {
  const options = {
    download: false,
    print: false,
    search: false,
    selectableRows: false,
    filter: false,
  };
  const columns = [{ name: "datasetType", label: "Dataset Type" }];

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
