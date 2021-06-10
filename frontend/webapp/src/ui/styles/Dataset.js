const DataSet = (theme) => ({
    divStyle: {
        // margin: "1.5rem 23rem 3% 17.5rem",
        margin: '1.5rem 20% 3rem 15.5%',
        overflow: 'auto'
    },
    root: {
        padding: 0,
        margin: "1.5rem 23rem 3% 17.5rem",
        height: window.innerHeight,
        overflow: 'auto'
    },
    paper: {
        padding: '5%',
    },
    title: {
        marginBottom: '3vh'
    },
    form: {
        marginTop: '1vh',
        width: '90%',
    },
    radioGroup: {
        marginTop: '3vh',
        paddingRight: '2vw'
    },

    // typography:{fontSize:"1.1rem"},

    updateBtn: {
        
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
        marginBottom: '1.5rem',

    },
    link: {
        marginRight: "10px",
        cursor: "pointer",
        display: "flex", flexDirection: "row"
    },
    span: {
        color: "#0C0F0F",
        opacity: "0.5",
        margin: "-3px 0 0 1rem"
    },


    searchDataset: {
        maxHeight: '1.875rem',
        heigth: 'auto'
    },
    submittedOn: {
        display: 'block',
        marginTop: '-0.3rem'
    },
    updateDataset: {
        padding: '2rem',
        width: '21rem'
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
        padding: '.6rem 1rem',
        boxSizing: 'border-box',
        border: "1px solid rgb(224 224 224)",
        background: "white",
        marginTop: "-3px"
    },

    headerButtons: {
        display: "flex",
        justifyContent: 'flex-end',
        width: "100%",
        marginBottom: '.6rem',
        boxSizing: 'border-box',
    },
    buttonStyle: {
        marginLeft: "0.7rem"
    },
    iconStyle: { marginRight: '.5rem' },
    thumbsUpIcon: {
        margin: '24% 0 0 24%',
        fontSize: '3.7rem'
    },

    thumbsUpIconSpan: {
        width: "6.5rem",
        height: '6.5rem',
        backgroundColor: "#e0e1e0",
        borderRadius: "100%",
        display: "block"
    },
    submissionIcon: {
        "@media (max-width:1120px)": {
            display: 'none'
        }
    },

    dataSubmissionGrid: {
        marginTop: '5%'
    },
    thankYouTypo: {
        marginBottom: '1.3%'
    },
    reqNoTypo: {
        marginBottom: '2.5%',
    },
    myContriBtn: {
        marginTop: '8%',
    },
    ButtonRefresh: {
        marginLeft: "auto"
    },
    searchDivStyle: {
        //padding: '0% 4%',
        margin: '1% 10% 0 10%',
    },
    buttonDiv: {
        margin: '0% 0 3% 0'
    },
    innerButton: {
        margin: '0 2% 2% 0'
    },
    subHeader: {
        marginBottom: '3%'
    },
    clearNSubmit: {
        marginTop: '10vh',
        float: 'right'
    },

    action: { display: "flex", flexDirection: "row" },
    FindInPageIcon: { fontSize: '8rem' },
    searchResult:{
        textAlign: 'center',
        marginTop: '45%',
    },
    reqPaper:{
        // padding:'10%',
        // width:'60%'
        marginTop: '25%'
    },
    alignTypo:{
        textAlign: 'center',
    },
    iconSub:{
        marginLeft:'34%',
        marginBottom: '2%'
    },
    yourSearchQuery:{
        marginBottom: '2%'
    },
    serReqNoTypo:{
        marginBottom: '7%'
    },
    mySearches:{
      marginTop:'1%',
      width: '60%'
    },
    downloadDiv:{
        marginTop:'4%'
    },
    downloadPaper:{
        marginTop:'4%',
        padding:'5% 14% 2% 4%',
        width:'70%',
    },
    downloadBtnDiv:{
        marginTop:'10%'
    },
    downloadBtn:{
        fontSize:'0.8rem',
        marginRight:'1%'
    }
});

export default DataSet;