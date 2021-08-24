import React, { useState } from "react";
import { Toolbar, AppBar, Grid } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import HeaderStyles from "../../styles/HeaderStyles";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Theme from "../../theme/theme-default";

const StyledMenu = withStyles({
})((props) => (
    <Menu
        elevation={0}
        getContentAnchorEl={null}
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        transformOrigin={{
            vertical: 'top',
            horizontal: '',
        }}
        {...props}
    />
));
const TitleBar = (props) => {
    const { classes, options, selectedOption, handleSelectChange, page, count } = props;
    // const [options, setOptions] = useState('parallel-corpus');

    const handleChange = (event) => {
        const obj = {};

        obj.value = event;
        obj.label = options.reduce((acc, rem) => rem.value === event ? acc = rem.label : acc, "")

        handleSelectChange(obj, "", "", page)

    };
    //const [dropDownOptions, setOptions] = useState('parallel-corpus');
    const [anchorEl, openEl] = useState(null);
    const handleClose = () => {
        openEl(false)
    }

    // const getLabel = (value) => {
    //     return options.filter(data => data.value === value)[0].label

    // }
    return (
        <MuiThemeProvider theme={Theme}>
            <AppBar position="static" color="inherit" elevation={0} className={classes.appBar}>
                <Toolbar className={classes.toolbar}>
                    <Grid container className={classes.toolGrid}>
                        < Grid item xs={3} sm={3} md={2} lg={2} xl={2} className={classes.selectGrid}>
                            <Button className={classes.btnStyle}
                                disabled={page !== 0 ? true : false}
                                color="inherit"
                                onClick={(e) => openEl(e.currentTarget)}
                                variant="text">

                                {/* {selectedOption&& getLabel(selectedOption)} */}
                                <Typography variant="subtitle1">
                                    {selectedOption.label}
                                </Typography>
                                <DownIcon />
                            </Button>
                            <StyledMenu id="data-set"
                                anchorEl={anchorEl}
                                open={Boolean(anchorEl)}
                                onClose={(e) => handleClose(e)}
                                className={classes.styledMenu1}
                            >
                                {
                                    options.map(menu => {
                                        return <MenuItem
                                            value={menu.value}
                                            className={classes.styledMenu}
                                            onClick={(e) => {
                                                handleChange(menu.value)
                                                handleClose()
                                            }}
                                        >
                                            <Typography variant={"body1"}>
                                                {menu.label}
                                            </Typography>
                                        </MenuItem>
                                    })
                                }
                            </StyledMenu>
                        </Grid>
                        < Grid item xs={3} sm={3} md={2} lg={2} xl={2} className={classes.tempGrid}>
                            <Typography variant="body2" gutterBottom>
                                {/* {props.label} */}
                                Total Count
                            </Typography>
                            <Typography variant="subtitle1">
                                {/* {props.totValue} */}
                                {count ? new Intl.NumberFormat('en').format(count) : 0}
                            </Typography>
                        </Grid>
                        <Grid item xs={0} sm={0} md={3} lg={3} xl={3}></Grid>
                        <Grid item xs={6} sm={6} md={5} lg={5} xl={5}>
                            <Grid container spacing={2}>
                                {props.children}
                            </Grid>
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>
            {/* <Statistics label='Total parallel sentences' totValue='53645447'>
                 {
                    gridArray.map(grid => {
                        return (
                            <Grid item>
                                <Typography gutterBottom>{grid.title}</Typography>
                                <Typography variant="h6" component="h5">{grid.value}</Typography>
                            </Grid>
                        )
                    })
                <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="body1">29875</Typography>
                </Grid>
                 <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="h6" component='h5'>46786</Typography>
                </Grid>
                <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="h6" component='h5'>56676</Typography>
                </Grid> 
            </Statistics> */}
        </MuiThemeProvider >
    )
}

export default withStyles(HeaderStyles)(TitleBar);