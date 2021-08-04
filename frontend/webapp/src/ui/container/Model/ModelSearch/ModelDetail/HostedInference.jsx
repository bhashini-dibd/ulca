import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import Autocomplete from '@material-ui/lab/Autocomplete';
import {
    Grid,
    Typography,
    TextField,
    Button,
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
    const [target, setTarget] = useState("")
    const [sourceLanguage, setSourceLanguage] = useState(
        { value: 'en', label: 'English' }
    );
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
        const apiObj = new HostedInferenceAPI(modelId, sourceText, task);
        fetch(apiObj.apiEndPoint(), {
            method: 'POST',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        }).then(async resp => {
            let rsp_data = await resp.json();
            if (resp.ok) {
                if (rsp_data.hasOwnProperty('translation') && rsp_data.translation) {
                    setTarget(rsp_data.translation.output[0].target)
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
            console.log(err)
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
            <Card className={classes.hostedCard}>
                <CardContent>
                    <Typography variant='body1' className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
                    <div className={classes.dropDownStyle}>
                        {/* {renderTexfield()} */}
                    </div>
                    <textarea
                        value={sourceText}
                        rows={6}
                        // cols={40}
                        className={classes.textArea}
                        placeholder="Enter Text"
                        onChange={(e) => {
                            setSourceText(e.target.value);
                        }}
                    />
                </CardContent>

                    <CardActions style={{marginBottom:'13px', float:'right'}}>
                        <Grid container spacing={2}>
                            <Grid item>
                                <Button size="small" variant="outlined" onClick={clearAll}>
                                    Clear
                                </Button>
                            </Grid>
                            <Grid item>
                                <Button
                                    color="primary"
                                    className={classes.computeBtn}
                                    variant="contained"
                                    size={'small'}

                                    onClick={handleCompute}
                                >
                                    Translate
                                </Button>
                            </Grid>
                        </Grid>
                    </CardActions>
            </Card>
            <Card className={classes.translatedCard}>
                <CardContent>
                    <textarea
                        disabled
                        rows={8}
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