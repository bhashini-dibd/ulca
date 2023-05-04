import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    Typography
  } from "@material-ui/core";
  
  const ServiceProviderDialog = ({ open, handleClose, submit }) => {
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
                color="primary"
                style={{ borderRadius: "20px", marginTop: "10px",marginRight:"8px", marginBottom: "10px" }}
                onClick={handleClose}
              >
                Cancel
              </Button>
              <Button
                variant="contained"
                color="primary"
                style={{ borderRadius: "20px", marginTop: "10px", marginRight:"8px", marginBottom: "10px"}}
                onClick={() => submit()}
              >
                Revoke
              </Button>
        </DialogActions>
      </Dialog>
    );
  };
  
  export default ServiceProviderDialog;
  