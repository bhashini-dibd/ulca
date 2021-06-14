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
import SubmitDatasetApi from "../../../../redux/actions/api/UploadDataset/SubmitDataset"
import DatasetItems from "../../../../configs/DatasetItems";
const SubmitDataset = (props) => {
    const { classes } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [dataset, setDatasetInfo] = useState({ datasetName: "", url: "", type: "parallel-corpus" })
    const [title, setTitle]         = useState( "Parallel Corpus")
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: '',
        variant: 'success'
    })
    const [error, setError] = useState({datasetName: "", url: "", type: false})
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
            setDatasetInfo({ ...dataset, datasetName: dataset.filteredName })
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
                                options={[]}
                                getOptionLabel={(option) => option.name}
                                filterSelectedOptions
                                open={search}
                                onChange={(e, value) => {
                                    setDatasetInfo({ ...dataset, datasetName: value.name})
                                    handleClose();
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
               
            </div>
        )
    }

    const handleApicall = async () => {

let apiObj = new SubmitDatasetApi(dataset)
    fetch(apiObj.apiEndPoint(), {
      method: 'post',
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers
    }).then(async response => {
      const rsp_data = await response.json();
      if (!response.ok) {
        
        return Promise.reject('');
      } else {
        history.push(`${process.env.PUBLIC_URL}/submit-dataset/submission/${rsp_data.serviceRequestNumber}`)
//           return true;
      }
    }).catch((error) => {
        setSnackbarInfo({
                        ...snackbar,
                        open: true,
                        message: "something went wrong. PLease try again.",
                        timeOut: 40000,
                        variant: 'error'
                    })
    });
  
    }

    const renderRadioIcons = ()   =>{
        return <RadioGroup value={dataset.type} onChange={(e) => setDatasetInfo({ ...dataset, type: e })} className={classes.radioGroup} vertical="true">
                {DatasetItems.map((item, index)=>(
                    <RadioButton key ={index} rootColor="grey" pointColor="black" value={item.value}>
                   {item.label}
                </RadioButton>
                ))}
            </RadioGroup>
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
        if(dataset.datasetName.trim() === "" || dataset.url.trim() === ""){
            setError({...error, name: !dataset.datasetName.trim() ? "Name cannot be empty":"",url:!dataset.url.trim() ?"URL cannot be empty" :"" })
            
        }
        else if(dataset.datasetName.length > 256) {
            setError({...error, name:"Max 256 characters allowed"})
            
        }
        else if(!validURL(dataset.url)){
            setError({...error, url:"‘Invalid URL"})
        }
        else{

            handleApicall()
            setSnackbarInfo({
                ...snackbar,
                open: true,
                message: 'Please wait while we process your request...',
                variant: 'info'
            })
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
                            <Typography variant="h5"><strong>Submit Dataset</strong></Typography>
                        </Grid>
                    </Grid>
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={12} md={5} lg={5} xl={5}>
                            <Typography color="textSecondary" variant="subtitle1">STEP-1</Typography>
                            <FormControl className={classes.form}>
                                <Typography className={classes.typography} variant="subtitle1"><strong>Select Dataset Type</strong></Typography>
                                    {renderRadioIcons()}
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
                                <Grid container spacing={6}>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={5}>
                                            <Grid item xl={5} lg={5} md={5} sm={12} xs={12}>
                                                <Typography className={classes.typography} variant="subtitle1"><strong>{title}</strong></Typography>
                                            </Grid>
                                            <Grid item xl={7} lg={7} md={7} sm={12} xs={12}>
                                                <div>
                                                <Button
                                                    size = "medium"
                                                    className={classes.updateBtn}
                                                    color="primary"
                                                    variant="outlined"
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
                                                </div>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={3}>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth
                                                    
                                                    color="primary"
                                                    label="Dataset name"
                                                    value={dataset.datasetName}
                                                    error={error.name? true : false}
                                                    helperText={error.name}
                                                    onChange={(e) => {
                                                        setDatasetInfo({ ...dataset, datasetName: e.target.value })
                                                        setError({...error, name:false})
                                                }}
                                                />
                                            </Grid>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth
                                                    
                                                    color="primary"
                                                    label="Paste the URL of the public repository"
                                                    value={dataset.url}
                                                    error = {error.url? true : false}
                                                    helperText={error.url}
                                                    onChange={(e) => {
                                                        setDatasetInfo({ ...dataset, url: e.target.value })
                                                        setError({...error, url:false})
                                                    }}
                                                />
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Button
                                    color="primary"
                                    className={classes.submitBtn}
                                    variant="contained"
                                    size = {'large'}
                                    
                                    onClick={handleSubmitDataset}
                                >
                                    Submit
                                </Button>
                            </FormControl>
                        </Grid>
                    </Grid>
                </Paper>
            </div>
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

export default withStyles(DatasetStyle)(SubmitDataset);