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

import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import { LoginSocialFacebook, LoginSocialGithub } from "reactjs-social-login";
import GoogleLogin from "react-google-login";
import { LinkedIn } from "react-linkedin-login-oauth2";
import Divider from "@material-ui/core/Divider";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import {useReducer, useSelector} from "react-redux";
import {  useHistory } from "react-router-dom";

const Login = (props) => {
  const [values, setValues] = React.useState({
    email: "",
    password: "",
    checked: false,
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

  const responseGoogle = (data) => {
    alert(JSON.stringify(data));
  };

  const HandleSubmit = () => {
    console.log(values);
  };
  const { classes } = props;
  const REDIRECT_URI = "http://localhost:3000";

  return (
    <Grid container className={classes.loginGrid}>
      <Typography className={classes.body2}>Login to ULCA</Typography>

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
      <div className={classes.forgotPassword}>
        <FormControlLabel
          control={
            <Checkbox
              checked={values.checked}
              onChange={handleCheckChange("checked")}
              inputProps={{ "aria-label": "primary checkbox" }}
            />
          }
          label="Keep me logged in "
        />
        <Typography className={classes.forgoLink}>
          <Link
            id="newaccount"
            className={classes.link}
            href="#"
            onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/forgot-password`)}}
          >
            {" "}
            Forgot Password?
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
        Login
      </Button>
      <div className={classes.line}>
        <Divider className={classes.dividerFullWidth} />{" "}
        <Typography className={classes.divider}>Or</Typography>
        <Divider className={classes.dividerFullWidth} />
      </div>
      <GoogleLogin
        render={(renderProps) => (
          <Button
            variant="contained"
            className={classes.gmailStyle}
            onClick={renderProps.onClick}
          >
            <span style={{ marginRight: "30px" }}>
              <img
                
                src="gmail.png"
                alt=""
                width="25px"
                height="25px"
                style={{ maginLeft: "20px" }}
              />
            </span>
            Continue with Google
          </Button>
        )}
        clientId="1042231143652-m25ln3odich8tfi08ql2rlbj0h820g9v.apps.googleusercontent.com"
        buttonText="Continue with Google"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
        cookiePolicy={"single_host_origin"}
      />
      {/* <LinkedIn
          clientId="7859u4ovb44uiu"
          onFailure={responseGoogle}
          onSuccess={responseGoogle}
          redirectUri="http://localhost:3000/"
          renderElement={({ onClick, disabled }) => (
            <Button
              variant="contained"
              color="primary"
              className={classes.linkedStyle}
            >
              <span className={classes.width}>
                <img
                  src="linkedin.svg"
                  alt =''
                  width="20px"
                  height="20px"
                  style={{ maginLeft: "20px" }}
                />
              </span>
              Continue with LinkedIn
            </Button>
          )}
        />
        <LoginSocialFacebook
          appId={"813819182874104"}
          className={classes.labelWidth}
          onResolve={({ data }) => {
            alert(JSON.stringify(data));
          }}
          onReject={(err) => alert(err)}
        >
          <Button
            variant="contained"
            color="primary"
            className={classes.fullWidth}
          >
            <span className={classes.width}>
              <img
                src="facebook.svg"
                alt =''
                width="20px"
                height="20px"
                style={{ maginLeft: "20px" }}
              />
            </span>
            Continue with Facebook
          </Button>
        </LoginSocialFacebook> */}
      <LoginSocialGithub
        client_id={"fc66013ca8d2c0bcf178"}
        className={classes.labelWidth}
        redirect={REDIRECT_URI}
        onResolve={({ provider, data }) => {
          alert(JSON.stringify(data));
        }}
        onReject={(err) => alert(err)}
      >
        <Button
          variant="contained"
          color="primary"
          className={classes.githubStyle}
        >
          <span className={classes.width}>
            <img
              src="github.png"
              alt=""
              width="25px"
              height="25px"
              style={{ maginLeft: "20px" }}
            />
          </span>
          <span>Continue with Github</span>
        </Button>
      </LoginSocialGithub>
      <div className={classes.createLogin}>
        <Typography className={classes.width}>New to ULCA?</Typography>
        <Typography>
          <Link id="newaccount" className={classes.link}  href="#"
            onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/register`)}}>
            {" "}
            Create an account
          </Link>
        </Typography>
      </div>
    </Grid>
  );
};

export default withStyles(LoginStyles)(Login);
