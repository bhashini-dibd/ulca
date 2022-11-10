import { useEffect, useState } from "react";
import {
  disconnectSocket,
  socketConnection,
} from "../../../../../utils/socketConnection";
import DatasetStyle from "../../../../styles/Dataset";
import { withStyles } from "@material-ui/core/styles";
import Spinner from "../../../../components/common/Spinner";
import { Switch } from "@material-ui/core";
import { translate } from "../../../../../assets/localisation";
import DownIcon from "@material-ui/icons/ArrowDropDown";
import { ReactTransliterate } from "react-transliterate";
import configs from "../../../../../configs/configs";
import endpoints from "../../../../../configs/apiendpoints";
import ThumbUpAltIcon from "@material-ui/icons/ThumbUpAlt";
import ThumbDownAltIcon from "@material-ui/icons/ThumbDownAlt";
import Snackbar from "../../../../components/common/Snackbar";
import Modal from "../../../../components/common/Modal";
import FeedbackPopover from "../../../../components/common/FeedbackTTranslation";
import SubmitFeedback from "../../../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";
import GetTransliterationModelID from "../../../../../redux/actions/api/Model/ModelSearch/GetTransliterationModelID";
import { IndicTransliterate } from "@ai4bharat/indic-transliterate";

import {
  Grid,
  Typography,
  Button,
  CardContent,
  Card,
  CardActions,
  Menu,
  MenuItem,
} from "@material-ui/core";

const StyledMenu = withStyles({})((props) => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: "bottom",
      horizontal: "left",
    }}
    transformOrigin={{
      vertical: "top",
      horizontal: "",
    }}
    PaperProps={{
      style: {
        width: 140,
      },
    }}
    {...props}
  />
));

const TTSLiveInference = (props) => {
  const { classes, source } = props;
  const [socket, setSocket] = useState(null);
  const [loading, setLoading] = useState(false);
  const [transliterationModelId, setTransliterationModelId] = useState("");
  const [showTransliteration, setShowTransliteration] = useState(true);
  const [anchorEl, openEl] = useState(null);
  const [gender, setGender] = useState("Female");
  const [sourceText, setSourceText] = useState("");
  const [audio, setAudio] = useState(null);
  const [modal, setModal] = useState(false);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [base, setBase] = useState("");

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };
  const clearAll = () => {
    setSourceText("");
  };
  const handleAnchorClose = () => {
    openEl(false);
  };

  const handleChange = (val) => {
    setGender(val);
  };

  const handleFeedbackSubmit = (feedback) => {
    const apiObj = new SubmitFeedback("tts", sourceText, base, feedback);
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    }).then(async (resp) => {
      const rsp_data = await resp.json();
      if (resp.ok) {
        setSnackbarInfo({
          open: true,
          message: rsp_data.message,
          variant: "success",
        });
      } else {
        setSnackbarInfo({
          open: true,
          message: rsp_data.message,
          variant: "error",
        });
      }
    });
    setTimeout(
      () => setSnackbarInfo({ open: false, message: "", variant: null }),
      3000
    );
  };

  const handleSourceText = (text) => {
    const lastWord = text.split(" ").slice(-2)[0];
    // let request = {
    //   language: source,
    //   text:lastWord,
    //   speaker: gender.toLowerCase(),
    // };
    let request = {
      input:[{source:lastWord}],
      config: {
        gender: gender.toLowerCase(),
        language: {
          sourceLanguage: source
        }
      }
    }
    setAudio(null);
    if (text.slice(-1) === " ") {
      setSourceText(text);
      emitOnSpace(request);
    } else {
      setSourceText(text);
    }
  };

  const emitOnSpace = (request) => {
    socket.emit("infer", request, (x) => {
      setAudio(null);
      if (x["status"] == "SUCCESS") {
        console.log('inside ifffffff')
        let arrayBuffer = x["output"]["audio"];
        console.log('inside arrayBuffer', arrayBuffer)
        const blob = new Blob([arrayBuffer], { type: "audio/wav" });
        console.log('inside blob', blob)
        const url = window.URL.createObjectURL(blob);
        setAudio(url);
      } else {
        console.log("error");
      }
    });
  };

  const fetchTransliterationModel = async () => {
    const apiObj = new GetTransliterationModelID("en", source);
    source &&
      source !== "en" &&
      fetch(apiObj.apiEndPoint(), {
        method: "GET",
        // headers: apiObj.getHeaders().headers,
      })
        .then(async (resp) => {
          let rsp_data = await resp.json();
          if (resp.ok) {
            console.log("resp_data", rsp_data);
            setTransliterationModelId(rsp_data.modelId);
          }
        })
        .catch((err) => {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: "Transliteration Model ID Not Present.",
            variant: "error",
          });
        });
  };

  useEffect(() => {
    fetchTransliterationModel();
  }, [source]);

  useEffect(() => {
    const socket = socketConnection(
      `wss://tts-api.ai4bharat.org/tts`,
      `/tts_socket.io`
    );
    setSocket(socket);
    return () => {
      disconnectSocket(socket);
    };
  }, []);

  const renderGenderDropDown = () => {
    return (
      <>
        <Button
          className={classes.menuStyle}
          color="inherit"
          fullWidth
          onClick={(e) => openEl(e.currentTarget)}
          variant="text"
        >
          <Typography variant="body1">{gender}</Typography>
          <DownIcon />
        </Button>
        <StyledMenu
          id="data-set"
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={(e) => handleAnchorClose(e)}
          className={classes.styledMenu1}
        >
          <MenuItem
            value={"Male"}
            name={"Male"}
            className={classes.styledMenu}
            onClick={() => {
              handleChange("Male");
              handleAnchorClose();
            }}
          >
            <Typography variant={"body1"}>{"Male"}</Typography>
          </MenuItem>
          <MenuItem
            value={"Female"}
            name={"Female"}
            className={classes.styledMenu}
            onClick={() => {
              handleChange("Female");
              handleAnchorClose();
            }}
          >
            <Typography variant={"body1"}>{"Female"}</Typography>
          </MenuItem>
        </StyledMenu>
      </>
    );
  };

  const stopLiveTTS = () =>{
    let request = {
      language: source,
      text:sourceText,
      speaker: gender.toLowerCase(),
    };
    emitOnSpace(request);
  };

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
      {loading && <Spinner />}
      <Card className={classes.hostedCard}>
        <CardContent className={classes.translateCard}>
          <Grid container className={classes.cardHeader}>
            <Grid
              item
              xs={5}
              sm={5}
              md={transliterationModelId ? 3 : 8}
              lg={transliterationModelId ? 3 : 8}
              xl={transliterationModelId ? 3 : 8}
              className={classes.headerContent}
            >
              <Typography variant="h6" className={classes.hosted}>
                Input Text
              </Typography>
            </Grid>
            {transliterationModelId && (
              <Grid
                item
                xs={5}
                sm={3}
                md={5}
                lg={5}
                xl={5}
                style={{
                  display: "inline-flex",
                  alignItems: "baseline",
                  justifyContent: "center",
                }}
              >
                <Typography variant="h6" className={classes.hosted}>
                  Transliteration
                </Typography>
                <Switch
                  checked={showTransliteration}
                  onChange={() => setShowTransliteration(!showTransliteration)}
                  color="primary"
                  name="checkedB"
                  inputProps={{ "aria-label": "primary checkbox" }}
                />
              </Grid>
            )}
            <Grid
              item
              xs={12}
              sm={3}
              md={3}
              lg={3}
              xl={3}
              className={classes.headerContent}
            >
              {renderGenderDropDown()}
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>
          <Typography variant="caption">
            {translate("label.maxCharacters")}
          </Typography>
        </CardContent>
        <CardContent>
          {showTransliteration ? (
            <IndicTransliterate
              lang={source}
              apiURL={`${configs.BASE_URL_AUTO + endpoints.hostedInference}`}
              modelId={transliterationModelId}
              value={sourceText}
              onChangeText={(text) => {
                console.log("text", text);
                handleSourceText(text);
              }}
              renderComponent={(props) => (
                <textarea
                  placeholder="Enter text here..."
                  className={classes.textAreaTransliteration}
                  {...props}
                />
              )}
            />
          ) : (
            <textarea
              placeholder="Enter text here..."
              value={sourceText}
              onChange={(e) => {
                handleSourceText(e.target.value);
              }}
              className={classes.textAreaTransliteration}
            />
          )}
        </CardContent>

        <CardActions className={classes.actionButtons}>
          <Grid container spacing={2}>
          <Grid item>
              <Button
                disabled={sourceText ? false : true}
                size="small"
                variant="outlined"
                onClick={stopLiveTTS}
              >
                Stop
              </Button>
            </Grid>
            <Grid item>
              <Button
                disabled={sourceText ? false : true}
                size="small"
                variant="outlined"
                onClick={clearAll}
              >
                {translate("button.clearAll")}
              </Button>
            </Grid>
          </Grid>
        </CardActions>
      </Card>
      <Card className={classes.translatedCard}>
        <CardContent className={classes.translateCard}>
          <Grid container className={classes.cardHeader}>
            <Grid
              item
              xs={2}
              sm={2}
              md={2}
              lg={2}
              xl={2}
              className={classes.headerContent}
            >
              <Typography variant="h6" className={classes.hosted}>
                {"Output"}
              </Typography>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent
          style={{
            display: "flex",
            justifyContent: "center",
            padding: "8vh",
          }}
        >
          {audio ? (
            <>
              <audio controls autoPlay>
                <source src={audio}></source>
              </audio>
            </>
          ) : (
            <></>
          )}
        </CardContent>
        {audio && (
          <div>
            <div>
              <Button
                variant="contained"
                size="small"
                style={{
                  float: "right",
                  marginRight: "25px",
                  backgroundColor: "#FD7F23",
                }}
                onClick={() => setModal(true)}
              >
                <ThumbUpAltIcon className={classes.feedbackIcon} />
                <ThumbDownAltIcon className={classes.feedbackIcon} />
                <Typography variant="body2" className={classes.feedbackTitle}>
                  {" "}
                  {translate("button:feedback")}
                </Typography>
              </Button>
            </div>
          </div>
        )}
      </Card>
      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )}
      <Modal
        open={modal}
        onClose={() => setModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <FeedbackPopover
          setModal={setModal}
          suggestion={false}
          taskType="tts"
          handleSubmit={handleFeedbackSubmit}
        />
      </Modal>
    </Grid>
  );
};

export default withStyles(DatasetStyle)(TTSLiveInference);
