const FileUploadStyles = theme => ({
    container: {
      margin:'1.5rem 20% 3rem 15.5%',
      overflow: 'auto'
    },
    
    breadcrum: {
      marginBottom: '1.5rem',
      
  },
  
    cursor:{
      cursor:"pointer"
    },
    title: {
      margin: '0 0 3vh 1vh'
  },
  paper: {
    padding: '3%',
},

    langPairButtons: {
      display: "flex", 
      justifyContent: 'flex-end', 
      width: "100%",
      padding:'.6rem 1rem',
      boxSizing: 'border-box'
    },
    cardHeader: {
      display: 'flex',
      alignItems: 'center',
      borderBottom: '1px solid #EEEEF0',
      padding: '.6rem 1rem',
      width: '100%',
      boxSizing: 'border-box'
    },
    backButton: {
      boxShadow: 'none',
      backgroundColor: '#F0F1F3',
      color: '#0C0F0F',
      padding: '.5rem .625rem',
      marginRight: '.5rem'
    },
    seperator: {
      width: '1px',
      height: '2rem',
      backgroundColor: '#DADCE0',
      margin: '0 1rem',
      fontSize: '.75rem'
    },
    cardHeaderContainer: {
      display: "flex", 
      flexDirection: "row",
      minHeight:"2.3rem"
    }

  });
  
  
  export default FileUploadStyles;
  