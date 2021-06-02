import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import { withStyles } from '@material-ui/core/styles';
import GlobalStyle from '../../styles/Styles';

const CustomizedSnackbars = (props) => {
    let { classes } = props
    return (
        <div className={classes.snackbar}>
            <Snackbar
                open={props.open}
                autoHideDuration={props.timeOut}
                onClose={props.handleClose}
                anchorOrigin={props.anchorOrigin}
            >
                <Alert elevation={3}
                    variant="filled"
                    className={classes.snackbarFont}
                    onClose={props.handleClose}
                    severity={props.variant}>
                    {props.message}
                </Alert>
            </Snackbar>
        </div>
    );
}

export default withStyles(GlobalStyle)(CustomizedSnackbars);
