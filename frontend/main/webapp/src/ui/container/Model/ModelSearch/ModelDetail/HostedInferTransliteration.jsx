import React, { useCallback, useEffect, useState } from "react";
import {
  Grid,
  Typography,
  TextField,
  CardContent,
  Card,
  Popper,
} from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { Autocomplete } from "@material-ui/lab";
import GetTransliterationText from "../../../../../redux/actions/api/Model/ModelSearch/GetTransliterationText";
import { useDispatch, useSelector } from "react-redux";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";
import {
  setTransliterationText,
  setCurrentText,
  clearTransliterationResult,
} from "../../../../../redux/actions/api/Model/ModelSearch/SetTransliterationText";

function HostedInferTransliteration(props) {
  const { classes, target } = props;
  const dispatch = useDispatch();
  const { result, currentText, transliterationText } = useSelector(
    (state) => state.getTransliterationText
  );

  const [transliteration, setTransliteration] = useState("");

  const setTransliterateValues = (e) => {
    setTransliteration(e.target.value);
    dispatch(setCurrentText(e.target.value));
  };

  const getTransliterationText = () => {
    const apiObj = new GetTransliterationText(target, currentText);
    dispatch(APITransport(apiObj));
  };

  useEffect(() => {
    setTransliteration(transliterationText);
    dispatch(clearTransliterationResult());
  }, [transliterationText]);

  useEffect(() => {
    if (!!currentText) getTransliterationText();
  }, [currentText]);

  useEffect(() => {
    if (transliteration[transliteration.length - 1] === " " && result.length) {
      const transliterationArr = transliteration.split(" ");
      transliterationArr.pop();
      dispatch(
        setTransliterationText(transliterationArr.join(" "), `${result[0]} `)
      );
      dispatch(clearTransliterationResult());
    }
  }, [transliteration]);

  return (
    <Grid
      className={classes.gridCompute}
      item
      xl={12}
      lg={12}
      md={12}
      sm={12}
      xs={12}
    >
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
            ></Grid>
            <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
              <Typography variant="h6" className={classes.hosted}></Typography>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>
          <Autocomplete
            freeSolo
            clearOnBlur={false}
            disableClearable={true}
            options={result.map((elem) => elem)}
            PopperComponent={(params) => (
              <Popper
                {...params}
                onClick={(e) =>
                  dispatch(
                    setTransliterationText(
                      transliteration,
                      `${e.target.outerText} `
                    )
                  )
                }
              />
            )}
            sx={{ width: 300 }}
            renderInput={(params) => (
              <TextField
                variant="outlined"
                {...params}
                onChange={setTransliterateValues}
              />
            )}
            value={transliteration}
          />
        </CardContent>
      </Card>
    </Grid>
  );
}
export default withStyles(DatasetStyle)(HostedInferTransliteration);
