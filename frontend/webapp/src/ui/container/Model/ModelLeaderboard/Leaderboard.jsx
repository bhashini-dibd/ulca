import { Grid } from "@material-ui/core";
import SearchResult from "../../DataSet/DatasetSeatch/SearchResult";
import LeaderboardList from "./LeaderboardList";

const Leaderboard = () => {
  return (
    <Grid container spacing={3}>
      <Grid item xs={12} sm={5} md={3} lg={3} xl={3}></Grid>
      <Grid item xs={12} sm={7} md={9} lg={9} xl={9}>
        <LeaderboardList />
      </Grid>
    </Grid>
  );
};

export default Leaderboard;
