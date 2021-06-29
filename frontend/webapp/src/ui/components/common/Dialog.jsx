import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';

export default function ResponsiveDialog(props) {
  

  const getMuiTheme = () => createMuiTheme({
    overrides: {

      MuiDialog: {
            paperWidthSm:{
                    width:"30%",
                    minWidth:"300px"
            }
    },
   
    }
});

  

  const {title,message, open,handleSubmit, handleClose  } = props;

  return (
    
    <MuiThemeProvider theme={getMuiTheme()}> 

      <Dialog
        open={open}
        onClose ={() =>{handleClose()}}
        
      >
        <DialogTitle id="responsive-dialog-title">{title}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {message}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick ={() =>{handleClose()}} color="primary">
            No
          </Button>
          <Button variant="contained" onClick={() =>{handleSubmit()}} color="primary" autoFocus>
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </MuiThemeProvider>
  );
}