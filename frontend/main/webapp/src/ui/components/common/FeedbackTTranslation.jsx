import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
import DatasetStyle from "../../styles/Dataset";
import { translate } from "../../../assets/localisation";
import '../../styles/css/GlobalCssSlider.css';
import { StyledRating } from './StyledRating';
import { withStyles } from '@material-ui/core/styles';
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import CheckboxesGroup from "../common/FeedbackCheckbox"
import { Form } from 'react-final-form'
import { TextField } from '@material-ui/core';
import { useSelector } from "react-redux";
import SubmitFeedback from "../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";

import {
  MuiThemeProvider,
} from "@material-ui/core/styles";




function FeedbackPopover(props) {
  const { classes, setModal, suggestion, taskType, handleSubmit, target, handleOnChange, suggestEditValues } = props;
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [value, setValue] = React.useState(0);
  const [detailedFeedback, setDetailedFeedback] = useState(false);
  const [data2, setData2] = React.useState(false);
  const [opened, setOpened] = React.useState(false);
  const [textfield, setTextfield] = React.useState(null);
  const [textArea, setTextArea] = useState("")
  const [start, setStart] = React.useState(0);
  const questions = useSelector((state) => state.getMasterData.feedbackQns[0].values).filter(question => question.code === taskType)
  const [state, setState] = React.useState({
    checkedA: true,
    checkedB: true,
    checkedC: true,
    checkedD: true,
  });

  const generateState = () => {
    const obj = {}
    questions.forEach((q, i) => {
      obj[i] = false;
    });
    return obj
  }

  const [checkboxState, setCheckboxState] = useState(generateState());

  const handleCheckboxChange = (event) => {
    setCheckboxState({ ...checkboxState, [event.target.name]: event.target.checked });
  };

  const handleTextAreaChange = (e) => {
    setTextArea(e.target.value)
  }

  const handleChange = (event) => {
    setState({ ...state, [event.target.name]: event.target.checked });
  };

  const handleClick = (event) => {
    setData2(false)
    setAnchorEl(event.currentTarget);
  };

  const handleClickOpen = () => {
    setOpened(true);
  };
  const handleClose = () => {
    setOpened(false);
  };

  const onSubmit = () => {
    const feedback = [
      {
        feedbackQuestion: "Are you satisfied with this translation?",
        feedbackQuestionResponse: start
      },
      {
        feedbackQuestion: "Add your comments",
        feedbackQuestionResponse: textArea
      },
      {
        suggestedOutput: target !== undefined && suggestEditValues !== undefined ? (target === suggestEditValues ? null : suggestEditValues) : null
      }
    ]

    const keys = Object.keys(checkboxState);
    keys.forEach((key) => {
      feedback.push(
        {
          feedbackQuestion: questions[key].question,
          feedbackQuestionResponse: checkboxState[key] === true ? true : null
        }
      )
    })
    handleSubmit(feedback)
    setModal(false)
  }

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const handleRatingChange = (event, newValue) => {

    setStart(newValue)
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
      },
      MuiPopover: {
        root: {
          "@media (max-width:400px)": {
            width: "100%",
            maxHeight: "500px",
          },
        },
        paper: {
          padding: "0px 0px 0px 0px",
          "@media (max-width:400px)": {
            width: "100%",
            maxHeight: "500px",
          },
        }
      },
      MuiTypography: {
        root: {
          padding: "5px",
          fontSize: "10px"

        },
        body1: {
          fontSize: "15px"
        },
        colorPrimary: {
          "@media (max-width:400px)": {
            fontSize: "13px",
          },
        }

      },


      MuiBox: {
        root: {
          "@media (max-width:400px)": {
            width: "332px",

          },
        }
      },


    }
  });


  return (
    <Form
      onSubmit={onSubmit}
      render={({ handleSubmit }) => (
        <form onSubmit={handleSubmit}>
          <Grid container className={classes.feedbackgrid}>
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
            <Grid item justifyContent="center" xs={12} sm={12} md={12} lg={12} xl={12}>
              <Typography className={classes.typography} align="center" >{translate("lable.Areyousatisfiedwiththis")} <br />  {translate("lable. translation")}</Typography>
            </Grid>
            <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
              <StyledRating
                className={classes.rating}
                size="large"
                name="simple-controlled"
                value={start}
                onChange={handleRatingChange} />
            </Grid>
          </Grid>
          {(value === 0 || value > 3) &&
            (<Grid container justifyContent="center" className={classes.feedbackgrid}
            >
              <Grid item xs={12} sm={12} md={12} lg={12} xl={12} style={{ textAlign: "center", padding: "7px 0px 18px 0px" }}>
                <Link
                  id="simple-popover1"
                  component="button"
                  variant="body2"
                  onClick={() => setValue(true)}
                  style={{ color: "#FD7F23", fontSize: "13px", textDecoration: "underline" }}
                >
                  {translate("link.feedback")}
                </Link>
              </Grid>

              {suggestion &&
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12} className={classes.translationsuggestgrid}>
                  <Button variant="outlined" size="small" color="primary" className={classes.translationsuggestbutton} onClick={() => setTextfield(true)} 
                  // disabled={start > 0 ? false : true}
                   >
                    {translate("button.Suggest an edit")}
                  </Button>
                </Grid>}
              {textfield &&
                <Grid item xs={12} sm={12} md={12} lg={12} xl={12} style={{ paddingTop: "5px", marginLeft: "5px" }}>
                  <TextField
                    id="filled-multiline-static"
                    className={classes.translationtextfield}
                    onChange={(e) => handleOnChange(e)}
                    value={suggestEditValues}
                    fullWidth
                    multiline
                    maxRows={4}
                    size="small"
                    variant="outlined"
                  />
                </Grid>}

            </Grid>)
          }



          <MuiThemeProvider theme={theme2}>
            {
              value <= 3 && value > 0 &&
              <div className={classes.popover2} style={{}}> <Typography variant="body2" style={{
                margin: "17px 10px -10px 10px",
                fontSize: "16px"
              }}> {translate("lable.Pleaserateyourexperience")}</Typography>


                <CheckboxesGroup questions={questions} handleChange={handleCheckboxChange} state={checkboxState} />
                <div className={classes.border} ></div>

                <Typography variant="body2" className={classes.Addyourcomments} > {translate("lable.Addyourcomments")}</Typography>
                <Grid container justifyContent="center">
                  <Grid item>
                    <TextareaAutosize
                      aria-label="minimum height"
                      minRows={4}
                      className={classes.textareaAutosize}
                      onChange={handleTextAreaChange}
                      value={textArea}
                    />

                  </Grid>
                </Grid>
              </div>

            }
            <Grid container justifyContent="center">
              <Grid item>
                <Button type="submit" variant="contained" size="small" color="primary" style={{ textTransform: "capitalize" }} className={classes.submitbutton} 
                // disabled={start > 0 ? false : true}
                >
                  {translate("button.submit")}
                </Button>
              </Grid>
            </Grid>
            <Typography className={classes.typographys} align="center" variant="body2" component="div" >
              {translate("lable.feedbackwillbeusedtohelpimprovetheproduct")}</Typography>
          </MuiThemeProvider>


        </form>
      )}
    />
  )



}

export default withStyles(DatasetStyle)(FeedbackPopover);
