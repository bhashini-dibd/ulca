import { useSelector } from "react-redux";
import Header from "./components/Header";
import Theme from "./theme/theme-default";
import { withStyles, MuiThemeProvider } from "@material-ui/core/styles";
import GlobalStyles from "./styles/Styles";
import CircleLoader from "react-spinners/CircleLoader";
import Spinner from "./components/Spinner";

function App(props) {
  const Component = props.component;
  const { title, classes } = props;
  const apiStatus = useSelector((state) => state.apiStatus);

  const renderSpinner = () => {
    if (apiStatus.progress) {
      return <Spinner />;
    }
  };
  return (
    <MuiThemeProvider theme={Theme}>
      <div className={classes.root}>
        <Header title={title} />
        <div className={classes.container}>
          {renderSpinner()}
          <Component />
        </div>
      </div>
    </MuiThemeProvider>
  );
}
export default withStyles(GlobalStyles(Theme), { withTheme: true })(App);
