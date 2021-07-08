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
import { useHistory } from "react-router-dom";

const ForgotPassword = (props) => {
  const [values, setValues] = useState({
    email: "",
  });
  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };
  const HandleSubmit = () => {
  };
  const { classes } = props;

  return (
    <Grid container className={classes.loginGrid}>
      <Typography variant="h4">Forgot password?</Typography>
      <Typography variant="body2" className={classes.subTypo}>
        Enter you email address and we will send a link to reset your password.
      </Typography>
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("email")}
        id="outlined-required"
        value={values.email}
        label="Email address"
      // variant="outlined"
      />

      <div className={classes.loginLink}>
        <Typography>
          <Link id="newaccount" className={classes.link} href="#" onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`) }}>
            {" "}
            Back to Login
          </Link>
        </Typography>
      </div>

      <Button
        variant="contained"
        color="primary"
        size="large"
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
