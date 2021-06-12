import {
  Grid,
  Typography,
  withStyles,
  Button,
  Link,
  InputLabel,
  OutlinedInput,
  IconButton,
  InputAdornment,
  FormControl,
} from "@material-ui/core";

import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import {  useHistory } from "react-router-dom";

const ResetPassword = (props) => {
  const [values, setValues] = useState({
    password: "",
    confirmPassword: "",
  });
  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };
  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
  const HandleSubmit = () => {
  };
  const { classes } = props;

  return (
    <Grid container className={classes.loginGrid}>
      <Typography className={classes.body2}>Reset Password</Typography>
      <Typography className={classes.subText}>
        Please choose your new password
      </Typography>
      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">
          Enter new password
        </InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={values.showPassword ? "text" : "password"}
          value={values.password}
          onChange={handleChange("password")}
          endAdornment={
            <InputAdornment position="end">
              <IconButton
                aria-label="toggle password visibility"
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}
                edge="end"
              >
                {values.showPassword ? <Visibility /> : <VisibilityOff />}
              </IconButton>
            </InputAdornment>
          }
          labelWidth={70}
        />
      </FormControl>
      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">
          Confirm new password
        </InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={"password"}
          value={values.confirm}
          onChange={handleChange("password")}
          labelWidth={70}
        />
      </FormControl>

      <div className={classes.loginLink}>
        <Typography>
          <Link id="newaccount" className={classes.link}  href="#"
            onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`)}}>
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
        Save new password
      </Button>
    </Grid>
  );
};

export default withStyles(LoginStyles)(ResetPassword);
