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
  FormHelperText,
  FormControl,
  CircularProgress,
} from "@material-ui/core";

import React, { useState,useEffect } from "react";
import LoginStyles from "../../styles/Login";
import LoginApi from "../../../redux/actions/api/UserManagement/Login";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import { useHistory } from "react-router-dom";
import Snackbar from '../../components/common/Snackbar';
import { useDispatch } from 'react-redux';
import Logout from "../../../redux/actions/api/UserManagement/Logout"
// import {useReducer, useSelector} from "react-redux";
// import { LoginSocialFacebook, LoginSocialGithub } from "reactjs-social-login";
// import { LinkedIn } from "react-linkedin-login-oauth2";


const Login = (props) => {
  const dispatch = useDispatch();
  const [values, setValues] = useState({
    email: "",
    password: "",
    checked: false,
  });
  const [error, setError] = useState({
    email: false,
    password: false,

  });



  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: '',
    variant: 'success'
  })


  useEffect(() => {
   dispatch(Logout())
}, []);



  const history = useHistory();
  const [loading, setLoading] = useState(false);
  const handleChange = (prop) => (event) => {
    setError({ ...error, password: false, email: false });
    setValues({ ...values, [prop]: event.target.value });
  };

  // const handleCheckChange = (prop) => (event) => {
  //   setValues({ ...values, [prop]: event.target.checked });
  // };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  // const ValidateEmail = (mail) => {
  //   if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail)) {
  //     return (true)
  //   }
  //   else {
  //     return false;
  //   }
  // }

  // const ValidatePassword = (password) => {
  //   if (password.length > 7) {
  //     return (true)
  //   }
  //   else {
  //     return false;
  //   }
  // }


  const handleSubmit = async () => {

    let apiObj = new LoginApi(values)
    var rsp_data = []
    fetch(apiObj.apiEndPoint(), {
      method: 'post',
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers
    }).then(async response => {
      rsp_data = await response.json();
      setLoading(false)
      if (!response.ok) {

        return Promise.reject('');
      } else {
        localStorage.setItem(`userInfo`, JSON.stringify(rsp_data.data.userKeys));
        localStorage.setItem(`userDetails`, JSON.stringify(rsp_data.data.userDetails));

        history.push(`${process.env.PUBLIC_URL}${props.location.from ? props.location.from : '/dashboard'}`)
      }
    }).catch((error) => {
      setLoading(false)

      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: rsp_data.message ? rsp_data.message : "Invalid email / password",
        variant: 'error'
      })
    });

  }

  // const responseGoogle = (data) => {
  //   alert(JSON.stringify(data));
  // };

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false })
  }

  const HandleSubmitCheck = () => {
    if (!values.email.trim() || !values.password.trim()) {

      setError({ ...error, email: !values.email.trim() ? true : false, password: !values.password.trim() ? true : false });
    }

    else {
      handleSubmit();
      setLoading(true)
    }

  };
  const { classes } = props;
  // const REDIRECT_URI = "http://localhost:3000";

  return (
    <>
      <Grid container className={classes.loginGrid}>
        <Typography variant="h4">Sign in to ULCA</Typography>
        <form className={classes.root} autoComplete="off">
          <TextField
            className={classes.textField}
            required
            onChange={handleChange("email")}
            onKeyPress={(e) => e.key === 'Enter' && HandleSubmitCheck()}
            id="outlined-required"
            value={values.email}
            error={error.email}
            label="Email address"
            helperText={error.email ? "Enter an email" : " "}
            variant="outlined"
          />
          <FormControl className={classes.fullWidth} variant="outlined">
            <InputLabel error={error.password} htmlFor="outlined-adornment-password">Password * </InputLabel>

            <OutlinedInput
              id="outlined-adornment-password"
              type={values.showPassword ? "text" : "password"}
              value={values.password}
              required
              error={error.password}
              helperText={error.password ? "Enter a password" : ""}
              onChange={handleChange("password")}
              onKeyPress={(e) => e.key === 'Enter' && HandleSubmitCheck()}
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
              labelWidth={100}
            />
            {error.password && <FormHelperText error={true}>Incorrect password</FormHelperText>}
          </FormControl>
          <div className={classes.forgotPassword}>
            {/* <FormControlLabel
              control={
                <Checkbox
                  checked={values.checked}
                  onChange={handleCheckChange("checked")}
                  inputProps={{ "aria-label": "primary checkbox" }}
                />
              }
              label="Keep me logged in "
            /> */}
            <Typography className={classes.forgoLink}>
              <Link
                id="newaccount"
                className={classes.link}
                href="#"
                onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/forgot-password`) }}
              >
                {" "}
                Forgot Password?
              </Link>
            </Typography>
          </div>

          <Button
            color="primary"
            size="large"
            variant="contained" aria-label="edit" className={classes.fullWidth} onClick={() => {
              HandleSubmitCheck();
            }}
            disabled={loading}>
            {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
            Sign In
          </Button>
          {/* <Button
        
        color="primary"
        size = "large"
        className={classes.fullWidth}
        onClick={() => {
          HandleSubmitCheck();
        }}
      >
        Sign in
      </Button> */}
        </form>
        {/* <div className={classes.line}>
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
                
                src={GmailIcon}
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
      /> */}
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
        {/* <LoginSocialGithub
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
              src={GithubIcon}
              alt=""
              width="25px"
              height="25px"
              style={{ maginLeft: "20px" }}
            />
          </span>
          <span>Continue with Github</span>
        </Button>
      </LoginSocialGithub> */}
        <div className={classes.createLogin}>
          <Typography variant={"body2"} className={classes.width}>New to ULCA ?</Typography>
          <Typography variant={"body2"} >
            <Link id="newaccount" className={classes.link} href="#"
              onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/register`) }}>
              {" "}
              Create an account
            </Link>
          </Typography>
        </div>

      </Grid>
      {snackbar.open &&
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
          message={snackbar.message}
          variant={snackbar.variant}
        />}
    </>
  );
};

export default withStyles(LoginStyles)(Login);
