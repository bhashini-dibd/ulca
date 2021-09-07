import { Typography, withStyles } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Box from "@material-ui/core/Box";
import Collapse from "@material-ui/core/Collapse";
import IconButton from "@material-ui/core/IconButton";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import Paper from "@material-ui/core/Paper";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";

const ExpandTable = (props) => {
  const { rows, renderStatus, color, classes } = props;
  const renderTable = () => {
    const returnTypo = (value) => {
      return (
        <Typography variant="body2">
          <strong>{value}</strong>
        </Typography>
      );
    };

    return (
      <>
        <TableRow
          style={{
            borderLeft: `2px solid ${color ? "#E2F2FD" : "#E9F7EF"}`,
            borderRight: `2px solid ${color ? "#E2F2FD" : "#E9F7EF"}`,
          }}
        >
          <TableCell colSpan={8}>
            <>
              <Box style={{ margin: "0 80px" }}>
                <Table size="small" aria-label="purchases">
                  <TableHead>
                    <TableRow>
                      {/* <TableCell></TableCell> */}
                      {/* <TableCell></TableCell> */}
                      <TableCell>Benchmark Dataset</TableCell>
                      <TableCell>Metric</TableCell>
                      <TableCell>Score</TableCell>
                      <TableCell>Benchmark Run Date</TableCell>
                      <TableCell>Status</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {rows.map((row, i) => {
                      return (
                        <TableRow
                          key={i}
                          style={{
                            backgroundColor: "rgba(254, 191, 44, 0.1)",
                          }}
                        >
                          {/* <TableCell></TableCell> */}
                          {/* <TableCell></TableCell> */}
                          <TableCell>{row.benchmarkDatasetName}</TableCell>
                          <TableCell>{row.metric.toUpperCase()}</TableCell>
                          <TableCell>{row.score ? row.score : "--"}</TableCell>
                          <TableCell>{row.createdOn}</TableCell>
                          <TableCell>{renderStatus(row.status)}</TableCell>
                          {/* <TableCell></TableCell> */}
                          {/* <TableCell></TableCell> */}
                        </TableRow>
                      );
                    })}
                  </TableBody>
                </Table>
              </Box>
            </>
          </TableCell>
          .
        </TableRow>
        <TableRow className={classes.tableRow}></TableRow>
      </>
    );
  };

  return <>{renderTable()}</>;
};

export default withStyles(DataSet)(ExpandTable);
