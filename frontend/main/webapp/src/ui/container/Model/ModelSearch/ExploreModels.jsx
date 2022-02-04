import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../styles/Dataset";
import NewSearchModel from "./NewSearchModel";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { Paper } from "@material-ui/core";

const ExploreModels = (props) => {
  const { classes } = props; 
  return (
    <div className={classes.parentPaper}>
      <Paper elevation={0} className={classes.mainPaper}>
        {/* {!aunthenticate() && <Typography variant="h3">Explore Models</Typography>} */}
        <NewSearchModel />
      </Paper>
    </div>
  );
};

export default withStyles(DatasetStyle)(ExploreModels);
