


const drawerWidth = 240;
//let logo = require('../../../assets/logo.png')

const DataSet = (theme) => ({
    divStyle:{
        margin: "0% 23rem 3% 17rem", paddingTop: "7vh"
    },
    root: {
        padding: 0,
        margin: '0rem 2rem 0rem 2rem',
        height: window.innerHeight,
        overflow: 'auto'
    },
    paper: {
        margin: "1% 3% 3% 10%",
        width: '70%',
        padding: '5%',
    },
    title: {
        marginBottom: '3vh'
    },
    form: {
        marginTop: '1vh',
        width: '100%',
    },
    radioGroup: {
        marginTop: '1vh',
        paddingRight: '2vw'
    },

    updateBtn: {
        backgroundColor: "white",
        border: '1px solid black',
        display: 'flex',
        justifyItems: 'center',
        marginLeft: 'auto',
        marginTop: '-4%',
    },
    submitBtn: {
        marginTop: '6vh',
        color: 'white',
        fontSize: '1rem'
    },
    breadcrum: {
        marginTop: '1vh',
        marginLeft: '10%'
    },
    link: {
        color: "rgb(158 84 147)",
        marginRight: "10px",
        cursor: "pointer"
    },
    span: {
        color: "green"
    },
    searchDataset: {
        maxHeight: '1.875rem',
    },
    submittedOn: {
        display: 'block',
        marginTop: '-0.3rem'
    },
    updateDataset: {
        padding: '2rem',
        width: '21rem',
        height: 'auto',
        overflow: 'auto'
    },
    datasetName: {
        borderBottom: '1px solid #e0e1e0',
        borderTop: '1px solid #e0e1e0'
    },
    popOver: {
        marginTop: '0.3rem'
    },
    footerButtons: {
        display: "flex", 
        justifyContent: 'flex-end', 
        width: "100%",
        padding:'.6rem 1rem',
        boxSizing: 'border-box',
        border: "1px solid rgb(224 224 224)",
    background: "white",
    marginTop:"-3px"
      },

      headerButtons:{
        display: "flex", 
        justifyContent: 'flex-end', 
        width: "100%",
        marginBottom:'.6rem',
        boxSizing: 'border-box',
        
    
      },
      buttonStyle:{
          marginLeft:"0.7rem"
      },
      iconStyle:{marginRight: '.5rem'}
});

export default DataSet;