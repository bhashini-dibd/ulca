import { Grid, Button, Popover, withStyles, Divider } from "@material-ui/core";
import { translate } from "../../../assets/localisation";
import DataSet from "../../styles/Dataset";

const Filter = (props) => {
  const {
    children,
    open,
    id,
    anchorEl,
    handleClose,
    classes,
    handleApply,
    handleClear,    
    isDisabled
  } = props;

    return (
    <Popover
      id={id}
      open={open}
      anchorEl={anchorEl}
      onClose={handleClose}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "right",
      }}
      transformOrigin={{
        vertical: "top",
        horizontal: "right",
      }}
    >
      <Grid container className={classes.filterContainer}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          {children}
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Divider orientation="vertical" />
        </Grid>
      </Grid>
      <Button
        color="primary"
        size="small"
        variant="contained"
        className={classes.applyBtn}
        onClick={handleApply}
        disabled={isDisabled}
      >
        {translate("button.apply")}
      </Button>
      <Button
        variant="outlined"
        className={classes.clrBtn}
        onClick={handleClear}
        disabled={isDisabled}
      >
        {translate("button.clearAll")}
      </Button>
    </Popover>
  );
};

export default withStyles(DataSet)(Filter);
