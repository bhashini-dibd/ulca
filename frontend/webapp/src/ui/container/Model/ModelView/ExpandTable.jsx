import {
  withStyles,
  Typography,
  Card,
  Grid,
  ButtonBase,
} from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ExpandTable = (props) => {
  const { classes, data, handleCardClick, rows, renderStatus } = props;
  const renderTable = () => {
    return (
      <>
        <TableRow>
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
            <TableRow style={{ backgroundColor: "#E2F2FD" }}>
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
