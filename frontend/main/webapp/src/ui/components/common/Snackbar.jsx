import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import { MuiThemeProvider, withStyles } from '@material-ui/core/styles';
import GlobalStyle from '../../styles/Styles';
import { createMuiTheme } from '@material-ui/core/styles';

const CustomizedSnackbars = (props) => {

    const getMuiTheme = () => createMuiTheme({
        overrides: {
            MuiSnackbar: {
                anchorOriginTopRight: {
                    marginTop: "50px"
                }
            }
        }
    });
    let { classes } = props
    return (
        <MuiThemeProvider theme={getMuiTheme()}>
            <div className={classes.snackbar}>
                <Snackbar
                    autoHideDuration={props.hide? props.hide : 50000}
                    open={props.open}
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
        </MuiThemeProvider>
    );
}

export default withStyles(GlobalStyle)(CustomizedSnackbars);
