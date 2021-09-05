import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import Autocomplete from '@material-ui/lab/Autocomplete';
import Spinner from "../../../../components/common/Spinner";
import { getLanguageName } from '../../../../../utils/getLabel';
import {
    Grid,
    Typography,
    TextField,
    Button,
    CircularProgress,
    CardContent, Card, CardActions, CardHeader
} from '@material-ui/core';
import { useState } from 'react';
import { identifier } from '@babel/types';
import Snackbar from '../../../../components/common/Snackbar';

const HostedInference = (props) => {
    const { classes, title, para, modelId, task } = props;
    const history = useHistory();
    const [translation, setTranslationState] = useState(false)
    const [sourceText, setSourceText] = useState("");
    const [loading, setLoading] = useState(false);
    const [target, setTarget] = useState("")
    const [sourceLanguage, setSourceLanguage] = useState(
        { value: 'en', label: 'English' }
    );
    const srcLang = getLanguageName(props.source);
    const tgtLang = getLanguageName(props.target);

    // useEffect(() => {
    // 	fetchChartData(selectedOption.value,"", [{"field": "sourceLanguage","value": sourceLanguage.value}])
    // }, []);
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: '',
        variant: 'success'
    })
    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false })
    }
    const clearAll = () => {
        setSourceText("");
        setTarget("")
    }
    const handleCompute = () => {
        setLoading(true);
        const apiObj = new HostedInferenceAPI(modelId, sourceText, task, false);
        fetch(apiObj.apiEndPoint(), {
            method: 'POST',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        }).then(async resp => {
            let rsp_data = await resp.json();
            setLoading(false)
            if (resp.ok) {
                if (rsp_data.hasOwnProperty('outputText') && rsp_data.outputText) {
                    setTarget(rsp_data.outputText)
                    //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
                    setTranslationState(true)
                }
            } else {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: "The model is not accessible currently. Please try again later",
                    variant: 'error'
                })
                Promise.reject(rsp_data);
            }
        }).catch(err => {
            setLoading(false)
            setSnackbarInfo({
                ...snackbar,
                open: true,
                message: "The model is not accessible currently. Please try again later",
                variant: 'error'
            })
        })
    }
    // const renderTexfield = () => {
    //     //  let labels = Language.map(lang => lang.label)
    //     return (
    //         <Autocomplete
    //            // className={classes.titleDropdown}
    //             value={"English"}
    //             id="source"
    //             disabled
    //             //  options={labels}
    //             // onChange={(event, data) => handleLanguagePairChange(data, 'source')}
    //           //  renderInput={(params) => <TextField fullWidth {...params} variant="standard"
    //             // />}
    //         />
    //     )
    // }
    // const handleLanguagePairChange = (value, property) => {
    // 	let sLang =  Language.filter(val => val.label ===value )[0]
    // 	if(sLang){
    // 		fetchChartData(selectedOption.value, "", [{"field": "sourceLanguage","value":  sLang.value}])
    //     setSourceLanguage(sLang);
    // 	}
    // };


    return (
        // <div>
        //<Grid container spacing={2}>
        <Grid className={classes.gridCompute} item xl={12} lg={12} md={12} sm={12} xs={12}>
            {loading && <Spinner />}
            <Card className={classes.hostedCard}>
                <CardContent className={classes.translateCard}>
                    <Grid container className={classes.cardHeader}>
                        <Grid item xs={4} sm={4} md={4} lg={4} xl={4} className={classes.headerContent}>
                            <Typography variant='h6' className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
                        </Grid>
                        <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                            {/* <Autocomplete
                                disabled
                                options={['English']}
                                value={'English'}
                                renderInput={(params) => <TextField {...params} variant="standard" />}
                            /> */}
                            <Typography variant='h6' className={classes.hosted}>{srcLang}</Typography>
                        </Grid>
                    </Grid>
                </CardContent>
                <CardContent>
                    <textarea
                        value={sourceText}
                        rows={5}
                        // cols={40}
                        className={classes.textArea}
                        placeholder="Enter Text"
                        onChange={(e) => {
                            setSourceText(e.target.value);
                        }}
                    />
                </CardContent>

                <CardActions className={classes.actionButtons} >
                    <Grid container spacing={2}>
                        <Grid item>
                            <Button disabled={sourceText ? false : true} size="small" variant="outlined" onClick={clearAll}>
                                Clear All
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button
                                color="primary"
                                className={classes.computeBtn}
                                variant="contained"
                                size={'small'}
                                onClick={handleCompute}
                                disabled={sourceText ? false : true}>
                                Translate
                            </Button>
                        </Grid>
                    </Grid>
                </CardActions>
            </Card>
            <Card className={classes.translatedCard}>
                <CardContent className={classes.translateCard}>
                    <Grid container className={classes.cardHeader}>
                        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.headerContent}>
                            {/* <Autocomplete
                                disabled
                                options={['Hindi']}
                                value={'Hindi'}
                                renderInput={(params) => <TextField {...params} variant="standard" />}
                            /> */}
                            <Typography variant='h6' className={classes.hosted}>{tgtLang}</Typography>
                        </Grid>
                    </Grid>
                </CardContent>
                <CardContent>
                    <textarea
                        disabled
                        placeholder='Output'
                        rows={6}
                        value={target}
                        className={classes.textArea}
                    />
                </CardContent>
            </Card>
            {/* <TextField fullWidth
                        color="primary"
                        label="Enter Text"
                        value={sourceText}
                        // error={error.name ? true : false}
                        // helperText={error.name}
                        onChange={(e) => {
                            setSourceText(e.target.value);
                        }}
                    /> */}
            {snackbar.open &&
                <Snackbar
                    open={snackbar.open}
                    handleClose={handleSnackbarClose}
                    anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                    message={snackbar.message}
                    variant={snackbar.variant}
                />}
        </Grid>
        //  </Grid>


        //   </div>

    )
}
export default withStyles(DatasetStyle)(HostedInference);