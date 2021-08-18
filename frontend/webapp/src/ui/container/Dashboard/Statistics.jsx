import React from "react";
import { Toolbar, AppBar, Grid } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button } from "@material-ui/core";
import { SaveAlt } from '@material-ui/icons';
import HeaderStyles from "../../styles/HeaderStyles";


const Statistics = (props) => {
    const { classes } = props;
    return (
        <AppBar position="static" color="inherit" elevation={0} style={{ alignContent: 'center', display: 'grid' }}>
            <Toolbar className={classes.toolbar}>
                <Grid container style={{ alignItems: 'center' }}>
                    < Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                        <Typography variant="body2" gutterBottom>
                            {props.label}
                        </Typography>
                        <Typography variant="body1">
                            {props.totValue}
                        </Typography>
                    </Grid>
                    <Grid item xs={8} sm={8} md={8} lg={8} xl={8}>
                        <Grid container spacing={4}>
                            {props.children}
                        </Grid>
                    </Grid>
                    <Grid item xs={2} sm={2} md={2} lg={2} xl={2} >
                        <Button
                            size="medium"
                            color="primary"
                            variant="outlined"
                            style={{ textTransform: 'none', float: 'right' }}
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

export default withStyles(HeaderStyles)(Statistics);