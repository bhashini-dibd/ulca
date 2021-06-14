import { Paper, Typography, Grid, Divider, Button, OutlinedInput, InputAdornment, IconButton } from "@material-ui/core"
import GetAppOutlinedIcon from '@material-ui/icons/GetAppOutlined';
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';

const DownloadDatasetRecords = (props) => {
    const { classes } = props
    return (
        <div className={classes.searchResultFinal}>
            <Typography variant="h5">{`Search result for the ${props.datasetType} Dataset records`}</Typography>
            <Paper className={classes.downloadPaper}>
                <Grid container>
                    {props.sentencePair ?
                        <Grid item xs={7} sm={7} md={7} lg={7} xl={7}>
                            <Typography variant="h5">{`${props.sentencePair}`}</Typography>
                            <Typography variant="subtitle1">#Sentence Pair</Typography>
                        </Grid> :
                        <Grid item xs={7} sm={7} md={7} lg={7} xl={7}>
                            <Typography variant="h5">Result not found.</Typography>
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
                    <Button href={props.urls.downloadSample} target="_self" className={classes.downloadBtn} variant="contained" color="primary">
                        <GetAppOutlinedIcon /> Download Sample
                    </Button>
                    <Button href={props.urls.downloadAll} target="_self" className={classes.downloadBtn} variant="contained" color="primary">
                        <GetAppOutlinedIcon /> Download All
                    </Button>
                </div> : " "}
            </Paper>
        </div>

    )
}
export default withStyles(DatasetStyle)(DownloadDatasetRecords);