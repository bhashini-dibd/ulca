import {
  Grid,
  Typography,
  CardContent,
  Card,
  Tooltip,
} from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useEffect, useState } from "react";
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import { RecordState } from "audio-react-recorder";
import config from "../../../../../configs/configs";
// import StreamingClient from "../../../../../utils/streaming_client";
import {
  StreamingClient,
  SocketStatus,
} from "@project-sunbird/open-speech-streaming-client";
import { vakyanshLanguage } from "../../../../../configs/DatasetItems";
import { translate } from "../../../../../assets/localisation";
import LightTooltip from "../../../../components/common/LightTooltip";
const REACT_SOCKET_URL = config.REACT_SOCKET_URL;

const AudioRecord = (props) => {
  const streaming = props.streaming;
  const { classes, language } = props;
  const [recordAudio, setRecordAudio] = useState("");
  const [streamingState, setStreamingState] = useState("");
  const [data, setData] = useState("");
  const languageArr = vakyanshLanguage.filter(
    (lang) => lang.label === language
  );
  const languageCode = languageArr.length ? languageArr[0].value : "";
  const handleStart = (data) => {
    setStreamingState("start");
    const output = document.getElementById("asrCardOutput");
    output.innerText = "";
    setData("");
    streaming.connect(
      REACT_SOCKET_URL,
      languageCode,
      function (action, id) {
        setStreamingState("listen");
        setRecordAudio(RecordState.START);
        if (action === SocketStatus.CONNECTED) {
          streaming.startStreaming(
            function (transcript) {
              const output = document.getElementById("asrCardOutput");
              if (output) output.innerText = transcript;
            },
            function (errorMsg) {
              console.log("errorMsg", errorMsg);
            }
          );
        } else if (action === SocketStatus.TERMINATED) {
          handleStop();
        } else {
          console.log("Action", action, id);
        }
      }
    );
  };

  useEffect(() => {
    if (streamingState === "listen" && data === "") {
      setTimeout(async () => {
        handleStop();
      }, 61000);
    }
  }, [streamingState, data]);

  const handleStop = async (value) => {
    setStreamingState("");
    const output = document.getElementById("asrCardOutput");
    if (output) {
      streaming.punctuateText(
        output.innerText,
        `${REACT_SOCKET_URL}punctuate`,
        (status, text) => {
          output.innerText = text;
        },
        (status, error) => {
          // alert("Failed to punctuate");
        }
      );
    }
    streaming.stopStreaming((blob) => {
      const urlBlob = window.URL.createObjectURL(blob);
      onStop({ url: urlBlob });
    });
    setRecordAudio(RecordState.STOP);
    clearTimeout();
  };

  const onStop = (data) => {
    setData(data.url);
  };

  return (
    <Card className={classes.asrCard}>
      <Grid container className={classes.cardHeader}>
        <Typography variant="h6" className={classes.titleCard}>
          Hosted inference API{" "}
          {
            <LightTooltip
              arrow
              placement="right"
              title={translate("label.hostedInferenceASR")}
            >
              <InfoOutlinedIcon
                className={classes.buttonStyle}
                fontSize="small"
                color="disabled"
              />
            </LightTooltip>
          }
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
            {streamingState === "start"
              ? "Please wait..."
              : streamingState === "listen"
              ? "Listening..."
              : ""}
          </Typography>{" "}
        </div>
        <div className={classes.centerAudio}>
          {data && <audio src={data} controls id="sample"></audio>}
        </div>
      </CardContent>
    </Card>
  );
};

export default withStyles(DatasetStyle)(AudioRecord);
