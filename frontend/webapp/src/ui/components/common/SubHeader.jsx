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
import FilterListIcon from '@material-ui/icons/FilterList';
import { useHistory } from 'react-router-dom';
import {Toolbar, MuiThemeProvider} from '@material-ui/core'
import Theme from "../../theme/theme-default";


const styles = theme => ({
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
        borderTop: "none", borderRight: "none", borderLeft: "none"
    },
    toolbar: {
        minHeight: "48px",
        maxWidth: "1272px",
        width: "98%",
        margin: "0 auto",
        display: 'flex',
        alignItems: 'center',
        padding: "0",
        boxSizing: "border-box"
      },
    // tablabel: {
    //     fontSize: '16px',
    //     letterSpacing: '0px',
    //     fontFamily: 'Roboto',
    //     padding: '0',
    //     marginRight: '34px',
    //     "@media (min-width:600px)": {
    //         minWidth: 'auto',
    //     },

    //     "@media (max-width:600px)": {
    //         marginRight: '20px',
    //         minWidth: 'auto',
    //     },
    //     "@media (max-width:550px)": {
    //         fontSize: "0.5rem",
    //     }
    // }
});

function TabPanel(props) {
    const { children, value, index, ...other } = props;
    const history = useHistory();
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

const SubHeader = (props) => {
    const { classes } = props;
    const history = useHistory();
    const handleClick = (url) => {
        history.push(`${process.env.PUBLIC_URL}${url}`);
    }
    return (
        <AppBar className={classes.appTab} position="static" color='default'>
            <Toolbar className={classes.toolbar}>
            <Grid container spacing={0}>
            <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
                </Grid>
                <Grid item >
                    <Tabs value={props.value}  onChange={props.handleChange}>
                        {
                            props.tabs.map((tab, index) => {
                                return (
                                    <Tab label={tab.name} {...a11yProps(index)} onClick={() => handleClick(tab.url)} />
                                )
                            })
                        }
                    </Tabs>
                </Grid>
            </Grid>
            </Toolbar>
        </AppBar>
    );
}
export default withStyles(styles)(SubHeader);