import React, { useState } from "react";
import { Toolbar, FormControl, InputLabel, NativeSelect, AppBar, Grid, Paper } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import DatasetStyles  from "../../styles/Dataset";
import { DatasetItems } from "../../../configs/DatasetItems";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { SaveAlt } from '@material-ui/icons';


const Statistics = (props) => {
    const { classes } = props;
    return (
        <AppBar position="static" color="transparent" elevation={0} style={{ border: '1px solid #00000029' }}>
            <Toolbar>
                <Grid container style={{ alignItems: 'center' }}>
                    < Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                        <Typography variant="body2" gutterBottom>
                            {props.label}
                        </Typography>
                        <Typography variant="h6" component="h5">
                            {props.totValue}
                        </Typography>
                    </Grid>
                    <Grid item xs={8} sm={8} md={8} lg={8} xl={8}>
                        <Grid container spacing={2}>
                            {props.children}
                        </Grid>
                    </Grid>
                    <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                        <Button
                            size="medium"
                            color="primary"
                            variant="outlined"
                            style={{ textTransform: 'none' }}
                            onClick={props.handleOnClick}
                        >
                            <SaveAlt className={classes.iconStyle} />Download
                        </Button>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>

    )
}

export default withStyles(DatasetStyles)(Statistics);