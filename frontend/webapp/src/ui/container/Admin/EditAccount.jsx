import {
  Checkbox,
  FormControlLabel,
  Grid,
  Switch,
  TextField,
  Typography,
} from "@material-ui/core";

const EditAccount = (props) => {
  const { checked, handleChange, value, handlePwdChange } = props;
  return (
    <Grid container spacing={2}>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        <Typography variant="h6">Edit Account Details</Typography>
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        <FormControlLabel
          control={
            <Checkbox
              onChange={handleChange}
              checked={checked}
              color="primary"
            />
          }
          label="Change Password"
        />
      </Grid>
      {checked && (
        <Grid container spacing={2}>
          <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
            <TextField
              fullWidth
              variant="outlined"
              label="Password"
              value={value}
              onChange={handlePwdChange}
              color="primary"
            />
          </Grid>
          <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
            <TextField
              fullWidth
              variant="outlined"
              label="Confirm Password"
              value={value}
              onChange={handlePwdChange}
              color="primary"
            />
          </Grid>
        </Grid>
      )}
    </Grid>
  );
};

export default EditAccount;
