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
  CircularProgress,
  FormHelperText,
} from "@material-ui/core";
import { Visibility, VisibilityOff } from "@material-ui/icons";
import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import {  useHistory } from "react-router-dom";
import RegisterApi from "../../../redux/actions/api/UserManagement/Register"
import Snackbar from '../../components/common/Snackbar';

const SignUp = (props) => {
  const [values, setValues] = useState({
    firstName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState({
    firstName: false,
    email: false,
    password: false,
    confirmPassword: false,
  });
  const [loading, setLoading] = useState(false);
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: '',
    variant: 'success'
})

  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    setError({ ...error, [prop]: false });
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

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false })
}

  const handleSubmit = () =>{

    let apiObj = new RegisterApi(values)
    var rsp_data =[]
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
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.message ? rsp_data.message : "Invalid email / password",
            variant: 'success'
        })

        setValues({
          firstName: "",
          email: "",
          password: "",
          confirmPassword: "",
        })

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

  const  ValidateEmail = (mail) => 
{
 if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail))
  {
    return (true)
  }
  else{
    return false;
  }  
}

  
    const HandleSubmitValidate = () => {
      if(!(/^[A-Za-z ]+$/.test(values.firstName))){
        setError({...error, firstName:true})
      }
      else if(!ValidateEmail(values.email)){
      setError({...error, email:true})
      }
      else if(!(values.password.length>7)){
      setError({...error, password:true})
      }
      else if(values.password !== values.confirmPassword){
      setError({...error, confirmPassword:true})
      }
      else{
        handleSubmit()
        setLoading(true);
      }
  
  
    }

    const handlePrevent = (e) =>{
      e.preventDefault()
    }
  
  const { classes } = props;

  return (
    <Grid container className={classes.loginGrid}>
      <Typography className={classes.body2} variant={"h6"}>Sign up to ULCA</Typography>
      <Typography variant={"body2"} className={classes.subText}>
        Please enter the details to create an account with ULCA
      </Typography>
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("firstName")}
        id="outlined-required"
        error  = {error.firstName ? true : false}
        value={values.firstName}
        label="Name"
        helperText = {error.firstName ? "Name is not proper" : ""}
        variant="outlined"
      />
      <TextField
        className={classes.textField}
        required
        onChange={handleChange("email")}
        id="outlined-required"
        error  = {error.email ? true : false}
        value={values.email}
        helperText = {error.email ? "Invalid email" : ""}
        label="Email address"
        variant="outlined"
      />
      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel error  = {error.password ? true : false} htmlFor="outlined-adornment-password">Password</InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={values.showPassword ? "text" : "password"}
          value={values.password}
          onChange={handleChange("password")}
          
      onCut={handlePrevent}
      onCopy={handlePrevent}
      onPaste={handlePrevent}
          error  = {error.password ? true :false }
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
      {error.password && <FormHelperText error={true}>
Minimum length is 8 characters with combination of uppercase, lowercase, number and a special character</FormHelperText>}

      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">
          Confirm Password *
        </InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={"password"}
          
      onCut={handlePrevent}
      onCopy={handlePrevent}
      onPaste={handlePrevent}
          error  = {error.confirmPassword ? true :false }
          value={values.confirmPassword}
          onChange={handleChange("confirmPassword")}
          labelWidth={140}
        />
      </FormControl>
      {error.confirmPassword && <FormHelperText error={true}>Both password must match.</FormHelperText>}

      {/* <div className={classes.privatePolicy}>
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
      </div> */}

<Button
                    color="primary"
                    size = "large"
                    variant="contained" aria-label="edit"  className={classes.fullWidth} onClick={() => {
                      HandleSubmitValidate();
                    }}
                    disabled={loading}>
                    {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
                    Sign Up
                </Button>
      <div className={classes.createLogin}>
        <Typography variant="body2" className={classes.width}>
          Already have an account ?{" "}
        </Typography>
        <Typography variant="body2">
          <Link id="newaccount" className={classes.link} href="#"
            onClick={() => { history.push(`${process.env.PUBLIC_URL}/user/login`)}}>
            {" "}
            Sign in
          </Link>
        </Typography>
      </div>
      {snackbar.open &&
      <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
          message={snackbar.message}
          variant={snackbar.variant}
      />}
    </Grid>
  );
};

export default withStyles(LoginStyles)(SignUp);
