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
// import Dashboard from "./ui/container/Dashboard/Dashboard";
import DatasetSubmission from './ui/container/DataSet/UploadDataset/DatasetSubmission';
import authenticateUser from './configs/authenticate';
import MySearches from "./ui/container/DataSet/DatasetSeatch/MySearches";
import SearchAndDownloadRecords from "./ui/container/DataSet/DatasetSeatch/SearchDownloadRecords";
import ActivateUser from "./ui/container/UserManagement/ActivateUser";
import ActiveUser from "./ui/container/UserManagement/ActiveUser"
import ReadymadeDataset from "./ui/container/DataSet/ReadymadeDataset.jsx/ReadymadeDataset";

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

          <Route
            path={`${process.env.PUBLIC_URL}/activate/:email/:userId/:time?`}

            component={ActivateUser}

          />

          <Route exact path={`${process.env.PUBLIC_URL}/dashboard`} component={Dashboard} />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/dataset-status/:status/:name/:id`}
            title={"Submit Dataset"}
            component={DetailedStatus}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/my-contribution/:added?`}
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
            path={`${process.env.PUBLIC_URL}/readymade-dataset`}
            userRoles={[""]}
            component={ReadymadeDataset}
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


          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/search-and-download-rec/:params/:srno`}
            userRoles={[""]}
            component={SearchAndDownloadRecords}
            authenticate={authenticateUser}
            currentMenu="search-and-download-rec"
            dontShowHeader={false}
          />

          <Route
            path={`${process.env.PUBLIC_URL}/active-user`}

            component={ActiveUser}

          />

        </Switch>
      </div>
    </Router>
  );
}
