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

const SpeechToSpeech = () => {
  const dispatch = useDispatch();
  const { asr, tts, translation } = useSelector(
    (state) => state.getBulkModelSearch
  );
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

  useEffect(() => {
    dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
    return () => dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
  }, []);

  const makeModelSearchAPICall = (type, src, tgt) => {
    const apiObj = new SearchModel(type, src.value, tgt.value, true);
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    if (filter.src && filter.tgt) {
      makeModelSearchAPICall("asr", filter.src, { value: "" });
      makeModelSearchAPICall("translation", filter.src, filter.tgt);
      makeModelSearchAPICall("tts", filter.tgt, { value: "" });
    }
  }, [filter.src, filter.tgt]);

  return (
    <Grid container spacing={2}>
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
        <SpeechToSpeechOptions />
      </Grid>
    </Grid>
  );
};

export default SpeechToSpeech;
