import React, { useState, useEffect } from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import Popover from '@material-ui/core/Popover';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
// import FeedbackStyle from "../../styles/Feedback";
import DatasetStyle from "../../styles/Dataset";
import { translate } from "../../../assets/localisation";
import '../../styles/css/GlobalCssSlider.css';
import { StyledRating } from './StyledRating';
import { withStyles } from '@material-ui/core/styles';
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import {
  makeStyles,
  MuiThemeProvider,
  createMuiTheme
} from "@material-ui/core/styles";
import { Form, Field } from 'react-final-form';
import { TextField } from '@material-ui/core';



function SimpleDialogDemo(props) {
  const { classes, setSuggestEdit, suggestEdit, asrValue, ttsValue, setModal, handleOnChange,
    handleFeedbackSubmit, handleCommentChange, comment, setComment, setSuggestEditValues, output } = props;
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [anchorE2, setAnchorE2] = React.useState(null);
  const [value, setValue] = React.useState(-1);
  const [detailedFeedback, setDetailedFeedback] = useState(false);
  const [rating1, setRating1] = React.useState(0);
  const [rating2, setRating2] = React.useState(0);
  const [rating3, setRating3] = React.useState(0);
  const [data2, setData2] = React.useState(false);

  useEffect(() => {
    return () => {
      handleClose();
      setSuggestEdit(null);
      setSuggestEditValues({ asr: output.asr, translation: output.translation })
      setComment("")
    }
  }, [])



  const handleClick = (event) => {
    setData2(false)
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setValue(0)
  };
  const handleClosefeedback = (reason) => {
    setAnchorE2(null);
    handleClose();


  }
  const handleClickfeedback = (event) => {
    setData2(true)
    //handleClose();
    setAnchorE2(event.currentTarget);
  };

  const open1 = Boolean(anchorE2);
  const id1 = open1 ? 'simple-popover' : undefined;


  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const divStyle = {
    display: 'flex',
    alignItems: 'center'
  };

  const handleRatingChange = (event, newValue) => {
    setValue(newValue);
    if (newValue <= 3)
      setDetailedFeedback(true);
    else
      setDetailedFeedback(false);
  }
  const theme2 = ({
    overrides: {
      MuiButton: {
        root: {
          borderRadius: "50px",
          marginLeft: "30px",
          "@media (max-width:400px)": {
            width: "90px",
            height: "26px",
          },
        },
        label: {
          textTransform: 'none'
        }
      },
      MuiPopover: {
        root: {
          // "@media (max-width:400px)": {
          //   width: "100%",
          //   maxHeight: "500px",
          // },
        },
        paper: {

          // right: "100px",
          // bottom:"25px",
          // position:"fixed",
          // width:"430px",
          // maxHeight:"500px",

          padding: "13px 7px 0px 9px",
          // "@media (max-width:400px)": {
          //   width: "100%",
          //   maxHeight: "500px",
          // },
        }
      },
      MuiTypography: {
        root: {
          padding: "5px",
          fontSize: "10px"

        },
        colorPrimary: {
          "@media (max-width:650px)": {
            fontSize: "12px",
            height: "21px",
            lineHeight: "0.7rem"
          },
        },
        body2: {

          // "@media (max-width:400px)": {
          //   lineHeight: "16px"
          // },

        }

      },
      MuiRating: {
        label: {
          paddingLeft: "5px"
        }
      },
      MuiBox: {
        root: {
          // "@media (max-width:400px)": {
          //   width: "332px",

          // },
        }
      },

    }
  });
  const BootstrapButton = withStyles({
    root: {
      boxShadow: 'none',
      textTransform: 'none',
      fontSize: 10,
      padding: '18px 5px',
      border: '1px solid',
      lineHeight: 1.1,

      borderColor: '#0063cc',
      "@media (max-width:400px)": {
        padding: '0px 0px',


      },


    },
  })(Button);

  // return (

  //   <div >
  //     {/* <Button variant="contained" size="small" className={classes.feedbackbutton} onClick={handleClick}>
  //       <ThumbUpAltIcon className={classes.feedbackIcon} />
  //       < ThumbDownAltIcon className={classes.feedbackIcon} />
  //       <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
  //     </Button> */}
  //     {/* <Button  id="detailed" >abc</Button> */}
  //     <div>
  //       {data2 === false && <div>
  //         <Typography className={classes.typography} align="center" >   {translate("lable.feedback1")} <br />  {translate("lable.feedbacks")}</Typography>

  //         <StyledRating
  //           className={classes.rating}
  //           size="large"
  //           name="simple-controlled"
  //           value={value}
  //           onChange={handleRatingChange}

  //         />


  //         {/* < Typography className={classes.feedbacktypography} variant="body2"  >  {translate("lable.verybad")}  < Typography variant="body2" style={{ float: "right", fontSize: "12px" }} >  {translate("lable.verygood")}  </Typography>   </Typography> */}

  //         <div className={classes.root}>

  //           <Grid container justifyContent="center">
  //             <Grid item>

  //               {detailedFeedback ? <Link
  //                 id="simple-popover1"
  //                 component="button"
  //                 variant="body2"
  //                 onClick={handleClickfeedback}
  //                 style={{ color: "#FD7F23", fontSize: "13px", textDecoration: "underline" }}
  //               >
  //                 {translate("link.feedback")}
  //               </Link> : <></>}

  //             </Grid>
  //           </Grid>
  //         </div>
  //         <Button variant="outlined" size="small" color="primary" className={classes.submitbutton}  >
  //           {translate("button.submit")}
  //         </Button>

  //         <Typography className={classes.typographys} align="center" variant="body2" component="div" >
  //           {translate("lable.feedback2")}</Typography>
  //       </div>}
  //       <MuiThemeProvider theme={theme2}>
  //         <div>
  //           {data2 === true && <div> <div style={{ position: "absolute", right: "3px", top: "4px" }}>

  //             <IconButton

  //               size="small"
  //               aria-label="close"
  //               color="inherit"
  //               onClick={handleClosefeedback}

  //             >
  //               <CloseIcon fontSize="small" />
  //             </IconButton>
  //           </div>
  //             <Typography variant="body2" className={classes.typography2}> {translate("lable.feedback3")}</Typography>
  //             <Box p={2}>

  //               <Typography variant="body2" className={classes.typography1}>Rate  <span style={{ fontWeight: "bold" }}>Speech to Text</span> Quality</Typography>

  //               <StyledRating
  //                 //  style={{position:"fixed" ,top:"30px",left:"40px"}}
  //                 size="large"
  //                 value={rating1}
  //                 onChange={(event, newValue) => {
  //                   setRating1(newValue)
  //                 }} />

  //               <Button className={classes.buttonsuggest} variant="outlined" size="small" color="primary" onClick={() => setSuggestEdit("asr")}>
  //                 <Typography variant="body2" color="primary" > {translate("button.Suggest an edit")}</Typography>
  //               </Button>
  //               <Typography variant="body2" className={classes.typography1}>Rate <span style={{ fontWeight: "bold" }}  >Translate  Text</span>  Quality</Typography>
  //               <StyledRating
  //                 size="large"
  //                 value={rating2}
  //                 onChange={(event, newValue) => {
  //                   setRating2(newValue)
  //                 }} />
  //               <Button variant="outlined" size="small" color="primary" className={classes.buttonsuggest} onClick={() => setSuggestEdit("tts")}>
  //                 <Typography variant="body2" color="primary">  {translate("button.Suggest an edit")}</Typography>

  //               </Button>
  //               <Typography variant="body2" className={classes.typography1} >Rate  <span style={{ fontWeight: "bold" }}>Translated Speech</span> Quality </Typography>
  //               <StyledRating
  //                 size="large"
  //                 value={rating3}
  //                 onChange={(event, newValue) => {
  //                   setRating3(newValue)
  //                 }} />
  //             </Box>
  //             <div style={{ borderBottom: "1px solid #ECE7E6 ", width: "300px", margin: "auto", paddingBottom: "10px" }}></div>

  //             <Typography variant="body2" style={{ margin: "10px 10px 10px 10px" }}> {translate("lable.feedback4")}</Typography>
  //             <Grid container justifyContent="center">
  //               <Grid item>
  //                 <TextareaAutosize
  //                   aria-label="minimum height"
  //                   minRows={4}
  //                   className={classes.textareaAutosize}
  //                   style={{ width: 250 }}
  //                 />

  //               </Grid>
  //               <Grid container justifyContent="center">
  //                 <Grid items>
  //                   <Button variant="outlined" size="small" color="primary" style={{ margin: "10px" }}  >
  //                     {translate("button.submit")}
  //                   </Button>
  //                 </Grid>
  //               </Grid>
  //             </Grid> </div>}
  //         </div>
  //       </MuiThemeProvider>
  //     </div>
  //   </div>
  // );

  console.log(props.questions)

  return <Form
    onSubmit={() => handleFeedbackSubmit(value, rating1, rating2, rating3)}
    render={({ handleSubmit }) => (<form onSubmit={handleSubmit}>
      <Grid container className={classes.feedbackgrid}
      >
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <div className={classes.iconbutton}>
            <IconButton
              size="small"
              aria-label="close"
              color="inherit"
              onClick={() => setModal(false)}
            >
              <CloseIcon fontSize="small" />
            </IconButton>
          </div>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Typography className={classes.typography} align="center" >{translate("lable.Areyousatisfiedwiththis")} <br />  {translate("lable. translation")}</Typography>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <StyledRating
            className={classes.rating}
            size="large"
            name="simple-controlled"
            value={value}
            onChange={handleRatingChange}
          />
        </Grid>
      </Grid>
      {/* < Typography className={classes.feedbacktypography} variant="body2"  >  {translate("lable.verybad")}  < Typography variant="body2" style={{ float: "right", fontSize: "12px" }} >  {translate("lable.verygood")}  </Typography>   </Typography> */}
      {/* <div className={classes.root}>
          <Grid container justifyContent="center">
            <Grid item>{detailedFeedback ? <Link
              id="simple-popover1"
              component="button"
              variant="body2"
              onClick={handleClickfeedback}
              style={{ color: "#FD7F23", fontSize: "13px", textDecoration: "underline" }}
            >
              {translate("link.feedback")}
            </Link> : <></>}

            </Grid>
          </Grid>
        </div> */}
      {/* <Button variant="outlined" size="small" color="primary" className={classes.submitbutton}  >
          {translate("button.submit")}
        </Button> */}
      <MuiThemeProvider theme={theme2}>
        {
          value <= 3 && value > 0 &&
          <div className={classes.popover2} style={{}}>
            <Typography variant="body2" className={classes.typography2}> {translate("lable.Pleaserateyourexperience")}</Typography>
            <Box p={2}>
              <Grid container className={classes.feedbackgrid} >
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  <Typography variant="body2" className={classes.typography1}>Rate  <span style={{ fontWeight: "bold" }}>Speech to Text</span> Quality</Typography>
                </Grid>
                <Grid items xs={10} sm={10} md={10} lg={10} xl={10}>
                  <StyledRating
                    size="large"
                    value={rating1}
                    onChange={(event, newValue) => {
                      setRating1(newValue)
                    }} />
                </Grid>
                <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                  <Button className={classes.buttonsuggest} variant="outlined" color="primary" onClick={() => setSuggestEdit("asr")}>
                    <Typography variant="body2" color="primary" className={classes.buttonsuggestlable}>  {translate("button.Suggest an edit")}</Typography>
                  </Button>

                </Grid>
                <br />
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  {suggestEdit === 'asr' && <TextField
                    className={classes.textfield}
                    fullWidth
                    variant="outlined"
                    onChange={(e) => handleOnChange('asr', e)}
                    value={asrValue}
                  />}
                </Grid>
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  <Typography variant="body2" className={classes.typography1}>Rate <span style={{ fontWeight: "bold" }}  >Translated Text</span>  Quality</Typography>
                </Grid>
                <Grid item xs={10} sm={10} md={10} lg={10} xl={10}>
                  <StyledRating
                    size="large"
                    value={rating2}
                    onChange={(event, newValue) => {
                      setRating2(newValue)
                    }} />
                </Grid>
                <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                  <Button className={classes.buttonsuggest} variant="outlined" color="primary" onClick={() => setSuggestEdit("tts")}>
                    <Typography variant="body2" color="primary" className={classes.buttonsuggestlable}>  {translate("button.Suggest an edit")}</Typography>
                  </Button>

                </Grid>
                <br />
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  {suggestEdit === 'tts' && <TextField
                    className={classes.textfield}
                    fullWidth
                    variant="outlined"
                    onChange={(e) => handleOnChange('translation', e)}
                    value={ttsValue} />}
                </Grid>
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  <Typography variant="body2" className={classes.typography1} >Rate  <span style={{ fontWeight: "bold" }}>Translated Speech</span> Quality </Typography>
                </Grid>
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                  <StyledRating
                    size="large"
                    value={rating3}
                    onChange={(event, newValue) => {
                      setRating3(newValue)
                    }} />
                </Grid>
                <br />
              </Grid>
            </Box>
            <div className={classes.border} ></div>

            <Typography variant="body2" className={classes.Addyourcomments} > {translate("lable.Addyourcomments")}</Typography>
            <Grid container justifyContent="center">
              <Grid item>
                <TextareaAutosize
                  aria-label="minimum height"
                  minRows={4}
                  className={classes.textareaAutosize}
                  onChange={handleCommentChange}
                  value={comment}
                />

              </Grid>
            </Grid>
          </div>
        }
        <Grid container justifyContent="center">
          <Grid item>
            <Button type="submit" variant="contained" size="small" color="primary" className={classes.submitbutton} disabled={value > 0 ? false : true} >
              {translate("button.submit")}
            </Button>
          </Grid>
        </Grid>
        <Typography className={classes.typographys} align="center" variant="body2" component="div" >
          {translate("lable.feedbackwillbeusedtohelpimprovetheproduct")}</Typography>
      </MuiThemeProvider>
    </form>)}
  />
}
export default withStyles(DatasetStyle)(SimpleDialogDemo);
