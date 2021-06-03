import { Button, Grid, Typography } from "@material-ui/core"
import { Paper } from "@material-ui/core"
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import { withStyles } from '@material-ui/core';
import DatasetStyle from '../../../styles/Dataset';
import BreadCrum from '../../../components/common/Breadcrum';
import { withRouter, useHistory } from "react-router-dom";
import urlconfig from '../../../../configs/internalurlmapping';


const DatasetSubmission = (props) => {
    const { classes } = props
    const { reqno } = props.match.params
    const history = useHistory();
    const { dataset } = urlconfig
    return (
        <div className={classes.divStyle}>
            <div className={classes.breadcrum}>
                <BreadCrum links={[dataset]} activeLink="Submit Dataset" />
            </div>
            <Paper className={classes.paper}>
                <Grid container className={classes.dataSubmissionGrid}>
                    <Grid className={classes.submissionIcon} item xs={12} sm={12} md={2} lg={2} xl={2}>
                        <span className={classes.thumbsUpIconSpan}><ThumbUpIcon className={classes.thumbsUpIcon} /></span>
                    </Grid>
                    <Grid item xs={12} sm={12} md={10} lg={10} xl={10}>
                        <Typography className={classes.thankYouTypo} color="primary" variant="h5">Thank you, for the dataset submission.</Typography>
                        <Typography className={classes.reqNoTypo} variant="h4">Your Service Request Number is {reqno}</Typography>
                        <Typography color="textSecondary" variant="h6">We are currently fetching the dataset from the URL you provided. This process may take some time.</Typography>
                        <Typography color="textSecondary" variant="h6">Note: The submitted dataset will go through a series of validation steps before it gets published.</Typography>
                        <Button className={classes.myContriBtn} color="primary" variant="outlined"
                            onClick={() => history.push(`${process.env.PUBLIC_URL}/my-contribution`)}
                        >
                            Go to My Contribution
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </div>
    )
}

export default withRouter(withStyles(DatasetStyle)(DatasetSubmission))