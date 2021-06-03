import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {  useHistory } from "react-router-dom";

import { withStyles, Typography,Link, MuiThemeProvider, createMuiTheme,Paper, Grid, Button } from "@material-ui/core";

import BreadCrum from '../../components/common/Breadcrum';
import ChartStyles from "../../styles/Dashboard";
import {
    ResponsiveContainer,
    BarChart, Bar, Brush, Cell, CartesianGrid, ReferenceLine, ReferenceArea,
    XAxis, YAxis, Tooltip, Legend, ErrorBar, LabelList, Rectangle
} from 'recharts';
import Select from 'react-select';
import APITransport from "../../../redux/actions/apitransport/apitransport";
import FetchLanguageDataSets from "../../../redux/actions/api/Dashboard/languageDatasets";

import { isMobile } from 'react-device-detect';
import {FilterList} from '@material-ui/icons';
var jp = require('jsonpath')
var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]


const ChartRender = (props) => {
        const [selectedOption, setselectedOption]   = useState({ value: 'Parallel Corpus', label: 'Parallel Corpus' });
        const [title, setTitle]                     = useState("Number of parallel sentences per language with English");
        const history                               = useHistory();
        const dispatch                              = useDispatch();
        const DashboardReport                       = useSelector( (state) => state.dashboardReport);
        const { classes }                           = props;
        const options = [
            { value: 'Parallel Corpus', label: 'Parallel Corpus' },
            { value: 'Monolingual Dataset', label: 'Monolingual Dataset' },
            { value: 'ASR / TTS Dataset', label: 'ASR / TTS Dataset' },
            { value: 'OCR Dataset', label: 'OCR Dataset' },
          ];

        useEffect(()                  => {
                const userObj         = new FetchLanguageDataSets( "parallel-corpus", "languagePairs");
                 dispatch(APITransport(userObj));
        }, []);

    return (

                <div className={classes.container}>

                    
                    {/* <div className={classes.breadcrum}>
                <BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
            </div> */}
            <Paper elevation={3} className={classes.paper}>
                            
                            
            <div className={classes.titleBar}>
                <Typography variant="b" component="h3" className = {classes.Typography}>Dataset Type :</Typography>
                <Select className = {classes.select} color= "primary"
                    value={selectedOption}
                    onChange={(value)=>setselectedOption( value  )}
                    options={options}
    /           >
                <Button color={"primary" } size="medium" variant="outlined" className={classes.filterButton} onClick={() => this.handleLanguageChange("domain")}><FilterList className ={classes.iconStyle}/>Filter</Button>
                <Button color={"primary" } size="medium" variant="outlined" className={classes.filterButtonIcon} onClick={() => this.handleLanguageChange("domain")}><FilterList className ={classes.iconStyle}/></Button>
                        </div>
                        <div className={classes.title}>
                                <Typography value="" variant="h6">
                                    {title}
                                </Typography>
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
               
    )
   

}



export default withStyles(ChartStyles)(ChartRender);
