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
  FormHelperText,
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
  const [error, setError] = useState({
    name: false,
    email: false,
    password: false,
    confirmPassword: false,
  });
  const history = useHistory();

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    setError({ ...error, [prop]: false });
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

  const handleSubmit = () =>{

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
      debugger
      if(!(/^[A-Za-z ]+$/.test(values.name))){
        setError({...error, name:true})
      }
      else if(!ValidateEmail(values.email)){
      setError({...error, email:true})
      }
      else if(!(values.password.length>8)){
      setError({...error, password:true})
      }
      else if(values.password !== values.confirmPassword){
      setError({...error, confirmPassword:true})
      }
      else{
        handleSubmit()
      }
  
  
    }
  
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
        error  = {error.name ? true : false}
        value={values.name}
        label="Name"
        helperText = {error.name ? "Name is not proper" : ""}
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
      {error.password && <FormHelperText error={true}>Length should be 8 chanracters and at least one uppercase letter, one lowercase letter and one number </FormHelperText>}

      <FormControl className={classes.fullWidth} variant="outlined">
        <InputLabel htmlFor="outlined-adornment-password">
          Confirm Password *
        </InputLabel>
        <OutlinedInput
          id="outlined-adornment-password"
          type={"password"}
          error  = {error.confirmPassword ? true :false }
          value={values.confirmPassword}
          onChange={handleChange("confirmPassword")}
          labelWidth={140}
        />
      </FormControl>
      {error.confirmPassword && <FormHelperText error={true}>Both password must match.</FormHelperText>}

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
          HandleSubmitValidate();
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
