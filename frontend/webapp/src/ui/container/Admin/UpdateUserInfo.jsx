import {
  Grid,
  TextField,
  Typography,
  Button,
  IconButton,
  Tooltip,
} from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import Modal from "../../components/common/Modal";
import AdminPanelStyle from "../../styles/AdminPanel";
import Autocomplete from "../../components/common/Autocomplete";
import CloseIcon from "@material-ui/icons/Close";

const UpdateUserInfo = (props) => {
  //fetching props
  const { open, handleClose, classes } = props;

  // function returning TextField

  const renderTextField = (label) => {
    return (
      <TextField fullWidth variant="outlined" color="primary" label={label} />
    );
  };

  return (
    <Modal open={open} handleClose={handleClose}>
      <Grid container spacing={2}>
        <Grid
          item
          xs={11}
          sm={11}
          md={11}
          lg={11}
          xl={11}
          style={{ display: "flex", alignItems: "center" }}
        >
          <Typography variant="h5">Edit Profile</Typography>
        </Grid>
        <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
          <IconButton onClick={handleClose}>
            <Tooltip placement="right" title="Close">
              <CloseIcon fontSize="small" />
            </Tooltip>
          </IconButton>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {renderTextField("User ID")}
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {renderTextField("Name")}
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {renderTextField("Password")}
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {renderTextField("Confirm Password")}
        </Grid>
        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
          <Autocomplete
            id="org"
            label="Organization"
            options={[{ label: "IIT Madras", value: "iitMadras" }]}
          />
        </Grid>
        <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
          <Autocomplete
            id="role"
            label="Role"
            options={[
              { label: "Contributor", value: "contributor" },
              { label: "Benchmark Dataset Contributor", value: "bmd" },
            ]}
          />
        </Grid>
        <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
          <Button fullWidth size="large" color="default" variant="contained">
            Clear
          </Button>
        </Grid>
        <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
          <Button
            onClick={handleClose}
            fullWidth
            size="large"
            color="primary"
            variant="contained"
          >
            Submit
          </Button>
        </Grid>
      </Grid>
    </Modal>
  );
};

export default withStyles(AdminPanelStyle)(UpdateUserInfo);
