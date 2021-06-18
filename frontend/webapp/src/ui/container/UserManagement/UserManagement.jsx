import { Grid, MuiThemeProvider, withStyles } from "@material-ui/core";
import Theme from "../../theme/theme-default";
import AppInfo from "./AppInfo";
import Login from "./Login";
import Dashboard from '../Dashboard/ChartRender';
import SignUp from "./Signup";
import { useParams, useHistory } from "react-router-dom";
import ForgotPassword from "./ForgotPassword";
import ResetPassword from "./Reset";
import Footer from "./Footer";
import LoginStyles from "../../styles/Login";

const UserManagement = (props) => {
  const { classes } = props;
  const param = useParams();
  let history = useHistory();
  const renderPage = () => {
    switch (param && param.page) {
      case "register":
        return <SignUp />;
      case "login":
        return <Login />;
      case "forgot-password":
        return <ForgotPassword />;
      case "reset-password":
        return <ResetPassword />;

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
