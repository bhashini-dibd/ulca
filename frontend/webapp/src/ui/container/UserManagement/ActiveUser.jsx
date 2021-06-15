import { Paper, Typography, withStyles, Grid } from "@material-ui/core";
import { MarkunreadMailbox } from "@material-ui/icons";
import DraftsIcon from '@material-ui/icons/Drafts';
import LoginStyles from "../../styles/Login";
import MailboxIcon from "../../../assets/mailbox.svg";
import { Link } from "react-router-dom";
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
const ActiveUser = (props) => {
  const { classes } = props;
  return (
    <MuiThemeProvider>
    <Paper style={{margin:'78px 324px 0 324px', height: '198px'}} >
      <Grid container spacing={1}>
      <Grid item xs={12} sm={12} md={3} lg={3} xl={3}>
      <span className={classes.width}>
            <img
              src={MailboxIcon}
              alt=""
            //   width='80%'
            //   height="80%"
            />
          </span>
          </Grid>
          <Grid  item xs={12} sm={12} md={9} lg={9} xl={9}>
        <Typography variant="h6">
          Congratulations
        </Typography>
        <Typography >
          Your email address has been verified. <Link>Proceed to login.</Link>
        </Typography>
        </Grid>
      </Grid>
    </Paper>
    </MuiThemeProvider>
  );
};

export default withStyles(LoginStyles)(ActiveUser);
