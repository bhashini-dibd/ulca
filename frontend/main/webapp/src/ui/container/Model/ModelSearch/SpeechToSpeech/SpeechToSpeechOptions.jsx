import { useState ,useEffect} from "react";
import {
  Grid,
  Card,
  Typography,
  CardContent,
  Button,
  TextField,
  Tabs,
  Tab,
  AppBar,
  MuiThemeProvider,
  createTheme,
  CardActions,
  IconButton,
  Tooltip,
  FormControl,
  
  
} from "@material-ui/core";
import SimpleDialogDemo from "../../../../components/common/Feedback";
//import ThumbsUpDownOutlinedIcon from '@mui/icons-material/ThumbsUpDownOutlined';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FileCopyIcon from "@material-ui/icons/FileCopy";
import { withStyles } from "@material-ui/styles";
import { translate } from "../../../../../assets/localisation";
import DatasetStyle from "../../../../styles/Dataset";
// import MyAccordion from "../../../../components/common/Accordion";
import TabPanel from "../../../../components/common/TabPanel";

const SpeechToSpeechOptions = (props) => {
  const {
    classes,
    audio,
    recordAudio,
    AudioReactRecorder,
    Stop,
    handleStartRecording,
    Start,
    handleStopRecording,
    data,
    handleCompute,
    onStopRecording,
    url,
    error,
    handleSubmit,
    setUrl,
    setError,
    output,
    handleTextAreaChange,
    textArea,
    makeTTSAPICall,
    makeTranslationAPICall,
    index,
    handleTabChange,
    clearAsr,
    clearTranslation,
    handleCopyClick,
    gender,
    genderValue,
  } = props;
  
  const renderVoiceRecorder = () => {
   
    return (
      <Grid container spacing={1}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {recordAudio === "start" ? (
            <div className={classes.center}>
              <img
                src={Stop}
                alt=""
                onClick={() => handleStopRecording()}
                style={{ cursor: "pointer" }}
              />{" "}
            </div>
          ) : (
            <div className={classes.center}>
              <img
                src={Start}
                alt=""
                onClick={() => handleStartRecording()}
                style={{ cursor: "pointer" }}
              />{" "}
            </div>
          )}
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <div className={classes.center}>
            <Typography style={{ height: "12px" }} variant="caption">
              {recordAudio === "start" ? "Recording..." : ""}
            </Typography>{" "}
          </div>
          <div style={{ display: "none" }}>
            <AudioReactRecorder
              state={recordAudio}
              onStop={onStopRecording}
              style={{ display: "none" }}
            />
          </div>
          <div className={classes.centerAudio} style={{height: "50px"}}>
            {data ? (
              <audio
                src={data}
                style={{ minWidth: "100%" }}
                controls
                id="sample"
              ></audio>
            ) : (
              <></>
            )}
          </div>

        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Grid container spacing={1}>
            <Grid item xs={8} sm={12} md={10} lg={10} xl={10}>
              <Typography variant={"caption"}>
                {translate("label.maxDuration")}
              </Typography>
            </Grid>
            <Grid
              item
              xs={4}
              sm={12}
              md={2}
              lg={2}
              xl={2}
              className={classes.flexEndStyle}
            >
              <Button
              style={{}}
                color="primary"
                variant="contained"
                size={"small"}
                disabled={data ? false : true}
                onClick={() => handleCompute()}
              >
                Convert
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    );
  };

  const renderURLInput = () => {
    return (
      <Grid container spacing={1}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <TextField
            fullWidth
            color="primary"
            label="Paste the public repository URL"
            value={url}
            error={error.url ? true : false}
            helperText={error.url}
            onChange={(e) => {
              setUrl(e.target.value);
              setError({ ...error, url: false });
            }}
          />
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <div
            style={{
              display: "flex",
              marginTop: "5.5vh",
              justifyContent: "center",
            }}
          >
            <audio style={{ minWidth: "100%" }} controls src={url}></audio>
          </div>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Grid container>
            <Grid item xs={12} sm={12} md={10} lg={10} xl={10}>
              <Typography variant={"caption"}>
                {translate("label.maxDuration")}
              </Typography>
            </Grid>
            <Grid
              item
              xs={12}
              sm={12}
              md={2}
              lg={2}
              xl={2}
              className={classes.flexEndStyle}
            >
              <Button
                color="primary"
                disabled={url ? false : true}
                variant="contained"
                size={"small"}
                onClick={handleSubmit}
              >
                {translate("button.convert")}
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    );
  };

  const renderAccordionDetails = (
    placeholder,
    textAreaLabel,
    value,
    prop,
    input,
    handleSubmitClick,
    handleClearSubmit,
    color
  ) => {
    return (
      <Card className={classes.asrCard}>
        <Grid
          container
          className={classes.cardHeader}
          style={{ backgroundColor: color }}
        >
          <Typography variant="h6" className={classes.titleCard}>
            {placeholder}
          </Typography>
        </Grid>
        <CardContent>
          <Grid container spacing={1}>
            <Grid
              item
              xs={12}
              sm={12}
              md={12}
              lg={12}
              xl={12}
              style={{ position: "relative" }}
            >
              <div>
                <textarea
                  disabled
                  placeholder={placeholder}
                  rows={2}
                  value={value}
                  className={classes.textArea}
                  style={{
                    color: "#404040",
                    border: "1px solid grey",
                    margin: 0,
                    paddingTop: '20px',
                  }}
                />
              </div>
              <IconButton
                style={{ position: "absolute", top: "0", right: "0", }}
                onClick={() => handleCopyClick(prop)}
              >

                <Tooltip title="copy-paste" style={{ marginBottom: "10px" }}>
                  <FileCopyIcon color="primary" fontSize="small" />
                </Tooltip>

              </IconButton>

            </Grid>
            <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
              <textarea
                placeholder={textAreaLabel}
                rows={2}
                className={classes.textArea}
                value={input}
                onChange={(e) => handleTextAreaChange(e, prop)}
                style={{ border: "1px solid grey" }}
              />
            </Grid>
          </Grid>
        </CardContent>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Grid container spacing="2">
            <Grid
              item
              xs={12}
              sm={12}
              md={10}
              lg={10}
              xl={10}
              className={classes.flexEndStyle}
            >
              <div style={{marginRight:"400px"}}>
              
               </div>
              <Button
              style={{color:"#707070"}}
                variant="outlined"
                size="small"
                color="primary"
                disabled={input && input.trim() ? false : true}
                onClick={handleClearSubmit}
              >
                Clear
              </Button>
            </Grid>
            <Grid item xs={12} sm={12} md={2} lg={2} xl={2}>
              <Button
               style={{color:"#707070"}}
                variant="outlined"
                size="small"
                color="primary"
                onClick={handleSubmitClick}
                disabled={input && input.trim() ? false : true}
              >
                Submit
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Card>
    );
  };

  const renderAccordion = () => {
    return (
      <Grid container spacing={3}>
        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
          {renderAccordionDetails(
            "ASR Output",
            "Corrected ASR Output",
            output.asr,
            "asr",
            textArea.asr,
            makeTranslationAPICall,
            clearAsr,
            "#D6EAF8"
          )}
        </Grid>
        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
          {renderAccordionDetails(
            "Translation Output",
            "Corrected Translation Output",
            output.translation,
            "translation",
            textArea.translation,
            makeTTSAPICall,
            clearTranslation,
            "#E9F7EF"
          )}
          {console.log(  output.asr,"kkkkk")}
        </Grid>
      </Grid>
    );
  };

  const renderOutput = () => {
    return (
      <Card className={classes.asrCard}>
        <Grid container className={classes.cardHeader}>
          <Typography variant="h6" className={classes.titleCard}>
            {`${translate("label.output")}`}
            
          </Typography>
        </Grid >
        
        <CardContent
          style={{
           justifyContent: "center",
            alignItems: "center",
            position: "relative",
            top: "25%",
          }}
        >
          {audio ? (
         <div>
            <audio
              style={{
                width: "100%",
               
                justifyContent: "center",
                alignContent: "center",
              }}
              src={audio}
              controls
            ></audio>
          <div style={{position:"absolute", right:"105px", top:"135px"}} >
        <SimpleDialogDemo  />
        </div>
           </div>
          ) : (
            <></>
           
          )}
           
         </CardContent>
         </Card>
    );
  };

  const getTheme = () =>
    createTheme({
      overrides: {
        PrivateTabIndicator: {
          colorSecondary: {
            backgroundColor: "#2A61AD",
          },
        },
        MuiButton: {
          root: {
            minWidth: "25",
            borderRadius: "none",
          },
          label: {
            textTransform: "none",
            fontFamily: '"Roboto", "Segoe UI"',
            fontSize: "16px",
            //fontWeight: "500",
            //lineHeight: "1.14",
            letterSpacing: "0.16px",
            textAlign: "center",
            height: "19px",
            "@media (max-width:640px)": {
              fontSize: "10px",
            },
          },
          sizeLarge: {
            height: "40px",
            borderRadius: "20px",
          },
          sizeMedium: {
            height: "40px",
            borderRadius: "20px",
          },
          sizeSmall: {
            height: "30px",
            borderRadius: "20px",
          },
        },
        MuiTab: {
          textColorInherit: {
            fontFamily: "Rowdies",
            fontWeight: 300,
            fontSize: "1.125rem",
            textTransform: "none",
            "&.Mui-selected": {
              color: "#2A61AD",
            },
          },
        
        },
      },
    
    });

  const renderTabs = () => {
    return (
      <Card className={classes.asrCard}>
        <Grid container className={classes.cardHeader}>
          <MuiThemeProvider theme={getTheme}>
          <AppBar className={classes.appTab} position="static">
              <Grid container>
              <Grid  item xs={12} sm={12} md={12} lg={12} xl={12} >
              <Tabs value={index} onChange={handleTabChange}   variant={"scrollable"} scrollButtons={"off"} >
                <Tab label={"Live Recording Inference"} />
                <Tab label={"Batch Inference"} />
                <Grid  item xs={12} sm={12} md={12} lg={12} xl={12}>
              <FormControl className={classes.formControl}>
               <Select  
                 MenuProps={{
                 anchorOrigin: {
                 vertical: "bottom",
                 horizontal: "left"
              },
                
            getContentAnchorEl: null
        }}  value={genderValue} className={classes.genderdropdown}    onChange={e => {gender ( (e.target.value).toLowerCase());
         
            
          }} >
               <MenuItem value="male">Male</MenuItem>
               <MenuItem value="female">Female</MenuItem>
              </Select>
               </FormControl>
               </Grid>
              </Tabs>
             
              </Grid>
             
               </Grid>
            </AppBar>
           <TabPanel value={index} index={0}>
              {renderVoiceRecorder()}
            </TabPanel>
            <TabPanel value={index} index={1}>
              {renderURLInput()}
            </TabPanel>
          </MuiThemeProvider>
         
        </Grid>
      </Card>
    );
  };

  return (
    <Grid container spacing={3} className={classes.stspart}>
      <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
        {renderTabs()}
     
      </Grid>
      <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
        {renderOutput() }
        
      </Grid>
      {audio ? (
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Typography variant="h5" style={{ marginBottom: "1%" }}>
            Intermediate Output 
          </Typography>
          {renderAccordion()}
        </Grid>
      ) : (
        <></>
      )}
    </Grid>
  );
};

export default withStyles(DatasetStyle)(SpeechToSpeechOptions);
