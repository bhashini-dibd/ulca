import React, { useEffect, useState } from "react";

import { useSelector } from "react-redux";

import { useHistory } from "react-router-dom";
import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import Theme from "./theme/theme-default";
import { withStyles, MuiThemeProvider } from "@material-ui/core/styles";
import GlobalStyles from "./styles/Styles";
import Spinner from "./components/common/Spinner";
import Snackbar from './components/common/Snackbar';


function App(props) {
  const Component = props.component;
  const { classes,type,index  } = props;

  const handleClose = () =>{
    setPopup(false)
  }

  

  return (
    <MuiThemeProvider theme={Theme}>
            <Header type={type} index={index} style={{ marginBottom: "10px" }} />
          <Component />
          <Footer/>
        </MuiThemeProvider>
          
       
  );
}
export default withStyles(GlobalStyles(Theme), { withTheme: true })(App);
