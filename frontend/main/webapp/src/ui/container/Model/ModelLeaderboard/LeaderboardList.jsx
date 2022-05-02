import { MuiThemeProvider, InputBase, Button } from "@material-ui/core";
import MUIDataTable from "mui-datatables";
import createMuiTheme from "../../../styles/Datatable";
import SearchIcon from "@material-ui/icons/Search";
import { withStyles } from "@material-ui/styles";
import DataSet from "../../../styles/Dataset";

const LeaderboardList = (props) => {
  const { classes } = props;
  const columns = [
    {
      name: "rank",
      label: "#Positions",
    },
    {
      name: "modelName",
      label: "Model Name",
    },
    {
      name: "language",
      label: "Language",
    },
    {
      name: "metric",
      label: "Metric",
    },
    {
      name: "score",
      label: "Score",
    },
    {
      name: "publishedOn",
      label: "Published On",
    },
    {
      name: "benchmark",
      label: "Benchmark Dataset",
    },
    {
      name: "",
      label: "",
      customRowRender: (rowdata) => {
        return <Button>Try Model</Button>;
      },
    },
  ];

  const handleSearch = () => {
  };

  const renderToolbar = () => {
    return (
      <div className={classes.search}>
        <div className={classes.searchIcon}>
          <SearchIcon fontSize="small" />
        </div>
        <InputBase
          placeholder="Search..."
          onChange={(e) => handleSearch(e)}
          value={props.searchValue}
          classes={{
            root: classes.inputRoot,
            input: classes.inputInput,
          }}
          inputProps={{ "aria-label": "search" }}
        />
      </div>
    );
  };
  const options = {
    print: false,
    download: false,
    search: false,
    filter: false,
    viewColumns: false,
    selectableRows: false,
    customToolbar: renderToolbar,
  };
  const data = [
    {
      rank: "1",
      modelName: "Model 1",
      language: "English-Hindi",
      metric: "Metric 1",
      score: "Score 1",
      publishedOn: "24/08/2020",
      benchmark: "Benchmark 1",
    },
    {
      rank: "2",
      modelName: "Model 2",
      language: "English-Hindi",
      metric: "Metric 2",
      score: "Score 2",
      publishedOn: "24/08/2020",
      benchmark: "Benchmark 2",
    },
  ];
  return (
    <MuiThemeProvider theme={createMuiTheme}>
      <MUIDataTable
        columns={columns}
        options={options}
        data={data}
        title={`Leaderboard of Translation models based on <Metric Name> score`}
      />
    </MuiThemeProvider>
  );
};

export default withStyles(DataSet)(LeaderboardList);
