import {
  Grid,
  Hidden,
  Typography,
  withStyles,
  Button,
} from "@material-ui/core";
import LoginStyles from "../../styles/Login";

function AppInfo(props) {
  const { classes } = props;
  return (
    <Hidden only="xs">
      <Grid item xs={12} sm={4} md={3} lg={3} color = {"primary"}className={classes.appInfo}>
        <Typography className={classes.title}>ULCA</Typography>
        <Typography className={classes.subTitle}>
          Universal Language Contribution APIs
        </Typography>
        <Typography className={classes.body}>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris .
        </Typography>
        <Button variant="contained" className={classes.expButton}>
          Explore
        </Button>
      </Grid>
    </Hidden>
  );
}

export default withStyles(LoginStyles)(AppInfo);
