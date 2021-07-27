import {
  Grid,
  Typography,
  withStyles,
  Button,
  TextField,
  Link,
  CircularProgress,
} from "@material-ui/core";

import React, { useState } from "react";
import LoginStyles from "../../styles/Login";
import { useHistory } from "react-router-dom";
import ForgotPasswordAPI from "../../../redux/actions/api/UserManagement/ForgotPassword";
import Snackbar from '../../components/common/Snackbar';

const ForgotPassword = (props) => {
  const [values, setValues] = useState({
    email: "",
  });
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: '',
    variant: 'success'
  })
  const history = useHistory();
  const [error, setError] = useState({
    email: false,
  });
  const [loading, setLoading] = useState(false);
  const [buttonDisable, setButtonDisable]=useState(false);

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
    setError({ ...error, [prop]: false });

  };

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false })
  }
  const ValidateEmail = (mail) => {
    if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail)) {
      return (true)
    }
    else {
      return false;
    }
  }
  const HandleSubmitValidate = () => {
    if (!ValidateEmail(values.email)) {
      setError({ ...error, email: true })
    }
    else {
      HandleSubmit()
      setLoading(true);
    }


  }
  const HandleSubmit = () => {
    const obj = new ForgotPasswordAPI(values.email)
    // setSnackbarInfo({
    //   ...snackbar,
    //   open: true,
    //   message: "Sending forgot password link...",
    //   variant: 'info'
    // })
    fetch(obj.apiEndPoint(), {
      method: 'POST',
      headers: obj.getHeaders().headers,
      body: JSON.stringify(obj.getBody())
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
          setButtonDisable(true);
          setTimeout(() => history.push(`${process.env.PUBLIC_URL}/user/login`), 3000)
        }
        else {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.message,
            variant: 'error'
          })
          Promise.reject(rsp_data.message)
        }
      })
      .catch(error => {
        setLoading(false)
      })
  };
  const { classes } = props;

  return (
    <>
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
          error={error.email ? true : false}
          value={values.email}
          helperText={error.email ? "Invalid email" : ""}
          label="Email address"
          variant="outlined"
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
            HandleSubmitValidate();
          }}
          disabled={loading ? loading:buttonDisable}>
          {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
          Send Link
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

export default withStyles(LoginStyles)(ForgotPassword);
