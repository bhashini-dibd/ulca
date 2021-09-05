import {
    Grid,
    Typography,
    Button,
    TextField,
    Checkbox,
    FormControlLabel,
    Menu,
    MenuItem,
    Paper
} from '@material-ui/core';
import DownIcon from '@material-ui/icons/ArrowDropDown';
import SearchModels from "./SearchModels";
import { withStyles, MuiThemeProvider } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import Snackbar from '../../../components/common/Snackbar';
import BreadCrum from '../../../components/common/Breadcrum';
import UrlConfig from '../../../../configs/internalurlmapping';
import { PageChange } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import C from "../../../../redux/actions/constants";
import { useDispatch, useSelector } from "react-redux";
import { useState, useEffect, useRef } from 'react';
import DownloadDatasetRecords from "../../DataSet/DatasetSeatch/DownloadDatasetRecords";
import RequestNumberCreation from "../../DataSet/DatasetSeatch/RequestNumberCreation";
import { useHistory, useParams } from 'react-router';
import Autocomplete from '@material-ui/lab/Autocomplete';
import MultiAutocomplete from '../../../components/common/Autocomplete';
import { Language, FilterBy } from '../../../../configs/DatasetItems';
import SubmitSearchRequest from '../../../../redux/actions/api/DataSet/DatasetSearch/SubmitSearchRequest';
import DatasetType from '../../../../configs/DatasetItems';
import { ModelTask } from '../../../../configs/DatasetItems';
import getLanguageLabel from '../../../../utils/getLabel';
import SearchModel from '../../../../redux/actions/api/Model/ModelSearch/SearchModel';
import APITransport from '../../../../redux/actions/apitransport/apitransport';
import Header from '../../../components/common/Header';
import aunthenticate from '../../../../configs/authenticate';
import NewSearchModel from './NewSearchModel';
import Theme from '../../../theme/theme-default';
import Footer from "../../../components/common/Footer";

const StyledMenu = withStyles({
})((props) => (
    <Menu
        elevation={0}
        getContentAnchorEl={null}
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        transformOrigin={{
            vertical: 'top',
            horizontal: '',
        }}
        {...props}
    />
));
const Benchmark = (props) => {
    const { classes } = props;
    const url = UrlConfig.dataset;
    const urlMySearch = UrlConfig.mySearches;
    const dispatch = useDispatch();
    const param = useParams();
    const history = useHistory();
    const searchFilter = useSelector(state => state.searchFilter);
    const [languagePair, setLanguagePair] = useState({
        source: searchFilter.source,
        target: searchFilter.target
    });
    // const [filterBy, setFilterBy] = useState({
    //     domain: [],
    //     source: [],
    //     collectionMethod: []
    // });
    const [filterBy, setFilterBy] = useState({
        domain: '',
        source: '',
        collectionMethod: ''
    });

    const [datasetType, setDatasetType] = useState(searchFilter.type)

    // const [modelTask, setModelTask]=useState({
    //     'translation': true
    // })
    const [count, setCount] = useState(0);
    const [urls, setUrls] = useState({
        downloadSample: '',
        downloadAll: ''
    })

    const makeSubmitAPICall = (src, tgt, type) => {
        const Dataset = Object.keys(type)[0]
        const apiObj = new SearchModel(Dataset, src, tgt)
        dispatch(APITransport(apiObj));
    }


    useEffect(() => {
        if (aunthenticate()) {
            const type = searchFilter.type
            if (type['translation'] !== undefined) {
                const source = getValueForLabel(languagePair.source).value;
                const target = languagePair.target.value;
                makeSubmitAPICall(source, target, type)
            } else {
                const source = languagePair.target !== "" ? languagePair.target.value : "";
                makeSubmitAPICall(source, "", type)
            }
        }
    }, [])

    const handleCheckboxChange = (event) => {
        setState({ ...state, [event.target.name]: event.target.checked });
    };
    const handleLanguagePairChange = (value, property) => {
        setLanguagePair({ ...languagePair, [property]: value });

        if (property === 'source')
            setSrcError(false)
        else
            setTgtError(false)
    };
    const handleFilterByChange = (value, property) => {
        setFilterBy({ ...filterBy, [property]: value });
    };
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: '',
        variant: 'success'
    })
    const [state, setState] = useState({
        checkedA: false,
        checkedB: false,
        checkedC: false,

    });

    const modelLabel = ModelTask.filter(task => task.value === Object.keys(searchFilter.type)[0])[0].label
    const [label, setLabel] = useState(modelLabel)
    const [srcError, setSrcError] = useState(false)
    const [tgtError, setTgtError] = useState(false)
    const { params, srno } = param


    const handleDatasetClick = (property) => {
        // history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/initiate/-1`)
        clearfilter()
        setDatasetType({ [property]: true })
        // setModelTask({ [property]: true })
        setSrcError(false)
        setTgtError(false)
    }
    const getLabel = () => {
        if (datasetType['translation'])
            return "Target Language *"
        // else if (datasetType['ocr-corpus'])
        //     return "Script *"
        else
            return "Language"
    }

    const getTitle = () => {
        if (datasetType['translation'])
            return "Select Language Pair"
        // else if (datasetType['ocr-corpus'])
        //     return "Select Script"
        else
            return "Select Language"
    }
    const clearfilter = () => {
        setFilterBy({
            domain: "",
            source: "",
            collectionMethod: ""
        });
        setLanguagePair({
            source: "",
            target: ""
        });
    }



    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false })
    }
    const getValueForLabel = (label) => {
        return Language.filter(val => val.label === label)[0]

    }
    const getFilterValueForLabel = (data, label) => {
        //  if (data === 'domain') {
        return (FilterBy[data].filter(val => val.label === label)[0])
        //  }
        // else if (data === 'collectionMethod') {
        //     return (FilterBy.collectionMethod.filter(val => val.label === label)[0])
        // }
    }

    const handleSubmitBtn = () => {
        // let tgt = languagePair.target.map(trgt => trgt.value)
        let tgt = languagePair.target ? languagePair.target.value : ""
        let domain = "All"
        let submitter = "All"
        // let domain = filterBy.domain && [getFilterValueForLabel('domain', filterBy.domain).value]
        // let collectionMethod = filterBy.collectionMethod && [getFilterValueForLabel('collectionMethod', filterBy.collectionMethod).value]

        if (datasetType['translation']) {
            if (languagePair.source && languagePair.target) {
                let source = languagePair.source ? getValueForLabel(languagePair.source).value : ""
                makeSubmitAPICall(source, tgt, datasetType, domain, submitter)
                //  makeSubmitAPICall(languagePair.source, tgt, domain, collectionMethod, datasetType)
            }

            else if (!languagePair.source && !languagePair.target) {
                setSrcError(true)
                setTgtError(true)
            }

            else if (!languagePair.source)
                setSrcError(true)
            else if (!languagePair.target)
                setTgtError(true)
        }
        else {
            // if (!languagePair.target)
            //     setTgtError(true)
            // else {
            makeSubmitAPICall(tgt, null, datasetType, domain, submitter)
            // }

        }


    }
    const handleChange = (label, value) => {
        setLabel(label)
        handleDatasetClick(value)
    };
    const [anchorEl, openEl] = useState(null);
    const handleClose = () => {
        openEl(false)
    }

    const renderDatasetButtons = () => {
        let filterByOptions = FilterBy['domain'].map(data => data.label)
        return (
            <>
                <Button className={classes.menuStyle}
                    // disabled={page !== 0 ? true : false}
                    color="inherit"
                    fullWidth
                    onClick={(e) => openEl(e.currentTarget)}
                    variant="text">
                    <Typography variant="body1">
                        {label}
                    </Typography>
                    <DownIcon />
                </Button>
                <StyledMenu id="data-set"
                    anchorEl={anchorEl}

                    open={Boolean(anchorEl)}
                    onClose={(e) => handleClose(e)}
                    className={classes.styledMenu1}
                >
                    {
                        ModelTask.map(menu => {

                            return <MenuItem
                                value={menu.value}
                                name={menu.label}
                                className={classes.styledMenu}
                                onClick={() => {
                                    handleChange(menu.label, menu.value)
                                    handleClose()
                                }}
                            >
                                <Typography variant={"body1"}>
                                    {menu.label}
                                </Typography>
                            </MenuItem>
                        })
                    }
                </StyledMenu></>

            // )
            // }
            // )
        )
    }

    const renderFilterByOptions = (id, options, filter, value, label) => {
        return (
            <MultiAutocomplete
                id={id}
                options={options}
                filter={filter}
                value={value}
                handleOnChange={handleFilterByChange}
                label={label}
            />

        )
    }
    // const renderFilterByfield = (id, label, value, filter) => {
    //     return (
    //         <TextField className={classes.subHeader}
    //             fullWidth
    //             select
    //             id={id}
    //             label={label}
    //             value={value}
    //             onChange={(event) => handleFilterByChange(event.target.value, id)}
    //         >
    //             {filter.map((option) => (
    //                 <MenuItem key={option.value} value={option.value}>
    //                     {option.label}
    //                 </MenuItem>
    //             ))}
    //         </TextField>
    //     )
    // }
    const renderFilterByfield = (id, label, value, filter) => {
        let filterByOptions = ["All"]
        return (
            <Autocomplete
                disableClearable
                value="All"
                id={id}
                options={filterByOptions}
                //  onChange={(event, data) => handleFilterByChange(data, id)}
                renderInput={(params) => <TextField fullWidth {...params} label={label} variant="standard"
                />}
            />
        )
    }
    const renderTexfield = (id, label, value, options, filter) => {
        let labels = Language.map(lang => lang.label)
        return (
            <Autocomplete
                value={languagePair.source ? languagePair.source : null}
                id="source"
                options={labels}
                onChange={(event, data) => handleLanguagePairChange(data, 'source')}
                renderInput={(params) => <TextField fullWidth {...params} label="Source Language *" variant="standard"
                    error={srcError}
                    helperText={srcError && "This field is mandatory"}
                />}
            />


        )
    }
    const renderCheckBox = (name, color, label) => {
        return (
            <FormControlLabel
                control={
                    <Checkbox
                        checked={state[name]}
                        onChange={handleCheckboxChange}
                        name={name}
                        color={color}
                    />
                }
                label={label}
            />
        )
    }
    const getTargetLang = () => {
        return Language.filter(lang => lang.label !== languagePair.source)
    }
    const renderclearNsubmitButtons = () => {
        return (
            <Grid container className={classes.clearNSubmit}>
                <Grid item xs={3}></Grid>
                <Grid item xs={9}>
                    <Grid container spacing={2} >
                        <Grid item xs={6}>
                            <Button size="large" fullWidth variant="outlined" onClick={clearfilter}>
                                Clear
                            </Button>
                        </Grid>
                        <Grid item xs={6}>
                            <Button fullWidth size="large" variant="contained" color="primary" onClick={handleSubmitBtn}>
                                Submit
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>

        )
    }

    return (
        <MuiThemeProvider theme={Theme}>
            <Header style={{ marginBottom: "10px" }} />
            <div className={classes.parentPaper}>
                {aunthenticate() ?
                    <Grid container spacing={3} >
                        <Grid className={classes.leftSection} item xs={12} sm={4} md={3} lg={3} xl={3}>
                            <Grid container spacing={2}>
                                {/* <Grid className={classes.breadcrum} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                    <BreadCrum links={(params === 'inprogress' || params === 'completed') ? [url, urlMySearch] : [url]} activeLink="Search Model" />
                                </Grid> */}
                                <Grid item className={(params === 'inprogress' || params === 'completed') && classes.blurOut}
                                    xs={12} sm={12} md={12} lg={12} xl={12}
                                >
                                    <Typography className={classes.subType} variant="body1">Select Model Task</Typography>
                                    <hr className={classes.styleHr} />
                                    <div className={classes.buttonDiv}>
                                        {renderDatasetButtons()}
                                    </div>
                                    <Typography className={classes.subHeader} variant="body1">{getTitle()}</Typography>
                                    <div className={classes.subHeader}>
                                        {datasetType['translation'] && renderTexfield("select-source-language", "Source Language *")}
                                    </div>
                                    <div className={classes.autoComplete}>
                                        <MultiAutocomplete
                                            single={true}
                                            id="language-target"
                                            options={getTargetLang()}
                                            filter='target'
                                            value={languagePair.target}
                                            handleOnChange={handleLanguagePairChange}
                                            label={getLabel()}
                                            error={tgtError}
                                            helperText="This field is mandatory"
                                        />
                                    </div>
                                    <Typography className={classes.subHeader} variant="body1">Filter by</Typography>
                                    <Grid container spacing={1}>
                                        <Grid className={classes.subHeader} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                            {renderFilterByfield("domain", "Domain", filterBy.domain, FilterBy.domain)}
                                        </Grid>
                                        <Grid className={classes.subHeader} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                            {renderFilterByfield("collectionMethod", "Submitter", filterBy.collectionMethod, FilterBy.collectionMethod)}
                                        </Grid>
                                        {/* <Grid className={classes.subHeader} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                    {renderFilterByfield("collectionMethod", "Select Metric", filterBy.collectionMethod, FilterBy.collectionMethod)}
                                </Grid> */}
                                    </Grid>

                                    {renderclearNsubmitButtons()}
                                </Grid>
                            </Grid>
                        </Grid>

                        <Grid item xs={12} sm={7} md={9} lg={9} xl={9} className={classes.modelTable}>
                            <SearchModels source={languagePair.source} target={languagePair.target} type={datasetType} />
                        </Grid>

                    </Grid>
                    :
                    <Paper elevation={0} className={classes.mainPaper}>
                        <Typography variant="h5">Explore Models</Typography>
                        <NewSearchModel />
                    </Paper>
                }
            </div>
            <Footer />
        </MuiThemeProvider>
    )


}

export default withStyles(DatasetStyle)(Benchmark);