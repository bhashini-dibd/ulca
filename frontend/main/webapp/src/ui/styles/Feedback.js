const Feedback = (theme) => ({
   

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
        margin:"10px 0px 0px 130px"
      },
      rating:{
        margin:"auto", 
        padding:"15px 20px 0px 89px"
      },
      buttonsuggest:{
        float:"right", 
        height:"25px",
        marginLeft:"20px"
      },
      typography1:{
        marginTop:"10px"
      },
      textareaAutosize:{
        backgroundColor:"#D3D1D1  ",
          margin:"auto",
          border:" none"
      },
      
     
      MuiButtonlabel:{
        fontSize:"11px"
      }
   
  });
  
  export default Feedback;
  