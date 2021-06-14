import { Typography, withStyles } from "@material-ui/core";
import LoginStyles from "../../styles/Login";
import DraftsIcon from '@material-ui/icons/Drafts';
const ActiveUser = (props) => {
  const { classes } = props;
  return (
      <div className={classes.activeUser}>
        <Typography variant="h3" className={classes.typoFooter} style={{ opacity: "0.5" }}>
          Congratulations
        </Typography>
        <Typography className={classes.typoBold}>
          Your email address has been verified. Proceed to Login.
        </Typography>
      </div>
  );
};

export default withStyles(LoginStyles)(ActiveUser);
