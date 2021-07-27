const DataSet = (theme) => ({

    paper: {
        minHeight: '674px',
        boxShadow: "0px 0px 2px #00000029",
        border: "1px solid #0000001F",

    },
    title: {
        marginBottom: '6vh'
    },
    form: {
        marginTop: '1vh',
        width: '90%',
    },
    radioGroup: {
        marginTop: '1vh',

    },

    divStyle: {
        padding: "5% 10% 5% 3.125rem"
    },

    typography: { marginBottom: "14px" },
    marginValue: { marginTop: "18px", color: "#0C0F0FB3" },

    list: {
        marginLeft: "-20px"
    },

    updateBtn: {

        display: 'flex',
        justifyItems: 'center',
        marginLeft: 'auto',
        marginTop: '-4%',
    },
    submitBtn: {
        marginTop: '6vh',
        color: 'white',
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
        padding: '5%'
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
        margin: '1% 2.5% 0 2.5%',
    },
    buttonDiv: {
        margin: '0% 0 1.5rem 0'
    },
    innerButton: {
        margin: '0 0.938rem 0.625rem 0'
    },
    subHeader: {
        marginBottom: '1.313rem'
    },
    autoComplete: {
        marginBottom: '1.938rem'
    },
    clearNSubmit: {
        marginTop: '4rem',
        float: 'right'
    },
    parent: {
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
        justifyContent: "center",
        height: window.innerHeight - 80
    },
    action: { display: "flex", flexDirection: "row" },
    FindInPageIcon: { fontSize: '8rem' },
    searchResult: {
        textAlign: 'center',
        justifyContent: "center",
        flexDirection: "column",
        display: "flex",

        verticalAlign: "middle",
        "@media (max-width:850px)": {
            minWidth: "270px",
            width: "85%",
        },

    },
    reqPaper: {
        // padding:'10%',
        // width:'60%'
        marginTop: '25%'
    },
    alignTypo: {
        textAlign: 'center',
    },
    yourSearchQuery: {
        marginBottom: '2%'
    },
    serReqNoTypo: {
        marginBottom: '7%'
    },
    mySearches: {
        marginTop: '1%',
        width: '60%',
        //  textTransform:'inherit'
    },
    downloadDiv: {
        marginTop: '4%'
    },
    downloadPaper: {
        marginTop: '4%',
        padding: '5% 14% 2% 4%',
        width: '70%',
        minHeight: "3.5rem"
    },
    downloadBtnDiv: {
        margin: '10%',
        marginLeft: "0",
        display: "flex",
        flexDirection: "row",

    },
    searchResultFinal: {
        width: "90%",
        marginTop: "-20%"
    },
    downloadBtn: {

        marginRight: '2%'
    },

    blurOut: {
        zIndex: -2,
        opacity: '0.5'
    },
    leftSection: {
        boxShadow: '4px 0 4px -4px #00000029'
    },
    popupDialog: {
        maxWidth: '46.125rem',
        height: '26.5rem'
    },
    clearAllBtn: {
        float: "right",
        margin: '9px 16px 0px auto',
        padding: '0',
        height: '15px'
    },
    filterContainer: {
        borderBottom: '1px solid #00000029',
        paddingLeft: '18.5px',
        marginTop: '20px'
    },
    filterTypo: {
        marginBottom: '9px'
    },
    applyBtn: {
        float: "right",

        borderRadius: '4px',
        margin: '9px 16px 9px auto',
        width: '80px'
    },
    menuStyle: {
        padding: '0px',
        justifyContent: 'left',
        fontSize: "1.125rem",
        fontWeight: "500 !important",
        "&:hover": {
            backgroundColor: 'white',
        },
        borderBottom: "1px solid rgba(0, 0, 0, 0.42)",
        // borderTop:"3px solid green",
        '& svg': {
            marginLeft: 'auto',
            color: "rgba(0, 0, 0, 0.42)"
        }
    },
    container: {
        margin: '12.75px 0px 18px 15px'
    },
    contriCard: {
        width: '578px',
        minHeight: '100px',
        margin: "10px",
        padding: "0px 10px",
        "@media (max-width:1250px)": {
            width: '500px',

        },
        "@media (max-width:900px)": {
            width: '350px',

        },
        "@media (max-width:700px)": {
            width: '350px',

        },

    },
    typeTypo: {
        marginTop: '6.25px',
        opacity: ".5"
    },
    Typo: {
        marginTop: '6.25px',
    },
    nameTypo: {
        marginTop: '6.25px',
        fontWeight: "500"
    },

    gridHeader: { padding: "13px 24px 14px 24px", display: "flex" },
    gridTypo: { marginTop: "4px" },
    gridData: {
        display: "flex",
        flexWrap: "wrap",
        alignContent: "flex-start",
        marginLeft: "14px", minHeight: "550px"
    },

});

export default DataSet;