import React from 'react';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import { Button, Grid } from '@material-ui/core';
import InputBase from '@material-ui/core/InputBase';
import SearchIcon from '@material-ui/icons/Search';
import { withStyles } from '@material-ui/core/styles';

const styles = theme => ({
    search: {
        position: 'relative',
        borderRadius: '24px',
        backgroundColor: "#F3F3F3",
        marginLeft: 0,
        width: '220px',
    },
    searchIcon: {
        padding: theme.spacing(0, 2),
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: '#00000029'
    },
    inputInput: {
        padding: theme.spacing(1, 1, 1, 0),
        paddingLeft: `calc(1em + ${theme.spacing(3)}px)`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: '12ch',
        },
        fontStyle: 'italic',
        fontSize: '14px'
    },
    appTab: {
        borderTop: "none", borderRight: "none", borderLeft: "none", marginTop: '10px'
    },
    gridAlign: {
        justifyContent: 'flex-end', alignItems: 'flex-end'
    }
});

function TabPanel(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const SimpleTabs = (props) => {
    const { classes } = props;

    return (
        <div>
            <AppBar className={classes.appTab} position="static" color="inherit">
                <Grid container spacing={2}>
                    <Grid item xs={6} sm={6} md={7} lg={8} xl={8}>
                        <Tabs value={props.value} onChange={props.handleChange}>
                            {
                                props.tabs.map((tab, index) => {
                                    return (
                                        <Tab label={tab.label} {...a11yProps(index)} />
                                    )
                                })
                            }
                        </Tabs>
                    </Grid>
                    <Grid item xs={6} sm={6} md={5} lg={4} xl={4}>
                        <Grid container spacing={2} className={classes.gridAlign}>
                            <Grid item>
                                <div className={classes.search}>
                                    <div className={classes.searchIcon}>
                                        <SearchIcon fontSize="small" />
                                    </div>
                                    <InputBase
                                        placeholder="Search..."
                                        onChange={(e) => props.handleSearch(e)} value={props.searchValue}
                                        classes={{
                                            root: classes.inputRoot,
                                            input: classes.inputInput,
                                        }}
                                        inputProps={{ 'aria-label': 'search' }}
                                    />
                                </div>
                            </Grid>
                            {/* <Grid item>
                                <Button variant="outlined" onClick={props.handleShowFilter}>Filter</Button>
                            </Grid> */}
                        </Grid>
                    </Grid>
                </Grid>
            </AppBar>
            {
                props.children
            }
        </div>
    );
}
export default withStyles(styles)(SimpleTabs);