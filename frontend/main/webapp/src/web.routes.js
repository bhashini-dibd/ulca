import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import history from "./web.history";
import Layout from "./ui/Layout";
import PublicLayout from "./ui/Layout";

import Login from "./ui/container/UserManagement/UserManagement";
import SubmitDataset from "./ui/container/DataSet/UploadDataset/SubmitDataset";
import SubmitModel from "./ui/container/Model/UploadModel/SubmitModel";
import ContributionList from "./ui/container/DataSet/DatasetView/ContributionList";
import ModelContributionList from "./ui/container/Model/ModelView/ContributionList";
import DetailedStatus from "./ui/container/DataSet/DatasetView/DetailedStatus";
import Dashboard from "./ui/container/Dashboard/ChartRender";
// import Dashboard from "./ui/container/Dashboard/Dashboard";
import SubmissionSubmission from "./ui/components/Datasets&Model/SubmissionStatus";
import authenticateUser from "./configs/authenticate";
import MySearches from "./ui/container/DataSet/DatasetSeatch/MySearches";
import SearchAndDownloadRecords from "./ui/container/DataSet/DatasetSeatch/SearchDownloadRecords";
import ActivateUser from "./ui/container/UserManagement/ActivateUser";
import ActiveUser from "./ui/container/UserManagement/ActiveUser";
import ReadymadeDataset from "./ui/container/DataSet/ReadymadeDataset/ReadymadeDataset";
import PopUp from "./ui/container/DataSet/ReadymadeDataset/PopUp";
import FilterList from "./ui/container/DataSet/DatasetView/FilterList";
import Reset from "./ui/container/UserManagement/Reset";
import Benchmark from "./ui/container/Model/ModelSearch/Benchmark";
import ExploreModels from "./ui/container/Model/ModelSearch/ExploreModels";
import SearchModelDetail from "./ui/container/Model/ModelSearch/ModelDetail/SearchModelDetail";
import Leaderboard from "./ui/container/Model/ModelLeaderboard/Leaderboard";
import BenchmarkModels from "./ui/container/Model/BenchmarkModel/BenchmarkDataset";
import BenchmarkDetails from "./ui/container/Model/BenchmarkModel/BenchmarkDetail";
import ViewUserDetails from "./ui/container/Admin/ViewUserDetail";
import {useEffect} from 'react';
import GetMasterDataAPI from './redux/actions/api/Common/getMasterData';
import { useDispatch } from "react-redux";
import APITransport from './redux/actions/apitransport/apitransport';
import DatasetMetrics from "./ui/container/DataSet/DatasetMetrics/DatasetMetrics";
import MyProfile from "./ui/container/UserManagement/MyProfile";
import GlossaryProfile from "./ui/container/UserManagement/GlossaryProfile";
import SpeakerEnrollment from "./ui/container/UserManagement/SpeakerEnrollment.jsx";

const PrivateRoute = ({
  path,
  component: Component,
  authenticate,
  title,
  token,
  type,
  index,
  ...rest
}) => {
  return (
    <Route
      {...rest}
      render={(props) => {
        return authenticate() ? (
          title === "Dashboard" ? (
            <Dashboard />
          ) : (
            <Layout component={Component} type={type} index={index} {...rest} />
          )
        ) : (
          // <Redirect to={`${process.env.PUBLIC_URL}/user/login`}/>
          <Redirect
            to={{
              pathname: `${process.env.PUBLIC_URL}/user/login`,
              from: props.location.pathname,
            }}
          />
        );
      }}
    />
  );
};

export default function App() {
  const dispatch = useDispatch();

  useEffect(() => {
      // const obj = new GetMasterDataAPI(["feedbackQns","languages", "domains"]);
      const obj = new GetMasterDataAPI(["feedbackQns","languages"]);
      dispatch(APITransport(obj));
  }, [])

  return (
    <Router history={history} basename="/">
      <Switch>
        <Route
          exact
          path={`${process.env.PUBLIC_URL.replace(
            process.env.PUBLIC_URL.substr(
              process.env.PUBLIC_URL.lastIndexOf("/")
            ),
            ""
          )}/`}
          component={Dashboard}
        />
        <Route
          exact
          path={`${process.env.PUBLIC_URL}/`}
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
          render={(props) => (
            <PublicLayout type={"models"} index={1} component={ExploreModels} />
          )}
        />
        <Route
          path={`${process.env.PUBLIC_URL}/model/benchmark-datasets`}
          render={(props) => (
            <PublicLayout
              type={"models"}
              index={2}
              component={BenchmarkModels}
            />
          )}
        />
        <Route
          path={`${process.env.PUBLIC_URL}/model/benchmark-details/:benchmarkId`}
          component={BenchmarkDetails}
        />

        <Route
          path={`${process.env.PUBLIC_URL}/dataset/readymade-datasets`}
          render={(props) => (
            <PublicLayout
              type={"dataset"}
              index={4}
              component={ReadymadeDataset}
            />
          )}
        />
        <Route
          path={`${process.env.PUBLIC_URL}/search-model/:srno?/:model?`}
          component={SearchModelDetail}
        />

        <Route
          exact
          path={`${process.env.PUBLIC_URL}/dashboard`}
          component={Dashboard}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/dataset-status/:id`}
          title={"Submit Dataset"}
          component={DetailedStatus}
          authenticate={authenticateUser}
          currentMenu="submit-dataset"
          dontShowHeader={false}
          type={"dataset"}
          index={0}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/dataset/my-contribution/:added?`}
          title={"My Contribution"}
          authenticate={authenticateUser}
          component={ContributionList}
          currentMenu="contribution-list"
          dontShowHeader={false}
          type={"dataset"}
          index={0}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/model/my-contribution/:added?`}
          title={"My Contribution"}
          authenticate={authenticateUser}
          component={ModelContributionList}
          currentMenu="contribution-list"
          dontShowHeader={false}
          type={"models"}
          index={0}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/dataset/upload`}
          title={"Submit Dataset"}
          component={SubmitDataset}
          authenticate={authenticateUser}
          currentMenu="submit-dataset"
          dontShowHeader={false}
          type={"dataset"}
          index={3}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/dataset/reports`}
          title={"Dataset Metrics"}
          component={DatasetMetrics}
          authenticate={authenticateUser}
          currentMenu="dataset-metrics"
          dontShowHeader={false}
          type={"dataset"}
          index={4}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/model/upload`}
          title={"Submit Dataset"}
          userRoles={[""]}
          component={SubmitModel}
          authenticate={authenticateUser}
          currentMenu="submit-model"
          dontShowHeader={false}
          type={"models"}
          index={3}
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
          path={`${process.env.PUBLIC_URL}/my-searches/:added?`}
          component={MySearches}
          authenticate={authenticateUser}
          currentMenu="submit-dataset"
          dontShowHeader={false}
          type={"dataset"}
          index={1}
        />
        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/readymade-dataset`}
          component={ReadymadeDataset}
          authenticate={authenticateUser}
          currentMenu="submit-dataset"
          dontShowHeader={false}
          type={"dataset"}
          index={0}
        />

        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/:type/submission/:reqno`}
          title={"Submission status"}
          component={SubmissionSubmission}
          authenticate={authenticateUser}
          currentMenu="submission-status"
          dontShowHeader={false}
          type={""}
          index={0}
        />

        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/search-and-download-rec/:params/:srno`}
          component={SearchAndDownloadRecords}
          authenticate={authenticateUser}
          currentMenu="search-and-download-rec"
          dontShowHeader={false}
          type={"dataset"}
          index={2}
        />

        <Route
          path={`${process.env.PUBLIC_URL}/active-user`}
          component={ActiveUser}
        />

        <PrivateRoute
          path={`${process.env.PUBLIC_URL}/pop-up`}
          component={PopUp}
          authenticate={authenticateUser}
          currentMenu="pop-up"
          dontShowHeader={false}
          type={"dataset"}
          index={2}
        />

          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/filter-list`}
            component={FilterList}
            authenticate={authenticateUser}
            currentMenu="pop-up"
            dontShowHeader={false}
            type={"dataset"}
            index={2}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/admin/view-user-details`}
            component={ViewUserDetails}
            authenticate={authenticateUser}
            currentMenu="view-user-details"
            dontShowHeader={false}
            type={"admin"}
            index={0}
          />
           <PrivateRoute
            path={`${process.env.PUBLIC_URL}/profile`}
            title={"My Profile"}
            component={MyProfile}
            authenticate={authenticateUser}
            currentMenu="user-my-profile"
            dontShowHeader={false}
          />
           <PrivateRoute
            path={`${process.env.PUBLIC_URL}/glossary`}
            title={"Glossary Profile"}
            component={GlossaryProfile}
            authenticate={authenticateUser}
            currentMenu="user-my-profile"
            dontShowHeader={false}
          />
          <PrivateRoute
            path={`${process.env.PUBLIC_URL}/speaker-recognition`}
            title={"Speaker Recognition"}
            component={SpeakerEnrollment}
            authenticate={authenticateUser}
            currentMenu="user-my-profile"
            dontShowHeader={false}
          />
          <Route
            path={"*"}
            component={Dashboard}
          />

        {/* <Route
            path={`${process.env.PUBLIC_URL}/model/leaderboard`}
            render={(props) =>
            <PublicLayout type = {"models"}
            index={2}
            component={Leaderboard} />
            }
            

          /> */}

        {/* <Route
            path={`${process.env.PUBLIC_URL}/user/reset-password/:email/:userId/:time`}
            component={Reset}
          /> */}
      </Switch>
    </Router>
  );
}
