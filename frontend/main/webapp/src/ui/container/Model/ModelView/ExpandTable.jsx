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
import { translate } from "../../../../assets/localisation";

const ExpandTable = (props) => {
  const { rows, renderStatus, color, classes } = props;

  const convertDate = (date) => {
    let myDate = new Date(date);
    return myDate
      .toLocaleString("en-IN", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      })
      .toUpperCase();
  };

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
          <TableCell colSpan={9}>
            <>
              <Box style={{ margin: "0 80px" }}>
                <Table size="small" aria-label="purchases">
                  <TableHead>
                    <TableRow>
                      {/* <TableCell></TableCell> */}
                      {/* <TableCell></TableCell> */}
                      <TableCell>
                        {translate("label.benchmarkDataset")}
                      </TableCell>
                      <TableCell> {translate("label.metric")}</TableCell>
                      <TableCell> {translate("label.score")}</TableCell>
                      <TableCell>
                        {translate("label.benchmarkRunDate")}
                      </TableCell>
                      <TableCell>{translate("label.status")}</TableCell>
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
                          <TableCell>{row?.benchmarkDatasetName}</TableCell>
                          <TableCell>{row?.metric?.toUpperCase()}</TableCell>
                          <TableCell>{row?.score ? row.score : "--"}</TableCell>
                          <TableCell>{convertDate(row.createdOn)}</TableCell>
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
        </TableRow>
        <TableRow className={classes.tableRow}></TableRow>
      </>
    );
  };

  return <>{renderTable()}</>;
};

export default withStyles(DataSet)(ExpandTable);
