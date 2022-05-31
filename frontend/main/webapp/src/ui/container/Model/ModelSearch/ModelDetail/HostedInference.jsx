import { Tooltip } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useHistory } from "react-router";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import Autocomplete from "@material-ui/lab/Autocomplete";
import Spinner from "../../../../components/common/Spinner";
import { getLanguageName } from "../../../../../utils/getLabel";
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import Modal from '../../../../components/common/Modal';

import {
  Grid,
  Typography,
  TextField,
  Button,
  CircularProgress,
  CardContent,
  Card,
  CardActions,
  CardHeader,
} from "@material-ui/core";
import { useState } from "react";
import { identifier } from "@babel/types";
import Snackbar from "../../../../components/common/Snackbar";
import { translate } from "../../../../../assets/localisation";
import LightTooltip from "../../../../components/common/LightTooltip";
import FeedbackPopover from "../../../../components/common/FeedbackTTranslation";
import SubmitFeedback from "../../../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";

const HostedInference = (props) => {
  const { classes, title, para, modelId, task } = props;
  const history = useHistory();
  const [translation, setTranslationState] = useState(false);
  const [sourceText, setSourceText] = useState("");
  const [loading, setLoading] = useState(false);
  const [target, setTarget] = useState("");
  const [modal, setModal] = useState(false);
  const [suggestEdit, setSuggestEdit] = useState(null)
  const [suggestEditValues, setSuggestEditValues] = useState("")


  const [sourceLanguage, setSourceLanguage] = useState({
    value: "en",
    label: "English",
  });
  const srcLang = getLanguageName(props.source);
  const tgtLang = getLanguageName(props.target);

  // useEffect(() => {
  // 	fetchChartData(selectedOption.value,"", [{"field": "sourceLanguage","value": sourceLanguage.value}])
  // }, []);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };
  const handleOnChange = (e) => {
    setSuggestEditValues(e.target.value)
  }
  const clearAll = () => {
    setSourceText("");
    setTarget("");
  };
  const handleCompute = () => {
    setLoading(true);
    const apiObj = new HostedInferenceAPI(modelId, sourceText, task, false);
    fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    })
      .then(async (resp) => {
        let rsp_data = await resp.json();
        setLoading(false);
        if (resp.ok) {
          if (rsp_data.hasOwnProperty("outputText") && rsp_data.outputText) {
            setTarget(rsp_data.outputText);
            setSuggestEditValues(rsp_data.outputText)
            //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
            setTranslationState(true);
          }
        } else {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message:
              "The model is not accessible currently. Please try again later",
            variant: "error",
          });
          Promise.reject(rsp_data);
        }
      })
      .catch((err) => {
        setLoading(false);
        setSnackbarInfo({
          ...snackbar,
          open: true,
          message:
            "The model is not accessible currently. Please try again later",
          variant: "error",
        });
      });
  };

  const handleFeedbackSubmit = (feedback) => {
    const apiObj = new SubmitFeedback('translation', sourceText, target, feedback)
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

  return (
    // <div>
    //<Grid container spacing={2}>
    <Grid
      className={classes.gridCompute}
      item
      xl={12}
      lg={12}
      md={12}
      sm={12}
      xs={12}
    >
      {loading && <Spinner />}
      <Card className={classes.hostedCard}>
        <CardContent className={classes.translateCard}>
          <Grid container className={classes.cardHeader}>
            <Grid
              item
              xs={4}
              sm={4}
              md={4}
              lg={4}
              xl={4}
              className={classes.headerContent}
            >
              <Typography variant="h6" className={classes.hosted}>
                Hosted inference API{" "}
                {
                  <LightTooltip
                    arrow
                    placement="right"
                    title={translate("label.hostedInferenceTranslation")}>
                    <InfoOutlinedIcon
                      className={classes.buttonStyle}
                      fontSize="small"
                      color="disabled"
                    />
                  </LightTooltip>
                }
              </Typography>
            </Grid>
            <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
              {/* <Autocomplete
                                disabled
                                options={['English']}
                                value={'English'}
                                renderInput={(params) => <TextField {...params} variant="standard" />}
                            /> */}
              <Typography variant="h6" className={classes.hosted}>
                {srcLang}
              </Typography>
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

        <CardActions className={classes.actionButtons}>
          <Grid container spacing={2}>
            <Grid item>
              <Button
                disabled={sourceText ? false : true}
                size="small"
                variant="outlined"
                onClick={clearAll}
              >
                {translate("button.clearAll")}
              </Button>
            </Grid>
            <Grid item>
              <Button
                color="primary"
                //  className={classes.computeBtn}
                variant="contained"
                size={"small"}
                onClick={handleCompute}
                disabled={sourceText ? false : true}
              >
                {translate("button.translate")}
              </Button>
            </Grid>
          </Grid>
        </CardActions>
      </Card>
      <Card className={classes.translatedCard}>
        <CardContent className={classes.translateCard}>
          <Grid container className={classes.cardHeader}>
            <Grid
              item
              xs={2}
              sm={2}
              md={2}
              lg={2}
              xl={2}
              className={classes.headerContent}
            >
              {/* <Autocomplete
                                disabled
                                options={['Hindi']}
                                value={'Hindi'}
                                renderInput={(params) => <TextField {...params} variant="standard" />}
                            /> */}
              <Typography variant="h6" className={classes.hosted}>
                {tgtLang}
              </Typography>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>
          <div>
            {target.length > 0 && (<>  <textarea
              disabled
              placeholder="Output"
              rows={5}
              value={target}
              className={classes.textArea}
            />

              <div   >
                <Button variant="contained" size="small" className={classes.translatfeedbackbutton} onClick={() => {setModal(true);setSuggestEditValues(target)}}>
                  <ThumbUpAltIcon className={classes.feedbackIcon} />
                  <ThumbDownAltIcon className={classes.feedbackIcon} />
                  <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
                </Button>
              </div>


            </>)}
          </div>

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
          target={target}
          suggestEditValues={suggestEditValues}
          handleOnChange={handleOnChange}
          setSuggestEdit={setSuggestEdit}
          setSuggestEditValues={setSuggestEditValues}
          taskType='translation'
          handleSubmit={handleFeedbackSubmit}

        />
      </Modal>


    </Grid>

    //  </Grid>

    //   </div>
  );
};
export default withStyles(DatasetStyle)(HostedInference);
