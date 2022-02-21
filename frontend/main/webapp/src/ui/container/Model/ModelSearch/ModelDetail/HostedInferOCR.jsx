import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useHistory } from "react-router";
// import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import UrlConfig from "../../../../../configs/internalurlmapping";
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import AudioRecord from "./VoiceRecorder";
import Spinner from "../../../../components/common/Spinner";
import {
  Grid,
  Typography,
  TextField,
  Button,
  CardContent,
  Card,
  CardActions,
  CardMedia,
  Modal,
  Backdrop,
  Fade ,
  
  
} from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import { useState } from "react";
import OCRModal from "./OCRModal";
import { translate } from "../../../../../assets/localisation";
import OCRFileUpload from "../../../../../redux/actions/api/Model/ModelSearch/FileUpload";
import { useDispatch } from "react-redux";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";
import Snackbar from "../../../../components/common/Snackbar";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";



const HostedInferASR = (props) => {
  
  const [openModal, setOpenModal] = useState(false);
  
  const { classes, title, para, modelId, task, source, inferenceEndPoint } =
    props;
  const history = useHistory();
  
  const [url, setUrl] = useState("");
  const [apiCall, setApiCall] = useState(false);
  const [error, setError] = useState({ url: "" });
  const [preview, setPreview] = useState("");
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [translation, setTranslationState] = useState(false);
  const [target, setTarget] = useState("");
  const [fileData, setFileData] = useState("");
  const [targetAudio, setTargetAudio] = useState("");
  const handleCompute = () => setTranslationState(true);
  const [open, setOpen] = useState(false);
  const dispatch = useDispatch();
  // const url = UrlConfig.dataset
  const handleClose = () => {
    setOpen(false);
  };

  const [file, setFile] = useState([]);
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
    let apiObj = new HostedInferenceAPI(
      modelId,
      url,
      task,
      status,
      source,
      inferenceEndPoint
    );
    setApiCall(true);
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
            message:
              "The model is not accessible currently. Please try again later",
            timeOut: 40000,
            variant: "error",
          });
        } else {
          if (rsp_data.hasOwnProperty("outputText") && rsp_data.outputText) {
            setTarget(rsp_data.outputText);
            //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
            setTranslationState(true);
          }
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

  const handleFile = (e) => {
    if (e.target.files) {
      const blob = URL.createObjectURL(e.target.files[0]);
      setPreview(blob);
      setFile(e.target.files);
    }
  };

  const handleFileSubmit = () => {
    setFileData("");
    setSnackbarInfo({
      ...snackbar,
      open: true,
      message: "Please wait while we process your request...",
      variant: "info",
    });
    const obj = new OCRFileUpload(file, modelId);
    // dispatch(APITransport(obj));
    fetch(obj.apiEndPoint(), {
      method: "post",
      body: obj.getFormData(),
    }).then(async (res) => {
      let rsp_data = await res.json();
      if (res.ok) {
        setFileData(rsp_data.outputText);
      } else {
        setSnackbarInfo({
          ...snackbar,
          open: true,
          message: rsp_data.message,
          variant: "error",
        });
      }
    });
  };

  const Imagemodal = () => {
      setOpenModal(true);

  }

 

  const handleCloseModal = (event, reason) => {
    if ("clickaway" == reason) return;
    setOpenModal(false);
  };


  return (
    <>
      <Grid container>
        {apiCall && <Spinner />}
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
                Upload image from local drive
              </Typography>
            </Grid>
            <CardContent>
              <TextField
                style={{ marginTop: "15px" }}
                fullWidth
                variant="outlined"
                color="primary"
                // label="Paste the public repository URL"
                onChange={handleFile}
                type="file"
              />
                <Button
                color="primary"
                style={{ float: "right", marginTop: "5px" }}
                disabled={file.length ? false : true}
                variant="contained"
                size={"small"}
                onClick={handleFileSubmit}
              >
                {translate("button.convert")}
              </Button>
              {preview ? (
                <>
                <img
                  style={{  margin: "10px",   cursor: "pointer",
                  maxWidth: "-webkit-fill-available",
                  border: "1px solid black" }}
                  src={preview}
                  alt="Preview"
                  width="100%"
                  //height="10px"
                  onClick={Imagemodal}

                />
                  <Modal
                  aria-labelledby="transition-modal-title"
                  aria-describedby="transition-modal-description"
                  className={classes.imagemodal}
                  open={openModal}
                  onClose={handleCloseModal}
                 
                  closeAfterTransition
                  BackdropComponent={Backdrop}
                  BackdropProps={{
                  timeout: 500,

              }}
                 >
              <Fade in={openModal}>
             
             <div className={classes.imagepaper}>
             <div   style={{paddingLeft:"93%",paddingBottom:"20px"}}>
              <IconButton
                 
                 
                     size="small"
                     aria-label="close"
                     color="inherit"
                     onClick={handleCloseModal}
                   >
                     <CloseIcon fontSize="small" />
                   </IconButton>
                   </div>
             
              <img
                  style={{ maxWidth: 550 ,}}
                  src={preview}
                  alt="Preview"
                 
                 
            />
           
         
          </div>
        
         
        </Fade>
      </Modal>
               </>
              ) : (
                <></>
              )}
              
              {/* <Button
                color="primary"
                style={{ float: "right", marginTop: "10px" }}
                disabled={file.length ? false : true}
                variant="contained"
                size={"small"}
                onClick={handleFileSubmit}
              >
                {translate("button.convert")}
              </Button> */}
            </CardContent>
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
            <CardContent>{fileData}</CardContent>
          </Card>
        </Grid>
      </Grid>
      <Grid container>
        {apiCall && <Spinner />}
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
                Image URL
              </Typography>
            </Grid>
            <CardContent>
              <TextField
                style={{ marginTop: "15px " }}
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
              <Button
                color="primary"
                style={{ float: "right", marginTop: "5px" }}
                disabled={url ? false : true}
                variant="contained"
                size={"small"}
                onClick={handleSubmit}
              >
                {translate("button.convert")}
              </Button>
            </CardContent>
            {url && (
              <img
                src={url}
                alt="Ocr URL"
                onClick={() => setOpen(true)}
                style={{
                  margin: "10px ",
                  cursor: "pointer",
                  maxWidth: "-webkit-fill-available",
                  border: "1px solid black",
                }}
              />
            )}
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
            <CardContent>{target}</CardContent>
          </Card>
        </Grid>
      </Grid>
      {open && (
        <OCRModal
          open={open}
          message={url}
          handleClose={handleClose}
          title="Ocr Image"
        />
      )}
      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
          hide="6000"
        />
      )}
    </>
  );
};
export default withStyles(DatasetStyle)(HostedInferASR);
