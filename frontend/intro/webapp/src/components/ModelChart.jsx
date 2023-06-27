import React, { useEffect, useState } from "react";
import { withStyles, Typography, Paper, Button,TextField } from "@material-ui/core";
import ChartStyles from "../styles/Dashboard";
import { ResponsiveContainer, BarChart, Bar, Cell, XAxis, LabelList, YAxis,Label, Tooltip } from 'recharts';
import FetchLanguageDataSets from "../actions/FetchLanguageDataSets";
import { ArrowBack } from '@material-ui/icons';
import {DatasetItems,Language} from "../configs/DatasetItems";
import TitleBar from "./TitleBar";
import Autocomplete from '@material-ui/lab/Autocomplete';
import image from "../img/shape2.svg"
import ResponsiveChartContainer from "./ResponsiveChartContainer"
var colors = ["188efc", "7a47a4", "b93e94", "1fc6a4", "f46154", "d088fd", "f3447d", "188efc", "f48734", "189ac9", "0e67bd"]


const ChartRender = (props) => {
	const [selectedOption, setSelectedOption] = useState(DatasetItems[0]);
	const [count, setCount] = useState(0);
	const [total, setTotal] = useState(0);
	const [axisValue, setAxisValue] = useState({yAxis:"Count", xAxis:"Task"});
	const [title, setTitle] = useState("Number of Models");
    const [selectedType, setSelectedType] = useState("");
    const [selectedTypeName, setSelectedTypeName] = useState("");
	const [filterValue, setFilterValue] = useState("language");
  const [data, setData] = useState([]);
  const [dataValue, setDataValue] = useState("");

	const [page, setPage] = useState(0);
	const [sourceLanguage, setSourceLanguage] = useState(
        { value: 'en', label: 'English' }
    );

  useEffect(() => {
    fetchChartData("model","", "")
      
  }, [selectedOption,sourceLanguage]);

	const { classes } = props;
	const options = DatasetItems;
	

  const fetchChartData = async (dataType, value, criterions) => {
    let apiObj = new FetchLanguageDataSets(dataType, value, criterions);
        fetch(apiObj.apiEndPoint(), {
          method: 'post',
          body: JSON.stringify(apiObj.getBody()),
          headers: apiObj.getHeaders().headers
        }).then(async response => {
          const rsp_data = await response.json();
          if (!response.ok) {
            return Promise.reject('');
          } else {
            let res = rsp_data.data.sort((a,b)=>b.value-a.value)
            setData(res)
			let total = rsp_data.data.reduce((acc,rem)=> acc= acc+Number(rem.value),0)
			setCount(total%1===0 ? total : total.toFixed(3))
			setTotal(rsp_data.count)
          }
        }).catch((error) => { 
        }); 
    }

	const handleLanguagePairChange = (value, property) => {
		let sLang =  Language.filter(val => val.label ===value )[0]
		if(sLang){
		// fetchChartData(selectedOption.value, "", [{"field": "sourceLanguage","value":  sLang.value}])
        setSourceLanguage(sLang);
		}


    };

	const renderTexfield = (id, label, value, options, filter) => {
        let labels = Language.map(lang => lang.label)
        return (
            <Autocomplete
				className={classes.titleDropdown}
				
                value={sourceLanguage.label}
                id="source"
                options={labels}
                onChange={(event, data) => handleLanguagePairChange(data, 'source')}
                renderInput={(params) => <TextField fullWidth {...params}  variant="standard"
                    // error={srcError}
                    // helperText={srcError && "This field is mandatory"}
                />}
            />


        )
    }

	

	const fetchParams = (event) => {
        setSelectedType(selectedType ? selectedType : event && event.hasOwnProperty("_id") && event._id)
		setSelectedTypeName(selectedTypeName ? selectedTypeName : event && event.hasOwnProperty("label") && event.label)
		return ([{ "field":"type", "value":  (event && event.hasOwnProperty("_id") && event._id)? event._id : selectedType}])
	}

	const handleOnClick = (value, event, filter) => {
		switch (value) {
			case 1:
				fetchChartData("model", filter ? filter : filterValue, fetchParams(event))
				setFilterValue(filter ? filter : filterValue)
                
				handleSelectChange(selectedOption, event, filter, value)
				setPage(value)

				break;
			case 0:
				fetchChartData("model","", "")
				setPage(value)
				setFilterValue('language')
				handleSelectChange(selectedOption, "", "", value)
				setSelectedType("")
				setSelectedTypeName("")
				break;
			default:

		}

	}
	const handleLanguageChange = (value) => {
		setFilterValue(value)
		handleOnClick(1, "", value)
	}
	const handleCardNavigation = () => {

		handleOnClick(0)
	}



	const fetchFilterButtons = () => {
		return (
			<div className={classes.filterButton}>
                <Typography className={classes.fiterText} value="" variant="body1"> Filter By </Typography>
				<Button color={filterValue === "language" ? "primary" : "default"}  size="small" variant="outlined" className={classes.backButton} onClick={() => handleLanguageChange("language")}>Language</Button>
				<Button color={filterValue === "submitter" ? "primary" : "default"}  size="small" variant="outlined" onClick={() => handleLanguageChange("submitter")}>Submitter</Button>

			</div>
		)
	}

	const CustomizedAxisTick =(props)=> {
		
		const { x, y, payload } = props;
	
		return (
		  <g transform={`translate(${x},${y})`}>
			<text x={0} y={0} dy={16} textAnchor="end" fill="#666" transform="rotate(-35)">
			 {payload.value && (payload.value.substr(0,14)+ (payload.value.length> 14? "...":""))}
			</text>
		  </g>
		);
	  
	}



	const handleSelectChange = (dataSet, event, filter, page) => {
		setSelectedOption(dataSet)
		
				if (page === 0) {
					setTitle("Number of Models")
					selectedOption.value !== dataSet.value && fetchChartData(dataSet.value, "", [{"field": "sourceLanguage","value": sourceLanguage.value}])
					setAxisValue({xAxis:"Task",yAxis:"Count"})
					

				} else if (page === 1) {
					setTitle(`Number of ${selectedTypeName ? selectedTypeName : event.label} models - Grouped by ${filter ? filter : filterValue}`)
					setAxisValue({yAxis:("Count"),xAxis:(filter === "language") ? "Language" : (filter === "submitter") ?  "Submitter" : "Language"})
				} 
	}
	return (
		<section className="section dashboard" style={{background:"white"}}>
        <div class="shape1"><img src={image} alt="shapes"/></div>
       
                
		
                    
                    <div class="text-center">
                        <h2 class="text-center text-black mt-3">Model Dashboard</h2>
                        <p class="ft-20">ULCA hosts a catalog of models in Indian languages</p>
                    <div class="join">
                      <a class="bh-btn-primary" href={`${process.env.REACT_APP_BASE_URL}/ulca/model/explore-models`} target="_self" rel="noopener noreferrer">Explore ULCA Models </a>
                  </div>
                   
                   
                    
			
			<div className={classes.container}>
				<Paper elevation={0} className={classes.paper}>	
				
        <TitleBar selectedOption=	{selectedOption}
				handleSelectChange=	{handleSelectChange}
				options		=	{""}
				isDisabled	=	{page !== 0 ? true : false}
				page		= 	{page}
				count 		= 	{total}
				title="Model"
				>
				{page === 1 && fetchFilterButtons() }
				
			</ TitleBar>
			<div className={classes.iconStyle}  style={{ display: "flex", flexDirection: "column", alignItems: "flex-start" }}>
					 	<><Button size="small" color="primary" className={classes.backButton} style={page === 0 ? {visibility:"hidden"}:{}}  onClick={() => handleCardNavigation()}>Reset</Button></>
						 {(selectedOption.value ==="model" && page===0 )? 
						<div className= {classes.titleStyle}>
						
						<Typography className={classes.titleText} value="" variant="h6"> {title} </Typography>
						<div className={classes.dropDownStyle}>
						{renderTexfield("select-source-language", "Source Language *")}
						<Typography  value="" variant="h6">({count ? (new Intl.NumberFormat('en').format(count)):0})</Typography>
						</div> 
						</div>
						:
						<Typography className={classes.titleText} value="" variant="h6"> {title} <span>({count ? (new Intl.NumberFormat('en').format(count)):0})</span></Typography>}	
					</div>
					
					<div className={classes.title}>
						<ResponsiveChartContainer  >
							<BarChart width={900} height={400} data={data} fontSize="14px" fontFamily="Roboto" maxBarSize={100} >

								<XAxis dataKey="label"
									textAnchor={"end"}
									tick={<CustomizedAxisTick/>}
									height={130}
									interval={0}
									position="insideLeft"
									type="category"
								
								>
									<Label value= {axisValue.xAxis} position= 'insideBottom' fontWeight="bold" fontSize={16} ></Label>
								</XAxis>
								<YAxis padding={{ top: 80 }} tickInterval={10} allowDecimals={false} type="number" dx={0} tickFormatter={(value) => new Intl.NumberFormat('en', { notation: "compact" }).format(value)}><Label value= {axisValue.yAxis} angle= {-90} position= 'insideLeft' fontWeight="bold" fontSize={16}></Label></YAxis>


								<Tooltip contentStyle={{fontFamily:"Roboto", fontSize:"14px"}} formatter={(value) => new Intl.NumberFormat('en').format(value)} cursor={{ fill: 'none' }} />
								<Bar margin={{ top: 140, left: 20, right: 20, bottom: 20 }} dataKey="value" cursor="pointer" radius={[8, 8, 0, 0]} maxBarSize={65} onClick={(event) => { handleOnClick(page + 1, event) }}>
									<LabelList
										formatter={(value) => new Intl.NumberFormat('en').format(value)} cursor={{ fill: 'none' }}
										position="top"
										
										dataKey="value"
										fill="black"
										style={{textAnchor:"start"}}
										angle={-30}
										clockWise={4}
									/>
									{
										data.length > 0 && data.map((entry, index) => {
											const color = colors[index < 9 ? index : index % 10]
											return <Cell key={index} fill={`#${color}`} />;
										})
									}
								</Bar>
							</BarChart>
						</ResponsiveChartContainer>
					</div>

				</Paper> 
				</div>
            </div>
               
       
    </section>		
	)
}
export default withStyles(ChartStyles())(ChartRender);
