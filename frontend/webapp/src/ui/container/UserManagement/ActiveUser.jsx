import { Paper, Typography, withStyles, Grid } from "@material-ui/core";
import LoginStyles from "../../styles/Login";
import MailboxIcon from "../../../assets/mailbox.svg";
import { Link, useHistory } from "react-router-dom";
import { translate } from "../../../assets/localisation";
const ActiveUser = (props) => {
  const { classes } = props;
  const history = useHistory();
  return (
    <Paper className={classes.ActiveUserPaper} >
      <Grid container>
        <Grid item xs={12} sm={4} md={3} lg={2} xl={2}>
          <img
            src={MailboxIcon}
            alt="Mail Box"
          />
        </Grid>
        <Grid className={classes.congrats} item xs={12} sm={8}>
          <Typography variant="h6">
            {translate("label.congratulations")}
          </Typography>
          <Typography >
            {translate("label.emailVerifiedMsg")}<Link href="#"
              onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`) }}>{translate("link.proceedToLogin")}</Link>
          </Typography>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default withStyles(LoginStyles)(ActiveUser);
