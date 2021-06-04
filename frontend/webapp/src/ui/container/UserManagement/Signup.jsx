import {
  Grid,
  Typography,
  withStyles,
  Button,
  TextField,
  Link,
  InputLabel,
  OutlinedInput,
  IconButton,
  InputAdornment,
  FormControl,
  Checkbox,
  FormControlLabel,
} from "@material-ui/core";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import {  useHistory } from "react-router-dom";

const SignUp = (props) => {
  const [values, setValues] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleCheckChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.checked });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const HandleSubmit = () => {
    console.log(values);
  };
  const { classes } = props;

  return (
    <Grid container className={classes.loginGrid}>
      <Typography className={classes.body2}>Sign up to ULCA</Typography>
      <Typography className={classes.subText}>
        Please enter the details to create an account with ULCA
      </Typography>
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("name")}
        id="outlined-required"
        value={values.name}
        label="Name"
        variant="outlined"
      />
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("email")}
        id="outlined-required"
        value={values.email}
        label="Email address"
        variant="outlined"
      />
      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">Password</InputLabel>
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
      <Typography className={classes.passwordHint}>
        At least one uppercase letter, one lowercase letter and one number
      </Typography>
      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">
          Confirm password
        </InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={"password"}
          value={values.confirm}
          onChange={handleChange("password")}
          labelWidth={70}
        />
      </FormControl>

      <Typography className={classes.passwordHint}>
        Both password must match.
      </Typography>

      <div className={classes.privatePolicy}>
        <FormControlLabel
          control={
            <Checkbox
              checked={values.checked}
              onChange={handleCheckChange("checked")}
              inputProps={{ "aria-label": "primary checkbox" }}
            />
          }
          label="I agree to the"
        />
        <Typography className={classes.policy}>
          <Link id="newaccount" className={classes.link} href="#">
            {" "}
            Privacy policy
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
        Sign up
      </Button>
      <div className={classes.createLogin}>
        <Typography className={classes.width}>
          Already have an account ?{" "}
        </Typography>
        <Typography>
          <Link id="newaccount" className={classes.link} href="#"
            onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`)}}>
            {" "}
            Sign in
          </Link>
        </Typography>
      </div>
    </Grid>
  );
};

export default withStyles(LoginStyles)(SignUp);
