import React ,{useState}from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ThumbUpOutlinedIcon from '@material-ui/icons/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@material-ui/icons/ThumbDownAltOutlined';
import { makeStyles ,withStyles} from '@material-ui/core/styles';
import Rating from '@material-ui/lab/Rating';
import Popover from '@material-ui/core/Popover';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';


const useStyles = makeStyles((theme) => ({
  typography: {
    padding: theme.spacing(1),
  },
  MuiRatingLabel:{
    paddingLeft:"19px"
  },
  feedbackbutton:{
    backgroundColor:"#FD7F23",
     position:"absolute" ,
     height:"28px",
     '&:hover':{
      backgroundColor:"#FD7F23"

     }
  },
  feedbackIcon:{
    width:"12px",
    heigth:"10px",
    color:"white",
    paddingLeft:"2px"
    
  },
  feedbackTitle:{
    fontSize:"10px" ,
    color:"white",
    paddingLeft:"3px"
  },
  feedbacktypography:{
    fontSize:"12px", 
    borderBottom:"1px solid #ECE7E6  ", 
    width:"225px", 
    margin:"auto"
  },
  submitbutton:{
    width:"70px",
    margin:"10px 0px 0px 140px"
  },
  rating:{
    margin:"auto", 
    padding:"15px 20px 0px 89px"
  },
  
  MuiRatinglabel:{
    paddingRight:"10px"
  }

}));


 function SimpleDialogDemo() {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [anchorE2, setAnchorE2] = React.useState(null);
  const [value, setValue] = React.useState(0);
 

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  const handleClosefeedback = () =>{
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
  console.log(anchorEl)
  return (
    <div>
      <Button  variant="contained" size="small" className={classes.feedbackbutton} onClick={handleClick}>
       <ThumbUpOutlinedIcon  className={classes.feedbackIcon} />
      < ThumbDownAltOutlinedIcon   className={classes.feedbackIcon}  />
       <Typography variant="body2"  className={classes.feedbackTitle} > Feedback</Typography>
       </Button>
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
      
       
        
     
      <Typography className={classes.typography} align="center" style={{marginTop:"15px"}}>Are you satisfied with this <br/> translation?</Typography>
     
      
        <Rating
        className={classes.rating}
          size="large"
          name="simple-controlled"
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
         
        />
     < Typography  className={classes.feedbacktypography} variant="body2"  > very bad   < Typography  variant="body2"  style={{ float: "right",fontSize:"12px"}} > very good </Typography>   </Typography>
   
     <div className={classes.root}>
      
      <Grid container justifyContent="center">
        <Grid item>
         
        <Link
          component="button"
          variant="body2"
          onClick={handleClickfeedback}
>
  detailed Feedback
</Link>

        </Grid>
      </Grid>
      
    </div>

  <Button variant="outlined" size="small"  color="primary"  className={classes.submitbutton}  >
         Submit
    </Button>
    
   <Typography  className={classes.typography} align="center" variant="body2" component="div"  style={{fontSize:"12px"}}>
      Your feedback will be used to help to improve the product</Typography> 
      
   
      </Popover>
      <Popover
       style={{maxwidth:"900px"}}
        id={id1}
        open={open1}
        anchorE2={anchorE2}
        onClose={handleClosefeedback}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: '',
          horizontal: 'right',
        }}
      
      >
        <Typography className={classes.typography}>Please rate your experience...</Typography>
        <Box  p={2}  style={{padding:"10px"}}>
              
              <Typography  variant="body2" style={{marginTop:"10px"}}>Rate Speech to Text Quality</Typography>
              <Rating name="size-medium" />
              <Button style={{float:"right"}} variant="outlined" size="small" color="primary" >
          Suggest an edit
        </Button>
              <Typography  variant="body2" style={{marginTop:"10px"}}>Rate Translate  Text Quality</Typography>
              <Rating name="size-medium" />
              <Button  style={{float:"right"}} variant="outlined" size="small" color="primary" >
          Suggest an edit
        </Button>
              <Typography  variant="body2" style={{marginTop:"10px"}}>Rate Translated Speech Quality Quality</Typography>
              <Rating name="size-medium" />
            </Box>
            <Typography  variant="body2">Add your comments</Typography>
            <TextareaAutosize aria-label="empty textarea" />
            <div>
            <Button variant="outlined" size="small" color="primary" >
          Submit
        </Button>
        </div>
      </Popover>
    </div>
  );
}
export default SimpleDialogDemo;
