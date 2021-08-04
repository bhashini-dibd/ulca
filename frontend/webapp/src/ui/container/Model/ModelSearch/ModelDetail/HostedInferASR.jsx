import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import UrlConfig from '../../../../../configs/internalurlmapping';
import HostedInferenceAPI from '../../../../../redux/actions/api/Model/ModelSearch/HostedInference';

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
    const [target,setTarget] = useState("");
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
                    message:"The model is not accessible currently. Please try again later",
                    timeOut: 40000,
                    variant: 'error'
                })
            }
            else {
                if(rsp_data.hasOwnProperty('asr') && rsp_data.asr){
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
        <div>
            <Typography className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
            <Grid container spacing={2}>
                <Grid className={classes.gridCompute} item xl={8} lg={8} md={8} sm={8} xs={8}>
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
        </div>

    )
}
export default withStyles(DatasetStyle)(HostedInferASR);