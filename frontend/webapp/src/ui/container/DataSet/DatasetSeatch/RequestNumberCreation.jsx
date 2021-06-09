import { Button, Card, Container, Grid, Typography } from "@material-ui/core"
import { Paper } from "@material-ui/core"
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import { withStyles } from '@material-ui/core';
import DatasetStyle from '../../../styles/Dataset';
import { withRouter, useHistory } from "react-router-dom";

const RequestNumberCreation = (props) => {
    const { classes } = props
    const { reqno } = props
    const history = useHistory();
    return (
        <Container className={classes.reqPaper}>
            <Grid container>
                <Grid className={classes.iconSub} item xs={12} sm={12} md={12} lg={12} xl={12}>
                    <span className={classes.thumbsUpIconSpan}><ThumbUpIcon className={classes.thumbsUpIcon} /></span>
                </Grid>
                <Grid item xs={12} sm={12} md={10} lg={10} xl={10} className={classes.alignTypo}>
                    <Typography className={classes.yourSearchQuery} color="primary" variant="h6">Your search query has been submitted.</Typography>
                    <Typography className={classes.serReqNoTypo} variant="h5">Your Service Request Number is {reqno}</Typography>
                    <Typography color="textSecondary" variant="subtitle1">The result will be displayed once it is ready.</Typography>
                    <Button className={classes.mySearches} color="primary" variant="outlined"
                        onClick={() => history.push(`${process.env.PUBLIC_URL}/my-searches`)}
                    >
                        Back to My Searches
                        </Button>
                </Grid>
            </Grid>
        </Container>

    )
}

export default withRouter(withStyles(DatasetStyle)(RequestNumberCreation))