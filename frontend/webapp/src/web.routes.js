import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import history from "./web.history";
import Layout from "./ui/Layout";
import Login from "./ui/container/UserManagement/UserManagement";
import SubmitDataset from './ui/container/DataSet/UploadDataset/SubmitDataset';
import ContributionList from "./ui/container/DataSet/DatasetView/ContributionList";
import DetailedStatus from "./ui/container/DataSet/DatasetView/DetailedStatus";
import Dashboard from "./ui/container/Dashboard/ChartRender";
import DatasetSubmission from './ui/container/DataSet/UploadDataset/DatasetSubmission';
import authenticateUser from './configs/authenticate';
import MySearches from "./ui/container/DataSet/DatasetSeatch/MySearches";

const PrivateRoute = ({ component: Component, authenticate, token, ...rest }) => {
  return (
    <Route
      {...rest}
      render={(props) =>
        authenticate() ? (
          <Layout component={Component} {...rest} />
        ) : (
          <Redirect to={`${process.env.PUBLIC_URL}/dashboard`} />
        )
      }
    />
  );
}

export default function App() {

  return (
    <Router history={history} basename="/">

      <div>

        <Switch>
          <PrivateRoute exact path={`${process.env.PUBLIC_URL}/`}
            authenticate={authenticateUser}
            component={Dashboard}
          />
          <Route
            exact
            path={`${process.env.PUBLIC_URL}/user/:page`}
            component={Login}
          />
          <Route exact path={`${process.env.PUBLIC_URL}/dashboard`} component={Dashboard} />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/dataset-status/:id`}
            title={"Submit Dataset"}
            component={DetailedStatus}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/my-contribution`}
            title={"My Contribution"}
            authenticate={authenticateUser}
            component={ContributionList}
            currentMenu="contribution-list"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/submit-dataset/upload`}
            title={"Submit Dataset"}
            userRoles={[""]}
            component={SubmitDataset}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/private-dashboard`}
            title={"Submit Dataset"}
            userRoles={[""]}
            component={Dashboard}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/my-searches`}
            userRoles={[""]}
            component={MySearches}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />


          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/submit-dataset/submission/:reqno`}
            title={"Dataset Submission"}
            userRoles={[""]}
            component={DatasetSubmission}
            authenticate={authenticateUser}
            currentMenu="dataset-submission"
            dontShowHeader={false}
          />

        </Switch>
      </div>
    </Router>
  );
}
