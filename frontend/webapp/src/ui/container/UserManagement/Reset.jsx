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
  Input
} from "@material-ui/core";

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

  const [xUserId, setXUserId] = useState("")

  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: '',
    variant: 'success'
  })

  useEffect(() => {
    console.log(props.token, props.email)
    const apiObj = new TokenSearch(props.token)
    fetch(apiObj.apiEndPoint(), {
      method: 'POST',
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    })
      .then(async response => {
        let rsp_data = await response.json()
        console.log(rsp_data)
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
    }
  }

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
  const HandleSubmit = () => {
    const apiObj = new ResetPasswordAPI(props.email, values.confirmPassword, xUserId)
    fetch(apiObj.apiEndPoint(), {
      method: 'POST',
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody())
    })
    .then(async response=>{
      let rsp_data = await response.json()
      console.log(rsp_data)
    })
  };
  const { classes } = props;

  return (
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
      {error.password && <FormHelperText error={true}>Length should be 8 chanracters and at least one uppercase letter, one lowercase letter and one number </FormHelperText>}
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
      >
        Save new password
      </Button>
    </Grid>
  );
};

export default withStyles(LoginStyles)(ResetPassword);
