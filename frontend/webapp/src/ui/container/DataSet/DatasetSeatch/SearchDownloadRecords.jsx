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
        pd: true
    })

    const searchOptions = useSelector((state) => state.mySearchOptions);
    const dispatch = useDispatch();
    useEffect(() => {
        const userObj = new SearchAndDownload();
        searchOptions.result.length === 0 && dispatch(APITransport(userObj));
    }, []);
    console.log(searchOptions)

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
    const sourceLanguages = [
        {
            value: 'Eng',
            label: 'English',
        },
        {
            value: 'Hin',
            label: 'Hindi',
        },
        {
            value: 'Ben',
            label: 'Bengali',
        },
        {
            value: 'Mar',
            label: 'Marathi',
        },
    ];
    const [state, setState] = useState({
        checkedA: false,
        checkedB: false,
    });
    const [srcError, setSrcError] = useState(false)
    const [tgtError, setTgtError] = useState(false)

    const renderPage = () => {
        const { params } = param
        switch (params) {
            case 'inprogress':
                return <RequestNumberCreation reqno={"0005870"} />
            case 'published':
                return <DownloadDatasetRecords datasetType={"Parallel"} sentencePair={"9.8 Million"} datasetsContributed={"29"} />
            default:
                return <SearchResult />
        }
    }

    const handleDatasetClick = (property) => {
        clearfilter()
        setDatasetType({ [property]: true })
        setSrcError(false)
        setTgtError(false)
    }
    const getLabel = () => {
        if (datasetType.pd)
            return "Target Language *"
        else if (datasetType.md || datasetType.atd)
            return "Select Language *"
        else
            return "Select Script *"
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
    const handleSubmitBtn = () => {
        if (datasetType.pd) {
            if (languagePair.source && languagePair.target.length)
                history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/inprogress`)
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
            else
                history.push(`${process.env.PUBLIC_URL}/search-and-download-rec/inprogress`)
        }


    }
    return (
        <div className={classes.searchDivStyle}>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={5} md={4} lg={4} xl={4}>
                    <div className={classes.breadcrum}>
                        <BreadCrum links={[url]} activeLink="Search & Download Records" />
                    </div>
                    <Typography className={classes.subHeader} variant="h6">Select Dataset Type</Typography>

                    <div className={classes.buttonDiv}>
                        <Button className={classes.innerButton} variant={datasetType.pd ? "contained" : "outlined"}
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
                    </Button>
                    </div>

                    <Typography className={classes.subHeader} variant="h6">Select Language Pair</Typography>
                    <div className={classes.subHeader}>
                        {datasetType.pd &&
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
                                {sourceLanguages.map((option) => (
                                    <MenuItem key={option.value} value={option.value}>
                                        {option.label}
                                    </MenuItem>
                                ))}
                            </TextField>}

                        <Autocomplete
                            id="language-target"
                            options={sourceLanguages}
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
                            <Grid className={classes.subHeader} item xs={12} sm={12} md={6} lg={6} xl={6}>
                                <Autocomplete
                                    id="domain"
                                    options={sourceLanguages}
                                    filter="domain"
                                    value={filterBy.domain}
                                    handleOnChange={handleFilterByChange}
                                    label="Select Domain"
                                />
                            </Grid>
                            <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                                <Autocomplete
                                    id="source"
                                    options={sourceLanguages}
                                    filter="source"
                                    value={filterBy.source}
                                    handleOnChange={handleFilterByChange}
                                    label="Select Source"
                                />
                            </Grid>
                        </Grid>
                        <Autocomplete
                            id="collection-method"
                            options={sourceLanguages}
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
                    <Divider orientation="vertical" />
                </Grid>
                <Grid item xs={12} sm={6} md={7} lg={7} xl={7}>
                    {renderPage()}
                    {/* <SearchResult/> */}
                    {/* <RequestNumberCreation reqno={"0005870"} /> */}
                    {/* <DownloadDatasetRecords datasetType={"Parallel"} sentencePair={"9.8 Million"} datasetsContributed={"29"}/> */}
                </Grid>

            </Grid>
        </div>
    )


}

export default withStyles(DatasetStyle)(SearchAndDownloadRecords);