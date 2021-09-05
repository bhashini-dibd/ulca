import { Button, Grid, Typography } from "@material-ui/core"
import { Paper } from "@material-ui/core"
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import { withStyles } from '@material-ui/core';
import DatasetStyle from '../../styles/Dataset';
import BreadCrum from '../common/Breadcrum';
import { withRouter, useHistory } from "react-router-dom";
import urlconfig from '../../../configs/internalurlmapping';
import thumbsUpIcon from "../../../assets/OrangeThumbsUp.svg"
import { useDispatch } from "react-redux";
import getMenuOption from "../../../redux/actions/api/Common/getMenuOption";

const SubmissionStatus = (props) => {
    const { classes } = props
    const { reqno, type } = props.match.params
    const history = useHistory();
    const dispatch = useDispatch();
    return (
        <div>
            <Paper className={classes.submitPaper}>
                <img className={classes.yourSearchQuery}
                    src={thumbsUpIcon}
                    alt="Success Icon"
                />
                <Typography className={classes.thankYouTypo} variant="body1">Thank you, for the {type} submission.</Typography>
                <Typography className={classes.reqNoTypo} variant="h5">Your Service Request Number is {reqno}</Typography>
                <Typography color="textSecondary" variant="body1">We are currently fetching the {type} from the URL you provided. This process may take some time.</Typography>
                <Typography className={classes.noteTypo} color="textSecondary" variant="body1">Note: The submitted {type} will go through a series of validation steps before it gets published.</Typography>
                <Button className={classes.myContriBtn} color="primary" variant="contained" size="large"
                    onClick={() => {
                        dispatch(getMenuOption(0))
                        history.push(`${process.env.PUBLIC_URL}/${type}/my-contribution/true`)
                    }}
                >
                    Go to My Contribution
                </Button>
            </Paper>
        </div>
    )
}

export default withRouter(withStyles(DatasetStyle)(SubmissionStatus))