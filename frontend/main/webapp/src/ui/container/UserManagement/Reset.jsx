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
  FormHelperText,
  CircularProgress,
} from "@material-ui/core";
import Snackbar from '../../components/common/Snackbar';
import React, { useState, useEffect } from "react";
import LoginStyles from "../../styles/Login";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import { useHistory } from "react-router-dom";
import TokenSearch from "../../../redux/actions/api/UserManagement/TokenSearch";
import ResetPasswordAPI from "../../../redux/actions/api/UserManagement/ResetPassword";

const ResetPassword = (props) => {
  const [values, setValues] = useState({
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState({
    password: false,
    confirmPassword: false,
  });

  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: '',
    variant: 'success'
  })
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    const apiObj = new TokenSearch(props.public)
    fetch(apiObj.apiEndPoint(), {
      method: 'POST',
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    })
      .then(async response => {
        let rsp_data = await response.json()
        if (!response.ok) {
          setSnackbarInfo({ ...snackbar, open: true, message: rsp_data.message, variant: 'error' })
          setTimeout(() => {
            history.push(`${process.env.PUBLIC_URL}/user/login`)
          }, 3000)
        }
      })
  }, [])

  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    setError({ ...error, [prop]: false });
  };
  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };
  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false })
  }
  const HandleSubmitValidate = () => {
    if (!(values.password.length > 7)) {
      setError({ ...error, password: true })
    }
    else if (values.password !== values.confirmPassword) {
      setError({ ...error, confirmPassword: true })
    }
    else {
      HandleSubmit()
      setLoading(true);
    }
  }

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
  const HandleSubmit = () => {
    const apiObj = new ResetPasswordAPI(props.email, values.confirmPassword, props.public, props.private)
    fetch(apiObj.apiEndPoint(), {
      method: 'POST',
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    })
      .then(async response => {
        let rsp_data = await response.json()
        setLoading(false)
        if (response.ok) {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.message,
            variant: 'success'
          })
          setTimeout(() => history.push(`${process.env.PUBLIC_URL}/user/login`), 3000)
        }
        else {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.Errors[0].message,
            variant: 'error'
          })
          Promise.reject(rsp_data.Errors[0].message)
        }
      })
      .catch(error => {
        setLoading(false)
      })
  };
  const handlePrevent = (e) => {
    e.preventDefault()
  }

  const { classes } = props;

  return (
    <>
      <Grid container className={classes.loginGrid}>
        <Typography variant="h4">Reset Password</Typography>
        <Typography variant="body2" className={classes.subTypo}>
          Please choose your new password
        </Typography>
        <FormControl className={classes.textField} variant="outlined">
          <InputLabel error={error.password} htmlFor="outlined-adornment-password">
            Enter new password
          </InputLabel>
          <OutlinedInput
            id="outlined-adornment-password"
            type={values.showPassword ? "text" : "password"}
            value={values.password}
            error={error.password}
            onChange={handleChange("password")}
            onCut={handlePrevent}
            onCopy={handlePrevent}
            onPaste={handlePrevent}
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
            labelWidth={140}
          />
        </FormControl>
        {error.password && <FormHelperText error={true}>
          Minimum length is 8 characters with combination of uppercase, lowercase, number and a special character</FormHelperText>}
        <FormControl className={classes.textField} variant="outlined">
          <InputLabel error={error.confirmPassword} htmlFor="outlined-adornment-password">
            Confirm new password
          </InputLabel>
          <OutlinedInput
            id="outlined-adornment-password"
            type={"password"}
            error={error.confirmPassword}
            value={values.confirmPassword}
            onChange={handleChange("confirmPassword")}
            onCut={handlePrevent}
            onCopy={handlePrevent}
            onPaste={handlePrevent}
            labelWidth={160}
          />
        </FormControl>
        {error.confirmPassword && <FormHelperText error={true}>Both password must match.</FormHelperText>}
        <div className={classes.loginLink}>
          <Typography>
            <Link id="newaccount" className={classes.link} href="#"
              onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`) }}>
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
            HandleSubmitValidate();
          }}
          disabled={loading}>
          {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
          Save new password
        </Button>
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

export default withStyles(LoginStyles)(ResetPassword);
