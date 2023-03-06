import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  Typography
} from "@material-ui/core";

const RevokeDialog = ({ open, handleClose, submit }) => {
  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
      PaperProps={{ style: { borderRadius: "10px" } }}
    >
      <DialogContent style={{marginTop:"5px"}}>
        <DialogContentText id="alert-dialog-description">
          <Typography variant="body1">
            {" "}
            Are you sure you want to revoke this API key ?
          </Typography>
        </DialogContentText>
      </DialogContent>
      <DialogActions >
        <Button
          variant="text"
          onClick={handleClose}
          style={{ borderRadius: "6px",color:"#042B91" }}
        >
          Cancel
        </Button>
        <Button
          variant="contained"
          color="primary"
          onClick={() => submit()}
          autoFocus
          style={{lineHeight: "1", borderRadius: "8px",height:"35px" }}
        >
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default RevokeDialog;
