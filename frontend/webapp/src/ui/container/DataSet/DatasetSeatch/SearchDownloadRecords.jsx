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
import { useDispatch, useSelector } from "react-redux";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import { useState, useEffect } from 'react';
import DownloadDatasetRecords from "./DownloadDatasetRecords";
import RequestNumberCreation from "./RequestNumberCreation";
import { useHistory, useParams } from 'react-router';
import Autocomplete from '../../../components/common/Autocomplete';
import { Language, FilterBy } from '../../../../configs/DatasetItems';
import SubmitSearchRequest from '../../../../redux/actions/api/DataSet/DatasetSearch/SubmitSearchRequest';
import DatasetType from '../../../../configs/DatasetItems';

const SearchAndDownloadRecords = (props) => {
    const { classes } = props;
    const url = UrlConfig.dataset;
    const param = useParams();
    const history = useHistory();
    const [languagePair, setLanguagePair] = useState({
        source: '',
        target: []
    });
    const [filterBy, setFilterBy] = useState({
        domain: [],
        source: [],
        collectionMethod: []
    });

    const [datasetType, setDatasetType] = useState({
        'parallel-corpus': true
    })

    // const searchOptions = useSelector((state) => state.mySearchOptions);
    // const dispatch = useDispatch();
    // useEffect(() => {
    //     const userObj = new SearchAndDownload();
    //     searchOptions.result.length === 0 && dispatch(APITransport(userObj));
    // }, []);
    // console.log(searchOptions)

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
        switch (params) {
            case 'inprogress':
                return <RequestNumberCreation reqno={srno} />
            case 'published':
                return <DownloadDatasetRecords datasetType={"Parallel"} sentencePair={"9.8 Million"} datasetsContributed={"29"} />
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
            return "Select Script *"
        else
            return "Select Language *"
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
            domain: [],
            source: [],
            collectionMethod: []
        });
        setLanguagePair({
            source: "",
            target: []
        });
    }

    const makeSubmitAPICall = (src, tgt, domain, collectionMethod, type) => {
        setSnackbarInfo({
            ...snackbar,
            open: true,
            message: 'Please wait while we process your request...',
            variant: 'info'
        })
        const apiObj = new SubmitSearchRequest(type, tgt, src, domain, collectionMethod)
        fetch(apiObj.apiEndPoint(), {
            method: 'post',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        })
            .then(async res => {
                if (res.ok) {
                    let response = await res.json()
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
        let domain = filterBy.domain.map(domain => domain.value)
        let collectionMethod = filterBy.collectionMethod.map(method => method.value)
        if (datasetType['parallel-corpus']) {
            if (languagePair.source && languagePair.target.length) {
                makeSubmitAPICall(languagePair.source, tgt, domain, collectionMethod)
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
                makeSubmitAPICall(null, tgt, domain, collectionMethod)
            }

        }


    }

    const renderDatasetButtons = () => {
        return (
            DatasetType.map((type, i) => {
                return (
                    <Button className={classes.innerButton} variant={datasetType[type.value] ? "contained" : "outlined"}
                        color="primary"
                        key={i}
                        onClick={() => handleDatasetClick(type.value)}
                    >
                        {type.label}
                    </Button>)
            })

        )
    }
    const getTargetLang=()=>{
        return Language.filter(lang=>lang.value!==languagePair.source)
    }

    return (
        <div className={classes.searchDivStyle}>
            <Grid container spacing={3}>
                <Grid className={(params==='inprogress'|| params==='published') && classes.blurOut} item xs={12} sm={5} md={4} lg={4} xl={4}>
                    <div className={classes.breadcrum}>
                        <BreadCrum links={[url]} activeLink="Search & Download Records" />
                    </div>
                    <Typography className={classes.subHeader} variant="h6">Select Dataset Type</Typography>

                    <div className={classes.buttonDiv}>
                        {renderDatasetButtons()}
                        {/* <Button className={classes.innerButton} variant={datasetType.pd ? "contained" : "outlined"}
                            color="primary"
                            onClick={() => handleDatasetClick('pd')}
                        >
                            Parallel Dataset
                    </Button>
                        <Button className={classes.innerButton} variant={datasetType.md ? "contained" : "outlined"}
                            color="primary"
                            onClick={() => handleDatasetClick('md')}
                        >
                            Monolingual Dataset
                    </Button>
                        <Button className={classes.innerButton} variant={datasetType.atd ? "contained" : "outlined"}
                            color="primary"
                            onClick={() => handleDatasetClick('atd')}
                        >
                            ASR/TTS Dataset
                    </Button>
                        <Button className={classes.innerButton} variant={datasetType.od ? "contained" : "outlined"}
                            color="primary"
                            onClick={() => handleDatasetClick('od')}
                        >
                            OCR Dataset
                    </Button> */}
                    </div>

                    <Typography className={classes.subHeader} variant="h6">{getTitle()}</Typography>
                    <div className={classes.subHeader}>
                        {datasetType['parallel-corpus'] &&
                            <TextField className={classes.subHeader}
                                fullWidth
                                error={srcError}
                                helperText={srcError && "Source language is mandatory"}
                                select
                                id="select-source-language"
                                label="Source Language *"
                                value={languagePair.source}
                                onChange={(event) => handleLanguagePairChange(event.target.value, 'source')}
                            >
                                {Language.map((option) => (
                                    <MenuItem key={option.value} value={option.value}>
                                        {option.label}
                                    </MenuItem>
                                ))}
                            </TextField>}

                        <Autocomplete
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
                    <Typography className={classes.subHeader} variant="h6">Filter by</Typography>
                    <div className={classes.subHeader}>
                        <Grid container spacing={1}>
                            <Grid className={classes.subHeader} item xs={12} sm={12} md={12} lg={12} xl={12}>
                                <Autocomplete
                                    id="domain"
                                    options={FilterBy.domain}
                                    filter="domain"
                                    value={filterBy.domain}
                                    handleOnChange={handleFilterByChange}
                                    label="Select Domain"
                                />
                            </Grid>
                            {/* <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                                <Autocomplete
                                    id="source"
                                    options={sourceLanguages}
                                    filter="source"
                                    value={filterBy.source}
                                    handleOnChange={handleFilterByChange}
                                    label="Select Source"
                                />
                            </Grid> */}
                        </Grid>
                        <Autocomplete
                            id="collection-method"
                            options={FilterBy.collectionMethod}
                            filter="collectionMethod"
                            value={filterBy.collectionMethod}
                            handleOnChange={handleFilterByChange}
                            label="Select Collection Method"
                        />
                    </div>

                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={state.checkedA}
                                onChange={handleCheckboxChange}
                                name="checkedA"
                                color="primary"
                            />
                        }
                        label="Vetted by multiple annotators"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={state.checkedB}
                                onChange={handleCheckboxChange}
                                name="checkedB"
                                color="primary"
                            />
                        }
                        label="Source sentences manually translated by multiple translators"
                    />
                    <div className={classes.clearNSubmit}>
                        <Button color="primary" onClick={clearfilter}>
                            Clear
                    </Button>
                        <Button variant="contained" color="primary" onClick={handleSubmitBtn}>
                            Submit
                    </Button>
                    </div>
                </Grid>
                <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
                    <Divider className={classes.divider} orientation="vertical" />
                </Grid>
                <Grid item xs={12} sm={6} md={7} lg={7} xl={7}>
                    {renderPage()}
                </Grid>

            </Grid>
            {snackbar.open &&
                <Snackbar
                    open={snackbar.open}
                    handleClose={handleSnackbarClose}
                    anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                    message={snackbar.message}
                    variant={snackbar.variant}
                />}
        </div>
    )


}

export default withStyles(DatasetStyle)(SearchAndDownloadRecords);