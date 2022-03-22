import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ThumbUpAltIcon from '@material-ui/icons/ThumbUpAlt';
import ThumbDownAltIcon from '@material-ui/icons/ThumbDownAlt';
import Popover from '@material-ui/core/Popover';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
import FeedbackStyle from "../../styles/Feedback";
import { translate } from "../../../assets/localisation";
import '../../styles/css/GlobalCssSlider.css';
import { StyledRating } from './StyledRating';
import { withStyles } from '@material-ui/core/styles';
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import CheckboxesGroup from "../common/FeedbackCheckbox"
import {
  makeStyles,
  MuiThemeProvider,
  createMuiTheme
} from "@material-ui/core/styles";




function FeedbackPopover(props) {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [value, setValue] = React.useState(0);
  const [detailedFeedback, setDetailedFeedback] = useState(false);
  const [data2, setData2] = React.useState(false);
  const [state, setState] = React.useState({
    checkedA: true,
    checkedB: true,
    checkedC: true,
    checkedD: true,
  });
  

  const handleChange = (event) => {
    setState({ ...state, [event.target.name]: event.target.checked });
  };

  const handleClick = (event) => {
    setData2(false)
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handleClosefeedback = ( reason) => {
   handleClose();
    
    
  }
  const handleClickfeedback = (event) => {
    setData2(true)
    
  };

 


  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;
  // console.log(anchorEl)

  

  const handleRatingChange = (event, newValue) => {
    setValue(newValue);
    if (newValue <= 3)
      setDetailedFeedback(true);
    else
      setDetailedFeedback(false);
  }
  const theme2 =({
    overrides: {
      MuiButton: {
        root: {
         borderRadius:"50px",
         marginLeft:"30px",
         "@media (max-width:400px)": {
          width: "90px",
          height: "26px",
         },
       },
     },
      MuiPopover: {
        root: { "@media (max-width:400px)": {
          width:"100%",
          maxHeight:"500px",
            },},
        paper: {
          padding:"0px 0px 0px 0px",
          "@media (max-width:400px)": {
           width:"100%",
           maxHeight:"500px",
             },
         }
      },
      MuiTypography:{
        root:{
          padding:"5px",
          fontSize:"10px"

        },
        body1:{
            fontSize:"15px"
        },
        colorPrimary:{
          "@media (max-width:400px)": {
            fontSize: "13px",
             },
          }

      },
    

        MuiBox:{
          root:{
            "@media (max-width:400px)": {
              width:"332px",
             
               },
          }
        },
        
      
    }
  });
  

  return (

    <div style={{position:"relative",left:"700px",top:"-10px"}}>
      <Button variant="contained" size="small" className={classes.feedbackbutton} onClick={handleClick}>
        <ThumbUpAltIcon className={classes.feedbackIcon} />
        < ThumbDownAltIcon className={classes.feedbackIcon} />
        <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
      </Button>
      
          <div>
     {setAnchorEl!==null &&(<Popover
        id={id}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: '',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
      >
        
  { data2 === false && <div>
        <Typography className={classes.typography} style={{marginBottom:"10px"}} align="center" >   {translate("lable.feedback1")} <br />  {translate("lable.feedbacks")}</Typography>

        <StyledRating
          className={classes.rating}
          size="large"
          name="simple-controlled"
          value={value}
          onChange={handleRatingChange}

        />

{/* < Typography className={classes.feedbacktypography} variant="body2"  >  {translate("lable.verybad")}  < Typography variant="body2" style={{ float: "right", fontSize: "12px" }} >  {translate("lable.verygood")}  </Typography>   </Typography> */}
       

        <div className={classes.root}>

          <Grid container justifyContent="center">
            <Grid item>

              {detailedFeedback ? <Link
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
        </div>
        <Button variant="outlined" size="small" color="primary" className={classes.suggestbutton}  >
        {translate("button.Suggest an edit")}
        </Button>
        <Button variant="outlined" size="small" color="primary"  className={classes.buttonsubmit}  >
          {translate("button.submit")}
        </Button>

        <Typography className={classes.typographys} align="center" variant="body2" component="div" >
          {translate("lable.feedback2")}</Typography>
          </div>}
          <MuiThemeProvider theme={theme2}>
          <div>
          {  data2 === true && <div> <div style={{ position: "absolute",right: "3px",top:"2px" }}>

              <IconButton
              
                     size="small"
                     aria-label="close"
                     color="inherit"
                    onClick={ handleClosefeedback}
                    
                   >
                     <CloseIcon fontSize="small" />
                   </IconButton>
                   </div> 
        <Typography variant="body2" style={{  margin: "17px 10px -10px 10px",
    fontSize: "16px"}}> {translate("lable.feedback3")}</Typography>
        <Box p={5}>

      <CheckboxesGroup/>
        
        </Box>
        <div style={{ borderBottom: "1px solid #ECE7E6 ", width: "300px", margin: "auto", paddingBottom: "10px" }}></div>

        <Typography variant="body2" style={{ margin: "10px 10px 10px 10px" }}> {translate("lable.feedback4")}</Typography>
        <Grid container justifyContent="center">
          <Grid item>
            <TextareaAutosize
              aria-label="minimum height"
              minRows={4}
              className={classes.textareaAutosize}
              style={{ width: 250 }}
            />

          </Grid>
          <Grid container justifyContent="center">
            <Grid items>
              <Button variant="outlined" size="small" color="primary" style={{ margin: "10px" }}  >
                {translate("button.submit")}
              </Button>
            </Grid>
          </Grid>
        </Grid> </div>}
        </div>
        </MuiThemeProvider>
        
      </Popover>)}
      </div>
    </div>
  );
}
export default withStyles(FeedbackStyle)(FeedbackPopover);
