import { Grid, MuiThemeProvider, withStyles } from "@material-ui/core";
import Theme from "../../theme/theme-default";
import AppInfo from "./AppInfo";
import Login from "./Login";
import Dashboard from '../Dashboard/ChartRender';
import SignUp from "./Signup";
import { useParams } from "react-router-dom";
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./Reset";
import LoginStyles from "../../styles/Login";

const UserManagement = (props) => {
  const { classes } = props;

  const param = useParams();
  const renderPage = () => {
    switch (param && param.page) {
      case "login":
        return <Login location={props.location} />;
      case "forgot-password":
        return <ForgotPassword />;
      case "reset-password":
        return <ResetPassword email={param.email} public={param.public} private={param.private}/>;

      default:
        return <Dashboard />;
    }
  };
  return (
    <MuiThemeProvider theme={Theme}>
      <Grid container>
        <AppInfo />
        <Grid item xs={12} sm={8} md={9} lg={9} className={classes.parent}>
          {renderPage()}
          {/* <Footer /> */}
        </Grid>
      </Grid>


    </MuiThemeProvider>
  );
};

export default withStyles(LoginStyles)(UserManagement);
