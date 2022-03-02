import React ,{useState}from 'react';
import Button from '@material-ui/core/Button';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import Typography from '@material-ui/core/Typography';
import ThumbUpOutlinedIcon from '@material-ui/icons/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@material-ui/icons/ThumbDownAltOutlined';
import ThumbsUpDownOutlinedIcon from '@material-ui/icons/ThumbsUpDownOutlined';





  function SimpleDialog(props) {
  const { onClose, selectedValue, open } = props;

  const handleClose = () => {
    onClose(selectedValue);
  };

  

  return (
    <Dialog onClose={handleClose} open={open}>
     
      <Typography align="center" style={{marginTop:"15px"}}>Are you satisfied with this <br/> translation?</Typography>
      
   
   <DialogTitle variant="outlined"  align="center" >
    <ThumbUpOutlinedIcon  style={{marginRight:"30px",border:"1px solid #BBBEC1 " ,padding:"10px",borderRadius:"25px"}} />
   < ThumbDownAltOutlinedIcon style={{border:"1px solid #BBBEC1 " ,padding:"10px",borderRadius:"25px"}}/>
  </DialogTitle>
    <Typography  align="center" variant="subtitle1"  style={{color:"#283BD1"}}>  Suggest an edit </Typography>
   <Typography align="center" variant="body" component="div"  style={{marginTop:"20px"}}>
      Your feedback will be used to help </Typography><Typography variant="body" style={{marginLeft:"70px",marginBottom:"30px"}}> to improve the product</Typography> 
      
    </Dialog>
  );
}



export default function SimpleDialogDemo() {
  const [open, setOpen] = useState(false);
 

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
   
  };

  return (
    <div>
       <Button  onClick={handleClickOpen}>
       <ThumbsUpDownOutlinedIcon color="action" />
      </Button>
      <SimpleDialog
        open={open}
        onClose={handleClose}
      />
    </div>
  );
}
