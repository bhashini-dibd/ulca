import React from "react";
import {
  Dialog,
  Typography,
  Divider,
  IconButton,
  Grid,
} from "@material-ui/core";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import { MuiThemeProvider } from "@material-ui/core/styles";
import Theme from "../../../../theme/theme-default";
import CloseIcon from "@material-ui/icons/Close";

export default function OCRModal(props) {
  const { title, message, open, handleClose } = props;
  return (
    <MuiThemeProvider theme={Theme}>
      <Dialog
        open={open}
        onClose={() => {
          handleClose();
        }}
      >
        <DialogTitle id="responsive-dialog-title">
          {" "}
          <IconButton
            onClick={handleClose}
            style={{ padding: "0px", float: "right" }}
          >
            <CloseIcon color="action" />
          </IconButton>
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            <img src={message} alt="OCR URL" />
          </DialogContentText>
        </DialogContent>
        <Divider light />
      </Dialog>
    </MuiThemeProvider>
  );
}
