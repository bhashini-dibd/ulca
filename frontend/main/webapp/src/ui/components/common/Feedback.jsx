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
    <ThumbUpOutlinedIcon />
   < ThumbDownAltOutlinedIcon/>
  </DialogTitle>
    <Typography  align="center" variant="h4"  style={{color:"#3498DB"}}>  Suggest an edit </Typography>
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
