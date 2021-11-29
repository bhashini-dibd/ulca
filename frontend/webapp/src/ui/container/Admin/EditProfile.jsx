import { Grid, TextField, Typography } from "@material-ui/core";
import MultiSelect from "../../components/common/Autocomplete";
import { roles, org } from "../../../configs/AdminConfig";
import SingleAutoComplete from "../../components/common/SingleAutoComplete";

const EditProfile = (props) => {
  const { userName, fullName, role, orgValue } = props.info;
  const { handleRoleChange, handleOrgChange, handleTextFieldChange } = props;
  return (
    <Grid container spacing={2} style={{ width: "auto" }}>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        <Typography variant="h6">Edit Profile Details</Typography>
      </Grid>
      <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
        <TextField
          fullWidth
          variant="outlined"
          label="User ID"
          color="primary"
          value={userName}
          disabled
        />
      </Grid>
      <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
        <TextField
          fullWidth
          variant="outlined"
          label="Full Name"
          color="primary"
          onChange={(e) => handleTextFieldChange(e.target.value, "fullName")}
          value={fullName}
        />
      </Grid>
      <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
        <MultiSelect
          id="roles"
          label="Roles"
          filter="roles"
          value={role}
          handleOnChange={handleRoleChange}
          options={roles}
        />
      </Grid>
      <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
        <SingleAutoComplete
          id="org"
          placeholder="Organisation"
          value={orgValue}
          labels={org}
          handleChange={handleOrgChange}
        />
      </Grid>
    </Grid>
  );
};

export default EditProfile;
