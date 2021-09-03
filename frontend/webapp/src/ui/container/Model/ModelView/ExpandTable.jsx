import { Typography, withStyles } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ExpandTable = (props) => {
  const { rows, renderStatus, color } = props;
  const renderTable = () => {
    const returnTypo = (value) => {
      return <Typography variant="body2">{value}</Typography>;
    };

    return (
      <>
        <TableRow
          style={{
            borderLeft: `5px solid ${color ? "#E2F2FD" : "#E9F7EF"}`,
            borderRight: `4px solid ${color ? "#E2F2FD" : "#E9F7EF"}`,
          }}
        >
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell>{returnTypo("Benchmark Dataset")}</TableCell>
          <TableCell>{returnTypo("Metric")}</TableCell>
          <TableCell>{returnTypo("Score")}</TableCell>
          <TableCell>{returnTypo("Status")}</TableCell>
          {/* <TableCell>Action</TableCell> */}
          <TableCell></TableCell>
          <TableCell></TableCell>
        </TableRow>
        {rows.map((row) => {
          return (
            <TableRow
              style={{
                backgroundColor: color ? "#E2F2FD" : "#E9F7EF",
              }}
            >
              <TableCell></TableCell>
              <TableCell></TableCell>
              <TableCell>{row.benchmarkDatasetName}</TableCell>
              <TableCell>{row.metric.toUpperCase()}</TableCell>
              <TableCell>{row.score ? row.score : "--"}</TableCell>
              <TableCell>{renderStatus(row.status)}</TableCell>
              {/* <TableCell>Action</TableCell> */}
              <TableCell></TableCell>
              <TableCell></TableCell>
            </TableRow>
          );
        })}
      </>
    );
  };

  return <>{renderTable()}</>;
};

export default withStyles(DataSet)(ExpandTable);
