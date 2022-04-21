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
import { translate } from "../../../../assets/localisation";

const FilterBenchmark = (props) => {
  const {
    classes,
    clearAll,
    filter,
    selectedFilter,
    id,
    open,
    anchorEl,
    handleClose,
    apply,
  } = props;

  const isChecked = (type, property) => {
    return selectedFilter[property].indexOf(type) > -1 ? true : false;
  };

  const isDisabled = () => {
    const keys = Object.keys(selectedFilter);
    for (let i = 0; i < keys.length; i++) {
      if (selectedFilter[keys[i]].length > 0) {
        return false;
      }
    }
    return true;
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
        <Grid container justify-content=" space-between"  className={classes.filterContainer}>
          <Grid  item xs={6} sm={6} md={3} lg={3} xl={3} >
            <Typography variant="h6" className={classes.filterTypo}>
              Task
            </Typography>
            <FormGroup>
              {filter.task.map((type) => {
                return (
                  <Grid>
                  <FormControlLabel
                    control={
                      <Checkbox
                      
                        checked={isChecked(type, "task")}
                        name={type}
                        color="primary"
                        onChange={() => props.handleCheckboxClick(type, "task")}
                      />
                    }
                    label={type}
                  />
                  </Grid>
                );
              })}
            </FormGroup>
          </Grid>
          <Grid item xs={6} sm={6} md={3} lg={3} xl={3} >
            <Typography variant="h6" className={classes.filterTypo}>
              Domain
            </Typography>
            <FormGroup>
              {filter.domain.map((type) => {
                return (
                  <Grid>
                  <FormControlLabel 
                    control={
                      <Checkbox
                        checked={isChecked(type, "domain")}
                        name={type}
                        color="primary"
                        onChange={() =>
                          props.handleCheckboxClick(type, "domain")
                        }
                      />
                    }
                    label={type}
                  />
                  </Grid>
                );
              })}
            </FormGroup>
          </Grid>
          <Grid item xs={6} sm={6} md={3} lg={3} xl={3}>
            <Typography variant="h6" className={classes.filterTypo}>
              License
            </Typography>
            <FormGroup>
              {filter.license.map((type) => {
                return (
                  <Grid>
                  <FormControlLabel
                    control={
                      <Checkbox
                        checked={isChecked(type, "license")}
                        name={type}
                        color="primary"
                        onChange={() =>
                          props.handleCheckboxClick(type, "license")
                        }
                      />
                    }
                    label={type }
                  />
                  </Grid>
                );
              })}
            </FormGroup>
          </Grid>
          <Grid item xs={6} sm={6} md={3} lg={3} xl={3}>
            <Typography variant="h6" className={classes.filterTypo}>
              Status
            </Typography>
            <FormGroup>
              {filter.status.map((type) => {
                return (
                  <Grid>
                  <FormControlLabel
                    control={
                      <Checkbox
                        checked={isChecked(type, "status")}
                        name={type}
                        color="primary"
                        onChange={() =>
                          props.handleCheckboxClick(type, "status")
                        }
                      />
                    }
                    label={type}
                  />
                  </Grid>
                );
              })}
            </FormGroup>
          </Grid>
        </Grid>
       
        <Button
          color="primary"
          variant="contained"
          className={classes.applyBtn}
          // disabled={isDisabled()}
          onClick={() => apply(selectedFilter)}
        >
          {" "}
          {translate("button.apply")}
        </Button>
      
        <Button
          onClick={clearAll}
          variant="outlined"
          className={classes.clrBtn}
          disabled={isDisabled()}
        >
          {" "}
          {translate("button.clearAll")}
        </Button>
      </Popover>
    </div>
  );
};

export default withStyles(DataSet)(FilterBenchmark);
