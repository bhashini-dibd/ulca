import { Tooltip } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { useHistory } from "react-router";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import Autocomplete from "@material-ui/lab/Autocomplete";
import Spinner from "../../../../components/common/Spinner";
import { getLanguageName } from "../../../../../utils/getLabel";
import DownIcon from "@material-ui/icons/ArrowDropDown";
// import SimpleDialogDemo from "../../../../components/common/Feedback";
import {
  Grid,
  Typography,
  TextField,
  Button,
  CircularProgress,
  CardContent,
  Card,
  CardActions,
  CardHeader,
  Menu,
  MenuItem,
} from "@material-ui/core";
import { useState } from "react";
import { identifier } from "@babel/types";
import Snackbar from "../../../../components/common/Snackbar";
import { translate } from "../../../../../assets/localisation";
import LightTooltip from "../../../../components/common/LightTooltip";

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
))

const HostedInference = (props) => {
  const { classes, title, para, modelId, task } = props;
  const [gender, setGender] = useState("Female");
  const [audio, setAudio] = useState(null);
  const history = useHistory();
  const [translation, setTranslationState] = useState(false);
  const [sourceText, setSourceText] = useState("");
  const [loading, setLoading] = useState(false);
  const [target, setTarget] = useState("");
  const [sourceLanguage, setSourceLanguage] = useState({
    value: "en",
    label: "English",
  });
  const srcLang = getLanguageName(props.source);
  const tgtLang = getLanguageName(props.target);

  // useEffect(() => {
  // 	fetchChartData(selectedOption.value,"", [{"field": "sourceLanguage","value": sourceLanguage.value}])
  // }, []);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };
  const clearAll = () => {
    setSourceText("");
    setTarget("");
  };

  const b64toBlob = (b64Data, contentType = "", sliceSize = 512) => {
    const byteCharacters = atob(b64Data);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: contentType });
    return blob;
  };
  const handleCompute = () => {
    setLoading(true);
    setAudio(null);
    const apiObj = new HostedInferenceAPI(
      modelId,
      sourceText,
      task,
      false,
      "",
      "",
      gender.toLowerCase()
    );
    fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    })
      .then(async (resp) => {
        let rsp_data = await resp.json();
        setLoading(false);
        if (resp.ok) {
          if (rsp_data.hasOwnProperty("outputText") && rsp_data.outputText) {
            const blob = b64toBlob(rsp_data.outputText, "audio/wav");
            const urlBlob = window.URL.createObjectURL(blob);
            setAudio(urlBlob);
            //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
            setTranslationState(true);
          }
        } else {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message:
              "The model is not accessible currently. Please try again later",
            variant: "error",
          });
          Promise.reject(rsp_data);
        }
      })
      .catch((err) => {
        setLoading(false);
        setSnackbarInfo({
          ...snackbar,
          open: true,
          message:
            "The model is not accessible currently. Please try again later",
          variant: "error",
        });
      });
  };

  const [anchorEl, openEl] = useState(null);
  const handleAnchorClose = () => {
    openEl(false);
  };

  const handleChange = (val) => {
    setGender(val);
     };
  
  const renderGenderDropDown = () => {
    return (
      <>
        <Button
          className={classes.menuStyle}
          // disabled={page !== 0 ? true : false}
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
              xs={9}
              sm={9}
              md={9}
              lg={9}
              xl={9}
              className={classes.headerContent}
            >
              <Typography variant="h6" className={classes.hosted}>
                Input Text
              </Typography>
            </Grid>
            <Grid
              item
              xs={2}
              sm={2}
              md={2}
              lg={2}
              xl={2}
              className={classes.headerContent}
            >
              {renderGenderDropDown()}
              </Grid>
          </Grid>
        </CardContent>
        <CardContent>
            <Typography variant="caption">{translate("label.maxCharacters")}</Typography>
          {/* <Grid container>
            <Grid item>{renderGenderDropDown()}</Grid>
          </Grid> */}
        </CardContent>
        <CardContent>
          <textarea
            value={sourceText}
            maxLength={150}
            rows={3}
            // cols={40}
            className={classes.textArea}
            placeholder="Enter Text"
            onChange={(e) => {
              setSourceText(e.target.value);
            }}
          />
        </CardContent>

        <CardActions className={classes.actionButtons}>
          <Grid container spacing={2}>
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
            <Grid item>
              <Button
                color="primary"
                variant="contained"
                size={"small"}
                onClick={handleCompute}
                disabled={sourceText ? false : true}
              >
                {translate("button.convert")}
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
           display:"flex",
            justifyContent: "center",
            padding: "8vh",
          }}
        >
          {audio ? (
            <>
            <audio controls>
              <source src={audio}></source>
            </audio>
           </>
          ) : (
            <></>
           
          )}
          
        </CardContent>
        {audio && <div style={{marginTop:"12%"}}>
             {/* <SimpleDialogDemo/> */}
              </div>}
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
    </Grid>
  );
};
export default withStyles(DatasetStyle)(HostedInference);
