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
import FeedbackPopover from "../../../../components/common/FeedbackTTranslation";
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
import { useEffect, useState } from "react";
import { identifier } from "@babel/types";
import Snackbar from "../../../../components/common/Snackbar";
import { translate } from "../../../../../assets/localisation";
// import LightTooltip from "../../../../components/common/LightTooltip";
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import Modal from '../../../../components/common/Modal';
import SubmitFeedback from "../../../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";
import configs from "../../../../../configs/configs";
import endpoints from "../../../../../configs/apiendpoints";
import GetTransliterationModelID from "../../../../../redux/actions/api/Model/ModelSearch/GetTransliterationModelID";
import { Switch } from "@material-ui/core";
import TTSLiveInference from "./TTSLiveInference";
import { Language } from "../../../../../configs/DatasetItems";
import { IndicTransliterate } from "react-transliterate";

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

const colors = [
    { light: "#f7dec5", dark: "#ff8000" },
    { light: "#cceeff", dark: "#00aaff" },
    { light: "#ff9999", dark: "#ff1a1a" },
    { light: "#99e6ff", dark: "#00bfff" },
    { light: "#adebad", dark: "#46d246" },
    { light: "#cccc99", dark: "#aaaa55" },
]

const HostedInference = (props) => {
    const { classes, title, para, modelId, task, source, submitter, inferenceEndPoint } = props;
    const [gender, setGender] = useState("Female");
    const [audio, setAudio] = useState(null);
    const history = useHistory();
    const [translation, setTranslationState] = useState(false);
    const [sourceText, setSourceText] = useState("");
    const [outputText, setOutputText] = useState();
    const [loading, setLoading] = useState(false);
    const [target, setTarget] = useState("");
    const [modal, setModal] = useState(false);
    const [sourceLanguage, setSourceLanguage] = useState({
        value: "en",
        label: "English",
    });
    const srcLang = getLanguageName(props.source);
    const tgtLang = getLanguageName(props.target);
    const [base, setBase] = useState("");
    // useEffect(() => {
    // 	fetchChartData(selectedOption.value,"", [{"field": "sourceLanguage","value": sourceLanguage.value}])
    // }, []);
    const [snackbar, setSnackbarInfo] = useState({
        open: false,
        message: "",
        variant: "success",
    });
    const [transliterationModelId, setTransliterationModelId] = useState("");
    const [showTransliteration, setShowTransliteration] = useState(true);

    const [lang, setLang] = useState("")
    useEffect(() => {
        const temp = Language.filter((element) => element.label === srcLang);
        setLang(temp[0].value);
    }, [srcLang])

    const handleSnackbarClose = () => {
        setSnackbarInfo({ ...snackbar, open: false });
    };
    const clearAll = () => {
        setSourceText("");
        setTarget("");
    };

    const fetchTransliterationModel = async () => {
        console.log("props.source, props.target", props.source, props.target);
        const apiObj = new GetTransliterationModelID("en", props.source);
        source && source !== "en" && fetch(apiObj.apiEndPoint(), {
            method: "GET",
            // headers: apiObj.getHeaders().headers,
        })
            .then(async (resp) => {
                let rsp_data = await resp.json();
                if (resp.ok) {
                    setTransliterationModelId(rsp_data.modelId);
                }
            })
            .catch((err) => {
                setSnackbarInfo({
                    ...snackbar,
                    open: true,
                    message:
                        "Transliteration Model ID Not Present.",
                    variant: "error",
                });
            });
    }

    useEffect(() => {
        fetchTransliterationModel();
    }, [source])

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
        setOutputText();
        setLoading(true);
        const apiObj = new HostedInferenceAPI(
            modelId,
            sourceText.trim(),
            task,
            false,
            "",
            "",
            ""
        );

        fetch(apiObj.apiEndPoint(), {
            method: "POST",
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody()),
        })
            .then(res => res.json())
            .then(async (response) => {
                let nerOutput = response?.output
                if (nerOutput && nerOutput.length > 0) {
                    let nerSource = nerOutput[0]?.source;
                    let predictions = nerOutput[0]?.nerPrediction;

                    let newText;
                    if (predictions && predictions.length > 0) {
                        let allTagsArr = [... new Set(predictions.map(x => x.tag))];
                        predictions.map((el, i) => {
                            allTagsArr.map((ele, index) => {
                                if (el.tag == ele) {
                                    el.color = colors[index] ? colors[index] : {light: "#eb99ff", dark: "#d11aff"};
                                    return el;
                                }
                            })
                        })

                        let splittedSourceTextArr = nerSource.split(" ");

                        const getStartEnd = (sub) => [nerSource.indexOf(sub), nerSource.indexOf(sub) + sub.length]


                        let tagStyle = (tagType, bgColor)=> {
                            return{
                                backgroundColor: tagType == "outer" ? bgColor?.light : bgColor?.dark,
                                fontSize: tagType == "inner" ? "15px" : "inherit",
                                paddingLeft: tagType == "inner" ? 5 : 3,
                                paddingRight: tagType == "inner" ? 5 : 3,
                                paddingTop: tagType == "inner" ? 0 : 4,
                                paddingBottom: tagType == "inner" ? 0 : 4,
                                borderRadius: tagType == "inner" ? 8 : 3,
                                color: tagType == "outer" ? "inherit" : "#FFFFFF",
                                textAlign: "center",
                                cursor: tagType == "inner" ? "context-menu" : "inherit",
                            }
                        }

                        newText = <Typography style={{lineHeight: "35px"}}>
                            {
                                splittedSourceTextArr.map((source, srcIndex) => {
                                    let startIndexOfWord = getStartEnd(source)[0];
                                    let endIndexOfWord = getStartEnd(source)[1];
                                    let foundPredictionEle = predictions.find(predictionsObj => 
                                        predictionsObj.token == source && startIndexOfWord == predictionsObj?.tokenStartIndex && endIndexOfWord == predictionsObj?.tokenEndIndex
                                    );
                                    if(foundPredictionEle){
                                        // console.log("foundPredictionEle ------- ", foundPredictionEle);
                                         return <span><span style={tagStyle("outer", foundPredictionEle?.color)}>
                                                {source} <span style={tagStyle("inner", foundPredictionEle?.color)}>{foundPredictionEle?.tag}</span></span> </span>
                                    }else {
                                        return <span>{source} </span>
                                    }
                                })
                            }
                        </Typography>
                    } else {
                        newText = <Typography >{nerSource}</Typography>
                    }
                    setLoading(false);
                    setOutputText(newText)
                }
            })
            .catch(error => {
                setLoading(false);
                console.log('error', error);
                setSnackbarInfo({ open: true, message: error.message ? error.message : "Something went wrong", variant: 'error' })
            })
    }
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

    const handleFeedbackSubmit = (feedback) => {
        const apiObj = new SubmitFeedback('ner', sourceText, base, feedback)
        fetch(apiObj.apiEndPoint(), {
            method: 'post',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        })
            .then(async resp => {
                const rsp_data = await resp.json();
                if (resp.ok) {
                    setSnackbarInfo({ open: true, message: rsp_data.message, variant: 'success' })
                } else {
                    setSnackbarInfo({ open: true, message: rsp_data.message, variant: 'error' })
                }
            });
        setTimeout(() => setSnackbarInfo({ open: false, message: "", variant: null }), 3000);
    }

    // console.log("inferenceEndPoint ----- ", inferenceEndPoint);

    return (
        <>
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

                                sm={3}
                                md={3}
                                lg={3}
                                xl={3}
                                className={classes.headerContent}
                            >
                                <Typography variant="h6" className={classes.hosted}>
                                    Input Text
                                </Typography>
                            </Grid>
                            {transliterationModelId &&
                                <Grid item xs={5} sm={3} md={5} lg={5} xl={5}
                                    style={{
                                        display: "inline-flex",
                                        alignItems: "baseline",
                                        justifyContent: "center"
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

                                </Grid>}
                        </Grid>
                    </CardContent>
                    <CardContent>
                        <Typography variant="caption">{translate("label.maxCharacters")}</Typography>
                    </CardContent>
                    <CardContent>
                        {showTransliteration && transliterationModelId ? <IndicTransliterate
                            lang={lang}
                            customApiURL={`${configs.BASE_URL_AUTO + endpoints.hostedInference}`}
                            transliterationModelId={transliterationModelId}
                            value={sourceText}
                            onChangeText={(text) => {
                                setSourceText(text);
                            }}
                            renderComponent={(props) => <textarea placeholder="Enter text here..." className={classes.textAreaTransliteration} {...props} />}
                        /> : <textarea placeholder="Enter text here..." value={sourceText} onChange={(e) => setSourceText(e.target.value)} className={classes.textAreaTransliteration} />
                        }
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
                            display: "flex",
                            // justifyContent: "center",
                            height: "15vh",
                            overflowY: "auto"
                        }}
                    >
                        {outputText}

                    </CardContent>
                    {outputText && <div >
                        <div>
                            <Button variant="contained" size="small" style={{ float: "right", marginRight: "25px", backgroundColor: "#FD7F23" }} onClick={() => setModal(true)}>
                                <ThumbUpAltIcon className={classes.feedbackIcon} />
                                <ThumbDownAltIcon className={classes.feedbackIcon} />
                                <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
                            </Button>
                        </div>
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
        </>
    );
};
export default withStyles(DatasetStyle)(HostedInference);
