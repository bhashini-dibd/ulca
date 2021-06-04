import {
  Grid,
  Typography,
  withStyles,
  Button,
  TextField,
  Link,
} from "@material-ui/core";

import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import {  useHistory } from "react-router-dom";

const ForgotPassword = (props) => {
  const [values, setValues] = React.useState({
    email: "",
  });
  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };
  const HandleSubmit = () => {
    console.log(values);
  };
  const { classes } = props;

  return (
    <Grid container className={classes.loginGrid}>
      <Typography className={classes.body2}>Forgot password?</Typography>
      <Typography className={classes.subText}>
        Enter you email address and we will send a link to reset your password.
      </Typography>
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("email")}
        id="outlined-required"
        value={values.email}
        label="Email address"
        variant="outlined"
      />

      <div className={classes.loginLink}>
        <Typography>
          <Link id="newaccount" className={classes.link} href="#" onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`)}}>
            {" "}
            Back to Sign in
          </Link>
        </Typography>
      </div>

      <Button
        variant="contained"
        color="primary"
        className={classes.fullWidth}
        onClick={() => {
          HandleSubmit();
        }}
      >
        Send Link
      </Button>
    </Grid>
  );
};

export default withStyles(LoginStyles)(ForgotPassword);
