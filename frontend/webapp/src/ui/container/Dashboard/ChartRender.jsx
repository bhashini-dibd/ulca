import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useParams } from "react-router-dom";
import Theme from "../../theme/theme-default";
import { withStyles, Typography, MuiThemeProvider, Paper, Button } from "@material-ui/core";
import ChartStyles from "../../styles/Dashboard";
import { ResponsiveContainer, BarChart, Bar, Cell, CartesianGrid, XAxis, YAxis, Tooltip,} from 'recharts';
import Select from 'react-select';
import APITransport from "../../../redux/actions/apitransport/apitransport";
import FetchLanguageDataSets from "../../../redux/actions/api/Dashboard/languageDatasets";
import { isMobile } from 'react-device-detect';
import {ArrowBack} from '@material-ui/icons';
import Header from '../../components/common/Header';
import Dataset from "../../../configs/DatasetItems";
import authenticate from '../../../configs/authenticate';

var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]


const ChartRender = (props) => {
        const [selectedOption, setSelectedOption]   	= 	useState(Dataset[0]);
        const [title, setTitle]                     	= 	useState("Number of parallel sentences per language with English");
	const [filterValue, setFilterValue]		=	useState("domains");
	const [selectedLanguage,setSelectedLanguage]	=	useState("");
	const [page, setPage]				= 	useState(0);
        const history                               	= 	useHistory();
        const dispatch                             	= 	useDispatch();
        const DashboardReport                       	= 	useSelector( (state) => state.dashboardReport);
        const { classes }                           	= 	props;
        const options 				    	= 	Dataset;
	useEffect(() => {
		fetchChartData(selectedOption.value, "languagePairs", [])
		if (authenticate()) {
			history.push(`${process.env.PUBLIC_URL}/private-dashboard`)
		} 
		else {
			localStorage.clear()
			history.push(`${process.env.PUBLIC_URL}/dashboard`)

		}
	}, []);

	const fetchChartData = (dataType, value, criterions) =>{
		const userObj 		= 	new FetchLanguageDataSets(dataType, value, criterions);
		dispatch(APITransport(userObj));
		
	} 

	const customStyles = {
		option	: (provided, state) => ({
			...provided,
			borderColor	:	"green",
			color		: 	'black',
			padding		: 	20,
			background	: 	state.isSelected && "#c7c6c68a !important",

		}),
		control: (base, state) => ({
		...base,
		// This line disable the blue border
			borderColor	:	"#392C71",
			border 		: 	"1px solid rgba(57, 44, 113, 0.5)",
			boxShadow	: 	state.isFocused ? 0 : 0,
			fontFamily	: 	"Source Sans Pro, Arial, sans-serif "
		})
	}

	const handleOnClick= (value, event, filter) =>  {
		// if (event && event.hasOwnProperty("label") && event.label === "Others") {
		//     let others = this.props.dataValues.slice(7, this.props.dataValues.length)
		//     this.setState({
		// 	dataSetValues: others,
		// 	cardNavigation: true
		//     })
		// } else {
		    switch (value) {
			case 1:
				
				fetchChartData(selectedOption.value, filter ? filter : filterValue, [{ "type": "PARAMS", "sourceLanguage": { "type": "PARAMS", "value": "en" }, "targetLanguage": { "type": "PARAMS", "value": selectedLanguage ? selectedLanguage : event && event.hasOwnProperty("label") && event.label } }])
				setPage(value)
				setTitle( `English-${selectedLanguage ? selectedLanguage : event && event.hasOwnProperty("label") && event.label }  parallel corpus - Grouped by ${(filter === "domains") ? "Domains" : (filter === "source") ? "Source" : filter === "collectionMethod_collectionDescriptions" ? "Collection Method" : "Domains"}`)
				setSelectedLanguage(selectedLanguage ? selectedLanguage : event && event.hasOwnProperty("label") && event.label)
				
				break;
			case 2:
				fetchChartData(selectedOption.value, filterValue === "collectionMethod_collectionDescriptions" ? "domains" : "collectionMethod_collectionDescriptions", [{ "type": "PARAMS", "sourceLanguage": { "type": "PARAMS", "value": "en" }, "targetLanguage": { "type": "PARAMS", "value": selectedLanguage } }, { "type": "PARAMS", "value": event && event.hasOwnProperty("label") && event.label }])
				setPage(value)
				setFilterValue('domains')
				setTitle( `English-${selectedLanguage} parallel corpus`)
				break;
			case 0:
				fetchChartData(selectedOption.value, "languagePairs", [])
				setPage(value)
				setFilterValue('domains')
				setTitle("English-Indic language parallel corpus")
				setSelectedLanguage("")
				
				
				break;
			default:
	
		    }
		
	    }

	const handleLanguageChange = (value) => {
		setFilterValue(value)
		setTitle( `English-${selectedLanguage }  parallel corpus - Grouped by ${(value === "domains") ? "Domains" : (value === "source") ? "Source" : value === "collectionMethod_collectionDescriptions" ? "Collection Method" : "Domain"}`)
		handleOnClick(1, "", value)
	    }
	const  handleCardNavigation = () => {

		    handleOnClick(page - 1)
	    }

	const fetchLanuagePairButtons = () => {
		return (
		    <div className={classes.filterButton}>
			<Button  color={filterValue ==="domains" ? "primary" :"default" } style={ filterValue === "domains" ? {backgroundColor: "#E8F5F8"} : {} } size="medium" variant="outlined" className={classes.backButton} onClick={() => handleLanguageChange("domains")}>Domain</Button>
			{/* <Button  color={filterValue === "source" ? "primary":"default"} style={ filterValue === "source" ? {backgroundColor: "#E8F5F8"} : {} }size="medium" variant="outlined" className={classes.backButton} onClick={() => handleLanguageChange("source")}>Source</Button> */}
			<Button  color={filterValue === "collectionMethod_collectionDescriptions" ?"primary" :"default"} style={ filterValue === "collectionMethod_collectionDescriptions" ? {backgroundColor: "#E8F5F8"} : {} } size="medium" variant="outlined" className={classes.backButton} onClick={() => handleLanguageChange("collectionMethod_collectionDescriptions")}>Collection Method</Button>
		   
		    </div>
		)
	    }

	const handleSelectChange = (dataSet) =>{
		setSelectedOption( dataSet)
		switch (dataSet.value) {
			case 'parallel-dataset':
				 setTitle("Number of parallel sentences per language with English")
				 break;
			case 'monolingual-dataset':
				 setTitle('Number of sentences per language')
				 break;
			case 'ASR/TTS-dataset':
				 setTitle("Numer of audio hours per language")
				 break;
			case 'OCR-dataset':
				setTitle("Numer of images per script")
				break;
			default:
				setTitle("")
		}

		
	}
     return (
        <>
            	{ !authenticate() &&
			<MuiThemeProvider theme={Theme}>
				<Header /><br /><br /><br /><br />
			</MuiThemeProvider>
        	}
                <div className	=	{classes.container}>
			{/* <div className={classes.breadcrum}>
				<BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
			</div> */}
			<Paper elevation  = {3} className  = {classes.paper}>
			
				<div className  =	{classes.titleBar}>
					{page!==0 && <><Button color="light" size="medium" variant="contained" className={classes.backButton} startIcon={<ArrowBack />} onClick={() => handleCardNavigation()}>Back</Button>
					<div className={classes.seperator}></div></>}
					
					<Typography 	variant   	=	"h5" component = "h3" 
							className 	= 	{classes.Typography}> Dataset Type :	</Typography>
					<Select 	className 	= 	{classes.select} 
							styles 		= 	{customStyles} color= "primary"
							value   	=	{selectedOption}
							onChange	=	{(value)=>{handleSelectChange(value)}}
							options		=	{options}
					/>
					{page === 1 && fetchLanuagePairButtons()}
					{/* <Button color={"primary" } size="medium" variant="outlined" className={classes.filterButton} onClick={() => this.handleLanguageChange("domain")}><FilterList className ={classes.iconStyle}/>Filter</Button>
					<Button color={"primary" } size="medium" variant="outlined" className={classes.filterButtonIcon} onClick={() => this.handleLanguageChange("domain")}><FilterList className ={classes.iconStyle}/></Button> */}
				</div>
				<div className={classes.title}>
					<Typography value="" variant="h6"> {title} </Typography>
				</div>
				<ResponsiveContainer width = "95%" height = {450}>
					<BarChart width = {900} height 	= 	{450} data={DashboardReport} maxBarSize = {100} >
						<XAxis 	dataKey 	= 	"label"
							textAnchor	=	{isMobile ? "end" : "middle"}
							tick		=	{{ angle: isMobile ? -60 : 0 }} 
							height		=	{isMobile ? 100 : 60}
							interval	=	{0}
							position	=	"insideLeft"
						/>
						<YAxis type="number" dx	=	{0} />
						<CartesianGrid horizontal = {true} vertical = {false} textAnchor = {"middle"} />
						<Tooltip cursor={{fill: 'none'}}/>
						<Bar dataKey = "value" cursor ="pointer" radius = {[4, 4, 0, 0]} maxBarSize = {30} onClick={(event) => { handleOnClick(page + 1, event) }}>
							{
								DashboardReport.length > 0 && DashboardReport.map((entry, index) => {
									const color 	= 	colors[index < 9 ? index : index % 10]
									return <Cell key = {index} fill = {`#${color}`} />;
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
