import { useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import Header from "./components/common/Header";
import Theme from "./theme/theme-default";
import { withStyles, MuiThemeProvider } from "@material-ui/core/styles";
import GlobalStyles from "./styles/Styles";
import Spinner from "./components/common/Spinner";
import Snackbar from './components/common/Snackbar';
function App(props) {
  const Component = props.component;
  const { classes  } = props;
  const apiStatus = useSelector((state) => state.apiStatus);
  const history = useHistory();
  const renderSpinner = () => {
    if (apiStatus.progress) {
      return <Spinner />;
    }
  };

  const renderError = () => {
    if (apiStatus.unauthrized) {
      
      setTimeout(() => history.push(`${process.env.PUBLIC_URL}/user/login`), 3000)
      
    }
    if (apiStatus.error) {

      return <Snackbar
      open={true}
      handleClose={false}
      anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
      message={apiStatus.message}
      variant={"error"}
      
  />
   
  };}
  return (
    <MuiThemeProvider theme={Theme}>
      <div className={classes.root}>
        <Header className={classes.headerContainer}/>
        <div className={classes.container}>
          {renderSpinner()}
          {renderError()}
          <Component />
        </div>
      </div>
    </MuiThemeProvider>
  );
}
export default withStyles(GlobalStyles(Theme), { withTheme: true })(App);
