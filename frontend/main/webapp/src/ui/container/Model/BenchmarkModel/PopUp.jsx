import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import { useState } from "react";
import { Grid } from "@material-ui/core";
import { SaveAlt } from "@material-ui/icons";
import DatasetStyle from "../../../styles/Dataset";
import { withStyles } from "@material-ui/core/styles";
import CloseIcon from "@material-ui/icons/Close";
import { translate } from "../../../../assets/localisation";
const useStyles = makeStyles((theme) => ({
  form: {
    display: "flex",
    flexDirection: "column",
    margin: "auto",
    width: "fit-content",
  },
  formControl: {
    marginTop: theme.spacing(2),
    minWidth: 120,
  },
  formControlLabel: {
    marginTop: theme.spacing(1),
  },
}));

const PopUpDialog = (props) => {
  const { classes } = props;
  // const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [fullWidth, setFullWidth] = useState(true);
  const [maxWidth, setMaxWidth] = useState("sm");
  const rows = [
    createData("Language Pair", "Hindi-English"),
    createData("Curation Date", "23/4/2019"),
    createData("#Records", "128k"),
    createData("#Downloaded", "3k"),
  ];
  function createData(name, calories) {
    return { name, calories };
  }
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  return (
    <>
      {/* <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                Open dialog
      </Button> */}
      <Dialog
        fullWidth={fullWidth}
        maxWidth={maxWidth}
        open={true}
        onClose={handleClose}
        // className={classes.popupDialog}
      >
        <Grid container style={{ alignItems: "center" }}>
          <Grid item xs={9} sm={9} md={9} lg={9} xl={9}>
            <DialogTitle>{translate("label.engHinCorpus")}</DialogTitle>
          </Grid>
          <Grid item xs={3} sm={3} md={3} lg={3} xl={3}>
            <Button
              variant="outlined"
              onClick={handleClose}
              style={{ float: "right", marginRight: "21px" }}
            >
              <CloseIcon fontSize="small" className={classes.iconStyle} />
              {translate("button.close")}
            </Button>
          </Grid>
        </Grid>
        <DialogContent>
          <DialogContentText></DialogContentText>
          <TableContainer
            component={Paper}
            elevation={0}
            style={{ border: "1px solid #00000029", borderRadius: "0" }}
          >
            <Table>
              <TableBody>
                {rows.map((row) => (
                  <TableRow key={row.name}>
                    <TableCell
                      style={{
                        boxShadow: "4px 0 4px -4px #00000029",
                        width: "140px",
                      }}
                      component="th"
                      scope="row"
                    >
                      {row.name}
                    </TableCell>
                    <TableCell align="left">{row.calories}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </DialogContent>
        <DialogActions>
          <Button size="small" variant="outlined">
            <SaveAlt className={classes.iconStyle} />
            {translate("label.downloadSample")}
          </Button>
          <Button
            size="small"
            variant="outlined"
            className={classes.buttonStyle}
          >
            <SaveAlt className={classes.iconStyle} />
            {translate("label.downloadAll")}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
export default withStyles(DatasetStyle)(PopUpDialog);
