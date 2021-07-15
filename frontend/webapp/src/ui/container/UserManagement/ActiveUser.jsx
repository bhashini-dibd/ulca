import { Paper, Typography, withStyles, Grid } from "@material-ui/core";
import LoginStyles from "../../styles/Login";
import MailboxIcon from "../../../assets/mailbox.svg";
import { Link, useHistory } from "react-router-dom";
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
            Congratulations
          </Typography>
          <Typography >
            Your email address has been verified. <Link href="#"
              onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`) }}>Proceed to login.</Link>
          </Typography>
        </Grid>
      </Grid>
    </Paper>
  );
};

export default withStyles(LoginStyles)(ActiveUser);
