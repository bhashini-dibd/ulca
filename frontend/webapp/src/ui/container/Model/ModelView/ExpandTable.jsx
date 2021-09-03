import { withStyles } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ExpandTable = (props) => {
  const { rows, renderStatus, color } = props;
  const renderTable = () => {
    console.log(color);
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
          <TableCell>Benchmark Dataset</TableCell>
          <TableCell>Metric</TableCell>
          <TableCell>Score</TableCell>
          <TableCell>Status</TableCell>
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
              <TableCell>{row.metric}</TableCell>
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
