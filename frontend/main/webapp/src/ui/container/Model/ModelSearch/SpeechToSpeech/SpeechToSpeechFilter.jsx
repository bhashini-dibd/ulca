import {
  Grid,
  Button,
  MuiThemeProvider,
  createTheme,
  Typography,
} from "@material-ui/core";
import SingleAutoComplete from "../../../../components/common/SingleAutoComplete";

const SpeechToSpeechFilter = (props) => {
  const {
    handleChange,
    filter,
    asr,
    tts,
    translation,
    handleClick,
    sourceLanguage,
    targetLanguage,
    disabled,
  } = props;

  const getTheme = () =>
    createTheme({
      MuiFormLabel: {
        root: {
          fontSize: "1rem",
        },
      },
    });

  const renderSingleAutoComplete = (
    value,
    id,
    labels,
    placeholder,
    disabled
  ) => {
    return (
      <MuiThemeProvider theme={getTheme}>
        <SingleAutoComplete
          value={value}
          id={id}
          labels={labels}
          placeholder={placeholder}
          handleChange={handleChange}
          disabled={disabled}
        />
      </MuiThemeProvider>
    );
  };

  return (
    <Grid container spacing={2}>
      <Grid
        style={{ marginTop: "1vh",textAlign:"justify",padding:"10px 15px 0px 10px" }}
        item
        xs={12}
        sm={12}
        md={12}
        lg={12}
        xl={12}
      >
        <Typography variant="body" component={"i"}>
        This is an experimental feature, where we concatenate the models submitted to ULCA to achieve Speech-To-Speech(STS) translation in Indian languages. Over time, the accuracy and performance of these models will improve, bringing us closer to the goal of realtime STS translation.
        </Typography>
      </Grid>
      <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
        {renderSingleAutoComplete(
          filter.src,
          "src",
          sourceLanguage,
          "Source Language",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
        {renderSingleAutoComplete(
          filter.tgt,
          "tgt",
          targetLanguage,
          "Target Language",
          filter.src.value ? false : true
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={3} lg={3} xl={3}>
        {renderSingleAutoComplete(
          filter.asr,
          "asr",
          // asr.filter((a) => a.sourceLanguage === filter.src.value),
          asr.filter(a => a.sourceLanguage === filter.src.value && a.inferenceEndPoint.schema.modelProcessingType.type === 'batch'),
          "ASR Model",
          filter.tgt.value ? false : true
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
        {renderSingleAutoComplete(
          filter.translation,
          "translation",
          translation.filter(
            (a) =>
              a.sourceLanguage === filter.src.value &&
              a.targetLanguage === filter.tgt.value
          ),
          "Translation Model",
          filter.tgt.value ? false : true
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
        {renderSingleAutoComplete(
          filter.tts,
          "tts",
          tts.filter((a) => a.sourceLanguage === filter.tgt.value),
          "TTS Model",
          filter.tgt.value ? false : true
        )}
      </Grid>
      <Grid
        item
        xs={12}
        sm={12}
        md={1}
        lg={1}
        xl={1}
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "flex-end",
        }}
      >
        <Button
          variant="contained"
          size="large"
          style={{ display: "flex", justifyContent: "center" }}
          color="primary"
          onClick={handleClick}
          disabled={disabled}
        >
          Clear
        </Button>
      </Grid>
    </Grid>
  );
};

export default SpeechToSpeechFilter;
