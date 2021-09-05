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
import SubmitModel from './ui/container/Model/UploadModel/SubmitModel';
import ContributionList from "./ui/container/DataSet/DatasetView/ContributionList";
import ModelContributionList from "./ui/container/Model/ModelView/ContributionList";
import DetailedStatus from "./ui/container/DataSet/DatasetView/DetailedStatus";
import Dashboard from "./ui/container/Dashboard/ChartRender";
// import Dashboard from "./ui/container/Dashboard/Dashboard";
import SubmissionSubmission from './ui/components/Datasets&Model/SubmissionStatus';
import authenticateUser from './configs/authenticate';
import MySearches from "./ui/container/DataSet/DatasetSeatch/MySearches";
import SearchAndDownloadRecords from "./ui/container/DataSet/DatasetSeatch/SearchDownloadRecords";
import ActivateUser from "./ui/container/UserManagement/ActivateUser";
import ActiveUser from "./ui/container/UserManagement/ActiveUser"
import ReadymadeDataset from "./ui/container/DataSet/ReadymadeDataset.jsx/ReadymadeDataset";
import PopUp from "./ui/container/DataSet/ReadymadeDataset.jsx/PopUp";
import FilterList from "./ui/container/DataSet/DatasetView/FilterList";
import Reset from "./ui/container/UserManagement/Reset";
import Benchmark from './ui/container/Model/ModelSearch/Benchmark';
import ExploreModels from "./ui/container/Model/ModelSearch/ExploreModels"
import SearchModelDetail from './ui/container/Model/ModelSearch/ModelDetail/SearchModelDetail';

const PrivateRoute = ({ path, component: Component, authenticate, title, token, ...rest }) => {
  return (
    <Route
      {...rest}
      render={(props) =>

        authenticate() ? (
          title === "Dashboard" ? <Dashboard /> :
            <Layout component={Component} {...rest} />
        ) : (
          // <Redirect to={`${process.env.PUBLIC_URL}/user/login`}/>
          <Redirect to={{
            pathname: `${process.env.PUBLIC_URL}/user/login`,
            from: path
          }} />

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
          <Route exact path={`${process.env.PUBLIC_URL}/`}
            component={Dashboard}
          />
          <Route
            exact
            path={`${process.env.PUBLIC_URL}/user/:page`}
            component={Login}
          />
          <Route
            path={`${process.env.PUBLIC_URL}/user/:page/:email/:public/:private/:time`}
            component={Login}
          />

          <Route
            path={`${process.env.PUBLIC_URL}/activate/:email/:userId/:time?`}
            component={ActivateUser}
          />

          <Route
            path={`${process.env.PUBLIC_URL}/benchmark/:params/:srno?`}

            component={Benchmark}

          />
          <Route
            path={`${process.env.PUBLIC_URL}/model/explore-models`}

            component={ExploreModels}

          />
          <Route
            path={`${process.env.PUBLIC_URL}/search-model/:srno?/:model?`}

            component={SearchModelDetail}

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
            path={`${process.env.PUBLIC_URL}/dataset/my-contribution/:added?`}
            title={"My Contribution"}
            authenticate={authenticateUser}
            component={ContributionList}
            currentMenu="contribution-list"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/model/my-contribution/:added?`}
            title={"My Contribution"}
            authenticate={authenticateUser}
            component={ModelContributionList}
            currentMenu="contribution-list"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/dataset/upload`}
            title={"Submit Dataset"}
            userRoles={[""]}
            component={SubmitDataset}
            authenticate={authenticateUser}
            currentMenu="submit-dataset"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/model/upload`}
            title={"Submit Dataset"}
            userRoles={[""]}
            component={SubmitModel}
            authenticate={authenticateUser}
            currentMenu="submit-model"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/dashboard`}
            title={"Dashboard"}
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
            path={`${process.env.PUBLIC_URL}/:type/submission/:reqno`}
            title={"Submission status"}
            userRoles={[""]}
            component={SubmissionSubmission}
            authenticate={authenticateUser}
            currentMenu="submission-status"
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

          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/pop-up`}
            userRoles={[""]}
            component={PopUp}
            authenticate={authenticateUser}
            currentMenu="pop-up"
            dontShowHeader={false}
          />

          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/filter-list`}
            userRoles={[""]}
            component={FilterList}
            authenticate={authenticateUser}
            currentMenu="pop-up"
            dontShowHeader={false}
          />


          {/* <Route
            path={`${process.env.PUBLIC_URL}/user/reset-password/:email/:userId/:time`}
            component={Reset}
          /> */}

        </Switch>
      </div>
    </Router>
  );
}
