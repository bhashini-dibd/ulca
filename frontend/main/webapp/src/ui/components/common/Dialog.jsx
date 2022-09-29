import React from 'react';
import Button from '@material-ui/core/Button';
import {Dialog,Typography,Divider, TextField,withStyles} from '@material-ui/core';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { MuiThemeProvider } from '@material-ui/core/styles';
import Theme from "../../theme/theme-default";
import CommonStyles from "../../styles/Styles";
import { useState } from 'react';

const ResponsiveDialog = (props) => {
    const {title,message, open,handleSubmit, handleClose,actionButton, actionButton2,showTextBox, handleTextBox, classes  } = props;
    const [reason, setReason] = useState();
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
            {
              showTextBox ? 
              <TextField 
                className={classes.contributionTextBox} 
                margin="dense" 
                required 
                label="Reason to unpublish" 
                variant="outlined" 
                fullWidth 
                onChange={(e) => {handleTextBox(e.target.value); setReason(e.target.value)}}
                InputProps={{ className: `${classes.contributionTextBoxInput}` }}
                InputLabelProps={{ className: `${classes.contributionTextBoxInput}` }}
                multiline
              />
              : null
            }
          </DialogContentText>
        </DialogContent >
        <Divider light />
        <DialogActions>
          <Button size ={"small"} autoFocus onClick ={() =>{handleClose()}} color="default" variant={"outlined"}>
            {actionButton}
          </Button>
          <Button size ={"small"} variant="contained" onClick={() =>{handleSubmit()}} color="primary" autoFocus disabled={showTextBox && !reason ? true: false}>
            {actionButton2}
          </Button>
        </DialogActions>
      </Dialog>
    </MuiThemeProvider>
  );
}

export default withStyles(CommonStyles)(ResponsiveDialog);