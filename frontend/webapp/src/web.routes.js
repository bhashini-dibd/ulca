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
import ContributionList from "./ui/container/DataSet/ContributionList";
import DetailedStatus from "./ui/container/DataSet/DetailedStatus";
import DatasetSubmission from './ui/container/DataSet/UploadDataset/DatasetSubmission';

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
  debugger
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
            path={`${process.env.PUBLIC_URL}/dataset-status/:id`}
            title={"Submit Dataset"}
            component={DetailedStatus}
            authenticate={true}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/my-contribution`}
            title={"My Contribution"}

            component={ContributionList}
            authenticate={true}
            currentMenu="contribution-list"
            dontShowHeader={true}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/submit-dataset/upload`}
            title={"Submit Dataset"}
            userRoles={[""]}
            component={SubmitDataset}
            authenticate={true}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />

          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/submit-dataset/submission/:reqno`}
            title={"Dataset Submission"}
            userRoles={[""]}
            component={DatasetSubmission}
            authenticate={true}
            currentMenu="dataset-submission"
            dontShowHeader={false}
          />

        </Switch>
      </div>
    </Router>
  );
}
