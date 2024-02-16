import React, { Suspense, useEffect, useState } from "react";

import { useSelector } from "react-redux";

import { useHistory } from "react-router-dom";
// import Header from "./components/common/Header";
// import Footer from "./components/common/Footer";
import Theme from "./theme/theme-default";
import { withStyles, MuiThemeProvider } from "@material-ui/core/styles";
import GlobalStyles from "./styles/Styles";
import Spinner from "./components/common/Spinner";
import Snackbar from "./components/common/Snackbar";

const Header = React.lazy(() => import("./components/common/Header"));
const Footer = React.lazy(() => import("./components/common/Footer"));

function App(props) {
  const Component = props.component;
  const { classes, type, index, userRoles } = props;
  const [show, setShow] = useState(false);
  const [popUp, setPopup] = useState(true);
  const apiStatus = useSelector((state) => state.apiStatus);
  const history = useHistory();
  const renderSpinner = () => {
    if (apiStatus.progress) {
      return <Spinner />;
    }
  };

  const handleClose = () => {
    setPopup(false);
  };

  const renderError = () => {
    if (apiStatus.unauthrized) {
      setTimeout(
        () => history.push(`${process.env.PUBLIC_URL}/user/login`),
        3000
      );
    }
    if (apiStatus.error && apiStatus.message && popUp) {
      return (
        <Snackbar
          open={true}
          handleClose={handleClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={apiStatus.message}
          variant={"error"}
        />
      );
    }
  };

  useEffect(() => {
    if (show) {
      window.removeEventListener('scroll', (e) => { });
    }
  }, [show])

  window.addEventListener('scroll', e => {
    if (window.pageYOffset > 100 && !show) {
      setShow(true);
    }
  })

  return (
    <MuiThemeProvider theme={Theme}>
      <div className={classes.root}>
        <Suspense fallback={<div>Loading....</div>}>
          <Header
            type={type}
            index={index}
            className={classes.headerContainer}
          />
        </Suspense>
        <div className={classes.container}>
          {renderSpinner()}
          {renderError()}
          <Suspense fallback={<div>Loading....</div>}>
            <Component />
          </Suspense>
        </div>
        {/* {show ? <Suspense fallback={<div>Loading....</div>}>
          <Footer />
        </Suspense: <></>} */}
        <Suspense fallback={<div>Loading....</div>}>
          <Footer />
        </Suspense>
      </div>
    </MuiThemeProvider>
  );
}
export default withStyles(GlobalStyles(Theme), { withTheme: true })(App);
