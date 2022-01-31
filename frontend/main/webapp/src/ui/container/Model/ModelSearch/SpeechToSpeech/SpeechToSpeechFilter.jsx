import { Grid, Button } from "@material-ui/core";
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
  } = props;
  const renderSingleAutoComplete = (
    value,
    id,
    labels,
    placeholder,
    disabled
  ) => {
    return (
      <SingleAutoComplete
        value={value}
        id={id}
        labels={labels}
        placeholder={placeholder}
        handleChange={handleChange}
        disabled={false}
      />
    );
  };

  return (
    <Grid container spacing={5}>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.src,
          "src",
          sourceLanguage,
          "Source Language",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.tgt,
          "tgt",
          targetLanguage,
          "Target Language",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.asr,
          "asr",
          asr.filter((a) => a.sourceLanguage === filter.src.value),
          "ASR Model",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.translation,
          "translation",
          translation.filter(
            (a) =>
              a.sourceLanguage === filter.src.value &&
              a.targetLanguage === filter.tgt.value
          ),
          "Translation Model",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.tts,
          "tts",
          tts.filter((a) => a.sourceLanguage === filter.tgt.value),
          "TTS Model",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        <Button
          style={{ float: "right" }}
          variant="contained"
          size="large"
          color="primary"
          onClick={handleClick}
        >
          Reset
        </Button>
      </Grid>
    </Grid>
  );
};

export default SpeechToSpeechFilter;
