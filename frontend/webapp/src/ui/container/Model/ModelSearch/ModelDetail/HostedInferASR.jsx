import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import UrlConfig from '../../../../../configs/internalurlmapping';
import HostedInferenceAPI from '../../../../../redux/actions/api/Model/ModelSearch/HostedInference';
import AudioRecord from './VoiceRecorder';
import {
    Grid,
    Typography,
    TextField,
    Button,
    CardContent, Card
} from '@material-ui/core';
import { useState } from 'react';

const HostedInferASR = (props) => {
    const { classes, title, para, modelId, task } = props;
    const history = useHistory();
    const [url, setUrl] = useState("");
    const [error, setError] = useState({ url: "" })
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: '',
        variant: 'success'
    })
    const [translation, setTranslationState] = useState(false);
    const [target, setTarget] = useState("");
    const handleCompute = () => setTranslationState(true);
    // const url = UrlConfig.dataset
    const handleClose = () => {
        // setAnchorEl(null);
    };
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
    const handleSubmit = (e) => {
        if (!validURL(url)) {
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
    const handleApicall = async () => {
        let apiObj = new HostedInferenceAPI(modelId, url, task)
        fetch(apiObj.apiEndPoint(), {
            method: 'post',
            body: JSON.stringify(apiObj.getBody()),
            headers: apiObj.getHeaders().headers
        }).then(async response => {
            const rsp_data = await response.json();
            if (!response.ok) {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message: "The model is not accessible currently. Please try again later",
                    timeOut: 40000,
                    variant: 'error'
                })
            }
            else {
                if (rsp_data.hasOwnProperty('asr') && rsp_data.asr) {
                    setTarget(rsp_data.asr.output[0].target)
                    setTranslationState(true)
                }
            }
        }).catch((error) => {
            setSnackbarInfo({
                ...snackbar,
                open: true,
                message: "The model is not accessible currently. Please try again later",
                timeOut: 40000,
                variant: 'error'
            })
        });

    }

    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false })
    }

    return (
        <Grid container >

            {/* <Typography className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography> */}




            <Grid className={classes.gridCompute} item xl={5} lg={5} md={5} sm={5} xs={5}><AudioRecord /></Grid>
            <Grid className={classes.gridCompute} item xl={6} lg={6} md={6} sm={6} xs={6} >

                <Card className={classes.asrCard}>
                    <CardContent>
                        <textarea
                            disabled
                            rows={6}
                            value={target}
                            className={classes.textArea}
                        />
                    </CardContent>
                </Card>


            </Grid>
            <Grid className={classes.gridCompute} item xl={5} lg={5} md={5} sm={5} xs={5} >
                <Card className={classes.asrCard}>
                    <CardContent>
                        <TextField fullWidth

                            color="primary"
                            label="Paste the URL of the public repository"
                            value={url}
                            error={error.url ? true : false}
                            helperText={error.url}
                            onChange={(e) => {
                                setUrl(e.target.value)
                                setError({ ...error, url: false })
                            }}
                        />
                    </CardContent>
                </Card>

            </Grid>
            <Grid item xl={4} lg={4} md={4} sm={4} xs={4} className={classes.computeGrid}>
                <Button
                    color="primary"
                    className={classes.computeBtn}
                    variant="contained"
                    size={'small'}

                    onClick={handleSubmit}
                >
                    Convert
                </Button>
            </Grid>

            {translation &&
                // <Grid item xl={11} lg={11} md={12} sm={12} xs={12}>
                <Card style={{ backgroundColor: '#139D601A', color: 'black', heigth: '50px', width: '440px' }}>
                    <CardContent style={{ paddingBottom: '16px' }}>
                        {target}
                    </CardContent>
                </Card>
                // </Grid>
            }

        </Grid>
    )
}
export default withStyles(DatasetStyle)(HostedInferASR);