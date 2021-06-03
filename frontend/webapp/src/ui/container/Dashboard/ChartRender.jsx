import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import Theme from "../../theme/theme-default";

import { withStyles, Typography, Link, MuiThemeProvider, createMuiTheme, Paper, Grid } from "@material-ui/core";

import ChartStyles from "../../styles/Dashboard";
import {
    ResponsiveContainer,
    BarChart, Bar, Brush, Cell, CartesianGrid, ReferenceLine, ReferenceArea,
    XAxis, YAxis, Tooltip, Legend, ErrorBar, LabelList, Rectangle
} from 'recharts';

import APITransport from "../../../redux/actions/apitransport/apitransport";
import FetchLanguageDataSets from "../../../redux/actions/api/Dashboard/languageDatasets";

import { isMobile } from 'react-device-detect';
import Header from '../../components/common/Header';
import authenticate from '../../../configs/authenticate';

var jp = require('jsonpath')
var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]


const ChartRender = (props) => {

    const history = useHistory();
    const dispatch = useDispatch();
    const DashboardReport = useSelector((state) => state.dashboardReport);
    const { classes } = props;

    useEffect(() => {
        const userObj = new FetchLanguageDataSets("parallel-corpus", "languagePairs");
        dispatch(APITransport(userObj));
        if (authenticate()) {
            history.push(`${process.env.PUBLIC_URL}/private-dashboard`)
        } else {
            localStorage.removeItem('token')
            history.push(`${process.env.PUBLIC_URL}/dashboard`)

        }
    }, []);

    return (
        <>
            { !authenticate() &&
                <MuiThemeProvider theme={Theme}>
                    <Header />
                    <br />
                    <br />
                    <br />
                    <br />
                </MuiThemeProvider>
            }
            <div className={classes.container}>
                <Paper elevation={3} className={classes.paper}>
                    <div className={classes.title}>
                        <Typography variant="b" component="h3">Dataset Type :</Typography>
                    </div>
                    <ResponsiveContainer width="95%" height={450}>
                        <BarChart width={900} height={450} data={DashboardReport} maxBarSize={100} >
                            <XAxis dataKey="label"
                                textAnchor={isMobile ? "end" : "middle"}
                                tick={{ angle: isMobile ? -60 : 0 }} height={isMobile ? 100 : 60}
                                interval={0}
                                position="insideLeft"

                            />
                            <YAxis type="number" dx={0} />
                            <CartesianGrid horizontal={true} vertical={false} textAnchor={"middle"} />

                            <Tooltip />
                            <Bar dataKey="value" radius={[4, 4, 0, 0]} maxBarSize={30}>

                                {
                                    DashboardReport.length > 0 && DashboardReport.map((entry, index) => {
                                        const color = colors[index < 9 ? index : index % 10]
                                        return <Cell key={index} fill={`#${color}`} />;
                                    })
                                }
                            </Bar>
                        </BarChart>
                    </ResponsiveContainer>

                </Paper>
            </div>
        </>
    )


}



export default withStyles(ChartStyles(Theme))(ChartRender);
