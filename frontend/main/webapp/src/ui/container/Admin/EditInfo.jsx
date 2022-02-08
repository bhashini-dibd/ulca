import { Grid, Button } from "@material-ui/core";
import { translate } from "../../../assets/localisation";

const EditProfile = (props) => {
  const { children, handleClose, handleSubmit } = props;
  return (
    <Grid container style={{ padding: "0 5%", width: "100%" }}>
      <Grid
        item
        xs={12}
        sm={12}
        md={12}
        lg={12}
        xl={12}
        style={{ minHeight: "15rem", marginTop: "2%" }}
      >
        {children}
      </Grid>
      <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
        <Grid container spacing={1} style={{ position: "relative" }}>
          <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
            <Button
              onClick={handleClose}
              size="large"
              variant="contained"
              color="default"
              fullWidth
            >
              {translate("button.cancel")}
            </Button>
          </Grid>
          <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
            <Button
              onClick={handleSubmit}
              size="large"
              variant="contained"
              color="primary"
              fullWidth
            >
              {translate("button.submit")}
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default EditProfile;
