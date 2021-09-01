import React from "react";
import DataSet from "../../../styles/Dataset";
import {
  withStyles,
  Button,
  Divider,
  Grid,
  Typography,
  Popover,
  FormGroup,
  Checkbox,
  FormControlLabel,
} from "@material-ui/core";

const FilterBenchmark = (props) => {
  const {
    classes,
    handleClearAll,
    filter,
    selectedFilter,
    id,
    open,
    anchorEl,
    handleClose,
  } = props;

  const isChecked = (type) => {
    return selectedFilter.indexOf(type) > -1;
  };

  return (
    <div>
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
        <Grid
          container
          style={{
            maxWidth: "238.53px",
            maxHeight: "290.22px",
            overflow: "auto",
          }}
        >
          <Grid
            item
            xs={12}
            sm={12}
            md={12}
            lg={12}
            xl={12}
            style={{ margin: "21px", height: "209.75px", width: "175px" }}
          >
            <Typography variant="h6" className={classes.filterTypo}>
              Domain
            </Typography>
            <FormGroup>
              {filter.map((type) => {
                return (
                  <FormControlLabel
                    control={
                      <Checkbox
                        checked={isChecked(type)}
                        name={type}
                        color="primary"
                        onChange={props.handleCheckboxClick}
                      />
                    }
                    label={type}
                  />
                );
              })}
            </FormGroup>
          </Grid>
        </Grid>
        <Divider />
        <div
          style={{
            display: "flex",
            alignItems: "center",
            margin: "9px 0",
            width: "238.53px",
          }}
        >
          <Button
            onClick={handleClearAll}
            variant="outlined"
            style={{
              width: "100px",
              marginRight: "10px",
              marginLeft: "34.2px",
              borderRadius: "20px",
            }}
          >
            {" "}
            Clear All
          </Button>
          <Button
            color="primary"
            variant="contained"
            style={{ width: "80px", borderRadius: "20px" }}
          >
            {" "}
            Apply
          </Button>
        </div>
      </Popover>
    </div>
  );
};

export default withStyles(DataSet)(FilterBenchmark);
