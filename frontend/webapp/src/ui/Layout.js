import { useSelector } from "react-redux";
import Header from "./components/common/Header";
import Theme from "./theme/theme-default";
import { withStyles, MuiThemeProvider } from "@material-ui/core/styles";
import GlobalStyles from "./styles/Styles";
import CircleLoader from "react-spinners/CircleLoader";

function App(props) {
  const Component = props.component;
  const { title, userRoles, classes } = props;
  debugger
  return (
    <MuiThemeProvider theme={Theme}>
      <div className={classes.root}>
        
        <Header title={title} />
        <div className={classes.container}>
          {/* <CircleLoader color={"green"} loading={true} size={150} /> */}
          <Component />
        </div>
      </div>
    </MuiThemeProvider>
  );
}
export default withStyles(GlobalStyles(Theme), { withTheme: true })(App);
