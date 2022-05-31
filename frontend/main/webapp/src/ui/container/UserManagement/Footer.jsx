import { Typography, withStyles } from "@material-ui/core";
import LoginStyles from "../../styles/Login";
const Footer = (props) => {
  const { classes } = props;
  return (
    <div className={classes.footer}>
      <div className={classes.typoDiv}>
        <Typography className={classes.typoFooter} style={{ opacity: "0.5" }}>
          By continuing, you agree to ULCA{" "}
        </Typography>
        <Typography >
          Terms of Service, Privacy Policy.
        </Typography>
      </div>
      <Typography className={classes.typoFooter}>
        Copyright © 2021-2022 ULCA. All rights reserved.
      </Typography>
    </div>
  );
};

export default withStyles(LoginStyles)(Footer);
