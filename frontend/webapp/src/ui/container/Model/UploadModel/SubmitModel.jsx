import {
    Grid,
    Paper,
    Typography,
    Divider,
    FormControl,
    Button,
    TextField,
    Link,
    Hidden
} from '@material-ui/core';
// import Autocomplete from '@material-ui/lab/Autocomplete';
import BreadCrum from '../../../components/common/Breadcrum';
import { withStyles } from '@material-ui/core/styles';
import { RadioButton, RadioGroup } from 'react-radio-buttons';
import DatasetStyle from '../../../styles/Dataset';
import { useState } from 'react';
import { useHistory } from "react-router-dom";
import Snackbar from '../../../components/common/Snackbar';
import UrlConfig from '../../../../configs/internalurlmapping';
import SubmitModelApi from "../../../../redux/actions/api/Model/UploadModel/SubmitModel"
import C from "../../../../redux/actions/constants";
import { useDispatch } from 'react-redux';
import { PageChange } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction"

const SubmitModel = (props) => {
    const { classes } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const [model, setModelInfo] = useState({ modelName: "", url: "" })
    const dispatch = useDispatch();
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: '',
        variant: 'success'
    })
    const [error, setError] = useState({ modelName: "", url: "", type: false })
    const [search, setSearch] = useState(false)
    const history = useHistory();

    // const handleClick = (event) => {
    //     setAnchorEl(event.currentTarget)
    // };

    const handleClose = () => {
        setAnchorEl(null);
    };

    // const handleDone = () => {
    //     if (dataset.filteredName) {
    //         setDatasetInfo({ ...dataset, datasetName: dataset.filteredName })
    //     }
    //     handleClose();
    // }

    // const renderUpdateDatasetSearch = () => {
    //     return (
    //         <div>
    //             <div className={classes.updateDataset}>
    //                 <Grid container spacing={1}>
    //                     <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
    //                         <Autocomplete
    //                             id="tags-outlined"
    //                             options={[]}
    //                             getOptionLabel={(option) => option.name}
    //                             filterSelectedOptions
    //                             open={search}
    //                             onChange={(e, value) => {
    //                                 setDatasetInfo({ ...dataset, datasetName: value.name})
    //                                 handleClose();
    //                             }}
    //                             onOpen={() => {
    //                                 setTimeout(() => setSearch(true), 200)
    //                             }}
    //                             onClose={() => {
    //                                 setSearch(false)
    //                             }}
    //                             openOnFocus
    //                             renderInput={(params) => (
    //                                 <TextField
    //                                     id="search-dataset"
    //                                     variant="outlined"
    //                                     placeholder="Search Dataset"
    //                                     autoFocus={true}
    //                                     {...params}
    //                                 />
    //                             )}
    //                         />
    //                     </Grid>
    //                 </Grid>
    //             </div>

    //         </div>
    //     )
    // }

    const handleApicall = async () => {
        

        let apiObj = new SubmitModelApi(model)
        fetch(apiObj.apiEndPoint(), {
            method: 'post',
            body: JSON.stringify(apiObj.getBody()),
            headers: apiObj.getHeaders().headers
        }).then(async response => {
            const rsp_data = await response.json();
            if (!response.ok) {
                setSnackbarInfo ({
                    ...snackbar,
                    open: true,
                    message: rsp_data.message ? rsp_data.message : "Something went wrong. Please try again.",
                    timeOut: 40000,
                    variant: 'error'
                })
                if(response.status===401){
                    setTimeout(()=>history.push(`${process.env.PUBLIC_URL}/user/login`),3000)}
                }
                
                
             else {
                dispatch(PageChange(0, C.PAGE_CHANGE));
                history.push(`${process.env.PUBLIC_URL}/model/submission/${rsp_data.data.serviceRequestNumber}`)
                //           return true;
            }
        }).catch((error) => {
            setSnackbarInfo({
                ...snackbar,
                open: true,
                message: "Something went wrong. Please try again.",
                timeOut: 40000,
                variant: 'error'
            })
        });

    }

   

    const renderInstructions = () => {
        return <div className={classes.list} >
            <ul>
            <li><Typography className={classes.marginValue} variant="body2">Provide a meaningful name to your model.</Typography></li>
            <li><Typography className={classes.marginValue} variant="body2">Provide the URL where the model is stored at.</Typography></li>
            <li><Typography className={classes.marginValue} variant="body2">Make sure the URL is a direct download link.</Typography></li>
            <li><Typography className={classes.marginValue} variant="body2">If your model is stored in Google Drive, use<Link id="newaccount" href="https://sites.google.com/site/gdocs2direct/home">{" "}
              https://sites.google.com/site/gdocs2direct/home {" "}
            </Link>to generate a direct download link.</Typography></li>
            <li><Typography className={classes.marginValue} variant="body2">Make sure the model is available in .zip format.</Typography></li>
            </ul>
        </div>
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

    const handleSubmitModel = (e) => {
        if (model.modelName.trim() === "" || model.url.trim() === "") {
            setError({ ...error, name: !model.modelName.trim() ? "Name cannot be empty" : "", url: !model.url.trim() ? "URL cannot be empty" : "" })

        }
        else if (model.modelName.length > 256) {
            setError({ ...error, name: "Max 256 characters allowed" })

        }
        else if (!validURL(model.url)) {
            setError({ ...error, url: "‘Invalid URL" })
        }
        else {

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

    return (
        <div>
            <div >
                <div className={classes.breadcrum}>
                    <BreadCrum links={[UrlConfig.model]} activeLink="Submit Model" />
                </div>
                <Paper elevation={3} className={classes.divStyle}>
                   
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={12} md={5} lg={5} xl={5}>
                           
                            <FormControl className={classes.form}>
                                <Typography className={classes.typography} variant="subtitle1">How to submit Model?</Typography>
                                    {renderInstructions()}
                            </FormControl>
                        </Grid>
                        <Hidden>
                            <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                                <Divider orientation="vertical" />
                            </Grid>
                        </Hidden>
                        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                        
                            <FormControl className={classes.form}>
                                <Grid container spacing={6}>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={5}>
                                            <Grid item xl={5} lg={5} md={5} sm={12} xs={12}>
                                                <Typography className={classes.typography} variant="subtitle1">Submit Model</Typography>
                                            </Grid>
                                            {/* <Grid item xl={7} lg={7} md={7} sm={12} xs={12}>
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
                                            </Grid> */}
                                        </Grid>
                                    </Grid>
                                    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                        <Grid container spacing={3}>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth

                                                    color="primary"
                                                    label="Model name"
                                                    value={model.modelName}
                                                    error={error.name ? true : false}
                                                    helperText={error.name}
                                                    onChange={(e) => {
                                                        setModelInfo({ ...model, modelName: e.target.value })
                                                        setError({ ...error, name: false })
                                                    }}
                                                />
                                            </Grid>
                                            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                <TextField fullWidth

                                                    color="primary"
                                                    label="Paste the URL of the public repository"
                                                    value={model.url}
                                                    error={error.url ? true : false}
                                                    helperText={error.url}
                                                    onChange={(e) => {
                                                        setModelInfo({ ...model, url: e.target.value })
                                                        setError({ ...error, url: false })
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
                                    size={'large'}

                                    onClick={handleSubmitModel}
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

export default withStyles(DatasetStyle)(SubmitModel);