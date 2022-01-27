import { Divider, Grid } from "@material-ui/core";
import SpeechToSpeechFilter from "./SpeechToSpeechFilter";
import SpeechToSpeechOptions from "./SpeechToSpeechOptions";
import { useState } from "react";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import SearchModel from "../../../../../redux/actions/api/Model/ModelSearch/SearchModel";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";
import { useSelector } from "react-redux";
import C from "../../../../../redux/actions/constants";
import Snackbar from "../../../../components/common/Snackbar";
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import AudioReactRecorder, { RecordState } from "audio-react-recorder";

const SpeechToSpeech = () => {
  const dispatch = useDispatch();
  const { asr, tts, translation } = useSelector(
    (state) => state.getBulkModelSearch
  );
  const [data, setData] = useState("");
  const [url, setUrl] = useState("");
  const [recordAudio, setRecordAudio] = useState("");
  const [error, setError] = useState({ url: "" });
  const [base, setBase] = useState("");
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [filter, setFilter] = useState({
    src: "",
    tgt: "",
    asr: "",
    translation: "",
    tts: "",
  });

  const handleChange = (data, id) => {
    setFilter({ ...filter, [id]: data });
  };

  const handleStartRecording = (data) => {
    setData(null);
    setRecordAudio(RecordState.START);
  };

  const handleStopRecording = (value) => {
    setRecordAudio(RecordState.STOP);
  };

  useEffect(() => {
    dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
    return () => dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
  }, []);

  const makeModelSearchAPICall = (type, src, tgt) => {
    const apiObj = new SearchModel(type, src.value, tgt.value, true);
    dispatch(APITransport(apiObj));
  };

  const blobToBase64 = (blob) => {
    var reader = new FileReader();
    reader.readAsDataURL(blob.blob);
    reader.onloadend = function () {
      let base64data = reader.result;
      setBase(base64data);
    };
  };

  const onStopRecording = (data) => {
    setData(data.url);
    setBase(blobToBase64(data));
  };

  useEffect(() => {
    if (filter.src && filter.tgt) {
      makeModelSearchAPICall("asr", filter.src, { value: "" });
      makeModelSearchAPICall("translation", filter.src, filter.tgt);
      makeModelSearchAPICall("tts", filter.tgt, { value: "" });
    }
  }, [filter.src, filter.tgt]);

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
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

  const handleUrlSubmit = (e) => {
    if (!validURL(url)) {
      setError({ ...error, url: "Invalid URL" });
    } else {
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please wait while we process your request...",
        variant: "info",
      });
    }
  };

  const handleCompute = () => {
    console.log("Compute");
  };

  return (
    <>
      <Grid container spacing={5}>
        <Grid item xs={12} sm={12} md={4} lg={4} xl={4}>
          <SpeechToSpeechFilter
            asr={asr}
            tts={tts}
            translation={translation}
            filter={filter}
            handleChange={handleChange}
          />
        </Grid>
        <Divider />
        <Grid item xs={12} sm={12} md={8} lg={8} xl={8}>
          <SpeechToSpeechOptions
            Start={Start}
            Stop={Stop}
            data={data}
            url={url}
            error={error}
            setUrl={setUrl}
            setError={setError}
            onStopRecording={onStopRecording}
            handleStartRecording={handleStartRecording}
            handleStopRecording={handleStopRecording}
            handleSubmit={handleUrlSubmit}
            AudioReactRecorder={AudioReactRecorder}
            recordAudio={recordAudio}
            handleCompute={handleCompute}
          />
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
    </>
  );
};

export default SpeechToSpeech;
