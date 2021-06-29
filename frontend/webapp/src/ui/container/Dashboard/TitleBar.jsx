import React, { useState } from "react";
import { Toolbar, FormControl, InputLabel, NativeSelect, AppBar, Paper,Grid } from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import HeaderStyles from "../../styles/HeaderStyles";
import { DatasetItems } from "../../../configs/DatasetItems";
import Statistics from "./Statistics";

const TitleBar = (props) => {
    const { classes } = props;
    const [options, setOptions] = useState('parallel-corpus');
    const gridArray = [{ title: 'Title1', value: '35555' }, { title: 'Title2', value: '4000' }]
    const handleChange = (event) => {
        const value = event.target.value;
        setOptions(value);
    };
    return (
        <>
            {/* <AppBar position="static" color="transparent" elevation={0}>
                <Toolbar>
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
                </Toolbar>
            </AppBar> */}
            <Statistics label='Total parallel sentences' totValue='53645447'>
            {/* {
                    gridArray.map(grid => {
                        return (
                            <Grid item>
                                <Typography gutterBottom>{grid.title}</Typography>
                                <Typography variant="h6" component="h5">{grid.value}</Typography>
                            </Grid>
                        )
                    }) */
                }
                <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="h6" component='h5'>29875</Typography>
                </Grid>
                <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="h6" component='h5'>46786</Typography>
                </Grid>
                <Grid item>
                    <Typography gutterBottom variant='body2'> Title Here</Typography>
                    <Typography variant="h6" component='h5'>56676</Typography>
                </Grid>
            </Statistics>
        </>
    )
}

export default withStyles(HeaderStyles)(TitleBar);