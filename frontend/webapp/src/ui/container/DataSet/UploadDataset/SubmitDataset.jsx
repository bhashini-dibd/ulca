import {
    Grid,
    Paper,
    Typography,
    Divider,
    FormControl,
    Button,
    TextField,
    Hidden,
    Popover
} from '@material-ui/core';
import Autocomplete from '@material-ui/lab/Autocomplete';
import BreadCrum from '../../../components/common/Breadcrum';
import { withStyles } from '@material-ui/core/styles';
import { RadioButton, RadioGroup } from 'react-radio-buttons';
import DatasetStyle from '../../../styles/Dataset';
import { useState } from 'react';
import { useHistory } from "react-router-dom";
import Snackbar from '../../../components/common/Snackbar';
import UrlConfig from '../../../../configs/internalurlmapping';

const SubmitDataset = (props) => {
    const { classes } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [dataset, setDatasetInfo] = useState({ name: "", url: "", type: "pd", filteredName: "" })
    const [snackbar, setSnackbarInfo] = useState({
        timeOut: 3000,
        open: false,
        message: '',
        variant: 'success'
    })
    const [error, setError] = useState(false)
    const [search, setSearch] = useState(false)
    const history = useHistory();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget)
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleDone = () => {
        if (dataset.filteredName) {
            setDatasetInfo({ ...dataset, name: dataset.filteredName })
        }
        handleClose();
    }

    const renderUpdateDatasetSearch = () => {
        return (
            <div>
                <div className={classes.updateDataset}>
                    <Grid container spacing={1}>
                        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Autocomplete
                                id="tags-outlined"
                                options={[{ "name": 'Test' }, { "name": 'Test123' }, { "name": 'Test456' }]}
                                getOptionLabel={(option) => option.name}
                                filterSelectedOptions
                                open={search}
                                onChange={(e, value) => {
                                    setDatasetInfo({ ...dataset, filteredName: value !== null ? value.name : "" })
                                }}
                                onOpen={() => {
                                    setTimeout(() => setSearch(true), 200)
                                }}
                                onClose={() => {
                                    setSearch(false)
                                }}
                                openOnFocus
                                renderInput={(params) => (
                                    <TextField
                                        id="search-dataset"
                                        variant="outlined"
                                        placeholder="Search Dataset"
                                        autoFocus={true}
                                        {...params}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                </div>
                <div style={{ float: 'right' }}>
                    <Button variant="text" color="primary" onClick={handleClose}>Cancel</Button>
                    <Button variant="text" color="primary" onClick={handleDone}>Done</Button>
                </div>
            </div>
        )
    }

    const validURL = (str) => {
        var pattern = new RegExp('^((ft|htt)ps?:\\/\\/)?' + // protocol
            '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name and extension
            '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
            '(\\:\\d+)?' + // port
            '(\\/[-a-z\\d%@_.~+&:]*)*' + // path
            '(\\?[;&a-z\\d%@_.,~+&:=-]*)?' + // query string
            '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
        return pattern.test(str);
    }

    const handleSubmitDataset = (e) => {
        if (dataset.name.trim() !== "" && dataset.url.trim() !== "" && dataset.type.trim() !== "") {
            if (validURL(dataset.url)) {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: 'Please wait while we process your request...',
                    timeOut: 3000,
                    variant: 'info'
                })

                setTimeout(() => history.push(`${process.env.PUBLIC_URL}/submit-dataset/submission/${5}`), 3000)
            } else {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: 'Invalid URL',
                    timeOut: 3000,
                    variant: 'error'
                })
            }
            if (dataset.name.length > 256) {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: 'Dataset name field can have a max of 256 characters',
                    timeOut: 3000,
                    variant: 'error'
                })
                alert('')
            }

        } else {
            setSnackbarInfo({
                ...snackbar,
                open: true,
                message: 'Please fill all the details',
                timeOut: 3000,
                variant: 'error'
            })
        }
    }

    const renderDatasetType = () => {
        let { type } = dataset
        switch (type) {
            case 'pd':
                return 'Parallel Dataset'
            case 'md':
                return 'Monolingual Dataset'
            case 'atd':
                return 'ASR/TTS Dataset'
            case 'od':
                return 'OCR Dataset'
            default:
                return 'Parallel Dataset'
        }
    }

    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false })
    }

    const url = UrlConfig.dataset

    return (
        <div>
            <div className={classes.divStyle}>
                <div className={classes.breadcrum}>
                    <BreadCrum links={[url]} activeLink="Submit Dataset" />
                </div>
                <Paper elevation={3} className={classes.paper}>
                    <Grid container className={classes.title}>
                        <Grid item>
                            <Typography variant="h4"><strong>Submit Dataset</strong></Typography>
                        </Grid>
                    </Grid>
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={12} md={5} lg={5} xl={5}>
                            <Typography color="textSecondary" variant="subtitle1">STEP-1</Typography>
                            <FormControl className={classes.form}>
                                <Typography className={classes.typography} variant="h5"><strong>Select Dataset Type</strong></Typography>
                                <RadioGroup value={dataset.type} onChange={(e) => setDatasetInfo({ ...dataset, type: e })} className={classes.radioGroup} vertical="true">
                                    <RadioButton rootColor="grey" pointColor="black" value="pd">
                                        Parallel Dataset
                                    </RadioButton>
                                    <RadioButton rootColor="grey" pointColor="black" value="md">
                                        Monolingual Dataset
                                    </RadioButton>
                                    <RadioButton rootColor="grey" pointColor="black" value="atd">
                                        ASR/TTS Dataset
                                    </RadioButton>
                                    <RadioButton rootColor="grey" pointColor="black" value="od">
                                        OCR Dataset
                                    </RadioButton>
                                </RadioGroup>
                            </FormControl>
                        </Grid>
                        <Hidden>
                            <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                                <Divider orientation="vertical" />
                            </Grid>
                        </Hidden>
                        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                            <Typography color="textSecondary" variant="subtitle1">STEP-2</Typography>
                            <FormControl className={classes.form}>
                                <Grid container spacing={3}>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={5}>
                                            <Grid item xl={6} lg={6} md={6} sm={6} xs={6}>
                                                <Typography variant="h5"><strong>{renderDatasetType()}</strong></Typography>
                                            </Grid>
                                            <Grid item xl={6} lg={6} md={6} sm={6} xs={6}>
                                                <Button
                                                    className={classes.updateBtn}
                                                    color="inherit"
                                                    variant="contained"
                                                    onClick={(e) => handleClick(e)}
                                                >
                                                    Update an existing dataset
                                            </Button>
                                                <Popover
                                                    className={classes.popOver}
                                                    id={"update-dataset"}
                                                    open={Boolean(anchorEl)}
                                                    anchorEl={anchorEl}
                                                    onClose={handleClose}
                                                    anchorOrigin={{
                                                        vertical: 'bottom',
                                                        horizontal: 'left',
                                                    }}
                                                    transformOrigin={{
                                                        vertical: 'top',
                                                        horizontal: "center",
                                                    }}
                                                    children={renderUpdateDatasetSearch()}
                                                />
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={3}>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth
                                                    variant="outlined"
                                                    color="primary"
                                                    label="Dataset name"
                                                    value={dataset.name}
                                                    error={error}
                                                    helperText={error && "Dataset name already exists"}
                                                    onChange={(e) => setDatasetInfo({ ...dataset, name: e.target.value })}
                                                />
                                            </Grid>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth
                                                    variant="outlined"
                                                    color="primary"
                                                    label="Paste the URL of the public repository"
                                                    value={dataset.url}
                                                    onChange={(e) => setDatasetInfo({ ...dataset, url: e.target.value })}
                                                />
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Button
                                    color="primary"
                                    className={classes.submitBtn}
                                    variant="contained"
                                    
                                    onClick={handleSubmitDataset}
                                >
                                    Submit
                                </Button>
                            </FormControl>
                        </Grid>
                    </Grid>
                </Paper>
            </div>
            <Snackbar
                open={snackbar.open}
                timeOut={snackbar.timeOut}
                handleClose={handleSnackbarClose}
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                message={snackbar.message}
                variant={snackbar.variant}
            />
        </div>
    )
}

export default withStyles(DatasetStyle)(SubmitDataset);