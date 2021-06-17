import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useParams } from "react-router-dom";
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
import Theme from "../../theme/theme-default";
var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]


const ChartRender = (props) => {
        const [selectedOption, setSelectedOption]   	= 	useState(Dataset[0]);
        const [title, setTitle]                     	= 	useState("Number of parallel dataset per language with English");
	const [filterValue, setFilterValue]		=	useState("domains");
	const [selectedLanguage,setSelectedLanguage]	=	useState("");
	const [selectedLanguageName,setSelectedLanguageName]	=	useState("");
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

	const fetchParams = (event ) => {
		var sourceLanguage = ""
		let targetLanguage = ""
		if(selectedOption.value ===	"parallel-corpus"){
			 sourceLanguage =  	{ "type": "PARAMS", "value": "en" };
			targetLanguage 	=	{ "type": "PARAMS", "value": (selectedLanguage ? selectedLanguage : event && event.hasOwnProperty("_id") && event._id)}
		}
		else{
			sourceLanguage =  	{ "type": "PARAMS", "value": (selectedLanguage ? selectedLanguage : event && event.hasOwnProperty("_id") && event._id)};
			targetLanguage 	=	{ "type": "PARAMS", "value": "" };
			
		}
		setSelectedLanguage(selectedLanguage ? selectedLanguage :event && event.hasOwnProperty("_id") && event._id)
		setSelectedLanguageName(selectedLanguageName ? selectedLanguageName : event && event.hasOwnProperty("label") && event.label)
		return ([{ "type": "PARAMS", "sourceLanguage" : sourceLanguage,"targetLanguage" : targetLanguage}])
	}
	
	const fetchNextParams = (eventValue ) => {
		var sourceLanguage = ""
		let targetLanguage = ""
		let event			=	{ "type": "PARAMS", "value": eventValue && eventValue.hasOwnProperty("_id") && eventValue._id } 
		if(selectedOption.value ===	"parallel-corpus"){
			 sourceLanguage =  	{ "type": "PARAMS", "value": "en" };
			targetLanguage 	=	{ "type": "PARAMS", "value": selectedLanguage}
			
		}
		else{
			sourceLanguage =  	{ "type": "PARAMS", "value": selectedLanguage};
			targetLanguage 	=	{ "type": "PARAMS", "value": "" };
			
		}
		setSelectedLanguage(selectedLanguage ? selectedLanguage :event && event.hasOwnProperty("_id") && event._id)
		setSelectedLanguageName(selectedLanguageName ? selectedLanguageName : event && event.hasOwnProperty("label") && event.label)
		return ([{ "type": "PARAMS", "sourceLanguage" : sourceLanguage,"targetLanguage" : targetLanguage, event : event}])
	}

	const handleOnClick= (value, event, filter) =>  {

		    switch (value) {
			case 1:
				// fetchChartData(selectedOption.value, filter ? filter : filterValue, [{ "type": "PARAMS", "sourceLanguage": { "type": "PARAMS", "value": "en" }, "targetLanguage": { "type": "PARAMS", "value": selectedLanguage ? selectedLanguage : (event && event.hasOwnProperty("_id")) ? event._id : "" } }])
				
				fetchChartData(selectedOption.value,filter ? filter : filterValue, fetchParams(event ))
				setPage(value)
				setTitle( `English-${selectedLanguageName ? selectedLanguageName : event && event.hasOwnProperty("label") && event.label }  ${selectedOption.label} - Grouped by ${(filter === "domains") ? "Domain" : (filter === "source") ? "Source" : filter === "collectionMethod_collectionDescriptions" ? "Collection Method" : "Domain"}`)
				
				break;
			case 2:
				fetchChartData(selectedOption.value, filterValue === "collectionMethod_collectionDescriptions" ? "domains" : "collectionMethod_collectionDescriptions", fetchNextParams(event))
				setPage(value)
				setFilterValue('domains')
				setTitle( `English-${selectedLanguageName} ${selectedOption.label} `)
				
				break;
			case 0:
				fetchChartData(selectedOption.value, "languagePairs", [])
				setPage(value)
				setFilterValue('domains')
				setTitle(`English-Indic language ${selectedOption.label} `)
				setSelectedLanguage("")
				setSelectedLanguageName("")
				
				
				break;
			default:
	
		    }
		
	    }

	const handleLanguageChange = (value) => {
		setFilterValue(value)
		setTitle( `English-${selectedLanguageName }  ${selectedOption.value}- Grouped by ${(value === "domains") ? "Domain" : (value === "source") ? "Source" : value === "collectionMethod_collectionDescriptions" ? "Collection Method" : "Domain"}`)
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
		fetchChartData(dataSet.value, "languagePairs", [])
		setSelectedOption( dataSet)
		switch (dataSet.value) {
			case 'parallel-corpus':
				 setTitle("Number of parallel sentences per language with English")
				 break;
			case 'monolingual-corpus':
				 setTitle('Number of sentences per language')
				 break;
			case 'asr-corpus':
				 setTitle("Number of audio hours per language")
				 break;
			case 'ocr-corpus':
				setTitle("Number of images per script")
				break;
			default:
				setTitle("")
		}

		
	}
     return (
        <MuiThemeProvider theme={Theme}>
            	{ !authenticate() &&
			
				<><Header /><br /><br /><br /><br /> </>
        	}
                <div className	=	{classes.container}>
			{/* <div className={classes.breadcrum}>
				<BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
			</div> */}
			<Paper elevation  = {3} className  = {classes.paper}>
			
				<div className  =	{classes.titleBar}>
					{page!==0 && <><Button color="primary" size="medium" variant="contained" className={classes.backButton} startIcon={<ArrowBack />} onClick={() => handleCardNavigation()}>Back</Button>
					<div className={classes.seperator}></div></>}
					
					<Typography 	variant   	=	"h5" 
							> Dataset Type :	</Typography>
					<Select 	className 	= 	{classes.select} 
							styles 		= 	{customStyles} color= "primary"
							value   	=	{selectedOption}
							onChange	=	{(value)=>{handleSelectChange(value)}}
							options		=	{options}
							isDisabled	= {page!== 0 ? true : false}
					/>
					{page === 1 && fetchLanuagePairButtons()}
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
						<Bar dataKey = "value" cursor ="pointer" radius = {[4, 4, 0, 0]} maxBarSize = {65} onClick={(event) => { handleOnClick(page + 1, event) }}>
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
        </MuiThemeProvider>
    )


}



export default withStyles(ChartStyles(Theme))(ChartRender);
