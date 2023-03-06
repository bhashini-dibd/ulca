import DataTable from "../../components/common/DataTable";
import { withStyles, Button, Typography, Grid, Box } from "@material-ui/core";
import Search from "../../components/Datasets&Model/Search";
import { translate } from "../../../assets/localisation";
import FilterListIcon from "@material-ui/icons/FilterList";
import { Cached } from "@material-ui/icons";
import DataSet from "../../styles/Dataset";

const MyProfile = (props) => {
  const {classes} = props;
  const fetchHeaderButton = () => {
    return (
      <Grid container spacing={1} className={classes.Gridroot}>
        <Grid item xs={7} sm={8} md={8} lg={8} xl={8}>
          <Search value=""  />
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
          <Button
            color={"default"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            // onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
          </Button>
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
          <Button
            color={"default"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            // onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
            {translate("button.filter")}
          </Button>
        </Grid>
        <Grid item xs={2} sm={2} md={2} lg={2} xl={2} className={classes.filterGrid}>
          <Button
            color={"primary"}
            size="medium"
            variant="outlined"
            className={classes.ButtonRefresh}
            // onClick={() => MyContributionListApi()}
          >
            <Cached className={classes.iconStyle} />
            {translate("button.refresh")}
          </Button>
        </Grid>
        <Grid item xs={3} sm={2} md={2} lg={2} xl={2} className={classes.filterGridMobile}>
          <Button
            color={"primary"}
            size="small"
            variant="outlined"
            className={classes.ButtonRefreshMobile}
            // onClick={() => MyContributionListApi()}
          >
           
            <Cached className={classes.iconStyle} />
          </Button>
        </Grid>
      </Grid>
    );
  };

  const columns = [
    {
      name: "appName",
      label: "App Name",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "ulcaApiKey",
      label: "Ulca Api Key",
      options: {
        filter: false,
        sort: false,
      },
    },
    {
      name: "action",
      label: "Action",
      options: {
        filter: false,
        sort: false,
      },
    },
  ];
  const options = {
    textLabels: {
      body: {
        noMatch: "No records ",
      },
      toolbar: {
        search: "Search",
        viewColumns: "View Column",
      },
      pagination: {
        rowsPerPage: "Rows per page",
      },
    },
    customToolbar: fetchHeaderButton,
    print: false,
    viewColumns: false,
    rowsPerPageOptions: false,
    selectableRows: "none",
    fixedHeader: false,
    download: false,
    search: false,
    filter: false,
    expandableRowsHeader: false,
    expandableRowsOnClick: true,
    expandableRows: false,
  };
  return (
    <div>
      <DataTable
        data={[
          {
            appName: "ULCA",
            ulcaApiKey: "/ulca/apikey",
          },
        ]}
        columns={columns}
        options={options}
      />
    </div>
  );
};

export default withStyles(DataSet)(MyProfile);