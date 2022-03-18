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
import TextField from '@material-ui/core/TextField';
import FeedbackStyle from "../../styles/Feedback";
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


function SimpleDialogDemo(props) {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [anchorE2, setAnchorE2] = React.useState(null);
  const [value, setValue] = React.useState(0);
  const [detailedFeedback, setDetailedFeedback] = useState(false);
  const [rating1, setRating1] = React.useState(0);
  const [rating2, setRating2] = React.useState(0);
  const [rating3, setRating3] = React.useState(0);
  
  

  


  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handleClosefeedback = ( reason) => {
    setAnchorE2(null);
    
    
  }
  const handleClickfeedback = (event) => {
    handleClose();
    setAnchorE2(event.currentTarget);
  };

  const open1 = Boolean(anchorE2);
  const id1 = open1 ? 'simple-popover' : undefined;


  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;
  // console.log(anchorEl)

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
  const theme2 = createMuiTheme({
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
        root: {},
        paper: {
          padding:"0px 0px 0px 0px",
          "@media (max-width:400px)": {
           width:"100%"
             },
         }
      },
      MuiTypography:{
        root:{
          padding:"5px",
          fontSize:"10px"

        },
        colorPrimary:{
          "@media (max-width:400px)": {
           
            fontSize: "12px",
             },
          }

      },
      MuiRating:{
        label:{
          padding:"3px",
          }
        }
    }
  });
  

  return (

    <div >
      <Button variant="contained" size="small" className={classes.feedbackbutton} onClick={handleClick}>
        <ThumbUpAltIcon className={classes.feedbackIcon} />
        < ThumbDownAltIcon className={classes.feedbackIcon} />
        <Typography variant="body2" className={classes.feedbackTitle} > {translate("button:feedback")}</Typography>
      </Button>
      <div>
      <Popover
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

        <Typography className={classes.typography} align="center" >   {translate("lable.feedback1")} <br />  {translate("lable.feedbacks")}</Typography>

        <StyledRating
          className={classes.rating}
          size="large"
          name="simple-controlled"
          value={value}
          onChange={handleRatingChange}

        />

         
        < Typography className={classes.feedbacktypography} variant="body2"  >  {translate("lable.verybad")}  < Typography variant="body2" style={{ float: "right", fontSize: "12px" }} >  {translate("lable.verygood")}  </Typography>   </Typography>

        <div className={classes.root}>

          <Grid container justifyContent="center">
            <Grid item>

              {detailedFeedback ? <Link
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
        <Button variant="outlined" size="small" color="primary" className={classes.submitbutton}  >
          {translate("button.submit")}
        </Button>

        <Typography className={classes.typographys} align="center" variant="body2" component="div" >
          {translate("lable.feedback2")}</Typography>
      </Popover>
      <MuiThemeProvider theme={theme2}>
       <Popover
       style={{width:"100%"}}
        id={id1}
        open={open1}
        anchor={anchorE2}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "center",
          horizontal: "right"
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "center"
        }}
//         anchorReference="anchorPosition"
//         anchorPosition={{ top: 180, left: 1000 }}
 
//  anchorOrigin={{horizontal: 'right', vertical: 'center'}}
//  targetOrigin={{horizontal: '', vertical: 'center'}}
        
      >
        
          <div style={{ position: "absolute",right: "0px",Button:"10px" }}>
              <IconButton
              
                     size="small"
                     aria-label="close"
                     color="inherit"
                    onClick={ handleClosefeedback}
                    
                   >
                     <CloseIcon fontSize="small" />
                   </IconButton>
                   </div> 
        <Typography variant="body2" className={classes.typography2}> {translate("lable.feedback3")}</Typography>
        <Box p={2}>

          <Typography variant="body2" className={classes.typography1}>Rate  <span style={{ fontWeight: "bold" }}>Speech to Text</span> Quality</Typography>
         
          <StyledRating
          //  style={{position:"fixed" ,top:"30px",left:"40px"}}
          size="large"
           value={rating1}
          onChange={(event, newValue) => {
               setRating1(newValue)
          }} />
         
          <Button className={classes.buttonsuggest} variant="outlined" size="small" color="primary" >
            <Typography variant="body2" color="primary" > {translate("button.Suggest an edit")}</Typography>

          </Button>
          <Typography variant="body2" className={classes.typography1}>Rate <span style={{ fontWeight: "bold" }}  >Translate  Text</span>  Quality</Typography>
          <StyledRating 
            size="large"
           value={rating2} 
            onChange={(event, newValue) => {
             setRating2(newValue)
          } } />
          <Button variant="outlined" size="small" color="primary" className={classes.buttonsuggest}>
            <Typography variant="body2" color="primary">  {translate("button.Suggest an edit")}</Typography>

          </Button>
          <Typography variant="body2" className={classes.typography1} >Rate  <span style={{ fontWeight: "bold" }}>Translated Speech</span> Quality </Typography>
          <StyledRating  
            size="large"
           value={rating3} 
            onChange={(event, newValue) => {
               setRating3(newValue)
          }} />
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
        </Grid>
      </Popover>
      </MuiThemeProvider>
      </div>
    </div>
  );
}
export default withStyles(FeedbackStyle)(SimpleDialogDemo);
