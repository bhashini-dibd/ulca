import {
  Grid,
  Popover,
  withStyles,
  Checkbox,
  FormControlLabel,
  FormGroup,
  Typography,
} from "@material-ui/core";
import DataSet from "../../styles/Dataset";

const SelectColumn = (props) => {
  const {
    open,
    id,
    anchorEl,
    handleClose,
    classes,
    columns,
    handleColumnSelection,
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
      <Grid container className={classes.selectColumnContainer}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Typography variant='h6' className={classes.selectColumnHeader}>Select Columns :</Typography>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <FormGroup>
            {columns.map((item) => (
              <FormControlLabel
                control={
                  <Checkbox
                    color="primary"
                    checked={item.checked}
                    disabled={item.disable}
                    onClick={(e) => handleColumnSelection(e)}
                  />
                }
                label={item.label}
                name={item.name}
              />
            ))}
          </FormGroup>
        </Grid>
      </Grid>
    </Popover>
  );
};

export default withStyles(DataSet)(SelectColumn);
