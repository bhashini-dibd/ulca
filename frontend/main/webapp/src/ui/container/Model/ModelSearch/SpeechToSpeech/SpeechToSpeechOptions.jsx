import {
  Grid,
  Card,
  Typography,
  CardContent,
  CardActions,
  Button,
  TextField,
} from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import { translate } from "../../../../../assets/localisation";
import DatasetStyle from "../../../../styles/Dataset";
import MyAccordion from "../../../../components/common/Accordion";

const SpeechToSpeechOptions = (props) => {
  const {
    classes,
    audio,
    recordAudio,
    AudioReactRecorder,
    Stop,
    handleStartRecording,
    Start,
    handleStopRecording,
    data,
    handleCompute,
    onStopRecording,
    url,
    error,
    handleSubmit,
    setUrl,
    setError,
    output,
  } = props;
  const renderVoiceRecorder = () => {
    return (
      <Card className={classes.asrCard}>
        <Grid container className={classes.cardHeader}>
          <Typography variant="h6" className={classes.titleCard}>
            {/* {translate("label.notes")} */}
            Live Recording Inference
          </Typography>
        </Grid>
        <CardContent>
          <Typography variant={"caption"}>
            {translate("label.maxDuration")}
          </Typography>
          {recordAudio === "start" ? (
            <div className={classes.center}>
              <img
                src={Stop}
                alt=""
                onClick={() => handleStopRecording()}
                style={{ cursor: "pointer" }}
              />{" "}
            </div>
          ) : (
            <div className={classes.center}>
              <img
                src={Start}
                alt=""
                onClick={handleStartRecording}
                style={{ cursor: "pointer" }}
              />{" "}
            </div>
          )}
          <div className={classes.center}>
            <Typography style={{ height: "12px" }} variant="caption">
              {recordAudio === "start" ? "Recording..." : ""}
            </Typography>{" "}
          </div>
          <div style={{ display: "none" }}>
            <AudioReactRecorder
              state={recordAudio}
              onStop={onStopRecording}
              style={{ display: "none" }}
            />
          </div>
          <div className={classes.centerAudio}>
            {data ? (
              <audio src={data} controls id="sample"></audio>
            ) : (
              <audio src="sample" controls id="sample"></audio>
            )}
          </div>
          <CardActions
            style={{ justifyContent: "flex-end", paddingRight: "20px" }}
          >
            <Button
              color="primary"
              variant="contained"
              size={"small"}
              disabled={data ? false : true}
              onClick={() => handleCompute()}
            >
              Convert
            </Button>
          </CardActions>
        </CardContent>
      </Card>
    );
  };

  const renderURLInput = () => {
    return (
      <Card className={classes.asrCard}>
        <Grid container className={classes.cardHeader}>
          <Typography variant="h6" className={classes.titleCard}>
            {/* {translate("label.notes")} */}
            Batch Inference
          </Typography>
        </Grid>
        <CardContent>
          <Typography variant={"caption"}>
            {translate("label.maxDuration")}
          </Typography>
          <TextField
            style={{ marginTop: "15px", marginBottom: "37px" }}
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
    );
  };

  const renderAccordionDetails = (placeholder, textAreaLabel, value) => {
    return (
      <Grid container>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <textarea
            disabled
            placeholder={placeholder}
            rows={3}
            value={value}
            className={classes.textArea}
          />
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <textarea
            placeholder={textAreaLabel}
            rows={3}
            className={classes.textArea}
          />
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Grid container spacing="2">
            <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
              <Button fullWidth variant="outlined" size="small" color="primary">
                Clear
              </Button>
            </Grid>
            <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
              <Button
                fullWidth
                variant="contained"
                size="small"
                color="primary"
              >
                Submit
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    );
  };

  const renderAccordion = () => {
    return (
      <div>
        <MyAccordion label={"ASR Output"}>
          {renderAccordionDetails(
            "ASR Output",
            "Corrected ASR Output",
            output.asr
          )}
        </MyAccordion>
        <MyAccordion label={"Translation Output"}>
          {renderAccordionDetails(
            "Translation Output",
            "Corrected Translation Output",
            output.translation
          )}
        </MyAccordion>
      </div>
    );
  };

  const renderOutput = () => {
    return (
      <Card className={classes.asrCard}>
        <Grid container className={classes.cardHeader}>
          <Typography variant="h6" className={classes.titleCard}>
            {translate("label.output")}
          </Typography>
        </Grid>
        <CardContent>
          <audio src={audio} controls></audio>
        </CardContent>
      </Card>
    );
  };

  return (
    <Grid container spacing={3}>
      <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
        {renderVoiceRecorder()}
      </Grid>
      <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
        {renderURLInput()}
      </Grid>
      {audio ? (
        <>
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
            <Typography variant="h5" style={{ marginBottom: "1%" }}>
              Intermediate Output
            </Typography>
            {renderAccordion()}
          </Grid>
          <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
            {renderOutput()}
          </Grid>
        </>
      ) : (
        <></>
      )}
    </Grid>
  );
};

export default withStyles(DatasetStyle)(SpeechToSpeechOptions);
