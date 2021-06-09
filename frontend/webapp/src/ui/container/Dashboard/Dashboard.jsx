import React from "react";
import { withRouter } from "react-router-dom";
import { withStyles } from '@material-ui/core/styles';
import { bindActionCreators } from "redux";
import { connect } from "react-redux";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import ChartStyles from "../../../styles/web/chartStyles";
import {
    ResponsiveContainer,
    BarChart, Bar, Brush, Cell, CartesianGrid, ReferenceLine, ReferenceArea,
    XAxis, YAxis, Tooltip, Legend, ErrorBar, LabelList, Rectangle
} from 'recharts';
import { createMuiTheme } from '@material-ui/core/styles';
import APITransport from "../../../redux/actions/apitransport/apitransport";
import FetchLanguageDataSets from "../../../redux/actions/apis/dashboard/languageDatasets";
import ChartRenderHeader from "./ChartRenderHeader"
import Container from '@material-ui/core/Container';
import { isMobile } from 'react-device-detect';
import Button from '@material-ui/core/Button';
import BackIcon from '@material-ui/icons/ArrowBack';
var jp = require('jsonpath')
var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]

class ChartRender extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            word: "",
            currentPage: 0,
            dataSetValues: [],
            filterValue: 'domain',
            title: "English-Indic language parallel corpus"
        }

    }

    getData(dataValue) {
        let condition = `$..[*].${dataValue}`
        let dataCalue = jp.query(this.state.dataSetValues, condition)
        return dataCalue
    }

    componentDidMount() {
        this.handleApiCall("parallel-corpus", "languagePairs", [])
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.dataValues !== this.props.dataValues) {
            if (this.props.dataValues.length > 0) {
                this.fetchChartData()
            }

        }
    }

    fetchChartData() {
        if (this.props.dataValues.length > 0) {
            let others = this.props.dataValues.slice(7, this.props.dataValues.length)
            let othersCount = 0
            others.map(dataVal => {
                othersCount = dataVal.value + othersCount

            })

            let dataSetValues = this.props.dataValues.slice(0, 7)
            let obj = {}

            if (this.props.dataValues.length > 7) {
                obj.value = othersCount
                obj.label = "Others"
                dataSetValues.push(obj)
            }

            this.setState({ dataSetValues, originalValues: this.props.dataValues })

            if (this.state.cardNavigation) {
                this.setState({
                    cardNavigation: false
                })
            }
        }

    }

    handleApiCall = (dataType, value, criterions) => {
        const apiObj = new FetchLanguageDataSets(dataType, value, criterions);
        this.props.APITransport(apiObj);
    }

    handleOnClick(value, event, filterValue) {
        if (event && event.hasOwnProperty("label") && event.label === "Others") {
            let others = this.props.dataValues.slice(7, this.props.dataValues.length)
            this.setState({
                dataSetValues: others,
                cardNavigation: true
            })
        } else {
            switch (value) {
                case 1:
                    this.handleApiCall("parallel-corpus", filterValue ? filterValue : this.state.filterValue, [{ "type": "PARAMS", "sourceLanguage": { "type": "PARAMS", "value": "English" }, "targetLanguage": { "type": "PARAMS", "value": this.state.selectedLanguage ? this.state.selectedLanguage : event && event.hasOwnProperty("label") && event.label } }])
                    this.setState({ currentPage: value, dataSetValues: [], selectedLanguage: this.state.selectedLanguage ? this.state.selectedLanguage : event && event.hasOwnProperty("label") && event.label, title: `English-${this.state.selectedLanguage ? this.state.selectedLanguage : event && event.hasOwnProperty("label") && event.label}  parallel corpus - Grouped by ${(filterValue == "domain") ? "Domain" : (filterValue == "source") ? "Source" : filterValue == "collectionMethod" ? "Collection Method" : "Domain"}` })
                    break;
                case 2:
                    this.handleApiCall("parallel-corpus", this.state.filterValue == "source" ? "domain" : "source", [{ "type": "PARAMS", "sourceLanguage": { "type": "PARAMS", "value": "English" }, "targetLanguage": { "type": "PARAMS", "value": this.state.selectedLanguage } }, { "type": "PARAMS", "value": event && event.hasOwnProperty("label") && event.label }])
                    this.setState({ currentPage: value, dataSetValues: [], title: `English-${this.state.selectedLanguage} parallel corpus`, filterValue: 'domain' })
                    break;
                case 0:
                    this.handleApiCall("parallel-corpus", "languagePairs", [])
                    this.setState({ currentPage: value, filterValue: 'domain', selectedLanguage: '', dataSetValues: [], title: "English-Indic language parallel corpus" })
                    break;
                default:

            }
        }
    }

    handleCardNavigation = () => {
        if (this.state.cardNavigation) {
            this.fetchChartData()
        } else {
            this.handleOnClick(this.state.currentPage - 1)
        }
    }

    handleLanguageChange = (value) => {
        this.setState({ filterValue: value })
        this.handleOnClick(1, "", value)
    }

    fetchLanuagePairButtons() {
        const { classes } = this.props;

        return (
            <div>
                <Button color={this.state.filterValue === "domain" ? "primary" : "light"} style={ this.state.filterValue === "domain" ? {backgroundColor: "#E8F5F8"} : {} } size="medium" variant="outlined" className={classes.backButton} onClick={() => this.handleLanguageChange("domain")}>Domain</Button>
                <Button color={this.state.filterValue === "source" ? "primary" : "light"} style={ this.state.filterValue === "source" ? {backgroundColor: "#E8F5F8"} : {} }size="medium" variant="outlined" className={classes.backButton} onClick={() => this.handleLanguageChange("source")}>Source</Button>
                <Button color={this.state.filterValue === "collectionMethod" ? "primary" : "light"} style={ this.state.filterValue === "collectionMethod" ? {backgroundColor: "#E8F5F8"} : {} } size="medium" variant="outlined" className={classes.backButton} onClick={() => this.handleLanguageChange("collectionMethod")}>Collection Method</Button>
           
            </div>
        )
    }

    render() {
        console.log(this.state.dataSetValues)
        const { classes } = this.props;
      
        this.getData()

        return (
            <>
                <ChartRenderHeader
                    handleOnClick={this.handleOnClick.bind(this)}
                    currentPage={this.state.currentPage}

                />
                <Container className={classes.container}>

                    <div className={classes.card}>
                        <div className={classes.cardHeader}>
                            <div>
                                
                                    <div className={classes.cardHeaderContainer}>
                                    {(this.state.cardNavigation || this.state.currentPage !== 0) && this.state.dataSetValues.length > 0 &&
                                    <>
                                        <Button color="light" size="medium" variant="contained" className={classes.backButton} startIcon={<BackIcon />} onClick={() => this.handleCardNavigation()}>Back</Button>
                                    
                                        <div className={classes.seperator}></div>
                                        </>
                                    }
                                    </div>
                                
                            </div>

                            <div className={classes.title}>
                                <Typography value="" variant="h6">

                                    {this.state.title}
                                </Typography>
                            </div>
                        </div>
                        <div className={classes.langPairButtons}>
                            {this.state.currentPage === 1 && this.fetchLanuagePairButtons()}
                        </div>
                        <Paper elevation={3} className={classes.paper}>

                            <ResponsiveContainer width="95%" height={450}>
                                <BarChart width={900} height={450} data={this.state.dataSetValues} maxBarSize={100} >
                                    <XAxis dataKey="label"
                                        textAnchor={isMobile ? "end" : "middle"}
                                        tick={{ angle: isMobile ? -60 : 0 }} height={isMobile ? 100 : 60}
                                        interval={0}
                                        position="insideLeft"

                                    />
                                    <YAxis type="number" dx={0} />
                                    <CartesianGrid horizontal={true} vertical={false} textAnchor={"middle"} />

                                    <Tooltip />
                                    <Bar dataKey="value" radius={[4, 4, 0, 0]} maxBarSize={30} onClick={(event) => { this.handleOnClick(this.state.currentPage + 1, event) }} className={this.state.currentPage !== 2 && classes.cursor}>

                                        {
                                            this.state.dataSetValues.length > 0 && this.state.dataSetValues.map((entry, index) => {
                                                const color = colors[index < 9 ? index : index % 10]
                                                return <Cell key={index} fill={`#${color}`} />;
                                            })
                                        }
                                    </Bar>
                                </BarChart>
                            </ResponsiveContainer>

                        </Paper>
                    </div>
                </Container>

            </>
        )

    }

}

const mapStateToProps = state => ({

    dataValues: state.dataValues
});

const mapDispatchToProps = dispatch => bindActionCreators(
    {
        APITransport,
      
    },
    dispatch
);

export default withRouter(withStyles(ChartStyles)(connect(mapStateToProps, mapDispatchToProps)(ChartRender)));
