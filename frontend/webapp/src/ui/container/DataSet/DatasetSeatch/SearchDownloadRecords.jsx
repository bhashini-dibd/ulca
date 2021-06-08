import {
    Grid,
    Paper,
    Typography,
    Button,
    TextField,
    MenuItem,
    Checkbox,
    FormControlLabel,
    Divider
} from '@material-ui/core';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import BreadCrum from '../../../components/common/Breadcrum';
import UrlConfig from '../../../../configs/internalurlmapping';
import { useState } from 'react';

const SearchAndDownloadRecords = (props) => {
    const { classes } = props;
    const url = UrlConfig.dataset;
    const [languagePair, setLanguagePair] = useState({
        source: '',
        target: ''
    });
    const [filterBy, setFilterBy] = useState({
        domain: '',
        source: '',
        collectionMethod: ''
    });
    const handleCheckboxChange = (event) => {
        setState({ ...state, [event.target.name]: event.target.checked });
    };
    const handleLanguagePairChange = (value, property) => {
        setLanguagePair({ ...languagePair, [property]: value });
    };
    const handleFilterByChange = (event, property) => {
        setFilterBy({ ...filterBy, [`${property}`]: event.target.value });
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
        checkedA: true,
        checkedB: false,
    });
   console.log('filterBy', filterBy);
   console.log('languagePair', languagePair);

    return (
        <div className={classes.searchDivStyle}>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={12} md={4} lg={4} xl={4}>
                    <div className={classes.breadcrum}>
                        <BreadCrum links={[url]} activeLink="Search & Download Records" />
                    </div>
                    <Typography className={classes.subHeader} variant="h6">Select Dataset Type</Typography>

                    <div className={classes.buttonDiv}>
                        <Button className={classes.innerButton} variant="contained" color="primary">
                            Parallel Dataset
                    </Button>
                        <Button className={classes.innerButton} variant="outlined" color="primary">
                            Monolingual Dataset
                    </Button>
                        <Button className={classes.innerButton} variant="outlined" color="primary">
                            ASR/TTS Dataset
                    </Button>
                        <Button className={classes.innerButton} variant="outlined" color="primary">
                            OCR Dataset
                    </Button>
                    </div>

                    <Typography className={classes.subHeader} variant="h6">Select Language Pair</Typography>
                    <div className={classes.subHeader}>
                        <TextField className={classes.subHeader}
                            fullWidth
                            id="select-source-language"
                            select
                            label="Source Language *"
                            value={languagePair.source}
                            onChange={(event)=>handleLanguagePairChange(event.target.value,'source')}
                        >
                            {sourceLanguages.map((option) => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </TextField>
                        <Autocomplete
                            filterSelectedOptions
                            limitTags={3}
                            multiple
                            id="select-target-language"
                            options={sourceLanguages}
                            getOptionLabel={(option) => option.label}
                            onChange= {(event, value, reason)=>handleLanguagePairChange(value,'target')}
                            renderInput={(params) => (
                                <TextField
                                    {...params}
                                    variant="standard"
                                    label="Target Language *"
                                // placeholder="Favorites"
                                />
                            )}
                        />
                    </div>
                    <Typography className={classes.subHeader} variant="h6">Filter by</Typography>
                    <div className={classes.subHeader}>
                        <Grid container spacing={2}>
                            <Grid className={classes.subHeader} item xs={6}>
                                <TextField
                                    fullWidth
                                    id="select-source-language"
                                    select
                                    label="Select Domain"
                                    value={filterBy.domain}
                                    onChange={(event) => handleFilterByChange(event, 'domain')}
                                >
                                    {sourceLanguages.map((option) => (
                                        <MenuItem key={option.value} value={option.value}>
                                            {option.label}
                                        </MenuItem>
                                    ))}
                                </TextField>
                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    fullWidth
                                    id="select-source-language"
                                    select
                                    label="Select Source"
                                    value={filterBy.source}
                                    onChange={(event) => handleFilterByChange(event, 'source')}
                                >
                                    {sourceLanguages.map((option) => (
                                        <MenuItem key={option.value} value={option.value}>
                                            {option.label}
                                        </MenuItem>
                                    ))}
                                </TextField>
                            </Grid>
                        </Grid>
                        <TextField
                            fullWidth
                            id="select-source-language"
                            select
                            label="Select Collection Method"
                            value={filterBy.collectionMethod}
                            onChange={(event) => handleFilterByChange(event, 'collectionMethod')}
                        >
                            {sourceLanguages.map((option) => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </TextField>
                    </div>

                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={state.checkedA}
                                onChange={handleCheckboxChange}
                                name="checkedB"
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
                        <Button color="primary">
                            Clear
                    </Button>
                        <Button variant="contained" color="primary">
                            Submit
                    </Button>
                    </div>
                </Grid>
                <Grid item>
                    <Divider orientation="vertical" />
                </Grid>
                <Grid item xs={12} sm={12} md={8} lg={8} xl={8}>
                    {/* <Paper >xs=6</Paper> */}
                </Grid>

            </Grid>
        </div>
    )


}

export default withStyles(DatasetStyle)(SearchAndDownloadRecords);