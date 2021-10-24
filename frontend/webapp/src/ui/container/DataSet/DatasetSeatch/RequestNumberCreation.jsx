import { Button, Container, Grid, Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/core";
import DatasetStyle from "../../../styles/Dataset";
import { withRouter, useHistory } from "react-router-dom";
<<<<<<< HEAD
import thumbsUpIcon from "../../../../assets/OrangeThumbsUp.svg"
const RequestNumberCreation = (props) => {
    const { classes } = props
    const { reqno } = props
    const history = useHistory();
    return (
        <Container className={classes.searchResult}>
            <Grid container>
                <Grid item xs={12} sm={12} md={10} lg={10} xl={10} >
                    <img className={classes.yourSearchQuery}
                        src={thumbsUpIcon}
                        alt="Success Icon"
                    />
                    <Typography className={classes.yourSearchQuery} variant="body1">Your search query has been submitted.</Typography>
                    <Typography className={classes.serReqNoTypo} variant="h5">Your Service Request Number is {reqno}</Typography>
                    <Typography color="textSecondary" variant="body1">The result will be displayed once it is ready.</Typography>
                    <Button className={classes.mySearches} color="primary" variant="contained" size="large"
                        onClick={() => history.push(`${process.env.PUBLIC_URL}/my-searches`)}
                    >
                        Back to My Searches
                        </Button>
                </Grid>
            </Grid>
        </Container>
=======
import thumbsUpIcon from "../../../../assets/thumbsUp.svg";
import { translate } from "../../../../assets/localisation";
const RequestNumberCreation = (props) => {
  const { classes } = props;
  const { reqno } = props;
  const history = useHistory();
  return (
    <Container className={classes.searchResult}>
      <Grid container>
        <Grid item xs={12} sm={12} md={10} lg={10} xl={10}>
          <img
            className={classes.yourSearchQuery}
            src={thumbsUpIcon}
            alt="Success Icon"
          />
          <Typography
            className={classes.yourSearchQuery}
            color="primary"
            variant="h6"
          >
            {translate("label.searchQueryMsg")}
          </Typography>
          <Typography
            className={classes.serReqNoTypo}
            variant="h5"
          >{`${translate("label.serviceReqNo")} ${reqno}`}</Typography>
          <Typography color="textSecondary" variant="subtitle1">
            {translate("label.searchResult")}
          </Typography>
          <Button
            className={classes.mySearches}
            color="primary"
            variant="outlined"
            onClick={() =>
              history.push(`${process.env.PUBLIC_URL}/my-searches`)
            }
          >
            {translate("button.backToMySearch")}
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
};
>>>>>>> develop

export default withRouter(withStyles(DatasetStyle)(RequestNumberCreation));
