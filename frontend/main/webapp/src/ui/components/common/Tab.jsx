import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import { Button, Grid } from "@material-ui/core";
import InputBase from "@material-ui/core/InputBase";
import SearchIcon from "@material-ui/icons/Search";
import { withStyles } from "@material-ui/core/styles";
import FilterListIcon from "@material-ui/icons/FilterList";
import TabStyles from "../../styles/TabStyles";

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

const SimpleTabs = (props) => {
  const { classes } = props;

  return (
    <div>
      <AppBar className={classes.appTab} position="static" color="inherit">
        <Grid container spacing={2}>
          <Grid item xs={12} sm={12} md={7} lg={8} xl={8}>
            <Tabs value={props.value} onChange={props.handleChange}>
              {props.tabs.map((tab, index) => {
                return (
                  <Tab
                    className={classes.tablabel}
                    label={tab.label}
                    {...a11yProps(index)}
                  />
                );
              })}
            </Tabs>
          </Grid>
          <Grid item xs={12} sm={12} md={5} lg={4} xl={4}>
            <Grid container spacing={2} className={classes.gridAlign}>
              <Grid item>
                <div className={classes.search}>
                  <div className={classes.searchIcon}>
                    <SearchIcon fontSize="small" />
                  </div>
                  <InputBase
                    placeholder="Search..."
                    onChange={(e) => props.handleSearch(e)}
                    value={props.searchValue}
                    classes={{
                      root: classes.inputRoot,
                      input: classes.inputInput,
                    }}
                    inputProps={{ "aria-label": "search" }}
                  />
                </div>
              </Grid>
              <Grid item>
                <Button
                  variant="outlined"
                  size="medium"
                  className={classes.filterBtn}
                  onClick={props.handleShowFilter}
                >
                  <FilterListIcon className={classes.iconStyle} />
                  Filter
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </AppBar>
      {props.children}
    </div>
  );
};
export default withStyles(TabStyles)(SimpleTabs);
