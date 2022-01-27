import { Grid } from "@material-ui/core";
import SingleAutoComplete from "../../../../components/common/SingleAutoComplete";
import { Language } from "../../../../../configs/DatasetItems";

const SpeechToSpeechFilter = (props) => {
  const { handleChange, filter, asr, tts, translation } = props;
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
          Language,
          "Source Language",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.tgt,
          "tgt",
          Language,
          "Target Language",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(filter.asr, "asr", asr, "ASR Model", false)}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(
          filter.translation,
          "translation",
          translation,
          "Translation Model",
          false
        )}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        {renderSingleAutoComplete(filter.tts, "tts", tts, "TTS Model", false)}
      </Grid>
    </Grid>
  );
};

export default SpeechToSpeechFilter;
