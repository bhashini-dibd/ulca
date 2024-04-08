import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './Layout';
import reportWebVitals from './reportWebVitals';
import Theme from "./theme/theme-default";
import { MuiThemeProvider } from "@material-ui/core";
import i18n from './configs/i18n';

import "slick-carousel/slick/slick-theme.css";
import "slick-carousel/slick/slick.css";

import "./App.css";

 
ReactDOM.render(
  <React.StrictMode>
    <MuiThemeProvider theme={Theme}>
    <App />
    </MuiThemeProvider>
  </React.StrictMode>,
  
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
