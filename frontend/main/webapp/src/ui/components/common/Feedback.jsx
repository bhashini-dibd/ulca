import React ,{useState}from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ThumbUpOutlinedIcon from '@material-ui/icons/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@material-ui/icons/ThumbDownAltOutlined';
import { makeStyles ,withStyles} from '@material-ui/core/styles';
import Rating from '@material-ui/lab/Rating';
import Popover from '@material-ui/core/Popover';





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
  const [value, setValue] = React.useState(0);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

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
   
  <Button variant="outlined" size="small"  color="primary"  className={classes.submitbutton}  >
         Submit
    </Button>
   <Typography  className={classes.typography} align="center" variant="body2" component="div"  style={{fontSize:"12px"}}>
      Your feedback will be used to help to improve the product</Typography> 
      
   
      </Popover>
    </div>
  );
}
export default SimpleDialogDemo;
