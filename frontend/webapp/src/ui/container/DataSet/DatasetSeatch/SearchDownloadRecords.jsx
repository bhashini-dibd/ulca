import {
    Grid,
    Typography,
    Button,
    TextField,
    MenuItem,
    Checkbox,
    FormControlLabel,
    Divider
} from '@material-ui/core';
import SearchResult from "./SearchResult";
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import Snackbar from '../../../components/common/Snackbar';
import BreadCrum from '../../../components/common/Breadcrum';
import UrlConfig from '../../../../configs/internalurlmapping';
import SearchAndDownload from '../../../../redux/actions/api/DataSet/DatasetSearch/SearchAndDownload';
import {PageChange} from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import C from "../../../../redux/actions/constants";
import { useDispatch, useSelector } from "react-redux";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useState, useEffect, useRef } from 'react';
import DownloadDatasetRecords from "./DownloadDatasetRecords";
import RequestNumberCreation from "./RequestNumberCreation";
import { useHistory, useParams } from 'react-router';
import Autocomplete from '@material-ui/lab/Autocomplete';
import MultiAutocomplete from '../../../components/common/Autocomplete';
import { Language, FilterBy } from '../../../../configs/DatasetItems';
import SubmitSearchRequest from '../../../../redux/actions/api/DataSet/DatasetSearch/SubmitSearchRequest';
import DatasetType from '../../../../configs/DatasetItems';
import getLanguageLabel from '../../../../utils/getLabel';

const SearchAndDownloadRecords = (props) => {
    const { classes } = props;
    const url = UrlConfig.dataset;
    const urlMySearch = UrlConfig.mySearches;
    const dispatch                  = useDispatch();
    const param = useParams();
    const history = useHistory();
    const [languagePair, setLanguagePair] = useState({
        source: '',
        target: []
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

    const [datasetType, setDatasetType] = useState({
        'parallel-corpus': true
    })

    const [count, setCount] = useState(0);
    const [urls, setUrls] = useState({
        downloadSample: '',
        downloadAll: ''
    })

    const previousUrl = useRef();


    const detailedReport = useSelector((state) => state.mySearchReport);

    useEffect(() => {

        previousUrl.current = params;

        let data = detailedReport.responseData.filter((val) => {
            return val.sr_no === srno
        })
        if (data[0]) {
            setCount(data[0].count);
            setUrls({
                downloadSample: data[0].sampleUrl,
                downloadAll: data[0].downloadUrl
            })

            let target = data[0].targetLanguage ? getLanguageLabel(data[0].targetLanguage) : getLanguageLabel(data[0].sourceLanguage)
            let source = data[0].sourceLanguage && Language.filter(val => val.value === data[0].sourceLanguage[0])[0].label
            setLanguagePair({ target, source })
            //   setLanguagePair({ target, source: getLanguageLabel(data[0].sourceLanguage)})
            setDatasetType({ [data[0].datasetType]: true })
        }

        else if ((params === 'completed' || params === 'inprogress') && count === 0)
            history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/initiate/-1`)

    }, []);

    useEffect(() => {
        if (previousUrl.current !== params && previousUrl.current !== 'initiate') {
            setLanguagePair({ target: [], source: "" })
            setFilterBy({
                domain: "",
                source: "",
                collectionMethod: ""
            })
        }
        previousUrl.current = params;
    })

    const getValueForLabel = (label) => {
        return Language.filter(val => val.label === label)[0]
    }

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
    });
    const [srcError, setSrcError] = useState(false)
    const [tgtError, setTgtError] = useState(false)
    const { params, srno } = param
    const renderPage = () => {
        let data = detailedReport.responseData.filter((val) => {
            return val.sr_no === srno
        })
        let datasetType = data.length && getLanguageLabel(data[0].datasetType, 'datasetType')[0]
        switch (params) {
            case 'inprogress':
                return <RequestNumberCreation reqno={srno} />
            case 'completed':
                return <DownloadDatasetRecords datasetType={datasetType ? datasetType : 'Parallel'} sentencePair={count} urls={urls} />
            default:
                return <SearchResult />
        }
    }

    const handleDatasetClick = (property) => {
        history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/initiate/-1`)
        clearfilter()
        setDatasetType({ [property]: true })
        setSrcError(false)
        setTgtError(false)
    }
    const getLabel = () => {
        if (datasetType['parallel-corpus'])
            return "Target Language *"
        else if (datasetType['ocr-corpus'])
            return "Script *"
        else
            return "Language *"
    }

    const getTitle = () => {
        if (datasetType['parallel-corpus'])
            return "Select Language Pair"
        else if (datasetType['ocr-corpus'])
            return "Select Script"
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
            target: []
        });
    }

    const makeSubmitAPICall = (src, tgt, domain, collectionMethod, type) => {
        const Dataset = Object.keys(type)[0]
        setSnackbarInfo({
            ...snackbar,
            open: true,
            message: 'Please wait while we process your request...',
            variant: 'info'
        })
        const apiObj = new SubmitSearchRequest(Dataset, tgt, src, domain, collectionMethod)
        fetch(apiObj.apiEndPoint(), {
            method: 'post',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        })
            .then(async res => {
                if (res.ok) {
                    let response = await res.json()
                    dispatch(PageChange(0, C.SEARCH_PAGE_NO));
                    history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/inprogress/${response.serviceRequestNumber}`)
                    handleSnackbarClose()

                } else {
                    new Promise.reject("")
                }
            })
            .catch(err => {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: 'Failed to submit your search request...',
                    variant: 'error'
                })
            })

    }
    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false })
    }
    const handleSubmitBtn = () => {
        let tgt = languagePair.target.map(trgt => trgt.value)
        //let domain = filterBy.domain.map(domain => domain.value)
        //let collectionMethod = filterBy.collectionMethod.map(method => method.value)
        let domain = filterBy.domain && [filterBy.domain]
        let collectionMethod = filterBy.collectionMethod && [filterBy.collectionMethod]
        if (datasetType['parallel-corpus']) {
            if (languagePair.source && languagePair.target.length) {
                let source = getValueForLabel(languagePair.source).value
                makeSubmitAPICall(source, tgt, domain, collectionMethod, datasetType)
                //  makeSubmitAPICall(languagePair.source, tgt, domain, collectionMethod, datasetType)
            }

            else if (!languagePair.source && !languagePair.target.length) {
                setSrcError(true)
                setTgtError(true)
            }

            else if (!languagePair.source)
                setSrcError(true)
            else if (!languagePair.target.length)
                setTgtError(true)
        }
        else {
            if (!languagePair.target.length)
                setTgtError(true)
            else {
                makeSubmitAPICall(null, tgt, domain, collectionMethod, datasetType)
            }

        }


    }

    const renderDatasetButtons = () => {
        return (
            DatasetType.map((type, i) => {
                return (
                    <Button size='small' className={classes.innerButton} variant="outlined"
                        color={datasetType[type.value] && "primary"}
                        key={i}
                        onClick={() => handleDatasetClick(type.value)}
                    >
                        {type.label}
                    </Button>)
            })

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
    const renderFilterByfield = (id, label, value, filter) => {
        return (
            <TextField className={classes.subHeader}
                fullWidth
                select
                id={id}
                label={label}
                value={value}
                onChange={(event) => handleFilterByChange(event.target.value, id)}
            >
                {filter.map((option) => (
                    <MenuItem key={option.value} value={option.value}>
                        {option.label}
                    </MenuItem>
                ))}
            </TextField>
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
                    helperText={srcError && "Source language is mandatory"}
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
            /* <div className={classes.clearNSubmit}>
                      <Button size="large"  variant="outlined" onClick={clearfilter}>
                          Clear
                  </Button>
                      <Button size="large" className={classes.buttonStyle} variant="contained" color="primary" onClick={handleSubmitBtn}>
                          Submit
                  </Button>
                  </div> */
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
        <div>
            <Grid container spacing={3} className={classes.searchGrid}>
                <Grid className={classes.leftSection} item xs={12} sm={5} md={4} lg={4} xl={4}>
                    <Grid container spacing={2}>
                        <Grid className={classes.breadcrum} item xs={12} sm={12} md={12} lg={12} xl={12}>
                            <BreadCrum links={(params === 'inprogress' || params === 'completed') ? [url, urlMySearch] : [url]} activeLink="Search & Download Records" />
                        </Grid>
                        <Grid item className={(params === 'inprogress' || params === 'completed') && classes.blurOut}
                            xs={12} sm={12} md={12} lg={12} xl={12}
                        >
                            <Typography className={classes.subHeader} variant="body1">Select Dataset Type</Typography>
                            <div className={classes.buttonDiv}>
                                {renderDatasetButtons()}
                            </div>
                            <Typography className={classes.subHeader} variant="body1">{getTitle()}</Typography>
                            <div className={classes.subHeader}>
                                {datasetType['parallel-corpus'] && renderTexfield("select-source-language", "Source Language *")}
                            </div>
                            <div className={classes.subHeader}>
                            <MultiAutocomplete
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
                                    {renderFilterByfield("domain", "Select Domain", filterBy.domain, FilterBy.domain)}
                                </Grid>
                                <Grid className={classes.subHeader} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                    {renderFilterByfield("collectionMethod", "Select Collection Method", filterBy.collectionMethod, FilterBy.collectionMethod)}
                                </Grid>
                            </Grid>

                            {renderCheckBox("checkedA", "primary", "Vetted by multiple annotators")}
                            {renderCheckBox("checkedB", "primary", "Source sentences manually translated by multiple translators")}
                            {renderclearNsubmitButtons()}
                        </Grid>
                    </Grid>
                </Grid>

                <Grid item xs={12} sm={7} md={8} lg={8} xl={8} className={classes.parent}>
                    {renderPage()}
                </Grid>

            </Grid>
            {
                snackbar.open &&
                <Snackbar
                    open={snackbar.open}
                    handleClose={handleSnackbarClose}
                    anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                    message={snackbar.message}
                    variant={snackbar.variant}
                />
            }
        </div >
    )


}

export default withStyles(DatasetStyle)(SearchAndDownloadRecords);