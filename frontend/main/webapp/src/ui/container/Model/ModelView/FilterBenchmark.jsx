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
            style={{ margin: "21px", height: "290.75px", width: "175px" }}
          >
            <Typography variant="h6" className={classes.filterTypo}>
              {translate("label.domain")}
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
            onClick={clearAll}
            variant="outlined"
            style={{
              width: "100px",
              marginRight: "10px",
              marginLeft: "34.2px",
              borderRadius: "20px",
            }}
            disabled={selectedFilter.length ? false : true}
          >
            {" "}
            {translate("button.cleaAll")}
          </Button>
          <Button
            color="primary"
            variant="contained"
            style={{ width: "80px", borderRadius: "20px" }}
            disabled={selectedFilter.length ? false : true}
            onClick={apply}
          >
            {" "}
            {translate("button.apply")}
          </Button>
        </div>
      </Popover>
    </div>
  );
};

export default withStyles(DataSet)(FilterBenchmark);
