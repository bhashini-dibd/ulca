import {
    Grid, Paper, Typography, Divider, FormControl,
    Button, TextField, Hidden, Popover, InputAdornment, OutlinedInput,
} from '@material-ui/core';
import Autocomplete from '@material-ui/lab/Autocomplete';
import BreadCrum from '../../../components/common/Breadcrum';
import { withStyles } from '@material-ui/core/styles';
import { RadioButton, RadioGroup } from 'react-radio-buttons';
import DatasetStyle from '../../../styles/Dataset';
import { useState } from 'react';
import SearchIcon from '@material-ui/icons/Search';

const SubmitDataset = (props) => {
    const { classes } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [dataset, setDatasetInfo] = useState({ name: "", url: "", type: "pd" })
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };


    const renderUpdateDatasetSearch = () => {
        return (
            <div>
                <div className={classes.updateDataset}>
                    <Grid container spacing={1}>
                        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>

                            <OutlinedInput className={classes.searchDataset} id="dataset-name" variant="outlined"
                                fullWidth
                                placeholder="Search dataset"
                                color="primary"
                                startAdornment={
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                }
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                            <div container className={classes.datasetName}>
                                <Typography variant="subtitle1">Tourism Set-1 Parallel Text Corpus</Typography>
                                <Typography className={classes.submittedOn}
                                    color="textSecondary"
                                    variant="caption">Submitted on: 14/4/2020</Typography>
                            </div>
                        </Grid>
                    </Grid>
                </div>
                <div style={{ float: 'right' }}>
                    <Button variant="text" color="primary">Cancel</Button>
                    <Button variant="text" color="primary">Done</Button>
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
                alert('Submitted successfully')
            } else {
                alert('Invalid URL')
            }
            if (dataset.name.length > 256) {
                alert('Dataset name field can have a max of 256 characters')
            }

        } else {
            alert('Please fill all the mandatory details')
        }
    }

    return (
        <div className={classes.divStyle}>
            <div className={classes.breadcrum}>
                <BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
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
                                        <Grid item xl={4} lg={4} md={4} sm={4} xs={4}>
                                            <Typography variant="h5"><strong>Parallel Dataset</strong></Typography>
                                        </Grid>
                                        <Grid item xl={8} lg={8} md={8} sm={8} xs={8}>
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
                                className={classes.submitBtn}
                                variant="contained"
                                color="primary"
                                onClick={handleSubmitDataset}
                            >
                                Submit
                                </Button>
                        </FormControl>
                    </Grid>
                </Grid>
            </Paper>
        </div>
    )
}

export default withStyles(DatasetStyle)(SubmitDataset);