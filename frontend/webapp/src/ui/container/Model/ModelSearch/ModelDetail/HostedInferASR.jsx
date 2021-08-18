import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useHistory } from "react-router";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import UrlConfig from "../../../../../configs/internalurlmapping";
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import AudioRecord from "./VoiceRecorder";
import Spinner from "../../../../components/common/Spinner"
import {
  Grid,
  Typography,
  TextField,
  Button,
  CardContent,
  Card,
  CardActions,
} from "@material-ui/core";
import { useState } from "react";

const HostedInferASR = (props) => {
  const { classes, title, para, modelId, task, source, inferenceEndPoint } = props;
  const history = useHistory();
  const [url, setUrl] = useState("");
  const [apiCall, setApiCall] = useState(false);
  const [error, setError] = useState({ url: "" });
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [translation, setTranslationState] = useState(false);
  const [target, setTarget] = useState("");
  const [targetAudio, setTargetAudio] = useState("");
  const handleCompute = () => setTranslationState(true);
  // const url = UrlConfig.dataset
  const handleClose = () => {
    // setAnchorEl(null);
  };
  const validURL = (str) => {
    var pattern = new RegExp(
      "^((ft|htt)ps?:\\/\\/)?" + // protocol
      "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // domain name and extension
      "((\\d{1,3}\\.){3}\\d{1,3}))" + // OR ip (v4) address
      "(\\:\\d+)?" + // port
      "(\\/[-a-z\\d%@_.~+&:]*)*" + // path
      "(\\?[;&a-z\\d%@_.,~+&:=-]*)?" + // query string
      "(\\#[-a-z\\d_]*)?$",
      "i"
    );
    return pattern.test(str);
  };
  const handleSubmit = (e) => {
    if (!validURL(url)) {
      setError({ ...error, url: "‘Invalid URL" });
    } else {
      handleApicall(modelId, url, task);
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please wait while we process your request...",
        variant: "info",
      });
    }
  };
  const handleApicall = async (modelId, url, task, status = false) => {

    let apiObj = new HostedInferenceAPI(modelId, url, task, status, source, inferenceEndPoint);
    setApiCall(true)
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers,
    })
      .then(async (response) => {
        const rsp_data = await response.json();
        setApiCall(false)
        if (!response.ok) {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message:
              "The model is not accessible currently. Please try again later",
            timeOut: 40000,
            variant: "error",
          });
        } else {
          if (status) {
            setTargetAudio(rsp_data.data.transcript);

          }
          else {
            setTarget(rsp_data.data.transcript);
          }



          setTranslationState(true);
        }

      })
      .catch((error) => {
        setApiCall(false)
        setSnackbarInfo({
          ...snackbar,
          open: true,
          message:
            "The model is not accessible currently. Please try again later",
          timeOut: 40000,
          variant: "error",
        });
      });
  };

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };
  return (
    <Grid container>

      {apiCall && <Spinner />}
      {/* <Typography className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography> */}

      <Grid
        className={classes.grid}
        item
        xl={5}
        lg={5}
        md={5}
        sm={12}
        xs={12}
      >
        <AudioRecord modelId={modelId} handleApicall={handleApicall} />
      </Grid>
      <Grid
        className={classes.grid}
        item
        xl={6}
        lg={6}
        md={6}
        sm={12}
        xs={12}
      >
        <Card className={classes.asrCard}>
          <Grid container className={classes.cardHeader}>
            <Typography variant='h6' className={classes.titleCard}>Output</Typography>
          </Grid>
          <CardContent>{targetAudio}</CardContent>
        </Card>
      </Grid>

      <Typography variant={"body1"}>Disclaimer : </Typography>

      <Typography style={{width:"95%"}} variant={"body2"}>Transcription is best if you directly speak into the microphone and the performance might not be the same if you use it over a conference call.</Typography>

      <Grid
        className={classes.grid}
        item
        xl={5}
        lg={5}
        md={5}
        sm={12}
        xs={12}
      >
        <Card className={classes.asrCard}>
          <Grid container className={classes.cardHeader}>
            <Typography variant='h6' className={classes.titleCard}>Notes</Typography>
          </Grid>
          <CardContent>
            <Typography variant={"caption"}>Max duration: 1 min</Typography>
            <TextField
              style={{ marginTop: "15px", marginBottom: "10px"}}
              fullWidth
              color="primary"
              label="Paste the public repository URL"
              value={url}
              error={error.url ? true : false}
              helperText={error.url}
              onChange={(e) => {
                setUrl(e.target.value);
                setError({ ...error, url: false });
              }}
            />
          </CardContent>
          <CardActions
            style={{ justifyContent: "flex-end", paddingRight: "20px" }}
          >
            <Button
              color="primary"
              className={classes.computeBtnUrl}
              disabled={url ? false : true}
              variant="contained"
              size={"small"}
              onClick={handleSubmit}
            >
              Convert
            </Button>
          </CardActions>
        </Card>
      </Grid>
      <Grid
        className={classes.grid}
        item
        xl={6}
        lg={6}
        md={6}
        sm={12}
        xs={12}
      >
        <Card className={classes.asrCard}>
          <Grid container className={classes.cardHeader}>
            <Typography variant='h6' className={classes.titleCard}>Output</Typography>
          </Grid>
          <CardContent>{target}</CardContent>
        </Card>
      </Grid>
    </Grid>
  );
};
export default withStyles(DatasetStyle)(HostedInferASR);
