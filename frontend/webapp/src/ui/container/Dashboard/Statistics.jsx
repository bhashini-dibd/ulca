import React, { useState } from "react";
import { Toolbar, FormControl, InputLabel, NativeSelect, AppBar, Grid, Paper } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import HeaderStyles from "../../styles/HeaderStyles";
import { DatasetItems } from "../../../configs/DatasetItems";
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { SaveAlt } from '@material-ui/icons';

const Statistics = (props) => {
    const { classes } = props;
    return (
        <AppBar position="static" color="transparent" elevation={0} style={{ border: '1px solid #00000029' }}>
            <Toolbar>
                <Grid container>
                    <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>

                        <Typography variant="body2" gutterBottom>
                            Total Parallel Sentences
                                 </Typography>
                        <Typography variant="h6" component="h5">
                            345283
                                </Typography>

                    </Grid>
                    <Grid item xs={8} sm={8} md={8} lg={8} xl={8}>
                        {props.children}
                    </Grid>
                    <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                        <Button color="primary" size="medium" variant="outlined" ><SaveAlt className={classes.iconStyle} />Download</Button>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>

    )
}

export default withStyles(HeaderStyles)(Statistics);