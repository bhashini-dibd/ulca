import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useHistory } from "react-router";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import UrlConfig from "../../../../../configs/internalurlmapping";
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import AudioRecord from "./VoiceRecorder";
import Spinner from "../../../../components/common/Spinner";
import FeedbackPopover from "../../../../components/common/FeedbackTTranslation";
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import Modal from '../../../../components/common/Modal';
import {
  Grid,
  Typography,
  TextField,
  Button,
  CardContent,
  Card,
  CardActions,
} from "@material-ui/core";
import { useEffect, useState } from "react";
import { translate } from "../../../../../assets/localisation";
import Snackbar from "../../../../components/common/Snackbar";
import SubmitFeedback from "../../../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";
import ALDVoiceRecorder from "./ALDVoiceRecorder";
import { useSelector } from "react-redux";

const HostedInferAGD = (props) => {
  console.log(props,"--------------->>>>jhg");
  
  const {
    classes,
    title,
    para,
    modelId,
    task,
    source,
    inferenceEndPoint,
    language,
  } = props;
  const history = useHistory();
  const [data, setData] = useState(null);
  const [base, setBase64] = useState("")
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
  const [suggestEditValues, setSuggestEditValues] = useState("")
  const [feedbackAudioInput, setFeedbackAudioInput] = useState("");
  const [isUrl, setIsUrl] = useState(false);
  const { languages } = useSelector((state) => state.getMasterData);
  const [modal, setModal] = useState(false);
  const [targetLang, setTargetLang] = useState("");
  const [targetAudioLang, setTargetAudioLang] = useState("");
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
      handleApicall(modelId, url, task,true);
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please wait while we process your request...",
        variant: "info",
      });
    }
  };
  const handleApicall = async (modelId, url, task,activeset, status = false) => {
    let apiObj = new HostedInferenceAPI(
      modelId,
      url,
      task,
      status,
      source,
      inferenceEndPoint
    );
    setApiCall(true);
    setBase64(url);
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers,
    })
      .then(async (response) => {
        const rsp_data = await response.json();
        setApiCall(false);
        if (!response.ok) {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.message,
            timeOut: 40000,
            variant: "error",
          });
        } else {
            setSnackbarInfo({
              ...snackbar,
              open: false,
              message: "",
              timeOut: 40000,
              variant: "success",
            });
            const language = rsp_data.output[0].genderPrediction.map((data) => {
              return data.gender;
            });
            console.log(activeset,language,"gend");
            
            const capitalizeFirstChar = (str) => {
              if (!str) return ''; // Handle empty or undefined strings
              return str.charAt(0).toUpperCase() + str.slice(1);
            };
           
            if (activeset) {
              setTargetLang(capitalizeFirstChar(language.toString()));
            } else {
              setTargetAudioLang(capitalizeFirstChar(language.toString()));
            }
            
          // if (status) {
          //   setTargetAudio(rsp_data.data.source);
          // } else {
          //   setSnackbarInfo({
          //     ...snackbar,
          //     open: false,
          //     message: "",
          //     timeOut: 0,
          //     variant: "",
          //   });
          //   setTarget(rsp_data.data.source);
          // }
          setTranslationState(true);
        }
      })
      .catch((error) => {
        setApiCall(false);
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

  const getLabel = (code) => {
    const detectedLanguage = languages.filter((lang) => lang.code === code);
    return detectedLanguage[0]?.label;
  };


  const childData = (text, base64 = "") => {
    setData(text)
  }

  const handleFeedbackSubmit = (feedback) => {

    let apiObj;
    if (isUrl) {
      apiObj = new SubmitFeedback(
        "asr",
        `data:text/uri;${url}`,
        targetAudio,
        feedback,
        [],
        modelId
      );
    } else {
      apiObj = new SubmitFeedback(
        "asr",
        feedbackAudioInput,
        targetAudio,
        feedback,
        [],
        modelId
      );
    }

    fetch(apiObj.apiEndPoint(), {
      method: 'post',
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    })
      .then(async resp => {
        const rsp_data = await resp.json();
        if (resp.ok) {
          setSnackbarInfo({ open: true, message: rsp_data.message, variant: 'success' })
        } else {
          setSnackbarInfo({ open: true, message: rsp_data.message, variant: 'error' })
        }
      });
    setTimeout(() => setSnackbarInfo({ open: false, message: "", variant: null }), 3000);
  }

  const handleOnChange = (e) => {
    setSuggestEditValues(e.target.value)
  }

  return (
    <>
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

          <ALDVoiceRecorder
            submitter={props.submitter}
            modelId={modelId}
            handleApicall={handleApicall}
            language={language}
            task={task}
            streaming={props.streaming}
            getchildData={childData}
            feedback={{ setTarget, setTargetAudio, setData }}
            inferenceEndPoint={inferenceEndPoint}
            setFeedbackAudioInput={setFeedbackAudioInput}
          />
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
              <Typography variant="h6" className={classes.titleCard}>
                {translate("label.output")}
              </Typography>
            </Grid>


            <CardContent id="aldCardOutput" className={classes.Asrcard}>{targetAudioLang}</CardContent>
            {/* {targetAudio.length > 0 && (<>
              <div    >
                <Button variant="contained" size="small"  className={classes.Asrfeedback}  onClick={() => { setModal(true); setSuggestEditValues(targetAudio); setIsUrl(false); }}>
                  <ThumbUpAltIcon className={classes.feedbackIcon} />
                  <ThumbDownAltIcon className={classes.feedbackIcon} />
                  <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
                </Button>
              </div>


            </>)} */}

            {/* {data && (


              <div    >
                <Button variant="contained" size="small" className={classes.Asrfeedback} onClick={() => { setModal(true); setSuggestEditValues(data); setIsUrl(false); }}>
                  <ThumbUpAltIcon className={classes.feedbackIcon} />
                  <ThumbDownAltIcon className={classes.feedbackIcon} />
                  <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
                </Button>

              </div>)} */}
          </Card>
        </Grid>

        <Typography variant={"body1"}>
          {translate("label.disclaimer")}
        </Typography>

        <Typography style={{ width: "95%" }} variant={"body2"}>
          {translate("label.transcriptionNote")}
        </Typography>

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
              <Typography variant="h6" className={classes.titleCard}>
                {translate("label.notes")}
              </Typography>
            </Grid>
            <CardContent>
              <Typography variant={"caption"}>
                {translate("label.maxDuration")}
              </Typography>
              <TextField
                style={{ marginTop: "15px", marginBottom: "10px" }}
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
                {translate("button.convert")}
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
              <Typography variant="h6" className={classes.titleCard}>
                {translate("label.output")}
              </Typography>
            </Grid>
            <CardContent><textarea
              rows={5}
              className={classes.textareas}
              value={targetLang}
              /></CardContent>
            {/* {targetLang.length > 0 && (<><CardContent><textarea
              rows={5}
              className={classes.textareas}
              value={targetLang}
              /></CardContent>
              <div >
                <Button variant="contained" size="small" style={{ float: "right", marginTop: "-5px", marginRight: "20px", backgroundColor: "#FD7F23" }} onClick={() => { setModal(true); setSuggestEditValues(target); setIsUrl(true); }}>
                  <ThumbUpAltIcon className={classes.feedbackIcon} />
                  <ThumbDownAltIcon className={classes.feedbackIcon} />
                  <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
                </Button>

              </div></>)} */}

          </Card>
        </Grid>
      </Grid>
      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )}
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <FeedbackPopover
          setModal={setModal}
          suggestion={true}
          target={targetAudio}
          suggestEditValues={suggestEditValues}
          handleOnChange={handleOnChange}
          setSuggestEditValues={setSuggestEditValues}
          taskType='asr'
          handleSubmit={handleFeedbackSubmit}
        />
      </Modal>
    </>
  );
};
export default withStyles(DatasetStyle)(HostedInferAGD);
