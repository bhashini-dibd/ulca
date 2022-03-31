import {
  Grid,
  Hidden,
  Typography,
  withStyles,
} from "@material-ui/core";
import LoginStyles from "../../styles/Login";
import {  useHistory } from "react-router-dom";
import { translate } from "../../../assets/localisation";

function AppInfo(props) {
  const { classes } = props;
  const history = useHistory();
  return (
    
      <Grid item xs={12} sm={4} md={3} lg={3} color = {"primary"}className={classes.appInfo}>
        
        <Typography className={classes.title} variant={"h2"} onClick={() => { history.push(`${process.env.PUBLIC_URL}/dashboard`)}}>ULCA</Typography>
        <Hidden only="xs">
        <Typography variant={"h3"} className={classes.subTitle}>
          {translate("label.ulcaFullForm")}
        </Typography>
       
        <Typography variant={"body1"} className={classes.body}>
        {translate("label.ulcaInfo")}
        </Typography>
        </Hidden>
      </Grid>
     
  );
}

export default withStyles(LoginStyles)(AppInfo);
