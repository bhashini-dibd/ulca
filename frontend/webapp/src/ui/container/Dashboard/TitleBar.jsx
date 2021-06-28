import React, { useState } from "react";
import { Toolbar, FormControl, InputLabel, NativeSelect, AppBar, Paper } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import HeaderStyles from "../../styles/HeaderStyles";
import { DatasetItems } from "../../../configs/DatasetItems";
import Statistics from "./Statistics";

const TitleBar = (props) => {
    const { classes } = props;
    const [options, setOptions] = useState('parallel-corpus');

    const handleChange = (event) => {
        const value = event.target.value;
        setOptions(value);
    };
    return (
        <>
            <AppBar position="static" color="transparent" elevation={0}>
                <Toolbar>
                    {/* <Paper style={{height:'100%'}}> */}
                    <FormControl className={classes.formControl}>
                        <NativeSelect
                            value={options}
                            onChange={handleChange}
                        >
                            {
                                DatasetItems.map(menu => {
                                    return <option
                                        value={menu.value}
                                        className={classes.styledMenu}
                                    >

                                        {menu.label}
                                    </option>
                                })
                            }
                        </NativeSelect>
                    </FormControl>
                    {/* </Paper> */}
                </Toolbar>
            </AppBar>
            <Statistics />
        </>
    )
}

export default withStyles(HeaderStyles)(TitleBar);