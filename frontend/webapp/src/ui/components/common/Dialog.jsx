import React from 'react';
import Button from '@material-ui/core/Button';
import {Dialog,Typography,Divider} from '@material-ui/core';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import Theme from "../../theme/theme-default";

export default function ResponsiveDialog(props) {
  

//   const getMuiTheme = () => createMuiTheme({
//     overrides: {

//       MuiDialog: {
//             paperWidthSm:{
//                     width:"30%",
//                     minWidth:"300px"
//             }
//     },
   
//     }
// });

  

  const {title,message, open,handleSubmit, handleClose,actionButton, actionButton2  } = props;
  debugger
  return (
    
    <MuiThemeProvider theme={Theme}> 

      <Dialog
        open={open}
        onClose ={() =>{handleClose()}}
        
      >
        <DialogTitle id="responsive-dialog-title"> <Typography variant={"body1"}>{title}</Typography></DialogTitle>
        <DialogContent>
          <DialogContentText>
            <Typography variant={"body2"}>
            {message}
            </Typography>
          </DialogContentText>
        </DialogContent >
        <Divider light />
        <DialogActions>
          <Button size ={"small"} autoFocus onClick ={() =>{handleClose()}} color="default" variant={"outlined"}>
            {actionButton}
          </Button>
          <Button size ={"small"} variant="contained" onClick={() =>{handleSubmit()}} color="primary" autoFocus>
            {actionButton2}
          </Button>
        </DialogActions>
      </Dialog>
    </MuiThemeProvider>
  );
}