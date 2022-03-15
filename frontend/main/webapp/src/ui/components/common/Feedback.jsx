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
import TextField from '@material-ui/core/TextField';
import FeedbackStyle from "../../styles/Feedback";




 function SimpleDialogDemo(props) {
  const { classes} = props;
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
          style={{color:"#FD7F23",fontSize:"11px",textDecoration: "underline"}}
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
   Your feedback will be used to help to improve the product </Typography> 
      
   
      </Popover>
     
      <Popover
     
        id={id1}
        open={open1}
        anchorE2={anchorE2}
        onClose={handleClosefeedback}
        anchorOrigin={{
          vertical: 'center',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: '',
          horizontal: 'left',
        }}
        // anchorReference="anchorPosition"
        // anchorPosition={{ top: 360, left: 1200 }}
        
      
      >
        <Typography variant="body2" className={classes.typography}>Please rate your experience...</Typography>
        <Box  p={2}  style={{padding:"10px",}}>
              
              <Typography  variant="body2" >Rate  <span  style={{fontWeight:"bold"}}>Speech to Text</span> Quality</Typography>
              <Rating name="size-medium" />
              <Button className={classes.buttonsuggest}  variant="outlined" size="small" color="primary" >
             <Typography variant="body2"  color="primary" >Suggest an edit</Typography>   
          
        </Button>
              <Typography  variant="body2" className={classes.typography1}>Rate <span style={{fontWeight:"bold"}}  >Translate  Text</span>  Quality</Typography>
              <Rating  name="size-medium" />
              <Button   variant="outlined" size="small" color="primary"  className={classes.buttonsuggest}>
                <Typography variant="body2"  color="primary"> Suggest an edit</Typography>
          
        </Button>
              <Typography  variant="body2" className={classes.typography1} >Rate  <span  style={{fontWeight:"bold"}}>Translated Speech</span> Quality </Typography>
              <Rating name="size-medium" />
            </Box>
            <div style={{  borderBottom:"1px solid #ECE7E6 ", width:"240px", margin:"auto"}}></div>
           
            <Typography  variant="body2" style={{marginLeft:"10px"}}>Add your comments</Typography>
            <Grid container justifyContent="center">
              <Grid item>
            <TextareaAutosize
               aria-label="minimum height"
               minRows={4}
               className={classes.textareaAutosize}
               style={{ width: 250  }}
    />
    
            </Grid>
            <Grid container justifyContent="center">
         <Grid items>
            <Button variant="outlined" size="small" color="primary"  className={classes.submitbutton}>
          Submit
        </Button>
        </Grid>
        </Grid>
        </Grid>
      </Popover>
    
    </div>
  );
}
export default  withStyles(FeedbackStyle) (SimpleDialogDemo);
