import React, { useCallback, useEffect, useRef, useState } from "react";
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
import LightTooltip from "../../../../components/common/LightTooltip";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import { translate } from "../../../../../assets/localisation";
import { getLanguageName } from "../../../../../utils/getLabel";

function HostedInferTransliteration(props) {
  const { classes, target } = props;
  const dispatch = useDispatch();
  const { result, currentText, transliterationText } = useSelector(
    (state) => state.getTransliterationText
  );

  const [sourceLanguage, setSourceLanguage] = useState({
    value: "en",
    label: "English",
  });
  const srcLang = getLanguageName(props.source);
  const tgtLang = getLanguageName(props.target);

  const inputRef = useRef(null);

  const [transliteration, setTransliteration] = useState("");
  const [shouldFetchData, setShouldFetchData] = useState(true);
  const [isFirstRender, setIsFirstRender] = useState(true);
  const [startPositionOfCurrentWord, setStartPositionOfCurrentWord] = useState(0);
  const [curserIndexPosition, setCurserIndexPosition] = useState(0);


  const setTransliterateValues = (e) => {
    let inputValue = e.target.value;
    let currentCurserPosition = e.target.selectionStart;
    setCurserIndexPosition(currentCurserPosition);

    let startingPositionOfWord = inputValue.lastIndexOf(" ", currentCurserPosition - 1);
    setStartPositionOfCurrentWord(startingPositionOfWord);
    let activeWord = inputValue.slice(startingPositionOfWord, currentCurserPosition);

    setTransliteration(inputValue);
    if (shouldFetchData) {
      dispatch(setCurrentText(startingPositionOfWord < 0 ? inputValue : activeWord));
    } else {
      dispatch(clearTransliterationResult());
      return false;
    }
    setTimeout(() => {
      inputRef.current.selectionStart = currentCurserPosition;
    }, 0);

  };

  const handleKeyDown = (e) => {
    setShouldFetchData(e.key === "Backspace" ? false : true);
    if (e.key === " " && currentText) {
      e.preventDefault();
      dispatch(
        setTransliterationText(transliteration, `${result[0]}  `, startPositionOfCurrentWord < 0 ? startPositionOfCurrentWord + 1 : startPositionOfCurrentWord, curserIndexPosition)
      );
      dispatch(setCurrentText(""));
      dispatch(clearTransliterationResult());
    } else if (e.key === "Enter" && currentText) {
      e.preventDefault();
      dispatch(
        setTransliterationText(transliteration, `${result[0]}  `, startPositionOfCurrentWord < 0 ? startPositionOfCurrentWord + 1 : startPositionOfCurrentWord, curserIndexPosition)
      );
      dispatch(setCurrentText(""));
      dispatch(clearTransliterationResult());
    }
  }

  useEffect(() => {
    if (isFirstRender) {
      setTransliteration(" ");
      dispatch(setCurrentText(" "));
      setTimeout(() => {
        getTransliterationText();
        setIsFirstRender(false);
      }, 0);
    }
    // Update the document title using the browser API
  });


  const getTransliterationText = () => {
    const apiObj = new GetTransliterationText(target, currentText);
    console.log(apiObj, "apiObj")
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
        setTransliterationText(transliteration, `${result[0]}  `, startPositionOfCurrentWord < 0 ? startPositionOfCurrentWord + 1 : startPositionOfCurrentWord, curserIndexPosition)
      );
      dispatch(clearTransliterationResult());
    }
  }, [transliteration]);
  console.log(transliteration, "transliteration")


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
              <Typography variant="h6" className={classes.hosted}>
                {tgtLang}
              </Typography>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>
          <Autocomplete
            freeSolo
            clearOnBlur={false}
            disableClearable={true}
            options={!currentText ? [] : result.map((elem) => elem)}
            onKeyDown={handleKeyDown}
            PopperComponent={(params) => (
              <Popper
                {...params}
                placement='bottom-start'
                style={{ width: window.innerWidth > 776 ? window.innerWidth * 0.15 : window.innerWidth < 450 ? window.innerWidth * 0.5 : window.innerWidth * 0.3 }}
                onClick={(e) =>
                  dispatch(
                    setTransliterationText(
                      transliteration,
                      `${e.target.outerText}  `, startPositionOfCurrentWord < 0 ? startPositionOfCurrentWord + 1 : startPositionOfCurrentWord, curserIndexPosition
                    )
                  )
                }
              />
            )}
            sx={{ width: 300 }}
            renderInput={(params) => (
              <TextField
                variant="outlined"
                ref={inputRef}
                {...params}
                onKeyDown={handleKeyDown}
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