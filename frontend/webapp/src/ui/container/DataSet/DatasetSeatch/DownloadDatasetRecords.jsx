import { Paper, Typography, Grid, Button } from "@material-ui/core"
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import { SaveAlt } from '@material-ui/icons';

const DownloadDatasetRecords = (props) => {
    const renderSentence = () => {
        if (props.datasetType === 'ASR / TTS Dataset' || props.datasetType === 'ASR Unlabeled Dataset')
            return "#Hours"
        else if (props.datasetType === 'OCR Dataset')
            return "#Images"
        else
            return "#Sentences"
    }
    const { classes } = props
    return (
        <div className={classes.searchResultFinal}>
            <Typography variant="h5">{`Search result for the ${props.datasetType} records`}</Typography>
            <Paper className={classes.downloadPaper}>
                <Grid container>
                    {props.sentencePair ?
                        <Grid item xs={7} sm={7} md={7} lg={7} xl={7}>
                            <Typography variant="h5">{`${props.sentencePair}`}</Typography>
                            <Typography variant="body1">{renderSentence()}</Typography>
                        </Grid> :
                        <Grid item xs={7} sm={7} md={7} lg={7} xl={7}>
                            <Typography variant="h5">No records found.</Typography>
                        </Grid>}
                    {/* <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
                        <Divider orientation="vertical" />
                    </Grid> */}
                    {/* <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                        <Typography variant="h5">{`${props.datasetsContributed}`}</Typography>
                        <Typography variant="subtitle1">#Datasets Contributed</Typography>
                    </Grid> */}
                </Grid>
                {props.sentencePair ? <div className={classes.downloadBtnDiv}>
                    <Button href={props.urls.downloadSample} target="_self" size="medium" variant="outlined" ><SaveAlt className={classes.iconStyle} />Download Sample</Button>
                    <Button href={props.urls.downloadAll} target="_self" size="medium" variant="outlined" className={classes.buttonStyle} ><SaveAlt className={classes.iconStyle} />Download All</Button>
                    {/* <Button href={props.urls.downloadSample} target="_self" className={classes.downloadBtn} variant="contained" color="primary"> */}
                    {/* <GetAppOutlinedIcon /> Download Sample */}
                    {/* </Button> */}
                    {/* <Button href={props.urls.downloadAll} target="_self" className={classes.downloadBtn} variant="contained" color="primary"> */}
                    {/* <GetAppOutlinedIcon /> Download All */}
                    {/* </Button> */}
                </div> : " "}
            </Paper>
        </div>

    )
}
export default withStyles(DatasetStyle)(DownloadDatasetRecords);