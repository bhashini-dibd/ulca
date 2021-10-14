import {
  Grid,
  Typography,
  Button,
  CardContent,
  Card,
  CardActions,
} from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useState } from "react";
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import AudioReactRecorder, { RecordState } from "audio-react-recorder";
import {
  connect,
  startStreaming,
  stopStreaming,
} from "../../../../../utils/streaming_client";
import config from "../../../../../configs/configs";

const SOCKET_URL = config.SOCKET_URL;

const AudioRecord = (props) => {
  const { classes, modelId } = props;
  const [recordAudio, setRecordAudio] = useState("");
  const [base, setBase] = useState("");
  const [data, setData] = useState("");

  const blobToBase64 = (blob) => {
    var reader = new FileReader();
    reader.readAsDataURL(blob.blob);
    reader.onloadend = function () {
      let base64data = reader.result;
      setBase(base64data);
    };
  };

  const handleCompute = () => {
    props.handleApicall(modelId, base, "asr", true);
  };

  const handleStart = (data) => {
    setData(null);
    setRecordAudio(RecordState.START);
    let language = "en-IN";
    console.log(SOCKET_URL, language);
    connect(SOCKET_URL, language, function (action, id) {
      console.log("Connected", id);
      if (action === null) {
        startStreaming(function (transcript) {
          console.log(transcript);
        });
      } else {
        // post-done
        console.log("Action", action, id);
      }
    });
  };

  const handleStop = (value) => {
    setRecordAudio(RecordState.STOP);
    stopStreaming();
  };

  const onStop = (data) => {
    setData(data.url);
    setBase(blobToBase64(data));
  };

  return (
    <Card className={classes.asrCard}>
      <Grid container className={classes.cardHeader}>
        <Typography variant="h6" className={classes.titleCard}>
          Hosted inference API{" "}
          {
            <InfoOutlinedIcon
              className={classes.buttonStyle}
              fontSize="small"
              color="disabled"
            />
          }
        </Typography>
      </Grid>
      <CardContent>
        {recordAudio === "start" ? (
          <div className={classes.center}>
            <img
              src={Stop}
              alt=""
              onClick={() => handleStop()}
              style={{ cursor: "pointer" }}
            />{" "}
          </div>
        ) : (
          <div className={classes.center}>
            <img
              src={Start}
              alt=""
              onClick={() => handleStart()}
              style={{ cursor: "pointer" }}
            />{" "}
          </div>
        )}

        <div className={classes.center}>
          <Typography style={{ height: "12px" }} variant="caption">
            {recordAudio === "start" ? "Listening..." : ""}
          </Typography>{" "}
        </div>

        <div style={{ display: "none" }}>
          <AudioReactRecorder
            state={recordAudio}
            onStop={onStop}
            style={{ display: "none" }}
          />
        </div>
        <div className={classes.centerAudio}>
          {data ? (
            <audio src={data} controls id="sample"></audio>
          ) : (
            <audio src={"test"} controls id="sample"></audio>
          )}
        </div>
      </CardContent>
      <CardActions style={{ justifyContent: "flex-end", paddingRight: "20px" }}>
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
    </Card>
  );
};

export default withStyles(DatasetStyle)(AudioRecord);
