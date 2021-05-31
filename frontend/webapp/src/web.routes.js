import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import history from "./web.history";
import Layout from "./ui/Layout";
import Sample from "./ui/container/Sample";
import Login from "./ui/container/UserManagement/UserManagement";
import ContributionList from "./ui/container/DataSet/ContributionList";

const PrivateRoute = ({ component: Component, authenticate, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      authenticate ? (
       
        <Layout component={Component} {...rest} />
      ) : (
        <Redirect to={`${process.env.PUBLIC_URL}/logout`} />
      )
    }
  />
);

export default function App() {
  return (
    <Router history={history} basename="/">
      <div>
        <Switch>
          <Route exact path={`${process.env.PUBLIC_URL}/`} component={Login} />
          <Route
            exact
            path={`${process.env.PUBLIC_URL}/user/:page`}
            component={Login}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/my-contribution`}
            title={"My Contribution"}
           
            component={ContributionList}
            authenticate={true}
            currentMenu="contribution-list"
            dontShowHeader={true}
          />
        </Switch>
      </div>
    </Router>
  );
}
